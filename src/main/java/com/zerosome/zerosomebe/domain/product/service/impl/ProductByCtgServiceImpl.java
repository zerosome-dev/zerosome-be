package com.zerosome.zerosomebe.domain.product.service.impl;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zerosome.zerosomebe.domain.code.entity.CommCode;
import com.zerosome.zerosomebe.domain.code.repository.CommCodeJpaRepository;
import com.zerosome.zerosomebe.domain.product.dto.OrderType;
import com.zerosome.zerosomebe.domain.product.dto.ProductByCtgListRequest;
import com.zerosome.zerosomebe.domain.product.dto.ProductByCtgResponse;
import com.zerosome.zerosomebe.domain.product.repository.ProductJpaRepository;
import com.zerosome.zerosomebe.domain.product.service.ProductByCtgService;
import com.zerosome.zerosomebe.global.entity.OffsetPageResponse;
import com.zerosome.zerosomebe.global.error.exception.CategoryNotFoundException;
import com.zerosome.zerosomebe.global.error.exception.NotSubCategoryException;
import com.zerosome.zerosomebe.global.s3.S3Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductByCtgServiceImpl implements ProductByCtgService {
    private final S3Service s3Service;
    private final ProductJpaRepository productRepository;
    private final CommCodeJpaRepository commCodeRepository;

    @Override
    public OffsetPageResponse<List<ProductByCtgResponse>> getProductList
            (String d2categoryCode, Integer offset, Integer limit, ProductByCtgListRequest filter) {
        CommCode d2Category = commCodeRepository.findFirstByCode(d2categoryCode).orElseThrow(
                CategoryNotFoundException::new);
        if (d2Category.getSuperCode() == null) {
            throw new NotSubCategoryException();
        }
        
        List<String> categoryCodeList = d2Category.isSubNoOptionYn()
                                        ? commCodeRepository.findAllBySuperCode(d2Category.getSuperCode())
                                                            .stream()
                                                            .map(CommCode::getCode).toList()
                                        : List.of(d2categoryCode);

        List<String> brandList = filter == null ? null : filter.getBrandList();
        List<String> zeroCtgList = filter == null ? null : filter.getZeroCtgList();

        if (offset == null) {
            offset = 0;
        }
        if (limit == null) {
            limit = 10;
        }

        OrderType orderType = filter == null ? null : filter.getOrderType();
        JpaSort sort;
        // Sort orders;
        if (orderType == OrderType.REVIEWHIGH) {
            sort = JpaSort.unsafe(Direction.DESC, "ROUND(AVG(rv.rating), 1)")
                          .andUnsafe(Direction.DESC, "regDate");
            // orders = Sort.by(Sort.Order.desc("ROUND(AVG(rv.rating), 1)"), Sort.Order.desc("regDate"));
        } else if (orderType == OrderType.REVIEWLOW) {
            sort = JpaSort.unsafe(Direction.ASC, "ROUND(AVG(rv.rating), 1)")
                          .andUnsafe(Direction.DESC, "regDate");
            // orders = Sort.by(Sort.Order.asc("ROUND(AVG(rv.rating), 1)"), Sort.Order.desc("regDate"));
        } else if (orderType == OrderType.REVIEWMANY) {
            sort = JpaSort.unsafe(Direction.DESC, "COUNT(rv.id)")
                          .andUnsafe(Direction.DESC, "regDate");
            // orders = Sort.by(Sort.Order.desc("COUNT(rv.id)"), Sort.Order.desc("regDate"));
        } else if (orderType == OrderType.REVIEWFEW) {
            sort = JpaSort.unsafe(Direction.ASC, "COUNT(rv.id)")
                          .andUnsafe(Direction.DESC, "regDate");
            //orders = Sort.by(Sort.Order.asc("COUNT(rv.id)"), Sort.Order.desc("regDate"));
        } else {
            sort = JpaSort.unsafe(Direction.DESC, "regDate")
                          .andUnsafe(Direction.DESC, "ROUND(AVG(rv.rating), 1)");
            //orders = Sort.by(Sort.Order.desc("regDate"), Sort.Order.desc("ROUND(AVG(rv.rating), 1)"));
        }
        Pageable pageable = PageRequest.of(offset, limit, sort);

        brandList = brandList != null && brandList.size() > 0 ? brandList : null;
        zeroCtgList = zeroCtgList != null && zeroCtgList.size() > 0 ? zeroCtgList : null;
        List<ProductByCtgResponse> data
                = productRepository.findProductByCtgList(categoryCodeList, brandList, zeroCtgList, pageable)
                                   .stream()
                                   .map(
                                           product -> product.setImage(
                                                   s3Service.generateGetPresignedUrl(product.getImage())))
                                   .toList();

        return OffsetPageResponse.of(data, limit, offset + 1);
        /*
            d2CategoryCode
            - 코드 정보를 가져와서, subNoOptionYn = true 이면 superCode로 같은 d2 카테고리 코드 다 가져와야함
            - 코드 정보를 가져와서, subNoOptionYn = false 이면 이 코드 그대로 쓰면 됨

            offset
            - null이면, offset = 0
            - LIMIT offset, limit

            limit
            - null이면, limit = 10
            - LIMIT offset, limit

            (ProductByCtgListRequest filter)
            filter.getOrderType().equals(OrderType.RECENT)
            - ORDER BY p.regDate DESC, ROUND(AVG(rv.rating), 1) DESC

            filter.getOrderType().equals(OrderType.REVIEWHIGH)
            - ORDER BY ROUND(AVG(rv.rating), 1) DESC, p.regDate DESC

            filter.getOrderType().equals(OrderType.REVIEWLOW)
            - ORDER BY ROUND(AVG(rv.rating), 1) ASC, p.regDate DESC

            filter.getOrderType().equals(OrderType.REVIEWMANY)
            - ORDER BY COUNT(rv.id) DESC, p.regDate DESC

            filter.getOrderType().equals(OrderType.REVIEWFEW)
            - ORDER BY COUNT(rv.id) ASC, p.regDate DESC

            filter.getBrandList() != null
            - p.brandCode IN filter.getBrandList()

            filter.getZeroCtgList() != null
            - left join 한 뒤, where 절
            - zeroCategory IN filter.getZeroCtgList()

         */
    }
}

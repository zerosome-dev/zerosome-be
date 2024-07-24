package com.zerosome.zerosomebe.domain.datadump.service.impl;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.zerosome.zerosomebe.domain.code.entity.CommCode;
import com.zerosome.zerosomebe.domain.code.repository.CommCodeJpaRepository;
import com.zerosome.zerosomebe.domain.datadump.repository.DataDumpRepository;
import com.zerosome.zerosomebe.domain.datadump.service.DataDumpService;
import com.zerosome.zerosomebe.domain.product.entity.Nutrient;
import com.zerosome.zerosomebe.domain.product.entity.Product;
import com.zerosome.zerosomebe.domain.product.entity.SalesStore;
import com.zerosome.zerosomebe.domain.product.repository.NutrientJpaRepository;
import com.zerosome.zerosomebe.domain.product.repository.ProductJpaRepository;
import com.zerosome.zerosomebe.domain.product.repository.SalesStoreJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DataDumpServiceImpl implements DataDumpService {
    private final DataDumpRepository dataDumpRepository;
    private final ProductJpaRepository productRepository;
    private final CommCodeJpaRepository commCodeRepository;
    private final NutrientJpaRepository nutrientRepository;
    private final SalesStoreJpaRepository salesStoreRepository;

    @Override
    @Transactional
    public int loadProductDataByCsv(String fileName) throws IOException, CsvValidationException {
        int lastIdx = 0;
        System.out.println("FILE NAME : " + fileName);

        CSVReader csvReader = new CSVReader(new FileReader(fileName));

        // Header Row 1, 2 생략
        csvReader.readNext();
        //csvReader.readNext();

        String[] nextRecord;
        while ((nextRecord = csvReader.readNext()) != null) {
            lastIdx++;
            // START PARSING
            /* 상품 정보 */
            String id = nextRecord[0];
            String depth2 = nextRecord[1];
            String image = "prod/product/" + nextRecord[2];
            String brandName = nextRecord[3];
            String productName = nextRecord[4];

            System.out.println("PRODUCT NAME : " + productName);

            CommCode brand = commCodeRepository.findFirstByCodeDescAndGroupCode(brandName, "BRAND")
                                               .orElse(null);
            if (brand == null) {
                System.out.println("PASS - BRAND NULL");
                continue;
            }
            String brandCode = brand.getCode();

            CommCode category = commCodeRepository.findFirstByCodeDescAndGroupCode(depth2, "CTG")
                                                  .orElse(null);
            if (category == null) {
                System.out.println("PASS - CATEGORY NULL");
                continue;
            }
            String categoryCode = category.getCode();

            boolean showYn = true;
            boolean rolloutYn = true;
            Long regMember = 0L;

            Product product = productRepository.save(
                    new Product(productName, brandCode, categoryCode, image, showYn, rolloutYn, regMember)
            );

            System.out.println("PRODUCT ID : " + product.getId());

            /* 영양 성분 */
            List<Nutrient> nutrientList = new ArrayList<>();

            String nutr_total_name = "총 내용량";
            String nutr_total_amount = getNotNullValue(nextRecord[6]);
            String nutr_total_amount_unit = getNotNullValue(nextRecord[7]);
            String nutr_total_percentage = null;
            String nutr_total_percentage_unit = null;
            nutrientList.add(
                    getDefaultNutrientIfNull(product.getId(), nutr_total_name, nutr_total_amount,
                                             nutr_total_amount_unit,
                                             nutr_total_percentage,
                                             nutr_total_percentage_unit));

            String nutr_serving_name = "기준 용량";
            String nutr_serving_amount = nextRecord[11];
            String nutr_serving_amount_unit = nextRecord[12];
            String nutr_serving_percentage = nextRecord[14]; // 열량 amount
            String nutr_serving_percentage_unit = nextRecord[15]; // 열량 unit
            nutrientList.add(
                    getDefaultNutrientIfNull(product.getId(), nutr_serving_name, nutr_serving_amount,
                                             nutr_serving_amount_unit,
                                             nutr_serving_percentage,
                                             nutr_serving_percentage_unit));

            String nutr_protien_name = "단백질";
            String nutr_protien_amount = nextRecord[19];
            String nutr_protien_amount_unit = nextRecord[20];
            String nutr_protien_percentage = nextRecord[21];
            String nutr_protien_percentage_unit = nextRecord[22];
            nutrientList.add(
                    getDefaultNutrientIfNull(product.getId(), nutr_protien_name, nutr_protien_amount,
                                             nutr_protien_amount_unit,
                                             nutr_protien_percentage,
                                             nutr_protien_percentage_unit));

            String nutr_fat_name = "지방";
            String nutr_fat_amount = nextRecord[24];
            String nutr_fat_amount_unit = nextRecord[25];
            String nutr_fat_percentage = nextRecord[26];
            String nutr_fat_percentage_unit = nextRecord[27];
            nutrientList.add(
                    getDefaultNutrientIfNull(product.getId(), nutr_fat_name, nutr_fat_amount,
                                             nutr_fat_amount_unit, nutr_fat_percentage,
                                             nutr_fat_percentage_unit));

            String nutr_saturatedfat_name = "포화지방";
            String nutr_saturatedfat_amount = nextRecord[29];
            String nutr_saturatedfat_amount_unit = nextRecord[30];
            String nutr_saturatedfat_percentage = nextRecord[31];
            String nutr_saturatedfat_percentage_unit = nextRecord[32];
            nutrientList.add(
                    getDefaultNutrientIfNull(product.getId(), nutr_saturatedfat_name, nutr_saturatedfat_amount,
                                             nutr_saturatedfat_amount_unit,
                                             nutr_saturatedfat_percentage,
                                             nutr_saturatedfat_percentage_unit));

            String nutr_transfat_name = "트랜스지방";
            String nutr_transfat_amount = nextRecord[34];
            String nutr_transfat_amount_unit = nextRecord[35];
            String nutr_transfat_percentage = nextRecord[36];
            String nutr_transfat_percentage_unit = nextRecord[37];
            nutrientList.add(
                    getDefaultNutrientIfNull(product.getId(), nutr_saturatedfat_name, nutr_saturatedfat_amount,
                                             nutr_saturatedfat_amount_unit,
                                             nutr_saturatedfat_percentage,
                                             nutr_saturatedfat_percentage_unit));

            String nutr_carbs_name = "탄수화물";
            String nutr_carbs_amount = nextRecord[39];
            String nutr_carbs_amount_unit = nextRecord[40];
            String nutr_carbs_percentage = nextRecord[41];
            String nutr_carbs_percentage_unit = nextRecord[42];
            nutrientList.add(
                    getDefaultNutrientIfNull(product.getId(), nutr_carbs_name, nutr_carbs_amount,
                                             nutr_carbs_amount_unit,
                                             nutr_carbs_percentage,
                                             nutr_carbs_percentage_unit));

            String nutr_sugar_name = "당류";
            String nutr_sugar_amount = nextRecord[44];
            String nutr_sugar_amount_unit = nextRecord[45];
            String nutr_sugar_percentage = nextRecord[46];
            String nutr_sugar_percentage_unit = nextRecord[47];
            //String nutr_sugar_percentage_unit = nextRecord[47];
            nutrientList.add(
                    getDefaultNutrientIfNull(product.getId(), nutr_sugar_name, nutr_sugar_amount,
                                             nutr_sugar_amount_unit,
                                             nutr_sugar_percentage,
                                             nutr_sugar_percentage_unit));

            String nutr_sodium_name = "나트륨";
            String nutr_sodium_amount = nextRecord[49];
            String nutr_sodium_amount_unit = nextRecord[50];
            String nutr_sodium_percentage = nextRecord[51];
            String nutr_sodium_percentage_unit = nextRecord[52];
            nutrientList.add(
                    getDefaultNutrientIfNull(product.getId(), nutr_sodium_name, nutr_sodium_amount,
                                             nutr_sodium_amount_unit,
                                             nutr_sodium_percentage,
                                             nutr_sodium_percentage_unit));

            String nutr_cholesterol_name = "콜레스테롤";
            String nutr_cholesterol_amount = nextRecord[54];
            String nutr_cholesterol_amount_unit = nextRecord[55];
            String nutr_cholesterol_percentage = nextRecord[56];
            String nutr_cholesterol_percentage_unit = nextRecord[57];
            nutrientList.add(
                    getDefaultNutrientIfNull(product.getId(), nutr_cholesterol_name, nutr_cholesterol_amount,
                                             nutr_cholesterol_amount_unit,
                                             nutr_cholesterol_percentage,
                                             nutr_cholesterol_percentage_unit));

            String nutr_caffeine_name = "카페인";
            String nutr_caffeine_amount = nextRecord[59];
            String nutr_caffeine_amount_unit = nextRecord[60];
            String nutr_caffeine_percentage = "0";
            String nutr_caffeine_percentage_unit = "%";
            nutrientList.add(
                    getDefaultNutrientIfNull(product.getId(), nutr_caffeine_name, nutr_caffeine_amount,
                                             nutr_caffeine_amount_unit,
                                             nutr_caffeine_percentage,
                                             nutr_caffeine_percentage_unit));

            nutrientRepository.saveAll(nutrientList);
            System.out.println("NUTRIENT INSERT SIZE : " + nutrientList.size());

            /* 판매처 */
            List<SalesStore> storeList = new ArrayList<>();

            int idx = 63;
            String storeName = getNotNullValue(nextRecord[idx]);
            String storeUrl = getNotNullValue(nextRecord[idx + 1]);
            boolean offlineYn;
            //CommCode store;
            System.out.println("INIT STORE NAME : " + storeName);
            while (getNotNullValue(storeName) != null) {
                System.out.println("STORE INDEX : " + idx);

                idx += 2;
                offlineYn = storeUrl == null;
                /* 판매처 공통코드 등록이 안되서 일단 판매처 명으로 입력 */
                /*
                store = commCodeRepository.findFirstByCodeDescAndGroupCode(storeName, "ST").orElse(null);
                if (store == null) {
                    continue;
                }
                storeList.add(
                        new SalesStore(product.getId(), offlineYn, store.getCode(), storeUrl));
                 */
                storeList.add(
                        new SalesStore(product.getId(), offlineYn, storeName, storeUrl));

                storeName = nextRecord[idx];
                storeUrl = nextRecord[idx + 1];
            }

            salesStoreRepository.saveAll(storeList);
        }
        csvReader.close();
        return lastIdx;
    }

    private Nutrient getDefaultNutrientIfNull(Long productId, String nutr_name, String nutr_amount,
                                              String nutr_amount_unit,
                                              String nutr_percentage,
                                              String nutr_percentage_unit) {
        nutr_amount = getNotNullValue(nutr_amount);
        nutr_amount_unit = getNotNullValue(nutr_amount_unit);
        nutr_percentage = getNotNullValue(nutr_percentage);
        nutr_percentage_unit = getNotNullValue(nutr_percentage_unit);

        if (nutr_name.equals("기준 용량")) {
            if (nutr_amount == null) {
                nutr_amount = "0";
            }
            if (nutr_amount_unit == null) {
                nutr_amount_unit = "g";
            }
            if (nutr_percentage == null) {
                nutr_percentage = "0";
            }
            if (nutr_percentage_unit == null) {
                nutr_percentage_unit = "kcal";
            }
        } else {
            if (nutr_amount == null) {
                nutr_amount = "0";
            }
            if (nutr_amount_unit == null) {
                nutr_amount_unit = "g";
            }
            if (nutr_percentage == null) {
                nutr_percentage = "0";
            }
            if (nutr_percentage_unit == null) {
                nutr_percentage_unit = "%";
            }
        }

        return Nutrient.builder()
                       .productId(productId)
                       .name(nutr_name)
                       .amount(Double.parseDouble(nutr_amount))
                       .amountUnit(nutr_amount_unit)
                       .percentage(Double.parseDouble(nutr_percentage))
                       .percentageUnit(nutr_percentage_unit)
                       .build();
    }

    private String getNotNullValue(String value) {
        return value == null || value.equals("") || value.equals(" ") || value.equals("-")
               || value.equals("#VALUE!") ? null : value;
    }
}

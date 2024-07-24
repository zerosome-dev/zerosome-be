package com.zerosome.zerosomebe.domain.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "SALES_STORE")
public class SalesStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STORE_IDX", nullable = false)
    private Long id;

    @Column(name = "PRD_IDX", nullable = false)
    private Long productId;

    @Column(name = "OFFLINE_YN", nullable = false)
    private boolean offlineYn;

    @Column(name = "STORE", nullable = true)
    private String storeCode;

    @Column(name = "URL", nullable = true)
    private String url;

    public SalesStore(Long productId, boolean offlineYn, String storeCode, String url) {
        this.productId = productId;
        this.offlineYn = offlineYn;
        this.storeCode = storeCode;
        this.url = url;
    }

    /*
       샘플 데이터

       INSERT INTO SALES_STORE (STORE_IDX, PRD_IDX, OFFLINE_YN, STORE, URL)
       VALUES (1, 1, FALSE, 'ST11', 'https://cu.bgfretail.com/product/view.do?category=product&gdIdx=19795');

       INSERT INTO SALES_STORE (STORE_IDX, PRD_IDX, OFFLINE_YN, STORE, URL)
       VALUES (2, 1, TRUE, 'ST5', NULL);

       INSERT INTO SALES_STORE (STORE_IDX, PRD_IDX, OFFLINE_YN, STORE, URL)
       VALUES (3, 2, FALSE, 'ST1', NULL);

       INSERT INTO SALES_STORE (STORE_IDX, PRD_IDX, OFFLINE_YN, STORE, URL)
       VALUES (4, 3, FALSE, 'ST1', NULL);

       INSERT INTO SALES_STORE (STORE_IDX, PRD_IDX, OFFLINE_YN, STORE, URL)
       VALUES (5, 3, TRUE, 'ST6', NULL);

     */
}

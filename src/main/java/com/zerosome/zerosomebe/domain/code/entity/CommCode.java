package com.zerosome.zerosomebe.domain.code.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "COMM_CODE")
public class CommCode {
    @Id
    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "GROUP_CODE", nullable = false)
    private String groupCode;

    @Column(name = "CODE_DESC", nullable = false)
    private String codeDesc;

    @Column(name = "SUPER_CODE", nullable = true)
    private String superCode;

    @Column(name = "SHOW_YN", nullable = false)
    private boolean showYn;

    @Column(name = "SUB_NO_OPTION_YN", nullable = false)
    private boolean subNoOptionYn;

    /*
        샘플 데이터

        INSERT INTO COMM_CODE (CODE, GROUP_CODE, CODE_DESC, SUPER_CODE, SHOW_YN, SUB_NO_OPTION_YN)
        VALUES ('B001', 'BRAND', '스타벅스', null, true, false);

     */
}

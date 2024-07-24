package com.zerosome.zerosomebe.domain.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AllFoodCategory {
    String d1CategoryCode;
    String d1CategoryName;
    String d2CategoryCode;
    String d2CategoryName;
    String d2CategoryImage;
    boolean noOptionYn;
}

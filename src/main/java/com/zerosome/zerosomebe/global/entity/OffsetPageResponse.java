package com.zerosome.zerosomebe.global.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(description = "Pagination Response")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OffsetPageResponse<T> {

    public static <T> OffsetPageResponse<T> of(T data, int limit, int offset) {
        OffsetPageResponse<T> response = new OffsetPageResponse<>();
        response.content = data;
        response.limit = limit;
        response.offset = offset;
        return response;
    }

    @Schema(description = "내부 데이터")
    T content;
    @Schema(description = "1번에 조회한 데이터 개수")
    int limit;

    @Schema(description = "pageNumber")
    int offset;

}

package com.zerosome.zerosomebe.domain.datadump.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opencsv.exceptions.CsvValidationException;
import com.zerosome.zerosomebe.domain.datadump.service.DataDumpService;
import com.zerosome.zerosomebe.global.entity.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/local/datadump")
public class DataDumpController {

    private final DataDumpService dumpService;

    @Operation(summary = "csv로 데이터 덤프")
    @PostMapping("/product")
    public ResponseEntity<ApiResponse<Integer>> loadProductDataByCsv(
            @Parameter(name = "fileName", description = "csv filename", in = ParameterIn.QUERY)
            @RequestParam(value = "fileName", required = true) String fileName)
            throws CsvValidationException, IOException {
        fileName = "/Users/maminji/Development/zerosome/dataimport/server_reading_dir/" + fileName;
        int response = dumpService.loadProductDataByCsv(fileName);
        return ApiResponse.ofSuccess(HttpStatus.OK, response);
    }
}

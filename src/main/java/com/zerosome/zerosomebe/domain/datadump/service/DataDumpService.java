package com.zerosome.zerosomebe.domain.datadump.service;

import java.io.IOException;

import com.opencsv.exceptions.CsvValidationException;

public interface DataDumpService {
    int loadProductDataByCsv(String fileName) throws IOException, CsvValidationException;
}

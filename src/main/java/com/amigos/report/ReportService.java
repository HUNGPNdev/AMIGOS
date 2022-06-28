package com.amigos.report;

import com.amigos.common.ResponseApi;

import java.io.IOException;

public interface ReportService {
    ResponseApi createReport() throws IOException;
    ResponseApi countUser();
    ResponseApi countComment();
    ResponseApi countProduct();
    ResponseApi countCategory();
}

package com.amigos.report;

import com.amigos.common.ResponseApi;
import com.amigos.dto.OrderCartDTO;

import javax.servlet.http.HttpServletRequest;

public interface ReportService {
    ResponseApi createReport();

}

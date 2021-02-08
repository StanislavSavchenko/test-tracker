package com.test.tracker.controller;

import com.test.tracker.model.entity.TaskEntity;
import com.test.tracker.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @ApiOperation(
            value = "Get report",
            response = TaskEntity.class
    )
    @GetMapping(value = "/report", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskEntity> buildReport() {
        return reportService.buildReport();
    }

}

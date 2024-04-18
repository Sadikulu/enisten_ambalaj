package com.kss.controller;

import com.kss.dto.DashboardCountDTO;
import com.kss.dto.response.KSSResponse;
import com.kss.dto.response.ResponseMessage;
import com.kss.service.DatabaseService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/database")
public class DatabaseController {

    private final DatabaseService databaseService;

    @Operation(summary = "Delete all database records except built-in true", description = "This endpoint going to reset all data in database which built-in status is false")
    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<KSSResponse> deleteAllData(){
        databaseService.resetAll();
        KSSResponse response = new KSSResponse(ResponseMessage.DATABASE_RESET_RESPONSE,true,null);
        return  ResponseEntity.ok(response);
    }

    @Operation(summary = "Get count of all sections for dashboard")
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<KSSResponse> getCountOfAllRecords(){
        DashboardCountDTO dashboardCountDTO = databaseService.getCountOfAllRecords();
        KSSResponse response = new KSSResponse(ResponseMessage.COUNT_OF_ALL_RECORDS_RESPONSE,true,dashboardCountDTO);
        return ResponseEntity.ok(response);
    }

}

package com.example.controller;

import com.example.core.CountryStateCapitals;
import com.example.service.PdfGeneratorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
public class PdfGeneratorController {
    @Autowired
    PdfGeneratorService service;

    @RequestMapping(value = "/pdf",
            method = RequestMethod.POST)
    @ApiOperation(value = "Generate a PDF file",
            httpMethod = "POST",
            notes = "Generate a PDF")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Processed Successfully"),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    void generatePdf(@RequestParam final String fileName,
                     @ApiParam(name="countryStateCapitals", value = "countryStateCapitals details", required = true)
                     @RequestBody @Valid final CountryStateCapitals countryStateCapitals) {
        log.info("Begin generatePdf");
        try{
            if(!fileName.endsWith(".pdf"))
                throw new IllegalArgumentException("File name must contain .pdf as extension");

            service.generatePdf(fileName, countryStateCapitals);
        } catch (Exception e){
            log.error("ERROR: " + e.toString());
            throw new RuntimeException(e.toString());
        }
    }

    @RequestMapping(value = "/pdf/encrypt",
            method = RequestMethod.POST)
    @ApiOperation(value = "Encrypt a PDF file",
            httpMethod = "POST",
            notes = "Encrypt a PDF")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Processed Successfully"),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    void encryptPdf(@RequestParam final String fileName) {
        log.info("Begin generatePdf");
        try{
            if(!fileName.endsWith(".pdf"))
                throw new IllegalArgumentException("File name must contain .pdf as extension");

            service.encryptPdf(fileName);
        } catch (Exception e){
            log.error("ERROR: " + e.toString());
            throw new RuntimeException(e.toString());
        }
    }
}

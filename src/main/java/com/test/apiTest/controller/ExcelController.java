package com.test.apiTest.controller;

import com.test.apiTest.response.ApiResponse;
import com.test.apiTest.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/excel")
public class ExcelController {

    @Autowired
    private ExcelService excelService;
// y=this code is working
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadExcel(@RequestParam("file") MultipartFile file,@RequestParam("option") String option) throws IOException {
//        Boolean response=false;
//        if(option.equals("Training") ) {
//             response = this.excelService.saveTrainingNeedExcelData(file);
//        }else if(option.equals("Employee")){
//            response = this.excelService.saveExcelDataCorrect(file);
//        }
//            String responseMessage = "";
//            try {
//                if (response) {
//                    responseMessage = "Excel file uploaded and data successfully added/updated into the database !!";
//                } else {
//                    responseMessage = "Something went wrong please contact developer.";
//
//                }
//                responseMessage = "Uploaded the file successfully: " + file.getOriginalFilename();
//                return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
//                //return ResponseEntity.ok(Map.of("message", responseMessage).toString());
//            } catch (Exception e) {
//                responseMessage = "Could not upload the file: " + file.getOriginalFilename() + "!";
//                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseMessage);
//            }
//
//
//    }

//    @PostMapping("/upload")
//    public ResponseEntity<ApiResponse<?>> uploadExcel(@RequestParam("file") MultipartFile file,
//                                                      @RequestParam(value = "entityName", required = false) String entityName) throws IOException {
//        Boolean response = excelService.saveExcelDataCorrect(file, entityName);
//
//        String responseMessage ="Excel data uploaded successfully!";
//        HttpStatus status;
//
//        if (response) {
//            responseMessage = "Excel data uploaded successfully!";
//            status = HttpStatus.OK;
//        } else {
//            responseMessage = "User's Entity name provided does not match with the Excel data.";
//            status = HttpStatus.BAD_REQUEST;
//        }
//
//        ApiResponse<T> apiResponse = new ApiResponse<>(status.value(), responseMessage, null);
//        return ResponseEntity.status(status).body(apiResponse);
//    }


    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<?>> uploadExcel(@RequestParam("file") MultipartFile file,
                                                      @RequestParam(value = "entityName", required = false) String entityName) throws IOException {
        List<String> errorMessages = new ArrayList<>();
        HttpStatus status = HttpStatus.OK;

        try {
            Boolean response = excelService.saveExcelDataCorrect(file, entityName, errorMessages);

            if (response) {
                // If the response is successful, add a success message
                errorMessages.add("Employee File uploaded and data successfully added/updated into the database !!"+ file.getOriginalFilename());
            } else {
                status = HttpStatus.BAD_REQUEST;
            }
        } catch (Exception e) {
            errorMessages.add("Could not upload the file: " + file.getOriginalFilename() +"-"+ e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        ApiResponse<?> apiResponse = new ApiResponse<>(status.value(), "Excel data processed", errorMessages);
        return ResponseEntity.status(status).body(apiResponse);
    }

    @PostMapping("/trainingNeedsUpload")
    public ResponseEntity<ApiResponse<?>> uploadTrainingNeeds(@RequestParam("file") MultipartFile file,
                                                      @RequestParam(value = "entityName", required = false) String entityName) throws IOException {
        // excelService.saveTrainingNeedExcelData(file);
        List<String> errorMessages = new ArrayList<>();
        HttpStatus status = HttpStatus.OK;

        try {
            Boolean response = this.excelService.saveTrainingNeedExcelData(file, entityName, errorMessages);
            if (response) {
                // If the response is successful, add a success message
                errorMessages.add("Training File uploaded and data successfully added/updated into the database !!"+ file.getOriginalFilename());
            } else {
                status = HttpStatus.BAD_REQUEST;
            }


        } catch (Exception e) {
            errorMessages.add("Could not upload the file: " + file.getOriginalFilename() +"-"+ e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        ApiResponse<?> apiResponse = new ApiResponse<>(status.value(), "Excel data processed", errorMessages);
        return ResponseEntity.status(status).body(apiResponse);
    }


//    @PostMapping("/upload")
//    public  ResponseEntity<ApiResponse> uploadExcel(@RequestParam("file") MultipartFile file,
//                                                    @RequestParam(value = "entityName", required = false) String entityName) throws IOException {
//        Boolean response = excelService.saveExcelDataCorrect(file, entityName);
//
//        String responseMessage;
//        HttpStatus status;
//
//        if (response) {
//            responseMessage = "Excel data uploaded successfully!";
//            status = HttpStatus.OK;
//        } else {
//            responseMessage = "Entity name provided does not match with the Excel data.";
//            status = HttpStatus.BAD_REQUEST;
//        }
//        ApiResponse uploadResponse = new ApiResponse(responseMessage, status);
//        return ResponseEntity.status(status).body(uploadResponse);
//        //return ResponseEntity.status(status).body(responseMessage);
//    }


//    @PostMapping("/upload1")
//    public ResponseEntity<String> uploadExcel1(@RequestParam("file") MultipartFile file) {
//        try {
//            Boolean response = this.excelService.saveExcelDataCorrect(file);
//            String responseMessage = "";
//
//            if (response) {
//                responseMessage = "Excel file uploaded and data successfully added/updated into the database !!";
//            } else {
//                responseMessage = "Something went wrong please contact the developer.";
//            }
//
//            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
//        } catch (javax.persistence.NonUniqueResultException e) {
//            String errorMessage = "Error: Multiple rows found with the same data in the database. " + getColumnNameAndValueFromException(e);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
//        } catch (Exception e) {
//            String responseMessage = "Could not upload the file: " + file.getOriginalFilename() + "!";
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseMessage);
//        }
//    }


    private String getColumnNameAndValueFromException(javax.persistence.NonUniqueResultException e) {
        String message = e.getMessage();
        int startIndex = message.indexOf("Column") + 7;
        int endIndex = message.indexOf("is not unique");
        String columnName = message.substring(startIndex, endIndex).trim();

        int valueStartIndex = message.indexOf("Detail: ") + 8;
        int valueEndIndex = message.indexOf("\n", valueStartIndex);
        String columnValue = message.substring(valueStartIndex, valueEndIndex).trim();

        return "Duplicate data in column '" + columnName + "' with value '" + columnValue + "'.";
    }


}

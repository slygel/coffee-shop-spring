package com.project.coffeeshop.controller;

import com.itextpdf.text.DocumentException;
import com.project.coffeeshop.exception.CatchException;
import com.project.coffeeshop.model.BillModel;
import com.project.coffeeshop.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BillController {

    @Autowired
    private BillService billService;

    @GetMapping("/bills")
    public ResponseEntity<List<BillModel>> getAllBillOfUser(){
        List<BillModel> billModels = billService.getBillOfUser();

        return new ResponseEntity<>(billModels, HttpStatus.OK);
    }

    @GetMapping("/bills/on-going")
    public ResponseEntity<List<BillModel>> getAllBillOnGoingOfUser(){
        List<BillModel> billModels = billService.getBillOnGoingOfUser();

        return new ResponseEntity<>(billModels,HttpStatus.OK);
    }

    @GetMapping("/bills/complete")
    public ResponseEntity<List<BillModel>> getAllBillCompleteOfUser(){
        List<BillModel> billModels = billService.getBillCompleteOfUser();
        return new ResponseEntity<>(billModels,HttpStatus.OK);
    }

    @GetMapping("/bills/{billId}/generate-pdf")
    public ResponseEntity<byte[]> getBillPdf(@PathVariable("billId") Long billId) {
        BillModel billModel = billService.getBillById(billId);
        if (billModel != null) {
            try {
                byte[] pdfContent = billService.generateBillPdf(billModel);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("attachment", "bill_" + billId + ".pdf");

                return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
            } catch (IOException | DocumentException e) {
                // Handle exceptions if necessary
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{billId}/generate-pdf")
    public ResponseEntity<String> postBillPdf(@PathVariable("billId") Long billId) {
        try {
            BillModel billModel = billService.getBillById(billId);
            if (billModel != null) {
                byte[] pdfContent = billService.generateBillPdf(billModel);

                // Define the file path on the D drive
                String filePath = "D://bill_" + billId + ".pdf";
                File file = new File(filePath);

                // Write the PDF content to the file
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(pdfContent);
                }

                return ResponseEntity.ok("Bill PDF generated and saved to " + filePath);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException | DocumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating PDF: " + e.getMessage());
        } catch (CatchException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

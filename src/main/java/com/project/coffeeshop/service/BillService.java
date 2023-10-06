package com.project.coffeeshop.service;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.project.coffeeshop.entity.Bill;
import com.project.coffeeshop.entity.User;
import com.project.coffeeshop.exception.CatchException;
import com.project.coffeeshop.model.BillModel;
import com.project.coffeeshop.model.ItemModel;
import com.project.coffeeshop.repo.BillRepository;
import com.project.coffeeshop.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private UserRepository userRepository;

    public List<BillModel> getBillOfUser(){
        List<BillModel> billModels = new ArrayList<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUserName = authentication.getName();
            User user = userRepository.getUserByUsername(currentUserName).get(0);
            List<Bill> bills = billRepository.findByUser(user);

            for (Bill bill : bills){
                billModels.add(new BillModel(bill));
            }
        }
        return billModels;
    }

    public List<BillModel> getBillByAdmin(){
        List<BillModel> billModels = new ArrayList<>();
        List<Bill> bills = billRepository.findAll();
        for (Bill bill : bills){
            billModels.add(new BillModel(bill));
        }
        return billModels;
    }

    public List<BillModel> getBillOnGoingOfUser(){
        List<BillModel> billModels = new ArrayList<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUserName = authentication.getName();

            List<Bill> bills = billRepository.getAllBillOnGoing(currentUserName);
            Set<Bill> uniqueBills = new LinkedHashSet<>(bills);

            List<Bill> sortedBills = new ArrayList<>(uniqueBills);
            Collections.sort(sortedBills, Comparator.comparing(Bill::getId));

            for (Bill bill : sortedBills){
                billModels.add(new BillModel(bill));
            }
        }
        return billModels;
    }

    public List<BillModel> getBillCompleteOfUser(){
        List<BillModel> billModels = new ArrayList<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUserName = authentication.getName();

            List<Bill> bills = billRepository.getAllBillComplete(currentUserName);
            Set<Bill> uniqueBills = new LinkedHashSet<>(bills); // Loại bỏ giá trị bị lặp và giữ nguyên thứ tự

            List<Bill> sortedBills = new ArrayList<>(uniqueBills);
            Collections.sort(sortedBills, Comparator.comparing(Bill::getId));

            for (Bill bill : sortedBills){
                billModels.add(new BillModel(bill));
            }
        }
        return billModels;
    }

    // get Bill
    public byte[] generateBillPdf(BillModel billModel) throws IOException, DocumentException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();
        setRectangleInPdf(document);

        // Define the background color and border color for the table cells
        BaseColor cellBackgroundColor = new BaseColor(240, 240, 240);

        // Add content to the PDF document
        Font header = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, BaseColor.BLACK);
        header.setStyle(Font.BOLD);
        Font info = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, BaseColor.BLACK);
        info.setStyle(Font.BOLD);
        Font data = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, BaseColor.BLACK);
        data.setStyle(Font.BOLD);


        Paragraph title = new Paragraph("Coffee Management System", header);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);

        Paragraph userInfo = new Paragraph();
        userInfo.setFont(info);
        userInfo.add("Bill ID: " + billModel.getId() + "\n");
        userInfo.add("Name: " + billModel.getOrder().getUser().getFullName() + "\n");
        userInfo.add("Email: " + billModel.getOrder().getUser().getEmail() + "\n");
        userInfo.add("Phone: " + billModel.getOrder().getUser().getDeliveryInfos().get(0).getPhoneNumber() + "\n");
        userInfo.add("Payment Method: " + billModel.getOrder().getPaymentModel().getName() + "\n");
        userInfo.setAlignment(Element.ALIGN_LEFT);
        document.add(userInfo);
        document.add(Chunk.NEWLINE);


        // Add bill details to the PDF in a table
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        addTableHeader(table);

        for (ItemModel item : billModel.getOrder().getItems()){
            table.addCell(createCell(item.getProduct().getName(), cellBackgroundColor,data,Element.ALIGN_CENTER));
            table.addCell(createCell(item.getProduct().getCategory().getName(), cellBackgroundColor, data, Element.ALIGN_CENTER));
            table.addCell(createCell(String.valueOf(item.getQuantity()), cellBackgroundColor, data, Element.ALIGN_CENTER));
            table.addCell(createCell(String.valueOf(item.getPriceIn()), cellBackgroundColor, data, Element.ALIGN_CENTER));
            table.addCell(createCell(String.valueOf(item.getPriceIn()* item.getQuantity()), cellBackgroundColor, data, Element.ALIGN_CENTER));
        }
        document.add(table);
        document.add(Chunk.NEWLINE);

        Paragraph footer = new Paragraph("Total : " + billModel.getAmount(),info);
        document.add(footer);

        Paragraph thanks = new Paragraph("Thank you for visiting. Please visit again!!",info);
        document.add(thanks);

        document.close();

        // Return the generated PDF as a byte array
        return outputStream.toByteArray();
    }

    // Helper method to create a PdfPCell with background color and alignment
    private PdfPCell createCell(String content, BaseColor backgroundColor, Font font, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setBackgroundColor(backgroundColor);
        cell.setHorizontalAlignment(alignment);
        cell.setBorderColor(BaseColor.BLACK);
        return cell;
    }

    public void setRectangleInPdf(Document document) throws DocumentException{
        Rectangle rect = new Rectangle(577, 825, 18, 15);
        rect.enableBorderSide(1);
        rect.enableBorderSide(1);
        rect.enableBorderSide(1);
        rect.enableBorderSide(1);
        rect.setBorderColor(BaseColor.BLACK);
        rect.setBorderWidth(1);

        document.add(rect);
    }

    private void addTableHeader(PdfPTable table){
        Stream.of("Name","Category","Quantity","Price","Sub Total")
                .forEach(columnTitle ->{
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    header.setBackgroundColor(BaseColor.YELLOW);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });
    }

    // Authentication
    public BillModel getBillById(Long billId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();

            User user = userRepository.getUserByUsername(currentUsername).get(0);

            Optional<Bill> optionalBill = billRepository.findById(billId);

            if (optionalBill.isPresent()) {
                Bill bill = optionalBill.get();

                // Check if the bill belongs to the current authenticated user before returning the BillModel
                if (bill.getOrder().getUser().getId().equals(user.getId())) {
                    return new BillModel(bill);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    // Get all users
    public BillModel getBillByAdmin(Long billId){
        try{
            Bill bill = billRepository.getById(billId);
            return new BillModel(bill);
        }catch (Exception e){
            String message = "Not founded Bill with id " + billId;
            throw new CatchException(message);
        }
    }
}


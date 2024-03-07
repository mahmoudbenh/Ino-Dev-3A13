/*package models;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExporter {
    public void exportToExcel(List<Debat> debats, String filePath) {
        // Create a new Workbook
        Workbook workbook = new XSSFWorkbook();
        // Create a Sheet
        Sheet sheet = workbook.createSheet("Debats");

        // Create a header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Nom Anime");
        headerRow.createCell(1).setCellValue("Description Debat");
        headerRow.createCell(2).setCellValue("Sujet Debat");

        // Populate data rows
        int rowNum = 1;
        for (Debat debat : debats) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(debat.getNom_Anime());
            row.createCell(1).setCellValue(debat.getDescription_Debat());
            row.createCell(2).setCellValue(debat.getSujet_Debat());
        }

        // Write the workbook to a file
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
            System.out.println("Excel file has been created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close the workbook
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}*/

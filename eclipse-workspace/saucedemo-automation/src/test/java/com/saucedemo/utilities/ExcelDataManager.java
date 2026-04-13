package com.saucedemo.utilities;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDataManager {

    // holds the opened Excel file
    public XSSFWorkbook workbook;

    // holds the specific sheet we want to read
    public XSSFSheet sheet;

    // ===================================================
    // Constructor - opens the Excel file
    // pass full file path to the .xlsx file
    // ===================================================
    public ExcelDataManager(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        workbook = new XSSFWorkbook(fis); // opens the .xlsx file
    }

    // ===================================================
    // getStringData - reads a single cell value
    // sheetName = tab name in Excel
    // row = row number starting from 0
    // col = column number starting from 0
    // ===================================================
    public String getStringData(String sheetName, int row, int col) {
        sheet = workbook.getSheet(sheetName);

        // DataFormatter reads cell as String
        // regardless of whether it's text or number in Excel
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(sheet.getRow(row).getCell(col));
    }

    // ===================================================
    // getRowCount - returns total rows in a sheet
    // useful for looping through all data rows
    // ===================================================
    public int getRowCount(String sheetName) {
        sheet = workbook.getSheet(sheetName);
        return sheet.getLastRowNum(); // returns last row index
    }
}
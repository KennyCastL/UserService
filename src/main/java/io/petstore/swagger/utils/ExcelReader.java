// language: java
package io.petstore.swagger.utils;
//
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
///*
// Utilidad simple para leer una fila de un archivo Excel (.xls / .xlsx).
// Devuelve los valores de las celdas como un arreglo de String.
//*/
//public class ExcelReader {
//
//    public static String[] readRow(String filePath, String sheetName, int rowIndex) throws IOException {
//        File file = new File(filePath);
//        try (FileInputStream fis = new FileInputStream(file);
//             Workbook workbook = WorkbookFactory.create(fis)) {
//
//            Sheet sheet = (sheetName == null || sheetName.isEmpty()) ? workbook.getSheetAt(0) : workbook.getSheet(sheetName);
//            if (sheet == null) {
//                throw new IllegalArgumentException("Hoja no encontrada: " + sheetName);
//            }
//
//            Row row = sheet.getRow(rowIndex);
//            if (row == null) {
//                return new String[0];
//            }
//
//            int lastCell = row.getLastCellNum();
//            List<String> values = new ArrayList<>();
//            for (int i = 0; i < lastCell; i++) {
//                Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
//                values.add(cellToString(cell));
//            }
//            return values.toArray(new String[0]);
//        } catch (InvalidFormatException e) {
//            throw new IOException("Formato de archivo inválido", e);
//        }
//    }
//
//    private static String cellToString(Cell cell) {
//        if (cell == null) return "";
//        switch (cell.getCellType()) {
//            case STRING:
//                return cell.getStringCellValue().trim();
//            case NUMERIC:
//                if (DateUtil.isCellDateFormatted(cell)) {
//                    return cell.getDateCellValue().toString();
//                } else {
//                    double d = cell.getNumericCellValue();
//                    if (d == (long) d) {
//                        return String.valueOf((long) d);
//                    } else {
//                        return String.valueOf(d);
//                    }
//                }
//            case BOOLEAN:
//                return String.valueOf(cell.getBooleanCellValue());
//            case FORMULA:
//                return cellToString(evaluateFormulaCell(cell));
//            case BLANK:
//            case _NONE:
//            case ERROR:
//            default:
//                return "";
//        }
//    }
//
//    private static Cell evaluateFormulaCell(Cell cell) {
//        FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
//        evaluator.evaluateInCell(cell);
//        return cell;
//    }
//}



import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {

    public static List<String[]> read(String path, String sheetName) {
        List<String[]> data = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(path);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // header

                String[] rowData = new String[row.getLastCellNum()];
                for (int i = 0; i < row.getLastCellNum(); i++) {
                    rowData[i] = row.getCell(i).toString();
                }
                data.add(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}

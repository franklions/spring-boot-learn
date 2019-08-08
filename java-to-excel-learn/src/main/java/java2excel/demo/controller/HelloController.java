package java2excel.demo.controller;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author administrator
 * @version 1.0
 * @date 2019-08-07
 * @since Jdk 1.8
 */
@RestController
public class HelloController {

    @GetMapping("/say")
    public String hello(){
        return "hello";
    }

    @GetMapping("/export/excel")
    public String exportExcel(HttpServletResponse response) throws IOException {

        OutputStream output = null;
        try {

            Workbook wb =new HSSFWorkbook();
            CreationHelper creationHelper = wb.getCreationHelper();
            Sheet sheet = wb.createSheet("new sheet");
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue(creationHelper.createRichTextString( "标题"));
            row.createCell(1).setCellValue(creationHelper.createRichTextString("200109202121"));
            row.createCell(2).setCellValue(creationHelper.createRichTextString("副标题"));
            row.createCell(3).setCellValue(creationHelper.createRichTextString("2018-09-22"));
            Row row1 = sheet.createRow(1);
            row1.createCell(0).setCellValue(true);
            row1.createCell(1).setCellValue(1.2);
            output = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=details.xls");
            response.setContentType("application/msexcel");
            wb.write(output);
            output.close();
        } catch (IOException e) {
            if(output != null){
                output.close();
            }
        }
        return null;
    }
}

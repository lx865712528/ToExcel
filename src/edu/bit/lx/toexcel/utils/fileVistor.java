package edu.bit.lx.toexcel.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.json.JSONObject;

import java.io.*;

/**
 * Created by Xiao on 2015/12/16 0016.
 */
public class fileVistor {
    private String inFileDir = null;

    private String outFile = null;

    private HSSFWorkbook workbook = null;

    private HSSFSheet sheet = null;

    final private static int column = 5;

    private int fileCount = 0;

    private int rowCount = 0;

    final private static String[] title = {
        "user_name", // ROOT - user - screen_name
        "user_id",   // ROOT - user - id_str
        "time",      // ROOT - created_at
        "tweet_id",  // ROOT - id_str
        "text"       // ROOT - text
    };

    public fileVistor(String ifd, String of) {
        this.inFileDir = ifd;
        this.outFile = of;
    }

    public void createNewFile() {
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet("sheet1");
        HSSFRow headRow = sheet.createRow(0);
        for (int i = 0; i < column; i++) {
            HSSFCell cell = headRow.createCell(i);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            sheet.setColumnWidth(i, 6000);
            HSSFCellStyle style = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            short color = HSSFColor.RED.index;
            font.setColor(color);
            style.setFont(font);
            cell.setCellStyle(style);
            cell.setCellValue(title[i]);
        }
        rowCount = 0;
    }

    public void writeToFile(String fileName) {
        try {
            FileOutputStream out = new FileOutputStream(fileName);
            workbook.write(out);
            out.flush();
            out.close();
            workbook = null;
            sheet = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void transfer() {
        System.out.println(inFileDir + " to " + outFile);
        File root = new File(inFileDir);
        for (File userTweet : root.listFiles()) {
            String fileName = userTweet.getName();
            if (!fileName.matches("normal_.+"))
                continue;
            System.out.println("    processing " + fileName);
            BufferedReader br = null;
            String[] values = new String[column];
            createNewFile();
            try {
                br = new BufferedReader(
                    new FileReader(new File(userTweet.getAbsolutePath())));
                String str;
                while ((str = br.readLine()) != null) {
                    JSONObject json = new JSONObject(str);
                    //    "user_name"  ROOT - user - screen_name
                    values[0] = json.getJSONObject("user")
                        .getString("screen_name");
                    //    "user_id"    ROOT - user - id_str
                    values[1] = json.getJSONObject("user").getString("id_str");
                    //    "time"       ROOT - created_at
                    values[2] = json.getString("created_at");
                    //    "tweet_id"   ROOT - id_str
                    values[3] = json.getString("id_str");
                    //    "text"       ROOT - text
                    values[4] = json.getString("text");
                    HSSFRow row = sheet.createRow(++rowCount);
                    for (int i = 0; i < column; i++) {
                        row.createCell(i);
                        row.getCell(i).setCellValue(values[i]);
                    }
                }
                writeToFile(
                    outFile + "_" + userTweet.getName().substring(7) + ".xls");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("done!");
    }
}

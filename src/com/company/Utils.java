package com.company;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Apple on 2018/3/18.
 */
public class Utils {
    public final static boolean isNumeric(String s) {
        if (s != null && !"".equals(s.trim()))
            return s.matches("^[0-9]*$");
        else
            return false;
    }

    public static List<PdfPTable> table(List<ColumnModel> list) throws DocumentException, IOException {

        PdfPCell cell1;
        PdfPTable table;
        List<PdfPTable> lists = new ArrayList<PdfPTable>();


        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);


        //绿色字体
        Font greenFont = new Font(bfChinese);
        greenFont.setColor(BaseColor.BLACK);




        for (int i = 0; i < list.size(); i++) {


            table = new PdfPTable(3); // 3 columns.
            table.setWidthPercentage(100); // Width 100%
            table.setSpacingBefore(10f); // Space before table
            table.setSpacingAfter(10f); // Space after table

            // Set Column widths
            float[] columnWidths = {1f, 1f, 1f};
            table.setWidths(columnWidths);


            for (int j = 0; j < 3; j++) {

                if (j == 0) {
                    cell1 = new PdfPCell(new Paragraph(list.get(i).column1, greenFont));
                } else if (j == 1) {
                    cell1 = new PdfPCell(new Paragraph(list.get(i).column2, greenFont));
                } else {
                    cell1 = new PdfPCell(new Paragraph(list.get(i).column3, greenFont));
                }


                cell1.setBorderColor(BaseColor.WHITE);
                cell1.setPaddingLeft(10);
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell1);
            }
            lists.add(table);

        }


        return lists;


    }


    public static List<List<ColumnModel>> TXTtoEXCEl1(String txtfilePath) {
        ColumnModel model;
        BufferedReader br = null;

        List<List<ColumnModel>> lists = new ArrayList<List<ColumnModel>>();

        List<ColumnModel> lists1FinalData = new ArrayList();

        List<ColumnModel> list = new ArrayList<ColumnModel>();

        List<ColumnModel> list2 = new ArrayList<ColumnModel>();

        List<ColumnModel> list5 = new ArrayList<ColumnModel>();

        List<ColumnModel> list6 = new ArrayList<ColumnModel>();


        list.clear();

        String line = "";
        String[] split;
        try {
            br = new BufferedReader(new FileReader(txtfilePath));

            while ((line = br.readLine()) != null) {

                //System.out.println(line);

                if ((line.split("\\s+").length == 3 && isInteger(line.split("\\s+")[0]))) {

                    //System.out.println(line);
                    model = new ColumnModel(line.split("\\s+")[0], line.split("\\s+")[1], line.split("\\s+")[2]);
                    list.add(model);
                    continue;
                }


                if ((line.split("\\s+").length == 4 && isInteger(line.split("\\s+")[0])) && isDouble(line.split("\\s+")[3])) {
                    model = new ColumnModel(line.split("\\s+")[0], line.split("\\s+")[1] + line.split("\\s+")[2], line.split("\\s+")[3]);
                    list.add(model);
                    continue;
                }

                if ((line.split("\\s+").length == 4 && isInteger(line.split("\\s+")[1]))) {
                    model = new ColumnModel(line.split("\\s+")[1], line.split("\\s+")[2], line.split("\\s+")[3]);
                    list.add(model);
                    continue;
                }

                split = line.trim().split("\\s+");

                if ((split.length > 3 && isInteger(line.split("\\s+")[0])) && isDouble(line.split("\\s+")[split.length - 1])) {
                    model = new ColumnModel(line.split("\\s+")[0], "@@@@@@", line.split("\\s+")[split.length - 1]);
                    list.add(model);
                    continue;
                }


                if (split.length == 2 && isInteger(line.trim().split("\\s+")[0]) && !isInteger(line.trim().split("\\s+")[1])) {
                    model = new ColumnModel(line.split("\\s+")[0], line.split("\\s+")[1], "@@@@@");
                    list.add(model);
                    continue;
                }


                /**
                 * 表2的生成规则
                 */


                if (line.trim().endsWith("一般") || line.trim().endsWith("关注") || line.trim().endsWith("强烈")) {

                    String[] splitArray = line.trim().split("\\s+");


                    if (splitArray.length > 4) {
                        String value1 = "", value2, value3, value4 = "";


                        if (splitArray[splitArray.length - 2].equals("↑")) {
                            value4 = "↑";
                        } else {
                            value4 = "";
                        }


                        if (!value4.isEmpty()) {
                            for (int i = 0; i < splitArray.length - 4; i++) {
                                value1 += splitArray[i];
                            }

                            value2 = splitArray[splitArray.length - 4];
                            value3 = splitArray[splitArray.length - 3];
                        } else {
                            for (int i = 0; i < splitArray.length - 3; i++) {
                                value1 += splitArray[i];
                            }
                            value2 = splitArray[splitArray.length - 3];
                            value3 = splitArray[splitArray.length - 2];
                        }


                        String value5 = splitArray[splitArray.length - 1];

//                        if (isNumeric(value2)&&isNumeric(value3)){
//
//                        }

                        System.out.println(line);

                        if (splitArray[splitArray.length - 1].trim().equals("一般") ||
                                splitArray[splitArray.length - 1].trim().equals("关注") ||
                                splitArray[splitArray.length - 1].trim().equals("强烈")) {
                            model = new ColumnModel(value1, value2, value3, value4, value5);
                            list2.add(model);

                        }


                    } else if (splitArray.length == 4) {
                        model = new ColumnModel(line.split("\\s+")[0], line.split("\\s+")[1], line.split("\\s+")[2], ""
                                , line.split("\\s+")[3]);
                        list2.add(model);
                    }


                    continue;


                }


                /**
                 * 表3的生成规则
                 */


                if ((line.trim().endsWith("正常") || line.trim().endsWith("风险"))
                        && line.split("\\s+").length == 5) {
                    //System.out.println(line);
                    model = new ColumnModel(line.split("\\s+")[0], line.split("\\s+")[1], line.split("\\s+")[2], line.split("\\s+")[3]
                            , line.split("\\s+")[4]);
                    list5.add(model);
                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        for (int i = 0; i < list.size(); i++) {
            if (Utils.isDouble(list.get(i).column3)&&list.get(i).column3.contains(".")) {

                if (Double.parseDouble(list.get(i).column3) >= 1f) {
                    lists1FinalData.add(new ColumnModel(list.get(i).column2, list.get(i).column3, "高风险"));
                } else if (Double.parseDouble(list.get(i).column3) >= 0.5f && Double.parseDouble(list.get(i).column3) < 1f) {
                    lists1FinalData.add(new ColumnModel(list.get(i).column2, list.get(i).column3, "中风险"));
                }
            }
        }


        lists.add(lists1FinalData);
        lists.add(list);


        lists.add(list2);
        lists.add(list5);

        for (int i = 0; i < list5.size(); i++) {
            if (list5.get(i).column1.equals("NQO1") && list5.get(i).column3.equals("CT")) {
                model = new ColumnModel(list5.get(i).column1, list5.get(i).column2, list5.get(i).column3, "高风险", "1.避免住在加加油站附近\n" +
                        "2.避免染发\n" +
                        "3.避免住进刚装修的房子");
                list6.add(model);
            }
        }


        lists.add(list6);


        return lists;

    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    private static boolean isDouble(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }


    public static void readPdfToTxt(String pdfPath, String txtfilePath) {

        // 读取pdf所使用的输出流

        PrintWriter writer = null;

        PdfReader reader = null;

        try {

            writer = new PrintWriter(new FileOutputStream(txtfilePath));

            reader = new PdfReader(pdfPath);

            int num = reader.getNumberOfPages();// 获得页数

            // System.out.println("Total Page: " + num);

            StringBuilder content = new StringBuilder(); // 存放读取出的文档内容

            for (int i = 11; i <= num; i++) {

                // 读取第i页的文档内容


                content.append(PdfTextExtractor.getTextFromPage(reader, i));
                System.out.println(PdfTextExtractor.getTextFromPage(reader, i));
            }


            writer.write(content.toString());// 写入文件内容

            writer.flush();

            writer.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public static void initExcel(List<List<ColumnModel>> lists) {
        HSSFWorkbook workbook = new HSSFWorkbook();

        String [] value = {"只包含了高风险低风险的表","全部风险的表","带箭头的表","基因、基因型表","根据规则删选的 基因、基因型表"};

        for (int i = 0; i < lists.size(); i++) {
            //第一步，创建一个workbook对应一个excel文件

            //第二部，在workbook中创建一个sheet对应excel中的sheet
            HSSFSheet sheet = workbook.createSheet(value[i]);
            //第三部，在sheet表中添加表头第0行，老版本的poi对sheet的行列有限制
            HSSFRow row = sheet.createRow(0);
            //第四步，创建单元格，设置表头
            HSSFCell cell = row.createCell(0);
            cell.setCellValue("c1");
            cell = row.createCell(1);
            cell.setCellValue("c2");
            cell = row.createCell(2);
            cell.setCellValue("c3");

            cell = row.createCell(3);
            cell.setCellValue("c4");
            cell = row.createCell(4);
            cell.setCellValue("c5");


            for (int i1 = 0; i1 < lists.get(i).size(); i1++) {
                HSSFRow row1 = sheet.createRow(i1 + 1);
                row1.createCell(0).setCellValue(lists.get(i).get(i1).column1);
                row1.createCell(1).setCellValue(lists.get(i).get(i1).column2);
                row1.createCell(2).setCellValue(lists.get(i).get(i1).column3);
                row1.createCell(3).setCellValue(lists.get(i).get(i1).column4);
                row1.createCell(4).setCellValue(lists.get(i).get(i1).column5);
            }


        }


        //将文件保存到指定的位置
        try {
            FileOutputStream fos = new FileOutputStream(new File("excel.xls"));
            workbook.write(fos);
            System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void writeToPdf(List<List<ColumnModel>> listAllData) {
        //创建文件
        Document document = new Document();
        //建立一个书写器
        PdfWriter writer = null;
        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream("/Users/Apple/Downloads/南方基因报告模板—new.pdf"));
            //打开文件
            document.open();

            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);


            //绿色字体
            Font greenFont = new Font(bfChinese);
            greenFont.setColor(BaseColor.BLACK);

            //创建章节
            Paragraph chapterTitle = new Paragraph("检测结论及检验", greenFont);
            chapterTitle.setAlignment(1);


            Paragraph sectionContent = new Paragraph("检测结果", greenFont);


            /**
             * 检索表用户表0 只关心 高风险 和 中等风险
             */
            List<ColumnModel> list1 = new ArrayList<ColumnModel>();


            for (int i = 0; i < listAllData.get(0).size(); i++) {
                list1.add(new ColumnModel(listAllData.get(0).get(i).column1, listAllData.get(0).get(i).column2, listAllData.get(0).get(i).column3));
            }


            List<PdfPTable> tables1 = Utils.table(list1);


            Paragraph sectionContent2 = new Paragraph("相关风险基因为", greenFont);

            Paragraph sectionContent3 = new Paragraph("相关结果方案", greenFont);

            List<ColumnModel> list2 = new ArrayList<ColumnModel>();
            list2.add(new ColumnModel("1", "2", "3"));
            list2.add(new ColumnModel("1", "5", "5"));

            List<PdfPTable> tables2 = Utils.table(list2);


            //将章节添加到文章中
            document.add(chapterTitle);
            document.add(sectionContent);
            for (int i = 0; i < tables1.size(); i++) {
                document.add(tables1.get(i));
            }

            document.add(sectionContent2);

            for (int i = 0; i < tables2.size(); i++) {
                document.add(tables2.get(i));
            }

            document.add(sectionContent3);

            //关闭文档
            document.close();
            //关闭书写器
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

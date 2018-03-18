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

public class Main {


    public static void main(String[] args) {


        String pdfPath = "/Users/Apple/Downloads/南方基因报告模板.pdf";

        String txtfilePath = "/Users/Apple/Downloads/南方基因报告模板-itext.txt";


        Utils.readPdfToTxt(pdfPath, txtfilePath);

        List<List<ColumnModel>> listAllData = Utils.TXTtoEXCEl1(txtfilePath);

        Utils.initExcel(listAllData);

        Utils.writeToPdf(listAllData);

        System.out.println("Finished !");


    }


}



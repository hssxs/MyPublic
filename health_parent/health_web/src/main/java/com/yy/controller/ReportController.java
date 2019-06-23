package com.yy.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.yy.constant.MessageConstant;
import com.yy.entity.Result;
import com.yy.service.MemberService;
import com.yy.service.ReportService;
import com.yy.service.SetmealService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ：YY
 * @date ：Created in 2019/6/22
 * @description ：
 * @version: 1.0
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    MemberService memberService;
    @Reference
    SetmealService setmealService;
    @Reference
    ReportService reportService;
    @RequestMapping("/getMemberReport")
    public Result getMemberReport() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -12);
        List<String> months = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            String month = sdf.format(calendar.getTime());
            months.add(month);
            calendar.add(Calendar.MONTH, +1);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("months", months);
       List<Integer> memberCount = memberService.findByMemberBeforeMonth(months);
        map.put("memberCount", memberCount);
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
     }

     @RequestMapping("/getSetmealReport")
     public Result getSetmealReport() {
        //setmealNames ['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
         // setmealCount data:[value:135, name:'视频广告'},value:1548, name:'搜索引擎'}],
         List<Map<String, String>> setmealCount = setmealService.findsetmealCount();
         List<String> setmealNames = new ArrayList<>();
         for (Map<String, String> stringStringMap : setmealCount) {
             String setmealName = stringStringMap.get("name");
             setmealNames.add(setmealName);
         }
         Map<String, Object> map = new HashMap<>();
         map.put("setmealCount", setmealCount);
         map.put("setmealNames", setmealNames);
         return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, map);
     }

     @RequestMapping("/getBusinessReportData")
     public Result getBusinessReportData() {
         try {
             Map<String, Object> map = reportService.getBusinessReportData();
             return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, map);
         } catch (Exception e) {
             e.printStackTrace();
             return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
         }
     }

    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> reportData = reportService.getBusinessReportData();
            String reportDate = (String) reportData.get("reportDate");
            Integer todayNewMember = (Integer) reportData.get("todayNewMember");
            Integer totalMember = (Integer) reportData.get("totalMember");
            Integer thisWeekNewMember = (Integer) reportData.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) reportData.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) reportData.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) reportData.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) reportData.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) reportData.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) reportData.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) reportData.get("thisMonthVisitsNumber");
            List<Map> hotSetmeal = (List<Map>) reportData.get("hotSetmeal");

            String templateRealPath = request.getSession().getServletContext().getRealPath("/template") + File.separator + "report_template.xlsx";
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(templateRealPath)));
            XSSFSheet sheet1 = workbook.getSheet("sheet1");
            XSSFRow row = sheet1.getRow(2);
            row.getCell(5).setCellValue(reportDate);//日期

            row = sheet1.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);//新增会员数（本日）
            row.getCell(7).setCellValue(totalMember);//总会员数

            row = sheet1.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
            row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数

            row = sheet1.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
            row.getCell(7).setCellValue(todayVisitsNumber);//今日到诊数

            row = sheet1.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周到诊数

            row = sheet1.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月到诊数

            int rowNum = 12;
            for (Map map : hotSetmeal) {
                String name = String.valueOf(map.get("name"));
                String setmeal_count = String.valueOf(map.get("setmeal_count"));
                String proportion = String.valueOf(map.get("proportion"));

                XSSFRow cells = sheet1.getRow(rowNum++);
                cells.getCell(4).setCellValue(name);
                cells.getCell(5).setCellValue(setmeal_count);
                cells.getCell(6).setCellValue(proportion);
            }

            //通过输出流进行文件下载
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
            workbook.write(out);
            out.flush();
            out.close();
            workbook.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL,null);
        }
    }
}

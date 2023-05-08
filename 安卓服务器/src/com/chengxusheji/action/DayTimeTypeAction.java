package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.DayTimeTypeDAO;
import com.chengxusheji.domain.DayTimeType;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class DayTimeTypeAction extends BaseAction {

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int dayTimeTypeId;
    public void setDayTimeTypeId(int dayTimeTypeId) {
        this.dayTimeTypeId = dayTimeTypeId;
    }
    public int getDayTimeTypeId() {
        return dayTimeTypeId;
    }

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource DayTimeTypeDAO dayTimeTypeDAO;

    /*待操作的DayTimeType对象*/
    private DayTimeType dayTimeType;
    public void setDayTimeType(DayTimeType dayTimeType) {
        this.dayTimeType = dayTimeType;
    }
    public DayTimeType getDayTimeType() {
        return this.dayTimeType;
    }

    /*跳转到添加DayTimeType视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加DayTimeType信息*/
    @SuppressWarnings("deprecation")
    public String AddDayTimeType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            dayTimeTypeDAO.AddDayTimeType(dayTimeType);
            ctx.put("message",  java.net.URLEncoder.encode("DayTimeType添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("DayTimeType添加失败!"));
            return "error";
        }
    }

    /*查询DayTimeType信息*/
    public String QueryDayTimeType() {
        if(currentPage == 0) currentPage = 1;
        List<DayTimeType> dayTimeTypeList = dayTimeTypeDAO.QueryDayTimeTypeInfo(currentPage);
        /*计算总的页数和总的记录数*/
        dayTimeTypeDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = dayTimeTypeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = dayTimeTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("dayTimeTypeList",  dayTimeTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryDayTimeTypeOutputToExcel() { 
        List<DayTimeType> dayTimeTypeList = dayTimeTypeDAO.QueryDayTimeTypeInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "DayTimeType信息记录"; 
        String[] headers = { "记录编号","时间段名称"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<dayTimeTypeList.size();i++) {
        	DayTimeType dayTimeType = dayTimeTypeList.get(i); 
        	dataset.add(new String[]{dayTimeType.getDayTimeTypeId() + "",dayTimeType.getDayTimeTypeName()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"DayTimeType.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*前台查询DayTimeType信息*/
    public String FrontQueryDayTimeType() {
        if(currentPage == 0) currentPage = 1;
        List<DayTimeType> dayTimeTypeList = dayTimeTypeDAO.QueryDayTimeTypeInfo(currentPage);
        /*计算总的页数和总的记录数*/
        dayTimeTypeDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = dayTimeTypeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = dayTimeTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("dayTimeTypeList",  dayTimeTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的DayTimeType信息*/
    public String ModifyDayTimeTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键dayTimeTypeId获取DayTimeType对象*/
        DayTimeType dayTimeType = dayTimeTypeDAO.GetDayTimeTypeByDayTimeTypeId(dayTimeTypeId);

        ctx.put("dayTimeType",  dayTimeType);
        return "modify_view";
    }

    /*查询要修改的DayTimeType信息*/
    public String FrontShowDayTimeTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键dayTimeTypeId获取DayTimeType对象*/
        DayTimeType dayTimeType = dayTimeTypeDAO.GetDayTimeTypeByDayTimeTypeId(dayTimeTypeId);

        ctx.put("dayTimeType",  dayTimeType);
        return "front_show_view";
    }

    /*更新修改DayTimeType信息*/
    public String ModifyDayTimeType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            dayTimeTypeDAO.UpdateDayTimeType(dayTimeType);
            ctx.put("message",  java.net.URLEncoder.encode("DayTimeType信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("DayTimeType信息更新失败!"));
            return "error";
       }
   }

    /*删除DayTimeType信息*/
    public String DeleteDayTimeType() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            dayTimeTypeDAO.DeleteDayTimeType(dayTimeTypeId);
            ctx.put("message",  java.net.URLEncoder.encode("DayTimeType删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("DayTimeType删除失败!"));
            return "error";
        }
    }

}

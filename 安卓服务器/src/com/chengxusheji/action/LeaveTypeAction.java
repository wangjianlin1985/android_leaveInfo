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
import com.chengxusheji.dao.LeaveTypeDAO;
import com.chengxusheji.domain.LeaveType;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class LeaveTypeAction extends BaseAction {

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

    private int leaveTypeId;
    public void setLeaveTypeId(int leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }
    public int getLeaveTypeId() {
        return leaveTypeId;
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
    @Resource LeaveTypeDAO leaveTypeDAO;

    /*待操作的LeaveType对象*/
    private LeaveType leaveType;
    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }
    public LeaveType getLeaveType() {
        return this.leaveType;
    }

    /*跳转到添加LeaveType视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加LeaveType信息*/
    @SuppressWarnings("deprecation")
    public String AddLeaveType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            leaveTypeDAO.AddLeaveType(leaveType);
            ctx.put("message",  java.net.URLEncoder.encode("LeaveType添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LeaveType添加失败!"));
            return "error";
        }
    }

    /*查询LeaveType信息*/
    public String QueryLeaveType() {
        if(currentPage == 0) currentPage = 1;
        List<LeaveType> leaveTypeList = leaveTypeDAO.QueryLeaveTypeInfo(currentPage);
        /*计算总的页数和总的记录数*/
        leaveTypeDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = leaveTypeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = leaveTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("leaveTypeList",  leaveTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryLeaveTypeOutputToExcel() { 
        List<LeaveType> leaveTypeList = leaveTypeDAO.QueryLeaveTypeInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "LeaveType信息记录"; 
        String[] headers = { "请假类型ID","请假类型名称"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<leaveTypeList.size();i++) {
        	LeaveType leaveType = leaveTypeList.get(i); 
        	dataset.add(new String[]{leaveType.getLeaveTypeId() + "",leaveType.getLeaveTypeName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"LeaveType.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询LeaveType信息*/
    public String FrontQueryLeaveType() {
        if(currentPage == 0) currentPage = 1;
        List<LeaveType> leaveTypeList = leaveTypeDAO.QueryLeaveTypeInfo(currentPage);
        /*计算总的页数和总的记录数*/
        leaveTypeDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = leaveTypeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = leaveTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("leaveTypeList",  leaveTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的LeaveType信息*/
    public String ModifyLeaveTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键leaveTypeId获取LeaveType对象*/
        LeaveType leaveType = leaveTypeDAO.GetLeaveTypeByLeaveTypeId(leaveTypeId);

        ctx.put("leaveType",  leaveType);
        return "modify_view";
    }

    /*查询要修改的LeaveType信息*/
    public String FrontShowLeaveTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键leaveTypeId获取LeaveType对象*/
        LeaveType leaveType = leaveTypeDAO.GetLeaveTypeByLeaveTypeId(leaveTypeId);

        ctx.put("leaveType",  leaveType);
        return "front_show_view";
    }

    /*更新修改LeaveType信息*/
    public String ModifyLeaveType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            leaveTypeDAO.UpdateLeaveType(leaveType);
            ctx.put("message",  java.net.URLEncoder.encode("LeaveType信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LeaveType信息更新失败!"));
            return "error";
       }
   }

    /*删除LeaveType信息*/
    public String DeleteLeaveType() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            leaveTypeDAO.DeleteLeaveType(leaveTypeId);
            ctx.put("message",  java.net.URLEncoder.encode("LeaveType删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LeaveType删除失败!"));
            return "error";
        }
    }

}

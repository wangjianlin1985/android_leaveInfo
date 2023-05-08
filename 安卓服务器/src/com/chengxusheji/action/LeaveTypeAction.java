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

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
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

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource LeaveTypeDAO leaveTypeDAO;

    /*��������LeaveType����*/
    private LeaveType leaveType;
    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }
    public LeaveType getLeaveType() {
        return this.leaveType;
    }

    /*��ת�����LeaveType��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���LeaveType��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddLeaveType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            leaveTypeDAO.AddLeaveType(leaveType);
            ctx.put("message",  java.net.URLEncoder.encode("LeaveType��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LeaveType���ʧ��!"));
            return "error";
        }
    }

    /*��ѯLeaveType��Ϣ*/
    public String QueryLeaveType() {
        if(currentPage == 0) currentPage = 1;
        List<LeaveType> leaveTypeList = leaveTypeDAO.QueryLeaveTypeInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        leaveTypeDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = leaveTypeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = leaveTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("leaveTypeList",  leaveTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryLeaveTypeOutputToExcel() { 
        List<LeaveType> leaveTypeList = leaveTypeDAO.QueryLeaveTypeInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "LeaveType��Ϣ��¼"; 
        String[] headers = { "�������ID","�����������"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"LeaveType.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
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
    /*ǰ̨��ѯLeaveType��Ϣ*/
    public String FrontQueryLeaveType() {
        if(currentPage == 0) currentPage = 1;
        List<LeaveType> leaveTypeList = leaveTypeDAO.QueryLeaveTypeInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        leaveTypeDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = leaveTypeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = leaveTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("leaveTypeList",  leaveTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�LeaveType��Ϣ*/
    public String ModifyLeaveTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������leaveTypeId��ȡLeaveType����*/
        LeaveType leaveType = leaveTypeDAO.GetLeaveTypeByLeaveTypeId(leaveTypeId);

        ctx.put("leaveType",  leaveType);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�LeaveType��Ϣ*/
    public String FrontShowLeaveTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������leaveTypeId��ȡLeaveType����*/
        LeaveType leaveType = leaveTypeDAO.GetLeaveTypeByLeaveTypeId(leaveTypeId);

        ctx.put("leaveType",  leaveType);
        return "front_show_view";
    }

    /*�����޸�LeaveType��Ϣ*/
    public String ModifyLeaveType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            leaveTypeDAO.UpdateLeaveType(leaveType);
            ctx.put("message",  java.net.URLEncoder.encode("LeaveType��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LeaveType��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��LeaveType��Ϣ*/
    public String DeleteLeaveType() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            leaveTypeDAO.DeleteLeaveType(leaveTypeId);
            ctx.put("message",  java.net.URLEncoder.encode("LeaveTypeɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LeaveTypeɾ��ʧ��!"));
            return "error";
        }
    }

}

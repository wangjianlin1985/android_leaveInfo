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

    private int dayTimeTypeId;
    public void setDayTimeTypeId(int dayTimeTypeId) {
        this.dayTimeTypeId = dayTimeTypeId;
    }
    public int getDayTimeTypeId() {
        return dayTimeTypeId;
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
    @Resource DayTimeTypeDAO dayTimeTypeDAO;

    /*��������DayTimeType����*/
    private DayTimeType dayTimeType;
    public void setDayTimeType(DayTimeType dayTimeType) {
        this.dayTimeType = dayTimeType;
    }
    public DayTimeType getDayTimeType() {
        return this.dayTimeType;
    }

    /*��ת�����DayTimeType��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���DayTimeType��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddDayTimeType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            dayTimeTypeDAO.AddDayTimeType(dayTimeType);
            ctx.put("message",  java.net.URLEncoder.encode("DayTimeType��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("DayTimeType���ʧ��!"));
            return "error";
        }
    }

    /*��ѯDayTimeType��Ϣ*/
    public String QueryDayTimeType() {
        if(currentPage == 0) currentPage = 1;
        List<DayTimeType> dayTimeTypeList = dayTimeTypeDAO.QueryDayTimeTypeInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        dayTimeTypeDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = dayTimeTypeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = dayTimeTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("dayTimeTypeList",  dayTimeTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryDayTimeTypeOutputToExcel() { 
        List<DayTimeType> dayTimeTypeList = dayTimeTypeDAO.QueryDayTimeTypeInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "DayTimeType��Ϣ��¼"; 
        String[] headers = { "��¼���","ʱ�������"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"DayTimeType.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯDayTimeType��Ϣ*/
    public String FrontQueryDayTimeType() {
        if(currentPage == 0) currentPage = 1;
        List<DayTimeType> dayTimeTypeList = dayTimeTypeDAO.QueryDayTimeTypeInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        dayTimeTypeDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = dayTimeTypeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = dayTimeTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("dayTimeTypeList",  dayTimeTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�DayTimeType��Ϣ*/
    public String ModifyDayTimeTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������dayTimeTypeId��ȡDayTimeType����*/
        DayTimeType dayTimeType = dayTimeTypeDAO.GetDayTimeTypeByDayTimeTypeId(dayTimeTypeId);

        ctx.put("dayTimeType",  dayTimeType);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�DayTimeType��Ϣ*/
    public String FrontShowDayTimeTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������dayTimeTypeId��ȡDayTimeType����*/
        DayTimeType dayTimeType = dayTimeTypeDAO.GetDayTimeTypeByDayTimeTypeId(dayTimeTypeId);

        ctx.put("dayTimeType",  dayTimeType);
        return "front_show_view";
    }

    /*�����޸�DayTimeType��Ϣ*/
    public String ModifyDayTimeType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            dayTimeTypeDAO.UpdateDayTimeType(dayTimeType);
            ctx.put("message",  java.net.URLEncoder.encode("DayTimeType��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("DayTimeType��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��DayTimeType��Ϣ*/
    public String DeleteDayTimeType() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            dayTimeTypeDAO.DeleteDayTimeType(dayTimeTypeId);
            ctx.put("message",  java.net.URLEncoder.encode("DayTimeTypeɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("DayTimeTypeɾ��ʧ��!"));
            return "error";
        }
    }

}

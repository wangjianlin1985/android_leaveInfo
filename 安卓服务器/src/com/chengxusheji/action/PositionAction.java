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
import com.chengxusheji.dao.PositionDAO;
import com.chengxusheji.domain.Position;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class PositionAction extends BaseAction {

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

    private int positionId;
    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }
    public int getPositionId() {
        return positionId;
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
    @Resource PositionDAO positionDAO;

    /*��������Position����*/
    private Position position;
    public void setPosition(Position position) {
        this.position = position;
    }
    public Position getPosition() {
        return this.position;
    }

    /*��ת�����Position��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���Position��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddPosition() {
        ActionContext ctx = ActionContext.getContext();
        try {
            positionDAO.AddPosition(position);
            ctx.put("message",  java.net.URLEncoder.encode("Position��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Position���ʧ��!"));
            return "error";
        }
    }

    /*��ѯPosition��Ϣ*/
    public String QueryPosition() {
        if(currentPage == 0) currentPage = 1;
        List<Position> positionList = positionDAO.QueryPositionInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        positionDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = positionDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = positionDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("positionList",  positionList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryPositionOutputToExcel() { 
        List<Position> positionList = positionDAO.QueryPositionInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Position��Ϣ��¼"; 
        String[] headers = { "ְ��ID","ְ������"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<positionList.size();i++) {
        	Position position = positionList.get(i); 
        	dataset.add(new String[]{position.getPositionId() + "",position.getPositionName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Position.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯPosition��Ϣ*/
    public String FrontQueryPosition() {
        if(currentPage == 0) currentPage = 1;
        List<Position> positionList = positionDAO.QueryPositionInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        positionDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = positionDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = positionDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("positionList",  positionList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Position��Ϣ*/
    public String ModifyPositionQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������positionId��ȡPosition����*/
        Position position = positionDAO.GetPositionByPositionId(positionId);

        ctx.put("position",  position);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Position��Ϣ*/
    public String FrontShowPositionQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������positionId��ȡPosition����*/
        Position position = positionDAO.GetPositionByPositionId(positionId);

        ctx.put("position",  position);
        return "front_show_view";
    }

    /*�����޸�Position��Ϣ*/
    public String ModifyPosition() {
        ActionContext ctx = ActionContext.getContext();
        try {
            positionDAO.UpdatePosition(position);
            ctx.put("message",  java.net.URLEncoder.encode("Position��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Position��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Position��Ϣ*/
    public String DeletePosition() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            positionDAO.DeletePosition(positionId);
            ctx.put("message",  java.net.URLEncoder.encode("Positionɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Positionɾ��ʧ��!"));
            return "error";
        }
    }

}

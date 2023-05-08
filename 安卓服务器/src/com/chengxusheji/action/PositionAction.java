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

    private int positionId;
    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }
    public int getPositionId() {
        return positionId;
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
    @Resource PositionDAO positionDAO;

    /*待操作的Position对象*/
    private Position position;
    public void setPosition(Position position) {
        this.position = position;
    }
    public Position getPosition() {
        return this.position;
    }

    /*跳转到添加Position视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加Position信息*/
    @SuppressWarnings("deprecation")
    public String AddPosition() {
        ActionContext ctx = ActionContext.getContext();
        try {
            positionDAO.AddPosition(position);
            ctx.put("message",  java.net.URLEncoder.encode("Position添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Position添加失败!"));
            return "error";
        }
    }

    /*查询Position信息*/
    public String QueryPosition() {
        if(currentPage == 0) currentPage = 1;
        List<Position> positionList = positionDAO.QueryPositionInfo(currentPage);
        /*计算总的页数和总的记录数*/
        positionDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = positionDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = positionDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("positionList",  positionList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryPositionOutputToExcel() { 
        List<Position> positionList = positionDAO.QueryPositionInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Position信息记录"; 
        String[] headers = { "职级ID","职级名称"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Position.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Position信息*/
    public String FrontQueryPosition() {
        if(currentPage == 0) currentPage = 1;
        List<Position> positionList = positionDAO.QueryPositionInfo(currentPage);
        /*计算总的页数和总的记录数*/
        positionDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = positionDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = positionDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("positionList",  positionList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的Position信息*/
    public String ModifyPositionQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键positionId获取Position对象*/
        Position position = positionDAO.GetPositionByPositionId(positionId);

        ctx.put("position",  position);
        return "modify_view";
    }

    /*查询要修改的Position信息*/
    public String FrontShowPositionQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键positionId获取Position对象*/
        Position position = positionDAO.GetPositionByPositionId(positionId);

        ctx.put("position",  position);
        return "front_show_view";
    }

    /*更新修改Position信息*/
    public String ModifyPosition() {
        ActionContext ctx = ActionContext.getContext();
        try {
            positionDAO.UpdatePosition(position);
            ctx.put("message",  java.net.URLEncoder.encode("Position信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Position信息更新失败!"));
            return "error";
       }
   }

    /*删除Position信息*/
    public String DeletePosition() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            positionDAO.DeletePosition(positionId);
            ctx.put("message",  java.net.URLEncoder.encode("Position删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Position删除失败!"));
            return "error";
        }
    }

}

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
import com.chengxusheji.dao.NoteDAO;
import com.chengxusheji.domain.Note;
import com.chengxusheji.dao.DepartmentDAO;
import com.chengxusheji.domain.Department;
import com.chengxusheji.dao.PositionDAO;
import com.chengxusheji.domain.Position;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class NoteAction extends BaseAction {

    /*界面层需要查询的属性: 节点名称*/
    private String noteName;
    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }
    public String getNoteName() {
        return this.noteName;
    }

    /*界面层需要查询的属性: 处理部门*/
    private Department departmentObj;
    public void setDepartmentObj(Department departmentObj) {
        this.departmentObj = departmentObj;
    }
    public Department getDepartmentObj() {
        return this.departmentObj;
    }

    /*界面层需要查询的属性: 需要职级*/
    private Position positionObj;
    public void setPositionObj(Position positionObj) {
        this.positionObj = positionObj;
    }
    public Position getPositionObj() {
        return this.positionObj;
    }

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

    private int noteId;
    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }
    public int getNoteId() {
        return noteId;
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
    @Resource DepartmentDAO departmentDAO;
    @Resource PositionDAO positionDAO;
    @Resource NoteDAO noteDAO;

    /*待操作的Note对象*/
    private Note note;
    public void setNote(Note note) {
        this.note = note;
    }
    public Note getNote() {
        return this.note;
    }

    /*跳转到添加Note视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Department信息*/
        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        /*查询所有的Position信息*/
        List<Position> positionList = positionDAO.QueryAllPositionInfo();
        ctx.put("positionList", positionList);
        return "add_view";
    }

    /*添加Note信息*/
    @SuppressWarnings("deprecation")
    public String AddNote() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Department departmentObj = departmentDAO.GetDepartmentByDepartmentId(note.getDepartmentObj().getDepartmentId());
            note.setDepartmentObj(departmentObj);
            Position positionObj = positionDAO.GetPositionByPositionId(note.getPositionObj().getPositionId());
            note.setPositionObj(positionObj);
            noteDAO.AddNote(note);
            ctx.put("message",  java.net.URLEncoder.encode("Note添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Note添加失败!"));
            return "error";
        }
    }

    /*查询Note信息*/
    public String QueryNote() {
        if(currentPage == 0) currentPage = 1;
        if(noteName == null) noteName = "";
        List<Note> noteList = noteDAO.QueryNoteInfo(noteName, departmentObj, positionObj, currentPage);
        /*计算总的页数和总的记录数*/
        noteDAO.CalculateTotalPageAndRecordNumber(noteName, departmentObj, positionObj);
        /*获取到总的页码数目*/
        totalPage = noteDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = noteDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("noteList",  noteList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("noteName", noteName);
        ctx.put("departmentObj", departmentObj);
        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        ctx.put("positionObj", positionObj);
        List<Position> positionList = positionDAO.QueryAllPositionInfo();
        ctx.put("positionList", positionList);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryNoteOutputToExcel() { 
        if(noteName == null) noteName = "";
        List<Note> noteList = noteDAO.QueryNoteInfo(noteName,departmentObj,positionObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Note信息记录"; 
        String[] headers = { "节点ID","节点名称","处理部门","需要职级","节点编号"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<noteList.size();i++) {
        	Note note = noteList.get(i); 
        	dataset.add(new String[]{note.getNoteId() + "",note.getNoteName(),note.getDepartmentObj().getDepartmentName(),
note.getPositionObj().getPositionName(),
note.getNoteIndex() + ""});
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
			response.setHeader("Content-disposition","attachment; filename="+"Note.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Note信息*/
    public String FrontQueryNote() {
        if(currentPage == 0) currentPage = 1;
        if(noteName == null) noteName = "";
        List<Note> noteList = noteDAO.QueryNoteInfo(noteName, departmentObj, positionObj, currentPage);
        /*计算总的页数和总的记录数*/
        noteDAO.CalculateTotalPageAndRecordNumber(noteName, departmentObj, positionObj);
        /*获取到总的页码数目*/
        totalPage = noteDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = noteDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("noteList",  noteList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("noteName", noteName);
        ctx.put("departmentObj", departmentObj);
        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        ctx.put("positionObj", positionObj);
        List<Position> positionList = positionDAO.QueryAllPositionInfo();
        ctx.put("positionList", positionList);
        return "front_query_view";
    }

    /*查询要修改的Note信息*/
    public String ModifyNoteQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键noteId获取Note对象*/
        Note note = noteDAO.GetNoteByNoteId(noteId);

        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        List<Position> positionList = positionDAO.QueryAllPositionInfo();
        ctx.put("positionList", positionList);
        ctx.put("note",  note);
        return "modify_view";
    }

    /*查询要修改的Note信息*/
    public String FrontShowNoteQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键noteId获取Note对象*/
        Note note = noteDAO.GetNoteByNoteId(noteId);

        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        List<Position> positionList = positionDAO.QueryAllPositionInfo();
        ctx.put("positionList", positionList);
        ctx.put("note",  note);
        return "front_show_view";
    }

    /*更新修改Note信息*/
    public String ModifyNote() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Department departmentObj = departmentDAO.GetDepartmentByDepartmentId(note.getDepartmentObj().getDepartmentId());
            note.setDepartmentObj(departmentObj);
            Position positionObj = positionDAO.GetPositionByPositionId(note.getPositionObj().getPositionId());
            note.setPositionObj(positionObj);
            noteDAO.UpdateNote(note);
            ctx.put("message",  java.net.URLEncoder.encode("Note信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Note信息更新失败!"));
            return "error";
       }
   }

    /*删除Note信息*/
    public String DeleteNote() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            noteDAO.DeleteNote(noteId);
            ctx.put("message",  java.net.URLEncoder.encode("Note删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Note删除失败!"));
            return "error";
        }
    }

}

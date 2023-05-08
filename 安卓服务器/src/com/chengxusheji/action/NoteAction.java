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

    /*�������Ҫ��ѯ������: �ڵ�����*/
    private String noteName;
    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }
    public String getNoteName() {
        return this.noteName;
    }

    /*�������Ҫ��ѯ������: ������*/
    private Department departmentObj;
    public void setDepartmentObj(Department departmentObj) {
        this.departmentObj = departmentObj;
    }
    public Department getDepartmentObj() {
        return this.departmentObj;
    }

    /*�������Ҫ��ѯ������: ��Ҫְ��*/
    private Position positionObj;
    public void setPositionObj(Position positionObj) {
        this.positionObj = positionObj;
    }
    public Position getPositionObj() {
        return this.positionObj;
    }

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

    private int noteId;
    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }
    public int getNoteId() {
        return noteId;
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
    @Resource DepartmentDAO departmentDAO;
    @Resource PositionDAO positionDAO;
    @Resource NoteDAO noteDAO;

    /*��������Note����*/
    private Note note;
    public void setNote(Note note) {
        this.note = note;
    }
    public Note getNote() {
        return this.note;
    }

    /*��ת�����Note��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Department��Ϣ*/
        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        /*��ѯ���е�Position��Ϣ*/
        List<Position> positionList = positionDAO.QueryAllPositionInfo();
        ctx.put("positionList", positionList);
        return "add_view";
    }

    /*���Note��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddNote() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Department departmentObj = departmentDAO.GetDepartmentByDepartmentId(note.getDepartmentObj().getDepartmentId());
            note.setDepartmentObj(departmentObj);
            Position positionObj = positionDAO.GetPositionByPositionId(note.getPositionObj().getPositionId());
            note.setPositionObj(positionObj);
            noteDAO.AddNote(note);
            ctx.put("message",  java.net.URLEncoder.encode("Note��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Note���ʧ��!"));
            return "error";
        }
    }

    /*��ѯNote��Ϣ*/
    public String QueryNote() {
        if(currentPage == 0) currentPage = 1;
        if(noteName == null) noteName = "";
        List<Note> noteList = noteDAO.QueryNoteInfo(noteName, departmentObj, positionObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        noteDAO.CalculateTotalPageAndRecordNumber(noteName, departmentObj, positionObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = noteDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryNoteOutputToExcel() { 
        if(noteName == null) noteName = "";
        List<Note> noteList = noteDAO.QueryNoteInfo(noteName,departmentObj,positionObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Note��Ϣ��¼"; 
        String[] headers = { "�ڵ�ID","�ڵ�����","������","��Ҫְ��","�ڵ���"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Note.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯNote��Ϣ*/
    public String FrontQueryNote() {
        if(currentPage == 0) currentPage = 1;
        if(noteName == null) noteName = "";
        List<Note> noteList = noteDAO.QueryNoteInfo(noteName, departmentObj, positionObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        noteDAO.CalculateTotalPageAndRecordNumber(noteName, departmentObj, positionObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = noteDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�Note��Ϣ*/
    public String ModifyNoteQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������noteId��ȡNote����*/
        Note note = noteDAO.GetNoteByNoteId(noteId);

        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        List<Position> positionList = positionDAO.QueryAllPositionInfo();
        ctx.put("positionList", positionList);
        ctx.put("note",  note);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Note��Ϣ*/
    public String FrontShowNoteQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������noteId��ȡNote����*/
        Note note = noteDAO.GetNoteByNoteId(noteId);

        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        List<Position> positionList = positionDAO.QueryAllPositionInfo();
        ctx.put("positionList", positionList);
        ctx.put("note",  note);
        return "front_show_view";
    }

    /*�����޸�Note��Ϣ*/
    public String ModifyNote() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Department departmentObj = departmentDAO.GetDepartmentByDepartmentId(note.getDepartmentObj().getDepartmentId());
            note.setDepartmentObj(departmentObj);
            Position positionObj = positionDAO.GetPositionByPositionId(note.getPositionObj().getPositionId());
            note.setPositionObj(positionObj);
            noteDAO.UpdateNote(note);
            ctx.put("message",  java.net.URLEncoder.encode("Note��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Note��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Note��Ϣ*/
    public String DeleteNote() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            noteDAO.DeleteNote(noteId);
            ctx.put("message",  java.net.URLEncoder.encode("Noteɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Noteɾ��ʧ��!"));
            return "error";
        }
    }

}

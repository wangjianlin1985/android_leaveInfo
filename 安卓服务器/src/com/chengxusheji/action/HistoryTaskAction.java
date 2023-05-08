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
import com.chengxusheji.dao.HistoryTaskDAO;
import com.chengxusheji.domain.HistoryTask;
import com.chengxusheji.dao.LeaveInfoDAO;
import com.chengxusheji.domain.LeaveInfo;
import com.chengxusheji.dao.NoteDAO;
import com.chengxusheji.domain.Note;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class HistoryTaskAction extends BaseAction {

    /*�������Ҫ��ѯ������: ��ټ�¼ID*/
    private LeaveInfo leaveObj;
    public void setLeaveObj(LeaveInfo leaveObj) {
        this.leaveObj = leaveObj;
    }
    public LeaveInfo getLeaveObj() {
        return this.leaveObj;
    }

    /*�������Ҫ��ѯ������: �ڵ�*/
    private Note noteObj;
    public void setNoteObj(Note noteObj) {
        this.noteObj = noteObj;
    }
    public Note getNoteObj() {
        return this.noteObj;
    }

    /*�������Ҫ��ѯ������: ������*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*�������Ҫ��ѯ������: ����ʱ��*/
    private String taskTime;
    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }
    public String getTaskTime() {
        return this.taskTime;
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

    private int historyTaskId;
    public void setHistoryTaskId(int historyTaskId) {
        this.historyTaskId = historyTaskId;
    }
    public int getHistoryTaskId() {
        return historyTaskId;
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
    @Resource LeaveInfoDAO leaveInfoDAO;
    @Resource NoteDAO noteDAO;
    @Resource UserInfoDAO userInfoDAO;
    @Resource HistoryTaskDAO historyTaskDAO;

    /*��������HistoryTask����*/
    private HistoryTask historyTask;
    public void setHistoryTask(HistoryTask historyTask) {
        this.historyTask = historyTask;
    }
    public HistoryTask getHistoryTask() {
        return this.historyTask;
    }

    /*��ת�����HistoryTask��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�LeaveInfo��Ϣ*/
        List<LeaveInfo> leaveInfoList = leaveInfoDAO.QueryAllLeaveInfoInfo();
        ctx.put("leaveInfoList", leaveInfoList);
        /*��ѯ���е�Note��Ϣ*/
        List<Note> noteList = noteDAO.QueryAllNoteInfo();
        ctx.put("noteList", noteList);
        /*��ѯ���е�UserInfo��Ϣ*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*���HistoryTask��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddHistoryTask() {
        ActionContext ctx = ActionContext.getContext();
        try {
            LeaveInfo leaveObj = leaveInfoDAO.GetLeaveInfoByLeaveId(historyTask.getLeaveObj().getLeaveId());
            historyTask.setLeaveObj(leaveObj);
            Note noteObj = noteDAO.GetNoteByNoteId(historyTask.getNoteObj().getNoteId());
            historyTask.setNoteObj(noteObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(historyTask.getUserObj().getUser_name());
            historyTask.setUserObj(userObj);
            historyTaskDAO.AddHistoryTask(historyTask);
            ctx.put("message",  java.net.URLEncoder.encode("HistoryTask��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("HistoryTask���ʧ��!"));
            return "error";
        }
    }

    /*��ѯHistoryTask��Ϣ*/
    public String QueryHistoryTask() {
        if(currentPage == 0) currentPage = 1;
        if(taskTime == null) taskTime = "";
        List<HistoryTask> historyTaskList = historyTaskDAO.QueryHistoryTaskInfo(leaveObj, noteObj, userObj, taskTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        historyTaskDAO.CalculateTotalPageAndRecordNumber(leaveObj, noteObj, userObj, taskTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = historyTaskDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = historyTaskDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("historyTaskList",  historyTaskList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("leaveObj", leaveObj);
        List<LeaveInfo> leaveInfoList = leaveInfoDAO.QueryAllLeaveInfoInfo();
        ctx.put("leaveInfoList", leaveInfoList);
        ctx.put("noteObj", noteObj);
        List<Note> noteList = noteDAO.QueryAllNoteInfo();
        ctx.put("noteList", noteList);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("taskTime", taskTime);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryHistoryTaskOutputToExcel() { 
        if(taskTime == null) taskTime = "";
        List<HistoryTask> historyTaskList = historyTaskDAO.QueryHistoryTaskInfo(leaveObj,noteObj,userObj,taskTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "HistoryTask��Ϣ��¼"; 
        String[] headers = { "��ټ�¼ID","�ڵ�","�������","������","����ʱ��","����״̬"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<historyTaskList.size();i++) {
        	HistoryTask historyTask = historyTaskList.get(i); 
        	dataset.add(new String[]{historyTask.getLeaveObj().getLeaveId(),
historyTask.getNoteObj().getNoteName(),
historyTask.getCheckSuggest(),historyTask.getUserObj().getName(),
historyTask.getTaskTime(),historyTask.getCheckState() + ""});
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
			response.setHeader("Content-disposition","attachment; filename="+"HistoryTask.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯHistoryTask��Ϣ*/
    public String FrontQueryHistoryTask() {
        if(currentPage == 0) currentPage = 1;
        if(taskTime == null) taskTime = "";
        List<HistoryTask> historyTaskList = historyTaskDAO.QueryHistoryTaskInfo(leaveObj, noteObj, userObj, taskTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        historyTaskDAO.CalculateTotalPageAndRecordNumber(leaveObj, noteObj, userObj, taskTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = historyTaskDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = historyTaskDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("historyTaskList",  historyTaskList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("leaveObj", leaveObj);
        List<LeaveInfo> leaveInfoList = leaveInfoDAO.QueryAllLeaveInfoInfo();
        ctx.put("leaveInfoList", leaveInfoList);
        ctx.put("noteObj", noteObj);
        List<Note> noteList = noteDAO.QueryAllNoteInfo();
        ctx.put("noteList", noteList);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("taskTime", taskTime);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�HistoryTask��Ϣ*/
    public String ModifyHistoryTaskQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������historyTaskId��ȡHistoryTask����*/
        HistoryTask historyTask = historyTaskDAO.GetHistoryTaskByHistoryTaskId(historyTaskId);

        List<LeaveInfo> leaveInfoList = leaveInfoDAO.QueryAllLeaveInfoInfo();
        ctx.put("leaveInfoList", leaveInfoList);
        List<Note> noteList = noteDAO.QueryAllNoteInfo();
        ctx.put("noteList", noteList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("historyTask",  historyTask);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�HistoryTask��Ϣ*/
    public String FrontShowHistoryTaskQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������historyTaskId��ȡHistoryTask����*/
        HistoryTask historyTask = historyTaskDAO.GetHistoryTaskByHistoryTaskId(historyTaskId);

        List<LeaveInfo> leaveInfoList = leaveInfoDAO.QueryAllLeaveInfoInfo();
        ctx.put("leaveInfoList", leaveInfoList);
        List<Note> noteList = noteDAO.QueryAllNoteInfo();
        ctx.put("noteList", noteList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("historyTask",  historyTask);
        return "front_show_view";
    }

    /*�����޸�HistoryTask��Ϣ*/
    public String ModifyHistoryTask() {
        ActionContext ctx = ActionContext.getContext();
        try {
            LeaveInfo leaveObj = leaveInfoDAO.GetLeaveInfoByLeaveId(historyTask.getLeaveObj().getLeaveId());
            historyTask.setLeaveObj(leaveObj);
            Note noteObj = noteDAO.GetNoteByNoteId(historyTask.getNoteObj().getNoteId());
            historyTask.setNoteObj(noteObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(historyTask.getUserObj().getUser_name());
            historyTask.setUserObj(userObj);
            historyTaskDAO.UpdateHistoryTask(historyTask);
            ctx.put("message",  java.net.URLEncoder.encode("HistoryTask��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("HistoryTask��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��HistoryTask��Ϣ*/
    public String DeleteHistoryTask() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            historyTaskDAO.DeleteHistoryTask(historyTaskId);
            ctx.put("message",  java.net.URLEncoder.encode("HistoryTaskɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("HistoryTaskɾ��ʧ��!"));
            return "error";
        }
    }

}

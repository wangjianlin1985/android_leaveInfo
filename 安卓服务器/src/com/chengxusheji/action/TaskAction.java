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
import com.chengxusheji.dao.TaskDAO;
import com.chengxusheji.domain.Task;
import com.chengxusheji.dao.LeaveInfoDAO;
import com.chengxusheji.domain.LeaveInfo;
import com.chengxusheji.dao.NoteDAO;
import com.chengxusheji.domain.Note;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class TaskAction extends BaseAction {

    /*�������Ҫ��ѯ������: ���ID*/
    private LeaveInfo leaveInfoObj;
    public void setLeaveInfoObj(LeaveInfo leaveInfoObj) {
        this.leaveInfoObj = leaveInfoObj;
    }
    public LeaveInfo getLeaveInfoObj() {
        return this.leaveInfoObj;
    }

    /*�������Ҫ��ѯ������: ��ǰ�ڵ�*/
    private Note noteObj;
    public void setNoteObj(Note noteObj) {
        this.noteObj = noteObj;
    }
    public Note getNoteObj() {
        return this.noteObj;
    }

    /*�������Ҫ��ѯ������: ��ǰ������*/
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

    private int taskId;
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
    public int getTaskId() {
        return taskId;
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
    @Resource TaskDAO taskDAO;

    /*��������Task����*/
    private Task task;
    public void setTask(Task task) {
        this.task = task;
    }
    public Task getTask() {
        return this.task;
    }

    /*��ת�����Task��ͼ*/
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

    /*���Task��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddTask() {
        ActionContext ctx = ActionContext.getContext();
        try {
            LeaveInfo leaveInfoObj = leaveInfoDAO.GetLeaveInfoByLeaveId(task.getLeaveInfoObj().getLeaveId());
            task.setLeaveInfoObj(leaveInfoObj);
            Note noteObj = noteDAO.GetNoteByNoteId(task.getNoteObj().getNoteId());
            task.setNoteObj(noteObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(task.getUserObj().getUser_name());
            task.setUserObj(userObj);
            taskDAO.AddTask(task);
            ctx.put("message",  java.net.URLEncoder.encode("Task��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Task���ʧ��!"));
            return "error";
        }
    }

    /*��ѯTask��Ϣ*/
    public String QueryTask() {
        if(currentPage == 0) currentPage = 1;
        if(taskTime == null) taskTime = "";
        List<Task> taskList = taskDAO.QueryTaskInfo(leaveInfoObj, noteObj, userObj, taskTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        taskDAO.CalculateTotalPageAndRecordNumber(leaveInfoObj, noteObj, userObj, taskTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = taskDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = taskDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("taskList",  taskList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("leaveInfoObj", leaveInfoObj);
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
    public String QueryTaskOutputToExcel() { 
        if(taskTime == null) taskTime = "";
        List<Task> taskList = taskDAO.QueryTaskInfo(leaveInfoObj,noteObj,userObj,taskTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Task��Ϣ��¼"; 
        String[] headers = { "����ID","���ID","��ǰ�ڵ�","��ǰ״̬","��ǰ������","����ʱ��"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<taskList.size();i++) {
        	Task task = taskList.get(i); 
        	dataset.add(new String[]{task.getTaskId() + "",task.getLeaveInfoObj().getLeaveId(),
task.getNoteObj().getNoteName(),
task.getState() + "",task.getUserObj().getName(),
task.getTaskTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Task.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯTask��Ϣ*/
    public String FrontQueryTask() {
        if(currentPage == 0) currentPage = 1;
        if(taskTime == null) taskTime = "";
        List<Task> taskList = taskDAO.QueryTaskInfo(leaveInfoObj, noteObj, userObj, taskTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        taskDAO.CalculateTotalPageAndRecordNumber(leaveInfoObj, noteObj, userObj, taskTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = taskDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = taskDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("taskList",  taskList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("leaveInfoObj", leaveInfoObj);
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

    /*��ѯҪ�޸ĵ�Task��Ϣ*/
    public String ModifyTaskQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������taskId��ȡTask����*/
        Task task = taskDAO.GetTaskByTaskId(taskId);

        List<LeaveInfo> leaveInfoList = leaveInfoDAO.QueryAllLeaveInfoInfo();
        ctx.put("leaveInfoList", leaveInfoList);
        List<Note> noteList = noteDAO.QueryAllNoteInfo();
        ctx.put("noteList", noteList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("task",  task);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Task��Ϣ*/
    public String FrontShowTaskQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������taskId��ȡTask����*/
        Task task = taskDAO.GetTaskByTaskId(taskId);

        List<LeaveInfo> leaveInfoList = leaveInfoDAO.QueryAllLeaveInfoInfo();
        ctx.put("leaveInfoList", leaveInfoList);
        List<Note> noteList = noteDAO.QueryAllNoteInfo();
        ctx.put("noteList", noteList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("task",  task);
        return "front_show_view";
    }

    /*�����޸�Task��Ϣ*/
    public String ModifyTask() {
        ActionContext ctx = ActionContext.getContext();
        try {
            LeaveInfo leaveInfoObj = leaveInfoDAO.GetLeaveInfoByLeaveId(task.getLeaveInfoObj().getLeaveId());
            task.setLeaveInfoObj(leaveInfoObj);
            Note noteObj = noteDAO.GetNoteByNoteId(task.getNoteObj().getNoteId());
            task.setNoteObj(noteObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(task.getUserObj().getUser_name());
            task.setUserObj(userObj);
            taskDAO.UpdateTask(task);
            ctx.put("message",  java.net.URLEncoder.encode("Task��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Task��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Task��Ϣ*/
    public String DeleteTask() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            taskDAO.DeleteTask(taskId);
            ctx.put("message",  java.net.URLEncoder.encode("Taskɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Taskɾ��ʧ��!"));
            return "error";
        }
    }

}

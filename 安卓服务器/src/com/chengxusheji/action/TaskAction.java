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

    /*界面层需要查询的属性: 请假ID*/
    private LeaveInfo leaveInfoObj;
    public void setLeaveInfoObj(LeaveInfo leaveInfoObj) {
        this.leaveInfoObj = leaveInfoObj;
    }
    public LeaveInfo getLeaveInfoObj() {
        return this.leaveInfoObj;
    }

    /*界面层需要查询的属性: 当前节点*/
    private Note noteObj;
    public void setNoteObj(Note noteObj) {
        this.noteObj = noteObj;
    }
    public Note getNoteObj() {
        return this.noteObj;
    }

    /*界面层需要查询的属性: 当前处理人*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*界面层需要查询的属性: 创建时间*/
    private String taskTime;
    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }
    public String getTaskTime() {
        return this.taskTime;
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

    private int taskId;
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
    public int getTaskId() {
        return taskId;
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
    @Resource LeaveInfoDAO leaveInfoDAO;
    @Resource NoteDAO noteDAO;
    @Resource UserInfoDAO userInfoDAO;
    @Resource TaskDAO taskDAO;

    /*待操作的Task对象*/
    private Task task;
    public void setTask(Task task) {
        this.task = task;
    }
    public Task getTask() {
        return this.task;
    }

    /*跳转到添加Task视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的LeaveInfo信息*/
        List<LeaveInfo> leaveInfoList = leaveInfoDAO.QueryAllLeaveInfoInfo();
        ctx.put("leaveInfoList", leaveInfoList);
        /*查询所有的Note信息*/
        List<Note> noteList = noteDAO.QueryAllNoteInfo();
        ctx.put("noteList", noteList);
        /*查询所有的UserInfo信息*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*添加Task信息*/
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
            ctx.put("message",  java.net.URLEncoder.encode("Task添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Task添加失败!"));
            return "error";
        }
    }

    /*查询Task信息*/
    public String QueryTask() {
        if(currentPage == 0) currentPage = 1;
        if(taskTime == null) taskTime = "";
        List<Task> taskList = taskDAO.QueryTaskInfo(leaveInfoObj, noteObj, userObj, taskTime, currentPage);
        /*计算总的页数和总的记录数*/
        taskDAO.CalculateTotalPageAndRecordNumber(leaveInfoObj, noteObj, userObj, taskTime);
        /*获取到总的页码数目*/
        totalPage = taskDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryTaskOutputToExcel() { 
        if(taskTime == null) taskTime = "";
        List<Task> taskList = taskDAO.QueryTaskInfo(leaveInfoObj,noteObj,userObj,taskTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Task信息记录"; 
        String[] headers = { "任务ID","请假ID","当前节点","当前状态","当前处理人","创建时间"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Task.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Task信息*/
    public String FrontQueryTask() {
        if(currentPage == 0) currentPage = 1;
        if(taskTime == null) taskTime = "";
        List<Task> taskList = taskDAO.QueryTaskInfo(leaveInfoObj, noteObj, userObj, taskTime, currentPage);
        /*计算总的页数和总的记录数*/
        taskDAO.CalculateTotalPageAndRecordNumber(leaveInfoObj, noteObj, userObj, taskTime);
        /*获取到总的页码数目*/
        totalPage = taskDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的Task信息*/
    public String ModifyTaskQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键taskId获取Task对象*/
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

    /*查询要修改的Task信息*/
    public String FrontShowTaskQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键taskId获取Task对象*/
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

    /*更新修改Task信息*/
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
            ctx.put("message",  java.net.URLEncoder.encode("Task信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Task信息更新失败!"));
            return "error";
       }
   }

    /*删除Task信息*/
    public String DeleteTask() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            taskDAO.DeleteTask(taskId);
            ctx.put("message",  java.net.URLEncoder.encode("Task删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Task删除失败!"));
            return "error";
        }
    }

}

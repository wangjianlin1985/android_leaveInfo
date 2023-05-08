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

    /*界面层需要查询的属性: 请假记录ID*/
    private LeaveInfo leaveObj;
    public void setLeaveObj(LeaveInfo leaveObj) {
        this.leaveObj = leaveObj;
    }
    public LeaveInfo getLeaveObj() {
        return this.leaveObj;
    }

    /*界面层需要查询的属性: 节点*/
    private Note noteObj;
    public void setNoteObj(Note noteObj) {
        this.noteObj = noteObj;
    }
    public Note getNoteObj() {
        return this.noteObj;
    }

    /*界面层需要查询的属性: 处理人*/
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

    private int historyTaskId;
    public void setHistoryTaskId(int historyTaskId) {
        this.historyTaskId = historyTaskId;
    }
    public int getHistoryTaskId() {
        return historyTaskId;
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
    @Resource HistoryTaskDAO historyTaskDAO;

    /*待操作的HistoryTask对象*/
    private HistoryTask historyTask;
    public void setHistoryTask(HistoryTask historyTask) {
        this.historyTask = historyTask;
    }
    public HistoryTask getHistoryTask() {
        return this.historyTask;
    }

    /*跳转到添加HistoryTask视图*/
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

    /*添加HistoryTask信息*/
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
            ctx.put("message",  java.net.URLEncoder.encode("HistoryTask添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("HistoryTask添加失败!"));
            return "error";
        }
    }

    /*查询HistoryTask信息*/
    public String QueryHistoryTask() {
        if(currentPage == 0) currentPage = 1;
        if(taskTime == null) taskTime = "";
        List<HistoryTask> historyTaskList = historyTaskDAO.QueryHistoryTaskInfo(leaveObj, noteObj, userObj, taskTime, currentPage);
        /*计算总的页数和总的记录数*/
        historyTaskDAO.CalculateTotalPageAndRecordNumber(leaveObj, noteObj, userObj, taskTime);
        /*获取到总的页码数目*/
        totalPage = historyTaskDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryHistoryTaskOutputToExcel() { 
        if(taskTime == null) taskTime = "";
        List<HistoryTask> historyTaskList = historyTaskDAO.QueryHistoryTaskInfo(leaveObj,noteObj,userObj,taskTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "HistoryTask信息记录"; 
        String[] headers = { "请假记录ID","节点","审批意见","处理人","创建时间","审批状态"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"HistoryTask.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询HistoryTask信息*/
    public String FrontQueryHistoryTask() {
        if(currentPage == 0) currentPage = 1;
        if(taskTime == null) taskTime = "";
        List<HistoryTask> historyTaskList = historyTaskDAO.QueryHistoryTaskInfo(leaveObj, noteObj, userObj, taskTime, currentPage);
        /*计算总的页数和总的记录数*/
        historyTaskDAO.CalculateTotalPageAndRecordNumber(leaveObj, noteObj, userObj, taskTime);
        /*获取到总的页码数目*/
        totalPage = historyTaskDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的HistoryTask信息*/
    public String ModifyHistoryTaskQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键historyTaskId获取HistoryTask对象*/
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

    /*查询要修改的HistoryTask信息*/
    public String FrontShowHistoryTaskQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键historyTaskId获取HistoryTask对象*/
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

    /*更新修改HistoryTask信息*/
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
            ctx.put("message",  java.net.URLEncoder.encode("HistoryTask信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("HistoryTask信息更新失败!"));
            return "error";
       }
   }

    /*删除HistoryTask信息*/
    public String DeleteHistoryTask() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            historyTaskDAO.DeleteHistoryTask(historyTaskId);
            ctx.put("message",  java.net.URLEncoder.encode("HistoryTask删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("HistoryTask删除失败!"));
            return "error";
        }
    }

}

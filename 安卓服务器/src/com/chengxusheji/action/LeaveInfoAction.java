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
import com.chengxusheji.dao.LeaveInfoDAO;
import com.chengxusheji.domain.LeaveInfo;
import com.chengxusheji.dao.DepartmentDAO;
import com.chengxusheji.domain.Department;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.dao.PositionDAO;
import com.chengxusheji.domain.Position;
import com.chengxusheji.dao.LeaveTypeDAO;
import com.chengxusheji.domain.LeaveType;
import com.chengxusheji.dao.DayTimeTypeDAO;
import com.chengxusheji.domain.DayTimeType;
import com.chengxusheji.dao.DayTimeTypeDAO;
import com.chengxusheji.domain.DayTimeType;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class LeaveInfoAction extends BaseAction {

    /*界面层需要查询的属性: 填表时间*/
    private String writeTime;
    public void setWriteTime(String writeTime) {
        this.writeTime = writeTime;
    }
    public String getWriteTime() {
        return this.writeTime;
    }

    /*界面层需要查询的属性: 部门*/
    private Department departmentObj;
    public void setDepartmentObj(Department departmentObj) {
        this.departmentObj = departmentObj;
    }
    public Department getDepartmentObj() {
        return this.departmentObj;
    }

    /*界面层需要查询的属性: 请假人*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*界面层需要查询的属性: 职级*/
    private Position positionObj;
    public void setPositionObj(Position positionObj) {
        this.positionObj = positionObj;
    }
    public Position getPositionObj() {
        return this.positionObj;
    }

    /*界面层需要查询的属性: 请假类别*/
    private LeaveType leaveTypeObj;
    public void setLeaveTypeObj(LeaveType leaveTypeObj) {
        this.leaveTypeObj = leaveTypeObj;
    }
    public LeaveType getLeaveTypeObj() {
        return this.leaveTypeObj;
    }

    /*界面层需要查询的属性: 开始时间*/
    private String startDate;
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getStartDate() {
        return this.startDate;
    }

    /*界面层需要查询的属性: 开始时间段*/
    private DayTimeType startDayTimeType;
    public void setStartDayTimeType(DayTimeType startDayTimeType) {
        this.startDayTimeType = startDayTimeType;
    }
    public DayTimeType getStartDayTimeType() {
        return this.startDayTimeType;
    }

    /*界面层需要查询的属性: 结束时间*/
    private String endDate;
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public String getEndDate() {
        return this.endDate;
    }

    /*界面层需要查询的属性: 结束时间段*/
    private DayTimeType endDayTimeType;
    public void setEndDayTimeType(DayTimeType endDayTimeType) {
        this.endDayTimeType = endDayTimeType;
    }
    public DayTimeType getEndDayTimeType() {
        return this.endDayTimeType;
    }

    /*界面层需要查询的属性: 前往地点*/
    private String place;
    public void setPlace(String place) {
        this.place = place;
    }
    public String getPlace() {
        return this.place;
    }

    /*界面层需要查询的属性: 请假事由*/
    private String reason;
    public void setReason(String reason) {
        this.reason = reason;
    }
    public String getReason() {
        return this.reason;
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

    private int leaveId;
    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
    }
    public int getLeaveId() {
        return leaveId;
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
    @Resource UserInfoDAO userInfoDAO;
    @Resource PositionDAO positionDAO;
    @Resource LeaveTypeDAO leaveTypeDAO;
    @Resource DayTimeTypeDAO dayTimeTypeDAO;
    @Resource LeaveInfoDAO leaveInfoDAO;

    /*待操作的LeaveInfo对象*/
    private LeaveInfo leaveInfo;
    public void setLeaveInfo(LeaveInfo leaveInfo) {
        this.leaveInfo = leaveInfo;
    }
    public LeaveInfo getLeaveInfo() {
        return this.leaveInfo;
    }

    /*跳转到添加LeaveInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Department信息*/
        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        /*查询所有的UserInfo信息*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        /*查询所有的Position信息*/
        List<Position> positionList = positionDAO.QueryAllPositionInfo();
        ctx.put("positionList", positionList);
        /*查询所有的LeaveType信息*/
        List<LeaveType> leaveTypeList = leaveTypeDAO.QueryAllLeaveTypeInfo();
        ctx.put("leaveTypeList", leaveTypeList);
        /*查询所有的DayTimeType信息*/
        List<DayTimeType> dayTimeTypeList = dayTimeTypeDAO.QueryAllDayTimeTypeInfo();
        ctx.put("dayTimeTypeList", dayTimeTypeList);
        return "add_view";
    }

    /*添加LeaveInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddLeaveInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Department departmentObj = departmentDAO.GetDepartmentByDepartmentId(leaveInfo.getDepartmentObj().getDepartmentId());
            leaveInfo.setDepartmentObj(departmentObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(leaveInfo.getUserObj().getUser_name());
            leaveInfo.setUserObj(userObj);
            Position positionObj = positionDAO.GetPositionByPositionId(leaveInfo.getPositionObj().getPositionId());
            leaveInfo.setPositionObj(positionObj);
            LeaveType leaveTypeObj = leaveTypeDAO.GetLeaveTypeByLeaveTypeId(leaveInfo.getLeaveTypeObj().getLeaveTypeId());
            leaveInfo.setLeaveTypeObj(leaveTypeObj);
            DayTimeType startDayTimeType = dayTimeTypeDAO.GetDayTimeTypeByDayTimeTypeId(leaveInfo.getStartDayTimeType().getDayTimeTypeId());
            leaveInfo.setStartDayTimeType(startDayTimeType);
            DayTimeType endDayTimeType = dayTimeTypeDAO.GetDayTimeTypeByDayTimeTypeId(leaveInfo.getEndDayTimeType().getDayTimeTypeId());
            leaveInfo.setEndDayTimeType(endDayTimeType);
            UserInfo writeUserObj = userInfoDAO.GetUserInfoByUser_name(leaveInfo.getWriteUserObj().getUser_name());
            leaveInfo.setWriteUserObj(writeUserObj);
            leaveInfoDAO.AddLeaveInfo(leaveInfo);
            ctx.put("message",  java.net.URLEncoder.encode("LeaveInfo添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LeaveInfo添加失败!"));
            return "error";
        }
    }

    /*查询LeaveInfo信息*/
    public String QueryLeaveInfo() {
        if(currentPage == 0) currentPage = 1;
        if(writeTime == null) writeTime = "";
        if(startDate == null) startDate = "";
        if(endDate == null) endDate = "";
        if(place == null) place = "";
        if(reason == null) reason = "";
        List<LeaveInfo> leaveInfoList = leaveInfoDAO.QueryLeaveInfoInfo(writeTime, departmentObj, userObj, positionObj, leaveTypeObj, startDate, startDayTimeType, endDate, endDayTimeType, place, reason, currentPage);
        /*计算总的页数和总的记录数*/
        leaveInfoDAO.CalculateTotalPageAndRecordNumber(writeTime, departmentObj, userObj, positionObj, leaveTypeObj, startDate, startDayTimeType, endDate, endDayTimeType, place, reason);
        /*获取到总的页码数目*/
        totalPage = leaveInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = leaveInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("leaveInfoList",  leaveInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("writeTime", writeTime);
        ctx.put("departmentObj", departmentObj);
        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("positionObj", positionObj);
        List<Position> positionList = positionDAO.QueryAllPositionInfo();
        ctx.put("positionList", positionList);
        ctx.put("leaveTypeObj", leaveTypeObj);
        List<LeaveType> leaveTypeList = leaveTypeDAO.QueryAllLeaveTypeInfo();
        ctx.put("leaveTypeList", leaveTypeList);
        ctx.put("startDate", startDate);
        ctx.put("startDayTimeType", startDayTimeType);
        List<DayTimeType> dayTimeTypeList = dayTimeTypeDAO.QueryAllDayTimeTypeInfo();
        ctx.put("dayTimeTypeList", dayTimeTypeList);
        ctx.put("endDate", endDate);
        ctx.put("endDayTimeType", endDayTimeType);
        ctx.put("place", place);
        ctx.put("reason", reason);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryLeaveInfoOutputToExcel() { 
        if(writeTime == null) writeTime = "";
        if(startDate == null) startDate = "";
        if(endDate == null) endDate = "";
        if(place == null) place = "";
        if(reason == null) reason = "";
        List<LeaveInfo> leaveInfoList = leaveInfoDAO.QueryLeaveInfoInfo(writeTime,departmentObj,userObj,positionObj,leaveTypeObj,startDate,startDayTimeType,endDate,endDayTimeType,place,reason);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "LeaveInfo信息记录"; 
        String[] headers = { "填表时间","部门","请假人","职级","请假类别","是否长假","开始时间","开始时间段","结束时间","结束时间段","请假天数","填写人","前往地点","请假事由","状态"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<leaveInfoList.size();i++) {
        	LeaveInfo leaveInfo = leaveInfoList.get(i); 
        	dataset.add(new String[]{leaveInfo.getWriteTime(),leaveInfo.getDepartmentObj().getDepartmentName(),
leaveInfo.getUserObj().getName(),
leaveInfo.getPositionObj().getPositionName(),
leaveInfo.getLeaveTypeObj().getLeaveTypeName(),
leaveInfo.getSfcj(),new SimpleDateFormat("yyyy-MM-dd").format(leaveInfo.getStartDate()),leaveInfo.getStartDayTimeType().getDayTimeTypeName(),
new SimpleDateFormat("yyyy-MM-dd").format(leaveInfo.getEndDate()),leaveInfo.getEndDayTimeType().getDayTimeTypeName(),
leaveInfo.getLeaveDays() + "",leaveInfo.getWriteUserObj().getName(),
leaveInfo.getPlace(),leaveInfo.getReason(),leaveInfo.getState() + ""});
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
			response.setHeader("Content-disposition","attachment; filename="+"LeaveInfo.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询LeaveInfo信息*/
    public String FrontQueryLeaveInfo() {
        if(currentPage == 0) currentPage = 1;
        if(writeTime == null) writeTime = "";
        if(startDate == null) startDate = "";
        if(endDate == null) endDate = "";
        if(place == null) place = "";
        if(reason == null) reason = "";
        List<LeaveInfo> leaveInfoList = leaveInfoDAO.QueryLeaveInfoInfo(writeTime, departmentObj, userObj, positionObj, leaveTypeObj, startDate, startDayTimeType, endDate, endDayTimeType, place, reason, currentPage);
        /*计算总的页数和总的记录数*/
        leaveInfoDAO.CalculateTotalPageAndRecordNumber(writeTime, departmentObj, userObj, positionObj, leaveTypeObj, startDate, startDayTimeType, endDate, endDayTimeType, place, reason);
        /*获取到总的页码数目*/
        totalPage = leaveInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = leaveInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("leaveInfoList",  leaveInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("writeTime", writeTime);
        ctx.put("departmentObj", departmentObj);
        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("positionObj", positionObj);
        List<Position> positionList = positionDAO.QueryAllPositionInfo();
        ctx.put("positionList", positionList);
        ctx.put("leaveTypeObj", leaveTypeObj);
        List<LeaveType> leaveTypeList = leaveTypeDAO.QueryAllLeaveTypeInfo();
        ctx.put("leaveTypeList", leaveTypeList);
        ctx.put("startDate", startDate);
        ctx.put("startDayTimeType", startDayTimeType);
        List<DayTimeType> dayTimeTypeList = dayTimeTypeDAO.QueryAllDayTimeTypeInfo();
        ctx.put("dayTimeTypeList", dayTimeTypeList);
        ctx.put("endDate", endDate);
        ctx.put("endDayTimeType", endDayTimeType);
        ctx.put("place", place);
        ctx.put("reason", reason);
        return "front_query_view";
    }

    /*查询要修改的LeaveInfo信息*/
    public String ModifyLeaveInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键leaveId获取LeaveInfo对象*/
        LeaveInfo leaveInfo = leaveInfoDAO.GetLeaveInfoByLeaveId(leaveId);

        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        List<Position> positionList = positionDAO.QueryAllPositionInfo();
        ctx.put("positionList", positionList);
        List<LeaveType> leaveTypeList = leaveTypeDAO.QueryAllLeaveTypeInfo();
        ctx.put("leaveTypeList", leaveTypeList);
        List<DayTimeType> dayTimeTypeList = dayTimeTypeDAO.QueryAllDayTimeTypeInfo();
        ctx.put("dayTimeTypeList", dayTimeTypeList);
        ctx.put("leaveInfo",  leaveInfo);
        return "modify_view";
    }

    /*查询要修改的LeaveInfo信息*/
    public String FrontShowLeaveInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键leaveId获取LeaveInfo对象*/
        LeaveInfo leaveInfo = leaveInfoDAO.GetLeaveInfoByLeaveId(leaveId);

        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        List<Position> positionList = positionDAO.QueryAllPositionInfo();
        ctx.put("positionList", positionList);
        List<LeaveType> leaveTypeList = leaveTypeDAO.QueryAllLeaveTypeInfo();
        ctx.put("leaveTypeList", leaveTypeList);
        List<DayTimeType> dayTimeTypeList = dayTimeTypeDAO.QueryAllDayTimeTypeInfo();
        ctx.put("dayTimeTypeList", dayTimeTypeList);
        ctx.put("leaveInfo",  leaveInfo);
        return "front_show_view";
    }

    /*更新修改LeaveInfo信息*/
    public String ModifyLeaveInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Department departmentObj = departmentDAO.GetDepartmentByDepartmentId(leaveInfo.getDepartmentObj().getDepartmentId());
            leaveInfo.setDepartmentObj(departmentObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(leaveInfo.getUserObj().getUser_name());
            leaveInfo.setUserObj(userObj);
            Position positionObj = positionDAO.GetPositionByPositionId(leaveInfo.getPositionObj().getPositionId());
            leaveInfo.setPositionObj(positionObj);
            LeaveType leaveTypeObj = leaveTypeDAO.GetLeaveTypeByLeaveTypeId(leaveInfo.getLeaveTypeObj().getLeaveTypeId());
            leaveInfo.setLeaveTypeObj(leaveTypeObj);
            DayTimeType startDayTimeType = dayTimeTypeDAO.GetDayTimeTypeByDayTimeTypeId(leaveInfo.getStartDayTimeType().getDayTimeTypeId());
            leaveInfo.setStartDayTimeType(startDayTimeType);
            DayTimeType endDayTimeType = dayTimeTypeDAO.GetDayTimeTypeByDayTimeTypeId(leaveInfo.getEndDayTimeType().getDayTimeTypeId());
            leaveInfo.setEndDayTimeType(endDayTimeType);
            UserInfo writeUserObj = userInfoDAO.GetUserInfoByUser_name(leaveInfo.getWriteUserObj().getUser_name());
            leaveInfo.setWriteUserObj(writeUserObj);
            leaveInfoDAO.UpdateLeaveInfo(leaveInfo);
            ctx.put("message",  java.net.URLEncoder.encode("LeaveInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LeaveInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除LeaveInfo信息*/
    public String DeleteLeaveInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            leaveInfoDAO.DeleteLeaveInfo(leaveId);
            ctx.put("message",  java.net.URLEncoder.encode("LeaveInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LeaveInfo删除失败!"));
            return "error";
        }
    }

}

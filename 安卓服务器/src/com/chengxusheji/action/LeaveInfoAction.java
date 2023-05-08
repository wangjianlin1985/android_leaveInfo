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

    /*�������Ҫ��ѯ������: ���ʱ��*/
    private String writeTime;
    public void setWriteTime(String writeTime) {
        this.writeTime = writeTime;
    }
    public String getWriteTime() {
        return this.writeTime;
    }

    /*�������Ҫ��ѯ������: ����*/
    private Department departmentObj;
    public void setDepartmentObj(Department departmentObj) {
        this.departmentObj = departmentObj;
    }
    public Department getDepartmentObj() {
        return this.departmentObj;
    }

    /*�������Ҫ��ѯ������: �����*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*�������Ҫ��ѯ������: ְ��*/
    private Position positionObj;
    public void setPositionObj(Position positionObj) {
        this.positionObj = positionObj;
    }
    public Position getPositionObj() {
        return this.positionObj;
    }

    /*�������Ҫ��ѯ������: ������*/
    private LeaveType leaveTypeObj;
    public void setLeaveTypeObj(LeaveType leaveTypeObj) {
        this.leaveTypeObj = leaveTypeObj;
    }
    public LeaveType getLeaveTypeObj() {
        return this.leaveTypeObj;
    }

    /*�������Ҫ��ѯ������: ��ʼʱ��*/
    private String startDate;
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getStartDate() {
        return this.startDate;
    }

    /*�������Ҫ��ѯ������: ��ʼʱ���*/
    private DayTimeType startDayTimeType;
    public void setStartDayTimeType(DayTimeType startDayTimeType) {
        this.startDayTimeType = startDayTimeType;
    }
    public DayTimeType getStartDayTimeType() {
        return this.startDayTimeType;
    }

    /*�������Ҫ��ѯ������: ����ʱ��*/
    private String endDate;
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public String getEndDate() {
        return this.endDate;
    }

    /*�������Ҫ��ѯ������: ����ʱ���*/
    private DayTimeType endDayTimeType;
    public void setEndDayTimeType(DayTimeType endDayTimeType) {
        this.endDayTimeType = endDayTimeType;
    }
    public DayTimeType getEndDayTimeType() {
        return this.endDayTimeType;
    }

    /*�������Ҫ��ѯ������: ǰ���ص�*/
    private String place;
    public void setPlace(String place) {
        this.place = place;
    }
    public String getPlace() {
        return this.place;
    }

    /*�������Ҫ��ѯ������: �������*/
    private String reason;
    public void setReason(String reason) {
        this.reason = reason;
    }
    public String getReason() {
        return this.reason;
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

    private int leaveId;
    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
    }
    public int getLeaveId() {
        return leaveId;
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
    @Resource UserInfoDAO userInfoDAO;
    @Resource PositionDAO positionDAO;
    @Resource LeaveTypeDAO leaveTypeDAO;
    @Resource DayTimeTypeDAO dayTimeTypeDAO;
    @Resource LeaveInfoDAO leaveInfoDAO;

    /*��������LeaveInfo����*/
    private LeaveInfo leaveInfo;
    public void setLeaveInfo(LeaveInfo leaveInfo) {
        this.leaveInfo = leaveInfo;
    }
    public LeaveInfo getLeaveInfo() {
        return this.leaveInfo;
    }

    /*��ת�����LeaveInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Department��Ϣ*/
        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        /*��ѯ���е�UserInfo��Ϣ*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        /*��ѯ���е�Position��Ϣ*/
        List<Position> positionList = positionDAO.QueryAllPositionInfo();
        ctx.put("positionList", positionList);
        /*��ѯ���е�LeaveType��Ϣ*/
        List<LeaveType> leaveTypeList = leaveTypeDAO.QueryAllLeaveTypeInfo();
        ctx.put("leaveTypeList", leaveTypeList);
        /*��ѯ���е�DayTimeType��Ϣ*/
        List<DayTimeType> dayTimeTypeList = dayTimeTypeDAO.QueryAllDayTimeTypeInfo();
        ctx.put("dayTimeTypeList", dayTimeTypeList);
        return "add_view";
    }

    /*���LeaveInfo��Ϣ*/
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
            ctx.put("message",  java.net.URLEncoder.encode("LeaveInfo��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LeaveInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯLeaveInfo��Ϣ*/
    public String QueryLeaveInfo() {
        if(currentPage == 0) currentPage = 1;
        if(writeTime == null) writeTime = "";
        if(startDate == null) startDate = "";
        if(endDate == null) endDate = "";
        if(place == null) place = "";
        if(reason == null) reason = "";
        List<LeaveInfo> leaveInfoList = leaveInfoDAO.QueryLeaveInfoInfo(writeTime, departmentObj, userObj, positionObj, leaveTypeObj, startDate, startDayTimeType, endDate, endDayTimeType, place, reason, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        leaveInfoDAO.CalculateTotalPageAndRecordNumber(writeTime, departmentObj, userObj, positionObj, leaveTypeObj, startDate, startDayTimeType, endDate, endDayTimeType, place, reason);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = leaveInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryLeaveInfoOutputToExcel() { 
        if(writeTime == null) writeTime = "";
        if(startDate == null) startDate = "";
        if(endDate == null) endDate = "";
        if(place == null) place = "";
        if(reason == null) reason = "";
        List<LeaveInfo> leaveInfoList = leaveInfoDAO.QueryLeaveInfoInfo(writeTime,departmentObj,userObj,positionObj,leaveTypeObj,startDate,startDayTimeType,endDate,endDayTimeType,place,reason);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "LeaveInfo��Ϣ��¼"; 
        String[] headers = { "���ʱ��","����","�����","ְ��","������","�Ƿ񳤼�","��ʼʱ��","��ʼʱ���","����ʱ��","����ʱ���","�������","��д��","ǰ���ص�","�������","״̬"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"LeaveInfo.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯLeaveInfo��Ϣ*/
    public String FrontQueryLeaveInfo() {
        if(currentPage == 0) currentPage = 1;
        if(writeTime == null) writeTime = "";
        if(startDate == null) startDate = "";
        if(endDate == null) endDate = "";
        if(place == null) place = "";
        if(reason == null) reason = "";
        List<LeaveInfo> leaveInfoList = leaveInfoDAO.QueryLeaveInfoInfo(writeTime, departmentObj, userObj, positionObj, leaveTypeObj, startDate, startDayTimeType, endDate, endDayTimeType, place, reason, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        leaveInfoDAO.CalculateTotalPageAndRecordNumber(writeTime, departmentObj, userObj, positionObj, leaveTypeObj, startDate, startDayTimeType, endDate, endDayTimeType, place, reason);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = leaveInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�LeaveInfo��Ϣ*/
    public String ModifyLeaveInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������leaveId��ȡLeaveInfo����*/
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

    /*��ѯҪ�޸ĵ�LeaveInfo��Ϣ*/
    public String FrontShowLeaveInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������leaveId��ȡLeaveInfo����*/
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

    /*�����޸�LeaveInfo��Ϣ*/
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
            ctx.put("message",  java.net.URLEncoder.encode("LeaveInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LeaveInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��LeaveInfo��Ϣ*/
    public String DeleteLeaveInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            leaveInfoDAO.DeleteLeaveInfo(leaveId);
            ctx.put("message",  java.net.URLEncoder.encode("LeaveInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LeaveInfoɾ��ʧ��!"));
            return "error";
        }
    }

}

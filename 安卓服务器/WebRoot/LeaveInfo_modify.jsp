<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.LeaveInfo" %>
<%@ page import="com.chengxusheji.domain.Department" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="com.chengxusheji.domain.Position" %>
<%@ page import="com.chengxusheji.domain.LeaveType" %>
<%@ page import="com.chengxusheji.domain.DayTimeType" %>
<%@ page import="com.chengxusheji.domain.DayTimeType" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Department信息
    List<Department> departmentList = (List<Department>)request.getAttribute("departmentList");
    //获取所有的UserInfo信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    //获取所有的Position信息
    List<Position> positionList = (List<Position>)request.getAttribute("positionList");
    //获取所有的LeaveType信息
    List<LeaveType> leaveTypeList = (List<LeaveType>)request.getAttribute("leaveTypeList");
    //获取所有的DayTimeType信息
    List<DayTimeType> dayTimeTypeList = (List<DayTimeType>)request.getAttribute("dayTimeTypeList");
    LeaveInfo leaveInfo = (LeaveInfo)request.getAttribute("leaveInfo");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改请假信息</TITLE>
<STYLE type=text/css>
BODY {
	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*验证表单*/
function checkForm() {
    var sfcj = document.getElementById("leaveInfo.sfcj").value;
    if(sfcj=="") {
        alert('请输入是否长假!');
        return false;
    }
    var place = document.getElementById("leaveInfo.place").value;
    if(place=="") {
        alert('请输入前往地点!');
        return false;
    }
    var reason = document.getElementById("leaveInfo.reason").value;
    if(reason=="") {
        alert('请输入请假事由!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>
<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="LeaveInfo/LeaveInfo_ModifyLeaveInfo.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>请假记录ID:</td>
    <td width=70%><input id="leaveInfo.leaveId" name="leaveInfo.leaveId" type="text" value="<%=leaveInfo.getLeaveId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>填表时间:</td>
    <td width=70%><input id="leaveInfo.writeTime" name="leaveInfo.writeTime" type="text" size="40" value='<%=leaveInfo.getWriteTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>部门:</td>
    <td width=70%>
      <select name="leaveInfo.departmentObj.departmentId">
      <%
        for(Department department:departmentList) {
          String selected = "";
          if(department.getDepartmentId() == leaveInfo.getDepartmentObj().getDepartmentId())
            selected = "selected";
      %>
          <option value='<%=department.getDepartmentId() %>' <%=selected %>><%=department.getDepartmentName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>请假人:</td>
    <td width=70%>
      <select name="leaveInfo.userObj.user_name">
      <%
        for(UserInfo userInfo:userInfoList) {
          String selected = "";
          if(userInfo.getUser_name().equals(leaveInfo.getUserObj().getUser_name()))
            selected = "selected";
      %>
          <option value='<%=userInfo.getUser_name() %>' <%=selected %>><%=userInfo.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>职级:</td>
    <td width=70%>
      <select name="leaveInfo.positionObj.positionId">
      <%
        for(Position position:positionList) {
          String selected = "";
          if(position.getPositionId() == leaveInfo.getPositionObj().getPositionId())
            selected = "selected";
      %>
          <option value='<%=position.getPositionId() %>' <%=selected %>><%=position.getPositionName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>请假类别:</td>
    <td width=70%>
      <select name="leaveInfo.leaveTypeObj.leaveTypeId">
      <%
        for(LeaveType leaveType:leaveTypeList) {
          String selected = "";
          if(leaveType.getLeaveTypeId() == leaveInfo.getLeaveTypeObj().getLeaveTypeId())
            selected = "selected";
      %>
          <option value='<%=leaveType.getLeaveTypeId() %>' <%=selected %>><%=leaveType.getLeaveTypeName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>是否长假:</td>
    <td width=70%><input id="leaveInfo.sfcj" name="leaveInfo.sfcj" type="text" size="20" value='<%=leaveInfo.getSfcj() %>'/></td>
  </tr>

  <tr>
    <td width=30%>开始时间:</td>
    <% DateFormat startDateSDF = new SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><input type="text" readonly  id="leaveInfo.startDate"  name="leaveInfo.startDate" onclick="setDay(this);" value='<%=startDateSDF.format(leaveInfo.getStartDate()) %>'/></td>
  </tr>

  <tr>
    <td width=30%>开始时间段:</td>
    <td width=70%>
      <select name="leaveInfo.startDayTimeType.dayTimeTypeId">
      <%
        for(DayTimeType dayTimeType:dayTimeTypeList) {
          String selected = "";
          if(dayTimeType.getDayTimeTypeId() == leaveInfo.getStartDayTimeType().getDayTimeTypeId())
            selected = "selected";
      %>
          <option value='<%=dayTimeType.getDayTimeTypeId() %>' <%=selected %>><%=dayTimeType.getDayTimeTypeName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>结束时间:</td>
    <% DateFormat endDateSDF = new SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><input type="text" readonly  id="leaveInfo.endDate"  name="leaveInfo.endDate" onclick="setDay(this);" value='<%=endDateSDF.format(leaveInfo.getEndDate()) %>'/></td>
  </tr>

  <tr>
    <td width=30%>结束时间段:</td>
    <td width=70%>
      <select name="leaveInfo.endDayTimeType.dayTimeTypeId">
      <%
        for(DayTimeType dayTimeType:dayTimeTypeList) {
          String selected = "";
          if(dayTimeType.getDayTimeTypeId() == leaveInfo.getEndDayTimeType().getDayTimeTypeId())
            selected = "selected";
      %>
          <option value='<%=dayTimeType.getDayTimeTypeId() %>' <%=selected %>><%=dayTimeType.getDayTimeTypeName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>请假天数:</td>
    <td width=70%><input id="leaveInfo.leaveDays" name="leaveInfo.leaveDays" type="text" size="8" value='<%=leaveInfo.getLeaveDays() %>'/></td>
  </tr>

  <tr>
    <td width=30%>填写人:</td>
    <td width=70%>
      <select name="leaveInfo.writeUserObj.user_name">
      <%
        for(UserInfo userInfo:userInfoList) {
          String selected = "";
          if(userInfo.getUser_name().equals(leaveInfo.getWriteUserObj().getUser_name()))
            selected = "selected";
      %>
          <option value='<%=userInfo.getUser_name() %>' <%=selected %>><%=userInfo.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>前往地点:</td>
    <td width=70%><input id="leaveInfo.place" name="leaveInfo.place" type="text" size="60" value='<%=leaveInfo.getPlace() %>'/></td>
  </tr>

  <tr>
    <td width=30%>请假事由:</td>
    <td width=70%><textarea id="leaveInfo.reason" name="leaveInfo.reason" rows=5 cols=50><%=leaveInfo.getReason() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>备注:</td>
    <td width=70%><textarea id="leaveInfo.memo" name="leaveInfo.memo" rows=5 cols=50><%=leaveInfo.getMemo() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>状态:</td>
    <td width=70%><input id="leaveInfo.state" name="leaveInfo.state" type="text" size="8" value='<%=leaveInfo.getState() %>'/></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>

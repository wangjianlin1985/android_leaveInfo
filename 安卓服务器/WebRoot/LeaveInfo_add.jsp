<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.Department" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="com.chengxusheji.domain.Position" %>
<%@ page import="com.chengxusheji.domain.LeaveType" %>
<%@ page import="com.chengxusheji.domain.DayTimeType" %>
<%@ page import="com.chengxusheji.domain.DayTimeType" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�Department��Ϣ
    List<Department> departmentList = (List<Department>)request.getAttribute("departmentList");
    //��ȡ���е�UserInfo��Ϣ
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    //��ȡ���е�Position��Ϣ
    List<Position> positionList = (List<Position>)request.getAttribute("positionList");
    //��ȡ���е�LeaveType��Ϣ
    List<LeaveType> leaveTypeList = (List<LeaveType>)request.getAttribute("leaveTypeList");
    //��ȡ���е�DayTimeType��Ϣ
    List<DayTimeType> dayTimeTypeList = (List<DayTimeType>)request.getAttribute("dayTimeTypeList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>��������Ϣ</TITLE> 
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
/*��֤��*/
function checkForm() {
    var sfcj = document.getElementById("leaveInfo.sfcj").value;
    if(sfcj=="") {
        alert('�������Ƿ񳤼�!');
        return false;
    }
    var place = document.getElementById("leaveInfo.place").value;
    if(place=="") {
        alert('������ǰ���ص�!');
        return false;
    }
    var reason = document.getElementById("leaveInfo.reason").value;
    if(reason=="") {
        alert('�������������!');
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
    <TD align="left" vAlign=top >
    <s:form action="LeaveInfo/LeaveInfo_AddLeaveInfo.action" method="post" id="leaveInfoAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>���ʱ��:</td>
    <td width=70%><input id="leaveInfo.writeTime" name="leaveInfo.writeTime" type="text" size="40" /></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%>
      <select name="leaveInfo.departmentObj.departmentId">
      <%
        for(Department department:departmentList) {
      %>
          <option value='<%=department.getDepartmentId() %>'><%=department.getDepartmentName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>�����:</td>
    <td width=70%>
      <select name="leaveInfo.userObj.user_name">
      <%
        for(UserInfo userInfo:userInfoList) {
      %>
          <option value='<%=userInfo.getUser_name() %>'><%=userInfo.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>ְ��:</td>
    <td width=70%>
      <select name="leaveInfo.positionObj.positionId">
      <%
        for(Position position:positionList) {
      %>
          <option value='<%=position.getPositionId() %>'><%=position.getPositionName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>������:</td>
    <td width=70%>
      <select name="leaveInfo.leaveTypeObj.leaveTypeId">
      <%
        for(LeaveType leaveType:leaveTypeList) {
      %>
          <option value='<%=leaveType.getLeaveTypeId() %>'><%=leaveType.getLeaveTypeName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>�Ƿ񳤼�:</td>
    <td width=70%><input id="leaveInfo.sfcj" name="leaveInfo.sfcj" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>��ʼʱ��:</td>
    <td width=70%><input type="text" readonly id="leaveInfo.startDate"  name="leaveInfo.startDate" onclick="setDay(this);"/></td>
  </tr>

  <tr>
    <td width=30%>��ʼʱ���:</td>
    <td width=70%>
      <select name="leaveInfo.startDayTimeType.dayTimeTypeId">
      <%
        for(DayTimeType dayTimeType:dayTimeTypeList) {
      %>
          <option value='<%=dayTimeType.getDayTimeTypeId() %>'><%=dayTimeType.getDayTimeTypeName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>����ʱ��:</td>
    <td width=70%><input type="text" readonly id="leaveInfo.endDate"  name="leaveInfo.endDate" onclick="setDay(this);"/></td>
  </tr>

  <tr>
    <td width=30%>����ʱ���:</td>
    <td width=70%>
      <select name="leaveInfo.endDayTimeType.dayTimeTypeId">
      <%
        for(DayTimeType dayTimeType:dayTimeTypeList) {
      %>
          <option value='<%=dayTimeType.getDayTimeTypeId() %>'><%=dayTimeType.getDayTimeTypeName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>�������:</td>
    <td width=70%><input id="leaveInfo.leaveDays" name="leaveInfo.leaveDays" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>��д��:</td>
    <td width=70%>
      <select name="leaveInfo.writeUserObj.user_name">
      <%
        for(UserInfo userInfo:userInfoList) {
      %>
          <option value='<%=userInfo.getUser_name() %>'><%=userInfo.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>ǰ���ص�:</td>
    <td width=70%><input id="leaveInfo.place" name="leaveInfo.place" type="text" size="60" /></td>
  </tr>

  <tr>
    <td width=30%>�������:</td>
    <td width=70%><textarea id="leaveInfo.reason" name="leaveInfo.reason" rows="5" cols="50"></textarea></td>
  </tr>

  <tr>
    <td width=30%>��ע:</td>
    <td width=70%><textarea id="leaveInfo.memo" name="leaveInfo.memo" rows="5" cols="50"></textarea></td>
  </tr>

  <tr>
    <td width=30%>״̬:</td>
    <td width=70%><input id="leaveInfo.state" name="leaveInfo.state" type="text" size="8" /></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='����' >
        &nbsp;&nbsp;
        <input type="reset" value='��д' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>

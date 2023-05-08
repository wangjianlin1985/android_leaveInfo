<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.LeaveInfo" %>
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
    LeaveInfo leaveInfo = (LeaveInfo)request.getAttribute("leaveInfo");

%>
<HTML><HEAD><TITLE>�鿴�����Ϣ</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>��ټ�¼ID:</td>
    <td width=70%><%=leaveInfo.getLeaveId() %></td>
  </tr>

  <tr>
    <td width=30%>���ʱ��:</td>
    <td width=70%><%=leaveInfo.getWriteTime() %></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%>
      <%=leaveInfo.getDepartmentObj().getDepartmentName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>�����:</td>
    <td width=70%>
      <%=leaveInfo.getUserObj().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>ְ��:</td>
    <td width=70%>
      <%=leaveInfo.getPositionObj().getPositionName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>������:</td>
    <td width=70%>
      <%=leaveInfo.getLeaveTypeObj().getLeaveTypeName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>�Ƿ񳤼�:</td>
    <td width=70%><%=leaveInfo.getSfcj() %></td>
  </tr>

  <tr>
    <td width=30%>��ʼʱ��:</td>
        <% java.text.DateFormat startDateSDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=startDateSDF.format(leaveInfo.getStartDate()) %></td>
  </tr>

  <tr>
    <td width=30%>��ʼʱ���:</td>
    <td width=70%>
      <%=leaveInfo.getStartDayTimeType().getDayTimeTypeName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>����ʱ��:</td>
        <% java.text.DateFormat endDateSDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=endDateSDF.format(leaveInfo.getEndDate()) %></td>
  </tr>

  <tr>
    <td width=30%>����ʱ���:</td>
    <td width=70%>
      <%=leaveInfo.getStartDayTimeType().getDayTimeTypeName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>�������:</td>
    <td width=70%><%=leaveInfo.getLeaveDays() %></td>
  </tr>

  <tr>
    <td width=30%>��д��:</td>
    <td width=70%>
      <%=leaveInfo.getUserObj().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>ǰ���ص�:</td>
    <td width=70%><%=leaveInfo.getPlace() %></td>
  </tr>

  <tr>
    <td width=30%>�������:</td>
    <td width=70%><%=leaveInfo.getReason() %></td>
  </tr>

  <tr>
    <td width=30%>��ע:</td>
    <td width=70%><%=leaveInfo.getMemo() %></td>
  </tr>

  <tr>
    <td width=30%>״̬:</td>
    <td width=70%><%=leaveInfo.getState() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="����" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>

<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="com.chengxusheji.domain.Department" %>
<%@ page import="com.chengxusheji.domain.Position" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Department信息
    List<Department> departmentList = (List<Department>)request.getAttribute("departmentList");
    //获取所有的Position信息
    List<Position> positionList = (List<Position>)request.getAttribute("positionList");
    UserInfo userInfo = (UserInfo)request.getAttribute("userInfo");

%>
<HTML><HEAD><TITLE>查看用户信息</TITLE>
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
    <td width=30%>用户名:</td>
    <td width=70%><%=userInfo.getUser_name() %></td>
  </tr>

  <tr>
    <td width=30%>密码:</td>
    <td width=70%><%=userInfo.getPassword() %></td>
  </tr>

  <tr>
    <td width=30%>姓名:</td>
    <td width=70%><%=userInfo.getName() %></td>
  </tr>

  <tr>
    <td width=30%>性别:</td>
    <td width=70%><%=userInfo.getSex() %></td>
  </tr>

  <tr>
    <td width=30%>出生日期:</td>
        <% java.text.DateFormat birthdaySDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=birthdaySDF.format(userInfo.getBirthday()) %></td>
  </tr>

  <tr>
    <td width=30%>所在部门:</td>
    <td width=70%>
      <%=userInfo.getDepartmentObj().getDepartmentName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>手机号:</td>
    <td width=70%><%=userInfo.getMobileNumber() %></td>
  </tr>

  <tr>
    <td width=30%>职级:</td>
    <td width=70%>
      <%=userInfo.getPositionName().getPositionName() %>
    </td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>

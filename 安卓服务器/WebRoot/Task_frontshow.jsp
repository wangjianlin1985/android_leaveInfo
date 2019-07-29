<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Task" %>
<%@ page import="com.chengxusheji.domain.LeaveInfo" %>
<%@ page import="com.chengxusheji.domain.Note" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的LeaveInfo信息
    List<LeaveInfo> leaveInfoList = (List<LeaveInfo>)request.getAttribute("leaveInfoList");
    //获取所有的Note信息
    List<Note> noteList = (List<Note>)request.getAttribute("noteList");
    //获取所有的UserInfo信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    Task task = (Task)request.getAttribute("task");

%>
<HTML><HEAD><TITLE>查看任务信息</TITLE>
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
    <td width=30%>任务ID:</td>
    <td width=70%><%=task.getTaskId() %></td>
  </tr>

  <tr>
    <td width=30%>请假ID:</td>
    <td width=70%>
      <%=task.getLeaveInfoObj().getLeaveId() %>
    </td>
  </tr>

  <tr>
    <td width=30%>当前节点:</td>
    <td width=70%>
      <%=task.getNoteObj().getNoteName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>当前状态:</td>
    <td width=70%><%=task.getState() %></td>
  </tr>

  <tr>
    <td width=30%>当前处理人:</td>
    <td width=70%>
      <%=task.getUserObj().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>创建时间:</td>
    <td width=70%><%=task.getTaskTime() %></td>
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

<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
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
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>添加任务信息</TITLE> 
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
    var taskTime = document.getElementById("task.taskTime").value;
    if(taskTime=="") {
        alert('请输入创建时间!');
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
    <s:form action="Task/Task_AddTask.action" method="post" id="taskAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>请假ID:</td>
    <td width=70%>
      <select name="task.leaveInfoObj.leaveId">
      <%
        for(LeaveInfo leaveInfo:leaveInfoList) {
      %>
          <option value='<%=leaveInfo.getLeaveId() %>'><%=leaveInfo.getLeaveId() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>当前节点:</td>
    <td width=70%>
      <select name="task.noteObj.noteId">
      <%
        for(Note note:noteList) {
      %>
          <option value='<%=note.getNoteId() %>'><%=note.getNoteName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>当前状态:</td>
    <td width=70%><input id="task.state" name="task.state" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>当前处理人:</td>
    <td width=70%>
      <select name="task.userObj.user_name">
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
    <td width=30%>创建时间:</td>
    <td width=70%><input id="task.taskTime" name="task.taskTime" type="text" size="20" /></td>
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

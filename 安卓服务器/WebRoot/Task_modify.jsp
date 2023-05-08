<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Task" %>
<%@ page import="com.chengxusheji.domain.LeaveInfo" %>
<%@ page import="com.chengxusheji.domain.Note" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�LeaveInfo��Ϣ
    List<LeaveInfo> leaveInfoList = (List<LeaveInfo>)request.getAttribute("leaveInfoList");
    //��ȡ���е�Note��Ϣ
    List<Note> noteList = (List<Note>)request.getAttribute("noteList");
    //��ȡ���е�UserInfo��Ϣ
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    Task task = (Task)request.getAttribute("task");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸�������Ϣ</TITLE>
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
    var taskTime = document.getElementById("task.taskTime").value;
    if(taskTime=="") {
        alert('�����봴��ʱ��!');
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
    <TD align="left" vAlign=top ><s:form action="Task/Task_ModifyTask.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>����ID:</td>
    <td width=70%><input id="task.taskId" name="task.taskId" type="text" value="<%=task.getTaskId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>���ID:</td>
    <td width=70%>
      <select name="task.leaveInfoObj.leaveId">
      <%
        for(LeaveInfo leaveInfo:leaveInfoList) {
          String selected = "";
          if(leaveInfo.getLeaveId() == task.getLeaveInfoObj().getLeaveId())
            selected = "selected";
      %>
          <option value='<%=leaveInfo.getLeaveId() %>' <%=selected %>><%=leaveInfo.getLeaveId() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>��ǰ�ڵ�:</td>
    <td width=70%>
      <select name="task.noteObj.noteId">
      <%
        for(Note note:noteList) {
          String selected = "";
          if(note.getNoteId() == task.getNoteObj().getNoteId())
            selected = "selected";
      %>
          <option value='<%=note.getNoteId() %>' <%=selected %>><%=note.getNoteName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>��ǰ״̬:</td>
    <td width=70%><input id="task.state" name="task.state" type="text" size="8" value='<%=task.getState() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ǰ������:</td>
    <td width=70%>
      <select name="task.userObj.user_name">
      <%
        for(UserInfo userInfo:userInfoList) {
          String selected = "";
          if(userInfo.getUser_name().equals(task.getUserObj().getUser_name()))
            selected = "selected";
      %>
          <option value='<%=userInfo.getUser_name() %>' <%=selected %>><%=userInfo.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>����ʱ��:</td>
    <td width=70%><input id="task.taskTime" name="task.taskTime" type="text" size="20" value='<%=task.getTaskTime() %>'/></td>
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

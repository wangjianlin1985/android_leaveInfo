<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.HistoryTask" %>
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
    HistoryTask historyTask = (HistoryTask)request.getAttribute("historyTask");

%>
<HTML><HEAD><TITLE>查看历史任务</TITLE>
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
    <td width=30%>任务历史记录Id:</td>
    <td width=70%><%=historyTask.getHistoryTaskId() %></td>
  </tr>

  <tr>
    <td width=30%>请假记录ID:</td>
    <td width=70%>
      <%=historyTask.getLeaveObj().getLeaveId() %>
    </td>
  </tr>

  <tr>
    <td width=30%>节点:</td>
    <td width=70%>
      <%=historyTask.getNoteObj().getNoteName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>审批意见:</td>
    <td width=70%><%=historyTask.getCheckSuggest() %></td>
  </tr>

  <tr>
    <td width=30%>处理人:</td>
    <td width=70%>
      <%=historyTask.getUserObj().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>创建时间:</td>
    <td width=70%><%=historyTask.getTaskTime() %></td>
  </tr>

  <tr>
    <td width=30%>审批状态:</td>
    <td width=70%><%=historyTask.getCheckState() %></td>
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

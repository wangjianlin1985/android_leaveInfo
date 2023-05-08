<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.HistoryTask" %>
<%@ page import="com.chengxusheji.domain.LeaveInfo" %>
<%@ page import="com.chengxusheji.domain.Note" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
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
    HistoryTask historyTask = (HistoryTask)request.getAttribute("historyTask");

%>
<HTML><HEAD><TITLE>�鿴��ʷ����</TITLE>
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
    <td width=30%>������ʷ��¼Id:</td>
    <td width=70%><%=historyTask.getHistoryTaskId() %></td>
  </tr>

  <tr>
    <td width=30%>��ټ�¼ID:</td>
    <td width=70%>
      <%=historyTask.getLeaveObj().getLeaveId() %>
    </td>
  </tr>

  <tr>
    <td width=30%>�ڵ�:</td>
    <td width=70%>
      <%=historyTask.getNoteObj().getNoteName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>�������:</td>
    <td width=70%><%=historyTask.getCheckSuggest() %></td>
  </tr>

  <tr>
    <td width=30%>������:</td>
    <td width=70%>
      <%=historyTask.getUserObj().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>����ʱ��:</td>
    <td width=70%><%=historyTask.getTaskTime() %></td>
  </tr>

  <tr>
    <td width=30%>����״̬:</td>
    <td width=70%><%=historyTask.getCheckState() %></td>
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

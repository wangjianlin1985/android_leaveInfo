<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Note" %>
<%@ page import="com.chengxusheji.domain.Department" %>
<%@ page import="com.chengxusheji.domain.Position" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�Department��Ϣ
    List<Department> departmentList = (List<Department>)request.getAttribute("departmentList");
    //��ȡ���е�Position��Ϣ
    List<Position> positionList = (List<Position>)request.getAttribute("positionList");
    Note note = (Note)request.getAttribute("note");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸Ľڵ���Ϣ</TITLE>
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
    var noteName = document.getElementById("note.noteName").value;
    if(noteName=="") {
        alert('������ڵ�����!');
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
    <TD align="left" vAlign=top ><s:form action="Note/Note_ModifyNote.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>�ڵ�ID:</td>
    <td width=70%><input id="note.noteId" name="note.noteId" type="text" value="<%=note.getNoteId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>�ڵ�����:</td>
    <td width=70%><input id="note.noteName" name="note.noteName" type="text" size="20" value='<%=note.getNoteName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>������:</td>
    <td width=70%>
      <select name="note.departmentObj.departmentId">
      <%
        for(Department department:departmentList) {
          String selected = "";
          if(department.getDepartmentId() == note.getDepartmentObj().getDepartmentId())
            selected = "selected";
      %>
          <option value='<%=department.getDepartmentId() %>' <%=selected %>><%=department.getDepartmentName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>��Ҫְ��:</td>
    <td width=70%>
      <select name="note.positionObj.positionId">
      <%
        for(Position position:positionList) {
          String selected = "";
          if(position.getPositionId() == note.getPositionObj().getPositionId())
            selected = "selected";
      %>
          <option value='<%=position.getPositionId() %>' <%=selected %>><%=position.getPositionName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>�ڵ���:</td>
    <td width=70%><input id="note.noteIndex" name="note.noteIndex" type="text" size="8" value='<%=note.getNoteIndex() %>'/></td>
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

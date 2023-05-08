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
    List<LeaveInfo> leaveInfoList = (List<LeaveInfo>)request.getAttribute("leaveInfoList");
    //获取所有的Department信息
    List<Department> departmentList = (List<Department>)request.getAttribute("departmentList");
    Department departmentObj = (Department)request.getAttribute("department");

    //获取所有的UserInfo信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    UserInfo userObj = (UserInfo)request.getAttribute("userInfo");

    //获取所有的Position信息
    List<Position> positionList = (List<Position>)request.getAttribute("positionList");
    Position positionObj = (Position)request.getAttribute("position");

    //获取所有的LeaveType信息
    List<LeaveType> leaveTypeList = (List<LeaveType>)request.getAttribute("leaveTypeList");
    LeaveType leaveTypeObj = (LeaveType)request.getAttribute("leaveType");

    //获取所有的DayTimeType信息
    List<DayTimeType> dayTimeTypeList = (List<DayTimeType>)request.getAttribute("dayTimeTypeList");
    DayTimeType startDayTimeType = (DayTimeType)request.getAttribute("dayTimeType");

    DayTimeType endDayTimeType = (DayTimeType)request.getAttribute("dayTimeType");

    UserInfo writeUserObj = (UserInfo)request.getAttribute("userInfo");

    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int  recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    String writeTime = (String)request.getAttribute("writeTime"); //填表时间查询关键字
    String startDate = (String)request.getAttribute("startDate"); //开始时间查询关键字
    String endDate = (String)request.getAttribute("endDate"); //结束时间查询关键字
    String place = (String)request.getAttribute("place"); //前往地点查询关键字
    String reason = (String)request.getAttribute("reason"); //请假事由查询关键字
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>请假信息查询</title>
<style type="text/css">
<!--
body {
    margin-left: 0px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
}
.STYLE1 {font-size: 12px}
.STYLE3 {font-size: 12px; font-weight: bold; }
.STYLE4 {
    color: #03515d;
    font-size: 12px;
}
-->
</style>

 <script src="<%=basePath %>calendar.js"></script>
<script>
var  highlightcolor='#c1ebff';
//此处clickcolor只能用win系统颜色代码才能成功,如果用#xxxxxx的代码就不行,还没搞清楚为什么:(
var  clickcolor='#51b2f6';
function  changeto(){
source=event.srcElement;
if  (source.tagName=="TR"||source.tagName=="TABLE")
return;
while(source.tagName!="TD")
source=source.parentElement;
source=source.parentElement;
cs  =  source.children;
//alert(cs.length);
if  (cs[1].style.backgroundColor!=clickcolor&&source.id!="nc")
for(i=0;i<cs.length;i++){
    cs[i].style.backgroundColor=clickcolor;
}
else
for(i=0;i<cs.length;i++){
    cs[i].style.backgroundColor="";
}
}

function  changeback(){
if  (event.fromElement.contains(event.toElement)||source.contains(event.toElement)||source.id=="nc")
return
if  (event.toElement!=source&&cs[1].style.backgroundColor!=clickcolor)
//source.style.backgroundColor=originalcolor
for(i=0;i<cs.length;i++){
	cs[i].style.backgroundColor="";
}
}

/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.forms[0].currentPage.value = currentPage;
    document.forms[0].action = "<%=basePath %>/LeaveInfo/LeaveInfo_QueryLeaveInfo.action";
    document.forms[0].submit();

}

function changepage(totalPage)
{
    var pageValue=document.bookQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.leaveInfoQueryForm.currentPage.value = pageValue;
    document.forms["leaveInfoQueryForm"].action = "<%=basePath %>/LeaveInfo/LeaveInfo_QueryLeaveInfo.action";
    document.leaveInfoQueryForm.submit();
}

function QueryLeaveInfo() {
	document.forms["leaveInfoQueryForm"].action = "<%=basePath %>/LeaveInfo/LeaveInfo_QueryLeaveInfo.action";
	document.forms["leaveInfoQueryForm"].submit();
}

function OutputToExcel() {
	document.forms["leaveInfoQueryForm"].action = "<%=basePath %>/LeaveInfo/LeaveInfo_QueryLeaveInfoOutputToExcel.action";
	document.forms["leaveInfoQueryForm"].submit(); 
}
</script>
</head>

<body>
<form action="<%=basePath %>/LeaveInfo/LeaveInfo_QueryLeaveInfo.action" name="leaveInfoQueryForm" method="post">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="30" background="<%=basePath %>images/tab_05.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="12" height="30"><img src="<%=basePath %>images/tab_03.gif" width="12" height="30" /></td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="46%" valign="middle"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="5%"><div align="center"><img src="<%=basePath %>images/tb.gif" width="16" height="16" /></div></td>
                <td width="95%" class="STYLE1"><span class="STYLE3">你当前的位置</span>：[请假信息管理]-[请假信息查询]</td>
              </tr>
            </table></td>
            <td width="54%"><table border="0" align="right" cellpadding="0" cellspacing="0">

            </table></td>
          </tr>
        </table></td>
        <td width="16"><img src="<%=basePath %>images/tab_07.gif" width="16" height="30" /></td>
      </tr>
    </table></td>
  </tr>


  <tr>
  <td>
填表时间:<input type=text name="writeTime" value="<%=writeTime %>" />&nbsp;
部门：<select name="departmentObj.departmentId">
 				<option value="0">不限制</option>
 				<%
 					for(Department departmentTemp:departmentList) {
 						String selected = "";
 						if(departmentObj!=null && departmentTemp.getDepartmentId() == departmentObj.getDepartmentId()) selected = "selected"; 			   %>
 			   <option <%=selected %> value="<%=departmentTemp.getDepartmentId() %>"><%=departmentTemp.getDepartmentName() %></option>
 			   <%
 					}
 				%>
 			</select>
请假人：<select name="userObj.user_name">
 				<option value="">不限制</option>
 				<%
 					for(UserInfo userInfoTemp:userInfoList) {
 						String selected = "";
 						if(userObj!=null && userInfoTemp.getUser_name().equals(userObj.getUser_name())) selected = "selected"; 			   %>
 			   <option <%=selected %> value="<%=userInfoTemp.getUser_name() %>"><%=userInfoTemp.getName() %></option>
 			   <%
 					}
 				%>
 			</select>
职级：<select name="positionObj.positionId">
 				<option value="0">不限制</option>
 				<%
 					for(Position positionTemp:positionList) {
 						String selected = "";
 						if(positionObj!=null && positionTemp.getPositionId() == positionObj.getPositionId()) selected = "selected"; 			   %>
 			   <option <%=selected %> value="<%=positionTemp.getPositionId() %>"><%=positionTemp.getPositionName() %></option>
 			   <%
 					}
 				%>
 			</select>
请假类别：<select name="leaveTypeObj.leaveTypeId">
 				<option value="0">不限制</option>
 				<%
 					for(LeaveType leaveTypeTemp:leaveTypeList) {
 						String selected = "";
 						if(leaveTypeObj!=null && leaveTypeTemp.getLeaveTypeId() == leaveTypeObj.getLeaveTypeId()) selected = "selected"; 			   %>
 			   <option <%=selected %> value="<%=leaveTypeTemp.getLeaveTypeId() %>"><%=leaveTypeTemp.getLeaveTypeName() %></option>
 			   <%
 					}
 				%>
 			</select>
开始时间:<input type=text readonly  name="startDate" value="<%=startDate %>" onclick="setDay(this);"/>&nbsp;
开始时间段：<select name="startDayTimeType.dayTimeTypeId">
 				<option value="0">不限制</option>
 				<%
 					for(DayTimeType dayTimeTypeTemp:dayTimeTypeList) {
 						String selected = "";
 						if(startDayTimeType!=null && dayTimeTypeTemp.getDayTimeTypeId() == startDayTimeType.getDayTimeTypeId()) selected = "selected"; 			   %>
 			   <option <%=selected %> value="<%=dayTimeTypeTemp.getDayTimeTypeId() %>"><%=dayTimeTypeTemp.getDayTimeTypeName() %></option>
 			   <%
 					}
 				%>
 			</select>
结束时间:<input type=text readonly  name="endDate" value="<%=endDate %>" onclick="setDay(this);"/>&nbsp;
结束时间段：<select name="endDayTimeType.dayTimeTypeId">
 				<option value="0">不限制</option>
 				<%
 					for(DayTimeType dayTimeTypeTemp:dayTimeTypeList) {
 						String selected = "";
 						if(endDayTimeType!=null && dayTimeTypeTemp.getDayTimeTypeId() == endDayTimeType.getDayTimeTypeId()) selected = "selected"; 			   %>
 			   <option <%=selected %> value="<%=dayTimeTypeTemp.getDayTimeTypeId() %>"><%=dayTimeTypeTemp.getDayTimeTypeName() %></option>
 			   <%
 					}
 				%>
 			</select>
前往地点:<input type=text name="place" value="<%=place %>" />&nbsp;
请假事由:<input type=text name="reason" value="<%=reason %>" />&nbsp;
    <input type=hidden name=currentPage value="1" />
    <input type=submit value="查询" onclick="QueryLeaveInfo();" />
  </td>
</tr>
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="8" background="<%=basePath %>images/tab_12.gif">&nbsp;</td>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="b5d6e6" onmouseover="changeto()"  onmouseout="changeback()">
          <tr>
          <!-- 
            <td width="3%" height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center">
              <input type="checkbox" name="checkall" onclick="checkAll();" />
            </div></td> -->
            <td width="3%" height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">序号</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">填表时间</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">部门</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">请假人</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">职级</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">请假类别</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">是否长假</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">开始时间</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">开始时间段</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">结束时间</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">结束时间段</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">请假天数</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">填写人</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">前往地点</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">请假事由</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">状态</span></div></td>
            <td width="10%" height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF" class="STYLE1"><div align="center">基本操作</div></td>
          </tr>
           <%
           		/*计算起始序号*/
            	int startIndex = (currentPage -1) * 3;
            	/*遍历记录*/
            	for(int i=0;i<leaveInfoList.size();i++) {
            		int currentIndex = startIndex + i + 1; //当前记录的序号
            		LeaveInfo leaveInfo = leaveInfoList.get(i); //获取到LeaveInfo对象
             %>
          <tr>
            <td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE1">
              <div align="center"><%=currentIndex %></div>
            </div></td>
            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=leaveInfo.getWriteTime() %></span></div></td>
            <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=leaveInfo.getDepartmentObj().getDepartmentName() %></span></div></td>
            <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=leaveInfo.getUserObj().getName() %></span></div></td>
            <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=leaveInfo.getPositionObj().getPositionName() %></span></div></td>
            <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=leaveInfo.getLeaveTypeObj().getLeaveTypeName() %></span></div></td>
            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=leaveInfo.getSfcj() %></span></div></td>
            <% DateFormat startDateSDF = new SimpleDateFormat("yyyy-MM-dd");  %>
            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=startDateSDF.format(leaveInfo.getStartDate()) %></span></div></td>
            <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=leaveInfo.getStartDayTimeType().getDayTimeTypeName() %></span></div></td>
            <% DateFormat endDateSDF = new SimpleDateFormat("yyyy-MM-dd");  %>
            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=endDateSDF.format(leaveInfo.getEndDate()) %></span></div></td>
            <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=leaveInfo.getEndDayTimeType().getDayTimeTypeName() %></span></div></td>
            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=leaveInfo.getLeaveDays() %></span></div></td>
            <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=leaveInfo.getWriteUserObj().getName() %></span></div></td>
            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=leaveInfo.getPlace() %></span></div></td>
            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=leaveInfo.getReason() %></span></div></td>
            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=leaveInfo.getState() %></span></div></td>
            <td height="20" bgcolor="#FFFFFF"><div align="center">
            <span class="STYLE4">
            <span style="cursor:hand;" onclick="location.href='<%=basePath %>LeaveInfo/LeaveInfo_ModifyLeaveInfoQuery.action?leaveId=<%=leaveInfo.getLeaveId() %>'"><a href='#'><img src="<%=basePath %>images/edt.gif" width="16" height="16"/>编辑&nbsp; &nbsp;</a></span>
            <img src="<%=basePath %>images/del.gif" width="16" height="16"/><a href="<%=basePath  %>LeaveInfo/LeaveInfo_DeleteLeaveInfo.action?leaveId=<%=leaveInfo.getLeaveId() %>" onclick="return confirm('确定删除本LeaveInfo吗?');">删除</a></span>
            </div></td>
          </tr>
          <%	} %>
        </table></td>
        <td width="8" background="images/tab_15.gif">&nbsp;</td>
      </tr>
    </table></td>
  </tr>

  <tr>
    <td height="35" background="<%=basePath %>images/tab_19.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="12" height="35"><img src="<%=basePath %>images/tab_18.gif" width="12" height="35" /></td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="STYLE4">&nbsp;&nbsp;共有<%=recordNumber %>条记录，当前第 <%=currentPage %>/<%=totalPage %> 页&nbsp;&nbsp;<span style="color:red;text-decoration:underline;cursor:hand" onclick="OutputToExcel();">导出当前记录到excel</span></td>
            <td><table border="0" align="right" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="40"><img src="<%=basePath %>images/first.gif" width="37" height="15" style="cursor:hand;" onclick="GoToPage(1,<%=totalPage %>);" /></td>
                  <td width="45"><img src="<%=basePath %>images/back.gif" width="43" height="15" style="cursor:hand;" onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);"/></td>
                  <td width="45"><img src="<%=basePath %>images/next.gif" width="43" height="15" style="cursor:hand;" onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);" /></td>
                  <td width="40"><img src="<%=basePath %>images/last.gif" width="37" height="15" style="cursor:hand;" onclick="GoToPage(<%=totalPage %>,<%=totalPage %>);"/></td>
                  <td width="100"><div align="center"><span class="STYLE1">转到第
                    <input name="pageValue" type="text" size="4" style="height:12px; width:20px; border:1px solid #999999;" />
                    页 </span></div></td>
                  <td width="40"><img src="<%=basePath %>images/go.gif" onclick="changepage(<%=totalPage %>);" width="37" height="15" /></td>
                </tr>
            </table></td>
          </tr>
        </table></td>
        <td width="16"><img src="<%=basePath %>images/tab_20.gif" width="16" height="35" /></td>
      </tr>
    </table></td>
  </tr>
</table>
  </form>
</body>
</html>

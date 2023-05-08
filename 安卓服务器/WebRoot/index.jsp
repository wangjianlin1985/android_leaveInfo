<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%> <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>基于安卓的请假管理系统-首页</title>
<link href="<%=basePath %>css/index.css" rel="stylesheet" type="text/css" />
 </head>
<body>
<div id="container">
	<div id="banner"><img src="<%=basePath %>images/logo.gif" /></div>
	<div id="globallink">
		<ul>
			<li><a href="<%=basePath %>index.jsp">首页</a></li>
			<li><a href="<%=basePath %>UserInfo/UserInfo_FrontQueryUserInfo.action" target="OfficeMain">用户信息</a></li> 
			<li><a href="<%=basePath %>Department/Department_FrontQueryDepartment.action" target="OfficeMain">部门信息</a></li> 
			<li><a href="<%=basePath %>Position/Position_FrontQueryPosition.action" target="OfficeMain">职级信息</a></li> 
			<li><a href="<%=basePath %>LeaveInfo/LeaveInfo_FrontQueryLeaveInfo.action" target="OfficeMain">请假信息</a></li> 
			<li><a href="<%=basePath %>Note/Note_FrontQueryNote.action" target="OfficeMain">节点信息</a></li> 
			<li><a href="<%=basePath %>Task/Task_FrontQueryTask.action" target="OfficeMain">任务信息</a></li> 
			<li><a href="<%=basePath %>HistoryTask/HistoryTask_FrontQueryHistoryTask.action" target="OfficeMain">历史任务</a></li> 
			<li><a href="<%=basePath %>News/News_FrontQueryNews.action" target="OfficeMain">新闻公告</a></li> 
		</ul>
		<br />
	</div> 
	<div id="main">
	 <iframe id="frame1" src="<%=basePath %>desk.jsp" name="OfficeMain" width="100%" height="100%" scrolling="yes" marginwidth=0 marginheight=0 frameborder=0 vspace=0 hspace=0 >
	 </iframe>
	</div>
	<div id="footer">
		<p>双鱼林设计 QQ:287307421或254540457 &copy;版权所有 <a href="http://www.shuangyulin.com" target="_blank">双鱼林设计网</a>&nbsp;&nbsp;<a href="<%=basePath%>login/login_view.action"><font color=red>后台登陆</font></a></p>
	</div>
</div>
</body>
</html>

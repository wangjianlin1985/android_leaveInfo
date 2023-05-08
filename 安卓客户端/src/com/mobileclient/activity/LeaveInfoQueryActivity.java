package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.LeaveInfo;
import com.mobileclient.domain.Department;
import com.mobileclient.service.DepartmentService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.domain.Position;
import com.mobileclient.service.PositionService;
import com.mobileclient.domain.LeaveType;
import com.mobileclient.service.LeaveTypeService;
import com.mobileclient.domain.DayTimeType;
import com.mobileclient.service.DayTimeTypeService;
import com.mobileclient.domain.DayTimeType;
import com.mobileclient.service.DayTimeTypeService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class LeaveInfoQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明填表时间输入框
	private EditText ET_writeTime;
	// 声明部门下拉框
	private Spinner spinner_departmentObj;
	private ArrayAdapter<String> departmentObj_adapter;
	private static  String[] departmentObj_ShowText  = null;
	private List<Department> departmentList = null; 
	/*部门信息管理业务逻辑层*/
	private DepartmentService departmentService = new DepartmentService();
	// 声明请假人下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null; 
	/*用户信息管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明职级下拉框
	private Spinner spinner_positionObj;
	private ArrayAdapter<String> positionObj_adapter;
	private static  String[] positionObj_ShowText  = null;
	private List<Position> positionList = null; 
	/*职级信息管理业务逻辑层*/
	private PositionService positionService = new PositionService();
	// 声明请假类别下拉框
	private Spinner spinner_leaveTypeObj;
	private ArrayAdapter<String> leaveTypeObj_adapter;
	private static  String[] leaveTypeObj_ShowText  = null;
	private List<LeaveType> leaveTypeList = null; 
	/*请假类型管理业务逻辑层*/
	private LeaveTypeService leaveTypeService = new LeaveTypeService();
	// 开始时间控件
	private DatePicker dp_startDate;
	private CheckBox cb_startDate;
	// 声明开始时间段下拉框
	private Spinner spinner_startDayTimeType;
	private ArrayAdapter<String> startDayTimeType_adapter;
	private static  String[] startDayTimeType_ShowText  = null;
	private List<DayTimeType> dayTimeTypeList = null; 
	/*时间段类型管理业务逻辑层*/
	private DayTimeTypeService dayTimeTypeService = new DayTimeTypeService();
	// 结束时间控件
	private DatePicker dp_endDate;
	private CheckBox cb_endDate;
	// 声明结束时间段下拉框
	private Spinner spinner_endDayTimeType;
	private ArrayAdapter<String> endDayTimeType_adapter;
	private static  String[] endDayTimeType_ShowText  = null;
	// 声明前往地点输入框
	private EditText ET_place;
	// 声明请假事由输入框
	private EditText ET_reason;
	/*查询过滤条件保存到这个对象中*/
	private LeaveInfo queryConditionLeaveInfo = new LeaveInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.leaveinfo_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置请假信息查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_writeTime = (EditText) findViewById(R.id.ET_writeTime);
		spinner_departmentObj = (Spinner) findViewById(R.id.Spinner_departmentObj);
		// 获取所有的部门信息
		try {
			departmentList = departmentService.QueryDepartment(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int departmentCount = departmentList.size();
		departmentObj_ShowText = new String[departmentCount+1];
		departmentObj_ShowText[0] = "不限制";
		for(int i=1;i<=departmentCount;i++) { 
			departmentObj_ShowText[i] = departmentList.get(i-1).getDepartmentName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		departmentObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, departmentObj_ShowText);
		// 设置部门下拉列表的风格
		departmentObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_departmentObj.setAdapter(departmentObj_adapter);
		// 添加事件Spinner事件监听
		spinner_departmentObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionLeaveInfo.setDepartmentObj(departmentList.get(arg2-1).getDepartmentId()); 
				else
					queryConditionLeaveInfo.setDepartmentObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_departmentObj.setVisibility(View.VISIBLE);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的用户信息
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount+1];
		userObj_ShowText[0] = "不限制";
		for(int i=1;i<=userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i-1).getName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// 设置请假人下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionLeaveInfo.setUserObj(userInfoList.get(arg2-1).getUser_name()); 
				else
					queryConditionLeaveInfo.setUserObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		spinner_positionObj = (Spinner) findViewById(R.id.Spinner_positionObj);
		// 获取所有的职级信息
		try {
			positionList = positionService.QueryPosition(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int positionCount = positionList.size();
		positionObj_ShowText = new String[positionCount+1];
		positionObj_ShowText[0] = "不限制";
		for(int i=1;i<=positionCount;i++) { 
			positionObj_ShowText[i] = positionList.get(i-1).getPositionName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		positionObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, positionObj_ShowText);
		// 设置职级下拉列表的风格
		positionObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_positionObj.setAdapter(positionObj_adapter);
		// 添加事件Spinner事件监听
		spinner_positionObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionLeaveInfo.setPositionObj(positionList.get(arg2-1).getPositionId()); 
				else
					queryConditionLeaveInfo.setPositionObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_positionObj.setVisibility(View.VISIBLE);
		spinner_leaveTypeObj = (Spinner) findViewById(R.id.Spinner_leaveTypeObj);
		// 获取所有的请假类型
		try {
			leaveTypeList = leaveTypeService.QueryLeaveType(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int leaveTypeCount = leaveTypeList.size();
		leaveTypeObj_ShowText = new String[leaveTypeCount+1];
		leaveTypeObj_ShowText[0] = "不限制";
		for(int i=1;i<=leaveTypeCount;i++) { 
			leaveTypeObj_ShowText[i] = leaveTypeList.get(i-1).getLeaveTypeName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		leaveTypeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, leaveTypeObj_ShowText);
		// 设置请假类别下拉列表的风格
		leaveTypeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_leaveTypeObj.setAdapter(leaveTypeObj_adapter);
		// 添加事件Spinner事件监听
		spinner_leaveTypeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionLeaveInfo.setLeaveTypeObj(leaveTypeList.get(arg2-1).getLeaveTypeId()); 
				else
					queryConditionLeaveInfo.setLeaveTypeObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_leaveTypeObj.setVisibility(View.VISIBLE);
		dp_startDate = (DatePicker) findViewById(R.id.dp_startDate);
		cb_startDate = (CheckBox) findViewById(R.id.cb_startDate);
		spinner_startDayTimeType = (Spinner) findViewById(R.id.Spinner_startDayTimeType);
		// 获取所有的时间段类型
		try {
			dayTimeTypeList = dayTimeTypeService.QueryDayTimeType(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int dayTimeTypeCount = dayTimeTypeList.size();
		startDayTimeType_ShowText = new String[dayTimeTypeCount+1];
		startDayTimeType_ShowText[0] = "不限制";
		for(int i=1;i<=dayTimeTypeCount;i++) { 
			startDayTimeType_ShowText[i] = dayTimeTypeList.get(i-1).getDayTimeTypeName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		startDayTimeType_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, startDayTimeType_ShowText);
		// 设置开始时间段下拉列表的风格
		startDayTimeType_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_startDayTimeType.setAdapter(startDayTimeType_adapter);
		// 添加事件Spinner事件监听
		spinner_startDayTimeType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionLeaveInfo.setStartDayTimeType(dayTimeTypeList.get(arg2-1).getDayTimeTypeId()); 
				else
					queryConditionLeaveInfo.setStartDayTimeType(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_startDayTimeType.setVisibility(View.VISIBLE);
		dp_endDate = (DatePicker) findViewById(R.id.dp_endDate);
		cb_endDate = (CheckBox) findViewById(R.id.cb_endDate);
		spinner_endDayTimeType = (Spinner) findViewById(R.id.Spinner_endDayTimeType);
		endDayTimeType_ShowText = new String[dayTimeTypeCount+1];
		endDayTimeType_ShowText[0] = "不限制";
		for(int i=1;i<=dayTimeTypeCount;i++) { 
			endDayTimeType_ShowText[i] = dayTimeTypeList.get(i-1).getDayTimeTypeName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		endDayTimeType_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, endDayTimeType_ShowText);
		// 设置结束时间段下拉列表的风格
		endDayTimeType_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_endDayTimeType.setAdapter(endDayTimeType_adapter);
		// 添加事件Spinner事件监听
		spinner_endDayTimeType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionLeaveInfo.setEndDayTimeType(dayTimeTypeList.get(arg2-1).getDayTimeTypeId()); 
				else
					queryConditionLeaveInfo.setEndDayTimeType(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_endDayTimeType.setVisibility(View.VISIBLE);
		ET_place = (EditText) findViewById(R.id.ET_place);
		ET_reason = (EditText) findViewById(R.id.ET_reason);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionLeaveInfo.setWriteTime(ET_writeTime.getText().toString());
					if(cb_startDate.isChecked()) {
						/*获取开始时间*/
						Date startDate = new Date(dp_startDate.getYear()-1900,dp_startDate.getMonth(),dp_startDate.getDayOfMonth());
						queryConditionLeaveInfo.setStartDate(new Timestamp(startDate.getTime()));
					} else {
						queryConditionLeaveInfo.setStartDate(null);
					} 
					if(cb_endDate.isChecked()) {
						/*获取结束时间*/
						Date endDate = new Date(dp_endDate.getYear()-1900,dp_endDate.getMonth(),dp_endDate.getDayOfMonth());
						queryConditionLeaveInfo.setEndDate(new Timestamp(endDate.getTime()));
					} else {
						queryConditionLeaveInfo.setEndDate(null);
					} 
					queryConditionLeaveInfo.setPlace(ET_place.getText().toString());
					queryConditionLeaveInfo.setReason(ET_reason.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionLeaveInfo", queryConditionLeaveInfo);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}

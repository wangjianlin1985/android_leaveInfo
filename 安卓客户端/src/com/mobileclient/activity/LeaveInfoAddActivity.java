package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.LeaveInfo;
import com.mobileclient.service.LeaveInfoService;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class LeaveInfoAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明填表时间输入框
	private EditText ET_writeTime;
	// 声明部门下拉框
	private Spinner spinner_departmentObj;
	private ArrayAdapter<String> departmentObj_adapter;
	private static  String[] departmentObj_ShowText  = null;
	private List<Department> departmentList = null;
	/*部门管理业务逻辑层*/
	private DepartmentService departmentService = new DepartmentService();
	// 声明请假人下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*请假人管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明职级下拉框
	private Spinner spinner_positionObj;
	private ArrayAdapter<String> positionObj_adapter;
	private static  String[] positionObj_ShowText  = null;
	private List<Position> positionList = null;
	/*职级管理业务逻辑层*/
	private PositionService positionService = new PositionService();
	// 声明请假类别下拉框
	private Spinner spinner_leaveTypeObj;
	private ArrayAdapter<String> leaveTypeObj_adapter;
	private static  String[] leaveTypeObj_ShowText  = null;
	private List<LeaveType> leaveTypeList = null;
	/*请假类别管理业务逻辑层*/
	private LeaveTypeService leaveTypeService = new LeaveTypeService();
	// 声明是否长假输入框
	private EditText ET_sfcj;
	// 出版开始时间控件
	private DatePicker dp_startDate;
	// 声明开始时间段下拉框
	private Spinner spinner_startDayTimeType;
	private ArrayAdapter<String> startDayTimeType_adapter;
	private static  String[] startDayTimeType_ShowText  = null;
	private List<DayTimeType> dayTimeTypeList = null;
	/*开始时间段管理业务逻辑层*/
	private DayTimeTypeService dayTimeTypeService = new DayTimeTypeService();
	// 出版结束时间控件
	private DatePicker dp_endDate;
	// 声明结束时间段下拉框
	private Spinner spinner_endDayTimeType;
	private ArrayAdapter<String> endDayTimeType_adapter;
	private static  String[] endDayTimeType_ShowText  = null;
	// 声明请假天数输入框
	private EditText ET_leaveDays;
	// 声明填写人下拉框
	private Spinner spinner_writeUserObj;
	private ArrayAdapter<String> writeUserObj_adapter;
	private static  String[] writeUserObj_ShowText  = null;
	// 声明前往地点输入框
	private EditText ET_place;
	// 声明请假事由输入框
	private EditText ET_reason;
	// 声明备注输入框
	private EditText ET_memo;
	// 声明状态输入框
	private EditText ET_state;
	protected String carmera_path;
	/*要保存的请假信息信息*/
	LeaveInfo leaveInfo = new LeaveInfo();
	/*请假信息管理业务逻辑层*/
	private LeaveInfoService leaveInfoService = new LeaveInfoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.leaveinfo_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加请假信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_writeTime = (EditText) findViewById(R.id.ET_writeTime);
		spinner_departmentObj = (Spinner) findViewById(R.id.Spinner_departmentObj);
		// 获取所有的部门
		try {
			departmentList = departmentService.QueryDepartment(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int departmentCount = departmentList.size();
		departmentObj_ShowText = new String[departmentCount];
		for(int i=0;i<departmentCount;i++) { 
			departmentObj_ShowText[i] = departmentList.get(i).getDepartmentName();
		}
		// 将可选内容与ArrayAdapter连接起来
		departmentObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, departmentObj_ShowText);
		// 设置下拉列表的风格
		departmentObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_departmentObj.setAdapter(departmentObj_adapter);
		// 添加事件Spinner事件监听
		spinner_departmentObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				leaveInfo.setDepartmentObj(departmentList.get(arg2).getDepartmentId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_departmentObj.setVisibility(View.VISIBLE);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的请假人
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount];
		for(int i=0;i<userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i).getName();
		}
		// 将可选内容与ArrayAdapter连接起来
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// 设置下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				leaveInfo.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		spinner_positionObj = (Spinner) findViewById(R.id.Spinner_positionObj);
		// 获取所有的职级
		try {
			positionList = positionService.QueryPosition(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int positionCount = positionList.size();
		positionObj_ShowText = new String[positionCount];
		for(int i=0;i<positionCount;i++) { 
			positionObj_ShowText[i] = positionList.get(i).getPositionName();
		}
		// 将可选内容与ArrayAdapter连接起来
		positionObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, positionObj_ShowText);
		// 设置下拉列表的风格
		positionObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_positionObj.setAdapter(positionObj_adapter);
		// 添加事件Spinner事件监听
		spinner_positionObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				leaveInfo.setPositionObj(positionList.get(arg2).getPositionId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_positionObj.setVisibility(View.VISIBLE);
		spinner_leaveTypeObj = (Spinner) findViewById(R.id.Spinner_leaveTypeObj);
		// 获取所有的请假类别
		try {
			leaveTypeList = leaveTypeService.QueryLeaveType(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int leaveTypeCount = leaveTypeList.size();
		leaveTypeObj_ShowText = new String[leaveTypeCount];
		for(int i=0;i<leaveTypeCount;i++) { 
			leaveTypeObj_ShowText[i] = leaveTypeList.get(i).getLeaveTypeName();
		}
		// 将可选内容与ArrayAdapter连接起来
		leaveTypeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, leaveTypeObj_ShowText);
		// 设置下拉列表的风格
		leaveTypeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_leaveTypeObj.setAdapter(leaveTypeObj_adapter);
		// 添加事件Spinner事件监听
		spinner_leaveTypeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				leaveInfo.setLeaveTypeObj(leaveTypeList.get(arg2).getLeaveTypeId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_leaveTypeObj.setVisibility(View.VISIBLE);
		ET_sfcj = (EditText) findViewById(R.id.ET_sfcj);
		dp_startDate = (DatePicker)this.findViewById(R.id.dp_startDate);
		spinner_startDayTimeType = (Spinner) findViewById(R.id.Spinner_startDayTimeType);
		// 获取所有的开始时间段
		try {
			dayTimeTypeList = dayTimeTypeService.QueryDayTimeType(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int dayTimeTypeCount = dayTimeTypeList.size();
		startDayTimeType_ShowText = new String[dayTimeTypeCount];
		for(int i=0;i<dayTimeTypeCount;i++) { 
			startDayTimeType_ShowText[i] = dayTimeTypeList.get(i).getDayTimeTypeName();
		}
		// 将可选内容与ArrayAdapter连接起来
		startDayTimeType_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, startDayTimeType_ShowText);
		// 设置下拉列表的风格
		startDayTimeType_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_startDayTimeType.setAdapter(startDayTimeType_adapter);
		// 添加事件Spinner事件监听
		spinner_startDayTimeType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				leaveInfo.setStartDayTimeType(dayTimeTypeList.get(arg2).getDayTimeTypeId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_startDayTimeType.setVisibility(View.VISIBLE);
		dp_endDate = (DatePicker)this.findViewById(R.id.dp_endDate);
		spinner_endDayTimeType = (Spinner) findViewById(R.id.Spinner_endDayTimeType);
		endDayTimeType_ShowText = new String[dayTimeTypeCount];
		for(int i=0;i<dayTimeTypeCount;i++) { 
			endDayTimeType_ShowText[i] = dayTimeTypeList.get(i).getDayTimeTypeName();
		}
		// 将可选内容与ArrayAdapter连接起来
		endDayTimeType_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, endDayTimeType_ShowText);
		// 设置下拉列表的风格
		endDayTimeType_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_endDayTimeType.setAdapter(endDayTimeType_adapter);
		// 添加事件Spinner事件监听
		spinner_endDayTimeType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				leaveInfo.setEndDayTimeType(dayTimeTypeList.get(arg2).getDayTimeTypeId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_endDayTimeType.setVisibility(View.VISIBLE);
		ET_leaveDays = (EditText) findViewById(R.id.ET_leaveDays);
		spinner_writeUserObj = (Spinner) findViewById(R.id.Spinner_writeUserObj);
		writeUserObj_ShowText = new String[userInfoCount];
		for(int i=0;i<userInfoCount;i++) { 
			writeUserObj_ShowText[i] = userInfoList.get(i).getName();
		}
		// 将可选内容与ArrayAdapter连接起来
		writeUserObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, writeUserObj_ShowText);
		// 设置下拉列表的风格
		writeUserObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_writeUserObj.setAdapter(writeUserObj_adapter);
		// 添加事件Spinner事件监听
		spinner_writeUserObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				leaveInfo.setWriteUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_writeUserObj.setVisibility(View.VISIBLE);
		ET_place = (EditText) findViewById(R.id.ET_place);
		ET_reason = (EditText) findViewById(R.id.ET_reason);
		ET_memo = (EditText) findViewById(R.id.ET_memo);
		ET_state = (EditText) findViewById(R.id.ET_state);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加请假信息按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取填表时间*/ 
					if(ET_writeTime.getText().toString().equals("")) {
						Toast.makeText(LeaveInfoAddActivity.this, "填表时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_writeTime.setFocusable(true);
						ET_writeTime.requestFocus();
						return;	
					}
					leaveInfo.setWriteTime(ET_writeTime.getText().toString());
					/*验证获取是否长假*/ 
					if(ET_sfcj.getText().toString().equals("")) {
						Toast.makeText(LeaveInfoAddActivity.this, "是否长假输入不能为空!", Toast.LENGTH_LONG).show();
						ET_sfcj.setFocusable(true);
						ET_sfcj.requestFocus();
						return;	
					}
					leaveInfo.setSfcj(ET_sfcj.getText().toString());
					/*获取开始时间*/
					Date startDate = new Date(dp_startDate.getYear()-1900,dp_startDate.getMonth(),dp_startDate.getDayOfMonth());
					leaveInfo.setStartDate(new Timestamp(startDate.getTime()));
					/*获取结束时间*/
					Date endDate = new Date(dp_endDate.getYear()-1900,dp_endDate.getMonth(),dp_endDate.getDayOfMonth());
					leaveInfo.setEndDate(new Timestamp(endDate.getTime()));
					/*验证获取请假天数*/ 
					if(ET_leaveDays.getText().toString().equals("")) {
						Toast.makeText(LeaveInfoAddActivity.this, "请假天数输入不能为空!", Toast.LENGTH_LONG).show();
						ET_leaveDays.setFocusable(true);
						ET_leaveDays.requestFocus();
						return;	
					}
					leaveInfo.setLeaveDays(Float.parseFloat(ET_leaveDays.getText().toString()));
					/*验证获取前往地点*/ 
					if(ET_place.getText().toString().equals("")) {
						Toast.makeText(LeaveInfoAddActivity.this, "前往地点输入不能为空!", Toast.LENGTH_LONG).show();
						ET_place.setFocusable(true);
						ET_place.requestFocus();
						return;	
					}
					leaveInfo.setPlace(ET_place.getText().toString());
					/*验证获取请假事由*/ 
					if(ET_reason.getText().toString().equals("")) {
						Toast.makeText(LeaveInfoAddActivity.this, "请假事由输入不能为空!", Toast.LENGTH_LONG).show();
						ET_reason.setFocusable(true);
						ET_reason.requestFocus();
						return;	
					}
					leaveInfo.setReason(ET_reason.getText().toString());
					/*验证获取备注*/ 
					if(ET_memo.getText().toString().equals("")) {
						Toast.makeText(LeaveInfoAddActivity.this, "备注输入不能为空!", Toast.LENGTH_LONG).show();
						ET_memo.setFocusable(true);
						ET_memo.requestFocus();
						return;	
					}
					leaveInfo.setMemo(ET_memo.getText().toString());
					/*验证获取状态*/ 
					if(ET_state.getText().toString().equals("")) {
						Toast.makeText(LeaveInfoAddActivity.this, "状态输入不能为空!", Toast.LENGTH_LONG).show();
						ET_state.setFocusable(true);
						ET_state.requestFocus();
						return;	
					}
					leaveInfo.setState(Integer.parseInt(ET_state.getText().toString()));
					/*调用业务逻辑层上传请假信息信息*/
					LeaveInfoAddActivity.this.setTitle("正在上传请假信息信息，稍等...");
					String result = leaveInfoService.AddLeaveInfo(leaveInfo);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}

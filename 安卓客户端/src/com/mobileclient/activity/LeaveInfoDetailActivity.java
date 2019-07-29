package com.mobileclient.activity;

import java.util.Date;
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
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class LeaveInfoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明请假记录ID控件
	private TextView TV_leaveId;
	// 声明填表时间控件
	private TextView TV_writeTime;
	// 声明部门控件
	private TextView TV_departmentObj;
	// 声明请假人控件
	private TextView TV_userObj;
	// 声明职级控件
	private TextView TV_positionObj;
	// 声明请假类别控件
	private TextView TV_leaveTypeObj;
	// 声明是否长假控件
	private TextView TV_sfcj;
	// 声明开始时间控件
	private TextView TV_startDate;
	// 声明开始时间段控件
	private TextView TV_startDayTimeType;
	// 声明结束时间控件
	private TextView TV_endDate;
	// 声明结束时间段控件
	private TextView TV_endDayTimeType;
	// 声明请假天数控件
	private TextView TV_leaveDays;
	// 声明填写人控件
	private TextView TV_writeUserObj;
	// 声明前往地点控件
	private TextView TV_place;
	// 声明请假事由控件
	private TextView TV_reason;
	// 声明备注控件
	private TextView TV_memo;
	// 声明状态控件
	private TextView TV_state;
	/* 要保存的请假信息信息 */
	LeaveInfo leaveInfo = new LeaveInfo(); 
	/* 请假信息管理业务逻辑层 */
	private LeaveInfoService leaveInfoService = new LeaveInfoService();
	private DepartmentService departmentService = new DepartmentService();
	private UserInfoService userInfoService = new UserInfoService();
	private PositionService positionService = new PositionService();
	private LeaveTypeService leaveTypeService = new LeaveTypeService();
	private DayTimeTypeService dayTimeTypeService = new DayTimeTypeService();
	private int leaveId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.leaveinfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看请假信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_leaveId = (TextView) findViewById(R.id.TV_leaveId);
		TV_writeTime = (TextView) findViewById(R.id.TV_writeTime);
		TV_departmentObj = (TextView) findViewById(R.id.TV_departmentObj);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_positionObj = (TextView) findViewById(R.id.TV_positionObj);
		TV_leaveTypeObj = (TextView) findViewById(R.id.TV_leaveTypeObj);
		TV_sfcj = (TextView) findViewById(R.id.TV_sfcj);
		TV_startDate = (TextView) findViewById(R.id.TV_startDate);
		TV_startDayTimeType = (TextView) findViewById(R.id.TV_startDayTimeType);
		TV_endDate = (TextView) findViewById(R.id.TV_endDate);
		TV_endDayTimeType = (TextView) findViewById(R.id.TV_endDayTimeType);
		TV_leaveDays = (TextView) findViewById(R.id.TV_leaveDays);
		TV_writeUserObj = (TextView) findViewById(R.id.TV_writeUserObj);
		TV_place = (TextView) findViewById(R.id.TV_place);
		TV_reason = (TextView) findViewById(R.id.TV_reason);
		TV_memo = (TextView) findViewById(R.id.TV_memo);
		TV_state = (TextView) findViewById(R.id.TV_state);
		Bundle extras = this.getIntent().getExtras();
		leaveId = extras.getInt("leaveId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				LeaveInfoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    leaveInfo = leaveInfoService.GetLeaveInfo(leaveId); 
		this.TV_leaveId.setText(leaveInfo.getLeaveId() + "");
		this.TV_writeTime.setText(leaveInfo.getWriteTime());
		Department departmentObj = departmentService.GetDepartment(leaveInfo.getDepartmentObj());
		this.TV_departmentObj.setText(departmentObj.getDepartmentName());
		UserInfo userObj = userInfoService.GetUserInfo(leaveInfo.getUserObj());
		this.TV_userObj.setText(userObj.getName());
		Position positionObj = positionService.GetPosition(leaveInfo.getPositionObj());
		this.TV_positionObj.setText(positionObj.getPositionName());
		LeaveType leaveTypeObj = leaveTypeService.GetLeaveType(leaveInfo.getLeaveTypeObj());
		this.TV_leaveTypeObj.setText(leaveTypeObj.getLeaveTypeName());
		this.TV_sfcj.setText(leaveInfo.getSfcj());
		Date startDate = new Date(leaveInfo.getStartDate().getTime());
		String startDateStr = (startDate.getYear() + 1900) + "-" + (startDate.getMonth()+1) + "-" + startDate.getDate();
		this.TV_startDate.setText(startDateStr);
		DayTimeType startDayTimeType = dayTimeTypeService.GetDayTimeType(leaveInfo.getStartDayTimeType());
		this.TV_startDayTimeType.setText(startDayTimeType.getDayTimeTypeName());
		Date endDate = new Date(leaveInfo.getEndDate().getTime());
		String endDateStr = (endDate.getYear() + 1900) + "-" + (endDate.getMonth()+1) + "-" + endDate.getDate();
		this.TV_endDate.setText(endDateStr);
		DayTimeType endDayTimeType = dayTimeTypeService.GetDayTimeType(leaveInfo.getEndDayTimeType());
		this.TV_endDayTimeType.setText(endDayTimeType.getDayTimeTypeName());
		this.TV_leaveDays.setText(leaveInfo.getLeaveDays() + "");
		UserInfo writeUserObj = userInfoService.GetUserInfo(leaveInfo.getWriteUserObj());
		this.TV_writeUserObj.setText(writeUserObj.getName());
		this.TV_place.setText(leaveInfo.getPlace());
		this.TV_reason.setText(leaveInfo.getReason());
		this.TV_memo.setText(leaveInfo.getMemo());
		this.TV_state.setText(leaveInfo.getState() + "");
	} 
}

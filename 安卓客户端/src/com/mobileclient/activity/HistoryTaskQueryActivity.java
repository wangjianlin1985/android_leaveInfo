package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.HistoryTask;
import com.mobileclient.domain.LeaveInfo;
import com.mobileclient.service.LeaveInfoService;
import com.mobileclient.domain.Note;
import com.mobileclient.service.NoteService;
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
public class HistoryTaskQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明请假记录ID下拉框
	private Spinner spinner_leaveObj;
	private ArrayAdapter<String> leaveObj_adapter;
	private static  String[] leaveObj_ShowText  = null;
	private List<LeaveInfo> leaveInfoList = null; 
	/*请假信息管理业务逻辑层*/
	private LeaveInfoService leaveInfoService = new LeaveInfoService();
	// 声明节点下拉框
	private Spinner spinner_noteObj;
	private ArrayAdapter<String> noteObj_adapter;
	private static  String[] noteObj_ShowText  = null;
	private List<Note> noteList = null; 
	/*节点信息管理业务逻辑层*/
	private NoteService noteService = new NoteService();
	// 声明处理人下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null; 
	/*用户信息管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明创建时间输入框
	private EditText ET_taskTime;
	/*查询过滤条件保存到这个对象中*/
	private HistoryTask queryConditionHistoryTask = new HistoryTask();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.historytask_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置历史任务查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_leaveObj = (Spinner) findViewById(R.id.Spinner_leaveObj);
		// 获取所有的请假信息
		try {
			leaveInfoList = leaveInfoService.QueryLeaveInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int leaveInfoCount = leaveInfoList.size();
		leaveObj_ShowText = new String[leaveInfoCount+1];
		leaveObj_ShowText[0] = "不限制";
		for(int i=1;i<=leaveInfoCount;i++) { 
			leaveObj_ShowText[i] = leaveInfoList.get(i-1).getLeaveId();
		} 
		// 将可选内容与ArrayAdapter连接起来
		leaveObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, leaveObj_ShowText);
		// 设置请假记录ID下拉列表的风格
		leaveObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_leaveObj.setAdapter(leaveObj_adapter);
		// 添加事件Spinner事件监听
		spinner_leaveObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionHistoryTask.setLeaveObj(leaveInfoList.get(arg2-1).getLeaveId()); 
				else
					queryConditionHistoryTask.setLeaveObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_leaveObj.setVisibility(View.VISIBLE);
		spinner_noteObj = (Spinner) findViewById(R.id.Spinner_noteObj);
		// 获取所有的节点信息
		try {
			noteList = noteService.QueryNote(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int noteCount = noteList.size();
		noteObj_ShowText = new String[noteCount+1];
		noteObj_ShowText[0] = "不限制";
		for(int i=1;i<=noteCount;i++) { 
			noteObj_ShowText[i] = noteList.get(i-1).getNoteName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		noteObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, noteObj_ShowText);
		// 设置节点下拉列表的风格
		noteObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_noteObj.setAdapter(noteObj_adapter);
		// 添加事件Spinner事件监听
		spinner_noteObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionHistoryTask.setNoteObj(noteList.get(arg2-1).getNoteId()); 
				else
					queryConditionHistoryTask.setNoteObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_noteObj.setVisibility(View.VISIBLE);
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
		// 设置处理人下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionHistoryTask.setUserObj(userInfoList.get(arg2-1).getUser_name()); 
				else
					queryConditionHistoryTask.setUserObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_taskTime = (EditText) findViewById(R.id.ET_taskTime);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionHistoryTask.setTaskTime(ET_taskTime.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionHistoryTask", queryConditionHistoryTask);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}

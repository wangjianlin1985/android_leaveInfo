package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Task;
import com.mobileclient.service.TaskService;
import com.mobileclient.domain.LeaveInfo;
import com.mobileclient.service.LeaveInfoService;
import com.mobileclient.domain.Note;
import com.mobileclient.service.NoteService;
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
public class TaskAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明请假ID下拉框
	private Spinner spinner_leaveInfoObj;
	private ArrayAdapter<String> leaveInfoObj_adapter;
	private static  String[] leaveInfoObj_ShowText  = null;
	private List<LeaveInfo> leaveInfoList = null;
	/*请假ID管理业务逻辑层*/
	private LeaveInfoService leaveInfoService = new LeaveInfoService();
	// 声明当前节点下拉框
	private Spinner spinner_noteObj;
	private ArrayAdapter<String> noteObj_adapter;
	private static  String[] noteObj_ShowText  = null;
	private List<Note> noteList = null;
	/*当前节点管理业务逻辑层*/
	private NoteService noteService = new NoteService();
	// 声明当前状态输入框
	private EditText ET_state;
	// 声明当前处理人下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*当前处理人管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明创建时间输入框
	private EditText ET_taskTime;
	protected String carmera_path;
	/*要保存的任务信息信息*/
	Task task = new Task();
	/*任务信息管理业务逻辑层*/
	private TaskService taskService = new TaskService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.task_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加任务信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		spinner_leaveInfoObj = (Spinner) findViewById(R.id.Spinner_leaveInfoObj);
		// 获取所有的请假ID
		try {
			leaveInfoList = leaveInfoService.QueryLeaveInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int leaveInfoCount = leaveInfoList.size();
		leaveInfoObj_ShowText = new String[leaveInfoCount];
		for(int i=0;i<leaveInfoCount;i++) { 
			leaveInfoObj_ShowText[i] = leaveInfoList.get(i).getLeaveId();
		}
		// 将可选内容与ArrayAdapter连接起来
		leaveInfoObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, leaveInfoObj_ShowText);
		// 设置下拉列表的风格
		leaveInfoObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_leaveInfoObj.setAdapter(leaveInfoObj_adapter);
		// 添加事件Spinner事件监听
		spinner_leaveInfoObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				task.setLeaveInfoObj(leaveInfoList.get(arg2).getLeaveId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_leaveInfoObj.setVisibility(View.VISIBLE);
		spinner_noteObj = (Spinner) findViewById(R.id.Spinner_noteObj);
		// 获取所有的当前节点
		try {
			noteList = noteService.QueryNote(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int noteCount = noteList.size();
		noteObj_ShowText = new String[noteCount];
		for(int i=0;i<noteCount;i++) { 
			noteObj_ShowText[i] = noteList.get(i).getNoteName();
		}
		// 将可选内容与ArrayAdapter连接起来
		noteObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, noteObj_ShowText);
		// 设置下拉列表的风格
		noteObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_noteObj.setAdapter(noteObj_adapter);
		// 添加事件Spinner事件监听
		spinner_noteObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				task.setNoteObj(noteList.get(arg2).getNoteId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_noteObj.setVisibility(View.VISIBLE);
		ET_state = (EditText) findViewById(R.id.ET_state);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的当前处理人
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
				task.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_taskTime = (EditText) findViewById(R.id.ET_taskTime);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加任务信息按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取当前状态*/ 
					if(ET_state.getText().toString().equals("")) {
						Toast.makeText(TaskAddActivity.this, "当前状态输入不能为空!", Toast.LENGTH_LONG).show();
						ET_state.setFocusable(true);
						ET_state.requestFocus();
						return;	
					}
					task.setState(Integer.parseInt(ET_state.getText().toString()));
					/*验证获取创建时间*/ 
					if(ET_taskTime.getText().toString().equals("")) {
						Toast.makeText(TaskAddActivity.this, "创建时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_taskTime.setFocusable(true);
						ET_taskTime.requestFocus();
						return;	
					}
					task.setTaskTime(ET_taskTime.getText().toString());
					/*调用业务逻辑层上传任务信息信息*/
					TaskAddActivity.this.setTitle("正在上传任务信息信息，稍等...");
					String result = taskService.AddTask(task);
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

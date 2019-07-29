package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.HistoryTask;
import com.mobileclient.service.HistoryTaskService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class HistoryTaskEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明任务历史记录IdTextView
	private TextView TV_historyTaskId;
	// 声明请假记录ID下拉框
	private Spinner spinner_leaveObj;
	private ArrayAdapter<String> leaveObj_adapter;
	private static  String[] leaveObj_ShowText  = null;
	private List<LeaveInfo> leaveInfoList = null;
	/*请假记录ID管理业务逻辑层*/
	private LeaveInfoService leaveInfoService = new LeaveInfoService();
	// 声明节点下拉框
	private Spinner spinner_noteObj;
	private ArrayAdapter<String> noteObj_adapter;
	private static  String[] noteObj_ShowText  = null;
	private List<Note> noteList = null;
	/*节点管理业务逻辑层*/
	private NoteService noteService = new NoteService();
	// 声明审批意见输入框
	private EditText ET_checkSuggest;
	// 声明处理人下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*处理人管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明创建时间输入框
	private EditText ET_taskTime;
	// 声明审批状态输入框
	private EditText ET_checkState;
	protected String carmera_path;
	/*要保存的历史任务信息*/
	HistoryTask historyTask = new HistoryTask();
	/*历史任务管理业务逻辑层*/
	private HistoryTaskService historyTaskService = new HistoryTaskService();

	private int historyTaskId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.historytask_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑历史任务信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_historyTaskId = (TextView) findViewById(R.id.TV_historyTaskId);
		spinner_leaveObj = (Spinner) findViewById(R.id.Spinner_leaveObj);
		// 获取所有的请假记录ID
		try {
			leaveInfoList = leaveInfoService.QueryLeaveInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int leaveInfoCount = leaveInfoList.size();
		leaveObj_ShowText = new String[leaveInfoCount];
		for(int i=0;i<leaveInfoCount;i++) { 
			leaveObj_ShowText[i] = leaveInfoList.get(i).getLeaveId();
		}
		// 将可选内容与ArrayAdapter连接起来
		leaveObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, leaveObj_ShowText);
		// 设置图书类别下拉列表的风格
		leaveObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_leaveObj.setAdapter(leaveObj_adapter);
		// 添加事件Spinner事件监听
		spinner_leaveObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				historyTask.setLeaveObj(leaveInfoList.get(arg2).getLeaveId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_leaveObj.setVisibility(View.VISIBLE);
		spinner_noteObj = (Spinner) findViewById(R.id.Spinner_noteObj);
		// 获取所有的节点
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
		// 设置图书类别下拉列表的风格
		noteObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_noteObj.setAdapter(noteObj_adapter);
		// 添加事件Spinner事件监听
		spinner_noteObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				historyTask.setNoteObj(noteList.get(arg2).getNoteId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_noteObj.setVisibility(View.VISIBLE);
		ET_checkSuggest = (EditText) findViewById(R.id.ET_checkSuggest);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的处理人
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
		// 设置图书类别下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				historyTask.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_taskTime = (EditText) findViewById(R.id.ET_taskTime);
		ET_checkState = (EditText) findViewById(R.id.ET_checkState);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		historyTaskId = extras.getInt("historyTaskId");
		/*单击修改历史任务按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取审批意见*/ 
					if(ET_checkSuggest.getText().toString().equals("")) {
						Toast.makeText(HistoryTaskEditActivity.this, "审批意见输入不能为空!", Toast.LENGTH_LONG).show();
						ET_checkSuggest.setFocusable(true);
						ET_checkSuggest.requestFocus();
						return;	
					}
					historyTask.setCheckSuggest(ET_checkSuggest.getText().toString());
					/*验证获取创建时间*/ 
					if(ET_taskTime.getText().toString().equals("")) {
						Toast.makeText(HistoryTaskEditActivity.this, "创建时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_taskTime.setFocusable(true);
						ET_taskTime.requestFocus();
						return;	
					}
					historyTask.setTaskTime(ET_taskTime.getText().toString());
					/*验证获取审批状态*/ 
					if(ET_checkState.getText().toString().equals("")) {
						Toast.makeText(HistoryTaskEditActivity.this, "审批状态输入不能为空!", Toast.LENGTH_LONG).show();
						ET_checkState.setFocusable(true);
						ET_checkState.requestFocus();
						return;	
					}
					historyTask.setCheckState(Integer.parseInt(ET_checkState.getText().toString()));
					/*调用业务逻辑层上传历史任务信息*/
					HistoryTaskEditActivity.this.setTitle("正在更新历史任务信息，稍等...");
					String result = historyTaskService.UpdateHistoryTask(historyTask);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    historyTask = historyTaskService.GetHistoryTask(historyTaskId);
		this.TV_historyTaskId.setText(historyTaskId+"");
		for (int i = 0; i < leaveInfoList.size(); i++) {
			if (historyTask.getLeaveObj() == leaveInfoList.get(i).getLeaveId()) {
				this.spinner_leaveObj.setSelection(i);
				break;
			}
		}
		for (int i = 0; i < noteList.size(); i++) {
			if (historyTask.getNoteObj() == noteList.get(i).getNoteId()) {
				this.spinner_noteObj.setSelection(i);
				break;
			}
		}
		this.ET_checkSuggest.setText(historyTask.getCheckSuggest());
		for (int i = 0; i < userInfoList.size(); i++) {
			if (historyTask.getUserObj().equals(userInfoList.get(i).getUser_name())) {
				this.spinner_userObj.setSelection(i);
				break;
			}
		}
		this.ET_taskTime.setText(historyTask.getTaskTime());
		this.ET_checkState.setText(historyTask.getCheckState() + "");
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}

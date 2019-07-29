package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Task;
import com.mobileclient.service.TaskService;
import com.mobileclient.domain.LeaveInfo;
import com.mobileclient.service.LeaveInfoService;
import com.mobileclient.domain.Note;
import com.mobileclient.service.NoteService;
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
public class TaskDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明任务ID控件
	private TextView TV_taskId;
	// 声明请假ID控件
	private TextView TV_leaveInfoObj;
	// 声明当前节点控件
	private TextView TV_noteObj;
	// 声明当前状态控件
	private TextView TV_state;
	// 声明当前处理人控件
	private TextView TV_userObj;
	// 声明创建时间控件
	private TextView TV_taskTime;
	/* 要保存的任务信息信息 */
	Task task = new Task(); 
	/* 任务信息管理业务逻辑层 */
	private TaskService taskService = new TaskService();
	private LeaveInfoService leaveInfoService = new LeaveInfoService();
	private NoteService noteService = new NoteService();
	private UserInfoService userInfoService = new UserInfoService();
	private int taskId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.task_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看任务信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_taskId = (TextView) findViewById(R.id.TV_taskId);
		TV_leaveInfoObj = (TextView) findViewById(R.id.TV_leaveInfoObj);
		TV_noteObj = (TextView) findViewById(R.id.TV_noteObj);
		TV_state = (TextView) findViewById(R.id.TV_state);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_taskTime = (TextView) findViewById(R.id.TV_taskTime);
		Bundle extras = this.getIntent().getExtras();
		taskId = extras.getInt("taskId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				TaskDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    task = taskService.GetTask(taskId); 
		this.TV_taskId.setText(task.getTaskId() + "");
		LeaveInfo leaveInfoObj = leaveInfoService.GetLeaveInfo(task.getLeaveInfoObj());
		this.TV_leaveInfoObj.setText(leaveInfoObj.getLeaveId());
		Note noteObj = noteService.GetNote(task.getNoteObj());
		this.TV_noteObj.setText(noteObj.getNoteName());
		this.TV_state.setText(task.getState() + "");
		UserInfo userObj = userInfoService.GetUserInfo(task.getUserObj());
		this.TV_userObj.setText(userObj.getName());
		this.TV_taskTime.setText(task.getTaskTime());
	} 
}

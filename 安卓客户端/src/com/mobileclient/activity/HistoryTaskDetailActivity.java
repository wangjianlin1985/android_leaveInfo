package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.HistoryTask;
import com.mobileclient.service.HistoryTaskService;
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
public class HistoryTaskDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明任务历史记录Id控件
	private TextView TV_historyTaskId;
	// 声明请假记录ID控件
	private TextView TV_leaveObj;
	// 声明节点控件
	private TextView TV_noteObj;
	// 声明审批意见控件
	private TextView TV_checkSuggest;
	// 声明处理人控件
	private TextView TV_userObj;
	// 声明创建时间控件
	private TextView TV_taskTime;
	// 声明审批状态控件
	private TextView TV_checkState;
	/* 要保存的历史任务信息 */
	HistoryTask historyTask = new HistoryTask(); 
	/* 历史任务管理业务逻辑层 */
	private HistoryTaskService historyTaskService = new HistoryTaskService();
	private LeaveInfoService leaveInfoService = new LeaveInfoService();
	private NoteService noteService = new NoteService();
	private UserInfoService userInfoService = new UserInfoService();
	private int historyTaskId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.historytask_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看历史任务详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_historyTaskId = (TextView) findViewById(R.id.TV_historyTaskId);
		TV_leaveObj = (TextView) findViewById(R.id.TV_leaveObj);
		TV_noteObj = (TextView) findViewById(R.id.TV_noteObj);
		TV_checkSuggest = (TextView) findViewById(R.id.TV_checkSuggest);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_taskTime = (TextView) findViewById(R.id.TV_taskTime);
		TV_checkState = (TextView) findViewById(R.id.TV_checkState);
		Bundle extras = this.getIntent().getExtras();
		historyTaskId = extras.getInt("historyTaskId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				HistoryTaskDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    historyTask = historyTaskService.GetHistoryTask(historyTaskId); 
		this.TV_historyTaskId.setText(historyTask.getHistoryTaskId() + "");
		LeaveInfo leaveObj = leaveInfoService.GetLeaveInfo(historyTask.getLeaveObj());
		this.TV_leaveObj.setText(leaveObj.getLeaveId());
		Note noteObj = noteService.GetNote(historyTask.getNoteObj());
		this.TV_noteObj.setText(noteObj.getNoteName());
		this.TV_checkSuggest.setText(historyTask.getCheckSuggest());
		UserInfo userObj = userInfoService.GetUserInfo(historyTask.getUserObj());
		this.TV_userObj.setText(userObj.getName());
		this.TV_taskTime.setText(historyTask.getTaskTime());
		this.TV_checkState.setText(historyTask.getCheckState() + "");
	} 
}

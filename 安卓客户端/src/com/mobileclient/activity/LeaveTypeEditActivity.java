package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.LeaveType;
import com.mobileclient.service.LeaveTypeService;
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

public class LeaveTypeEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明请假类型IDTextView
	private TextView TV_leaveTypeId;
	// 声明请假类型名称输入框
	private EditText ET_leaveTypeName;
	protected String carmera_path;
	/*要保存的请假类型信息*/
	LeaveType leaveType = new LeaveType();
	/*请假类型管理业务逻辑层*/
	private LeaveTypeService leaveTypeService = new LeaveTypeService();

	private int leaveTypeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.leavetype_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑请假类型信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_leaveTypeId = (TextView) findViewById(R.id.TV_leaveTypeId);
		ET_leaveTypeName = (EditText) findViewById(R.id.ET_leaveTypeName);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		leaveTypeId = extras.getInt("leaveTypeId");
		/*单击修改请假类型按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取请假类型名称*/ 
					if(ET_leaveTypeName.getText().toString().equals("")) {
						Toast.makeText(LeaveTypeEditActivity.this, "请假类型名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_leaveTypeName.setFocusable(true);
						ET_leaveTypeName.requestFocus();
						return;	
					}
					leaveType.setLeaveTypeName(ET_leaveTypeName.getText().toString());
					/*调用业务逻辑层上传请假类型信息*/
					LeaveTypeEditActivity.this.setTitle("正在更新请假类型信息，稍等...");
					String result = leaveTypeService.UpdateLeaveType(leaveType);
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
	    leaveType = leaveTypeService.GetLeaveType(leaveTypeId);
		this.TV_leaveTypeId.setText(leaveTypeId+"");
		this.ET_leaveTypeName.setText(leaveType.getLeaveTypeName());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}

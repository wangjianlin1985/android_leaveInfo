package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Position;
import com.mobileclient.service.PositionService;
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

public class PositionEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明职级IDTextView
	private TextView TV_positionId;
	// 声明职级名称输入框
	private EditText ET_positionName;
	protected String carmera_path;
	/*要保存的职级信息信息*/
	Position position = new Position();
	/*职级信息管理业务逻辑层*/
	private PositionService positionService = new PositionService();

	private int positionId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.position_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑职级信息信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_positionId = (TextView) findViewById(R.id.TV_positionId);
		ET_positionName = (EditText) findViewById(R.id.ET_positionName);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		positionId = extras.getInt("positionId");
		/*单击修改职级信息按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取职级名称*/ 
					if(ET_positionName.getText().toString().equals("")) {
						Toast.makeText(PositionEditActivity.this, "职级名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_positionName.setFocusable(true);
						ET_positionName.requestFocus();
						return;	
					}
					position.setPositionName(ET_positionName.getText().toString());
					/*调用业务逻辑层上传职级信息信息*/
					PositionEditActivity.this.setTitle("正在更新职级信息信息，稍等...");
					String result = positionService.UpdatePosition(position);
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
	    position = positionService.GetPosition(positionId);
		this.TV_positionId.setText(positionId+"");
		this.ET_positionName.setText(position.getPositionName());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}

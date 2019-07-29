package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.DayTimeType;
import com.mobileclient.service.DayTimeTypeService;
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
public class DayTimeTypeDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_dayTimeTypeId;
	// 声明时间段名称控件
	private TextView TV_dayTimeTypeName;
	/* 要保存的时间段类型信息 */
	DayTimeType dayTimeType = new DayTimeType(); 
	/* 时间段类型管理业务逻辑层 */
	private DayTimeTypeService dayTimeTypeService = new DayTimeTypeService();
	private int dayTimeTypeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.daytimetype_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看时间段类型详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_dayTimeTypeId = (TextView) findViewById(R.id.TV_dayTimeTypeId);
		TV_dayTimeTypeName = (TextView) findViewById(R.id.TV_dayTimeTypeName);
		Bundle extras = this.getIntent().getExtras();
		dayTimeTypeId = extras.getInt("dayTimeTypeId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				DayTimeTypeDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    dayTimeType = dayTimeTypeService.GetDayTimeType(dayTimeTypeId); 
		this.TV_dayTimeTypeId.setText(dayTimeType.getDayTimeTypeId() + "");
		this.TV_dayTimeTypeName.setText(dayTimeType.getDayTimeTypeName());
	} 
}

package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Position;
import com.mobileclient.service.PositionService;
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
public class PositionDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明职级ID控件
	private TextView TV_positionId;
	// 声明职级名称控件
	private TextView TV_positionName;
	/* 要保存的职级信息信息 */
	Position position = new Position(); 
	/* 职级信息管理业务逻辑层 */
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
		setContentView(R.layout.position_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看职级信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_positionId = (TextView) findViewById(R.id.TV_positionId);
		TV_positionName = (TextView) findViewById(R.id.TV_positionName);
		Bundle extras = this.getIntent().getExtras();
		positionId = extras.getInt("positionId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				PositionDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    position = positionService.GetPosition(positionId); 
		this.TV_positionId.setText(position.getPositionId() + "");
		this.TV_positionName.setText(position.getPositionName());
	} 
}

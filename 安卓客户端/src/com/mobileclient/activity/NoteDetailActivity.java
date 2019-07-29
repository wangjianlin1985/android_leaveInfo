package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Note;
import com.mobileclient.service.NoteService;
import com.mobileclient.domain.Department;
import com.mobileclient.service.DepartmentService;
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
public class NoteDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明节点ID控件
	private TextView TV_noteId;
	// 声明节点名称控件
	private TextView TV_noteName;
	// 声明处理部门控件
	private TextView TV_departmentObj;
	// 声明需要职级控件
	private TextView TV_positionObj;
	// 声明节点编号控件
	private TextView TV_noteIndex;
	/* 要保存的节点信息信息 */
	Note note = new Note(); 
	/* 节点信息管理业务逻辑层 */
	private NoteService noteService = new NoteService();
	private DepartmentService departmentService = new DepartmentService();
	private PositionService positionService = new PositionService();
	private int noteId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.note_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看节点信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_noteId = (TextView) findViewById(R.id.TV_noteId);
		TV_noteName = (TextView) findViewById(R.id.TV_noteName);
		TV_departmentObj = (TextView) findViewById(R.id.TV_departmentObj);
		TV_positionObj = (TextView) findViewById(R.id.TV_positionObj);
		TV_noteIndex = (TextView) findViewById(R.id.TV_noteIndex);
		Bundle extras = this.getIntent().getExtras();
		noteId = extras.getInt("noteId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				NoteDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    note = noteService.GetNote(noteId); 
		this.TV_noteId.setText(note.getNoteId() + "");
		this.TV_noteName.setText(note.getNoteName());
		Department departmentObj = departmentService.GetDepartment(note.getDepartmentObj());
		this.TV_departmentObj.setText(departmentObj.getDepartmentName());
		Position positionObj = positionService.GetPosition(note.getPositionObj());
		this.TV_positionObj.setText(positionObj.getPositionName());
		this.TV_noteIndex.setText(note.getNoteIndex() + "");
	} 
}

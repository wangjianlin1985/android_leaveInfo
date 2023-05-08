package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Note;
import com.mobileclient.service.NoteService;
import com.mobileclient.domain.Department;
import com.mobileclient.service.DepartmentService;
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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class NoteAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明节点名称输入框
	private EditText ET_noteName;
	// 声明处理部门下拉框
	private Spinner spinner_departmentObj;
	private ArrayAdapter<String> departmentObj_adapter;
	private static  String[] departmentObj_ShowText  = null;
	private List<Department> departmentList = null;
	/*处理部门管理业务逻辑层*/
	private DepartmentService departmentService = new DepartmentService();
	// 声明需要职级下拉框
	private Spinner spinner_positionObj;
	private ArrayAdapter<String> positionObj_adapter;
	private static  String[] positionObj_ShowText  = null;
	private List<Position> positionList = null;
	/*需要职级管理业务逻辑层*/
	private PositionService positionService = new PositionService();
	// 声明节点编号输入框
	private EditText ET_noteIndex;
	protected String carmera_path;
	/*要保存的节点信息信息*/
	Note note = new Note();
	/*节点信息管理业务逻辑层*/
	private NoteService noteService = new NoteService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.note_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加节点信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_noteName = (EditText) findViewById(R.id.ET_noteName);
		spinner_departmentObj = (Spinner) findViewById(R.id.Spinner_departmentObj);
		// 获取所有的处理部门
		try {
			departmentList = departmentService.QueryDepartment(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int departmentCount = departmentList.size();
		departmentObj_ShowText = new String[departmentCount];
		for(int i=0;i<departmentCount;i++) { 
			departmentObj_ShowText[i] = departmentList.get(i).getDepartmentName();
		}
		// 将可选内容与ArrayAdapter连接起来
		departmentObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, departmentObj_ShowText);
		// 设置下拉列表的风格
		departmentObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_departmentObj.setAdapter(departmentObj_adapter);
		// 添加事件Spinner事件监听
		spinner_departmentObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				note.setDepartmentObj(departmentList.get(arg2).getDepartmentId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_departmentObj.setVisibility(View.VISIBLE);
		spinner_positionObj = (Spinner) findViewById(R.id.Spinner_positionObj);
		// 获取所有的需要职级
		try {
			positionList = positionService.QueryPosition(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int positionCount = positionList.size();
		positionObj_ShowText = new String[positionCount];
		for(int i=0;i<positionCount;i++) { 
			positionObj_ShowText[i] = positionList.get(i).getPositionName();
		}
		// 将可选内容与ArrayAdapter连接起来
		positionObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, positionObj_ShowText);
		// 设置下拉列表的风格
		positionObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_positionObj.setAdapter(positionObj_adapter);
		// 添加事件Spinner事件监听
		spinner_positionObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				note.setPositionObj(positionList.get(arg2).getPositionId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_positionObj.setVisibility(View.VISIBLE);
		ET_noteIndex = (EditText) findViewById(R.id.ET_noteIndex);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加节点信息按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取节点名称*/ 
					if(ET_noteName.getText().toString().equals("")) {
						Toast.makeText(NoteAddActivity.this, "节点名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_noteName.setFocusable(true);
						ET_noteName.requestFocus();
						return;	
					}
					note.setNoteName(ET_noteName.getText().toString());
					/*验证获取节点编号*/ 
					if(ET_noteIndex.getText().toString().equals("")) {
						Toast.makeText(NoteAddActivity.this, "节点编号输入不能为空!", Toast.LENGTH_LONG).show();
						ET_noteIndex.setFocusable(true);
						ET_noteIndex.requestFocus();
						return;	
					}
					note.setNoteIndex(Integer.parseInt(ET_noteIndex.getText().toString()));
					/*调用业务逻辑层上传节点信息信息*/
					NoteAddActivity.this.setTitle("正在上传节点信息信息，稍等...");
					String result = noteService.AddNote(note);
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

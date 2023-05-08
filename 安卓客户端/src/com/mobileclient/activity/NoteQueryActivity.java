package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Note;
import com.mobileclient.domain.Department;
import com.mobileclient.service.DepartmentService;
import com.mobileclient.domain.Position;
import com.mobileclient.service.PositionService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class NoteQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明节点名称输入框
	private EditText ET_noteName;
	// 声明处理部门下拉框
	private Spinner spinner_departmentObj;
	private ArrayAdapter<String> departmentObj_adapter;
	private static  String[] departmentObj_ShowText  = null;
	private List<Department> departmentList = null; 
	/*部门信息管理业务逻辑层*/
	private DepartmentService departmentService = new DepartmentService();
	// 声明需要职级下拉框
	private Spinner spinner_positionObj;
	private ArrayAdapter<String> positionObj_adapter;
	private static  String[] positionObj_ShowText  = null;
	private List<Position> positionList = null; 
	/*职级信息管理业务逻辑层*/
	private PositionService positionService = new PositionService();
	/*查询过滤条件保存到这个对象中*/
	private Note queryConditionNote = new Note();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.note_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置节点信息查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_noteName = (EditText) findViewById(R.id.ET_noteName);
		spinner_departmentObj = (Spinner) findViewById(R.id.Spinner_departmentObj);
		// 获取所有的部门信息
		try {
			departmentList = departmentService.QueryDepartment(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int departmentCount = departmentList.size();
		departmentObj_ShowText = new String[departmentCount+1];
		departmentObj_ShowText[0] = "不限制";
		for(int i=1;i<=departmentCount;i++) { 
			departmentObj_ShowText[i] = departmentList.get(i-1).getDepartmentName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		departmentObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, departmentObj_ShowText);
		// 设置处理部门下拉列表的风格
		departmentObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_departmentObj.setAdapter(departmentObj_adapter);
		// 添加事件Spinner事件监听
		spinner_departmentObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionNote.setDepartmentObj(departmentList.get(arg2-1).getDepartmentId()); 
				else
					queryConditionNote.setDepartmentObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_departmentObj.setVisibility(View.VISIBLE);
		spinner_positionObj = (Spinner) findViewById(R.id.Spinner_positionObj);
		// 获取所有的职级信息
		try {
			positionList = positionService.QueryPosition(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int positionCount = positionList.size();
		positionObj_ShowText = new String[positionCount+1];
		positionObj_ShowText[0] = "不限制";
		for(int i=1;i<=positionCount;i++) { 
			positionObj_ShowText[i] = positionList.get(i-1).getPositionName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		positionObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, positionObj_ShowText);
		// 设置需要职级下拉列表的风格
		positionObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_positionObj.setAdapter(positionObj_adapter);
		// 添加事件Spinner事件监听
		spinner_positionObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionNote.setPositionObj(positionList.get(arg2-1).getPositionId()); 
				else
					queryConditionNote.setPositionObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_positionObj.setVisibility(View.VISIBLE);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionNote.setNoteName(ET_noteName.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionNote", queryConditionNote);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}

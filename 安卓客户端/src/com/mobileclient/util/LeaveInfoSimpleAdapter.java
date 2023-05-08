package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.DepartmentService;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.service.PositionService;
import com.mobileclient.service.LeaveTypeService;
import com.mobileclient.service.DayTimeTypeService;
import com.mobileclient.service.DayTimeTypeService;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class LeaveInfoSimpleAdapter extends SimpleAdapter { 
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
    private SyncImageLoader syncImageLoader;

    public LeaveInfoSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.leaveinfo_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_writeTime = (TextView)convertView.findViewById(R.id.tv_writeTime);
	  holder.tv_departmentObj = (TextView)convertView.findViewById(R.id.tv_departmentObj);
	  holder.tv_userObj = (TextView)convertView.findViewById(R.id.tv_userObj);
	  holder.tv_positionObj = (TextView)convertView.findViewById(R.id.tv_positionObj);
	  holder.tv_leaveTypeObj = (TextView)convertView.findViewById(R.id.tv_leaveTypeObj);
	  holder.tv_sfcj = (TextView)convertView.findViewById(R.id.tv_sfcj);
	  holder.tv_startDate = (TextView)convertView.findViewById(R.id.tv_startDate);
	  holder.tv_startDayTimeType = (TextView)convertView.findViewById(R.id.tv_startDayTimeType);
	  holder.tv_endDate = (TextView)convertView.findViewById(R.id.tv_endDate);
	  holder.tv_endDayTimeType = (TextView)convertView.findViewById(R.id.tv_endDayTimeType);
	  holder.tv_leaveDays = (TextView)convertView.findViewById(R.id.tv_leaveDays);
	  holder.tv_writeUserObj = (TextView)convertView.findViewById(R.id.tv_writeUserObj);
	  holder.tv_place = (TextView)convertView.findViewById(R.id.tv_place);
	  holder.tv_reason = (TextView)convertView.findViewById(R.id.tv_reason);
	  holder.tv_state = (TextView)convertView.findViewById(R.id.tv_state);
	  /*设置各个控件的展示内容*/
	  holder.tv_writeTime.setText("填表时间：" + mData.get(position).get("writeTime").toString());
	  holder.tv_departmentObj.setText("部门：" + (new DepartmentService()).GetDepartment(Integer.parseInt(mData.get(position).get("departmentObj").toString())).getDepartmentName());
	  holder.tv_userObj.setText("请假人：" + (new UserInfoService()).GetUserInfo(mData.get(position).get("userObj").toString()).getName());
	  holder.tv_positionObj.setText("职级：" + (new PositionService()).GetPosition(Integer.parseInt(mData.get(position).get("positionObj").toString())).getPositionName());
	  holder.tv_leaveTypeObj.setText("请假类别：" + (new LeaveTypeService()).GetLeaveType(Integer.parseInt(mData.get(position).get("leaveTypeObj").toString())).getLeaveTypeName());
	  holder.tv_sfcj.setText("是否长假：" + mData.get(position).get("sfcj").toString());
	  try {holder.tv_startDate.setText("开始时间：" + mData.get(position).get("startDate").toString().substring(0, 10));} catch(Exception ex){}
	  holder.tv_startDayTimeType.setText("开始时间段：" + (new DayTimeTypeService()).GetDayTimeType(Integer.parseInt(mData.get(position).get("startDayTimeType").toString())).getDayTimeTypeName());
	  try {holder.tv_endDate.setText("结束时间：" + mData.get(position).get("endDate").toString().substring(0, 10));} catch(Exception ex){}
	  holder.tv_endDayTimeType.setText("结束时间段：" + (new DayTimeTypeService()).GetDayTimeType(Integer.parseInt(mData.get(position).get("endDayTimeType").toString())).getDayTimeTypeName());
	  holder.tv_leaveDays.setText("请假天数：" + mData.get(position).get("leaveDays").toString());
	  holder.tv_writeUserObj.setText("填写人：" + (new UserInfoService()).GetUserInfo(mData.get(position).get("writeUserObj").toString()).getName());
	  holder.tv_place.setText("前往地点：" + mData.get(position).get("place").toString());
	  holder.tv_reason.setText("请假事由：" + mData.get(position).get("reason").toString());
	  holder.tv_state.setText("状态：" + mData.get(position).get("state").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_writeTime;
    	TextView tv_departmentObj;
    	TextView tv_userObj;
    	TextView tv_positionObj;
    	TextView tv_leaveTypeObj;
    	TextView tv_sfcj;
    	TextView tv_startDate;
    	TextView tv_startDayTimeType;
    	TextView tv_endDate;
    	TextView tv_endDayTimeType;
    	TextView tv_leaveDays;
    	TextView tv_writeUserObj;
    	TextView tv_place;
    	TextView tv_reason;
    	TextView tv_state;
    }
} 

package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.LeaveInfoService;
import com.mobileclient.service.NoteService;
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

public class HistoryTaskSimpleAdapter extends SimpleAdapter { 
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

    public HistoryTaskSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.historytask_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_leaveObj = (TextView)convertView.findViewById(R.id.tv_leaveObj);
	  holder.tv_noteObj = (TextView)convertView.findViewById(R.id.tv_noteObj);
	  holder.tv_checkSuggest = (TextView)convertView.findViewById(R.id.tv_checkSuggest);
	  holder.tv_userObj = (TextView)convertView.findViewById(R.id.tv_userObj);
	  holder.tv_taskTime = (TextView)convertView.findViewById(R.id.tv_taskTime);
	  holder.tv_checkState = (TextView)convertView.findViewById(R.id.tv_checkState);
	  /*设置各个控件的展示内容*/
	  holder.tv_leaveObj.setText("请假记录ID：" + (new LeaveInfoService()).GetLeaveInfo(Integer.parseInt(mData.get(position).get("leaveObj").toString())).getLeaveId());
	  holder.tv_noteObj.setText("节点：" + (new NoteService()).GetNote(Integer.parseInt(mData.get(position).get("noteObj").toString())).getNoteName());
	  holder.tv_checkSuggest.setText("审批意见：" + mData.get(position).get("checkSuggest").toString());
	  holder.tv_userObj.setText("处理人：" + (new UserInfoService()).GetUserInfo(mData.get(position).get("userObj").toString()).getName());
	  holder.tv_taskTime.setText("创建时间：" + mData.get(position).get("taskTime").toString());
	  holder.tv_checkState.setText("审批状态：" + mData.get(position).get("checkState").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_leaveObj;
    	TextView tv_noteObj;
    	TextView tv_checkSuggest;
    	TextView tv_userObj;
    	TextView tv_taskTime;
    	TextView tv_checkState;
    }
} 

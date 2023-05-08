package com.mobileclient.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainMenuActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("手机客户端-主界面");
        setContentView(R.layout.main_menu);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        
        AnimationSet set = new AnimationSet(false);
        Animation animation = new AlphaAnimation(0,1);
        animation.setDuration(500);
        set.addAnimation(animation);
        
        animation = new TranslateAnimation(1, 13, 10, 50);
        animation.setDuration(300);
        set.addAnimation(animation);
        
        animation = new RotateAnimation(30,10);
        animation.setDuration(300);
        set.addAnimation(animation);
        
        animation = new ScaleAnimation(5,0,2,0);
        animation.setDuration(300);
        set.addAnimation(animation);
        
        LayoutAnimationController controller = new LayoutAnimationController(set, 1);
        
        gridview.setLayoutAnimation(controller);
        
        gridview.setAdapter(new ImageAdapter(this));
    }
    
    // 继承BaseAdapter
    public class ImageAdapter extends BaseAdapter {
    	
    	LayoutInflater inflater;
    	
    	// 上下文
        private Context mContext;
        
        // 图片资源数组
        private Integer[] mThumbIds = {
                R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon
        };
        private String[] menuString = {"用户信息管理","部门信息管理","职级信息管理","请假类型管理","请假信息管理","时间段类型管理","节点信息管理","任务信息管理","历史任务管理","新闻公告管理"};

        // 构造方法
        public ImageAdapter(Context c) {
            mContext = c;
            inflater = LayoutInflater.from(mContext);
        }
        // 组件个数
        public int getCount() {
            return mThumbIds.length;
        }
        // 当前组件
        public Object getItem(int position) {
            return null;
        }
        // 当前组件id
        public long getItemId(int position) {
            return 0;
        }
        // 获得当前视图
        public View getView(int position, View convertView, ViewGroup parent) { 
        	View view = inflater.inflate(R.layout.gv_item, null);
			TextView tv = (TextView) view.findViewById(R.id.gv_item_appname);
			ImageView iv = (ImageView) view.findViewById(R.id.gv_item_icon);  
			tv.setText(menuString[position]); 
			iv.setImageResource(mThumbIds[position]); 
			  switch (position) {
				case 0:
					// 用户信息管理监听器
					view.setOnClickListener(userInfoLinstener);
					break;
				case 1:
					// 部门信息管理监听器
					view.setOnClickListener(departmentLinstener);
					break;
				case 2:
					// 职级信息管理监听器
					view.setOnClickListener(positionLinstener);
					break;
				case 3:
					// 请假类型管理监听器
					view.setOnClickListener(leaveTypeLinstener);
					break;
				case 4:
					// 请假信息管理监听器
					view.setOnClickListener(leaveInfoLinstener);
					break;
				case 5:
					// 时间段类型管理监听器
					view.setOnClickListener(dayTimeTypeLinstener);
					break;
				case 6:
					// 节点信息管理监听器
					view.setOnClickListener(noteLinstener);
					break;
				case 7:
					// 任务信息管理监听器
					view.setOnClickListener(taskLinstener);
					break;
				case 8:
					// 历史任务管理监听器
					view.setOnClickListener(historyTaskLinstener);
					break;
				case 9:
					// 新闻公告管理监听器
					view.setOnClickListener(newsLinstener);
					break;

			 
				default:
					break;
				} 
			return view; 
        }
       
    }
    
    OnClickListener userInfoLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动用户信息管理Activity
			intent.setClass(MainMenuActivity.this, UserInfoListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener departmentLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动部门信息管理Activity
			intent.setClass(MainMenuActivity.this, DepartmentListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener positionLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动职级信息管理Activity
			intent.setClass(MainMenuActivity.this, PositionListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener leaveTypeLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动请假类型管理Activity
			intent.setClass(MainMenuActivity.this, LeaveTypeListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener leaveInfoLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动请假信息管理Activity
			intent.setClass(MainMenuActivity.this, LeaveInfoListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener dayTimeTypeLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动时间段类型管理Activity
			intent.setClass(MainMenuActivity.this, DayTimeTypeListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener noteLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动节点信息管理Activity
			intent.setClass(MainMenuActivity.this, NoteListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener taskLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动任务信息管理Activity
			intent.setClass(MainMenuActivity.this, TaskListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener historyTaskLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动历史任务管理Activity
			intent.setClass(MainMenuActivity.this, HistoryTaskListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener newsLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动新闻公告管理Activity
			intent.setClass(MainMenuActivity.this, NewsListActivity.class);
			startActivity(intent);
		}
	};


	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 1, 1, "重新登入");  
		menu.add(0, 2, 2, "退出"); 
		return super.onCreateOptionsMenu(menu); 
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == 1) {//重新登入 
			Intent intent = new Intent();
			intent.setClass(MainMenuActivity.this,
					LoginActivity.class);
			startActivity(intent);
		} else if (item.getItemId() == 2) {//退出
			System.exit(0);  
		} 
		return true; 
	}
}

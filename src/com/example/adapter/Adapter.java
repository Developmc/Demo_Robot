package com.example.adapter;

import java.util.List;

import com.example.data.ListData;
import com.example.robot.R;

import android.R.layout;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Adapter extends BaseAdapter {

	private List<ListData> lists ;
	private Context mContext ;
	private RelativeLayout layout ;
	
	public Adapter(List<ListData> lists,Context mContext)
	{
		this.lists = lists ;
		this.mContext = mContext ;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext) ;
        if(lists.get(position).getFlag()==ListData.RECEIVER)
        {
        	layout = (RelativeLayout)inflater.inflate(R.layout.left_item, null) ;
        }
        if(lists.get(position).getFlag()==ListData.SEND)
        {
        	layout = (RelativeLayout)inflater.inflate(R.layout.right_item, null) ;
        }
        TextView tv = (TextView)layout.findViewById(R.id.tv) ;
        TextView time = (TextView)layout.findViewById(R.id.time) ;
        tv.setText(lists.get(position).getData()) ;
        time.setText(lists.get(position).getTime()) ;
        
		return layout;
	}

}

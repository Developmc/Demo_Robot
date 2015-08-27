package com.example.robot;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.adapter.Adapter;
import com.example.data.ListData;
import com.example.http.HttpData;
import com.example.http.HttpGetDataListener;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
public class MainActivity extends Activity implements HttpGetDataListener,
    OnClickListener{

	private HttpData httpData ;
	private List<ListData> lists ;
	private ListView lv ;
	private EditText sendText ;
	private Button send_btn ;
	private String content_str ;
	private Adapter adapter ;
	private String[] welcome_array ;
	private double currentTime ,oldTime = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView(savedInstanceState) ;
    }

	@Override
	public void getDataUrl(String data) {
		System.out.println(data) ;
		Log.d("output", data) ;
		parseText(data) ;
	}
	
	public void initView(Bundle savedInstanceState)
	{
		lv = (ListView)findViewById(R.id.lv) ;
		sendText = (EditText)findViewById(R.id.sendText) ;
		send_btn = (Button)findViewById(R.id.send_btn) ;
		lists = new ArrayList<ListData>() ;
		send_btn.setOnClickListener(this);
		//横竖屏数据保存
	    if(savedInstanceState==null)
		{
			ListData data = new ListData(getRandomWelcomeTips(), ListData.RECEIVER,getTime()) ;
		    lists.add(data) ;
		}
	    else {
	    	lists=savedInstanceState.getParcelableArrayList("data") ;
		}
	    adapter = new Adapter(lists, this) ;
	    lv.setAdapter(adapter) ;
	    
	}
	
	//解析json
	public void parseText(String data)
	{
		try {
			JSONObject jb = new JSONObject(data) ;
			ListData listData = new ListData(jb.getString("text"),ListData.RECEIVER,getTime()) ;
			lists.add(listData) ;
			System.out.println(jb.getString("text")) ;
			//重新适配
			adapter.notifyDataSetChanged() ;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void onClick(View v) {
		content_str = sendText.getText().toString() ;
		ListData listaData = new ListData(content_str, ListData.SEND,getTime()) ;
		lists.add(listaData) ;
		adapter.notifyDataSetChanged() ;
		//数据传输前去掉空格和换行
		String dropk = content_str.replace(" ", "") ;
		String droph = dropk.replace("\n", "") ;
		//判断网络是否可用
		if(getNetworkIsAvailable())
		{
			httpData = (HttpData) new HttpData(
	        		"http://www.tuling123.com/openapi/api?key=d925475fb913de7fe2ad2c337c6489d0&info="
					+droph,this).execute() ;
			//清空输入框内容
			sendText.setText("") ;
		}

		//检查列表，果然过长，则不显示（删除）前面部分
		while(lists.size()>30)
		{
			lists.remove(0) ;
		}
	}
	
	public String getRandomWelcomeTips()
	{
		welcome_array = this.getResources().getStringArray(R.array.welcome_tips) ;
		int index = (int)(Math.random()*(welcome_array.length-1)) ;
		return welcome_array[index] ;
	}
	//获取系统时间
	private String getTime()
	{
		currentTime = System.currentTimeMillis() ;
		SimpleDateFormat format = new SimpleDateFormat("yyyy.mm.dd HH:mm:ss") ;
		Date curData = new Date() ;
		String str = format.format(curData) ;
		if(currentTime-oldTime>=5*60*1000)
		{
			oldTime = currentTime ;
			return str ;
		}
		else {
			return "" ;
		}
	}
	
	public Boolean getNetworkIsAvailable(){
		//此处写网络检测的代码
		ConnectivityManager conManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE );
        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
        if (networkInfo != null ){
             return networkInfo.isAvailable();
        }
        return false ;
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		
		
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putParcelableArrayList("data", (ArrayList<? extends Parcelable>) lists) ;
	}
}

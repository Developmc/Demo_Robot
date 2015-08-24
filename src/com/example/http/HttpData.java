package com.example.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.PublicKey;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

public class HttpData extends AsyncTask<String, Void, String> {

	//通过HttpClient请求数据
	private HttpClient mHttpClient;
	//api接口的请求方式是get
	private HttpGet mHttpGet ;
	//依据URI请求
	private String url ;
	//获取请求的返回
    private HttpResponse mHttpResponse ;
    //Http实体
    private HttpEntity mHttpEntity ;
    private InputStream in ;
    private HttpGetDataListener listener ;
	
	public HttpData(String url,HttpGetDataListener listener)
	{
		this.url = url ;
		this.listener = listener ;
	}
	@Override
	protected String doInBackground(String... params) {
		try {
			//实例化客户端
			mHttpClient = new DefaultHttpClient() ;
			//发送请求
			mHttpGet = new HttpGet(url) ;
			//通过客户端进行发送
			mHttpResponse = mHttpClient.execute(mHttpGet) ;
			//获取数据
			mHttpEntity = mHttpResponse.getEntity() ;
			//将数据转化为流文件
			in = mHttpEntity.getContent();
			
			//通过缓冲区进行读取
			BufferedReader br = new BufferedReader(new InputStreamReader(in)) ;
			
			//获得数据
			String line = null ;
			StringBuffer sb = new StringBuffer() ;
			while((line=br.readLine())!=null)
			{
				sb.append(line) ;
			}
			return sb.toString() ;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null ;
	}
	@Override
	protected void onPostExecute(String result) {
		listener.getDataUrl(result) ;
		super.onPostExecute(result);
	}

}

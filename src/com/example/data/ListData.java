package com.example.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.os.ParcelableCompat;

/**封装数据
 * @author Devel_000
 *
 */
public class ListData implements Parcelable{

	private String content ;
	public static final int SEND = 1 ;
	public static final int RECEIVER = 2 ;
	private int flag ;
	private String time ;
	
	public ListData(String content,int flag,String time)
	{
		setData(content) ;
		setFlag(flag) ;
		setTime(time) ;
	}
	public void setData(String content)
	{
		this.content = content ;
	}
	public String getData()
	{
		return content ;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
}

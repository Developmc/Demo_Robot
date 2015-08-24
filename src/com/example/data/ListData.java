package com.example.data;

/**封装数据
 * @author Devel_000
 *
 */
public class ListData {

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
}

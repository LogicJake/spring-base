package com.scy.base.util.model;

public class GenericJsonResult<T> 
{
	private int hr;
	private String message;
	private T data;
	private String extraData;

	public int getHr()
	{
		return hr;
	}
	
	public void setHr(int hr){
		this.hr = hr;
		this.message = HResult.valueOf(hr).getMessage();
	}
	
	public void setHr(HResult hr)
	{
		this.hr = hr.getIndex();
		this.message = hr.getMessage();
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public void setMessage(String message)
	{
		this.message = message;
	}
	
	public T getData()
	{
		return data;
	}
	
	public void setData(T data)
	{
		this.data = data;
	}
	
	public GenericJsonResult()
	{
	}
	
	public GenericJsonResult(int hr)
	{
	}
	
	public GenericJsonResult(HResult hr)
	{
		this.hr = hr.getIndex();
		this.message = hr.getMessage();
	}
	
	public GenericJsonResult(HResult hr, String message)
	{
		this.hr = hr.getIndex();
		this.message = message;
	}
	
	public GenericJsonResult(HResult hr, T data, String message)
	{
		this.hr = hr.getIndex();
		this.data = data;
		this.message = message;
	}
	
	public String getExtraData()
	{
		return extraData;
	}
	
	public void setExtraData(String extraData)
	{
		this.extraData = extraData;
	}
}


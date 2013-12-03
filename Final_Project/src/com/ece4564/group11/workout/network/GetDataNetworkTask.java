package com.ece4564.group11.workout.network;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

public class GetDataNetworkTask extends AsyncTask<String, Integer, String>{

	String address_;
	String name_;
	String uuid_;
	
	public GetDataNetworkTask()
	{
		address_ = null;
		name_ = null;
		uuid_ = null;
	}
	
	public GetDataNetworkTask(String name, String address, String uuid)
	{
		name_ = name;
		uuid_ = uuid;
		address_ = address;
	}
	
	@Override
	protected String doInBackground(String... arg0) 
	{
		HttpClient client = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(address_ + "store?name=" + name_ + "&uuid=" + uuid_);
		
		try 
		{
			HttpResponse response = client.execute(httpget);
			return EntityUtils.toString(response.getEntity());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPostExecute(String result)
	{
		if (result == null)
		{
			System.out.println("GETTING WORKOUT FAILED");
		}
		else
		{
			System.out.println(result);
		}
	}
	
	

}

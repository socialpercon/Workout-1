package com.ece4564.group11.workout.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;

import com.ece4564.group11.workout.fragments.PlannerFragment;

import android.os.AsyncTask;
import android.util.Log;

public class GetDataNetworkTask extends AsyncTask<Void, Void, String> {
	
	private PlannerFragment pf_;
	String address_;
	String uuid_;
	HashMap<String, String> cache_;
	private String returnKey_ = "";

	public GetDataNetworkTask() {
		address_ = null;
		uuid_ = null;
	}

	public GetDataNetworkTask(String address, String uuid,
			PlannerFragment pf) {
		uuid_ = uuid;
		address_ = address;

		pf_ = pf;
	}

	@Override
	protected String doInBackground(Void... params) {
		HttpClient client = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(address_ + "store?" + "uuid=" + uuid_);

		try {
			HttpResponse response = client.execute(httpget);
			
			System.out.println("ASDosdojhsjodnasjodnasiudhasuodhasuidhasoudhasuodas");
			System.out.println(EntityUtils.toString(response.getEntity()));
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			Log.d("Get status:", response.getStatusLine().toString());
			JSONParser parser = new JSONParser();
			ContainerFactory containerFactory = new ContainerFactory() {
				public List creatArrayContainer() {
					return new LinkedList();
				}

				public Map createObjectContainer() {
					return new LinkedHashMap();
				}
			};
			Map json = (Map) parser.parse(reader, containerFactory);
			Iterator iter = json.entrySet().iterator();
			cache_ = new HashMap<String, String>();
			System.out.println("==iterate result==");

			Map.Entry<String, String> entry = null;
			while (iter.hasNext()) {
				entry = (Map.Entry<String, String>) iter.next();
				String value = entry.getValue();
				Log.d("VALUES: ", entry.getKey() + "=>" + value);

				cache_.put(entry.getKey(), value);
				System.out.println(cache_);
				returnKey_ = entry.getKey().toString();
				System.out.println("What key was retrieved? " + returnKey_);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d("RETURN KEY", returnKey_);
		return returnKey_;
	}

	@Override
	protected void onPostExecute(String result) {
		System.out.println("RESULT " + result);
		pf_.getDataNetworkTaskResult(result);
	}

}

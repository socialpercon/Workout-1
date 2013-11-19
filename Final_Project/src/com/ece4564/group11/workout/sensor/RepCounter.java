package com.ece4564.group11.workout.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class RepCounter implements SensorEventListener{
	
	int totalReps_;
	int state;
	int index_;
	
	float prevVal_;
	
	SensorManager sManager_;
	
	public RepCounter()
	{
		
		totalReps_ = 0;
		sManager_ = null;
		state = 0;
		index_ = 0;
	}
	
	public RepCounter(SensorManager s)
	{
		prevVal_ = 0;
		
		totalReps_ = 0;
		sManager_ = s;
		state = 0;
		index_ = 0;
	}
	
	public void start()
	{
		Sensor mAccel_ = sManager_.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		sManager_.registerListener(this, mAccel_, SensorManager.SENSOR_DELAY_GAME);
	}
	
	public void stop()
	{
		sManager_.unregisterListener(this);
	}
	
	public int getReps()
	{
		return totalReps_;
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent arg0) 
	{
		float absv = Math.abs(arg0.values[0]) + Math.abs(arg0.values[1]) + Math.abs(arg0.values[2]);
		System.out.println(absv);
		process(absv);
	}
	
	private void process(float f)
	{
		
		
		if (state == 0)
		{
			if (f > 6)
			{
				index_++;
			}
			else
			{
				index_ = 0;
			}
			
			if (index_ > 10)
			{
				state = 1;
				index_ = 0;
			}
		}
		else if (state == 1)
		{
			if (f < 2)
			{
				index_ ++;
			}
			else
			{
				index_ = 0;
			}
			
			if (index_ > 10)
			{
				state = 2;
				index_ = 0;
			}
		}
		else if (state == 2)
		{
			if (f > 6)
			{
				index_++;
			}
			else
			{
				index_ = 0;
			}
			
			if (index_ > 10)
			{
				state = 3;
				index_ = 0;
			}
		}
		
		if (state == 3)
		{
			state = 0;
			totalReps_++;
			index_ = 0;
		}
	}

}

package com.ece4564.group11.workout.media;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;

public class MusicPlayer {
	
	MediaPlayer mp_;
	
	public MusicPlayer(OnPreparedListener opl)
	{
		mp_ = new MediaPlayer();
		mp_.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mp_.setOnPreparedListener(opl);
	}
	
	public MusicPlayer()
	{
		mp_ = new MediaPlayer();
		mp_.setAudioStreamType(AudioManager.STREAM_MUSIC);
	}
	
	public void setOnPreparedListener(OnPreparedListener opl)
	{
		mp_.setOnPreparedListener(opl);
	}
	
	public void setData(String path)
	{
		try 
		{
			mp_.setDataSource(path);
			mp_.prepareAsync();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void start()
	{
		mp_.start();
	}
	
	public void pause()
	{
		mp_.pause();
	}
	
	public void reset()
	{
		mp_.reset();
	}
}

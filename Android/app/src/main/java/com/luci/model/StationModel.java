package com.luci.model;


import java.util.ArrayList;

public class StationModel {
	public int id;
	public String image;

	public String name;
	public String protocol;
	public String destination;
	public int jitter_buffers;
	public int dynamic_jitter_buffers;
	public String format;
	public boolean stereo;
	public int bitrate;
	public int samplerate;
	public boolean talk_mode;
    public String created_at;
    public String updated_at;

	public String disclaimer;

	public ArrayList<StationModel> children;
	public int depth = 0;
}

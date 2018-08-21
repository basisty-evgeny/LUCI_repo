package com.luci.model;


import java.util.ArrayList;

public class StationModel {
	public int id;
	public String name;
	public String image;
	public String disclaimer;

	public ArrayList<StationModel> children;
	public int depth = 0;
}

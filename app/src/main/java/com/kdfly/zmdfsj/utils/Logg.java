package com.kdfly.zmdfsj.utils;

import android.util.Log;

public class Logg {

	public static Boolean DEBUG = true;
	public static String Tag = "调试信息";
	public static final String LogTag = "Sean Log";
	public static String[] Filters = { LogTag };

	public static void i(String tag, String msg) {
		if (DEBUG)
			Log.i(tag, msg);
	}

	public static void e(String tag, String msg) {
		if (DEBUG)
			Log.e(tag, msg);
	}

	public static void e(String msg) {
		if (DEBUG)
			Logg.e(Tag, msg);
	}

	public static void i(String msg) {
		if (DEBUG)
			Logg.i(Tag, msg);
	}

}

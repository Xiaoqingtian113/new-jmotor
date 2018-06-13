package com.zhucq.mobile.util;

import java.io.File;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.win32.W32APIOptions;

public class JNAUtils {
	public static final int BM_CLICK = 0xF5; // 点击事件，即按下和抬起两个动作
	public static final int WM_SETTEXT = 0x000C; // 输入文本

	public static void uploadImg() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		HWND hwnd = null;
		while (true) {
			hwnd = User32Ext.instance.FindWindow("#32770", "打开"); // chrome
			if (hwnd != null) {
				break;
			}
			hwnd = User32Ext.instance.FindWindow("#32770", "选择要加载的文件"); // ie
			if (hwnd != null) {
				break;
			}
			hwnd = User32Ext.instance.FindWindow("#32770", "文件上传"); // firefox
			if (hwnd != null) {
				break;
			}
			return;
		}
		HWND hwndFilename = User32Ext.instance.FindWindowEx(hwnd, null, "ComboBoxEx32", "");
		User32Ext.instance.SendMessage(hwndFilename, WM_SETTEXT, 0, new File("res/test.jpg").getAbsolutePath());
		HWND hwndButton = User32Ext.instance.FindWindowEx(hwnd, null, "Button", "打开(&O)");
		User32Ext.instance.SendMessage(hwndButton, BM_CLICK, 0, 0);
	}

	public static interface User32Ext extends User32 {
		final User32Ext instance = (User32Ext) Native.loadLibrary("user32", User32Ext.class,
				W32APIOptions.DEFAULT_OPTIONS);

		HWND FindWindow(String winClass, String title);

		HWND FindWindowEx(HWND hwndParent, HWND hwndChildAfter, String lpszClass, String lpszWindow);

		int SendMessage(HWND hWnd, int Msg, int wParam, int lParam);

		int SendMessage(HWND hWnd, int Msg, int wParam, String str);
	}
}
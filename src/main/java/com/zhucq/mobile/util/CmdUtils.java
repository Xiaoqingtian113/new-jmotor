package com.zhucq.mobile.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class CmdUtils {
	public static void runCmd(String cmd) throws IOException {
		Runtime.getRuntime().exec(cmd);
	}

	public static void runCmdAndWait(String cmd, long waitTime) throws IOException, InterruptedException {
		Runtime.getRuntime().exec(cmd);
		Thread.sleep(waitTime);
	}

	public static boolean runWithTimeOut(String cmd, int timeout) throws IOException, InterruptedException {
		Process pro = Runtime.getRuntime().exec(cmd);
		boolean isover = pro.waitFor(timeout, TimeUnit.SECONDS);
		return isover;
	}

	public static String runCmdWithResult(String cmd) throws IOException {
		Process pro = null;
		if (System.getProperty("os.name").toLowerCase().contains("mac")) {
			String[] command = { "/bin/sh", "-c", cmd };
			pro = Runtime.getRuntime().exec(command);
		} else {
			pro = Runtime.getRuntime().exec(cmd);
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(pro.getInputStream(), "gbk"));
		String str = "";
		StringBuffer result = new StringBuffer();
		while ((str = reader.readLine()) != null) {
			result = result.append(str + "\n");
		}
		reader.close();
		return result.toString();
	}
}

package com.zhucq.mobile;

import java.io.IOException;

import com.zhucq.mobile.util.CmdUtils;

public class Port {
	private int portNum;

	public Port(int portNum) {
		this.portNum = portNum;
	}

	public int getPortNum() {
		return portNum;
	}

	public boolean isUsed() {
		String cmd = "cmd /c netstat -nao | findstr " + portNum;
		String result = null;
		try {
			result = CmdUtils.runCmdWithResult(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		boolean isused = false;
		if (result != null && !result.equals(""))
			isused = true;
		return isused;
	}

	public static Port getUnusedPort() {
		int num = (int) Math.round(Math.random() * 1000) + 9000;
		Port tmp = new Port(num);
		if (!tmp.isUsed())
			return tmp;
		else
			return getUnusedPort();
	}
	
//	public static void main(String[] args) {
//		for(int i=0; i<1000; i++){
//			System.out.println(getUnusedPort().getPortNum());
//			
//		}
//	}
}

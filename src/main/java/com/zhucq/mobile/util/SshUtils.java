package com.zhucq.mobile.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.SftpProgressMonitor;

public class SshUtils {
	public static String exec(String cmd, String ip, int port, String user, String pwd) {
		StringBuffer result = new StringBuffer();
		Session session = null;
		ChannelExec channel = null;
		try {
			JSch jsch = new JSch();
			session = jsch.getSession(user, ip, port);
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			config.put("PreferredAuthentications", "publickey,keyboard-interactive,password");
			session.setConfig(config);
			session.setPassword(pwd);
			session.connect();
			channel = (ChannelExec) session.openChannel("exec");
			channel.setCommand(cmd);
			channel.connect();
			InputStream in = channel.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = "";
			while ((line = reader.readLine()) != null) {
				result.append(line).append("\r\n");
			}
		} catch(JSchException | IOException e){
			e.printStackTrace();
		} finally {
			if (channel != null) channel.disconnect();
			if (session != null) session.disconnect();
		}
		return result.toString();
	}
	
	public static void sftpUpload(String src, String dst, String ip, int port, String user, String pwd){
		Session session = null;
		ChannelSftp channel = null;
		try{
			JSch jsch = new JSch();
			session = jsch.getSession(user, ip, port);
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			config.put("PreferredAuthentications", "publickey,keyboard-interactive,password");
			session.setConfig(config);
			session.setPassword(pwd);
			session.connect();
			channel = (ChannelSftp) session.openChannel("sftp");
			channel.connect();
//			channel.put(src, dst, ChannelSftp.OVERWRITE);
			channel.put(src, dst, new UploadProgressMonitor(), ChannelSftp.OVERWRITE);
		}catch(JSchException | SftpException e){
			e.printStackTrace();
		}finally{
			if (channel != null) channel.disconnect();
			if (session != null) session.disconnect();
		}
	}
	
	public static void main(String[] args) {
		SshUtils mgr = new SshUtils();
		mgr.sftpUpload("F:\\TestTool\\VisioProfessional_x64_zh-cn.exe", "/root", "192.168.192.130", 22, "root", "123456");
//		System.out.println(mgr.exec("pwd", "192.168.192.130", 22, "root", "123456"));
//		System.out.println(System.getProperty("java.io.tmpdir"));
	}
}

class UploadProgressMonitor implements SftpProgressMonitor {
    private long transfered;
    private long size;
    private int percent;
    
    @Override
    public void init(int op, String src, String dest, long max) {
    	size = FileUtils.sizeOf(new File(src));
        System.out.println("正在上传Bug截图");
    }
    
    @Override
    public boolean count(long count) {
    	transfered = transfered + count;
    	int tmp = (int) (transfered*100/size);
    	if(tmp >= percent+5){
		    System.out.println("已完成 "+tmp+"%");
		    percent = tmp;
    	}
        return true;
    }

    @Override
    public void end() {
        System.out.println("上传Bug截图结束,文件大小：" + transfered + " Byte");
    }

}
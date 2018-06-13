package com.zhucq.mobile.appium;

public class ImageNotFoundException extends Exception{
	private static final long serialVersionUID = 8323970546974767390L;

	public ImageNotFoundException(String message) {
		super(message);
	}
	public ImageNotFoundException(){
		super("没有找到指定的图片");
	}
}
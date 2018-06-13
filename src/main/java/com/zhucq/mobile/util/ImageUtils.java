package com.zhucq.mobile.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.DMatch;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.FlannBasedMatcher;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.xfeatures2d.SIFT;

public class ImageUtils {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) {
//		int a = 311;
//		int b = 1920;
//		double c = a/b;
//		System.out.println(c);
		
		List<org.openqa.selenium.Point> mp = findAllImage("img/kh3_iknow.png","img/screen(2).png");
		for(org.openqa.selenium.Point p : mp){
			System.out.println(p);
		}
//		System.out.println(findImageSurf("img/bb.png", "img/aa.png", 4));
//		System.out.println("self:  "+compareImageHSV("D:\\beach.jpg","D:\\beach.jpg"));
//		System.out.println("bear:  "+compareImageHSV("D:\\beach.jpg","D:\\bear.jpg"));
//		System.out.println("dog:   "+compareImageHSV("D:\\beach.jpg","D:\\dog.jpg"));
//		System.out.println("lake:  "+compareImageHSV("D:\\beach.jpg","D:\\lake.jpg"));
//		System.out.println("waves: "+compareImageHSV("D:\\beach.jpg","D:\\waves.jpg"));
//		System.out.println("moose: "+compareImageHSV("D:\\beach.jpg","D:\\moose.jpg"));
//		System.out.println("polar: "+compareImageHSV("D:\\beach.jpg","D:\\polar.jpg"));
	}
	
	public static org.openqa.selenium.Point findImage(String tmp, String src){
		return findAllImage(tmp,src).get(0);
	}
	
	public static List<org.openqa.selenium.Point> findAllImage(String tmp, String src){
		List<Point> lp = new ArrayList<Point>();
		List<org.openqa.selenium.Point> slp = new ArrayList<org.openqa.selenium.Point>();
		
		Mat tmpImg = Imgcodecs.imread(tmp);
		Mat srcImg = Imgcodecs.imread(src);
		
		if(!(new File(tmp).exists()) || !(new File(src).exists())){
			System.out.println("图片不存在");
			return slp;
		}
		double tmpSize = tmpImg.rows();
		double srcSize = srcImg.rows();
		double minScale = tmpSize/srcSize;
		double scale = 0.7;
		Mat scaleImg = new Mat();
		srcImg.copyTo(scaleImg);
		while(scale>minScale+0.2){
			System.out.println("scale="+scale);
			lp = findImageOrgin(tmpImg, scaleImg);
			if(!lp.isEmpty()){
				break;
			}
			scale-=0.01;
			scaleImg = resize(srcImg,scale);
		}
		
		for(Point p : lp){
			org.openqa.selenium.Point sp = new org.openqa.selenium.Point((int)(p.x/scale),(int)(p.y/scale));
			slp.add(sp);
		}
		slp.sort(new Comparator<org.openqa.selenium.Point>(){
			@Override
			public int compare(org.openqa.selenium.Point p1, org.openqa.selenium.Point p2) {
				int ret = 0;
				if(p1.y>p2.y){
					ret = 1;
				}
				if(p1.y<p2.y){
					ret = -1;
				}
				if(p1.y==p2.y){
					if(p1.x>p2.x){
						ret = 1;
					}
					if(p1.x<p2.x){
						ret = -1;
					}
					if(p1.x==p2.x){
						ret = 0;
					}
				}
				return ret;
			}
		});
		return slp;
	}
	
	public static List<Point> findImageOrgin(Mat tmpImg, Mat srcImg){
		List<Point> lp = new ArrayList<Point>();
		
		int method = Imgproc.TM_CCOEFF_NORMED;
		
		int result_cols = srcImg.cols() - tmpImg.cols() + 1;
		int result_rows = srcImg.rows() - tmpImg.rows() + 1;
		Mat result = Mat.zeros(result_rows, result_cols, CvType.CV_32FC1);
		Point matchLoc = null;
		int i=1;
		while(true){
			System.out.println("第"+i+"次匹配");
			Imgproc.matchTemplate(srcImg, tmpImg, result, method);
	        Core.MinMaxLocResult mmr = Core.minMaxLoc(result);
	        if( method  == Imgproc.TM_SQDIFF || method == Imgproc.TM_SQDIFF_NORMED ){
	        	matchLoc = mmr.minLoc;
	        }
	        else {
	        	matchLoc = mmr.maxLoc;
	        }
	        System.out.println("maxVal="+mmr.maxVal);
	        if(mmr.maxVal<0.7){
	        	break;
	        }
	        
//	        Imgproc.rectangle(srcImg, matchLoc, new Point(matchLoc.x + tmpImg.cols(),
//	                matchLoc.y + tmpImg.rows()), new Scalar(255, 0, 255), 2, 8, 0);
	        
	        lp.add(new Point(matchLoc.x + tmpImg.width()/2, matchLoc.y + tmpImg.height()/2));
	        
	        Mat mask=makeMask(srcImg,new Rect((int)matchLoc.x,(int)matchLoc.y,tmpImg.cols()+(int)matchLoc.x,tmpImg.rows()+(int)matchLoc.y));
	        srcImg.setTo(new Scalar(0,0,0), mask);
	        
	        i++;
		}
		
		Imgcodecs.imwrite("img/result.png",srcImg);
		
		return lp;
	}
	
	private static Mat makeMask(Mat src, Rect rct) {
		Size sz = new Size(src.cols(), src.rows());
		Mat mask = new Mat(sz, CvType.CV_8UC1);
		mask = Mat.zeros(sz, CvType.CV_8U);
		for (int y = rct.y; y < rct.height; y++) {
			for (int x = rct.x; x < rct.width; x++) {
				mask.put(y, x, 255);
			}
		}
		return mask;
	}
	
	public static Mat resize(Mat srcImg, double scale) {
		int method = Imgproc.INTER_LINEAR;
		Mat dstImg = new Mat();
		Imgproc.resize(srcImg, dstImg, new Size(srcImg.cols()*scale,srcImg.rows()*scale),0,0,method);
		return dstImg;
	}
	
	public static void resize(String src, String dst, double scale) {
		int method = Imgproc.INTER_LINEAR;
		Mat srcImg = Imgcodecs.imread(src);
		Mat dstImg = new Mat();
		Imgproc.resize(srcImg, dstImg, new Size(srcImg.cols()*scale,srcImg.rows()*scale),0,0,method);
		Imgcodecs.imwrite(dst, dstImg);
	}
	
	public static double compareImageRBG(String src,String dst){
		MatOfInt histSize = new MatOfInt(8,8,8);
		MatOfFloat ranges = new MatOfFloat(0,255,0,255,0,255);
		MatOfInt channels = new MatOfInt(0,1,2);
		
		Mat srcImg = Imgcodecs.imread(src,Imgcodecs.IMREAD_COLOR);
		List<Mat> rbg_src = new ArrayList<Mat>();
		Core.split(srcImg, rbg_src);
		Mat srcHist = new Mat();
		Imgproc.calcHist(rbg_src, channels, new Mat(), srcHist, histSize, ranges,false);
		
		Mat dstImg = Imgcodecs.imread(dst,Imgcodecs.IMREAD_COLOR);
		List<Mat> rbg_dst = new ArrayList<Mat>();
		Core.split(dstImg, rbg_dst);
		Mat dstHist = new Mat();
		Imgproc.calcHist(rbg_dst, channels, new Mat(), dstHist, histSize, ranges,false);
		
//		return Imgproc.compareHist(srcHist, dstHist, Imgproc.HISTCMP_CORREL );
		return Imgproc.compareHist(srcHist, dstHist, Imgproc.HISTCMP_CHISQR );
    }
	
	public static double compareImageHSV(String src,String dst){
		MatOfInt histSize = new MatOfInt(8,8);
		MatOfFloat ranges = new MatOfFloat(0,360,0,255);
		MatOfInt channels = new MatOfInt(0,1);
		
		Mat srcImg = Imgcodecs.imread(src,Imgcodecs.IMREAD_COLOR);
		Mat srcGray = new Mat();
		Imgproc.cvtColor(srcImg,srcGray,Imgproc.COLOR_BGR2HSV);
		List<Mat> gray_src = new ArrayList<Mat>();
		gray_src.add(srcGray);
		Mat srcHist = new Mat();
		Imgproc.calcHist(gray_src, channels, new Mat(), srcHist, histSize, ranges,false);
		
		Mat dstImg = Imgcodecs.imread(dst,Imgcodecs.IMREAD_COLOR);
		Mat dstGray = new Mat();
		Imgproc.cvtColor(dstImg,dstGray,Imgproc.COLOR_BGR2HSV);
		List<Mat> gray_dst = new ArrayList<Mat>();
		gray_dst.add(dstGray);
		Mat dstHist = new Mat();
		Imgproc.calcHist(gray_dst, channels, new Mat(), dstHist, histSize, ranges,false);
		
		return Imgproc.compareHist(srcHist, dstHist, Imgproc.HISTCMP_CORREL );
//		return Imgproc.compareHist(srcHist, dstHist, Imgproc.HISTCMP_CHISQR );
//		return Imgproc.compareHist(srcHist, dstHist, Imgproc.HISTCMP_BHATTACHARYYA );
//		return Imgproc.compareHist(srcHist, dstHist, Imgproc.HISTCMP_INTERSECT );
    }
	
	public static double compareImageGray(String src,String dst){
		MatOfInt histSize = new MatOfInt(16);
		MatOfFloat ranges = new MatOfFloat(0,255);
		MatOfInt channels = new MatOfInt(0);
		
		Mat srcImg = Imgcodecs.imread(src,Imgcodecs.IMREAD_COLOR);
		Mat srcGray = new Mat();
		Imgproc.cvtColor(srcImg,srcGray,Imgproc.COLOR_BGR2GRAY);
		List<Mat> gray_src = new ArrayList<Mat>();
		gray_src.add(srcGray);
		Mat srcHist = new Mat();
		Imgproc.calcHist(gray_src, channels, new Mat(), srcHist, histSize, ranges,false);
		
		Mat dstImg = Imgcodecs.imread(dst,Imgcodecs.IMREAD_COLOR);
		Mat dstGray = new Mat();
		Imgproc.cvtColor(dstImg,dstGray,Imgproc.COLOR_BGR2GRAY);
		List<Mat> gray_dst = new ArrayList<Mat>();
		gray_dst.add(dstGray);
		Mat dstHist = new Mat();
		Imgproc.calcHist(gray_dst, channels, new Mat(), dstHist, histSize, ranges,false);
		
		return Imgproc.compareHist(srcHist, dstHist, Imgproc.HISTCMP_CORREL );
//		return Imgproc.compareHist(srcHist, dstHist, Imgproc.HISTCMP_CHISQR );
//		return Imgproc.compareHist(srcHist, dstHist, Imgproc.HISTCMP_BHATTACHARYYA );
//		return Imgproc.compareHist(srcHist, dstHist, Imgproc.HISTCMP_INTERSECT );
    }
	
	public static Point findImageSurf(String object, String scene, long minKeyPointCount){
		Mat imgObject = Imgcodecs.imread(object);
		Mat imgScene = Imgcodecs.imread(scene);
		MatOfKeyPoint keypointObject = new MatOfKeyPoint();
		MatOfKeyPoint keypointScene = new MatOfKeyPoint();
		Mat descriptorObject = new Mat();
		Mat descriptorScene = new Mat();
//		SURF surf = SURF.create();
		SIFT sift = SIFT.create();
		
		sift.detect(imgObject, keypointObject);
		sift.detect(imgScene, keypointScene);
		Mat kpObject = new Mat();
		Mat kpScene = new Mat();
		Features2d.drawKeypoints(imgObject, keypointObject, kpObject,Scalar.all(-1),Features2d.NOT_DRAW_SINGLE_POINTS);
		Features2d.drawKeypoints(imgScene, keypointScene, kpScene,Scalar.all(-1),Features2d.NOT_DRAW_SINGLE_POINTS);
		Imgcodecs.imwrite("img/result/resultObject.png",kpObject);
		Imgcodecs.imwrite("img/result/resultScene.png",kpScene);
		
		sift.detectAndCompute(imgObject, new Mat(), keypointObject, descriptorObject);
		sift.detectAndCompute(imgScene, new Mat(), keypointScene, descriptorScene);
		
		int w = imgObject.width();
		int h = imgObject.height();
		
		FlannBasedMatcher matcher = FlannBasedMatcher.create();
		
		if(keypointObject.rows()<minKeyPointCount){
			System.out.println("模板特征点太少");
			return null;
		}
		if(keypointScene.rows()<minKeyPointCount){
			System.out.println("原图特征点太少");
			return null;
		}
		
		List<MatOfDMatch> matchResult = new ArrayList<MatOfDMatch>();
		matcher.knnMatch(descriptorObject, descriptorScene, matchResult, 2);
		System.out.println("size: "+matchResult.size());
//		MatOfDMatch matcheResult = new MatOfDMatch();
//		matcher.match(descriptorObject,descriptorScene, matcheResult);
		
		List<DMatch> gml = new ArrayList<DMatch>();
		for(MatOfDMatch m : matchResult){
			List<DMatch> dml = m.toList();
			if(dml.get(0).distance<0.98*dml.get(1).distance){
				gml.add(dml.get(0));
			}
		}
		
//		double minDistance=100;
//		for(MatOfDMatch m : matchResult){
//			for(int i=0; i<m.rows(); i++){
//				double dist = m.toList().get(i).distance;
//				if(minDistance>dist){
//					minDistance = dist;
//				}
//			}
//		}
//		List<DMatch> gml = new ArrayList<DMatch>();
//		for(MatOfDMatch m : matchResult){
//			for(int i=0; i<m.rows(); i++){
//				if(m.toList().get(i).distance<Math.max(3*minDistance,0.02)){
//					gml.add(m.toList().get(i));
//				}
//			}
//		}
		
		MatOfDMatch goodMatchs = new MatOfDMatch();
		goodMatchs.fromList(gml);
		
		List<MatOfDMatch> matchList = new ArrayList<MatOfDMatch>();
		matchList.add(goodMatchs);
		List<MatOfByte> matchesMask= new ArrayList<MatOfByte>();
		Mat outImg = new Mat();
		Features2d.drawMatches2(imgObject, keypointObject, imgScene, keypointScene, matchList,
				outImg, Scalar.all(-1), Scalar.all(-1), matchesMask, Features2d.NOT_DRAW_SINGLE_POINTS);
		Imgcodecs.imwrite("img/result/resultMatch.png", outImg);
		
		List<Point> pointObject = new ArrayList<Point>();
		List<Point> pointScene = new ArrayList<Point>();
		MatOfPoint2f p2fObject = new MatOfPoint2f();
		MatOfPoint2f p2fScene = new MatOfPoint2f();
		for (int i=0;i<gml.size();i++) {
			DMatch dMatch = gml.get(i);
			if (dMatch.queryIdx < 0 || dMatch.queryIdx >= keypointObject.toList().size()) {
				continue;
			}
			if (dMatch.trainIdx < 0 || dMatch.trainIdx >= keypointScene.toList().size()) {
				continue;
			}
			pointObject.add(keypointObject.toList().get(dMatch.queryIdx).pt);
			pointScene.add(keypointScene.toList().get(dMatch.trainIdx).pt);
		}
		p2fObject.fromList(pointObject);
		p2fScene.fromList(pointScene);
		Mat fd = Calib3d.findHomography(p2fObject, p2fScene, Calib3d.RANSAC, 5.0);
		
		List<Point> cornerList = new ArrayList<Point>();
		cornerList.add(new Point(0,0));
		cornerList.add(new Point(w,0));
		cornerList.add(new Point(w,h));
		cornerList.add(new Point(0,h));
		
		MatOfPoint2f cornerObject = new MatOfPoint2f();
		MatOfPoint2f cornerScene = new MatOfPoint2f();
		cornerObject.fromList(cornerList);
		Core.perspectiveTransform(cornerObject, cornerScene, fd);
		Point p0 = cornerScene.toList().get(0);
		Point p1 = cornerScene.toList().get(1);
		Point p2 = cornerScene.toList().get(2);
		Point p3 = cornerScene.toList().get(3);
		
		Imgproc.line(imgScene, p0, p1, new Scalar(0, 0, 0),2);
		Imgproc.line(imgScene, p1, p2, new Scalar(0, 0, 0),2);
		Imgproc.line(imgScene, p2, p3, new Scalar(0, 0, 0),2);
		Imgproc.line(imgScene, p3, p0, new Scalar(0, 0, 0),2);
		Imgcodecs.imwrite("img/result/resultRect.png",imgScene);
		
		return new Point((p0.x+p1.x+p2.x+p3.x)/4,(p0.y+p1.y+p2.y+p3.y)/4);
	}

}

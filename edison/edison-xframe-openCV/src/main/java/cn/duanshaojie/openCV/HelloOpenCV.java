package cn.duanshaojie.openCV;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;

import cn.duanshaojie.openCV.utils.ImageViewer;

/**
 * <b>类名：</b>HelloOpenCV.java<br>
 * <p><b>标题：</b>OpenCV 图形透视矫正</p>
 * <p><b>描述：</b>jar包安装详见下链接</p>
 * <p><a href='https://www.w3cschool.cn/opencv/opencv-x4yf28vx.html'>W3Cschool OpenCV之java教程</a></p>
 * @author <font color='blue'>edison_dsj@163.com</font>
 * @date  2017-11-14 下午
 * 
 * 桃之夭夭,灼灼其华
 */

public class HelloOpenCV {
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    Mat mat = Imgcodecs.imread("C:/Users/dev/Desktop/file2-2.jpg");
	    Mat src = new Mat();
	    photoHandle(mat, src);
	}
	/**
	 * 图形矫正的前提:
	 * 轮廓提取技术; 霍夫变换知识; ROI感兴趣区域知识
	 */
	private static void photoHandle(Mat mat,Mat src){
		//灰度处理
	    Imgproc.cvtColor(mat, src, Imgproc.COLOR_BGR2GRAY);
	    ImageViewer imageViewer1 = new ImageViewer(src, "灰度之后");
	    imageViewer1.imshow();
	    
	    //二值化
	    Imgproc.adaptiveThreshold(src, src, 255,
	    		Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, 
	    		Imgproc.THRESH_BINARY_INV, 25, 5);
	    ImageViewer imageViewer2 = new ImageViewer(src, "二值化之后");
	    imageViewer2.imshow();
	    
//	    //平滑处理-高斯滤波
//	    Imgproc.GaussianBlur(src, src, new Size(9,9), 0, 0); 
//	    ImageViewer imageViewer3 = new ImageViewer(src, "高斯滤波");
//	    imageViewer3.imshow();
//	    
//	    //边缘提取
//	    Imgproc.Canny(src, src, 10, 30, 3, true);
//	    ImageViewer imageViewer4 = new ImageViewer(src, "边缘提取");
//	    imageViewer4.imshow();
	    
		//找轮廓
	    List<MatOfPoint> contour = new ArrayList<MatOfPoint>();
	    Imgproc.findContours(src, contour, new Mat(), 
	                        Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE); 
	    
	    //
	    double maxArea = -1;
	    MatOfPoint temp_contour = contour.get(0); // the largest is at the
	                                                // index 0 for starting
	                                                // point
	    MatOfPoint2f approxCurve = new MatOfPoint2f();

	    for (int idx = 0; idx < contour.size(); idx++) {
	        temp_contour = contour.get(idx);
	        double contourarea = Imgproc.contourArea(temp_contour);
	        // compare this contour to the previous largest contour found
	        if (contourarea > maxArea) {
	            // check if this contour is a square
	            MatOfPoint2f new_mat = new MatOfPoint2f(temp_contour.toArray());
	            int contourSize = (int) temp_contour.total();
	            MatOfPoint2f approxCurve_temp = new MatOfPoint2f();
	            Imgproc.approxPolyDP(new_mat, approxCurve_temp, contourSize * 0.05, true);
	            if (approxCurve_temp.total() == 4) {
	                maxArea = contourarea;
	                approxCurve = approxCurve_temp;
	            }
	        }
	    }
	    double[] temp_double;
	    temp_double = approxCurve.get(0, 0);
	    Point p1 = new Point(temp_double[0], temp_double[1]);
	    // Core.circle(imgSource,p1,55,new Scalar(0,0,255));
	    // Imgproc.warpAffine(sourceImage, dummy, rotImage,sourceImage.size());
	    temp_double = approxCurve.get(1, 0);
	    Point p2 = new Point(temp_double[0], temp_double[1]);
	    // Core.circle(imgSource,p2,150,new Scalar(255,255,255));
	    temp_double = approxCurve.get(2, 0);
	    Point p3 = new Point(temp_double[0], temp_double[1]);
	    // Core.circle(imgSource,p3,200,new Scalar(255,0,0));
	    temp_double = approxCurve.get(3, 0);
	    Point p4 = new Point(temp_double[0], temp_double[1]);
	    // Core.circle(imgSource,p4,100,new Scalar(0,0,255));
	    List<Point> source = new ArrayList<Point>();
	    source.add(p1);
	    source.add(p2);
	    source.add(p3);
	    source.add(p4);
	    Mat startM = Converters.vector_Point2f_to_Mat(source);	    
	    //打印图片
	    Mat result = warp(mat, startM);
	    ImageViewer imageViewer = new ImageViewer(result, "最后矫正结果");
	    imageViewer.imshow();
	}
	
	public static Mat warp(Mat inputMat, Mat startM) {

	    int resultWidth = 680;
	    int resultHeight = 1200;
	    //4123
	    Point ocvPOut2 = new Point(0, 0);
	    Point ocvPOut3 = new Point(0, resultHeight);
	    Point ocvPOut4 = new Point(resultWidth, resultHeight);
	    Point ocvPOut1 = new Point(resultWidth, 0);

//	    if (inputMat.height() > inputMat.width()) {
//	        // int temp = resultWidth;
//	        // resultWidth = resultHeight;
//	        // resultHeight = temp;
//
//	        ocvPOut3 = new Point(0, 0);
//	        ocvPOut4 = new Point(0, resultHeight);
//	        ocvPOut1 = new Point(resultWidth, resultHeight);
//	        ocvPOut2 = new Point(resultWidth, 0);
//	    }

	    Mat outputMat = new Mat(resultWidth, resultHeight, CvType.CV_8UC4);

	    List<Point> dest = new ArrayList<Point>();
	    dest.add(ocvPOut1);
	    dest.add(ocvPOut2);
	    dest.add(ocvPOut3);
	    dest.add(ocvPOut4);

	    Mat endM = Converters.vector_Point2f_to_Mat(dest);

	    Mat perspectiveTransform = Imgproc.getPerspectiveTransform(startM, endM);

	    Imgproc.warpPerspective(inputMat, outputMat, perspectiveTransform, new Size(resultWidth, resultHeight), Imgproc.INTER_CUBIC);

	    return outputMat;
	}
}

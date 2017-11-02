package cn.duanshaojie.util.tesseract;

import java.io.File;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
/**
 * <b>类名：</b>TesseractUtil.java<br>
 * <p><b>标题：</b>google OCR</p>
 * <p><b>描述：</b>Tesseract 文字识别</p>
 * @author <font color='blue'>edison_dsj@163.com</font>
 * @date  2017-10-31 下午
 * 
 * 桃之夭夭,灼灼其华
 */

public class TesseractUtil {
	
	public static String parsedText(File imageFile) throws Exception{
		ITesseract instance = new Tesseract();  // JNA Interface Mapping
		instance.setLanguage("chi_sim");
		return instance.doOCR(imageFile);
	}
}

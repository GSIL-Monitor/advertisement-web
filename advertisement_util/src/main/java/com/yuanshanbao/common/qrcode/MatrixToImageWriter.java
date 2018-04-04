package com.yuanshanbao.common.qrcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
 
 
 public final class MatrixToImageWriter {
 
   private static final int BLACK = 0xFF000000;
   private static final int WHITE = 0xFFFFFFFF;
 
   private MatrixToImageWriter() {}
 
   
   public static BufferedImage toBufferedImage(BitMatrix matrix, int bitMap) {
     int width = matrix.getWidth();
     int height = matrix.getHeight();
     BufferedImage image = new BufferedImage(width, height, bitMap);
     for (int x = 0; x < width; x++) {
       for (int y = 0; y < height; y++) {
         image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
       }
     }
     return image;
   }
 
   
   public static void writeToFile(BitMatrix matrix, String format, File file, int bitMap)
       throws IOException {
     BufferedImage image = toBufferedImage(matrix, bitMap);
     if (!ImageIO.write(image, format, file)) {
       throw new IOException("Could not write an image of format " + format + " to " + file);
     }
   }
 
   
   public static void writeToStream(BitMatrix matrix, String format, OutputStream stream, int bitMap)
       throws IOException {
     BufferedImage image = toBufferedImage(matrix, bitMap);
     if (!ImageIO.write(image, format, stream)) {
       throw new IOException("Could not write an image of format " + format);
     }
   }
   
   public static void main(String[] args) {
	   try {
//		   BufferedImage.TYPE_INT_ARGB 32wei tx2
//		   BufferedImage.TYPE_INT_RGB  24wei
		     String content = "http://reg.163.com";
		     MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		     
		     Map<EncodeHintType,String> hints = new HashMap<EncodeHintType,String>();
		     hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		     BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400,hints);
		     File file1 = new File("cjz.jpg");
		     MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1, BufferedImage.TYPE_INT_RGB);
		     
		 } catch (Exception e) {
		     e.printStackTrace();
		 }
}
 
 }
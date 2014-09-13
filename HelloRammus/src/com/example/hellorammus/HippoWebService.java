package com.example.hellorammus;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class HippoWebService {
	public static String TAG = "HIPPO_DEBUG";
	  public static boolean bIfDebug = true;
	  public static String strDelimiter1="<Delimiter1>";
	  
	  /**
	   * getMethod method
	   * 
	   * @param strGetURL URL wanna GET
	   * @param strEncoding HTML Encoding
	   * @return String Retrieve HTTP REQUEST (GET protocol) HTML BODY
	   */
	  public String getMethod(String strGetURL, String strEncoding)
	  {
	    String strReturn="";
	    try
	    {
	      HttpURLConnection urlConnection= null;
	      URL url=new URL(strGetURL);
	      urlConnection=(HttpURLConnection)url.openConnection();
	      urlConnection.setRequestMethod("GET");
	      urlConnection.setDoOutput(true);
	      urlConnection.setDoInput(true);
	      urlConnection.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)");
	      urlConnection.setRequestProperty("Content-type","text/html; charset="+strEncoding);
	      urlConnection.connect();
	      InputStream htmlBODY = urlConnection.getInputStream();
	      
	      if(htmlBODY!=null)
	      {
	        int leng =0;
	        byte[] Data = new byte[100];
	        byte[] totalData = new byte[0];
	        int totalLeg =0;

	        do
	        {
	          leng = htmlBODY.read(Data);
	          if(leng>0)
	          {
	            totalLeg += leng;
	            byte[] temp = new byte[totalLeg];
	            System.arraycopy(totalData, 0, temp, 0, totalData.length);
	            System.arraycopy(Data, 0, temp, totalData.length, leng);
	            totalData = temp;
	          }
	        }while(leng>0);

	        //strReturn = new String(totalData,"UTF-8");
	        strReturn = new String(totalData, strEncoding);
	      }
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	    return strReturn;
	  }
	  
	  /**
	   * postMethod method
	   * 
	   * @param strGetURL URL wanna POST
	   * @param strPostParam POST parameters(Format:postMethod(uriAPI, "p=123&q=456" ,"big5");)
	   * @param strEncoding HTML Encoding
	   * @return String Retrieve HTTP REQUEST (GET protocol) HTML BODY
	   */
	  public String postMethod(String strPostURL, String strPostParam, String strEncoding)
	  {
	    String strReturn="";
	    try
	    {
	      HttpURLConnection urlConnection= null;
	      URL url=new URL(strPostURL);
	      urlConnection=(HttpURLConnection)url.openConnection();
	      urlConnection.setRequestMethod("POST");
	      urlConnection.setDoOutput(true);
	      urlConnection.setDoInput(true);
	      
	      /***********************/
	      urlConnection.setUseCaches (false);
	      urlConnection.setAllowUserInteraction(true);
	      urlConnection.setInstanceFollowRedirects(true);
	      /***********************/
	      
	      urlConnection.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)");
	      //urlConnection.setRequestProperty("Content-type","text/html; charset="+strEncoding);
	      urlConnection.setRequestProperty("Content-type","application/x-www-form-urlencoded");
	      
	      DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());
	      out.write (strPostParam.getBytes());
	      out.flush ();
	      out.close ();
	      
	      //OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream(),strEncoding);
	      //OutputStream out = urlConnection.getOutputStream();
	      //out.write(strPostParam.getBytes());
	      //out.write("\r\n");
	      //out.flush();
	      //out.close();
	            
	      //urlConnection.getOutputStream().write(strPostParam.getBytes());
	      //urlConnection.getOutputStream().flush();
	      //urlConnection.getOutputStream().close();
	      
	      urlConnection.connect();
	      InputStream htmlBODY = urlConnection.getInputStream();
	      
	      if(htmlBODY!=null)
	      {
	        int leng =0;
	        byte[] Data = new byte[100];
	        byte[] totalData = new byte[0];
	        int totalLeg =0;

	        do
	        {
	          leng = htmlBODY.read(Data);
	          if(leng>0)
	          {
	            totalLeg += leng;
	            byte[] temp = new byte[totalLeg];
	            System.arraycopy(totalData, 0, temp, 0, totalData.length);
	            System.arraycopy(Data, 0, temp, totalData.length, leng);
	            totalData = temp;
	          }
	        }while(leng>0);

	        //strReturn = new String(totalData,"UTF-8");
	        strReturn = new String(totalData, strEncoding);
	      }
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	    return strReturn;
	  }
	  
	  /**
	   * postMethodReferer method
	   * 
	   * @param strGetURL URL wanna POST
	   * @param strPostParam POST parameters(Format:postMethod(uriAPI, "p=123&q=456" ,"big5");)
	   * @param strReferer HTTP REQUEST Referer
	   * @param strEncoding HTML Encoding
	   * @return String Retrieve HTTP REQUEST (GET protocol) HTML BODY
	   */
	  public String postMethodReferer(String strPostURL, String strPostParam, String strReferer, String strEncoding)
	  {
	    String strReturn="";
	    try
	    {
	      HttpURLConnection urlConnection= null;
	      URL url=new URL(strPostURL);
	      urlConnection=(HttpURLConnection)url.openConnection();
	      urlConnection.setRequestMethod("POST");
	      urlConnection.setDoOutput(true);
	      urlConnection.setDoInput(true);
	      
	      /***********************/
	      urlConnection.setUseCaches (false);
	      urlConnection.setAllowUserInteraction(true);
	      urlConnection.setInstanceFollowRedirects(true);
	      /***********************/
	      
	      urlConnection.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)");
	      //urlConnection.setRequestProperty("Content-type","text/html; charset="+strEncoding);
	      urlConnection.setRequestProperty("Referer", strReferer);
	      urlConnection.setRequestProperty("Content-type","application/x-www-form-urlencoded");
	      
	      DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());
	      out.write (strPostParam.getBytes());
	      out.flush ();
	      out.close ();
	      
	      //OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream(),strEncoding);
	      //OutputStream out = urlConnection.getOutputStream();
	      //out.write(strPostParam.getBytes());
	      //out.write("\r\n");
	      //out.flush();
	      //out.close();
	            
	      //urlConnection.getOutputStream().write(strPostParam.getBytes());
	      //urlConnection.getOutputStream().flush();
	      //urlConnection.getOutputStream().close();
	      
	      urlConnection.connect();
	      InputStream htmlBODY = urlConnection.getInputStream();
	      
	      if(htmlBODY!=null)
	      {
	        int leng =0;
	        byte[] Data = new byte[100];
	        byte[] totalData = new byte[0];
	        int totalLeg =0;

	        do
	        {
	          leng = htmlBODY.read(Data);
	          if(leng>0)
	          {
	            totalLeg += leng;
	            byte[] temp = new byte[totalLeg];
	            System.arraycopy(totalData, 0, temp, 0, totalData.length);
	            System.arraycopy(Data, 0, temp, totalData.length, leng);
	            totalData = temp;
	          }
	        }while(leng>0);

	        //strReturn = new String(totalData,"UTF-8");
	        strReturn = new String(totalData, strEncoding);
	      }
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	    return strReturn;
	  }
	  
	  /**
	   * getURLBitmap method
	   * 
	   * @param String uriPic Image URL 
	   * @return Bitmap Retrieve HTTP REQUEST (GET protocol) Image Bitmap
	   */
	  public Bitmap getURLBitmap(String uriPic)
	  {
	    Bitmap retBitmap = null;
	    URL objURL;
	    try
	    {
	      objURL = new URL(uriPic);
	      URLConnection conn = objURL.openConnection();
	      conn.connect();
	      InputStream is = conn.getInputStream();
	      
	      if(is!=null)
	      {
	        BufferedInputStream bis = new BufferedInputStream(is);
	        retBitmap = BitmapFactory.decodeStream(bis);
	        bis.close();
	        is.close();
	        return retBitmap;
	      }
	    }
	    catch (Exception e)
	    {
	      // TODO Auto-generated catch block
	      if(bIfDebug)
	      {
	        e.printStackTrace();
	      }
	    }
	    return retBitmap;
	  }
	  
	  /**
	   * getURLBitmapResize method
	   * 
	   * @param String uriPic Image URL 
	   * @param double scale ratio of Target Image ratio
	   * @return Bitmap Retrieve HTTP REQUEST (GET protocol) Image Bitmap
	   */
	  public Bitmap getURLBitmapResize(String uriPic, double scale)
	  {
	    Bitmap retBitmap = null;
	    URL objURL;
	    try
	    {
	      objURL = new URL(uriPic);
	      URLConnection conn = objURL.openConnection();
	      conn.connect();
	      InputStream is = conn.getInputStream();
	      
	      if(is!=null)
	      {
	        BufferedInputStream bis = new BufferedInputStream(is);
	        retBitmap = BitmapFactory.decodeStream(bis);
	        bis.close();
	        is.close();
	        
	        float scaleWidth = 1;
	        float scaleHeight = 1;
	        
	        scaleWidth = (float)(scaleWidth*scale);
	        scaleHeight = (float)(scaleHeight*scale);
	        
	        Matrix matrix = new Matrix();
	        matrix.postScale(scaleWidth, scaleHeight);
	        Bitmap resizeBmp = Bitmap.createBitmap(retBitmap, 0, 0, retBitmap.getWidth(), retBitmap.getHeight(), matrix, true);
	        return resizeBmp;
	      }
	    }
	    catch (Exception e)
	    {
	      // TODO Auto-generated catch block
	      if(bIfDebug)
	      {
	        e.printStackTrace();
	      }
	    }
	    return retBitmap;
	  }
	  
	  /**
	   * eregi method
	   * 
	   * @param String strPat Pattern 
	   * @param String strUnknow source String
	   * @return boolean true or false
	   */
	  public static boolean eregi(String strPat, String strUnknow)
	  {
	    String strPattern = "(?i)"+strPat;
	    Pattern p = Pattern.compile(strPattern);
	    Matcher m = p.matcher(strUnknow);
	    return m.find();
	  }
	  
	  /**
	   * eregi_replace method
	   * 
	   * @param String strFrom Pattern to search 
	   * @param String strTo Pattern to replace of
	   * @param String strTarget source String
	   * @return String replace result String
	   */
	  public String eregi_replace(String strFrom, String strTo, String strTarget)
	  {
	    String strPattern = "(?i)"+strFrom;
	    Pattern p = Pattern.compile(strPattern);
	    Matcher m = p.matcher(strTarget);
	    if(m.find())
	    {
	      return strTarget.replaceAll(strFrom, strTo);
	    }
	    else
	    {
	      return strTarget;
	    }
	  }
	  
	  /**
	   * big52unicode method
	   * 
	   * @param String strBIG5 String in BIG5 code 
	   * @return String result String in UTF-8 code
	   */
	  public String big52unicode(String strBIG5)
	  {
	    String strReturn="";
	    try
	    {
	      strReturn = new String(strBIG5.getBytes("big5"), "UTF-8");
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    return strReturn;
	  }
	  
	  /**
	   * unicode2big5 method
	   * 
	   * @param String strBIG5 String in UTF-8 code 
	   * @return String result String in BIG5 code
	   */
	  public String unicode2big5(String strUTF8)
	  {
	    String strReturn="";
	    try
	    {
	      strReturn = new String(strUTF8.getBytes("UTF-8"), "big5");
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    return strReturn;
	  }
	  
	  /**
	   * ifInternetConnection method
	   * 
	   * @param String strURL URL to test 
	   * @return strEncoding String Encoding
	   * Usage Sample:
	   * HippoWebService myConn = new HippoWebService();
	   * Boolean bRet = myConn.ifInternetConnection("http://code.google.com/intl/zh-TW/apis/chart/", "utf-8");
	   * Result => bRet = true
	   */
	  public boolean ifInternetConnection(String strURL, String strEncoding)
	  {
	    /* set connection timeout in sections */
	    int intTimeout = 10;
	    try
	    {
	      HttpURLConnection urlConnection= null;
	      URL url = new URL(strURL);
	      urlConnection=(HttpURLConnection)url.openConnection();
	      urlConnection.setRequestMethod("GET");
	      urlConnection.setDoOutput(true);
	      urlConnection.setDoInput(true);
	      urlConnection.setRequestProperty
	      (
	        "User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)"
	      );
	      urlConnection.setRequestProperty
	      ("Content-type","text/html; charset="+strEncoding);
	      urlConnection.setConnectTimeout(1000*intTimeout);
	      urlConnection.connect();
	      if (urlConnection.getResponseCode() == 200)
	      {
	        return true;
	      }
	      else
	      {
	        return false;
	      }
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      return false;
	    }
	  }
	  
	  /**
	   * urlencode method
	   * 
	   * @param String strURL URL to encode 
	   * @return strEncoding String Encoding
	   * Usage Example:
	   * HippoWebService myConn = new HippoWebService();
	   * String sRet = myConn.urlencode("DavidLanz", "utf-8");
	   * */
	  public String urlencode(String strURL, String strEncoding)
	  {
	    String strRet = "";
	    try
	    {
	      strRet = URLEncoder.encode(strURL,strEncoding);
	    }
	    catch (Exception e)
	    {
	      strRet = e.toString();
	      e.printStackTrace();
	    }
	    return strRet;
	  }
	  
	  /**
	   * urldecode method
	   * 
	   * @param String strURL URL to encode 
	   * @return strEncoding String Encoding
	   * Usage Example:
	   * HippoWebService myConn = new HippoWebService();
	   * String sRet = myConn.urldecode("%E5%A4%A7%E8%A1%9B", "utf-8");
	   * */
	  public String urldecode(String strURL, String strEncoding)
	  {
	    String strRet = "";
	    try
	    {
	      strRet = URLDecoder.decode(strURL, strEncoding);
	    }
	    catch (Exception e)
	    {
	      strRet = e.toString();
	      e.printStackTrace();
	    }
	    return strRet;
	  }
}

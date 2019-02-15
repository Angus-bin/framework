package com.trevorpage.tpsvg;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.util.EncodingUtils;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import com.romaway.commons.log.Logger;

public class SvgRes1 {

	/***
	 * 通过资源ID获取Drawable
	 * @param context
	 * @param resId
	 * @return
	 */
	public static SVGParserRenderer getSVGParserRenderer(Context context, int resId){
		Resources resources = context.getResources();
		
		// Retrieve ID of the resource
        if (resId == -1 || resId == 0) {
        	return null;
        }

        // Get the input stream for the raw resource
        InputStream inStream = resources.openRawResource(resId);
        if (inStream == null) {
        	return null;
        }

        return new SVGParserRenderer(context, resId);
	}

	/**
	 * 通过svg文件内容获取Drawable
	 * @param context
	 * @param svgContent svg文件内容，可通过该接口解析网络传输过来的svg内容
	 * @return
	 */
	public static SVGParserRenderer getSVGParserRenderer(Context context, String svgContent){
	    if(svgContent == null || svgContent.equals(""))
	        return null;
	    
        return new SVGParserRenderer(context, parseCssStyleToSvg(svgContent));
	}

    /**
	 * 通过svg文件内容获取Drawable
	 * @param context
	 * @param svgContent svg文件内容，可通过该接口解析网络传输过来的svg内容
	 * @return
	 */
	public static SVGParserRenderer getSVGParserRenderer(Context context, String svgContent, String fillColor){
	    if(svgContent == null || svgContent.equals(""))
	        return null;

        return new SVGParserRenderer(context, replaceSVGColor(svgContent, fillColor));
	}
	
	 /**
     * 解析带Css风格颜色为正常的SVG格式
     * @param svgContent
     * @return
     */
    public static String parseCssStyleToSvg(String svgContent){
        try {
            int startIndex =   svgContent.indexOf("<style");
            int endIndex = svgContent.indexOf("</style>") + "</style>".length();

            String styleStr = null;
            //解析CSS style 将颜色替换
            if(startIndex >=0 && endIndex >= 0){
                styleStr = svgContent.substring(startIndex, endIndex);
                if(styleStr != null && !styleStr.equals("")){

                    int startIndex1 = styleStr.indexOf(".");
                    int endIndex1 = styleStr.lastIndexOf(".");
                    String colorStr;
                    int endendIndex = styleStr.lastIndexOf(";}")+";}".length();
                    if(startIndex1 == endIndex1){
                        colorStr = styleStr.substring(startIndex1+".".length(), endendIndex);
                    }else{
                        colorStr = styleStr.substring(startIndex1, endendIndex);
                    }
                    String[] fillColorArr = colorStr.split(";");
                    if(fillColorArr != null){
                        for(int i = 0; i < fillColorArr.length; i++){
                            if(fillColorArr[i].contains("fill:")){
                                String[] nameColorArr = fillColorArr[i].split("fill:");
                                if(nameColorArr != null){
                                    int nameStartIndex = nameColorArr[0].indexOf(".")+".".length();
                                    String name = nameColorArr[0].substring(nameStartIndex, nameColorArr[0].length()-1);
                                    String color = nameColorArr[1];
                                    if(svgContent.contains("class=\""+name+"\""))
                                        svgContent = svgContent.replace("class=\""+name+"\"", "fill=\""+color+"\"");
                                }
                            }
                        }
                    }
                }
                svgContent = svgContent.replace(styleStr, "");
            }
            return svgContent;
        }catch (Exception e){
            Log.e("SvgRes1", "解析可换肤SVG图标失败: "+ e.getMessage());
            return "";
        }
    }

    // 将SVG Drawable中的所有fill填充颜色替换为指定colorStr颜色:
    public static String replaceSVGColor(String svgContent, String colorStr){
        if(TextUtils.isEmpty(svgContent))
            return null;
        if (TextUtils.isEmpty(colorStr))
            return svgContent;
        try {
            String replaceContent = SvgRes1.parseCssStyleToSvg(svgContent);
            String ragex = "fill=\"#[0-9a-fA-F]{6}\"";
            return replaceContent.replaceAll(ragex, "fill=\"" + colorStr + "\"");
        }catch (Exception e){
            Logger.e("SvgRes1", "replaceSVGColor: " + e.getMessage());
            return svgContent;
        }
    }
    
//	/**
//	 * 用于解析CSS结构的Svg图片，并设置新的颜色
//	 * @param context
//	 * @param cssSvgContent
//	 * @param color 内容个格式：colorName{fill:#FF3434 如：fillColor:#FF3434
//	 * @return
//	 */
//	public static Drawable getDrawable(Context context, String cssSvgContent, String[] color){
//	    for(int i = 0; i < color.length;i++){
//	        String[] colorArr = color[i].split(":");
//	        //判断SVG中是否包含需要设置颜色的名字
//	        if(cssSvgContent.contains(colorArr[0]+"{fill:")){
//	            int colorStartIndex = cssSvgContent.indexOf(colorArr[0]+"{fill:");
//	            int colorEndIndex = colorStartIndex +colorArr[0].length() + 13;
//	            String oldColor = cssSvgContent.substring(colorStartIndex, colorEndIndex);
//	            cssSvgContent = cssSvgContent.replace(oldColor, colorArr[0]+"{fill:"+colorArr[1]);
//	        }
//	    }
//        Resources resources = context.getResources();
//        
//        SvgDrawable svg = new SvgDrawable(resources);
//        svg.LoadSvgContent(resources, cssSvgContent);
//        
//        return svg;
//    }
	
	public static String getSvgString(Context context, int resId){
        Resources resources = context.getResources();
        
        // Retrieve ID of the resource
        if (resId == -1 || resId == 0) {
            return null;
        }

        String content = null;
        // Get the input stream for the raw resource
        InputStream is = resources.openRawResource(resId);
        if (is == null) {
            return null;
        }   
        try{
        if (is != null) {
            byte[] buffer = new byte[is.available()];
                is.read(buffer);
    
            // And make a string
            content = EncodingUtils.getString(buffer, "UTF-8");
            buffer = null;
            is.close();
        }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return content;
    }
}

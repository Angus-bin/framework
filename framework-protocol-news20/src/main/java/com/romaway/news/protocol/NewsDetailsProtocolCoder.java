package com.romaway.news.protocol;

import android.content.Context;
import android.text.TextUtils;

import com.romalibs.common.CommonEvent;
import com.romalibs.utils.Logger;
import com.romalibs.utils.MD5;
import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.android.fileutil.FileSystem;
import com.romaway.news.eventbus.NewsEvent;
import com.romaway.news.protocol.util.NewsContent;
import com.romaway.news.sql.NewsCacheDao;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

import de.greenrobot.event.EventBus;

/** 
 * 资讯详情协议
 * @author  万籁唤
 * 创建时间：2016年3月24日 下午2:47:15
 * @version 1.0 
 */
public class NewsDetailsProtocolCoder extends AProtocolCoder<NewsDetailsProtocol> {

	private Context context;
	
	public NewsDetailsProtocolCoder(Context context){
		this.context = context;
	}
	
	@Override
	protected byte[] encode(NewsDetailsProtocol protocol) {
		// TODO Auto-generated method stub
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(NewsDetailsProtocol protocol)
			throws ProtocolParserException {
		// TODO Auto-generated method stub
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();
		Logger.d("NewsDetailsProtocolCoder", "result = " + result);
		//json数据转换成协议数据
		if(jsonToProtoocol(result, protocol) != null) {
//			//将转化成html模板的文件保存路径，提供给webView加载
			protocol.htmlCachePath = toHtmlCachePath(protocol);
		}
	}
	
	@SuppressWarnings("unchecked")
	private NewsDetailsProtocol jsonToProtoocol(String jsonData, final NewsDetailsProtocol protocol){
		
		String content = "";
		try {

			JSONObject jsonObject1 = new JSONObject(jsonData);

			if (jsonObject1.has("data")) {
				JSONObject jsonObject = new JSONObject(jsonObject1.getString("data"));
				if (jsonObject.has("newsId"))//资讯唯一ID
					protocol.resp_newsId = jsonObject.getString("newsId");

				if (jsonObject.has("pdfUrl"))//PDF地址
					protocol.resp_pdfUrl = jsonObject.getString("pdfUrl");

				//处理title 和 时间
				if (jsonObject.has("title"))
					protocol.resp_title = jsonObject.getString("title");
				if (jsonObject.has("time"))
					protocol.resp_time = jsonObject.getString("time");
				if (jsonObject.has("source"))
					protocol.resp_source = jsonObject.getString("source");
				if (jsonObject.has("digest"))
					protocol.resp_digest = jsonObject.getString("digest");

				if (jsonObject.has("shareUrl"))
					protocol.resp_shareUrl = jsonObject.getString("shareUrl");

				if (jsonObject.has("news")) {//资讯列表数据
					JSONObject newsJSONArray = jsonObject.getJSONObject("news");
					//处理body部分
					if(newsJSONArray.has("body")) {
						content = newsJSONArray.getString("body");
						Logger.d("NewsDetailsProtocol", "protocol.content1 :"+content);
						content = content.replaceAll("在任何情况下，华龙证券不对任何人因使用本平台中的任何内容所引致的任何损失负任何责任。", "在任何情况下，容维证券不对任何人因使用本平台中的任何内容所引致的任何损失负任何责任。");
						//content = null;
						//处理body没有数据
						if(TextUtils.isEmpty(content)){
							protocol.resp_body = "";
						}
					}

					//初始化更多股票
					if(newsJSONArray.has("contsCode")) {
						JSONArray contsCodeArr = newsJSONArray.getJSONArray("contsCode");
						if(contsCodeArr.length() == 0)
							protocol.resp_contsCode = null;
						else
							protocol.resp_contsCode = newsJSONArray.getString("contsCode");
					}

					//初始化images，并对body 中的content进行替换处理
					//定义H5图片显示的事件
					NewsEvent.DetailImgShowEvent mDetailImgShowEvent =
							new NewsEvent.DetailImgShowEvent();
					if(newsJSONArray.has("images")){

						JSONArray imagesArr = newsJSONArray.getJSONArray("images");
						int length = imagesArr.length();

						String filePath = FileSystem.getDataCacheRootDir(context,
								"zixun2_0").getAbsolutePath();

						//定义图片浏览事件
						CommonEvent.ImgPhotoPreviewEvent imgPhotoPreviewEvent =
								new CommonEvent.ImgPhotoPreviewEvent();

						mDetailImgShowEvent.replaceimages = new String[length];
						mDetailImgShowEvent.webImageUrls = new String[length];
						mDetailImgShowEvent.imageSavePaths = new String[length];

						for(int m = 0; m < length; m++){
							String imageTemplate = "<img id=\"<!--id-->\" onclick=\"onImageClick(this)\"  linkType=\"<!--linkType-->\" src=\"<!--src-->\" border=\"<!--border-->\" alt=\"<!--alt-->\" style=\"<!--style-->\" width=\"<!--width-->\" height=\"<!--height-->\"/>";
							JSONObject imagesJSONObject = imagesArr.getJSONObject(m);

							//设置img ID
							imageTemplate = imageTemplate.replaceAll("<!--id-->", "IMG#"+m);

							String linkType = "";
							if(imagesJSONObject.has("linkType")){
								linkType = imagesJSONObject.getString("linkType");
								imageTemplate =
										imageTemplate.replaceAll("<!--linkType-->", linkType);
							}

							String imgNetUrl = "";
							if(imagesJSONObject.has("src")){
								imgNetUrl = imagesJSONObject.getString("src");

								String savePath = filePath + File.separator +
										linkType + "_" + MD5.getInstance().getMD5(imgNetUrl);
								File savePathFile = new File(savePath);
								Logger.d("tag", "mDetailImgShowEvent imgNetUrl= "+imgNetUrl +",savePath = "+savePath);
								//事件保存缓存的路径，用于进入相册浏览图片时使用
								//if(savePath.contains("toPhoto_"))
								imgPhotoPreviewEvent.imagePathList.add("file://"+savePath);

								if(savePathFile.exists()){//用缓存中的
									imageTemplate =
											imageTemplate.replaceAll("<!--src-->", savePath);

									//回去处理该事件
									mDetailImgShowEvent.replaceimages[m] = null;
									mDetailImgShowEvent.webImageUrls[m] = null;
									mDetailImgShowEvent.imageSavePaths[m] = null;

								}else{//否则用默认的占位图
									imageTemplate =
											imageTemplate.replaceAll("<!--src-->",
													NewsContent.ASSET_PATH_HEADER +
															NewsContent.ASSET_DETAILS_PATH +
															"kds_news_empty_icon.png");

									//只在没有缓存的时候回去处理该事件
									mDetailImgShowEvent.replaceimages[m] = "replaceimageIMG#"+m;
									mDetailImgShowEvent.webImageUrls[m] = imgNetUrl;
									mDetailImgShowEvent.imageSavePaths[m] = savePath;
								}

							}else{
								Logger.d("tag", "onDetailImgShowEventPostThread 不存在src属性");
							}
							String value = imagesJSONObject.has("border")? imagesJSONObject.getString("border") : "";
							imageTemplate =
									imageTemplate.replaceAll("<!--border-->", value);

							value = imagesJSONObject.has("alt")? imagesJSONObject.getString("alt") : "";
							imageTemplate =
									imageTemplate.replaceAll("<!--alt-->", value);

							value = imagesJSONObject.has("style")? imagesJSONObject.getString("style") : "";
							imageTemplate =
									imageTemplate.replaceAll("<!--style-->", value+";border:0px solid transparent;");// 默认去掉边框

							value = imagesJSONObject.has("width")? imagesJSONObject.getString("width") : "";
							imageTemplate =
									imageTemplate.replaceAll("<!--width-->", value);

							value = imagesJSONObject.has("height")? imagesJSONObject.getString("height") : "";
							imageTemplate =
									imageTemplate.replaceAll("<!--height-->", value);


							content = content.replace("<!--IMG#"+ m +"-->", imageTemplate);
						}
						//发送H5图片显示的粘性事件
						Logger.d("tag", "onDetailImgShowEventPostThread postSticky");
						//发送图片浏览粘性事件
						EventBus.getDefault().postSticky(imgPhotoPreviewEvent);
						mDetailImgShowEvent.eventType = "images";
						EventBus.getDefault().postSticky(mDetailImgShowEvent);
					}

					// 处理视频播放
					if(newsJSONArray.has("videos")){
						JSONArray imagesArr = newsJSONArray.getJSONArray("videos");
						int length = imagesArr.length();

						String filePath = FileSystem.getDataCacheRootDir(context,
								"zixun2_0").getAbsolutePath();

						for(int m = 0; m < length; m++){
							JSONObject imagesJSONObject = imagesArr.getJSONObject(m);

//						String linkType = "";
//						if(imagesJSONObject.has("linkType"))
//							linkType = imagesJSONObject.getString("linkType");

							if(imagesJSONObject.has("videoUrl"))
								protocol.resp_videoUrl = imagesJSONObject.getString("videoUrl");

							if(imagesJSONObject.has("width"))
								protocol.resp_videoWidth = imagesJSONObject.getString("width");

							if(imagesJSONObject.has("height"))
								protocol.resp_videoHeight = imagesJSONObject.getString("height");

							if(imagesJSONObject.has("imgUrl")){
								protocol.resp_videoImageUrl = imagesJSONObject.getString("imgUrl");
//							String savePath = filePath + File.separator +
//									linkType + "_" + MD5.getInstance().getMD5(imgNetUrl);
//							File savePathFile = new File(savePath);
//							mDetailImgShowEvent.videoImageUrl = imgNetUrl;
//							mDetailImgShowEvent.videoImageSavePath = savePath;
							}
						}

//					mDetailImgShowEvent.eventType = "video";
//					EventBus.getDefault().postSticky(mDetailImgShowEvent);
					}

					//初始化links，并对body 中的content进行替换处理
					if(newsJSONArray.has("links")){
						JSONArray linksArr = newsJSONArray.getJSONArray("links");
						int length = linksArr.length();
						for(int m = 0; m < length; m++){
							JSONObject linksJSONObject = linksArr.getJSONObject(m);
							String linksTemplate = "<span class=\"linkSpan\" onclick=\"onLinkSpanClick(this)\" linkType=\"<!--linkType-->\" stockType=\"<!--stockType-->\" stockId=\"<!--stockId-->\" linkUrl=\"<!--linkUrl-->\" stockName=\"<!--stockName-->\"><!--text--></span>";

							String value = linksJSONObject.has("url")? linksJSONObject.getString("url") : "";
							linksTemplate =
									linksTemplate.replaceAll("<!--linkUrl-->", value);

							value = linksJSONObject.has("code")? linksJSONObject.getString("code") : "";
							linksTemplate =
									linksTemplate.replaceAll("<!--stockId-->", value);

							value = linksJSONObject.has("linkType")? linksJSONObject.getString("linkType") : "";
							linksTemplate =
									linksTemplate.replaceAll("<!--linkType-->", value);

							value = linksJSONObject.has("type")? linksJSONObject.getString("type") : "";
							linksTemplate =
									linksTemplate.replaceAll("<!--stockType-->", value);

							value = linksJSONObject.has("name")? linksJSONObject.getString("name") : "";
							linksTemplate =
									linksTemplate.replaceAll("<!--text-->", value);
							linksTemplate =
									linksTemplate.replaceAll("<!--stockName-->", value);

							content = content.replace("<!--LINK#"+ m +"-->", linksTemplate);
						}
					}
				}

				protocol.resp_body = content;//StringUtils.replaceBlank(content);
				Logger.d("tag", "protocol.resp_body :"+protocol.resp_body);

				// 是否有内存的标示
				if(!TextUtils.isEmpty(protocol.resp_newsId))
					protocol.isHasMemory = true;
				else
					protocol.isHasMemory = false;

			} else {
				if (jsonObject1.has("newsId"))//资讯唯一ID
					protocol.resp_newsId = jsonObject1.getString("newsId");

				if (jsonObject1.has("pdfUrl"))//PDF地址
					protocol.resp_pdfUrl = jsonObject1.getString("pdfUrl");

				//处理title 和 时间
				if (jsonObject1.has("title"))
					protocol.resp_title = jsonObject1.getString("title");
				if (jsonObject1.has("time"))
					protocol.resp_time = jsonObject1.getString("time");
				if (jsonObject1.has("source"))
					protocol.resp_source = jsonObject1.getString("source");
				if (jsonObject1.has("digest"))
					protocol.resp_digest = jsonObject1.getString("digest");

				if (jsonObject1.has("shareUrl"))
					protocol.resp_shareUrl = jsonObject1.getString("shareUrl");

				if (jsonObject1.has("news")) {//资讯列表数据
					JSONObject newsJSONArray = jsonObject1.getJSONObject("news");
					//处理body部分
					if(newsJSONArray.has("body")) {
						content = newsJSONArray.getString("body");
						Logger.d("NewsDetailsProtocol", "protocol.content :"+content);
						content = content.replaceAll("在任何情况下，华龙证券不对任何人因使用本平台中的任何内容所引致的任何损失负任何责任。", "在任何情况下，容维证券不对任何人因使用本平台中的任何内容所引致的任何损失负任何责任。");
						//content = null;
						//处理body没有数据
						if(TextUtils.isEmpty(content)){
							protocol.resp_body = "";
						}
					}

					//初始化更多股票
					if(newsJSONArray.has("contsCode")) {
						JSONArray contsCodeArr = newsJSONArray.getJSONArray("contsCode");
						if(contsCodeArr.length() == 0)
							protocol.resp_contsCode = null;
						else
							protocol.resp_contsCode = newsJSONArray.getString("contsCode");
					}

					//初始化images，并对body 中的content进行替换处理
					//定义H5图片显示的事件
					NewsEvent.DetailImgShowEvent mDetailImgShowEvent =
							new NewsEvent.DetailImgShowEvent();
					if(newsJSONArray.has("images")){

						JSONArray imagesArr = newsJSONArray.getJSONArray("images");
						int length = imagesArr.length();

						String filePath = FileSystem.getDataCacheRootDir(context,
								"zixun2_0").getAbsolutePath();

						//定义图片浏览事件
						CommonEvent.ImgPhotoPreviewEvent imgPhotoPreviewEvent =
								new CommonEvent.ImgPhotoPreviewEvent();

						mDetailImgShowEvent.replaceimages = new String[length];
						mDetailImgShowEvent.webImageUrls = new String[length];
						mDetailImgShowEvent.imageSavePaths = new String[length];

						for(int m = 0; m < length; m++){
							String imageTemplate = "<img id=\"<!--id-->\" onclick=\"onImageClick(this)\"  linkType=\"<!--linkType-->\" src=\"<!--src-->\" border=\"<!--border-->\" alt=\"<!--alt-->\" style=\"<!--style-->\" width=\"<!--width-->\" height=\"<!--height-->\"/>";
							JSONObject imagesJSONObject = imagesArr.getJSONObject(m);

							//设置img ID
							imageTemplate = imageTemplate.replaceAll("<!--id-->", "IMG#"+m);

							String linkType = "";
							if(imagesJSONObject.has("linkType")){
								linkType = imagesJSONObject.getString("linkType");
								imageTemplate =
										imageTemplate.replaceAll("<!--linkType-->", linkType);
							}

							String imgNetUrl = "";
							if(imagesJSONObject.has("src")){
								imgNetUrl = imagesJSONObject.getString("src");

								String savePath = filePath + File.separator +
										linkType + "_" + MD5.getInstance().getMD5(imgNetUrl);
								File savePathFile = new File(savePath);
								Logger.d("tag", "mDetailImgShowEvent imgNetUrl= "+imgNetUrl +",savePath = "+savePath);
								//事件保存缓存的路径，用于进入相册浏览图片时使用
								//if(savePath.contains("toPhoto_"))
								imgPhotoPreviewEvent.imagePathList.add("file://"+savePath);

								if(savePathFile.exists()){//用缓存中的
									imageTemplate =
											imageTemplate.replaceAll("<!--src-->", savePath);

									//回去处理该事件
									mDetailImgShowEvent.replaceimages[m] = null;
									mDetailImgShowEvent.webImageUrls[m] = null;
									mDetailImgShowEvent.imageSavePaths[m] = null;

								}else{//否则用默认的占位图
									imageTemplate =
											imageTemplate.replaceAll("<!--src-->",
													NewsContent.ASSET_PATH_HEADER +
															NewsContent.ASSET_DETAILS_PATH +
															"kds_news_empty_icon.png");

									//只在没有缓存的时候回去处理该事件
									mDetailImgShowEvent.replaceimages[m] = "replaceimageIMG#"+m;
									mDetailImgShowEvent.webImageUrls[m] = imgNetUrl;
									mDetailImgShowEvent.imageSavePaths[m] = savePath;
								}

							}else{
								Logger.d("tag", "onDetailImgShowEventPostThread 不存在src属性");
							}
							String value = imagesJSONObject.has("border")? imagesJSONObject.getString("border") : "";
							imageTemplate =
									imageTemplate.replaceAll("<!--border-->", value);

							value = imagesJSONObject.has("alt")? imagesJSONObject.getString("alt") : "";
							imageTemplate =
									imageTemplate.replaceAll("<!--alt-->", value);

							value = imagesJSONObject.has("style")? imagesJSONObject.getString("style") : "";
							imageTemplate =
									imageTemplate.replaceAll("<!--style-->", value+";border:0px solid transparent;");// 默认去掉边框

							value = imagesJSONObject.has("width")? imagesJSONObject.getString("width") : "";
							imageTemplate =
									imageTemplate.replaceAll("<!--width-->", value);

							value = imagesJSONObject.has("height")? imagesJSONObject.getString("height") : "";
							imageTemplate =
									imageTemplate.replaceAll("<!--height-->", value);


							content = content.replace("<!--IMG#"+ m +"-->", imageTemplate);
						}
						//发送H5图片显示的粘性事件
						Logger.d("tag", "onDetailImgShowEventPostThread postSticky");
						//发送图片浏览粘性事件
						EventBus.getDefault().postSticky(imgPhotoPreviewEvent);
						mDetailImgShowEvent.eventType = "images";
						EventBus.getDefault().postSticky(mDetailImgShowEvent);
					}

					// 处理视频播放
					if(newsJSONArray.has("videos")){
						JSONArray imagesArr = newsJSONArray.getJSONArray("videos");
						int length = imagesArr.length();

						String filePath = FileSystem.getDataCacheRootDir(context,
								"zixun2_0").getAbsolutePath();

						for(int m = 0; m < length; m++){
							JSONObject imagesJSONObject = imagesArr.getJSONObject(m);

//						String linkType = "";
//						if(imagesJSONObject.has("linkType"))
//							linkType = imagesJSONObject.getString("linkType");

							if(imagesJSONObject.has("videoUrl"))
								protocol.resp_videoUrl = imagesJSONObject.getString("videoUrl");

							if(imagesJSONObject.has("width"))
								protocol.resp_videoWidth = imagesJSONObject.getString("width");

							if(imagesJSONObject.has("height"))
								protocol.resp_videoHeight = imagesJSONObject.getString("height");

							if(imagesJSONObject.has("imgUrl")){
								protocol.resp_videoImageUrl = imagesJSONObject.getString("imgUrl");
//							String savePath = filePath + File.separator +
//									linkType + "_" + MD5.getInstance().getMD5(imgNetUrl);
//							File savePathFile = new File(savePath);
//							mDetailImgShowEvent.videoImageUrl = imgNetUrl;
//							mDetailImgShowEvent.videoImageSavePath = savePath;
							}
						}

//					mDetailImgShowEvent.eventType = "video";
//					EventBus.getDefault().postSticky(mDetailImgShowEvent);
					}

					//初始化links，并对body 中的content进行替换处理
					if(newsJSONArray.has("links")){
						JSONArray linksArr = newsJSONArray.getJSONArray("links");
						int length = linksArr.length();
						for(int m = 0; m < length; m++){
							JSONObject linksJSONObject = linksArr.getJSONObject(m);
							String linksTemplate = "<span class=\"linkSpan\" onclick=\"onLinkSpanClick(this)\" linkType=\"<!--linkType-->\" stockType=\"<!--stockType-->\" stockId=\"<!--stockId-->\" linkUrl=\"<!--linkUrl-->\" stockName=\"<!--stockName-->\"><!--text--></span>";

							String value = linksJSONObject.has("url")? linksJSONObject.getString("url") : "";
							linksTemplate =
									linksTemplate.replaceAll("<!--linkUrl-->", value);

							value = linksJSONObject.has("code")? linksJSONObject.getString("code") : "";
							linksTemplate =
									linksTemplate.replaceAll("<!--stockId-->", value);

							value = linksJSONObject.has("linkType")? linksJSONObject.getString("linkType") : "";
							linksTemplate =
									linksTemplate.replaceAll("<!--linkType-->", value);

							value = linksJSONObject.has("type")? linksJSONObject.getString("type") : "";
							linksTemplate =
									linksTemplate.replaceAll("<!--stockType-->", value);

							value = linksJSONObject.has("name")? linksJSONObject.getString("name") : "";
							linksTemplate =
									linksTemplate.replaceAll("<!--text-->", value);
							linksTemplate =
									linksTemplate.replaceAll("<!--stockName-->", value);

							content = content.replace("<!--LINK#"+ m +"-->", linksTemplate);
						}
					}
				}

				protocol.resp_body = content;//StringUtils.replaceBlank(content);
				Logger.d("tag", "protocol.resp_body :"+protocol.resp_body);

				// 是否有内存的标示
				if(!TextUtils.isEmpty(protocol.resp_newsId))
					protocol.isHasMemory = true;
				else
					protocol.isHasMemory = false;

			}


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		Logger.d("tag", "2-jsonToProtoocoljsonToProtoocoljsonToProtoocol");
		return protocol;
	}
	
	@Override
	protected void saveCache(String key, NewsDetailsProtocol protocol)
			throws ProtocolParserException {
		// TODO Auto-generated method stub
		super.saveCache(key, protocol);
		
		NewsCacheDao mNewsCacheDao = new NewsCacheDao(context, protocol.req_cacheDbName);
		
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();
		
		if(!TextUtils.isEmpty(result)) 
			mNewsCacheDao.updateDetails(protocol.req_funType, protocol.req_newsId,result);
	}
	
	@Override
	protected NewsDetailsProtocol readCache(String key,
			NewsDetailsProtocol protocol) throws ProtocolParserException {
		// TODO Auto-generated method stub
		
		NewsCacheDao mNewsCacheDao = new NewsCacheDao(context, protocol.req_cacheDbName);
		
		//json数据转换成协议数据
		String json =  mNewsCacheDao.selectDetails(
				protocol.req_funType, protocol.req_newsId);
		
		System.out.println("NewsDetailsProtocol readCache: "+json);
		
		if(TextUtils.isEmpty(json)) 
			return null;
		
		if(jsonToProtoocol(json, protocol) != null)
			protocol.htmlCachePath = toHtmlCachePath(protocol);
		return protocol;//super.readCache(key, protocol);
	}


	private String toHtmlCachePath(NewsDetailsProtocol protocol){
		
		String htmlDoc = null;
		try{
			htmlDoc = FileSystem.getFromAssets(context,
					NewsContent.ASSET_DETAILS_PATH + "newsDetailTemplate.html");
			Logger.d("toHtmlCachePath", "htmlDoc = " + htmlDoc);
			//替换模板中html标签类型
			htmlDoc = htmlDoc
					.replaceAll("<!--title-->", protocol.resp_title)
					.replaceAll("<!--time-->", protocol.resp_time)
					.replaceAll("<!--source-->", protocol.resp_source)
					.replaceAll("<!--body-->", protocol.resp_body);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		// 保存html文件到文件系统缓存
		String filePath = NewsContent.getFilesAbsoluteDir(context) + "newsDetailTemplate.html";
		FileSystem.saveTextToFile(htmlDoc, filePath);
		String fullPath = NewsContent.getFilesFullDir(context) + "newsDetailTemplate.html";
		
		// 返回html文件全路径
		return fullPath;
	}
}

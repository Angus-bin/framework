package com.romaway.news.protocol.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.romaway.news.protocol.info.Item_newsListItemData;
import com.romaway.news.protocol.info.NewsListGroupContiner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 分组
 * @author wanlh
 *
 */
public class MultiGroup {

	@SuppressLint("SimpleDateFormat")
	public static SimpleDateFormat yy_mm_dd = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 
	 * @param targetList 等待分组的list
	 * @param isMultiGroup 是否分多组 false 是不分组或者只分一组
	 * @return 返回分组结果
	 */
	public List<NewsListGroupContiner> toGroup(
			ArrayList<Item_newsListItemData> targetList, boolean isMultiGroup ) {
		
		List<NewsListGroupContiner> result = new ArrayList<NewsListGroupContiner>();
		@SuppressWarnings("unchecked")
		List<Item_newsListItemData> target = 
				(List<Item_newsListItemData>) targetList.clone();
		
		for (int i = 0; i < target.size();) {
			
			Item_newsListItemData preItem = target.get(0);
			String preItemTime = ssTodd(preItem.time, yy_mm_dd);
//			preItem.time = MultiGroup.getCustomFormat(preItem.time);// 处理时间为当天的就仅仅显示时间而不显示日期
			target.remove(preItem);
			NewsListGroupContiner gc = new NewsListGroupContiner();
			gc.getGroupList().add(preItem);
			gc.setGroupName(preItemTime);
			
			for (int j = 0; j < target.size();) {
				
				Item_newsListItemData nextItem = target.get(j);
				String nextItemTime = ssTodd(nextItem.time, yy_mm_dd);
//				nextItem.time = MultiGroup.getCustomFormat(nextItem.time);// 处理时间为当天的就仅仅显示时间而不显示日期
				
				if(!isMultiGroup || // 不进行多组分，那么只分一组
						nextItemTime.equals(preItemTime)){// 相同，分组，并加入到组容器集合
					
					gc.getGroupList().add(nextItem);
					target.remove(nextItem);
					
				}/*else if (nextItemTime.equals(preItemTime)) {
					
					gc.getGroupList().add(nextItem);
					target.remove(nextItem);
				} */else {
					j++;
				}
			}
			
			result.add(gc);
		}
		return result;
	}
	
	public static String ssTodd(String ddss, SimpleDateFormat sdf) {
		if(TextUtils.isEmpty(ddss))
			return "";
	    try
	    {
	      Date date = sdf.parse(ddss);
	      return sdf.format(date);
	    }
	    catch (ParseException e)
	    {
	      e.printStackTrace();
	    }
	    return null;
    }
	
	/**
	 * 
	 * @param date 格式：2016-02-01 12:23
	 * @return
	 */
	public static String getHHMM(String date) {
		String time = date;
		try{
			time = date.substring(11, 16);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return time;
	}
	
	/**
	 * 
	 * @param dateTime 格式：2016-02-01 12:23
	 * @return
	 */
	public static String getCustomFormat(String dateTime) {
		try{
			String date = yy_mm_dd.format(new Date());
			String hhmm = MultiGroup.getHHMM(dateTime);
			String timeDay = dateTime.substring(0, 10);
//			System.out.println("timeDay:"+timeDay+",date = "+date);
			if(timeDay.equals(date)) {
//				System.out.println("====timeDay:"+timeDay+",date:"+date);
				return hhmm;
			}

			return timeDay;
		}catch(Exception e) {

		}

		return "时间解析异常";
	}

	/**
	 * 格式化时间
	 * @param time 2013-08-13 15:41
	 * @return
	 */
	private static String formatDateTime(String time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(time==null ||"".equals(time)){
			return "";
		}
		Date date = null;
		try {
			date = format.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Calendar current = Calendar.getInstance();
		
		Calendar today = Calendar.getInstance();	//今天
		
		today.set(Calendar.YEAR, current.get(Calendar.YEAR));
		today.set(Calendar.MONTH, current.get(Calendar.MONTH));
		today.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH));
		//  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
		today.set( Calendar.HOUR_OF_DAY, 0);
		today.set( Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		
//		Calendar yesterday = Calendar.getInstance();	//昨天
//		
//		yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
//		yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
//		yesterday.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH)-1);
//		yesterday.set( Calendar.HOUR_OF_DAY, 0);
//		yesterday.set( Calendar.MINUTE, 0);
//		yesterday.set(Calendar.SECOND, 0);
//		
		current.setTime(date);
		
		if(current.after(today)){
			return "今天 "+time.split(" ")[1];
		}/*else if(current.before(today) && current.after(yesterday)){
			
			return "昨天 "+time.split(" ")[1];
		}*/else{
//			int index = time.indexOf("-");
			String date1 = time.substring(0, 10);
			return date1;
		}
	}
}

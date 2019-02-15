package com.romaway.commons.utils;

import java.util.List;
/**
 * 插入排序、拖动排序、置顶排序、置底排序工具类
 * @author wanlh
 *
 */
public class SortUtil {

	/**
	 * 从小到大插入排序
	 * @param list
	 * @return
	 */
	public static <T> List<T> insertSortArray(List<T> list)
	{ 
		
		for(int i=1;i<list.size();i++)
		{ 
		    T temp=list.get(i);
		    int j=i-1; 
		    
		    if(temp instanceof Integer){
		    	while (j>=0 && (Integer)list.get(j) > (Integer)temp)
				{ 
					list.set(j + 1,  list.get(j)); 
				    j--; 
				} 
				list.set(j + 1,  temp);
				
		    }
		} 
		
		return list;
	} 
	
	/**
	 * 拖动排序
	 * @param list
	 * @param from
	 * @param to
	 * @return
	 */
	public static List<String> dragSortGroupNameList(final List<String> list, int from, int to){
		
		if(from == to)
			return list;
		
		List<String> teampList = list;
		String tempData = list.get(from);
		
		if(from < to){
			for(int i = from + 1; i <= to; i++){
				teampList.set(i-1, list.get(i));
			}
			
		}else if(from > to){
			for(int i = from-1; i >= to; i--) {
				teampList.set(i+1, list.get(i));
			}
		}
		
		teampList.set(to, tempData);
		
		return teampList;
	}
	
	/**
	 * 拖动排序
	 * @param list
	 * @param from
	 * @param to
	 * @return
	 */
	public static <T> List<T> sortDragForList(final List<T> list, int from, int to){
		
		if(from == to)
			return list;
		
		List<T> newGroupNameList = list;
		T tempData = list.get(from);
		
		if(from < to){
			for(int i = from + 1; i <= to; i++){
				newGroupNameList.set(i-1, list.get(i));
			}
			
		}else if(from > to){
			for(int i = from-1; i >= to; i--) {
				newGroupNameList.set(i+1, list.get(i));
			}
		}
		
		newGroupNameList.set(to, tempData);
		
		return newGroupNameList;
	}
	
	/**
	 * 置顶功能
	 * @param <T>
	 * @param itemList
	 * @param from 其实项索引
	 * @return
	 */
	public static <T> List<T> setItem2Top(final List<T> itemList, int from){
		
		if(from == 0)
			return itemList;
		
		List<T> newItemList = itemList;
		T tempData = itemList.get(from);
		
		for(int i = from-1; i >= 0; i--)
			newItemList.set(i+1, itemList.get(i));
		
		newItemList.set(0, tempData);
		
		return newItemList;
	}
	
	/**
	 * 置底功能
	 * @param <T>
	 * @param itemList
	 * @param from 其实项索引
	 * @return
	 */
	public static <T> List<T> setItem2Bottom(final List<T> itemList, int from){
		
		int count = itemList.size();
		if(from >= count-1)
			return itemList;
		
		List<T> newItemList = itemList;
		T tempData = itemList.get(from);
		
		for(int i = from+1; i < count; i++)
			newItemList.set(i-1, itemList.get(i));
		
		newItemList.set(count-1, tempData);
		
		return newItemList;
	}
	
	/**
	 * 置顶功能
	 * @param <T>
	 * @param itemArray
	 * @param from 其实项索引
	 * @return
	 */
	public static <T> T[] setItem2Top(final T[] itemArray, int from){
		
		if(from == 0)
			return itemArray;
		
		T[] newArray = itemArray;
		T tempData = itemArray[from];
		
		for(int i = from-1; i >= 0; i--)
			newArray[i+1] = itemArray[i];
		
		newArray[0] = tempData;
		
		return newArray;
	}
	
	/**
	 * 置顶功能
	 * @param <T>
	 * @param itemArray
	 * @param from 其实项索引
	 * @return
	 */
	public static <T> T[][] setItem2Top(final T[][] itemArray, int from){
		
		if(from == 0)
			return itemArray;
		
		T[][] newArray = itemArray;
		T[] tempData = itemArray[from];
		
		for(int i = from-1; i >= 0; i--)
			newArray[i+1] = itemArray[i];
		
		newArray[0] = tempData;
		
		return newArray;
	}
}

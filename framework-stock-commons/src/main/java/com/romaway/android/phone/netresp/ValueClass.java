package com.romaway.android.phone.netresp;

public class ValueClass {

	/**存储数据值*/
	protected String[][] mDataValue;
	/**存储数据值的颜色*/
	protected int[][] mDataColor;
	
	private HQEnumValue[] dataArray;
	public ValueClass(HQEnumValue[] dataArray){
		this.dataArray = dataArray;
	}
	
	/**
	 * 获取每组数据中的个数
	 * @return
	 */
	public int getLength(){
		return dataArray.length;
	}
	
	/**
	 * 获取数据的组数
	 * @return
	 */
	public int getGroupLength(){
		return mDataValue.length;
	}
	
	/**
	 * 获取某行某列的数据
	 * @param index  某行
	 * @param key  某一列的标记key
	 * @return
	 */
	public String getValue(int index, HQEnumValue key){
		try{
			for(int i = 0; i < dataArray.length; i++){
				if(key == dataArray[i])
					return mDataValue[index][i];
			}
		}catch(Exception e){
			
		}
		return null;
	}
	/**
	 * 获取某行某列数据颜色 
	 * @param index  某行
	 * @param key  某一列的标记key
	 * @return
	 */
	public int getColor(int index, HQEnumValue key){
		for(int i = 0; i < dataArray.length; i++){
			if(key == dataArray[i])
				return mDataColor[index][i];
		}
		
		return 0;
	}
}

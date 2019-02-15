package roma.romaway.commons.android.tougu;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * loader加载管理器
 * @author wanlh
 *
 */
public class LoaderManager {

	private List<com.romaway.framework.view.RomaWayBaseViewLoader> loaderList;
	private com.romaway.framework.view.RomaWayBaseViewLoader currentLoader;
	private int currentLoaderIndex = 0;
	
	public LoaderManager(Context context){
		loaderList = new ArrayList<com.romaway.framework.view.RomaWayBaseViewLoader>();
	}
	
	/**
	 * 添加loader
	 * @param loader
	 */
	public void add(com.romaway.framework.view.RomaWayBaseViewLoader loader){
		loaderList.add(loader);
	}
	
	/**
	 * loader大小
	 * @return
	 */
	public int count(){
		return loaderList.size();
	}
	/**
	 * 移除一个loader
	 * @param removedItemIndex
	 */
	public void removeLoader(int removedItemIndex){
		if(loaderList.size() > 0){
			if(removedItemIndex < 0 || removedItemIndex > loaderList.size())
				return;
			
			loaderList.remove(removedItemIndex);
		}
	}
	
	/**
	 * 设置当前loader索引
	 * @param toItemIndex 被更新至loader的选项
	 */
	public void updateLoader(int toItemIndex){
		
		currentLoaderIndex = toItemIndex;
		
		if(loaderList.size() > 0){
			if(toItemIndex < 0)
				toItemIndex = 0;
			else if(toItemIndex > loaderList.size())
				toItemIndex = loaderList.size() - 1;
			if(currentLoader != null)
				currentLoader.onPause();//线暂停上一个loader
			currentLoader = loaderList.get(toItemIndex);
			currentLoader.onResume();//重启新的loader
		}
	}
	
	/**
	 * 获取当前loader
	 * @return
	 */
	public com.romaway.framework.view.RomaWayBaseViewLoader getCurrentLoader(){
		currentLoader = loaderList.get(currentLoaderIndex);
		return currentLoader;
	}
	
	/**
	 * 获取loader
	 * @param loaderIndex 需要得到的loader的索引
	 * @return
	 */
	public com.romaway.framework.view.RomaWayBaseViewLoader getLoader(int loaderIndex){
		
		return loaderList.get(loaderIndex);
	}
	
}

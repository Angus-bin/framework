package com.romaway.news.protocol.info;

import java.util.ArrayList;
import java.util.List;

/**
 * 资讯列表组的集合
 * @author wanlh
 *
 */
public class NewsListGroupContiner {
	private String groupName = new String();
	private List<Item_newsListItemData> groupList = 
			new ArrayList<Item_newsListItemData>();
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public List<Item_newsListItemData> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<Item_newsListItemData> groupList) {
		this.groupList = groupList;
	}
}

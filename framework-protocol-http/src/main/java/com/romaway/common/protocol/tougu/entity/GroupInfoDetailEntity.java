package com.romaway.common.protocol.tougu.entity;

/**
 * Created by hrb on 2016/8/4.
 */
public class GroupInfoDetailEntity {

	/*"idea" : "啊大大撒"，          		// 主理人建议
            "name" : "高管增值组合",      		// 组合名称
			"sfyc" ："1",							// 是否隐藏
			"id"   : "123"						// 组合id
			*/


    private String idea;
    private String name;
    private String sfyc;
    private String id;

    public String getIdea() {
        return idea;
    }

    public void setIdea(String idea) {
        this.idea = idea;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSfyc() {
        return sfyc;
    }

    public void setSfyc(String sfyc) {
        this.sfyc = sfyc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

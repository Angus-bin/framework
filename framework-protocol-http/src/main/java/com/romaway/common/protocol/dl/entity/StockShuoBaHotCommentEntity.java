package com.romaway.common.protocol.dl.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/3/29.
 */
public class StockShuoBaHotCommentEntity {

    private String id;
    private String user_id;
    private String key_id;
    private String content;
    private String created_at;
    private String source;
    private String type;
    private String is_praise;
    private String praise;
    private String avatar;
    private String name;
    private String other;
    private List<StockShuoBaHotReplyCommentEntity> list_reply;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIs_praise() {
        return is_praise;
    }

    public void setIs_praise(String is_praise) {
        this.is_praise = is_praise;
    }

    public String getPraise() {
        return praise;
    }

    public void setPraise(String praise) {
        this.praise = praise;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StockShuoBaHotReplyCommentEntity> getList_reply() {
        return list_reply;
    }

    public void setList_reply(List<StockShuoBaHotReplyCommentEntity> list_reply) {
        this.list_reply = list_reply;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}

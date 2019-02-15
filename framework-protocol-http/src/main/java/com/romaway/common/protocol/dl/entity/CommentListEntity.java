package com.romaway.common.protocol.dl.entity;

/**
 * Created by hongrb on 2017/6/8.
 */
public class CommentListEntity {

    private String user_id;
    private String userphoto;
    private String username;
    private String commentime;
    private String commentpoint;
    private String commentcount;
    private String comment;
    private String ispoint;
    private String feedbackid;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUserphoto() {
        return userphoto;
    }

    public void setUserphoto(String userphoto) {
        this.userphoto = userphoto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCommentime() {
        return commentime;
    }

    public void setCommentime(String commentime) {
        this.commentime = commentime;
    }

    public String getCommentpoint() {
        return commentpoint;
    }

    public void setCommentpoint(String commentpoint) {
        this.commentpoint = commentpoint;
    }

    public String getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(String commentcount) {
        this.commentcount = commentcount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getIspoint() {
        return ispoint;
    }

    public void setIspoint(String ispoint) {
        this.ispoint = ispoint;
    }

    public String getFeedbackid() {
        return feedbackid;
    }

    public void setFeedbackid(String feedbackid) {
        this.feedbackid = feedbackid;
    }
}

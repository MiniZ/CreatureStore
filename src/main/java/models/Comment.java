package main.java.models;

import java.sql.Timestamp;

public class Comment {

    private long id;

    private int accountId;

    private int postId;

    private String comment;

    private Timestamp createTime;

    public Comment() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", postId=" + postId +
                ", comment='" + comment + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}

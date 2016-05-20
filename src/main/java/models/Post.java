package main.java.models;

import java.sql.Timestamp;

public class Post {

    private int id;

    private int accountId;

    private String imgSrc;

    private String youtubeLink;

    private String title;

    private String description;

    private Timestamp postTime;

    public Post() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getPostTime() {
        return postTime;
    }

    public void setPostTime(Timestamp postTime) {
        this.postTime = postTime;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", imgSrc='" + imgSrc + '\'' +
                ", youtubeLink='" + youtubeLink + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", postTime=" + postTime +
                '}';
    }
}

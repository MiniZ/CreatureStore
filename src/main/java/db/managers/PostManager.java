package main.java.db.managers;

import main.java.models.Post;

import javax.sql.DataSource;

public class PostManager {

    protected DataSource dataSource;

    public static final String ATTRIBUTE_NAME = "post_manager";

    public PostManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Post getPostImgSrcById(String post_id) {
        return null;
    }
}

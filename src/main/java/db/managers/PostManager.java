package main.java.db.managers;

import main.java.models.Post;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostManager {

    protected DataSource dataSource;

    public static final String ATTRIBUTE_NAME = "post_manager";

    public PostManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Post getPostImgSrcById(String post_id) {
        return null;
    }

    public int savePost(Post post) {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("insert into post"
                    + "(account_id, img_src, youtube_link, title, description, post_time) "
                    + "values "
                    + "( ?, ?, ?, ?, ?, ?)"
            )) {
                stmt.setString(1, String.valueOf(post.getAccountId()));
                stmt.setString(2, post.getImgSrc());
                stmt.setString(3, post.getYoutubeLink());
                stmt.setString(4, post.getTitle());
                stmt.setString(5, post.getDescription());
                stmt.setString(6, String.valueOf(post.getPostTime()));
                stmt.executeUpdate();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getPostIdByName(post.getTitle());
    }

    public int getPostIdByName(String title) {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT id from post where title = ?")) {
                stmt.setString(1, title);
                ResultSet resultSet = stmt.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

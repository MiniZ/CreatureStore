package main.java.db.managers;

import main.java.models.Post;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Post> getAllPosts() {
        List<Post> result = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT id, title from post ORDER BY post_time DESC ")) {
                ResultSet resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    Post post = new Post();
                    post.setId(resultSet.getInt("id"));
                    post.setTitle(resultSet.getString("title"));
                    result.add(post);
                }
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void deletePost(String post_id) {
        try (Connection conn = dataSource.getConnection()){
            try (PreparedStatement stmt = conn.prepareStatement("delete from post WHERE id = ?")){
                stmt.setInt(1, Integer.valueOf(post_id));
                stmt.executeUpdate();
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

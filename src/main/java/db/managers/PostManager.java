package main.java.db.managers;

import main.java.models.Post;

import javax.sql.DataSource;
import java.sql.*;
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

    public ArrayList<String> getTagsByPostID(Integer post_id) {
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<Integer> tagIDs = new ArrayList<Integer>();


        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT tag_id from post_tag where post_id = ?")) {
                stmt.setInt(1, post_id);
                ResultSet resultSet = stmt.executeQuery();
                if (resultSet.next()) {
                    tagIDs.add(resultSet.getInt("tag_id"));
                }
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Integer tagID : tagIDs) {

            try (Connection conn = dataSource.getConnection()) {
                try (PreparedStatement stmt = conn.prepareStatement("SELECT tag from tag where id = ?")) {
                    stmt.setInt(1, tagID);
                    ResultSet resultSet = stmt.executeQuery();
                    if (resultSet.next()) {
                        result.add(resultSet.getString("tag"));
                    }
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return result;
    }

    public List<Post> getPosts() {
        List<Post> posts = new ArrayList<Post>();
        try (Connection conn = dataSource.getConnection()){
            try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM post")) {
                ResultSet resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    Post post = fetchPost(resultSet);
                    posts.add(post);
                }
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;

    }

    private Post fetchPost(ResultSet resultSet) {
        Post post = new Post();
        try {
            post.setId(resultSet.getInt("id"));
            post.setAccountId(resultSet.getInt("account_id"));
            post.setImgSrc(resultSet.getString("img_src"));
            post.setYoutubeLink(resultSet.getString("youtube_link"));
            post.setTitle(resultSet.getString("title"));
            post.setDescription(resultSet.getString("description"));
            post.setPostTime(resultSet.getTimestamp("post_time"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }
}

package main.java.db.managers;

import main.java.models.Account;
import main.java.models.Post;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                while (resultSet.next()) {
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
                    while (resultSet.next()) {
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
            try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM post ORDER BY post_time DESC ")) {
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

    public List<Post> getAllPosts(String postt) {
        List<Post> result = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            String query;
            if (postt != null && !postt.isEmpty()) {
                query = "SELECT id, title from post where title like ? ORDER BY post_time DESC";
            } else {
                query = "SELECT id, title from post ORDER BY post_time DESC";
            }
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                if (postt != null && !postt.isEmpty()) {
                    stmt.setString(1, "%" + postt + "%");
                }
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
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM post WHERE id = ?")) {
                stmt.setInt(1, Integer.valueOf(post_id));
                stmt.executeUpdate();
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<Integer,Integer> getPlusesMapForPosts(List<Post> posts) {
        Map<Integer,Integer> pluses = new HashMap<>();
        for (Post post : posts) {
            try (Connection conn = dataSource.getConnection()) {
                try (PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(id) as 'count' FROM plus WHERE post_id = ?")) {
                    stmt.setInt(1, post.getId());
                    ResultSet resultSet = stmt.executeQuery();
                    if (resultSet.next()) {
                        pluses.put(post.getId(), resultSet.getInt("count"));
                    }
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return pluses;
    }

    public Map<Integer,Integer> getMinusesMapForPosts(List<Post> posts) {
        Map<Integer,Integer> minuses = new HashMap<>();
        for (Post post : posts) {
            try (Connection conn = dataSource.getConnection()) {
                try (PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(id) as 'count' FROM minus WHERE post_id = ?")) {
                    stmt.setInt(1, post.getId());
                    ResultSet resultSet = stmt.executeQuery();
                    if (resultSet.next()) {
                        minuses.put(post.getId(), resultSet.getInt("count"));
                    }
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return minuses;
    }

    public List<Post> getUserFollowingPosts(String user_display_name) {
        AccountManager accountManager = new AccountManager(dataSource);
        List<Post> result = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT following_id FROM follow WHERE follower_id = ?")) {
                Account user = accountManager.getAccount(user_display_name);
                stmt.setInt(1, Integer.valueOf(String.valueOf(user.getId())));
                ResultSet resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    try (PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM post WHERE account_id = ?")) {
                        stmt2.setInt(1, Integer.valueOf(String.valueOf(resultSet.getString("following_id"))));
                        ResultSet resultSet2 = stmt2.executeQuery();
                        while (resultSet2.next()) {
                            result.add(fetchPost(resultSet2));
                        }
                    }
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

    public List<Post> getUserPlusesPost(String user_display_name) {
        List<Post> result = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        AccountManager accountManager = new AccountManager(dataSource);
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT post_id FROM plus WHERE account_id = ?")) {
                Account user = accountManager.getAccount(user_display_name);
                stmt.setInt(1, Integer.valueOf(String.valueOf(user.getId())));
                ResultSet resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    try (PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM post WHERE id = ?")) {
                        stmt2.setInt(1, Integer.valueOf(String.valueOf(resultSet.getString("post_id"))));
                        ResultSet resultSet2 = stmt2.executeQuery();
                        if (resultSet2.next()) {
                            if (!ids.contains(Integer.valueOf(String.valueOf(resultSet.getString("post_id"))))) {
                                result.add(fetchPost(resultSet2));
                                ids.add(Integer.valueOf(String.valueOf(resultSet.getString("post_id"))));
                            }
                        }
                    }
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

    public void plusOnPost(String postId, String logged_in_user_display_name) {
        AccountManager manager = new AccountManager(dataSource);
        try (Connection conn = dataSource.getConnection()){
            try (PreparedStatement stmt = conn.prepareStatement("insert into plus (post_id, account_id)"
                    + "values (?, ?)")){
                Account logged = manager.getAccount(logged_in_user_display_name);
                stmt.setInt(1, Integer.valueOf(String.valueOf(postId)));
                stmt.setInt(2, Integer.valueOf(String.valueOf(logged.getId())));
                stmt.executeUpdate();
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void minusOnPost(String postId, String logged_in_user_display_name) {
        AccountManager manager = new AccountManager(dataSource);
        try (Connection conn = dataSource.getConnection()){
            try (PreparedStatement stmt = conn.prepareStatement("insert into minus (post_id, account_id)"
                    + "values (?, ?)")){
                Account logged = manager.getAccount(logged_in_user_display_name);
                stmt.setInt(1, Integer.valueOf(String.valueOf(postId)));
                stmt.setInt(2, Integer.valueOf(String.valueOf(logged.getId())));
                stmt.executeUpdate();
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getPlusCountOnPost(int post_id) {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(id) as 'count' FROM plus WHERE post_id = ?")) {
                stmt.setInt(1, post_id);
                ResultSet resultSet = stmt.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt("count");
                }
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getMinusCountOnPost(int post_id) {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(id) as 'count' FROM minus WHERE post_id = ?")) {
                stmt.setInt(1, post_id);
                ResultSet resultSet = stmt.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt("count");
                }
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Post> getPostsForUser(String loggedUserDisplayName) {
        AccountManager manager = new AccountManager(dataSource);
        Account logged = manager.getAccount(loggedUserDisplayName);
        List<Post> posts = new ArrayList<Post>();
        try (Connection conn = dataSource.getConnection()){
            try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM post where account_id = ? ORDER BY post_time DESC ")) {
                stmt.setInt(1, (int) logged.getId());
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
}

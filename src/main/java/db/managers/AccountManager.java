package main.java.db.managers;

import main.java.models.Account;
import main.java.models.AccountType;
import main.java.models.Post;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountManager {

    protected DataSource dataSource;

    public static final String ATTRIBUTE_NAME = "account_manager";

    public AccountManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<String> getAllAccountDisplayNames() {
        List<String> result = new ArrayList<>();
        try {
            Connection con = dataSource.getConnection();
            String query = "SELECT display_name, first_name FROM accounts";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getString("display_name"));
                result.add(resultSet.getString("first_name"));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Account> getAllAccounts(String displayName) {
        List<Account> result = new ArrayList<>();
        try {
            Connection con = dataSource.getConnection();
            String query = "";
            if (displayName == null || displayName.length() == 0){
                query = "SELECT * FROM accounts ORDER BY display_name ASC";
            } else {
                query = "SELECT * FROM accounts WHERE display_name LIKE ? ORDER BY display_name ASC";
            }

            PreparedStatement stmt = con.prepareStatement(query);
            if (displayName != null && displayName.length() != 0) {
                stmt.setString(1, "%"+displayName+"%");
            }
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                result.add(fetchAccount(resultSet));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean userExists(String display_name) {
        try {
            Connection con = dataSource.getConnection();
            String query = "SELECT * FROM accounts WHERE display_name = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, display_name);
            ResultSet result = statement.executeQuery();
            boolean contains = result.next();
            con.close();
            return contains;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean authenticateUser(String display_name, String hashedPassword) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT * FROM accounts WHERE display_name = ? AND hashed_password = ? ";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, display_name);
            stmt.setString(2, hashedPassword);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                conn.close();
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int userIsBanned(String display_name, String hashedPassword) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT is_banned FROM accounts WHERE display_name = ? AND hashed_password = ? ";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, display_name);
            stmt.setString(2, hashedPassword);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                int res = result.getInt("is_banned");
                conn.close();
                return res;
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean createAccount(Account acc) {

        boolean flag = false;

        if (userExists(acc.getDisplayName())) {
            return false;
        } else {
            try (Connection conn = dataSource.getConnection()) {
                try (PreparedStatement stmt = conn.prepareStatement("insert into accounts"
                        + "(first_name, last_name, hashed_password, "
                        + "email, display_name, img_src, fb_link, twitter_link, google_plus_link, "
                        + "country, city,  about_me, type, is_banned) "
                        + "values "
                        + "( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                )) {
                    stmt.setString(1, acc.getFirstName());
                    stmt.setString(2, acc.getLastName());
                    stmt.setString(3, acc.getHashedPassword());
                    stmt.setString(4, acc.getMail());
                    stmt.setString(5, acc.getDisplayName());
                    stmt.setString(6, acc.getImgSrc());
                    stmt.setString(7, acc.getFbLink());
                    stmt.setString(8, acc.getTwitterLink());
                    stmt.setString(9, acc.getGoogleLink());
                    stmt.setString(10, acc.getCountry());
                    stmt.setString(11, acc.getCity());
                    stmt.setString(12, acc.getAboutMe());
                    switch (acc.getType()) {
                        case ADMIN:
                            stmt.setString(13, "ADMIN");
                            break;
                        case USER:
                            stmt.setString(13, "USER");
                            break;
                    }

                    if (acc.isBanned()) {
                        stmt.setString(14, "1");
                    } else {
                        stmt.setString(14, "0");
                    }
                    stmt.executeUpdate();
                    flag = true;
                    conn.close();
                    return flag;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return flag;
    }

    public Account getAccount(String displayName) {
        Account res = null;
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("select * from accounts where display_name = ?")) {
                stmt.setString(1, displayName);
                try (ResultSet rset = stmt.executeQuery()) {
                    if (rset.next()) {
                        res = fetchAccount(rset);
                        conn.close();
                        return res;
                    } else {
                        throw new IllegalArgumentException("Invalid display name");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    private Account fetchAccount(ResultSet rset) throws SQLException {
        boolean isAdmin = false;
        boolean isBanned = false;
        if (rset.getString("type").equals("ADMIN")) {
            isAdmin = true;
        }
        if (rset.getString("is_banned").equals("1")) {
            isBanned = true;
        }


        Account acc1 = new Account(rset.getInt("ID"), rset.getString("first_name"), rset.getString("last_name"),
                rset.getString("hashed_password"), rset.getString("email"),
                rset.getString("display_name"), rset.getString("img_src"), rset.getString("fb_link"),
                rset.getString("twitter_link"), rset.getString("google_plus_link"), rset.getString("country"),
                rset.getString("city"), rset.getString("about_me"), AccountType.valueOf(rset.getString("type")),
                isAdmin, isBanned);
        return acc1;
    }

    public String getImgSrcByDisplayName(String display_name) {
        String result = null;
        try {
            Connection con = dataSource.getConnection();
            String query = "SELECT img_src FROM accounts WHERE display_name = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, display_name);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getString("img_src");
            }
            con.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void updateUserImgSrc(String imgSrc, String display_name) {
        try (Connection conn = dataSource.getConnection()){
            try (PreparedStatement stmt = conn.prepareStatement("update accounts "
                    + "set img_src = ? where display_name = ?")){
                stmt.setString(1, imgSrc);
                stmt.setString(2, display_name);
                stmt.executeUpdate();
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean followUser(String logged_in_user_display_name, String chosen_user_display_name) {
        try (Connection conn = dataSource.getConnection()){
            try (PreparedStatement stmt = conn.prepareStatement("insert into follow (follower_id, following_id)"
                    + "values (?, ?)")){
                Account logged = getAccount(logged_in_user_display_name);
                Account chosen = getAccount(chosen_user_display_name);
                stmt.setInt(1, Integer.valueOf(String.valueOf(logged.getId())));
                stmt.setInt(2, Integer.valueOf(String.valueOf(chosen.getId())));
                stmt.executeUpdate();
                conn.close();
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean unFollowUser(String logged_in_user_display_name, String chosen_user_display_name) {
        try (Connection conn = dataSource.getConnection()){
            try (PreparedStatement stmt = conn.prepareStatement("delete from follow WHERE follower_id = ? and following_id = ?")){
                Account logged = getAccount(logged_in_user_display_name);
                Account chosen = getAccount(chosen_user_display_name);
                stmt.setInt(1, Integer.valueOf(String.valueOf(logged.getId())));
                stmt.setInt(2, Integer.valueOf(String.valueOf(chosen.getId())));
                stmt.executeUpdate();
                conn.close();
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public List<Account> getUserFollowers(String user_display_name) {
        List<Account> result = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()){
            try (PreparedStatement stmt = conn.prepareStatement("SELECT follower_id FROM follow WHERE following_id = ?")) {
                Account user = getAccount(user_display_name);
                stmt.setInt(1, Integer.valueOf(String.valueOf(user.getId())));
                ResultSet resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    try (PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM accounts WHERE id = ?")) {
                        stmt2.setInt(1, Integer.valueOf(String.valueOf(resultSet.getString("follower_id"))));
                        ResultSet resultSet2 = stmt2.executeQuery();
                        while (resultSet2.next()) {
                            Account follower = fetchAccount(resultSet2);
                            result.add(follower);
                        }
                    }
                }
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Account> getUserFollowing(String user_display_name) {
        List<Account> result = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT following_id FROM follow WHERE follower_id = ?")) {
                Account user = getAccount(user_display_name);
                stmt.setInt(1, Integer.valueOf(String.valueOf(user.getId())));
                ResultSet resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    try (PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM accounts WHERE id = ?")) {
                        stmt2.setInt(1, Integer.valueOf(String.valueOf(resultSet.getString("following_id"))));
                        ResultSet resultSet2 = stmt2.executeQuery();
                        while (resultSet2.next()) {
                            result.add(fetchAccount(resultSet2));
                        }
                    }
                }
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public boolean isUserAdmin(String display_name) {
        boolean isAdmin = false;
        try {
            Connection con = dataSource.getConnection();
            String query = "SELECT type FROM accounts WHERE display_name = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, display_name);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getString("type").equals("ADMIN")) {
                    isAdmin = true;
                }
            }
            con.close();
            return isAdmin;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAdmin;
    }

    public Map<String , Boolean> getAllAccountsBannedMap(String user) {
        Map<String, Boolean> resultMap = new HashMap<>();
        try {
            Connection con = dataSource.getConnection();
            String query;
            if (user != null && !user.isEmpty()) {
                query = "SELECT display_name, is_banned FROM accounts where display_name like ?";
            } else {
                query = "SELECT display_name, is_banned FROM accounts";
            }
            PreparedStatement stmt = con.prepareStatement(query);
            if (user != null && !user.isEmpty()) {
                stmt.setString(1, "%" + user + "%");
            }
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                resultMap.put(resultSet.getString("display_name"), resultSet.getString("is_banned").equals("1"));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    public void banUser(String display_name) {
        try (Connection conn = dataSource.getConnection()){
            try (PreparedStatement stmt = conn.prepareStatement("update accounts "
                    + "set is_banned = ? where display_name = ?")){
                stmt.setString(1, "1");
                stmt.setString(2, display_name);
                stmt.executeUpdate();
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void unBanUser(String display_name) {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("update accounts "
                    + "set is_banned = ? where display_name = ?")) {
                stmt.setString(1, "0");
                stmt.setString(2, display_name);
                stmt.executeUpdate();
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Post getPostById(String postID) {
        try (Connection conn = dataSource.getConnection()){
            try (PreparedStatement stmt = conn.prepareStatement("select * from post where id = ?")){
                stmt.setInt(1, Integer.parseInt(postID));
                ResultSet results = stmt.executeQuery();
                Post post = new Post();
                post.setId(results.getInt("id"));
                post.setAccountId(results.getInt("account_id"));
                post.setDescription(results.getString("description"));
                post.setImgSrc(results.getString("img_src"));
                post.setTitle(results.getString("title"));
                post.setPostTime(results.getTimestamp("post_time"));
                conn.close();
                return post;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getAccountDisplayNameByID(Integer account_id) {
        String result = "";
        try {
            Connection con = dataSource.getConnection();
            String query = "SELECT display_name FROM accounts WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, account_id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getString("display_name");
            }
            con.close();
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}

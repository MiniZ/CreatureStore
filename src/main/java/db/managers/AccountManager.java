package main.java.db.managers;

import main.java.models.Account;
import main.java.models.AccountType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public Account getImgSrcByDisplayName(String display_name) {
        return null;
    }

    public void updateUSerImgSrc(String imgSrc, String display_name) {

    }
}

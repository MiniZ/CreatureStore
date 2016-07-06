package main.java.db.managers;

import main.java.models.Account;
import main.java.models.AccountType;
import main.java.models.Comment;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class CommentsManager {

    protected DataSource dataSource;

    public static final String ATTRIBUTE_NAME = "comments_manager";

    public CommentsManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean addComment(Comment comment) {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("insert into comment"
                    + "(post_id, account_id, comment_text, add_time) "
                    + "values "
                    + "( ?, ?, ?, ?)"
                )) {
                stmt.setInt(1, comment.getPostId());
                stmt.setInt(2, comment.getAccountId());
                stmt.setString(3, comment.getComment());
                stmt.setTimestamp(4, comment.getCreateTime());
                stmt.executeUpdate();
                conn.close();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteComment(int commentId) {
        try (Connection conn = dataSource.getConnection()){
            try (PreparedStatement stmt = conn.prepareStatement("delete from comment WHERE id = ?")){
                stmt.setInt(1, commentId);
                stmt.executeUpdate();
                conn.close();
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean updateComment(int commentId, String newText, String user) {
        AccountManager manager = new AccountManager(dataSource);
        Account account = manager.getAccount(user);
        try (Connection conn = dataSource.getConnection()){
            String query;
            if (account.getType() == AccountType.ADMIN) {
                query = "update commnet set comment_text = ?, add_time = ?, account_id = ? WHERE id = ?";
            } else {
                query = "update commnet set comment_text = ?, add_time = ? WHERE id = ?";
            }
            try (PreparedStatement stmt = conn.prepareStatement(query)){
                stmt.setString(1, newText);
                Date date = new Date();
                stmt.setTimestamp(2, new Timestamp(date.getTime()));
                if (account.getType() == AccountType.ADMIN) {
                    stmt.setInt(3, (int) account.getId());
                    stmt.setInt(4, commentId);
                } else {
                    stmt.setInt(3, commentId);
                }
                stmt.executeUpdate();
                conn.close();
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
    }
}

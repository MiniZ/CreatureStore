package main.java.db.managers;

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
}

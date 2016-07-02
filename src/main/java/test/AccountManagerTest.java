package main.java.test;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import main.java.db.DBConfig;
import main.java.db.managers.AccountManager;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccountManagerTest {

    //All test pass on initial script

    private static DataSource dataSource;
    private static AccountManager am;

    @BeforeClass
    public static void setUpClass() throws Exception {
        // Create initial context
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                "org.apache.naming.java.javaURLContextFactory");
        System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
        Context ic = new InitialContext();

        ic.createSubcontext("java:");
        ic.createSubcontext("java:/comp");
        ic.createSubcontext("java:/comp/env");
        ic.createSubcontext("java:/comp/env/jdbc");

        // Construct DataSource
        MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
        ds.setURL("jdbc:mysql://localhost:3306/" + DBConfig.getDBName()
                + "?characterEncoding=UTF-8");
        ds.setUser(DBConfig.getDBUsername()); // use your username
        ds.setPassword(DBConfig.getDBPassword()); // use your password

        ic.bind("java:/comp/env/jdbc/" + DBConfig.getDBName(), ds);

        Context initContext = new InitialContext();
        Context webContext = (Context) initContext.lookup("java:/comp/env");

        // Initializes DataSource and AccountManager classes based on injected
        // resources
        dataSource = (DataSource) webContext.lookup("jdbc/" + DBConfig.getDBName());
        am = new AccountManager(dataSource);
    }

    @Test
	public void testGetAllAccountDisplayNames() throws NoSuchAlgorithmException {
        List<String> result = am.getAllAccountDisplayNames();
		assertEquals(4, result.size());
	}

	@Test
	public void testUserExists() {
        assertTrue(am.userExists("admin_display_name"));
        assertTrue(am.userExists("user_display_name"));
        assertFalse(am.userExists("rozeta"));
    }


}

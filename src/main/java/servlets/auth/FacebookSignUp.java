package main.java.servlets.auth;

import main.java.db.managers.AccountManager;
import main.java.utils.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

@WebServlet("/FacebookSignUp")
public class FacebookSignUp extends HttpServlet{

    private static final long serialVersionUID = 1L;
    //app info from facebook
    private static final String APP_ID = "836294589795940";
    private static final String APP_SECRET = "585a64187781037a092ff2b1af16524a";
    private static final String REDIRECT_URL = "http://localhost:8080/FacebookSignUp";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FacebookSignUp() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        service(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        service(request, response);
    }

    /**
     * Attempts authentication via Facebook.
     */
    public void service(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        // setting utf-8 encoding for inputs
        res.setContentType("UTF-8");
        res.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        // error checking
        String code = req.getParameter("code");
        if (code == null || code.equals("")) {
            req.setAttribute("error", "true");
            RequestDispatcher dispatcher = req
                    .getRequestDispatcher("SignUp.jsp");
            dispatcher.forward(req, res);
        }

        // building token url
        String token = null;
        try {
            String builder = "https://graph.facebook.com/oauth/access_token?client_id=" +
                    APP_ID +
                    "&redirect_uri="
                    + URLEncoder.encode(REDIRECT_URL, "UTF-8") +
                    "&client_secret=" +
                    APP_SECRET +
                    "&code=" + code;
            URL u = new URL(builder);
            URLConnection c = u.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    c.getInputStream()));
            String inputLine;
            StringBuilder b = new StringBuilder();
            while ((inputLine = in.readLine()) != null)
                b.append(inputLine).append("\n");
            in.close();
            token = b.toString();
            if (token.startsWith("{"))
                throw new Exception("Error on requesting token: " + token
                        + " with code: " + code);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String graph = null;
        try {
            String g = "https://graph.facebook.com/me?" + token;
            URL u = new URL(g);
            URLConnection c = u.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    c.getInputStream()));
            String inputLine;
            StringBuilder b = new StringBuilder();
            while ((inputLine = in.readLine()) != null)
                b.append(inputLine).append("\n");
            in.close();
            graph = b.toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // parsing user data from JSON that just returned facebook
        String facebookId = null; // username
        String email = null;
        String firstName = null;
        String lastName = null;
        String displayName = null;
        try {
            System.out.println(graph);
            JSONObject json = new JSONObject(graph);
            facebookId = json.getString("id");
            firstName = json.getString("first_name");
            lastName = json.getString("last_name");
            displayName = firstName + "." + lastName;
            if (json.has("email"))
                email = json.getString("email");
            else
                email = "" + facebookId +"@facebook.com";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String defFbPassword = facebookId + "creaturestore";

        // handling either signing in or signing up
        AccountManager manager = (AccountManager) getServletContext()
                .getAttribute(AccountManager.ATTRIBUTE_NAME);
        if (manager.userExists(facebookId)) {
            //already registered, so just redirecting
            req.getSession().setAttribute("display_name", facebookId);
            res.sendRedirect("index.jsp");
            System.out.println("redirected");
        } else {
            // registration
            req.setAttribute("socialSign", "true");
            req.setAttribute("password", defFbPassword);
            System.out.println("email + " + email);
            req.setAttribute("email", email);
            req.setAttribute("display_Name", facebookId);
            req.setAttribute("first_name", firstName);
            System.out.println(firstName);
            req.setAttribute("last_name", lastName);
            req.setAttribute("display_name", displayName);
            RequestDispatcher dispatcher = req.getRequestDispatcher("SignUp");
            dispatcher.forward(req, res);
            System.out.println("dispached");
        }
    }
}

package main.java.servlets.auth;

import main.java.db.managers.AccountManager;
import main.java.models.Account;
import main.java.models.AccountType;
import main.java.utils.PasswordHash;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet("/SignUp")
public class SignUpServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        // getting account manager
        AccountManager manager = (AccountManager) getServletContext()
                .getAttribute(AccountManager.ATTRIBUTE_NAME);

        // getting username either from social login or the web-page form
        String display_name = null;
        display_name = (String) request.getAttribute("display_name");
        if (!manager.userExists(display_name)) {
            Account account = getAccount(display_name, request);
            if (manager.createAccount(account)) {
                request.getSession()
                        .setAttribute("display_name", account.getDisplayName());
                //TODO
                /*RequestDispatcher dispatcher = request
                        .getRequestDispatcher("setavatar.jsp");
                dispatcher.forward(request, response);*/
                response.sendRedirect("index.jsp");
            } else {
                request.setAttribute("error", "true");
                RequestDispatcher dispatcher = request
                        .getRequestDispatcher("signup.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            request.setAttribute("usernameTaken", "true");
            RequestDispatcher dispatcher = request
                    .getRequestDispatcher("signup.jsp");
            dispatcher.forward(request, response);
        }

    }

    private Account getAccount(String display_name, HttpServletRequest request) {
        Account account = new Account();
        account.setDisplayName(display_name);
        try {
            account.setHashedPassword(PasswordHash.hashText((String) request.getAttribute("password")));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        account.setFirstName((String) request.getAttribute("first_name"));
        account.setLastName((String) request.getAttribute("last_name"));
        account.setMail((String) request.getAttribute("email"));
        account.setFbLink((String) request.getAttribute("facebook_link"));
        account.setTwitterLink((String) request.getAttribute("twitter_link"));
        account.setGoogleLink((String) request.getAttribute("google_plus_link"));
        account.setCountry((String) request.getAttribute("country"));
        account.setCity((String) request.getAttribute("city"));
        account.setAboutMe((String) request.getAttribute("about_me"));
        account.setType(AccountType.USER);
        account.setIsBanned(false);
        return account;
    }


}
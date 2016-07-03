package main.java.servlets.auth;

import main.java.db.managers.AccountManager;
import main.java.utils.PasswordHash;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet("/login")
public class LoginServlet  extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
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
        String display_name = request.getParameter("display_name");
        if (display_name == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        String password = request.getParameter("password");

        try {
            String hashedPassword = PasswordHash.hashText(password);
            AccountManager manager = (AccountManager) getServletContext()
                    .getAttribute(AccountManager.ATTRIBUTE_NAME);
            if (manager.userExists(display_name)) {
                if (manager.authenticateUser(display_name, hashedPassword)) {
                    if (manager.userIsBanned(display_name, hashedPassword) == 1) {
                        request.setAttribute("userIsBanned", "true");
                        RequestDispatcher dispatcher = request
                                .getRequestDispatcher("signin.jsp");
                        dispatcher.forward(request, response);
                        return;
                    }
                    HttpSession session = request.getSession();
                    session.setAttribute("display_name", display_name);
                    response.sendRedirect("index.jsp");
                } else {
                    request.setAttribute("incorrectPassword", "true");
                    RequestDispatcher dispatcher = request
                            .getRequestDispatcher("signin.jsp");
                    dispatcher.forward(request, response);
                }
            } else {
                request.setAttribute("incorrectUsername", "true");
                RequestDispatcher dispatcher = request
                        .getRequestDispatcher("signin.jsp");
                dispatcher.forward(request, response);
            }
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

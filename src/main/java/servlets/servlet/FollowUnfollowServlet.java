package main.java.servlets.servlet;

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

@WebServlet("/FollowUnfollowServlet")
public class FollowUnfollowServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowUnfollowServlet() {
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
        String logged_in_user_display_name = (String) request.getSession().getAttribute("display_name");
        String chosen_user_display_name = request.getParameter("chosen_user_display_name");
        String isFollow = request.getParameter("isFollow");
        if (logged_in_user_display_name == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        AccountManager manager = (AccountManager) getServletContext()
                .getAttribute(AccountManager.ATTRIBUTE_NAME);
        switch (isFollow) {
            case "follow" :
                if (manager.followUser(logged_in_user_display_name, chosen_user_display_name)) {
                    RequestDispatcher dispatcher = request
                            .getRequestDispatcher("profile.jsp?username=" + logged_in_user_display_name);
                    dispatcher.forward(request, response);
                } else {
                    RequestDispatcher dispatcher = request
                            .getRequestDispatcher("profile.jsp?username=" + logged_in_user_display_name);
                    dispatcher.forward(request, response);
                }
                break;
            case "unfollow" :
                if (manager.unFollowUser(logged_in_user_display_name, chosen_user_display_name)) {
                    RequestDispatcher dispatcher = request
                            .getRequestDispatcher("profile.jsp?username=" + logged_in_user_display_name);
                    dispatcher.forward(request, response);
                } else {
                    RequestDispatcher dispatcher = request
                            .getRequestDispatcher("profile.jsp?username=" + logged_in_user_display_name);
                    dispatcher.forward(request, response);
                }
                break;
        }
    }

}

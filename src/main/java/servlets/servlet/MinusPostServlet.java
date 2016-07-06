package main.java.servlets.servlet;

import main.java.db.managers.PostManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/MinusPostServlet")
public class MinusPostServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MinusPostServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String postId = request.getParameter("post_id");
        String logged_in_user_display_name = (String) request.getSession().getAttribute("display_name");
        if (postId == null || logged_in_user_display_name == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        PostManager manager = (PostManager) getServletContext()
                .getAttribute(PostManager.ATTRIBUTE_NAME);
        manager.minusOnPost(postId, logged_in_user_display_name);
        RequestDispatcher dispatcher = request
                .getRequestDispatcher("post-page.jsp?postId=" + postId);
        dispatcher.forward(request, response);
    }

}

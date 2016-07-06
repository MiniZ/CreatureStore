package main.java.servlets.servlet;

import main.java.db.managers.PostManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/DeletePostServlet")
public class DeletePostServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeletePostServlet() {
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
        String post_id = request.getParameter("post_id");
        if (post_id == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        PostManager postManager = (PostManager) getServletContext()
                .getAttribute(PostManager.ATTRIBUTE_NAME);
//        postManager.deletePost(post_id);
        RequestDispatcher dispatcher = request
                .getRequestDispatcher("admin.jsp?type=post");
        dispatcher.forward(request, response);
    }
}

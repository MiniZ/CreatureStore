package main.java.servlets.servlet;

import main.java.db.managers.AccountManager;
import main.java.db.managers.CommentsManager;
import main.java.models.Account;
import main.java.models.Comment;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

@WebServlet("/CommentService")
public class CommentService extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentService() {
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
        if (logged_in_user_display_name == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        AccountManager manager = (AccountManager) getServletContext()
                .getAttribute(AccountManager.ATTRIBUTE_NAME);
        CommentsManager commentsManager = (CommentsManager) getServletContext()
                .getAttribute(CommentsManager.ATTRIBUTE_NAME);
        String reqType = request.getParameter("type");
        String postId = request.getParameter("post_id");
        String commentId = request.getParameter("comment_id");
        String commentText = request.getParameter("comment");
        switch (reqType) {
            case "add" :
                Account account = manager.getAccount(logged_in_user_display_name);
                Comment comment = new Comment();
                comment.setAccountId(Integer.valueOf(String.valueOf(account.getId())));
                comment.setComment(commentText);
                Date curr = new Date();
                comment.setCreateTime(new Timestamp(curr.getTime()));
                comment.setPostId(Integer.valueOf(postId));
                if (commentsManager.addComment(comment)) {
                    request.setAttribute("success", true);
                } else {
                    request.setAttribute("success", false);
                }
                break;
            case "edit":
                if (commentsManager.updateComment(Integer.parseInt(commentId), commentText, logged_in_user_display_name)) {
                    request.setAttribute("success", true);
                } else {
                    request.setAttribute("success", false);
                }
                break;
            case "delete":
                if (commentsManager.deleteComment(Integer.parseInt(commentId))) {
                    request.setAttribute("success", true);
                } else {
                    request.setAttribute("success", false);
                }
                break;
        }
        RequestDispatcher dispatcher = request
                .getRequestDispatcher("post-page.jsp?postId=" + postId);
        dispatcher.forward(request, response);
    }


}

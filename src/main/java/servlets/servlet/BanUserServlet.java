package main.java.servlets.servlet;

import main.java.db.managers.AccountManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/BanUserServlet")
public class BanUserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BanUserServlet() {
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
        String display_name = request.getParameter("user");
        if (display_name == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        AccountManager manager = (AccountManager) getServletContext()
                .getAttribute(AccountManager.ATTRIBUTE_NAME);
        manager.banUser(display_name);
        RequestDispatcher dispatcher = request
                .getRequestDispatcher("admin.jsp");
        dispatcher.forward(request, response);
    }

}

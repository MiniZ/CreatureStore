package main.java.servlets.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet("/PostUploadServlet")
public class PostUploadServlet extends StorageServlet {

    private static final String POST_SAVE_DIR = "posts";
    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_LOCATION_PROPERTY_KEY = "storage.location";
    private String uploadsDirName;

    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 50 * 1024 * 1024;
    private int maxMemSize = 4 * 1024 *1024;
    private File file ;

    @Override
    public void init() throws ServletException {
        super.init();
        uploadsDirName = property(UPLOAD_LOCATION_PROPERTY_KEY);
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}

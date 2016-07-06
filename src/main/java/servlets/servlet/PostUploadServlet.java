package main.java.servlets.servlet;

import main.java.db.managers.AccountManager;
import main.java.db.managers.PostManager;
import main.java.models.Account;
import main.java.models.Post;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@WebServlet("/PostUploadServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50)
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        isMultipart = ServletFileUpload.isMultipartContent(request);
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(maxMemSize);
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax( maxFileSize );

        PostManager postManager = (PostManager) getServletContext().getAttribute(PostManager.ATTRIBUTE_NAME);
        AccountManager accountManager = (AccountManager) getServletContext().getAttribute(AccountManager.ATTRIBUTE_NAME);

        try{
            // Parse the request to get file items.
            List fileItems = upload.parseRequest(request);
            // Process the uploaded file items
            if (fileItems == null || fileItems.size() < 4) {
                request.setAttribute("tryAgain", "true");
                RequestDispatcher dispatcher = request
                        .getRequestDispatcher("postupload.jsp");
                dispatcher.forward(request, response);
                return;
            }
            int titlesize = ((FileItem)fileItems.get(0)).getInputStream().read();
            byte[] titledata  = new byte[titlesize];
            ((FileItem)fileItems.get(0)).getInputStream().read(titledata);
            String title = new String(titledata, StandardCharsets.UTF_8);
            title = title.trim();

            int descsize = ((FileItem)fileItems.get(1)).getInputStream().read();
            byte[] descdata  = new byte[descsize];
            ((FileItem)fileItems.get(1)).getInputStream().read(descdata);
            String description = new String(descdata, StandardCharsets.UTF_8);
            description = description.trim();

            int youtubesize = ((FileItem)fileItems.get(2)).getInputStream().read();
            byte[] youtubedata  = new byte[youtubesize];
            ((FileItem)fileItems.get(1)).getInputStream().read(youtubedata);
            String youtube_link = new String(youtubedata, StandardCharsets.UTF_8);
            youtube_link = youtube_link.trim();

            FileItem item = (FileItem) fileItems.get(3);
            if (!item.isFormField()) {
                String display_name = (String) request.getSession().getAttribute("display_name");
                if (display_name == null) {
                    response.sendRedirect("index.jsp");
                    return;
                }
                // constructs path of the directory to save uploaded file
                String savePath = uploadsDirName + File.separator + POST_SAVE_DIR;
                File file = new File(savePath);
                if (!file.exists()) {
                    //noinspection ResultOfMethodCallIgnored
                    file.mkdirs();
                }
                byte[] data = new byte[0];
                String filename = "";
                data = IOUtils.toByteArray(item.getInputStream());
                filename = item.getName();
                Path path = Paths.get(savePath + File.separator + filename);
                Files.write(path, data);
                Account account = accountManager.getAccount(display_name);
                Post post = new Post();
                post.setImgSrc(savePath + File.separator + filename);
                post.setAccountId(Integer.parseInt(String.valueOf(account.getId())));
                post.setDescription(description);
                post.setTitle(title);
                Date curr = new Date();
                post.setPostTime(new Timestamp(curr.getTime()));
                post.setYoutubeLink(youtube_link);
                int postId = postManager.savePost(post);
                if (postId != 0) {
                    //refresh user page
                    RequestDispatcher dispatcher = request
                            .getRequestDispatcher("post-page.jsp?postId=" + postId);
                    dispatcher.forward(request, response);
                } else {
                    request.setAttribute("tryAgain", "true");
                    RequestDispatcher dispatcher = request
                            .getRequestDispatcher("postupload.jsp");
                    dispatcher.forward(request, response);
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

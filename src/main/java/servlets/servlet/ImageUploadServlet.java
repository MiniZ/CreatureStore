package main.java.servlets.servlet;

import main.java.db.managers.AccountManager;
import main.java.db.managers.PostManager;
import main.java.models.Account;
import main.java.models.Post;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@WebServlet("/AvatarUpload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50)
public class ImageUploadServlet extends StorageServlet {
    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_LOCATION_PROPERTY_KEY = "storage.location";
    private String uploadsDirName;
    private static final String POST_SAVE_DIR = "posts";
    private static final String AVATAR_SAVE_DIR = "avatars";

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
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        AccountManager manager = (AccountManager) getServletContext()
                .getAttribute(AccountManager.ATTRIBUTE_NAME);

        PostManager postManager = (PostManager) getServletContext()
                .getAttribute(PostManager.ATTRIBUTE_NAME);

        String type = request.getParameter("type");
        String fileName = request.getParameter("name");

        switch (type) {
            case "avatar" :
                String display_name = (String) request.getSession()
                        .getAttribute("dispay_name");
                if (display_name == null) {
                    response.sendRedirect("index.jsp");
                    return;
                }
                Account acc = manager.getImgSrcByDisplayName(display_name);
                // constructs path of the directory to save uploaded file
                String savePath = uploadsDirName + File.separator + AVATAR_SAVE_DIR;
                if (acc.getImgSrc() != null || !acc.getImgSrc().equals("")) {
                    File fileStd = new File(savePath + File.separator + AVATAR_SAVE_DIR
                            + File.separator + acc.getImgSrc());
                    fileStd.delete();
                }
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List items = null;
                try {
                    items = upload.parseRequest(request);
                } catch (FileUploadException ignored) {}
                FileItem item = null;
                if (items != null) {
                    item = (FileItem) items.get(0);
                }
                File file = new File(savePath);
                if (!file.exists()) {
                    //noinspection ResultOfMethodCallIgnored
                    file.mkdirs();
                }
                byte[] data = new byte[0];
                if (item != null) {
                    data = IOUtils.toByteArray(item.getInputStream());
                }
                String id = UUID.randomUUID().toString();
                String result = id.length() + "_" + id + fileName;
                Path path = Paths.get(savePath + File.separator + result);
                Files.write(path, data);
                manager.updateUSerImgSrc(savePath + File.separator + result, display_name);
                //refresh user page
                break;
            case "post" :
                String display_name_1 = (String) request.getSession()
                        .getAttribute("dispay_name");
                String post_id = (String) request.getSession()
                        .getAttribute("post_id");
                if (display_name_1 == null) {
                    response.sendRedirect("index.jsp");
                    return;
                }
                Post post = postManager.getPostImgSrcById(post_id);
                // constructs path of the directory to save uploaded file
                String savePath1 = uploadsDirName + File.separator + POST_SAVE_DIR;
                if (post.getImgSrc() != null || !post.getImgSrc().equals("")) {
                    File fileStd = new File(savePath1 + File.separator + POST_SAVE_DIR
                            + File.separator + post.getImgSrc());
                    fileStd.delete();
                }
                FileItemFactory factory1 = new DiskFileItemFactory();
                ServletFileUpload upload1 = new ServletFileUpload(factory1);
                List items1 = null;
                try {
                    items1 = upload1.parseRequest(request);
                } catch (FileUploadException ignored) {}
                FileItem item1 = null;
                if (items1 != null) {
                    item1 = (FileItem) items1.get(0);
                }
                File file1 = new File(savePath1);
                if (!file1.exists()) {
                    //noinspection ResultOfMethodCallIgnored
                    file1.mkdirs();
                }
                byte[] data1 = new byte[0];
                if (item1 != null) {
                    data = IOUtils.toByteArray(item1.getInputStream());
                }
                String id1 = UUID.randomUUID().toString();
                String result1 = id1.length() + "_" + id1 + fileName;
                Path path1 = Paths.get(savePath1 + File.separator + result1);
                Files.write(path1, data1);
//                pos.updateUSerImgSrc(savePath1 + File.separator + result1, display_name1);
                break;
        }
    }

    /*
     * Extracts file's extensions from given filename.
     */
    private String getFileExtension(String fileName) {
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0)
            extension = fileName.substring(i + 1);
        return extension;
    }

    /*
     * Extracts file name from HTTP header content-disposition
     */
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }

}

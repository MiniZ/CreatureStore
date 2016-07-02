package main.java.servlets.servlet;

import main.java.db.managers.AccountManager;
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
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@WebServlet("/ImageUploadServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50)
public class ImageUploadServlet extends StorageServlet {
    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_LOCATION_PROPERTY_KEY = "storage.location";
    private String uploadsDirName;
    private static final String POST_SAVE_DIR = "posts";


    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 50 * 1024 * 1024;
    private int maxMemSize = 4 * 1024 *1024;
    private File file ;
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
        isMultipart = ServletFileUpload.isMultipartContent(request);
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(maxMemSize);
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax( maxFileSize );

        try{
            // Parse the request to get file items.
            List fileItems = upload.parseRequest(request);
            // Process the uploaded file items
            for (Object fileItem : fileItems) {
                FileItem item = (FileItem) fileItem;
                if (!item.isFormField()) {
                    AccountManager manager = (AccountManager) getServletContext()
                            .getAttribute(AccountManager.ATTRIBUTE_NAME);

                    String display_name = (String) request.getSession().getAttribute("display_name");
                    if (display_name == null) {
                        response.sendRedirect("index.jsp");
                        return;
                    }
                    String imgsrc = manager.getImgSrcByDisplayName(display_name);
                    // constructs path of the directory to save uploaded file
                    String savePath = uploadsDirName + File.separator + AVATAR_SAVE_DIR;
                    if (imgsrc != null) {
                        File fileStd = new File(savePath + File.separator + AVATAR_SAVE_DIR
                                + File.separator + imgsrc);
                        //noinspection ResultOfMethodCallIgnored
                        fileStd.delete();
                    }
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
                    manager.updateUSerImgSrc(savePath + File.separator + filename, display_name);
                    //refresh user page
                    RequestDispatcher dispatcher = request
                            .getRequestDispatcher("profile.jsp?username=" + display_name);
                    dispatcher.forward(request, response);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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

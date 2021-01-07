import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;

//@WebServlet("/MainServlet")
@WebServlet(name = "MainServlet", urlPatterns = {"/MainServlet"})
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
	maxFileSize=1024*1024*10,      // 10MB
	maxRequestSize=1024*1024*50)   // 50MB
public class MainServlet extends HttpServlet {
	/**
	 * Name of the directory where uploaded files will be saved, relative to
	 * the web application directory.
	 */
	private static final String SAVE_DIR = "uploadFiles";
	/**
	 * handles file upload
	 */
	protected void doPost(HttpServletRequest request,
						  HttpServletResponse response) throws ServletException, IOException {
		// gets absolute path of the web application
		String appPath = request.getServletContext().getRealPath("");
		// constructs path of the directory to save uploaded file
		String savePath = appPath + File.separator + SAVE_DIR;

		// creates the save directory if it does not exists
		File fileSaveDir = new File(savePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir();
		}

		for (Part part : request.getParts()) {
			String fileName = extractFileName(part);
			// refines the fileName in case it is an absolute path
			fileName = new File(fileName).getName();
			part.write(savePath + File.separator + fileName);
		}
		request.setAttribute("message", "Upload has been done successfully!");
		getServletContext().getRequestDispatcher("/message.jsp").forward(
			request, response);
	}

	/**
	 * Extracts file name from HTTP header content-disposition
	 */
	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length()-1);
			}
		}
		return "";
	}

/** загрузка файла на сервер через старую библиотеку UploadFile
 *  - данная библиотека не поддерживается сборщиком Maven
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		if (request.getContentType().contains("multipart/form-data")) {
			PostData multidata = new PostData(request);
			String path = multidata.getParameter("path");
			System.out.println("Server tries to save your file into " + path);
			FileData tempFile = multidata.getFileData("fileToUpload");
			if (tempFile != null) {
				saveFile(tempFile, path);
			}
		}
		try (PrintWriter out = response.getWriter()) {
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet UpLoadServlet</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Servlet UpLoadServlet at " + request.getContextPath() + "</h1>");
			out.println("</body>");
			out.println("</html>");
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setAttribute("name", "My_Servlet");
		request.getRequestDispatcher("mypage.jsp").forward(request, response);
	}

	private void saveFile(FileData tempFile, String path) {
		String filename = path + File.separator + tempFile.getFileName();
		File f = new File(filename);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(f);
		} catch (FileNotFoundException ex) {
			Logger.getLogger(MainServlet.class.getName()).log(Level.SEVERE, null, ex);
		}
		if (fos != null) {
			try {
				fos.write(tempFile.getByteData());     // паолучение данных из файла
				fos.close();
			} catch (IOException ex) {
				Logger.getLogger(MainServlet.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	*/
}

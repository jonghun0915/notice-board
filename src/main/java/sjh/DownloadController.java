package sjh;

import java.io.IOException;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sjh.FileUtil;
import com.oreilly.servlet.MultipartRequest;

//  Servlet implementation class DownloadController
 

public class DownloadController extends HttpServlet {
	 @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {
	        // 매개변수 받기
	        String ofsjh = req.getParameter("ofsjh");  // 원본 파일명
	        String sfsjh = req.getParameter("sfsjh");  // 저장된 파일명
	        String numsjh = req.getParameter("numsjh");      // 게시물 일련번호

	        // 파일 다운로드
	        FileUtil.download(req, resp, "/Uploads", sfsjh, ofsjh);

	        // 해당 게시물의 다운로드 수 1 증가
	        MVCBoardDAO dao = new MVCBoardDAO();
	        dao.downCountPlus(numsjh);
	        dao.close();
	    }
}

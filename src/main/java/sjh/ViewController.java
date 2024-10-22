package sjh;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.oreilly.servlet.MultipartRequest;


public class ViewController extends HttpServlet{
	@Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        // 게시물 불러오기
        MVCBoardDAO dao = new MVCBoardDAO();
        String numsjh = req.getParameter("numsjh");
        dao.updateVisitCount(numsjh);  // 조회수 1 증가
        MVCBoardDTO dto = dao.selectView(numsjh);
        dao.close();

        // 줄바꿈 처리
        dto.setConsjh(dto.getConsjh().replaceAll("\r\n", "<br/>"));

        // 게시물(dto) 저장 후 뷰로 포워드
        req.setAttribute("dto", dto);
        req.getRequestDispatcher("View.jsp").forward(req, resp);
    }
}

package sjh;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import sjh.FileUtil;
import sjh.JSFunction;

public class EditController extends HttpServlet {
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String numsjh = req.getParameter("numsjh");
        MVCBoardDAO dao = new MVCBoardDAO();
        MVCBoardDTO dto = dao.selectView(numsjh);
        req.setAttribute("dto", dto);
        req.getRequestDispatcher("Edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        // 1. 파일 업로드 처리 =============================
        // 업로드 디렉터리의 물리적 경로 확인
        String saveDirectory = req.getServletContext().getRealPath("/Uploads");

        // 초기화 매개변수로 설정한 첨부 파일 최대 용량 확인
        ServletContext application = this.getServletContext();
        int maxPostSize = Integer.parseInt(application.getInitParameter("maxPostSize"));

        // 파일 업로드
        MultipartRequest mr = FileUtil.uploadFile(req, saveDirectory, maxPostSize);

        if (mr == null) {
            // 파일 업로드 실패
            JSFunction.alertBack(resp, "첨부 파일이 제한 용량을 초과합니다.");
            return;
        }

        // 2. 파일 업로드 외 처리 =============================
        // 수정 내용을 매개변수에서 얻어옴
        String numsjh = mr.getParameter("numsjh");
        String prevOfile = mr.getParameter("prevOfile");
        String prevSfile = mr.getParameter("prevSfile");

        String nasjh = mr.getParameter("nasjh");
        String tisjh = mr.getParameter("tisjh");
        String consjh = mr.getParameter("consjh");
            
        // 비밀번호는 session에서 가져옴
        HttpSession session = req.getSession();
        String pwsjh = (String)session.getAttribute("pwsjh");

        // DTO에 저장
        MVCBoardDTO dto = new MVCBoardDTO();
        dto.setNumsjh(numsjh);
        dto.setNasjh(nasjh);
        dto.setTisjh(tisjh);
        dto.setConsjh(consjh);
        dto.setPwsjh(pwsjh);
            
        // 원본 파일명과 저장된 파일 이름 설정
        String fileName = mr.getFilesystemName("ofsjh");
        if (fileName != null) {
            // 첨부 파일이 있을 경우 파일명 변경
            // 새로운 파일명 생성
            String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
            String ext = fileName.substring(fileName.lastIndexOf("."));
            String newFileName = now + ext;

            // 파일명 변경
            File oldFile = new File(saveDirectory + File.separator + fileName);
            File newFile = new File(saveDirectory + File.separator + newFileName);
            oldFile.renameTo(newFile);

            // DTO에 저장
            dto.setOfsjh(fileName);  // 원래 파일 이름
            dto.setSfsjh(newFileName);  // 서버에 저장된 파일 이름

            // 기존 파일 삭제
            FileUtil.deleteFile(req, "/Uploads", prevSfile);
        }
        else {
            // 첨부 파일이 없으면 기존 이름 유지
            dto.setOfsjh(prevOfile);
            dto.setSfsjh(prevSfile);
        }

        // DB에 수정 내용 반영
        MVCBoardDAO dao = new MVCBoardDAO();
        int result = dao.updatePost(dto);
        dao.close();

        // 성공 or 실패?
        if (result == 1) {  // 수정 성공
            session.removeAttribute("pass");
            resp.sendRedirect("view.do?numsjh=" + numsjh);
        }
        else {  // 수정 실패
            JSFunction.alertLocation(resp, "비밀번호 검증을 다시 진행해주세요.",
                "view.do?numsjh=" + numsjh);
        }
    }
}

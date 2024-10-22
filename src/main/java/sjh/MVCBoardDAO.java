package sjh;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import sjh.DBConnPool;

public class MVCBoardDAO extends DBConnPool{
	
	public MVCBoardDAO() {
        super();
    }

    // 검색 조건에 맞는 게시물의 개수를 반환합니다.
    public int selectCount(Map<String, Object> map) {
        int totalCount = 0;
        String query = "SELECT COUNT(*) FROM sjh039";
        if (map.get("searchWord") != null) {
            query += " WHERE " + map.get("searchField") + " "
                   + " LIKE '%" + map.get("searchWord") + "%'";
        }
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            rs.next();
            totalCount = rs.getInt(1);
        }
        catch (Exception e) {
            System.out.println("게시물 카운트 중 예외 발생");
            e.printStackTrace();
        }

        return totalCount;
    }

    // 검색 조건에 맞는 게시물 목록을 반환합니다(페이징 기능 지원).
    public List<MVCBoardDTO> selectListPage(Map<String,Object> map) {
        List<MVCBoardDTO> board = new Vector<MVCBoardDTO>();
        String query = " "
                     + "SELECT * FROM ( "
                     + "    SELECT Tb.*, ROWNUM rNum FROM ( "
                     + "        SELECT * FROM sjh039 ";

        if (map.get("searchWord") != null)
        {
            query += " WHERE " + map.get("searchField")
                   + " LIKE '%" + map.get("searchWord") + "%' ";
        }

        query += "        ORDER BY numsjh DESC "
               + "    ) Tb "
               + " ) "
               + " WHERE rNum BETWEEN ? AND ?";

        try {
            psmt = con.prepareStatement(query);
            psmt.setString(1, map.get("start").toString());
            psmt.setString(2, map.get("end").toString());
            rs = psmt.executeQuery();

            while (rs.next()) {
                MVCBoardDTO dto = new MVCBoardDTO();

                dto.setNumsjh(rs.getString(1));
                dto.setNasjh(rs.getString(2));
                dto.setTisjh(rs.getString(3));
                dto.setConsjh(rs.getString(4));
                dto.setDatsjh(rs.getDate(5));
                dto.setOfsjh(rs.getString(6));
                dto.setSfsjh(rs.getString(7));
                dto.setDcsjh(rs.getInt(8));
                dto.setPwsjh(rs.getString(9));
                dto.setVcsjh(rs.getInt(10));

                board.add(dto);
            }
        }
        catch (Exception e) {
            System.out.println("게시물 조회 중 예외 발생");
            e.printStackTrace();
        }
        return board;
    }

    // 게시글 데이터를 받아 DB에 추가합니다(파일 업로드 지원).
    public int insertWrite(MVCBoardDTO dto) {
        int result = 0;
        try {
            String query = "INSERT INTO sjh039 ( "
                         + " numsjh, nasjh, tisjh, consjh, ofsjh, sfsjh, pwsjh) "
                         + " VALUES ( "
                         + " seq_board_num.NEXTVAL,?,?,?,?,?,?)";
            psmt = con.prepareStatement(query);
            psmt.setString(1, dto.getNasjh());
            psmt.setString(2, dto.getTisjh());
            psmt.setString(3, dto.getConsjh());
            psmt.setString(4, dto.getOfsjh());
            psmt.setString(5, dto.getSfsjh());
            psmt.setString(6, dto.getPwsjh());
            result = psmt.executeUpdate();
        }
        catch (Exception e) {
            System.out.println("게시물 입력 중 예외 발생");
            e.printStackTrace();
        }
        return result;
    }

    // 주어진 일련번호에 해당하는 게시물을 DTO에 담아 반환합니다.
    public MVCBoardDTO selectView(String numsjh) {
        MVCBoardDTO dto = new MVCBoardDTO();  // DTO 객체 생성
        String query = "SELECT * FROM sjh039 WHERE numsjh=?";  // 쿼리문 템플릿 준비
        try {
            psmt = con.prepareStatement(query);  // 쿼리문 준비
            psmt.setString(1, numsjh);  // 인파라미터 설정
            rs = psmt.executeQuery();  // 쿼리문 실행

            if (rs.next()) {  // 결과를 DTO 객체에 저장
                dto.setNumsjh(rs.getString(1));
                dto.setNasjh(rs.getString(2));
                dto.setTisjh(rs.getString(3));
                dto.setConsjh(rs.getString(4));
                dto.setDatsjh(rs.getDate(5));
                dto.setOfsjh(rs.getString(6));
                dto.setSfsjh(rs.getString(7));
                dto.setDcsjh(rs.getInt(8));
                dto.setPwsjh(rs.getString(9));
                dto.setVcsjh(rs.getInt(10));
            }
        }
        catch (Exception e) {
            System.out.println("게시물 상세보기 중 예외 발생");
            e.printStackTrace();
        }
        return dto;  // 결과 반환
    }

    // 주어진 일련번호에 해당하는 게시물의 조회수를 1 증가시킵니다.
    public void updateVisitCount(String numsjh) {
        String query = "UPDATE sjh039 SET "
                     + " vcsjh=vcsjh+1 "
                     + " WHERE numsjh=?"; 
        try {
            psmt = con.prepareStatement(query);
            psmt.setString(1, numsjh);
            psmt.executeQuery();
        }
        catch (Exception e) {
            System.out.println("게시물 조회수 증가 중 예외 발생");
            e.printStackTrace();
        }
    }

    // 다운로드 횟수를 1 증가시킵니다.
    public void downCountPlus(String numsjh) {
        String sql = "UPDATE sjh039 SET "
                + " dcsjh=dcsjh+1 "
                + " WHERE numsjh=? "; 
        try {
            psmt = con.prepareStatement(sql);
            psmt.setString(1, numsjh);
            psmt.executeUpdate();
        }
        catch (Exception e) {}
    }
    // 입력한 비밀번호가 지정한 일련번호의 게시물의 비밀번호와 일치하는지 확인합니다.
    public boolean confirmPassword(String pwsjh, String numsjh) {
        boolean isCorr = true;
        try {
            String sql = "SELECT COUNT(*) FROM sjh039 WHERE pwsjh=? AND numsjh=?";
            psmt = con.prepareStatement(sql);
            psmt.setString(1, pwsjh);
            psmt.setString(2, numsjh);
            rs = psmt.executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) {
                isCorr = false;
            }
        }
        catch (Exception e) {
            isCorr = false;
            e.printStackTrace();
        }
        return isCorr;
    }

    // 지정한 일련번호의 게시물을 삭제합니다.
    public int deletePost(String numsjh) {
        int result = 0;
        try {
            String query = "DELETE FROM sjh039 WHERE numsjh=?";
            psmt = con.prepareStatement(query);
            psmt.setString(1, numsjh);
            result = psmt.executeUpdate();
        }
        catch (Exception e) {
            System.out.println("게시물 삭제 중 예외 발생");
            e.printStackTrace();
        }
        return result;
    }

    // 게시글 데이터를 받아 DB에 저장되어 있던 내용을 갱신합니다(파일 업로드 지원).
    public int updatePost(MVCBoardDTO dto) {
        int result = 0;
        try {
            // 쿼리문 템플릿 준비
            String query = "UPDATE sjh039"
                         + " SET tisjh=?, nasjh=?, consjh=?, ofsjh=?, sfsjh=? "
                         + " WHERE numsjh=? and pwsjh=?";

            // 쿼리문 준비
            psmt = con.prepareStatement(query);
            psmt.setString(1, dto.getTisjh());
            psmt.setString(2, dto.getNasjh());
            psmt.setString(3, dto.getConsjh());
            psmt.setString(4, dto.getOfsjh());
            psmt.setString(5, dto.getSfsjh());
            psmt.setString(6, dto.getNumsjh());
            psmt.setString(7, dto.getPwsjh());

            // 쿼리문 실행
            result = psmt.executeUpdate();
        }
        catch (Exception e) {
            System.out.println("게시물 수정 중 예외 발생");
            e.printStackTrace();
        }
        return result;
    }


}

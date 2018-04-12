package kr.co.bit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import kr.co.bit.database.ConnectionManager;
import kr.co.bit.vo.MemberVO;

public class MemberDAO {

	public MemberVO select(String id) {
		MemberVO vo = null;
		// 조회
		ConnectionManager mgr = new ConnectionManager();
		Connection con = mgr.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select * from Member_tbl where user_id = ?";
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				vo = new MemberVO();
				vo.setId(rs.getString(1));
				vo.setPw(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setEmail(rs.getString(4));
				vo.setZipcode(rs.getString(5));
				vo.setAddr1(rs.getString(6));
				vo.setAddr2(rs.getString(7));
				vo.setTool(rs.getString(8));
				String temp = rs.getString(9); // langs
				// 배열로 변환하는 코드 작성

				String[] langs = temp.split("-");
				String[] vals = { "", "", "", "" };
				for (String index : langs) {
					int idx = Integer.parseInt(index);
					vals[idx] = index;
				}

				vo.setLangs(vals);
				vo.setProject(rs.getString(10));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (vo == null) {
			vo = new MemberVO();
			vo.setId(id);
		}
		mgr.connectClose(con, stmt, rs);
		return vo;
	}

	public boolean insert(MemberVO vo) {
		System.out.println("MemberDAO 들어옵니다.");
		boolean flag = false;
		ConnectionManager mgr = new ConnectionManager();
		Connection con = mgr.getConnection();
		String sql = "insert into member_tbl values (?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement stmt = null;
		try {
			System.out.println("insert들어옴");
			stmt = con.prepareStatement(sql);
			stmt.setString(1, vo.getId());
			stmt.setString(2, vo.getPw());
			stmt.setString(3, vo.getName());
			stmt.setString(4, vo.getEmail());
			stmt.setString(5, vo.getZipcode());
			stmt.setString(6, vo.getAddr1());
			stmt.setString(7, vo.getAddr2());
			stmt.setString(8, vo.getTool());
			String[] temp = vo.getLangs();
			StringBuffer sb = new StringBuffer(temp[0]);
			for (int i = 1; i < temp.length; i++) {
				// - 구분자로 해서 구현
				sb.append("-" + temp[i]);
			}
			stmt.setString(9, sb.toString());
			stmt.setString(10, vo.getProject());
			int affectedCount = stmt.executeUpdate();
			if (affectedCount > 0) {
				flag = true;
				System.out.println("삽입완료");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			mgr.connectClose(con, stmt, null);
		}
		return flag;
	}

	public ArrayList<MemberVO> selectAll() {
		ArrayList<MemberVO> list = null;
		// 테이블 접속 코드
		// Departments에 접속해서 모든 자료 가져와서 콘솔에 출력
		ConnectionManager mgr = new ConnectionManager();
		Connection con = mgr.getConnection();
		Statement stmt;
		try {
			stmt = con.createStatement();
			String sql = "select * from member_tbl";
			ResultSet rs = stmt.executeQuery(sql);
			list = new ArrayList<MemberVO>();
			MemberVO vo = null;
			System.out.println("selectAll While 들어가기전");
			while (rs.next()) {
				vo = new MemberVO();
				vo.setId(rs.getString(1));
				vo.setPw(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setEmail(rs.getString(4));
				vo.setZipcode(rs.getString(5));
				vo.setAddr1(rs.getString(6));
				vo.setAddr2(rs.getString(7));
				vo.setTool(rs.getString(8));
				String temp = rs.getString(9); // langs
				// 배열로 변환하는 코드 작성

				String[] langs = temp.split("-");
				String[] vals = { "", "", "", "" };
				for (String index : langs) {
					int idx = Integer.parseInt(index);
					vals[idx] = index;
				}

				vo.setLangs(vals);
				vo.setProject(rs.getString(10));
				list.add(vo);
			}
			System.out.println("selectAll 다 끝남");
			mgr.connectClose(con, stmt, rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<MemberVO> searchAll() {
		ArrayList<MemberVO> list = null;
		ConnectionManager mgr = new ConnectionManager();
		Connection con = mgr.getConnection();
		PreparedStatement pstmt = null;
		try {
			list = new ArrayList<MemberVO>();
			String sql = "select * from member_tbl;";
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				MemberVO vo = new MemberVO();
				vo.setId(rs.getString(1));
				vo.setPw(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setEmail(rs.getString(4));
				vo.setZipcode(rs.getString(5));
				vo.setAddr1(rs.getString(6));
				vo.setAddr2(rs.getString(7));
				vo.setTool(rs.getString(8));
				String temp = rs.getString(9); // langs
				// 배열로 변환하는 코드 작성

				String[] langs = temp.split("-");
				String[] vals = { "", "", "", "" };
				for (String index : langs) {
					int idx = Integer.parseInt(index);
					vals[idx] = index;
				}
				vo.setLangs(vals);
				vo.setProject(rs.getString(10));
				list.add(vo);
			}
			mgr.connectClose(con, pstmt, rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("DAO 마지막줄");
		return list;
	}
	
	public void update(MemberVO vo) {
		System.out.println("MemberDAO 들어옵니다.");
		boolean flag = false;
		ConnectionManager mgr = new ConnectionManager();
		Connection con = mgr.getConnection();
		String sql = "update member_tbl SET USER_ID = ?, USER_PW = ?, USER_NAME=?, email=?, zipcode=?, addr1=?, addr2=?, tool=?, lang=?, prj=? where user_id = '" + vo.getId() + "'";
		PreparedStatement stmt = null;
		
		try {
					
			stmt = con.prepareStatement(sql);
			stmt.setString(1, vo.getId());
			stmt.setString(2, vo.getPw());
			stmt.setString(3, vo.getName());
			stmt.setString(4, vo.getEmail());
			stmt.setString(5, vo.getZipcode());
			stmt.setString(6, vo.getAddr1());
			stmt.setString(7, vo.getAddr2());
			stmt.setString(8, vo.getTool());
			String[] temp = vo.getLangs();
			StringBuffer sb = new StringBuffer(temp[0]);
			for (int i = 1; i < temp.length; i++) {
				// - 구분자로 해서 구현
				sb.append("-" + temp[i]);
			}
			stmt.setString(9, sb.toString());
			stmt.setString(10, vo.getProject());
			int affectedCount = stmt.executeUpdate();
			if (affectedCount > 0) {
				flag = true;
				System.out.println("삽입완료");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			mgr.connectClose(con, stmt, null);
		}
	}
	public void delete(String id) {
		System.out.println("DOA delete 메소드 안에 들어옴");
		ConnectionManager mgr = new ConnectionManager();
		Connection con = mgr.getConnection();
		System.out.println("con 준비 완료");
		String sql = "delete from member_tbl where user_id='"+id+"'";
		
		PreparedStatement pstmt = null;
		try {
			System.out.println(id);
			System.out.println("Try문 안에 들어오고");
			pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
			//pstmt.setString(1, id);
			System.out.println("삭제 완료 리얼리얼");
//			System.out.println("ID DELETE");
//			int affectedCount = pstmt.executeUpdate(sql);
//			if (affectedCount > 0) {
//				System.out.println("삭제 REAL 완료");
//			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mgr.connectClose(con, pstmt, null);
	}
}

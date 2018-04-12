package kr.co.bit.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.co.bit.database.ConnectionManager;
import kr.co.bit.vo.ZipcodeVO;

public class ZipcodeDAO {
	private ZipcodeVO getInstance(String line) {
		ZipcodeVO vo = new ZipcodeVO();
		// �м��ؼ� - , �� �и�
		// VO �ν��Ͻ� ����
		String[] temp = line.split(",");
		vo.setZipcode(temp[0]);
		vo.setSido(temp[1]);
		vo.setGugun(temp[2].equals("") ? " " : temp[2]);
		vo.setDong(temp[3]);
		System.out.println("temp4 " + temp[4].equals(""));
		vo.setRi(temp[4].equals("") ? " " : temp[4]);
		vo.setBldg(temp[5].equals("") ? " " : temp[5]);
		vo.setBunji(temp[6].equals("") ? " " : temp[6]);
		vo.setSeq(temp[7]);
		return vo;
	}

	public ArrayList<ZipcodeVO> getSearchList(String dong) {
		ArrayList<ZipcodeVO> list = new ArrayList<ZipcodeVO>();
		// System.out.println(dong);
		ConnectionManager mgr = new ConnectionManager();
		Connection con = mgr.getConnection();
		String sql = "select * from zipcode_tbl where dong like ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + dong + "%");
			// System.out.println(sql);
			rs = pstmt.executeQuery();
			ZipcodeVO vo = null;
			while (rs.next()) {
				vo = new ZipcodeVO();
				vo.setZipcode(rs.getString(1));
				vo.setSido(rs.getString(2));
				vo.setGugun(rs.getString(3));
				vo.setDong(rs.getString(4));
				vo.setRi(rs.getString(5));
				vo.setBldg(rs.getString("bldg"));
				vo.setBunji(rs.getString("bunji"));
				vo.setSeq(rs.getString("seq"));
				list.add(vo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			mgr.connectClose(con, pstmt, rs);
		}

		return list;
	}

	public boolean insert(String path) {
		boolean flag = false;
		ConnectionManager mgr = new ConnectionManager();
		Connection con = mgr.getConnection();
		// ���Ͽ� �����ؼ� ������ �о�� 5���� ������ �Է��Ѵ�.
		// File ��ü����
		File file = new File(path);
		ArrayList<ZipcodeVO> list = new ArrayList<ZipcodeVO>();
		// ��Ʈ�� ����
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			while ((line = br.readLine()) != null) {
				// �Ѷ��� �о ������ ��
				// ����Ʈ�� ����
				// System.out.println(line);
				list.add(this.getInstance(line));
				// break;
			}
			br.close();
			fr.close();
			// flag = true; �׽�Ʈ��
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(list.size());
		String sql = "insert into zipcode_tbl values (?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			// ����Ʈ�� ������ �Ѳ����� �Է�
			for (ZipcodeVO vo : list) {
				// ZipcodeVO vo = list.get(0);
				pstmt.setString(1, vo.getZipcode());
				pstmt.setString(2, vo.getSido());
				pstmt.setString(3, vo.getGugun());
				pstmt.setString(4, vo.getDong());
				pstmt.setString(5, vo.getRi());
				pstmt.setString(6, vo.getBldg());
				pstmt.setString(7, vo.getBunji());
				pstmt.setString(8, vo.getSeq());

				int affectedCount = pstmt.executeUpdate();
				// if(affectedCount>0) {
				// flag = true;
				// }
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mgr.connectClose(con, pstmt, null);
		return flag;
	}

	public boolean insertV2(String path) {
		boolean flag = false;
		ConnectionManager mgr = new ConnectionManager();
		Connection con = mgr.getConnection();
		// ���Ͽ� �����ؼ� ������ �о�� 5���� ������ �Է��Ѵ�.
		// File ��ü����
		File file = new File(path);
		ArrayList<ZipcodeVO> list = new ArrayList<ZipcodeVO>();
		// ��Ʈ�� ����
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			while ((line = br.readLine()) != null) {
				// �Ѷ��� �о ������ ��
				// ����Ʈ�� ����
				// System.out.println(line);
				list.add(this.getInstance(line));
				// break;
			}
			br.close();
			fr.close();
			// flag = true; �׽�Ʈ��
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(list.size());
		String sql = "insert into zipcode2_tbl values (?,?,?,?,?,?,?,zipcode_seq2.NEXTVAL)";
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false); // 기본적으로 세팅되엉 ㅣㅆ는 커밋을 꺼야한다.
			pstmt = con.prepareStatement(sql);
			// ����Ʈ�� ������ �Ѳ����� �Է�

			int count = 0;
			for (ZipcodeVO vo : list) {
				// ZipcodeVO vo = list.get(0);
				pstmt.setString(1, vo.getZipcode());
				pstmt.setString(2, vo.getSido());
				pstmt.setString(3, vo.getGugun());
				pstmt.setString(4, vo.getDong());
				pstmt.setString(5, vo.getRi());
				pstmt.setString(6, vo.getBldg());
				pstmt.setString(7, vo.getBunji());

				pstmt.addBatch();// 쿼리를 보내지 않고 쭉 모았다가 한 번에 보내는 메소드.
				count++; // 몇 개의 배치가 쌓이는지 보기 위한 카운트
				if (count % 100 == 0) {
					pstmt.executeBatch();

				}
				//
				// int affectedCount = pstmt.executeUpdate();
				// if(affectedCount==0) {
				// flag = true;
				// break;
				// }
			}
			pstmt.executeBatch();
			System.out.println("삽입 완료");
			con.commit();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = true;
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mgr.connectClose(con, pstmt, null);
		}

		return !flag;
	}

}

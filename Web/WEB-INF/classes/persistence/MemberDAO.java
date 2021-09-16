package persistence;

import domain.MemberVO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MemberDAO {
	private static Map<String, MemberVO> storage = new HashMap();
	Connection conn = null;
	PreparedStatement pstmt = null;
	String jdbc_driver = "org.mariadb.jdbc.Driver";
	String jdbc_url = "jdbc:mariadb://pianokim.cafe24.com:3306/pianokim";

	void connect() {
		try {
			Class.forName(this.jdbc_driver);
			this.conn = DriverManager.getConnection(this.jdbc_url, "pianokim", "Password!");
		} catch (Exception var2) {
			var2.printStackTrace();
		}

	}

	void disconnect() {
		if (this.pstmt != null) {
			try {
				this.pstmt.close();
			} catch (SQLException var3) {
				var3.printStackTrace();
			}
		}

		if (this.conn != null) {
			try {
				this.conn.close();
			} catch (SQLException var2) {
				var2.printStackTrace();
			}
		}

	}

	public int login(MemberVO member) {
		String sql = "select * from member where id = ?";
		this.connect();
		int result= -1;
		ResultSet rs = null;

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setString(1, member.getID());
			rs = this.pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString("passwd").equals(member.getPasswd())) {
					result = 1;
				}
				else result = 2;
			}
			else {
				result = 0;
			}
		} catch (Exception var15) {
			var15.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (this.pstmt != null) {
					this.pstmt.close();
				}

				if (this.conn != null) {
					this.conn.close();
				}
			} catch (Exception var14) {
				var14.printStackTrace();
			}
			this.connect();
		}
		return result;
	}

	public boolean join(MemberVO member) {
		try {
			storage.put(member.getID(), member);
			return true;
		} catch (Exception var3) {
			return false;
		}
	}

	public int joinIdCheck(String id) {
		int result = -1;
		this.connect();
		String sql = "select * from member where id=?";

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setString(1, id);
			ResultSet rs = this.pstmt.executeQuery();
			if (rs.next()) {
				result = 0;
			} else {
				result = 1;
			}

			System.out.println("아이디 중복체크결과 : " + result);
		} catch (Exception var8) {
			var8.printStackTrace();
		} finally {
			this.disconnect();
		}

		return result;
	}

	public boolean add(MemberVO vo) {
		this.connect();
		String sql = "insert into member values (?,?,?,?)";

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setString(1, vo.getID());
			this.pstmt.setString(2, vo.getPasswd());
			this.pstmt.setString(3, vo.getName());
			this.pstmt.setString(4, vo.getEmail());
			this.pstmt.executeUpdate();
		} catch (SQLException var7) {
			var7.printStackTrace();
		} finally {
			this.disconnect();
		}

		return true;
	}

	public boolean update(MemberVO vo) {
		this.connect();
		String sql = "UPDATE member SET passwd=?, name=?, mail=? where id = ?";

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setString(1, vo.getPasswd());
			this.pstmt.setString(2, vo.getName());
			this.pstmt.setString(3, vo.getEmail());
			this.pstmt.setString(4, vo.getID());
			this.pstmt.executeUpdate();
		} catch (SQLException var7) {
			var7.printStackTrace();
		} finally {
			this.disconnect();
		}

		return true;
	}

	public MemberVO read(String id) {
		this.connect();
		MemberVO member = new MemberVO();
		String sql = "select * from member where id = ?";

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setString(1, id);
			ResultSet rs = this.pstmt.executeQuery();

			while (rs.next()) {
				member.setID(rs.getString("id"));
				member.setPasswd(rs.getString("passwd"));
				member.setName(rs.getString("name"));
				member.setEmail(rs.getString("mail"));
			}

			rs.close();
		} catch (SQLException var8) {
			var8.printStackTrace();
		} finally {
			this.disconnect();
		}

		return member;
	}

	public ArrayList<MemberVO> getMemberList() {
		this.connect();
		ArrayList<MemberVO> memberlist = new ArrayList();
		String sql = "select * from member";

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			ResultSet rs = this.pstmt.executeQuery();

			while (rs.next()) {
				MemberVO vo = new MemberVO();
				vo.setID(rs.getString("id"));
				vo.setPasswd(rs.getString("passwd"));
				vo.setName(rs.getString("name"));
				vo.setEmail(rs.getString("mail"));
				memberlist.add(vo);
			}

			rs.close();
		} catch (SQLException var8) {
			var8.printStackTrace();
		} finally {
			this.disconnect();
		}

		return memberlist;
	}

	public void delete(String id) {
		this.connect();
		String sql = "delete from member where id=?";

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setString(1, id);
			this.pstmt.executeUpdate();
		} catch (SQLException var7) {
			var7.printStackTrace();
		} finally {
			this.disconnect();
		}

	}
}
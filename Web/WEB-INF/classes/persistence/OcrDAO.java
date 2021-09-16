package persistence;

import domain.MemberVO;
import domain.OcrVO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OcrDAO {
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

	public ArrayList<OcrVO> getMemberList(String id) {
		this.connect();
		ArrayList<OcrVO> ocrlist = new ArrayList();
		String sql = "select * from ocr_list where id=?";

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setString(1, id);
			ResultSet rs = this.pstmt.executeQuery();

			while (rs.next()) {
				OcrVO vo = new OcrVO();
				vo.setId(rs.getString("id"));
				vo.setContent(rs.getString("contents"));
				vo.setNum(rs.getInt("num"));
				ocrlist.add(vo);
			}

			rs.close();
		} catch (SQLException var9) {
			var9.printStackTrace();
		} finally {
			this.disconnect();
		}

		return ocrlist;
	}

	public void delete(int num) {
		this.connect();
		String sql = "delete from ocr_list where num=?";

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setInt(1, num);
			this.pstmt.executeUpdate();
		} catch (SQLException var7) {
			var7.printStackTrace();
		} finally {
			this.disconnect();
		}

	}

	public OcrVO readOcr(int num) {
		this.connect();
		OcrVO ocr = new OcrVO();
		String sql = "select * from ocr_list where num = ?";

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setInt(1, num);
			ResultSet rs = this.pstmt.executeQuery();

			while (rs.next()) {
				ocr.setNum(num);
				ocr.setId(rs.getString("id"));
				ocr.setContent(rs.getString("contents"));
			}

			rs.close();
		} catch (SQLException var8) {
			var8.printStackTrace();
		} finally {
			this.disconnect();
		}

		return ocr;
	}
	public void save(OcrVO ocrVO) {
		this.connect();
		String sql = "insert into ocr_list(id,contents) values (?,?)";
		String id = ocrVO.getId();
		String data = ocrVO.getContent();
		try {
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setString(1, id);
			this.pstmt.setString(2, data);
			this.pstmt.executeUpdate();
		} catch (SQLException var7) {
			var7.printStackTrace();
		} finally {
			this.disconnect();
		}
	}
}
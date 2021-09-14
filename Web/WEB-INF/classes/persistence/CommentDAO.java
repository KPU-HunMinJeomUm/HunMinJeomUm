package persistence;

import domain.CommentVO;
import domain.PostVO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CommentDAO {
	private static Map<String, PostVO> storage = new HashMap();
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

	public ArrayList<CommentVO> getFreeCommentList(String id, int num) {
		this.connect();
		ArrayList<CommentVO> commentlist = new ArrayList();
		String sql = "select * from comment where boardid = ? AND postnum=?";

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setInt(1, 1);
			this.pstmt.setInt(2, num);
			ResultSet rs = this.pstmt.executeQuery();

			while (rs.next()) {
				CommentVO vo = new CommentVO();
				vo.setId(rs.getString("id"));
				vo.setContent(rs.getString("content"));
				vo.setPostid(rs.getInt("postnum"));
				vo.setDate(rs.getString("date"));
				vo.setBoardNum(rs.getInt("boardid"));
				commentlist.add(vo);
			}

			rs.close();
		} catch (SQLException var10) {
			var10.printStackTrace();
		} finally {
			this.disconnect();
		}

		return commentlist;
	}

	public ArrayList<CommentVO> getDeclareCommentList(String id, int num) {
		this.connect();
		ArrayList<CommentVO> commentlist = new ArrayList();
		String sql = "select * from comment where boardid = ? AND postnum=?";

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setInt(1, 3);
			this.pstmt.setInt(2, num);
			ResultSet rs = this.pstmt.executeQuery();

			while (rs.next()) {
				CommentVO vo = new CommentVO();
				vo.setId(rs.getString("id"));
				vo.setContent(rs.getString("content"));
				vo.setPostid(rs.getInt("postnum"));
				vo.setDate(rs.getString("date"));
				vo.setBoardNum(rs.getInt("boardid"));
				commentlist.add(vo);
			}

			rs.close();
		} catch (SQLException var10) {
			var10.printStackTrace();
		} finally {
			this.disconnect();
		}

		return commentlist;
	}

	public ArrayList<CommentVO> getSuggestCommentList(String id, int num) {
		this.connect();
		ArrayList<CommentVO> commentlist = new ArrayList();
		String sql = "select * from comment where boardid = ? AND postnum=?";

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setInt(1, 4);
			this.pstmt.setInt(2, num);
			ResultSet rs = this.pstmt.executeQuery();

			while (rs.next()) {
				CommentVO vo = new CommentVO();
				vo.setId(rs.getString("id"));
				vo.setContent(rs.getString("content"));
				vo.setPostid(rs.getInt("postnum"));
				vo.setDate(rs.getString("date"));
				vo.setBoardNum(rs.getInt("boardid"));
				commentlist.add(vo);
			}

			rs.close();
		} catch (SQLException var10) {
			var10.printStackTrace();
		} finally {
			this.disconnect();
		}

		return commentlist;
	}

	public ArrayList<CommentVO> getInfoCommentList(String id, int num) {
		this.connect();
		ArrayList<CommentVO> commentlist = new ArrayList();
		String sql = "select * from comment where boardid = ? AND postnum=?";

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setInt(1, 2);
			this.pstmt.setInt(2, num);
			ResultSet rs = this.pstmt.executeQuery();

			while (rs.next()) {
				CommentVO vo = new CommentVO();
				vo.setId(rs.getString("id"));
				vo.setContent(rs.getString("content"));
				vo.setPostid(rs.getInt("postnum"));
				vo.setDate(rs.getString("date"));
				vo.setBoardNum(rs.getInt("boardid"));
				commentlist.add(vo);
			}

			rs.close();
		} catch (SQLException var10) {
			var10.printStackTrace();
		} finally {
			this.disconnect();
		}

		return commentlist;
	}

	public void comment_add(CommentVO vo) {
		this.connect();
		String sql = "insert into comment(id,postnum,content,date,boardid) values (?,?,?,?,?)";
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String time = format1.format(date);

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setString(1, vo.getId());
			this.pstmt.setInt(2, vo.getPostid());
			this.pstmt.setString(3, vo.getContent());
			this.pstmt.setString(4, time);
			this.pstmt.setInt(5, vo.getBoardNum());
			this.pstmt.executeUpdate();
		} catch (SQLException var10) {
			var10.printStackTrace();
		} finally {
			this.disconnect();
		}

	}

	public void comment_delete(String id, int postId, int boardId, String date) {
		this.connect();
		String sql = "delete from comment where id=? AND postnum=? AND boardid=? AND date=?";

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setString(1, id);
			this.pstmt.setInt(2, postId);
			this.pstmt.setInt(3, boardId);
			this.pstmt.setString(4, date);
			this.pstmt.executeUpdate();
		} catch (SQLException var10) {
			var10.printStackTrace();
		} finally {
			this.disconnect();
		}

	}

	public void comment_delete_id(String id) {
		this.connect();
		String sql = "delete from comment where id=?";

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
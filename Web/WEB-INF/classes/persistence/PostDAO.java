package persistence;

import domain.PostVO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostDAO {
	private static Map<String, PostVO> storage = new HashMap();
	Connection conn = null;
	PreparedStatement pstmt = null;
	String jdbc_driver = "org.mariadb.jdbc.Driver";
	String jdbc_url = "jdbc:mariadb://pianokim.cafe24.com:3306/pianokim";

	void connect() {
		try {
			Class.forName(this.jdbc_driver);
			this.conn = DriverManager.getConnection(this.jdbc_url,"pianokim", "Password!");
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

	public ArrayList<PostVO> getInfoPostList() {
		this.connect();
		ArrayList<PostVO> postlist = new ArrayList();
		String sql = "select * from infoboard";

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			ResultSet rs = this.pstmt.executeQuery();

			while (rs.next()) {
				PostVO vo = new PostVO();
				vo.setPostNum(rs.getInt("postnum"));
				vo.setTitle(rs.getString("title"));
				vo.setId(rs.getString("id"));
				vo.setDate(rs.getString("date"));
				vo.setView(rs.getInt("view"));
				postlist.add(vo);
			}

			rs.close();
		} catch (SQLException var8) {
			var8.printStackTrace();
		} finally {
			this.disconnect();
		}

		return postlist;
	}

	public ArrayList<PostVO> getSuggestPostList() {
		this.connect();
		ArrayList<PostVO> postlist = new ArrayList();
		String sql = "select * from suggestboard";

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			ResultSet rs = this.pstmt.executeQuery();

			while (rs.next()) {
				PostVO vo = new PostVO();
				vo.setPostNum(rs.getInt("postnum"));
				vo.setTitle(rs.getString("title"));
				vo.setId(rs.getString("id"));
				vo.setDate(rs.getString("date"));
				vo.setView(rs.getInt("view"));
				postlist.add(vo);
			}

			rs.close();
		} catch (SQLException var8) {
			var8.printStackTrace();
		} finally {
			this.disconnect();
		}

		return postlist;
	}

	public ArrayList<PostVO> getDeclarePostList() {
		this.connect();
		ArrayList<PostVO> postlist = new ArrayList();
		String sql = "select * from reportboard";

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			ResultSet rs = this.pstmt.executeQuery();

			while (rs.next()) {
				PostVO vo = new PostVO();
				vo.setPostNum(rs.getInt("postnum"));
				vo.setTitle(rs.getString("title"));
				vo.setId(rs.getString("id"));
				vo.setDate(rs.getString("date"));
				vo.setView(rs.getInt("view"));
				postlist.add(vo);
			}

			rs.close();
		} catch (SQLException var8) {
			var8.printStackTrace();
		} finally {
			this.disconnect();
		}

		return postlist;
	}

	public ArrayList<PostVO> getFreePostList() {
		this.connect();
		ArrayList<PostVO> postlist = new ArrayList();
		String sql = "select * from freeboard";

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			ResultSet rs = this.pstmt.executeQuery();

			while (rs.next()) {
				PostVO vo = new PostVO();
				vo.setPostNum(rs.getInt("postnum"));
				vo.setTitle(rs.getString("title"));
				vo.setId(rs.getString("id"));
				vo.setDate(rs.getString("date"));
				vo.setView(rs.getInt("view"));
				postlist.add(vo);
			}

			rs.close();
		} catch (SQLException var8) {
			var8.printStackTrace();
		} finally {
			this.disconnect();
		}

		return postlist;
	}

	public boolean add(PostVO vo, String id) {
		this.connect();
		String sql = "insert into freeboard(title,id,content) values (?,?,?)";
		if (vo.getBoardId() == 2) {
			sql = "insert into infoboard(title,id,content) values (?,?,?)";
		} else if (vo.getBoardId() == 3) {
			sql = "insert into reportboard(title,id,content) values (?,?,?)";
		} else if (vo.getBoardId() == 4) {
			sql = "insert into suggestboard(title,id,content) values (?,?,?)";
		}

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setString(1, vo.getTitle());
			this.pstmt.setString(2, id);
			this.pstmt.setString(3, vo.getContent());
			this.pstmt.executeUpdate();
		} catch (SQLException var8) {
			var8.printStackTrace();
		} finally {
			this.disconnect();
		}

		return true;
	}

	public void increaseView(int boardnum, int num, int view) {
		this.connect();
		int newView = view + 1;
		String sql;
		if (boardnum == 1) {
			sql = "UPDATE freeboard SET view=? where postnum = ?";
		} else if (boardnum == 2) {
			sql = "UPDATE infoboard SET view=? where postnum = ?";
		} else if (boardnum == 3) {
			sql = "UPDATE reportboard SET view=? where postnum = ?";
		} else {
			sql = "UPDATE suggestboard SET view=? where postnum = ?";
		}

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setInt(1, newView);
			this.pstmt.setInt(2, num);
			this.pstmt.executeUpdate();
		} catch (SQLException var10) {
			var10.printStackTrace();
		} finally {
			this.disconnect();
		}

	}

	public void update(PostVO postVO) {
		this.connect();
		String sql;
		if (postVO.getBoardId() == 1) {
			sql = "UPDATE freeboard SET title=?, content=? where postnum = ?";
		} else if (postVO.getBoardId() == 2) {
			sql = "UPDATE infoboard SET title=?, content=? where postnum = ?";
		} else if (postVO.getBoardId() == 3) {
			sql = "UPDATE reportboard SET title=?, content=? where postnum = ?";
		} else {
			sql = "UPDATE suggestboard SET title=?, content=? where postnum = ?";
		}

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setString(1, postVO.getTitle());
			this.pstmt.setString(2, postVO.getContent());
			this.pstmt.setInt(3, postVO.getPostNum());
			this.pstmt.executeUpdate();
		} catch (SQLException var7) {
			var7.printStackTrace();
		} finally {
			this.disconnect();
		}

	}

	public PostVO readFree(int num) {
		this.connect();
		PostVO post = new PostVO();
		String sql = "select * from freeboard where postnum = ?";

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setInt(1, num);
			ResultSet rs = this.pstmt.executeQuery();

			while (rs.next()) {
				post.setPostNum(num);
				post.setTitle(rs.getString("title"));
				post.setContent(rs.getString("content"));
				post.setId(rs.getString("id"));
				post.setView(rs.getInt("view"));
				post.setDate(rs.getString("date"));
			}

			rs.close();
		} catch (SQLException var8) {
			var8.printStackTrace();
		} finally {
			this.disconnect();
		}

		this.increaseView(1, num, post.getView());
		return post;
	}

	public PostVO readInfo(int num) {
		this.connect();
		PostVO post = new PostVO();
		String sql = "select * from infoboard where postnum = ?";

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setInt(1, num);
			ResultSet rs = this.pstmt.executeQuery();

			while (rs.next()) {
				post.setPostNum(num);
				post.setTitle(rs.getString("title"));
				post.setContent(rs.getString("content"));
				post.setId(rs.getString("id"));
				post.setView(rs.getInt("view"));
				post.setDate(rs.getString("date"));
			}

			rs.close();
		} catch (SQLException var8) {
			var8.printStackTrace();
		} finally {
			this.disconnect();
		}

		this.increaseView(2, num, post.getView());
		return post;
	}

	public PostVO readSuggest(int num) {
		this.connect();
		PostVO post = new PostVO();
		String sql = "select * from suggestboard where postnum = ?";

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setInt(1, num);
			ResultSet rs = this.pstmt.executeQuery();

			while (rs.next()) {
				post.setPostNum(num);
				post.setTitle(rs.getString("title"));
				post.setContent(rs.getString("content"));
				post.setId(rs.getString("id"));
				post.setView(rs.getInt("view"));
				post.setDate(rs.getString("date"));
			}

			rs.close();
		} catch (SQLException var8) {
			var8.printStackTrace();
		} finally {
			this.disconnect();
		}

		this.increaseView(4, num, post.getView());
		return post;
	}

	public PostVO readDeclare(int num) {
		this.connect();
		PostVO post = new PostVO();
		String sql = "select * from reportboard where postnum = ?";

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setInt(1, num);
			ResultSet rs = this.pstmt.executeQuery();

			while (rs.next()) {
				post.setPostNum(num);
				post.setTitle(rs.getString("title"));
				post.setContent(rs.getString("content"));
				post.setId(rs.getString("id"));
				post.setView(rs.getInt("view"));
				post.setDate(rs.getString("date"));
			}

			rs.close();
		} catch (SQLException var8) {
			var8.printStackTrace();
		} finally {
			this.disconnect();
		}

		this.increaseView(3, num, post.getView());
		return post;
	}

	public void free_delete(int postId, String date, int boardId) {
		this.connect();
		String sql = "delete from freeboard where postnum=? AND boardid=? AND date=?";

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setInt(1, postId);
			this.pstmt.setInt(2, boardId);
			this.pstmt.setString(3, date);
			this.pstmt.executeUpdate();
		} catch (SQLException var9) {
			var9.printStackTrace();
		} finally {
			this.disconnect();
		}

	}

	public void declare_delete(int postId, String date, int boardId) {
		this.connect();
		String sql = "delete from reportboard where postnum=? AND boardid=? AND date=?";

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setInt(1, postId);
			this.pstmt.setInt(2, boardId);
			this.pstmt.setString(3, date);
			this.pstmt.executeUpdate();
		} catch (SQLException var9) {
			var9.printStackTrace();
		} finally {
			this.disconnect();
		}

	}

	public void suggest_delete(int postId, String date, int boardId) {
		this.connect();
		String sql = "delete from suggestboard where postnum=? AND boardid=? AND date=?";

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setInt(1, postId);
			this.pstmt.setInt(2, boardId);
			this.pstmt.setString(3, date);
			this.pstmt.executeUpdate();
		} catch (SQLException var9) {
			var9.printStackTrace();
		} finally {
			this.disconnect();
		}

	}

	public void info_delete(int postId, String date, int boardId) {
		this.connect();
		String sql = "delete from infoboard where postnum=? AND boardid=? AND date=?";

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setInt(1, postId);
			this.pstmt.setInt(2, boardId);
			this.pstmt.setString(3, date);
			this.pstmt.executeUpdate();
		} catch (SQLException var9) {
			var9.printStackTrace();
		} finally {
			this.disconnect();
		}

	}

	public void free_delete_id(String id) {
		this.connect();
		String sql = "delete from freeboard where id=?";

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

	public void declare_delete_id(String id) {
		this.connect();
		String sql = "delete from reportboard where id=?";

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

	public void suggest_delete_id(String id) {
		this.connect();
		String sql = "delete from suggestboard where id=?";

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

	public void info_delete_id(String id) {
		this.connect();
		String sql = "delete from infoboard where id=?";

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

	public void comment_delete(int postId, int boardId) {
		this.connect();
		String sql = "delete from comment where postnum=? AND boardid=?";

		try {
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setInt(1, postId);
			this.pstmt.setInt(2, boardId);
			this.pstmt.executeUpdate();
		} catch (SQLException var8) {
			var8.printStackTrace();
		} finally {
			this.disconnect();
		}

	}
}
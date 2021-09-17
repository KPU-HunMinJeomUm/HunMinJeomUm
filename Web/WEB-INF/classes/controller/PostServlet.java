package controller;

import domain.CommentVO;
import domain.PostVO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistence.CommentDAO;
import persistence.PostDAO;


public class PostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String cmdReq = "";
		cmdReq = request.getParameter("cmd");
		new PostVO();
		PostDAO postDAO = new PostDAO();
		PostVO postVO;
		String string;
		int num;
		String postNum;
		int postId;
		String date;
		CommentDAO commentDAO;
		RequestDispatcher view;
		if (cmdReq.equals("free")) {
			string = request.getParameter("num");
			num = Integer.parseInt(string);
			postVO = postDAO.readFree(num);
			request.setAttribute("postVO", postVO);
			view = request.getRequestDispatcher("freePost.jsp");
			view.forward(request, response);
		} else if (cmdReq.equals("info")) {
			string = request.getParameter("num");
			num = Integer.parseInt(string);
			postVO = postDAO.readInfo(num);
			request.setAttribute("postVO", postVO);
			view = request.getRequestDispatcher("informationPost.jsp");
			view.forward(request, response);
		} else if (cmdReq.equals("declare")) {
			string = request.getParameter("num");
			num = Integer.parseInt(string);
			postVO = postDAO.readDeclare(num);
			request.setAttribute("postVO", postVO);
			view = request.getRequestDispatcher("declarePost.jsp");
			view.forward(request, response);
		} else if (cmdReq.equals("suggest")) {
			string = request.getParameter("num");
			num = Integer.parseInt(string);
			postVO = postDAO.readSuggest(num);
			request.setAttribute("postVO", postVO);
			view = request.getRequestDispatcher("suggestPost.jsp");
			view.forward(request, response);
		} else if (cmdReq.equals("free_comment_delete")) {
				String id = request.getParameter("id");
				postNum = request.getParameter("num");
				postId = Integer.parseInt(postNum);
				date = request.getParameter("date");
				commentDAO = new CommentDAO();
				commentDAO.comment_delete(id, postId, 1, date);
				postVO = postDAO.readFree(postId);
				request.setAttribute("postVO", postVO);
				view = request.getRequestDispatcher("freePost.jsp");
				view.forward(request, response);
			} else if (cmdReq.equals("free_delete")) {
					postNum = request.getParameter("num");
					postId = Integer.parseInt(postNum);
					date = request.getParameter("date");
					postDAO.free_delete(postId, date, 1);
					postDAO.comment_delete(postId, 1);
					view = request.getRequestDispatcher("freeBoard.jsp");
					view.forward(request, response);
				} else if (cmdReq.equals("free_update")) {
					postNum = request.getParameter("num");
					postId = Integer.parseInt(postNum);
					postVO = postDAO.readFree(postId);
					postVO.setPostNum(postId);
					postVO.setBoardId(1);
					request.setAttribute("postVO", postVO);
					view = request.getRequestDispatcher("postUpdate.jsp");
					view.forward(request, response);
				} else if (cmdReq.equals("declare_update")) {
					postNum = request.getParameter("num");
					postId = Integer.parseInt(postNum);
					postVO = postDAO.readDeclare(postId);
					postVO.setPostNum(postId);
					postVO.setBoardId(3);
					request.setAttribute("postVO", postVO);
					view = request.getRequestDispatcher("postUpdate.jsp");
					view.forward(request, response);
				} else if (cmdReq.equals("suggest_update")) {
					postNum = request.getParameter("num");
					postId = Integer.parseInt(postNum);
					postVO = postDAO.readSuggest(postId);
					postVO.setPostNum(postId);
					postVO.setBoardId(4);
					request.setAttribute("postVO", postVO);
					view = request.getRequestDispatcher("postUpdate.jsp");
					view.forward(request, response);
				} else if (cmdReq.equals("info_update")) {
					postNum = request.getParameter("num");
					postId = Integer.parseInt(postNum);
					postVO = postDAO.readInfo(postId);
					postVO.setPostNum(postId);
					postVO.setBoardId(2);
					request.setAttribute("postVO", postVO);
					view = request.getRequestDispatcher("postUpdate.jsp");
					view.forward(request, response);
				} else if (cmdReq.equals("declare_comment_delete")) {
					String id = request.getParameter("id");
					postNum = request.getParameter("num");
					postId = Integer.parseInt(postNum);
					date = request.getParameter("date");
					commentDAO = new CommentDAO();
					commentDAO.comment_delete(id, postId, 3, date);
					postVO = postDAO.readDeclare(postId);
					request.setAttribute("postVO", postVO);
					view = request.getRequestDispatcher("declarePost.jsp");
					view.forward(request, response);
				} else if (cmdReq.equals("declare_delete")) {
					postNum = request.getParameter("num");
					postId = Integer.parseInt(postNum);
					date = request.getParameter("date");
					postDAO.declare_delete(postId, date, 3);
					postDAO.comment_delete(postId, 3);
					view = request.getRequestDispatcher("declarationBoard.jsp");
					view.forward(request, response);
				} else if (cmdReq.equals("suggest_comment_delete")) {
					String id = request.getParameter("id");
					postNum = request.getParameter("num");
					postId = Integer.parseInt(postNum);
					date = request.getParameter("date");
					commentDAO = new CommentDAO();
					commentDAO.comment_delete(id, postId, 4, date);
					postVO = postDAO.readSuggest(postId);
					request.setAttribute("postVO", postVO);
					view = request.getRequestDispatcher("suggestPost.jsp");
					view.forward(request, response);
				} else if (cmdReq.equals("suggest_delete")) {
					postNum = request.getParameter("num");
					postId = Integer.parseInt(postNum);
					date = request.getParameter("date");
					postDAO.suggest_delete(postId, date, 4);
					postDAO.comment_delete(postId, 4);
					view = request.getRequestDispatcher("suggestionBoard.jsp");
					view.forward(request, response);
				} else if (cmdReq.equals("info_comment_delete")) {
					String id = request.getParameter("id");
					postNum = request.getParameter("num");
					postId = Integer.parseInt(postNum);
					date = request.getParameter("date");
					commentDAO = new CommentDAO();
					commentDAO.comment_delete(id, postId, 2, date);
					postVO = postDAO.readInfo(postId);
					request.setAttribute("postVO", postVO);
					view = request.getRequestDispatcher("informationPost.jsp");
					view.forward(request, response);
				} else if (cmdReq.equals("info_delete")) {
					postNum = request.getParameter("num");
					postId = Integer.parseInt(postNum);
					date = request.getParameter("date");
					postDAO.info_delete(postId, date, 2);
					postDAO.comment_delete(postId, 2);
					view = request.getRequestDispatcher("informationBoard.jsp");
					view.forward(request, response);
				}
			}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PostVO postVO = new PostVO();
		PostDAO postDAO = new PostDAO();
		CommentVO commentVO = new CommentVO();
		CommentDAO commentDAO = new CommentDAO();
		String cmdReq = "";
		cmdReq = request.getParameter("cmd");
		String id = request.getParameter("id");
		RequestDispatcher view;
		if (cmdReq.equals("writing")) {
			postVO.setTitle(request.getParameter("title"));
			postVO.setContent(request.getParameter("content"));
			if (request.getParameter("boardName").equals("자유게시판")) {
				postVO.setBoardId(1);
			} else if (request.getParameter("boardName").equals("정보게시판")) {
				postVO.setBoardId(2);
			} else if (request.getParameter("boardName").equals("신고게시판")) {
				postVO.setBoardId(3);
			} else if (request.getParameter("boardName").equals("건의게시판")) {
				postVO.setBoardId(4);
			}

			postDAO.add(postVO, id);
			if (postVO.getBoardId() == 1) {
				view = request.getRequestDispatcher("freeBoard.jsp");
				view.forward(request, response);
			} else if (postVO.getBoardId() == 2) {
				view = request.getRequestDispatcher("informationBoard.jsp");
				view.forward(request, response);
			} else if (postVO.getBoardId() == 3) {
				view = request.getRequestDispatcher("declarationBoard.jsp");
				view.forward(request, response);
			} else if (postVO.getBoardId() == 4) {
				view = request.getRequestDispatcher("suggestionBoard.jsp");
				view.forward(request, response);
			}
		} else {
			String postNum;
			int postId;
			if (cmdReq.equals("free_comment_add")) {
				postNum = request.getParameter("num");
				postId = Integer.parseInt(postNum);
				commentVO.setContent(request.getParameter("comment_content"));
				commentVO.setBoardNum(1);
				commentVO.setPostid(postId);
				commentVO.setId(id);
				commentDAO.comment_add(commentVO);
				postVO = postDAO.readFree(postId);
				request.setAttribute("postVO", postVO);
				view = request.getRequestDispatcher("freePost.jsp");
				view.forward(request, response);
			} else if (cmdReq.equals("declare_comment_add")) {
				postNum = request.getParameter("num");
				postId = Integer.parseInt(postNum);
				commentVO.setContent(request.getParameter("comment_content"));
				commentVO.setBoardNum(3);
				commentVO.setPostid(postId);
				commentVO.setId(id);
				commentDAO.comment_add(commentVO);
				postVO = postDAO.readDeclare(postId);
				request.setAttribute("postVO", postVO);
				view = request.getRequestDispatcher("declarePost.jsp");
				view.forward(request, response);
			} else if (cmdReq.equals("suggest_comment_add")) {
				postNum = request.getParameter("num");
				postId = Integer.parseInt(postNum);
				commentVO.setContent(request.getParameter("comment_content"));
				commentVO.setBoardNum(4);
				commentVO.setPostid(postId);
				commentVO.setId(id);
				commentDAO.comment_add(commentVO);
				postVO = postDAO.readSuggest(postId);
				request.setAttribute("postVO", postVO);
				view = request.getRequestDispatcher("suggestPost.jsp");
				view.forward(request, response);
			} else if (cmdReq.equals("info_comment_add")) {
				postNum = request.getParameter("num");
				postId = Integer.parseInt(postNum);
				commentVO.setContent(request.getParameter("comment_content"));
				commentVO.setBoardNum(2);
				commentVO.setPostid(postId);
				commentVO.setId(id);
				commentDAO.comment_add(commentVO);
				postVO = postDAO.readInfo(postId);
				request.setAttribute("postVO", postVO);
				view = request.getRequestDispatcher("informationPost.jsp");
				view.forward(request, response);
			} else if (cmdReq.equals("updatePost")) {
				postNum = request.getParameter("postnum");
				postId = Integer.parseInt(postNum);
				String boardNum = request.getParameter("boardId");
				int boardId = Integer.parseInt(boardNum);
				postVO.setPostNum(postId);
				postVO.setTitle(request.getParameter("title"));
				postVO.setContent(request.getParameter("content"));
				if (boardId == 1) {
					postVO.setBoardId(1);
					postDAO.update(postVO);
					view = request.getRequestDispatcher("freeBoard.jsp");
					view.forward(request, response);
				} else if (boardId == 2) {
					postVO.setBoardId(2);
					postDAO.update(postVO);
					view = request.getRequestDispatcher("informationBoard.jsp");
					view.forward(request, response);
				} else if (boardId == 3) {
					postVO.setBoardId(3);
					postDAO.update(postVO);
					view = request.getRequestDispatcher("declarationBoard.jsp");
					view.forward(request, response);
				} else if (boardId == 4) {
					postVO.setBoardId(4);
					postDAO.update(postVO);
					view = request.getRequestDispatcher("suggestionBoard.jsp");
					view.forward(request, response);
				}
			}
		}

	}
}
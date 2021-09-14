package controller;

import domain.MemberVO;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import persistence.CommentDAO;
import persistence.MemberDAO;
import persistence.PostDAO;

public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String cmdReq = "";
		cmdReq = request.getParameter("cmd");
		if (cmdReq.equals("join")) {
			RequestDispatcher view = request.getRequestDispatcher("register.jsp");
			view.forward(request, response);
		}

		if (cmdReq.equals("logout")) {
			HttpSession httpSession = request.getSession(false);
			httpSession.invalidate();
			RequestDispatcher view = request.getRequestDispatcher("home.jsp");
			view.forward(request, response);
		}

		if (cmdReq.equals("delete")) {
			String id = request.getParameter("id");
			MemberDAO dao = new MemberDAO();
			PostDAO postDAO = new PostDAO();
			CommentDAO commentDAO = new CommentDAO();
			dao.delete(id);
			commentDAO.comment_delete_id(id);
			postDAO.free_delete_id(id);
			postDAO.declare_delete_id(id);
			postDAO.info_delete_id(id);
			postDAO.suggest_delete_id(id);
			HttpSession httpSession = request.getSession(false);
			httpSession.invalidate();
			RequestDispatcher view = request.getRequestDispatcher("home.jsp");
			view.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		MemberVO memberVO = new MemberVO();
		MemberDAO memberDAO = new MemberDAO();
		PostDAO postDAO = new PostDAO();
		CommentDAO commentDAO = new CommentDAO();
		String cmdReq = "";
		cmdReq = request.getParameter("cmd");
		RequestDispatcher view;
		if (cmdReq.equals("join")) {
			memberVO.setID(request.getParameter("id"));
			memberVO.setName(request.getParameter("name"));
			memberVO.setPasswd(request.getParameter("passwd"));
			memberVO.setPasswdCheck(request.getParameter("passwdCheck"));
			memberVO.setEmail(request.getParameter("email"));
			memberDAO.add(memberVO);
			view = request.getRequestDispatcher("home.jsp");
			view.forward(request, response);
		} else if (cmdReq.equals("login")) {
			memberVO.setID(request.getParameter("id"));
			memberVO.setPasswd(request.getParameter("passwd"));
			if (memberDAO.login(memberVO) == 1) {
				HttpSession httpSession = request.getSession(true);
				httpSession.setAttribute("sessionId", memberVO.getID());
				view = request.getRequestDispatcher("home.jsp");
				view.forward(request, response);
			}else if(memberDAO.login(memberVO)==0){
				
				view = request.getRequestDispatcher("loginform.jsp");
				view.forward(request, response);
			} else if(memberDAO.login(memberVO)==2){
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('비밀번호가 일치하지 않습니다!');");
				out.println("location.href='loginform.jsp';");
				out.println("</script>");
				out.flush();
				view = request.getRequestDispatcher("loginform.jsp");
				view.forward(request, response);
			}
			else {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('비밀번호나 아이디를 확인해주세요');");
				out.println("location.href='loginform.jsp';");
				out.println("</script>");
				out.flush();
				view = request.getRequestDispatcher("loginform.jsp");
				view.forward(request, response);
			}
		} else if (cmdReq.equals("update")) {
			memberVO.setID(request.getParameter("id"));
			memberVO.setName(request.getParameter("name"));
			memberVO.setPasswd(request.getParameter("passwd"));
			memberVO.setEmail(request.getParameter("email"));
			memberDAO.update(memberVO);
			view = request.getRequestDispatcher("home.jsp");
			view.forward(request, response);
		} else if (cmdReq.equals("admin_delete")) {
			String[] delUser = request.getParameterValues("RowCheck");

			for (int i = 0; i < delUser.length; ++i) {
				memberDAO.delete(delUser[i]);
				commentDAO.comment_delete_id(delUser[i]);
				postDAO.free_delete_id(delUser[i]);
				postDAO.declare_delete_id(delUser[i]);
				postDAO.info_delete_id(delUser[i]);
				postDAO.suggest_delete_id(delUser[i]);
			}

			view = request.getRequestDispatcher("adminPage.jsp");
			view.forward(request, response);
		}

	}
}
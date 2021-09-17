package controller;

import domain.OcrVO;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistence.OcrDAO;


public class OcrServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String cmdReq = "";
		cmdReq = request.getParameter("cmd");
		new OcrVO();
		OcrDAO ocrDAO = new OcrDAO();
		if (cmdReq.equals("ocr")) {
			String string = request.getParameter("num");
			int num = Integer.parseInt(string);
			OcrVO ocrVO = ocrDAO.readOcr(num);
			request.setAttribute("ocrVO", ocrVO);
			RequestDispatcher view = request.getRequestDispatcher("ocr.jsp");
			view.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		new OcrVO();
		OcrDAO ocrDAO = new OcrDAO();
		String cmdReq = "";
		cmdReq = request.getParameter("cmd");
		if (cmdReq.equals("ocr_delete")) {
			String[] delOcr = request.getParameterValues("RowCheck");

			for (int i = 0; i < delOcr.length; ++i) {
				int num = Integer.parseInt(delOcr[i]);
				ocrDAO.delete(num);
			}

			RequestDispatcher view = request.getRequestDispatcher("recentList.jsp");
			view.forward(request, response);
		}

	}
}
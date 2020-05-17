package com.sr03.forumdiscussion.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sr03.forumdiscussion.dao.impl.ForumDAOImpl;
import com.sr03.forumdiscussion.dao.impl.UserDAOImpl;
import com.sr03.forumdiscussion.model.Forum;
import com.sr03.forumdiscussion.model.User;

/**
 * Servlet implementation class ForumManager
 */
@WebServlet("/ForumManager")
public class ForumManager extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ForumManager() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			HttpSession session = request.getSession();
			User currentUser = (User) session.getAttribute("user");
			List<Forum> listForums = ForumDAOImpl.FindAll(currentUser);
			try (PrintWriter out = response.getWriter()) {
				/* TODO output your page here. You may use following sample code. */
				session.setAttribute("listForums", listForums);
				RequestDispatcher rd = request.getRequestDispatcher("affi_list_forum.jsp");
				rd.forward(request, response);
			}
		} catch (SQLException | ClassNotFoundException ex) {
			// TODO Auto-generated catch block
			Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

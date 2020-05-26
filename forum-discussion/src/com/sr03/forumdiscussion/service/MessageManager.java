package com.sr03.forumdiscussion.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sr03.forumdiscussion.dao.impl.*;
import com.sr03.forumdiscussion.model.*;

/**
 * Servlet implementation class MessageManager
 */
@WebServlet("/MessageManager")
public class MessageManager extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MessageDAOImpl messageDAO;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MessageManager() {
		super();
		this.messageDAO = new MessageDAOImpl();
	}

	/**
	 * Processes requests for both HTTP <code>POST</code> methods.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	protected void createProcess(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, ServletException, IOException, SQLException {
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();

		if (session.getAttribute("login") == null) {
			RequestDispatcher rd = request.getRequestDispatcher("echec_login.jsp");
			rd.forward(request, response);

		} else {
			// récupérer des champs depuis la formulaire
			String content = request.getParameter("Message content");
			User currentUser = (User) session.getAttribute("user");
			Integer idUser = currentUser.getId();
			Forum currentForum = (Forum) session.getAttribute("forum");
			Integer idForum = currentForum.getId();
			MessageId newMessage = messageDAO._insert(content, idUser, idForum);
			if (newMessage != null) {
				List<Message> listMessages = (ArrayList<Message>) MessageDAOImpl.FindAllByForum(currentForum);
				
				session.setAttribute("forum", currentForum);
				session.setAttribute("listMessages", listMessages);
			}
			

			response.setContentType("text/html;charset=UTF-8");
			try (PrintWriter out = response.getWriter()) {
				out.println("<h1> Un nouveau message est ajouté : </h1>");
				RequestDispatcher rd = request.getRequestDispatcher("forum.jsp");
				rd.include(request, response);
			}

		}

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		try {
//
//		} catch (SQLException | ClassNotFoundException ex) {
//			Logger.getLogger(ForumManager.class.getName()).log(Level.SEVERE, null, ex);
//		}
//	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("envoyerMessage") != null) {
			try {
				createProcess(request, response);
			} catch (ClassNotFoundException | IOException | SQLException | ServletException e) {
				e.printStackTrace();
			}
		}

	}

}

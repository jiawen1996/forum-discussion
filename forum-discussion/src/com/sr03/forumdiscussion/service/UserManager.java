/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sr03.forumdiscussion.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sr03.forumdiscussion.dao.impl.UserDAOImpl;
import com.sr03.forumdiscussion.model.User;

/**
 * Servlet implementation class UserManager
 * @author lounis
 */
@WebServlet(name = "UserManager", urlPatterns = { "/UserManager" })
public class UserManager extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private UserDAOImpl ud = new UserDAOImpl();
	@Override
	public void init() throws ServletException {
		super.init(); // To change body of generated methods, choose Tools | Templates.

	}
	
	public void creerNvUtilsateur() {
		
	}

	/**
	 * Processes requests for both HTTP <code>POST</code> methods.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		
		
		if (session.getAttribute("login") == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
			try (PrintWriter out = response.getWriter()) {
				/* TODO output your page here. You may use following sample code. */
				out.println("<!DOCTYPE html>");
				out.println("<html>");
				out.println("<head>");
				out.println("<meta http-equiv='refresh' content='5; URL=connexion.html' />");
				out.println("<title> Non autorisé</title>");
				out.println("</head>");
				out.println("<body>");
				out.println(
						"<h1>Vous n'êtes pas connecté ou vous n'êtes pas admin => redirigé vers la page connexion </h1>");
				out.println("</body>");
				out.println("</html>");
			}

		} else {
			//récupérer des champs depuis la formulaire
			String firstName = request.getParameter("User first name");
			String lastName = request.getParameter("User familly name");
			String mail = request.getParameter("User email");
			String gender = request.getParameter("gender");
			String password = request.getParameter("User password");
			int isAdmin = request.getParameter("admin") != null ? 1 : 0;

			UserDAOImpl userDAO = new UserDAOImpl();
			Integer newUserId = userDAO._insert(lastName, firstName, mail, (byte) isAdmin, gender, password);
			User newUser;
			try {
				newUser = UserDAOImpl.FindById(newUserId).get(0);
				response.setContentType("text/html;charset=UTF-8");
				try (PrintWriter out = response.getWriter()) {
					out.println("<h1> Un nouveau utilisateur est ajouté : </h1>");
					out.println(newUser.toString());
					RequestDispatcher rd = request.getRequestDispatcher("menu.jsp");
					rd.include(request, response);
				}
			} catch (SQLException | ClassNotFoundException ex) {
				// TODO Auto-generated catch block
				Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
			}

		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
	// + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		try {
			
			HttpSession session = request.getSession();
			User currentUser = (User) session.getAttribute("user");
			
			if (currentUser == null || currentUser.getIsAdmin() != 1) {
				RequestDispatcher rd = request.getRequestDispatcher("affi_list_forum.jsp");
				rd.forward(request, response);
			} else {
				List<User> listUser = (ArrayList<User>)UserDAOImpl.FindAll();
				session.setAttribute("listUser", listUser);
				RequestDispatcher rd = request.getRequestDispatcher("affi_list_util.jsp");
				rd.forward(request, response);
			}
		} catch (SQLException | ClassNotFoundException ex) {
			Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}

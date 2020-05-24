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

import com.sr03.forumdiscussion.dao.impl.UserDAOImpl;
import com.sr03.forumdiscussion.model.User;

/**
 * Servlet implementation class UserManager
 * 
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
				List<User> listUser = (ArrayList<User>) UserDAOImpl.FindAll();
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
		
		if (request.getParameter("idModify") != null ) {
			try {
				redirectModifyPage(request, response);
			} catch (ClassNotFoundException | ServletException | IOException | SQLException e) {
				e.printStackTrace();
			}
		}
		
		if (request.getParameter("idDelete") != null ) {
			try {
				deleteProcess(request, response);
			} catch (ClassNotFoundException |IOException | SQLException | ServletException e) {
				e.printStackTrace();
			}
		}
		
		if (request.getParameter("validModify") != null ) {
			try {
				modifyProcess(request, response);
			} catch (ClassNotFoundException |IOException | SQLException | ServletException e) {
				e.printStackTrace();
			}
		}
		
		addNewUser(request,response);
	}

	private void addNewUser(HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException {
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();

		if (session.getAttribute("login") == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
			RequestDispatcher rd = request.getRequestDispatcher("echec_login.jsp");
			rd.include(request, response);

		} else {
			// récupérer des champs depuis la formulaire
			String firstName = request.getParameter("User first name");
			String lastName = request.getParameter("User familly name");
			String login = request.getParameter("User login");
			String gender = request.getParameter("gender");
			String password = request.getParameter("User password");
			int isAdmin = request.getParameter("admin") != null ? 1 : 0;

			UserDAOImpl userDAO = new UserDAOImpl();
			Integer newUserId = userDAO._insert(lastName, firstName, login, (byte) isAdmin, gender, password);
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
				Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
			}

		}
	}


	private void modifyProcess(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, ClassNotFoundException, SQLException {
		HttpSession session = request.getSession();
		
		if (session.getAttribute("login") == null 
				|| !"admin".equalsIgnoreCase((String) session.getAttribute("role"))
				|| session.getAttribute("modifUser") == null ) {
			RequestDispatcher rd = request.getRequestDispatcher("echec_login.jsp");
            rd.forward(request, response);
		} else {
			//récupérer des champs depuis la formulaire
			String firstName = request.getParameter("firstNameModif");
			String lastName = request.getParameter("lastNameModif");
			String login = request.getParameter("loginModif");
			String gender = request.getParameter("genderModif");
			String password = request.getParameter("pwdModif");
			String isAdmin = request.getParameter("isAdminModif");
			
			User modifUser = (User) session.getAttribute("modifUser");
			session.removeAttribute("modifUser");
			
			if ( !firstName.equals("")) {
				modifUser.setFirstName(firstName);
			}
			
			if ( !lastName.equals("")) {
				modifUser.setLastName(lastName);
			}
			
			if ( !login.equals("")) {
				modifUser.setLogin(login);
			}
			
			if ( !gender.equals("")) {
				modifUser.setGender(gender);
			}
			
			if ( !password.equals("")) {
				modifUser.setPwd(password);
			}
			
			if ( !isAdmin.equals("")) {
				Byte res = Byte.parseByte(isAdmin);
				modifUser.setIsAdmin(res);
			}
			System.out.println("******** MODIFY *************\n (id : " + modifUser.getId() + "\n lastName : " + modifUser.getLastName()
								+ "\n firstName : " + modifUser.getFirstName()
								+ "\n login : " + modifUser.getLogin()
								+ "\n pwd : " + modifUser.getPwd()
								+ "\n gender : " + modifUser.getGender() 
								+ "\n isAdmin : " + modifUser.getIsAdmin() +" **********");
			
			UserDAOImpl userDAO = new UserDAOImpl();
			userDAO._update(modifUser);
			
			List<User> listUser = (ArrayList<User>) UserDAOImpl.FindAll();
			session.setAttribute("listUser", listUser);
			
			RequestDispatcher rd = request.getRequestDispatcher("affi_list_util.jsp");
			rd.include(request, response);
		}
		
	}

	private void deleteProcess(HttpServletRequest request, HttpServletResponse response) 
			throws ClassNotFoundException, IOException, SQLException, ServletException {
		Integer idUser = Integer.parseInt(request.getParameter("idDelete"));
		HttpSession session = request.getSession();
		
		if (session.getAttribute("login") == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
			RequestDispatcher rd = request.getRequestDispatcher("echec_login.jsp");
            rd.forward(request, response);
		} else {
			
			User user = UserDAOImpl.FindById(idUser).get(0);
			System.out.println("******** id user to delete : " + idUser +" **********");
			UserDAOImpl userDAO = new UserDAOImpl();
			userDAO._delete(user);
			
			List<User> listUser = (ArrayList<User>)UserDAOImpl.FindAll();
			session.setAttribute("listUser", listUser);
			
			RequestDispatcher rd = request.getRequestDispatcher("affi_list_util.jsp");
			rd.include(request, response);
		}
		
	}

	private void redirectModifyPage(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, ClassNotFoundException, SQLException {
		Integer idUser = Integer.parseInt(request.getParameter("idModify"));
		HttpSession session = request.getSession();
		RequestDispatcher rd;
		
		if (session.getAttribute("login") == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
			rd = request.getRequestDispatcher("echec_login.jsp");
		} else {
			User user = UserDAOImpl.FindById(idUser).get(0);
			session.setAttribute("modifUser", user);
			rd = request.getRequestDispatcher("modify_util.jsp");
		}
		rd.include(request, response);
		
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

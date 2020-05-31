/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sr03.forumdiscussion.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
 *
 * @author lounis
 */
@WebServlet(name = "Validation", urlPatterns = { "/Validation" })
public class Validation extends HttpServlet {

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean valid = true;
		response.setContentType("text/html;charset=UTF-8");

		HttpSession session = request.getSession();
		if (session.getAttribute("login") == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
			RequestDispatcher rd = request.getRequestDispatcher("echec_login.jsp");
            rd.forward(request, response);

		} else {
			try {
				String firstName = request.getParameter("User first name");
				String lastName = request.getParameter("User familly name");
				String login = request.getParameter("User login");
				String gender = request.getParameter("gender");
				String password = request.getParameter("User password");

				//vérifier des champs vides
				if (firstName == null || lastName == null || login == null || password == null) {
					System.out.println("Champs non renseignés");
					RequestDispatcher rd = request.getRequestDispatcher("ajouter_nv_util.jsp");
					rd.include(request, response);
					valid = false;
				} else if ("".equals(firstName) || "".equals(lastName) || "".equals(login) || "".equals(password)) {
					System.out.println("Champs vides");
					RequestDispatcher rd = request.getRequestDispatcher("ajouter_nv_util.jsp");
					rd.include(request, response);
					valid = false;

				} else if (UserDAOImpl.FindByloginAndPwd(login, password).size() != 0) {
					valid = false;
					try (PrintWriter out = response.getWriter()) {
						out.println(
								"<h1>A user with the same first and last name already exists. Please choose another login and password.</h1>");
						RequestDispatcher rd=request.getRequestDispatcher("ajouter_nv_util.jsp");  
				        rd.include(request, response); 
					}
				} else {
					valid = true;
				}

	
				if (valid) {
					request.setAttribute("addNewUser", "true");
					RequestDispatcher rd = request.getRequestDispatcher("UserManager");
					rd.forward(request, response);
				} else {
					try (PrintWriter out = response.getWriter()) {
						out.println("Impossible d'ajouter l'utilisateur ! ");
						RequestDispatcher rd=request.getRequestDispatcher("ajouter_nv_util.jsp");  
				        rd.include(request, response); 
						
					}
				}
			} catch (ClassNotFoundException ex) {
				Logger.getLogger(Validation.class.getName()).log(Level.SEVERE, null, ex);
			} catch (SQLException ex) {
				Logger.getLogger(Validation.class.getName()).log(Level.SEVERE, null, ex);
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
		processRequest(request, response);
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
		processRequest(request, response);
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

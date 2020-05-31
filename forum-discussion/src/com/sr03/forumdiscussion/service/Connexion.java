/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sr03.forumdiscussion.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Iterator;
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

import com.sr03.forumdiscussion.dao.IUserDAO;
import com.sr03.forumdiscussion.dao.impl.UserDAOImpl;
import com.sr03.forumdiscussion.model.User;

/**
 *
 * @author lounis
 */
@WebServlet(name = "Connexion", urlPatterns = {"/Connexion"})
public class Connexion extends HttpServlet {
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
//	private UserDAOImpl ud = new UserDAOImpl();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Vérifier si le login existe
            User u = UserDAOImpl.FindByloginAndPwd(request.getParameter("username"), request.getParameter("password")).get(0);
            if (u == null) {
                RequestDispatcher rd = request.getRequestDispatcher("echec_login.jsp");
                rd.forward(request, response);
            } else {
            	HttpSession session = request.getSession();
            	
            	Enumeration<String> attsName = session.getAttributeNames(); 
            	
            	while (attsName.hasMoreElements()) {
            		session.removeAttribute(attsName.nextElement());
            	}
            	
            	session.setAttribute("user", u);
            	RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
            	rd.forward(request, response);

            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
            RequestDispatcher rd = request.getRequestDispatcher("echec_login.jsp");
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
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

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.

    }

}


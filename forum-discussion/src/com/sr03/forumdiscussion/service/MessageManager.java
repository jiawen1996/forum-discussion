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

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageManager() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Integer idForum = Integer.parseInt(request.getParameter("idForum"));
            Forum currentForum = ForumDAOImpl.FindById(idForum).get(0);
            List<Message> listMessages = (ArrayList<Message>) MessageDAOImpl.FindAllByForum(currentForum);
            try (PrintWriter out = response.getWriter()) {

				session.setAttribute("listMessages", listMessages);
				RequestDispatcher rd = request.getRequestDispatcher("affi_list_message.jsp");
				rd.forward(request, response);
			}
		} catch (SQLException | ClassNotFoundException ex) {
			Logger.getLogger(ForumManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
        
    
}

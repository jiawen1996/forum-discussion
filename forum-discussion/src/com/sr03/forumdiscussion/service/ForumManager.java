package com.sr03.forumdiscussion.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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

import com.sr03.forumdiscussion.dao.impl.ForumDAOImpl;
import com.sr03.forumdiscussion.dao.impl.MessageDAOImpl;
import com.sr03.forumdiscussion.dao.impl.UserDAOImpl;
import com.sr03.forumdiscussion.model.Forum;
import com.sr03.forumdiscussion.model.Message;
import com.sr03.forumdiscussion.model.User;

/**
 * Servlet implementation class ForumManager
 */
@WebServlet("/ForumManager")
public class ForumManager extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ForumDAOImpl forumDAO;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ForumManager() {
		super();
		this.forumDAO = new ForumDAOImpl();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("idModify") != null) {
			try {
				redirectModifyPage(request, response);
			} catch (ClassNotFoundException | ServletException | IOException | SQLException e) {
				e.printStackTrace();
			}
		}

		if (request.getParameter("validModify") != null) {
			try {
				modifyProcess(request, response);
			} catch (ClassNotFoundException | IOException | SQLException | ServletException e) {
				e.printStackTrace();
			}
		}

		if (request.getParameter("idDelete") != null) {
			try {
				deleteProcess(request, response);
			} catch (ClassNotFoundException | IOException | SQLException | ServletException e) {
				e.printStackTrace();
			}
		}
		if (request.getParameter("idCreate") != null) {
			try {
				createProcess(request, response);
			} catch (ClassNotFoundException | IOException | SQLException | ServletException e) {
				e.printStackTrace();
			}
		}

	}

	private void userSubscription(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, IOException, SQLException, ServletException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = null;
		Integer idEnterForum = Integer.parseInt(request.getParameter("idEnterForum"));
		System.out.println("****** ID FORUM TO ENTER : " + idEnterForum);

		if (session.getAttribute("login") == null) {
			rd = request.getRequestDispatcher("echec_login.jsp");
		} else {
			User user = (User) session.getAttribute("user");
			Integer idUser = user.getId();
			// Si l'utilisateur ne s'est pas enregistré dans ce forum
			boolean res = forumDAO.updateUsers(idEnterForum, idUser);

			if (res) {
				Forum currentForum = ForumDAOImpl.FindById(idEnterForum).get(0);
				List<Message> listMessages = (ArrayList<Message>) MessageDAOImpl.FindAllByForum(currentForum);
				
				session.setAttribute("forum", currentForum);
				session.setAttribute("listMessages", listMessages);

				rd = request.getRequestDispatcher("forum.jsp");
			} else {

				rd = request.getRequestDispatcher("erreur.jsp");
			}
		}
		rd.include(request, response);

	}

	private void modifyProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ClassNotFoundException, SQLException {
		HttpSession session = request.getSession();

		if (session.getAttribute("login") == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))
				|| session.getAttribute("editForum") == null) {
			RequestDispatcher rd = request.getRequestDispatcher("echec_login.jsp");
			rd.forward(request, response);
		} else {
			// récupérer des champs depuis la formulaire
			String title = request.getParameter("Forum title");
			String description = request.getParameter("Forum description");
			String firstName = request.getParameter("Forum owner first name");
			String lastName = request.getParameter("Forum owner last name");

			Forum editForum = (Forum) session.getAttribute("editForum");
			User oldOwner = editForum.getOwner();

			// Check owner
			if (!(firstName.equals("") && lastName.equals(""))) {
				User newOwner = UserDAOImpl.FindByLastAndFirstName(firstName, lastName).get(0);

				if (newOwner != null) {
					editForum.setOwner(newOwner);
				}
			}

			if (!title.equals("")) {
				editForum.setTitle(title);
			}

			if (!description.equals("")) {
				editForum.setDescription(description);
			}

			session.removeAttribute("editForum");
			session.removeAttribute("owner");
			System.out.println("******** MODIFY FORUM *************\n (id : " + editForum.getId() + "\n title : "
					+ editForum.getTitle() + "\n description : " + editForum.getDescription() + "\n owner : "
					+ editForum.getOwner().getLogin() + "\\n **********");

			ForumDAOImpl forumDAO = new ForumDAOImpl();
			forumDAO._update(editForum);

			List<Forum> listForums = (ArrayList<Forum>) ForumDAOImpl.FindAll();
			session.setAttribute("listForums", listForums);

			RequestDispatcher rd = request.getRequestDispatcher("affi_list_forum.jsp");
			rd.include(request, response);
		}

	}

	private void redirectModifyPage(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, IOException, SQLException, ServletException {
		Integer idForum = Integer.parseInt(request.getParameter("idModify"));
		System.out.println("****** ID FORUM TO MODIFY : " + idForum);
		HttpSession session = request.getSession();
		RequestDispatcher rd;

		if (session.getAttribute("login") == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
			rd = request.getRequestDispatcher("echec_login.jsp");
		} else {
			Forum forum = ForumDAOImpl.FindById(idForum).get(0);
			User owner = UserDAOImpl.FindById(forum.getOwner().getId()).get(0);

			session.setAttribute("editForum", forum);
			session.setAttribute("owner", owner);

			rd = request.getRequestDispatcher("modify_forum.jsp");
		}
		rd.include(request, response);

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

		if (session.getAttribute("login") == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
			RequestDispatcher rd = request.getRequestDispatcher("echec_login.jsp");
			rd.forward(request, response);

		} else {
			// récupérer des champs depuis la formulaire
			String title = request.getParameter("Forum title");
			String description = request.getParameter("Forum description");

			User currentUser = (User) session.getAttribute("user");
			Integer newForumId = forumDAO._insert(title, description, currentUser);
			try {
				Forum newForum = ForumDAOImpl.FindById(newForumId).get(0);
				response.setContentType("text/html;charset=UTF-8");
				try (PrintWriter out = response.getWriter()) {
					out.println("<h1> Un nouveau forum est ajouté : </h1>");
					out.println(newForum.toString());
					RequestDispatcher rd = request.getRequestDispatcher("menu.jsp");
					rd.include(request, response);
				}
			} catch (SQLException | ClassNotFoundException ex) {
				// TODO Auto-generated catch block
				Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

	}

	/**
	 * Delete a forum for both HTTP <code>POST</code> methods.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException       if a servlet-specific error occurs
	 * @throws IOException            if an I/O error occurs
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	protected void deleteProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ClassNotFoundException, SQLException {
		Integer idForum = Integer.parseInt(request.getParameter("idDelete"));
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("user");
		Forum forum = ForumDAOImpl.FindById(idForum).get(0);
		System.out.println("******** id forum to delete : " + idForum + " **********");
		forumDAO._delete(forum);

		List<Forum> listForums = (ArrayList<Forum>) ForumDAOImpl.FindAll();
		session.setAttribute("listForums", listForums);

		PrintWriter out = response.getWriter();
		out.println("<h1> Success: supprimer un forum </h1>");
		RequestDispatcher rd = request.getRequestDispatcher("affi_list_forum.jsp");
		rd.include(request, response);

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			HttpSession session = request.getSession();
			List<Forum> listForums = (ArrayList<Forum>) ForumDAOImpl.FindAll();

			if (request.getParameter("idEnterForum") != null) {

				try {
					userSubscription(request, response);
				} catch (ClassNotFoundException | IOException | SQLException | ServletException e) {
					e.printStackTrace();
				}
			} else {
				session.setAttribute("listForums", listForums);
				RequestDispatcher rd = request.getRequestDispatcher("affi_list_forum.jsp");
				rd.forward(request, response);
			}

		} catch (SQLException | ClassNotFoundException ex) {
			Logger.getLogger(ForumManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}

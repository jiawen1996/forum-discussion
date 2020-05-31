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
	private UserDAOImpl userDAO;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ForumManager() {
		super();
		this.forumDAO = new ForumDAOImpl();
		this.userDAO = new UserDAOImpl(); 
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
				modifyForum(request, response);
			} catch (ClassNotFoundException | IOException | SQLException | ServletException e) {
				e.printStackTrace();
			}
		}

		if (request.getParameter("idDelete") != null) {
			try {
				deleteForum(request, response);
			} catch (ClassNotFoundException | IOException | SQLException | ServletException e) {
				e.printStackTrace();
			}
		}
		
		if (request.getParameter("idCreate") != null) {
			try {
				createForum(request, response);
			} catch (ClassNotFoundException | IOException | SQLException | ServletException e) {
				e.printStackTrace();
			}
		}
		
		if (request.getParameter("idQuitForum") != null) {

			try {
				userUnsubscription(request, response);
			} catch (ClassNotFoundException | IOException | SQLException | ServletException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			HttpSession session = request.getSession();
			//enter to forum or show list of forum
			if (request.getParameter("idEnterForum") != null) {

				try {
					userSubscription(request, response);
				} catch (ClassNotFoundException | IOException | SQLException | ServletException e) {
					e.printStackTrace();
				}
			} else {
				session.removeAttribute("forumNoSubs");
				session.removeAttribute("forumSubs");
				
				User user = (User) session.getAttribute("user");
				List<Forum> allForums = (ArrayList<Forum>) ForumDAOImpl.FindAll();
				Set<Forum> forumSubs = userDAO.getForumSubscriptions(user.getId());
				
				
				// remove common forum
				if (forumSubs.size() > 0) {
					List<Forum> forumNoSubs = new ArrayList<Forum>();
					List<Integer> forumSubsID = new ArrayList<Integer>();
					
					for (Forum f : forumSubs) {
						forumSubsID.add(f.getId());
					}

					for (Forum f : allForums) {
						if (forumSubsID.contains(f.getId()) == false) {
							forumNoSubs.add(f);
						}
					}
					
					session.setAttribute("forumNoSubs", forumNoSubs);
					session.setAttribute("forumSubs", forumSubs);
				} else {
					session.setAttribute("forumNoSubs", allForums);
				}
				
				RequestDispatcher rd = request.getRequestDispatcher("affi_list_forum.jsp");
				rd.forward(request, response);
			}
			
		} catch (SQLException | ClassNotFoundException ex) {
			Logger.getLogger(ForumManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}


	/**
	 * User subscribe a forum 
	 * @param request
	 * @param response
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 * @throws ServletException
	 */
	private void userSubscription(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, IOException, SQLException, ServletException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = null;
		Integer idEnterForum = Integer.parseInt(request.getParameter("idEnterForum"));

		if (session.getAttribute("login") == null) {
			rd = request.getRequestDispatcher("echec_login.jsp");
		} else {
			User user = (User) session.getAttribute("user");
			Integer idUser = user.getId();
			
			// Add user to list followers if uses haven't subscribed 
			boolean res = forumDAO.addFollower(idEnterForum, idUser);

			if (res) {
				// Update attributes in session
				session.removeAttribute("forumNoSubs");
				session.removeAttribute("forumSubs");
			
				List<Forum> allForums = (ArrayList<Forum>) ForumDAOImpl.FindAll();
				Set<Forum> forumSubs = userDAO.getForumSubscriptions(user.getId());
				
				
				// remove common forum
				if (forumSubs.size() > 0) {
					List<Forum> forumNoSubs = new ArrayList<Forum>();
					List<Integer> forumSubsID = new ArrayList<Integer>();
					
					for (Forum f : forumSubs) {
						forumSubsID.add(f.getId());
					}
					
					for (Forum f : allForums) {
						if (forumSubsID.contains(f.getId()) == false) {
							forumNoSubs.add(f);
						}
					}
					
					session.setAttribute("forumNoSubs", forumNoSubs);
					session.setAttribute("forumSubs", forumSubs);
				} else {
					session.setAttribute("forumNoSubs", allForums);
				}

				// get forum data
				Forum currentForum = ForumDAOImpl.FindById(idEnterForum).get(0);
				List<Message> listMessages = (ArrayList<Message>) MessageDAOImpl.FindAllByForum(currentForum);

				session.setAttribute("forum", currentForum);
				session.setAttribute("owner", currentForum.getOwner());
				session.setAttribute("listMessages", listMessages);

				rd = request.getRequestDispatcher("MessageManager");
			} else {
				try (PrintWriter out = response.getWriter()) {
					out.println("<h1> Unable to subscribe to the forum </h1>");
					rd = request.getRequestDispatcher("erreur.jsp");
				}
			}
		}
		rd.forward(request, response);
	}

	/***
	 * Admin modify forum's information such as : title, description and owner
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void modifyForum(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ClassNotFoundException, SQLException {
		HttpSession session = request.getSession();

		if (session.getAttribute("login") == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))
				|| session.getAttribute("editForum") == null) {
			RequestDispatcher rd = request.getRequestDispatcher("echec_login.jsp");
			rd.forward(request, response);
		} else {
			// get data form input form
			String title = request.getParameter("Forum title");
			String description = request.getParameter("Forum description");
			String firstName = request.getParameter("Forum owner first name");
			String lastName = request.getParameter("Forum owner last name");

			Forum editForum = (Forum) session.getAttribute("editForum");

			// Check owner's existence and owner's role
			if (!(firstName.equals("") && lastName.equals(""))) {
				User newOwner = UserDAOImpl.FindByLastAndFirstName(firstName, lastName).get(0);

				if (newOwner != null && newOwner.getIsAdmin() == 1) {
					editForum.setOwner(newOwner);
					
					// Add owner to list followers
					forumDAO.addFollower(editForum.getId(), newOwner.getId());
				} else {
					try (PrintWriter out = response.getWriter()) {
						out.println("<h1> Invalid new owner </h1>");
						RequestDispatcher rd = request.getRequestDispatcher("erreur.jsp");
						rd.include(request, response);
					}
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

			forumDAO._update(editForum);

			// Update attributes in session
			session.removeAttribute("forumNoSubs");
			session.removeAttribute("forumSubs");
			
			User user = (User) session.getAttribute("user");
			List<Forum> allForums = (ArrayList<Forum>) ForumDAOImpl.FindAll();
			Set<Forum> forumSubs = userDAO.getForumSubscriptions(user.getId());
			
			
			// remove common forum
			if (forumSubs.size() > 0) {
				List<Forum> forumNoSubs = new ArrayList<Forum>();
				List<Integer> forumSubsID = new ArrayList<Integer>();
				
				for (Forum f : forumSubs) {
					forumSubsID.add(f.getId());
				}
				
				for (Forum f : allForums) {
					if (forumSubsID.contains(f.getId()) == false) {
						forumNoSubs.add(f);
					}
				}
				
				session.setAttribute("forumNoSubs", forumNoSubs);
				session.setAttribute("forumSubs", forumSubs);
			} else {
				session.setAttribute("forumNoSubs", allForums);
			}
			
			// End of modifying process
			session.removeAttribute("owner");
			session.removeAttribute("editForum");

			RequestDispatcher rd = request.getRequestDispatcher("affi_list_forum.jsp");
			rd.include(request, response);
		}

	}


	/**
	 * Redirect to a new page for modifying forum's info
	 * @param request
	 * @param response
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 * @throws ServletException
	 */
	private void redirectModifyPage(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, IOException, SQLException, ServletException {
		Integer idForum = Integer.parseInt(request.getParameter("idModify"));
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
	 * Admin create a new forum
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	protected void createForum(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, ServletException, IOException, SQLException {
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();

		if (session.getAttribute("login") == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
			RequestDispatcher rd = request.getRequestDispatcher("echec_login.jsp");
			rd.forward(request, response);

		} else {
			// get data form input form
			String title = request.getParameter("Forum title");
			String description = request.getParameter("Forum description");

			User currentUser = (User) session.getAttribute("user");
			Integer newForumId = forumDAO._insert(title, description, currentUser);

			// Add user to list followers if uses haven't subscribed
			boolean res = forumDAO.addFollower(newForumId, currentUser.getId());

			try {
				Forum newForum = ForumDAOImpl.FindById(newForumId).get(0);

				response.setContentType("text/html;charset=UTF-8");
				
				try (PrintWriter out = response.getWriter()) {
					out.println("<h1> New forum created </h1>");
					out.println(newForum.getTitle());
					
					RequestDispatcher rd = request.getRequestDispatcher("menu.jsp");
					rd.include(request, response);
				}
			} catch (SQLException | ClassNotFoundException ex) {
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
	protected void deleteForum(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ClassNotFoundException, SQLException {
		Integer idForum = Integer.parseInt(request.getParameter("idDelete"));
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("user");
		Forum forum = ForumDAOImpl.FindById(idForum).get(0);
		List<Message> messages = MessageDAOImpl.FindAllByForum(forum);
		
		System.out.println("******** id forum to delete : " + idForum + " **********");
		
		MessageDAOImpl messageDAO = new MessageDAOImpl();
		for (Message m : messages) {
			messageDAO._delete(m);
		}

		forumDAO._delete(forum);

		//Update attributes in session
		session.removeAttribute("forumNoSubs");
		session.removeAttribute("forumSubs");
		
		List<Forum> allForums = (ArrayList<Forum>) ForumDAOImpl.FindAll();
		Set<Forum> forumSubs = userDAO.getForumSubscriptions(currentUser.getId());
		
		// remove common forum
		if (forumSubs.size() > 0) {
			List<Forum> forumNoSubs = new ArrayList<Forum>();
			List<Integer> forumSubsID = new ArrayList<Integer>();
			
			for (Forum f : forumSubs) {
				forumSubsID.add(f.getId());
			}
			
			for (Forum f : allForums) {
				if (forumSubsID.contains(f.getId()) == false) {
					System.out.println("add");
					forumNoSubs.add(f);
				}
			}
			
			session.setAttribute("forumNoSubs", forumNoSubs);
			session.setAttribute("forumSubs", forumSubs);
		} else {
			session.setAttribute("forumNoSubs", allForums);
		}

		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			out.println("<h1> Successfully delete a forum </h1>");
			RequestDispatcher rd = request.getRequestDispatcher("affi_list_forum.jsp");
			rd.include(request, response);
		}
	}

	/**
	 * User Unsubscribe a forum
	 * If user is the owner, he can't see the forum in his list of subscriptions 
	 * but he is still the owner
	 * @param request
	 * @param response
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 * @throws NumberFormatException
	 * @throws ServletException
	 */
	private void userUnsubscription(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, IOException, SQLException, NumberFormatException, ServletException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = null;
		
		Integer idQuitForum = Integer.parseInt(request.getParameter("idQuitForum"));

		if (session.getAttribute("login") == null) {
			rd = request.getRequestDispatcher("echec_login.jsp");
		} else {
			User user = (User) session.getAttribute("user");
			
			forumDAO.removeFollower(idQuitForum, user.getId());
			
			//Update attributes in session
			session.removeAttribute("forumNoSubs");
			session.removeAttribute("forumSubs");
			
			List<Forum> allForums = (ArrayList<Forum>) ForumDAOImpl.FindAll();
			Set<Forum> forumSubs = userDAO.getForumSubscriptions(user.getId());
			
			
			// remove common forum
			if (forumSubs.size() > 0) {
				List<Forum> forumNoSubs = new ArrayList<Forum>();
				List<Integer> forumSubsID = new ArrayList<Integer>();
				
				for (Forum f : forumSubs) {
					forumSubsID.add(f.getId());
				}
				
				for (Forum f : allForums) {
					if (forumSubsID.contains(f.getId()) == false) {
						System.out.println("add");
						forumNoSubs.add(f);
					}
				}
				
				session.setAttribute("forumNoSubs", forumNoSubs);
				session.setAttribute("forumSubs", forumSubs);
			} else {
				session.setAttribute("forumNoSubs", allForums);
			}
			rd = request.getRequestDispatcher("affi_list_forum.jsp");
			
		}
		rd.include(request, response);		
	}

}

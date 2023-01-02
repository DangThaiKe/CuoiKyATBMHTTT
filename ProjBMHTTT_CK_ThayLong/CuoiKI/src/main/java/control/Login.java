package control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DAO;
import entity.Account;
import uitl.Encrypt;

@WebServlet(name = "Login", urlPatterns = { "/login" })
public class Login extends HttpServlet {
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String username = request.getParameter("user");
		username = Encrypt.toSHA1(username);
		String password = request.getParameter("pass");
		password = Encrypt.toSHA1(password);
		DAO dao = new DAO();
		Account a = dao.login(username, password);
		if (a == null) {
			request.setAttribute("mess", "Wrong user or pass");
			request.getRequestDispatcher("Login.jsp").forward(request, response);
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("acc", a);
			session.setMaxInactiveInterval(1000);
			response.sendRedirect("home");
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	public String getServletInfo() {
		return "Short description";
	}
}

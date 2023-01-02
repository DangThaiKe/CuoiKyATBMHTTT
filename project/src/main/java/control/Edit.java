package control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAO;

@WebServlet(name = "Edit", urlPatterns = { "/edit" })
public class Edit extends HttpServlet {
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String pid = request.getParameter("id");
		String pname = request.getParameter("name");
		String pimage = request.getParameter("image");
		String pprice = request.getParameter("price");
		String ptitle = request.getParameter("title");
		String pdescription = request.getParameter("description");
		String pcategory = request.getParameter("category");
		DAO dao = new DAO();
		dao.editProduct(pname, pimage, pprice, ptitle, pdescription, pcategory, pid);
		response.sendRedirect("manager");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	public String getServletInfo() {
		return "Short description";
	}
}

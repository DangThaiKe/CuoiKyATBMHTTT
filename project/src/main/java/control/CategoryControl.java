package control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAO;
import entity.Category;
import entity.Product;

@WebServlet(name = "CategoryControl", urlPatterns = { "/category" })
public class CategoryControl extends HttpServlet {
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String cateID = request.getParameter("cId");
		// da lay dc category id ve roi
		DAO dao = new DAO();
		List<Product> list = dao.getProductByCID(cateID);
		List<Category> listC = dao.getAllCategory();
		Product last = dao.getLast();

		request.setAttribute("listP", list);
		request.setAttribute("listCC", listC);
		request.setAttribute("p", last);
		request.setAttribute("tag", cateID);
		request.getRequestDispatcher("Home.jsp").forward(request, response);
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

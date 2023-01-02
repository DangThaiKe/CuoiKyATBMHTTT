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

@WebServlet(name = "Home", urlPatterns = { "/home" })
public class Home extends HttpServlet {
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String indexPage = request.getParameter("index");
		if (indexPage == null) {
			indexPage = "1";
		}
		int index = Integer.parseInt(indexPage);
		// b1: get data from dao
		DAO dao = new DAO();
		List<Category> listC = dao.getAllCategory();
		Product last = dao.getLast();
		int count = dao.getTotalProduct();
		int endPage = count / 10;
		if (count % 3 != 0) {
			endPage++;
		}
		List<Product> list = dao.pagingProduct(index);

		// b2: set data to jsp
		request.setAttribute("listP", list);
		request.setAttribute("listCC", listC);
		request.setAttribute("p", last);
		request.setAttribute("endP", endPage);
		request.setAttribute("tag", index);
		request.getRequestDispatcher("Home.jsp").forward(request, response);
		// 404 -> url
		// 500 -> jsp properties
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

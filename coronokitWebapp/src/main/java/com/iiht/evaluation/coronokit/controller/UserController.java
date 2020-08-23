package com.iiht.evaluation.coronokit.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iiht.evaluation.coronokit.dao.KitDao;
import com.iiht.evaluation.coronokit.dao.ProductMasterDao;
import com.iiht.evaluation.coronokit.model.CoronaKit;
import com.iiht.evaluation.coronokit.model.KitDetail;
import com.iiht.evaluation.coronokit.model.OrderSummary;
import com.iiht.evaluation.coronokit.model.ProductMaster;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private KitDao kitDAO;
	private ProductMasterDao productMasterDao;

	public void setKitDAO(KitDao kitDAO) {
		this.kitDAO = kitDAO;
	}

	public void setProductMasterDao(ProductMasterDao productMasterDao) {
		this.productMasterDao = productMasterDao;
	}

	public void init(ServletConfig config) {
		String jdbcURL = config.getServletContext().getInitParameter("jdbcUrl");
		String jdbcUsername = config.getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = config.getServletContext().getInitParameter("jdbcPassword");

		this.kitDAO = new KitDao(jdbcURL, jdbcUsername, jdbcPassword);
		this.productMasterDao = new ProductMasterDao(jdbcURL, jdbcUsername, jdbcPassword);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

		String viewName = "";
		try {
			switch (action) {
			case "newuser":
				viewName = showNewUserForm(request, response);
				break;
			case "insertuser":
				viewName = insertNewUser(request, response);
				break;
			case "showproducts":
				viewName = showAllProducts(request, response);
				break;
			case "addnewitem":
				viewName = addNewItemToKit(request, response);
				break;
			case "deleteitem":
				viewName = deleteItemFromKit(request, response);
				break;
			case "showkit":
				viewName = showKitDetails(request, response);
				break;
			case "placeorder":
				viewName = showPlaceOrderForm(request, response);
				break;
			case "saveorder":
				viewName = saveOrderForDelivery(request, response);
				break;
			case "ordersummary":
				viewName = showOrderSummary(request, response);
				break;
			default:
				viewName = "notfound.jsp";
				break;
			}
		} catch (Exception ex) {

			throw new ServletException(ex.getMessage());
		}
		RequestDispatcher dispatch = request.getRequestDispatcher(viewName);
		dispatch.forward(request, response);

	}

	private String showNewUserForm(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("HeaderMsg", "Corona Kit - Customer Registration");
		request.setAttribute("FooterMsg", "Date : "+LocalDateTime.now().toLocalDate());
		String view = "newuser.jsp";
		return view;
	}

	private String insertNewUser(HttpServletRequest request, HttpServletResponse response) {
		String view = "user?action=showproducts";
		String username = request.getParameter("username");
		String emailid = request.getParameter("emailid");
		String contactnum = request.getParameter("contactnum");
		OrderSummary orderSummary ;
		if (username != null && emailid != null && contactnum != null) {
			HttpSession session = request.getSession();
			if (session.getAttribute("orderSumm") == null) {
				orderSummary = new OrderSummary(new CoronaKit(), new ArrayList<KitDetail>());
				session.setAttribute("orderSumm", orderSummary);
			} else {
				orderSummary = (OrderSummary) session.getAttribute("orderSumm");
			}
			orderSummary.getCoronaKit().setPersonName(username);
			orderSummary.getCoronaKit().setEmail(emailid);
			orderSummary.getCoronaKit().setContactNumber(contactnum);
			try {
				orderSummary.getCoronaKit().setId(kitDAO.getNextCoronaKitId());
			} catch (Exception e) {
				request.setAttribute("exception", e);
				view = "errorPage.jsp";
			}

		} else {
			request.setAttribute("FooterMsg", "Make sure fields are not blank or empty");
			view = "newuser.jsp";
		}
		request.setAttribute("HeaderMsg", "Corona Kit - Find your product");
		request.setAttribute("FooterMsg", "Date : "+LocalDateTime.now().toLocalDate());
		return view;
	}

	private String showAllProducts(HttpServletRequest request, HttpServletResponse response) {
		String view = "showproductstoadd.jsp";
		try {
			List<ProductMaster> products = productMasterDao.getAll();
			request.setAttribute("HeaderMsg", "Corona Kit - Products");
			request.setAttribute("FooterMsg", "Date : "+LocalDateTime.now().toLocalDate());
			request.setAttribute("products", products);
		} catch (Exception exception) {
			request.setAttribute("errmsg", exception.getMessage());
			view = "errorPage.jsp";
		}
		return view;
	}

	private String addNewItemToKit(HttpServletRequest request, HttpServletResponse response) {
		String view = "user?action=showproducts";
		String productid = request.getParameter("productid");
		if (productid != null) {
			Set<Integer> addedProducts = null;
			HttpSession session = request.getSession();
			if (session.getAttribute("addedProducts") == null)
				session.setAttribute("addedProducts", new HashSet<Integer>());
			addedProducts = (Set<Integer>) session.getAttribute("addedProducts");
			if (request.getParameter("productid") != null)
				addedProducts.add(Integer.parseInt(request.getParameter("productid")));
		} else {
			request.setAttribute("errmsg", "Add Item : Product id not found");
			view = "errorPage.jsp";
		}
		request.setAttribute("HeaderMsg", "Corona Kit - Customer");
		request.setAttribute("FooterMsg", "Date : "+LocalDateTime.now().toLocalDate());
		return view;
	}

	private String deleteItemFromKit(HttpServletRequest request, HttpServletResponse response) {

		String view = "user?action=showproducts";
		String productid = request.getParameter("productid");
		if (productid != null) {
			Set<Integer> addedProducts = null;
			HttpSession session = request.getSession();
			if (session.getAttribute("addedProducts") != null) {
				addedProducts = (Set<Integer>) session.getAttribute("addedProducts");
				if (request.getParameter("productid") != null)
					addedProducts.remove(Integer.parseInt(request.getParameter("productid")));
			}
		} else {
			request.setAttribute("errmsg", "Delete Item : Product id not found");
			view = "errorPage.jsp";
		}
		request.setAttribute("HeaderMsg", "Corona Kit - Customer");
		request.setAttribute("FooterMsg", "Item Deleted");
		return view;
	}

	private String showKitDetails(HttpServletRequest request, HttpServletResponse response) {
		String view = "showkit.jsp";
		List<ProductMaster> products;
		Set<Integer> addedProducts = null;
		HttpSession session = request.getSession();
		if (session.getAttribute("addedProducts") != null) {
			addedProducts = (Set<Integer>) session.getAttribute("addedProducts");
			products = new ArrayList<ProductMaster>();
			for (Integer productId : addedProducts) {
				try {
					ProductMaster productMaster = productMasterDao.getById(productId);
					products.add(productMaster);
				} catch (Exception exception) {
					request.setAttribute("errmsg", exception.getMessage());
					view = "errorPage.jsp";
					break;
				}
			}
			request.setAttribute("products", products);
		} else {
			request.setAttribute("txErrMsg", "No products are selected!!");
		}
		request.setAttribute("HeaderMsg", "Corona Kit - Customer");
		request.setAttribute("FooterMsg", "Date : "+LocalDateTime.now().toLocalDate());
		return view;
	}

	private String showPlaceOrderForm(HttpServletRequest request, HttpServletResponse response) {
		String view = "placeorder.jsp";
		OrderSummary orderSummary = (OrderSummary) request.getSession().getAttribute("orderSumm");
		if (orderSummary != null) {
			double totalAmount = 0;
			Enumeration<String> parameterNames = request.getParameterNames();
			Map<Integer, Integer> prdctQntyMap = new HashMap<Integer, Integer>();
			try {
				while (parameterNames.hasMoreElements()) {
					String paramName = parameterNames.nextElement();
					if (paramName.matches("pid\\d+")) {
						int quantity = Integer.parseInt(request.getParameterValues(paramName)[0]);
						prdctQntyMap.put(Integer.parseInt(paramName.replace("pid", "")), quantity);

					}
				}
				for (Entry<Integer, Integer> entry : prdctQntyMap.entrySet()) {
					ProductMaster product = productMasterDao.getById(entry.getKey());
					KitDetail kitDtls = new KitDetail();
					kitDtls.setCoronaKitId(orderSummary.getCoronaKit().getId());
					kitDtls.setProductId(product.getId());
					kitDtls.setQuantity(entry.getValue());
					Double kitAmnt = entry.getValue() * Double.parseDouble(product.getCost());
					kitDtls.setAmount(kitAmnt);
					totalAmount += kitAmnt;
					orderSummary.getKitDetails().add(kitDtls);
					orderSummary.getCoronaKit().setTotalAmount(totalAmount);
				}
			} catch (NumberFormatException exception) {
				request.setAttribute("errmsg", "Incorrect Quantity");
				view = "user?action=showkit";
			} catch (Exception exception) {
				request.setAttribute("errmsg", exception.getMessage());
				view = "errorPage.jsp";
			}

		} else {
			request.setAttribute("errmsg", "Order Status Request failed.");
			view = "errorPage.jsp";
		}
		request.setAttribute("HeaderMsg", "Corona Kit - Customer");
		request.setAttribute("FooterMsg", "Date : "+LocalDateTime.now().toLocalDate());
		return view;
	}

	private String saveOrderForDelivery(HttpServletRequest request, HttpServletResponse response) {
		String view = "placeorder.jsp";
		try {
			if (request.getParameter("address") == null) {
				request.setAttribute("errmsg", "Address details not found");
			}
			if (request.getParameter("address").trim().length() == 0) {
				request.setAttribute("errmsg", "Address can't be blank");
			} else {
				
				OrderSummary orderSummary = (OrderSummary) request.getSession().getAttribute("orderSumm");
				orderSummary.getCoronaKit().setDeliveryAddress(request.getParameter("address"));
				orderSummary.getCoronaKit().setOrderDate(LocalDateTime.now().toString());
				orderSummary.getCoronaKit().setOrderFinalized(true);
				List<KitDetail> kitDtls = orderSummary.getKitDetails();
				for (KitDetail kit : kitDtls) {
					int nextKitId = kitDAO.getNextKitId();
					kit.setId(nextKitId);
					kitDAO.addKit(kit);
				}
				view = "user?action=ordersummary";
			}
		} catch (Exception e) {
			request.setAttribute("errmsg", e.getMessage());
			view = "errorPage.jsp";
		}
		request.setAttribute("HeaderMsg", "Corona Kit - Customer");
		request.setAttribute("FooterMsg", "Date : "+LocalDateTime.now().toLocalDate());
		return view;
	}

	private String showOrderSummary(HttpServletRequest request, HttpServletResponse response) {
		String view = "ordersummary.jsp";
		OrderSummary orderSummary = (OrderSummary) request.getSession().getAttribute("orderSumm");
		if (orderSummary != null) {
			request.setAttribute("coronaKit", orderSummary.getCoronaKit());
			request.setAttribute("kitDetails", orderSummary.getKitDetails());
			request.getSession().setAttribute("orderSumm", null);
			request.getSession().setAttribute("addedProducts",null);
		} else {
			request.setAttribute("errmsg", "Couldn't fetch the address details");
			view = "errorPage.jsp";
		}
		request.setAttribute("HeaderMsg", "Corona Kit - Order");
		request.setAttribute("FooterMsg", "Date : "+LocalDateTime.now().toLocalDate());
		return view;
	}

}
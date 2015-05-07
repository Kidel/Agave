package it.uniroma3.controller.action;

import it.uniroma3.facade.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutCustomer implements Action {

	public String perform(HttpServletRequest request, CustomerFacade customerFacade) {

		HttpSession session = request.getSession();

		session.setAttribute("customerLogged", false);
		session.setAttribute("customer", null);

		return "/success.jsp";

	}

	@Override
	public String perform(HttpServletRequest request,
			ProductFacade productFacade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String perform(HttpServletRequest request, AdminFacade adminFacade) {
		// TODO Auto-generated method stub
		return null;
	}


}

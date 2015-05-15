package it.uniroma3.controller;

import java.util.List;

import it.uniroma3.facade.CustomerFacade;
import it.uniroma3.facade.OrderFacade;
import it.uniroma3.model.OrderLine;
import it.uniroma3.model.Orders;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class CartController {
	
	private Orders cart;
	
	@EJB
	private OrderFacade orderFacade;
	@EJB
	private CustomerFacade customerFacade;
	
	public void setCartFromId(Long idCustomer) {
		try{
			this.cart = customerFacade.getCart(idCustomer);
		}
		catch(Exception e){
			//TODO
		}
	}
	
	public Float getTotal() {
		List<OrderLine> ols = this.cart.getOrderLines();
		Integer size = ols.size();
		Float total = new Float(0);
		
		for (Integer i = 0; i < size; i++) {
			total += (ols.get(i).getQuantity() * ols.get(i).getProduct().getPrice());
		}
		
		return total;
	}

	public Orders getCart() {
		return cart;
	}
	
	public List<OrderLine> getOrderLines() {
		return this.cart.getOrderLines();
	}

	public void setCart(Orders cart) {
		this.cart = cart;
	}

	public OrderFacade getOrderFacade() {
		return orderFacade;
	}

	public void setOrderFacade(OrderFacade orderFacade) {
		this.orderFacade = orderFacade;
	}
	
	

}

package it.uniroma3.controller;

import java.util.List;

import it.uniroma3.facade.CustomerFacade;
import it.uniroma3.facade.OrderFacade;
import it.uniroma3.model.OrderLine;
import it.uniroma3.model.Orders;
import it.uniroma3.model.Product;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class CartController {
	
	private Orders cart;
	private int quantity;
	
	@EJB
	private OrderFacade orderFacade;
	@EJB
	private CustomerFacade customerFacade;
	
	public Orders setCartFromId(Long idCustomer) {
		try{
			this.cart = customerFacade.getCart(idCustomer);
		}
		catch(Exception e){
			//
		}
		return this.cart;
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
	
	public String addProductToCart(Orders cart, Product product){
		Integer quantity = 1;
		if(this.quantity != 0) quantity = this.quantity;
		System.out.println("trying to add " + quantity +" Product to Cart");
		
		try{
			orderFacade.addProductToCart(cart, product, quantity);
			System.out.println("Product added to Cart");
			
			return "cart?faces-redirect=true";
		}
		catch(Exception e){
			System.out.println("Insufficient Storage Quantity");
			return "errorQuantity";
		}

	}
	
	public String removeOrderLine(Orders cart, OrderLine orderLine){
		System.out.println("Trying to Remove OrderLine");
		orderFacade.removeOrderLine(cart, orderLine);
		
		return "cart?faces-redirect=true";
	}
	
	public String confirmCart(Orders cart){
		System.out.println("Creating new Order from Cart");
		
		orderFacade.createOrderFromCart(cart);
		return "cart?faces-redirect=true";
	}
	
	public Orders getCart() {
		return cart;
	}
	
	public List<OrderLine> getOrderLines(Orders cart) {
		return cart.getOrderLines();
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
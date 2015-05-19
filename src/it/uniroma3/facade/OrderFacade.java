package it.uniroma3.facade;

import it.uniroma3.model.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import java.util.Date;
import java.util.List;

@Stateless
public class OrderFacade {
	
    @PersistenceContext(unitName = "agave")
    private EntityManager em;
    
	public OrderFacade() {
	}
    
	public Orders createOrder(Customer customer, List<OrderLine> orderlines) {
		Orders order = new Orders();
		order.setCustomer(customer);
		order.setOrderLines(orderlines);
		
		Date creationTime = new Date();
		order.setCreationTime(creationTime);
		
		em.persist(order);
		return order;
	}
	
	public Orders createCart(Customer customer, List<OrderLine> orderlines) {
		Orders cart = new Orders();
		cart.setCustomer(customer);
		cart.setOrderLines(orderlines);
		
		return cart;
	}
	
	public Orders getOrder(Long id) {
	    Orders order = em.find(Orders.class, id);
		return order;
	}
	
	public OrderLine getOrderLine(Long id){
		OrderLine orderLine = em.find(OrderLine.class, id);
		return orderLine;
	}
	
	public List<Orders> getAllOrders() {
        CriteriaQuery<Orders> cq = em.getCriteriaBuilder().createQuery(Orders.class);
        cq.select(cq.from(Orders.class));
        List<Orders> ordersList = em.createQuery(cq).getResultList();
		return ordersList;
	}

	public void updateOrder(Orders order) {
        em.merge(order);
	}	
	
    private void deleteOrder(Orders order) {
        em.remove(order);
    }

	public void deleteOrder(Long id) {
		Orders order = em.find(Orders.class, id);
        deleteOrder(order);
	}
	
	public void deleteOrderLine(OrderLine orderLine){
		em.remove(orderLine);
	}
	
	public void addProductToCart(Orders cart, Product product, Integer quantity) throws Exception{
		Orders c = getOrder(cart.getId());
		Exception e = new Exception();
		boolean check = true;
		
		if(c.containsProduct(product)){
			System.out.println("Product already exists");
			check = c.updateOrderLine(product, quantity);
		}
		else if(product.getStorageQuantity() >= quantity) {
			OrderLine ol = makeOrderLineFromProduct(product, quantity);
			c.addOrderLine(ol);
		}
		else check = false;
		
		if(!check) throw e;
		
		updateOrder(c);

		System.out.println("Cart Updated");
	}
	
	public OrderLine makeOrderLineFromProduct(Product product, int quantity){
		OrderLine ol = new OrderLine(quantity, product);
		return ol;
	}
	
	public void removeOrderLine(Orders cart, OrderLine orderLine){
		Orders c = getOrder(cart.getId());
		OrderLine olToRemove = getOrderLine(orderLine.getId());
		
		c.removeOrderLine(olToRemove);
		
		deleteOrderLine(olToRemove);
		updateOrder(c);

		System.out.println("OrderLine Removed");
	}
	

}
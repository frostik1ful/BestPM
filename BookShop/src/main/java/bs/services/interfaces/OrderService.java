package bs.services.interfaces;

import java.util.List;

import bs.models.Order;

public interface OrderService {
	public Order getById(Integer id);

	public List<Order> getAll();

	public void deleteById(Integer id);

	public void save(Order order);
}

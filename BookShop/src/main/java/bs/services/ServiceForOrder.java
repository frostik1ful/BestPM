package bs.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import bs.models.Order;
import bs.repositories.OrderRepository;
import bs.services.interfaces.OrderService;

@Service
public class ServiceForOrder implements OrderService {

	private OrderRepository orderRepository;

	@Autowired
	public ServiceForOrder(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	public Order getById(Integer id) {
		// TODO Auto-generated method stub
		return orderRepository.getOne(id);
	}

	@Override
	public List<Order> getAll() {
		// TODO Auto-generated method stub
		return orderRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		Optional<Order> optional = orderRepository.findById(id);
		if (optional.isPresent()) {
			orderRepository.deleteById(id);
		}

	}

	@Override
	public void save(Order order) {
		// TODO Auto-generated method stub
		orderRepository.save(order);
	}

}

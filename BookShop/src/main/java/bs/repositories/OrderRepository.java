package bs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import bs.models.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}

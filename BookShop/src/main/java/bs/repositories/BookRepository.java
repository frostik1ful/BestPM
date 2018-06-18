package bs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import bs.models.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

}

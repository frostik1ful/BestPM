package bs.services.interfaces;

import java.util.List;

import bs.models.Book;

public interface BookService {

	public Book getById(Integer id);

	public List<Book> getAll();

	public void deleteById(Integer id);

	public void save(Book book);

}

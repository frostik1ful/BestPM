package bs.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import bs.models.Book;

import bs.repositories.BookRepository;
import bs.services.interfaces.BookService;

@Service
public class ServiceForBook implements BookService {

	private BookRepository bookRepository;

	@Autowired
	public ServiceForBook(BookRepository bookRepository) {
		this.bookRepository = bookRepository;

	}

	@Override
	public Book getById(Integer id) {
		return bookRepository.getOne(id);
	}

	@Override
	public List<Book> getAll() {
		return bookRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
	}

	@Override
	public void deleteById(Integer id) {
		Optional<Book> optional = bookRepository.findById(id);
		if (optional.isPresent()) {
			bookRepository.deleteById(id);
		}

	}

	@Override
	public void save(Book book) {
		bookRepository.save(book);
	}

}
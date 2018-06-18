package bs.services.interfaces;

import java.util.List;

import bs.models.Author;

public interface AuthorService {

	public List<Author> getAll();

	public Author getById(Integer id);

	public void save(Author author);

	public Author getByName(String name);

	public void deleteById(Integer id);

}

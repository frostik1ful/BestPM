package bs.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import bs.models.Author;
import bs.repositories.AuthorRepository;
import bs.services.interfaces.AuthorService;

@Service
public class ServiceForAuthor implements AuthorService {

	private AuthorRepository authorRepository;

	@Autowired
	public ServiceForAuthor(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	@Override
	public List<Author> getAll() {
		return authorRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
	}

	@Override
	public Author getById(Integer id) {
		return authorRepository.getOne(id);
	}

	@Override
	public void save(Author autor) {
		authorRepository.save(autor);
	}

	@Override
	public Author getByName(String name) {
		return authorRepository.findByName(name);
	}

	@Override
	public void deleteById(Integer id) {
		authorRepository.deleteById(id);

	}

}

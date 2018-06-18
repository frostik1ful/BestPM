package bs.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import bs.models.Genre;
import bs.repositories.GenreRepository;
import bs.services.interfaces.GenreService;

@Service
public class ServiceForGenre implements GenreService {

	private GenreRepository genreRepository;

	@Autowired
	public ServiceForGenre(GenreRepository genderRepository) {
		this.genreRepository = genderRepository;
	}

	@Override
	public List<Genre> getAll() {
		return genreRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
	}

	@Override
	public Genre getById(Integer id) {
		return genreRepository.getOne(id);
	}

	@Override
	public Genre getByName(String name) {
		return genreRepository.findByName(name);
	}

	@Override
	public void save(Genre gender) {
		if (genreRepository.findByName(gender.getName()) == null) {
			genreRepository.save(gender);
		}

	}

	@Override
	public void deleteById(int genreId) {
		// TODO Auto-generated method stub
		genreRepository.deleteById(genreId);
	}

}

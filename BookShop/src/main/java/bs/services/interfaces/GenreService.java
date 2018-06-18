package bs.services.interfaces;

import java.util.List;

import bs.models.Genre;

public interface GenreService {

	public List<Genre> getAll();

	public Genre getById(Integer id);

	public Genre getByName(String name);

	public void save(Genre gender);

	public void deleteById(int genreId);
}

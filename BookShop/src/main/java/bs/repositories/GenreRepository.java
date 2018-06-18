package bs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import bs.models.Genre;

public interface GenreRepository extends JpaRepository<Genre, Integer> {

	public Genre findByName(String name);

}

package bs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import bs.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

	public Author findByName(String name);
}

package guru.springframework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.domain.Category;


public interface CategoryRepository extends JpaRepository<Category, Long> {

	Category findByName(String name);
}

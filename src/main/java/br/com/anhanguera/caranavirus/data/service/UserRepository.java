package br.com.anhanguera.caranavirus.data.service;

import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.anhanguera.caranavirus.data.entity.User;

@EnableJpaRepositories
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//	Streamable<Order> findAll(PageRequest pageRequest, String filter);

	@Query("select u from User u where lower(u.name) like lower(concat('%', :searchTerm, '%')) ")
	Stream<User> findAll(@Param("searchTerm") String searchTerm);

	@Query("select u from User u where lower(u.name) like lower(concat('%', :searchTerm, '%')) ")
	Page<User> search(@Param("searchTerm") String searchTerm, Pageable pageable);

	@Query("select count(u) from User u where lower(u.name) like lower(concat('%', :searchTerm, '%')) ")
	Integer countBy(@Param("searchTerm") String searchTerm);

}
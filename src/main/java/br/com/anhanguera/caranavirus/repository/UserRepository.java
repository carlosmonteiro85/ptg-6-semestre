package br.com.anhanguera.caranavirus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.anhanguera.caranavirus.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
}

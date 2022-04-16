package br.com.anhanguera.caranavirus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.anhanguera.caranavirus.entity.Vacina;

@Repository
public interface VacinaRepository extends JpaRepository<Vacina, Long>{
}

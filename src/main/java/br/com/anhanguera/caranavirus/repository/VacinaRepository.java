package br.com.anhanguera.caranavirus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.anhanguera.caranavirus.entity.Vacina;

@Repository
public interface VacinaRepository extends JpaRepository<Vacina, Long>{
	
	@Query(value = "SELECT * FROM VACINA v WHERE v.VACINA_ID = :idUser ORDER BY v.DATA_APLICACAO DESC ", nativeQuery = true)
	List<Vacina> findLastVacina(@Param("idUser") Long idUser);
}

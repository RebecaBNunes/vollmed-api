package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

	Boolean existsByPacienteIdAndDataBetweenAndMotivoIsNull(Long idPaciente, LocalDateTime primeiroHorario,
			LocalDateTime ultimoHorario);

	Boolean existsByMedicoIdAndDataAndMotivoIsNull(Long idMedico, LocalDateTime data);

	@Query("""
			select c.data
			from Consulta c
			where
			c.id = :idConsulta
			""")
	LocalDateTime findDatabyId(Long idConsulta);

}

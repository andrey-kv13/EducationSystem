package students.dev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import students.dev.rq.CreateProfessorRequest;
import students.dev.rs.CreateProfessorResponse;
import students.dev.entity.Professor;
import students.dev.exception.ProfessorNotFoundException;
import students.dev.mapper.ProfessorMapper;
import students.dev.repository.ProfessorRepository;

@Service
@RequiredArgsConstructor
public class ProfessorService {
    private final ProfessorRepository professorRepository;
    private final ProfessorMapper professorMapper;

    public Page<CreateProfessorResponse> findAll(Pageable pageable) {
        return professorRepository.findAll(pageable).map(professorMapper::toResponse);
    }

    public CreateProfessorResponse findProfessorByName(String firstName, String lastName) {
        Professor professor = professorRepository
                .findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new ProfessorNotFoundException(firstName, lastName));
        return professorMapper.toResponse(professor);
    }

    private Professor findProfessorById(Long id) {
        return professorRepository.findById(id).orElseThrow(() -> new ProfessorNotFoundException(id));
    }

    public CreateProfessorResponse getProfessorById(Long id) {

        return professorMapper.toResponse(findProfessorById(id));
    }

    public CreateProfessorResponse createProfessor(CreateProfessorRequest request) {
        Professor professor = professorMapper.toEntity(request);
        return professorMapper.toResponse(professorRepository.save(professor));
    }
    public CreateProfessorResponse update(Long id, CreateProfessorRequest request) {
        Professor professor = findProfessorById(id);
        professorMapper.update(request, professor);
        return professorMapper.toResponse(professorRepository.save(professor));
    }

    public void deleteProfessor(Long id) {
        Professor professor = findProfessorById(id);
        professorRepository.delete(professor);
    }
}

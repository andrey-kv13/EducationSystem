package students.dev.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import students.dev.dto.CreateProfessorRequest;
import students.dev.dto.CreateProfessorResponse;
import students.dev.service.ProfessorService;

@RequestMapping("/api/professor")
@RestController
@Validated
@RequiredArgsConstructor
public class ProfessorController {
    private final ProfessorService professorService;

    @GetMapping
    public Page<CreateProfessorResponse> getAllProfessors(Pageable pageable) {
        return professorService.findAll(pageable);
    }

    @GetMapping("/search")
    public CreateProfessorResponse findByName(
            @RequestParam @NotBlank(message = "Имя обязательно") String firstName,
            @RequestParam @NotBlank(message = "Фамилия обязательна") String lastName) {
        return professorService.findProfessorByName(firstName, lastName);
    }

    @GetMapping("/{id}")
    public CreateProfessorResponse getProfessorById(@PathVariable Long id) {
        return professorService.getProfessorById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateProfessorResponse createProfessor(@Valid @RequestBody CreateProfessorRequest request) {
        return professorService.createProfessor(request);
    }

    @PutMapping("/{id}")
    public CreateProfessorResponse updateProfessor(
            @PathVariable Long id,
            @Valid @RequestBody CreateProfessorRequest request) {
        return professorService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfessor(@PathVariable Long id) {
        professorService.deleteProfessor(id);

    }

}

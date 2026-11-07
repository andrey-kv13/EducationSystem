package students.dev.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;
import students.dev.controller.ProfessorController;
import students.dev.entity.Professor;
import students.dev.repository.ProfessorRepository;
import students.dev.rq.CreateProfessorRequest;
import students.dev.rs.CreateProfessorResponse;
import students.dev.service.ProfessorService;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class ProfessorIntegrationTest extends PostgresTest {
    private Professor professor;

    @LocalServerPort
    int port;

    @Autowired
    private ProfessorRepository professorRepository;
    private final RestTemplate restTemplate = new RestTemplate();


    @Autowired
    private ProfessorService professorService;

    @Autowired
    private ProfessorController professorController;

    @BeforeEach
    void setUp(){
        professorRepository.deleteAll();
        professor  = professorRepository.save(new Professor(null,
                                        "test professor name",
                                        "last professor name"));
    }

    @AfterEach
    void tearDown(){

        professorRepository.deleteAll();
    }

    @Test
    public void professorSuccessCreateTest(){
        CreateProfessorRequest request = new CreateProfessorRequest("Иван", "Петров");
        ResponseEntity<CreateProfessorResponse> response = restTemplate.postForEntity(("http://localhost:" + port +
                        "/api/v1/professor"), request, CreateProfessorResponse.class);
        CreateProfessorResponse body = response.getBody();


        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "создание преподавателя должно вернуть 201");
        assertNotNull(body, "тело ответа создания не должно быть пустым");
        assertEquals("Иван", body.firstName(), "в ответе должно быть имя из запроса");
        assertEquals("Петров", body.lastName(), "в ответе должна быть фамилия из запроса");

    }

    @Test
    public void professorExist(){
        ResponseEntity<String> response = restTemplate.getForEntity(("http://localhost:" + port +
                "/api/v1/professor"), String.class);

        assertNotNull(response.getBody(), "тело списка преподавателей не должно быть пустым");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "список преподавателей должен вернуть 200");
        assertTrue(response.getBody().contains("test professor name"), "в списке должно быть имя из BeforeEach");
        assertTrue(response.getBody().contains("last professor name"), "в списке должна быть фамилия из BeforeEach");
    }

    @Test
    public void professorSuccessDelete(){
        restTemplate.exchange(("http://localhost:" + port + "/api/v1/professor/"
                        + professor.getId()), HttpMethod.DELETE, null, Void.class);

        ResponseEntity<String> list = restTemplate.getForEntity((
                "http://localhost:" + port) + "/api/v1/professor", String.class);

        assertEquals(HttpStatus.OK, list.getStatusCode(), "список после удаления должен вернуть 200");
        assertNotNull(list.getBody(), "тело списка после удаления не должно быть пустым");
        assertTrue(list.getBody().contains("\"totalElements\":0"), "после удаления в списке не должно остаться преподавателей");
    }

    @Test
    public void professorExistFailed(){
        professorRepository.deleteAll();

        HttpClientErrorException exception = assertThrows(
                HttpClientErrorException.class,
                () -> restTemplate.getForEntity(
                        ("http://localhost:" + port + "/api/v1/professor/" + professor.getId()),
                        CreateProfessorResponse.class));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "запрос несуществующего преподавателя должен вернуть 404");
    }

    @Test
    public void createProfessorFailed(){
        CreateProfessorRequest request = new CreateProfessorRequest(null, null);

        HttpClientErrorException exception = assertThrows(
                HttpClientErrorException.class,
                () -> restTemplate.postForEntity(("http://localhost:" + port +
                        "/api/v1/professor"), request, CreateProfessorResponse.class));


        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode(), "создание с пустыми полями должно вернуть 400");
    }

}

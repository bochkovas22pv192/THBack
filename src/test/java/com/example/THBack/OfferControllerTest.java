package com.example.THBack;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import com.example.THBack.dto.OfferGetDTO;
import com.example.THBack.dto.OfferPostAndPutDTO;
import com.example.THBack.mappers.OfferMapper;
import com.example.THBack.models.Employee;
import com.example.THBack.models.Offer;
import com.example.THBack.models.OfferPhoto;
import com.example.THBack.models.OfferRate;
import com.example.THBack.models.enums.OfferState;
import com.example.THBack.repository.EmployeeRepository;
import com.example.THBack.repository.OfferPhotoRepository;
import com.example.THBack.repository.OfferRateRepository;
import com.example.THBack.repository.OfferRepository;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.LinkedHashSet;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OfferControllerTest {

    @LocalServerPort
    private Integer port;

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    OfferRepository offerRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    OfferPhotoRepository offerPhotoRepository;

    @Autowired
    OfferRateRepository offerRateRepository;

    @Autowired
    OfferMapper offerMapper;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        offerRateRepository.deleteAll();
        offerPhotoRepository.deleteAll();
        offerRepository.deleteAll();
        employeeRepository.deleteAll();


    }

    @Test
    void shouldGetOfferById() {
        Employee employee = new Employee("Ivan", "Ivanov", "Ivanovich",
                "Junior", "88005553535", LocalDate.now(), "Ivan@mail.ru", "Ivan",
                LocalDate.now(), HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d"));
        List<Offer> offers = List.of(
                new Offer(1L, "Помыть полы", "Помыть полы", OfferState.APPROVED,
                        LocalDate.now(), employee, new LinkedHashSet<OfferRate>(), new LinkedHashSet<OfferPhoto>())
        );
        employeeRepository.save(employee);
        offerRepository.saveAll(offers);

        OfferGetDTO[] temp = given()
                .contentType("application/json")
                .body("{\"type\":\"ALL\"}")
                .when()
                .get("/api/v1/offer/")
                .then().statusCode(200)
                .extract().body().as(OfferGetDTO[].class);

        OfferGetDTO result = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/offer/"+temp[0].getId())
                .then()
                .statusCode(200)
                .extract().body().as(OfferGetDTO.class);

        MatcherAssert.assertThat(result, equalTo(offerMapper.offerToOfferGetDTO(new Offer(result.getId(), "Помыть полы", "Помыть полы", OfferState.APPROVED,
                LocalDate.now(), employee, new LinkedHashSet<OfferRate>(), new LinkedHashSet<OfferPhoto>()))));
    }

    @Test
    void shouldGetOfferAll() {
        Employee employee = new Employee("Ivan", "Ivanov", "Ivanovich",
                "Junior", "88005553535", LocalDate.now(), "Ivan@mail.ru", "Ivan",
                LocalDate.now(), HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d"));

        List<Offer> offers = List.of(
                new Offer("Помыть полы", "Помыть полы", OfferState.APPROVED,
                        LocalDate.now(), employee, new LinkedHashSet<OfferRate>(), new LinkedHashSet<OfferPhoto>()),
                new Offer("Помыть окна", "Помыть окна", OfferState.WAITING,
                        LocalDate.now(), employee, new LinkedHashSet<OfferRate>(), new LinkedHashSet<OfferPhoto>())
        );
        employeeRepository.save(employee);
        offerRepository.saveAll(offers);

        OfferGetDTO[] result1= given()
                .contentType("application/json")
                .body("{\"type\":\"APPROVED\"}")
                .when()
                .get("/api/v1/offer/")
                .then().statusCode(200)
                .extract().body().as(OfferGetDTO[].class);

        OfferGetDTO[] result2 = given()
                .contentType("application/json")
                .body("{\"type\":\"WAITING\"}")
                .when()
                .get("/api/v1/offer/")
                .then().statusCode(200)
                .extract().body().as(OfferGetDTO[].class);

        MatcherAssert.assertThat(result1[0], equalTo(offerMapper.offerToOfferGetDTO(new Offer(result1[0].getId(), "Помыть полы", "Помыть полы", OfferState.APPROVED,
                LocalDate.now(), employee, new LinkedHashSet<OfferRate>(), new LinkedHashSet<OfferPhoto>()))));

        MatcherAssert.assertThat(result2[0], equalTo(offerMapper.offerToOfferGetDTO(new Offer(result2[0].getId(), "Помыть окна", "Помыть окна", OfferState.WAITING,
                LocalDate.now(), employee, new LinkedHashSet<OfferRate>(), new LinkedHashSet<OfferPhoto>()))));
    }

    @Test
    void shouldGetOfferAllByUser() {
        Employee employee = new Employee("Ivan", "Ivanov", "Ivanovich",
                "Junior", "88005553535", LocalDate.now(), "Ivan@mail.ru", "Ivan",
                LocalDate.now(), HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d"));
        Employee employee2 = new Employee("Petr", "Petrov", "Ivanovich",
                "Junior", "88005553530", LocalDate.now(), "Petr@mail.ru", "Petr",
                LocalDate.now(), HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d")
        );

        List<Offer> offers = List.of(
                new Offer("Помыть полы", "Помыть полы", OfferState.APPROVED,
                        LocalDate.now(), employee, new LinkedHashSet<OfferRate>(), new LinkedHashSet<OfferPhoto>()),
                new Offer("Помыть окна", "Помыть окна", OfferState.WAITING,
                        LocalDate.now(), employee, new LinkedHashSet<OfferRate>(), new LinkedHashSet<OfferPhoto>()),
                new Offer("Помыть потолок", "Помыть потолок", OfferState.APPROVED,
                LocalDate.now(), employee2, new LinkedHashSet<OfferRate>(), new LinkedHashSet<OfferPhoto>())
        );
        employeeRepository.save(employee);
        employeeRepository.save(employee2);
        offerRepository.saveAll(offers);

        List<Employee> employees = employeeRepository.findAll();

        OfferGetDTO[] result1= given()
                .contentType("application/json")
                .body("{\"type\":\"APPROVED\"}")
                .when()
                .get("/api/v1/offer/user/" + employees.get(0).getId())
                .then().statusCode(200)
                .extract().body().as(OfferGetDTO[].class);

        OfferGetDTO[] result2 = given()
                .contentType("application/json")
                .body("{\"type\":\"APPROVED\"}")
                .when()
                .get("/api/v1/offer/user/" + employees.get(1).getId())
                .then().statusCode(200)
                .extract().body().as(OfferGetDTO[].class);

        MatcherAssert.assertThat(result1[0], equalTo(offerMapper.offerToOfferGetDTO(new Offer(result1[0].getId(), "Помыть полы", "Помыть полы", OfferState.APPROVED,
                LocalDate.now(), employee, new LinkedHashSet<OfferRate>(), new LinkedHashSet<OfferPhoto>()))));

        MatcherAssert.assertThat(result2[0], equalTo(offerMapper.offerToOfferGetDTO(new Offer(result2[0].getId(), "Помыть потолок", "Помыть потолок", OfferState.APPROVED,
                LocalDate.now(), employee2, new LinkedHashSet<OfferRate>(), new LinkedHashSet<OfferPhoto>()))));
    }

    @Test
    void shouldCreateOffer() {
        Employee employee = new Employee("Ivan", "Ivanov", "Ivanovich",
                "Junior", "88005553535", LocalDate.now(), "Ivan@mail.ru", "Ivan",
                LocalDate.now(), HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d"));

        List<Offer> offers = List.of(
                new Offer("Помыть полы", "Помыть полы", OfferState.APPROVED,
                        LocalDate.now(), employee, new LinkedHashSet<OfferRate>(), new LinkedHashSet<OfferPhoto>()),
                new Offer("Помыть окна", "Помыть окна", OfferState.WAITING,
                        LocalDate.now(), employee, new LinkedHashSet<OfferRate>(), new LinkedHashSet<OfferPhoto>())
        );
        employeeRepository.save(employee);
        offerRepository.saveAll(offers);

        List<Employee> employees = employeeRepository.findAll();

        String requestBody = "{\n" +
                "    \"authorId\": "+ employees.getFirst().getId() +",\n" +
                "    \"title\": \"ddd\",\n" +
                "    \"description\": \"ddd\",\n" +
                "    \"datePublished\": \"2024-09-24\",\n" +
                "    \"images\": [\n" +
                "        \"iVBORw0KGgo\"\n" +
                "    ]\n" +
                "}";

        OfferPostAndPutDTO result = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/offer/")
                .then().statusCode(200)
                .extract().body().as(OfferPostAndPutDTO.class);

        List<Offer> offersFromRep = offerRepository.findAll();
        OfferPhoto photo = new OfferPhoto();
        LinkedHashSet<OfferPhoto> photoSet = new LinkedHashSet<OfferPhoto>();
        photoSet.add(photo);
        photoSet.getFirst().setPhoto("iVBORw0KGgo".getBytes());
        photoSet.getFirst().setOffer(offersFromRep.getLast());
        MatcherAssert.assertThat(result, equalTo(offerMapper.offerToOfferPostAndPutDTO(new Offer(offersFromRep.getLast().getId(),
                "ddd", "ddd", OfferState.WAITING,
                LocalDate.parse("2024-09-24",dtf), employee, new LinkedHashSet<OfferRate>(), photoSet))));


    }

    @Test
    void shouldUpdateOffer() {
        Employee employee = new Employee("Ivan", "Ivanov", "Ivanovich",
                "Junior", "88005553535", LocalDate.now(), "Ivan@mail.ru", "Ivan",
                LocalDate.now(), HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d"));

        List<Offer> offers = List.of(
                new Offer("Помыть полы", "Помыть полы", OfferState.APPROVED,
                        LocalDate.now(), employee, new LinkedHashSet<OfferRate>(), new LinkedHashSet<OfferPhoto>()),
                new Offer("Помыть окна", "Помыть окна", OfferState.WAITING,
                        LocalDate.now(), employee, new LinkedHashSet<OfferRate>(), new LinkedHashSet<OfferPhoto>())
        );
        employeeRepository.save(employee);
        offerRepository.saveAll(offers);

        List<Employee> employees = employeeRepository.findAll();

        String requestBody = "{\n" +
                "    \"authorId\": "+ employees.getFirst().getId() +",\n" +
                "    \"title\": \"ddd\",\n" +
                "    \"description\": \"ddd\",\n" +
                "    \"datePublished\": \"2024-09-24\",\n" +
                "    \"images\": [\n" +
                "        \"iVBORw0KGgo\"\n" +
                "    ]\n" +
                "}";

        OfferPostAndPutDTO result = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .put("/api/v1/offer/" + offerRepository.findAll().getFirst().getId())
                .then().statusCode(200)
                .extract().body().as(OfferPostAndPutDTO.class);

        List<Offer> offersFromRep = offerRepository.findAll();
        OfferPhoto photo = new OfferPhoto();
        LinkedHashSet<OfferPhoto> photoSet = new LinkedHashSet<OfferPhoto>();
        photoSet.add(photo);
        photoSet.getFirst().setPhoto("iVBORw0KGgo".getBytes());
        photoSet.getFirst().setOffer(offersFromRep.getFirst());
        MatcherAssert.assertThat(result, equalTo(offerMapper.offerToOfferPostAndPutDTO(new Offer(offersFromRep.getFirst().getId(),
                "ddd", "ddd", OfferState.WAITING,
                LocalDate.parse("2024-09-24",dtf), employee, new LinkedHashSet<OfferRate>(), photoSet))));


    }

//
//    @Test
//    void shouldCreateTasks() {
//        String requestBody = "{\"id\":1,\"email\":\"Ivan@mail.ru\",\"name\":\"Помыть полы\",\"description\":\"Мыть полы\",\"priority\":\"Низкий\",\"emailExecutor\":\"Ivan@mail.ru\",\"date\":\"2025-09-15\",\"timeCreate\":\"2024-09-15T01:32:46.406105\",\"state\":\"NEW\",\"timeUpdate\":\"2024-09-15T01:32:46.406105\"}";
//
//        given().
//                contentType("application/json").
//                body(requestBody).
//                when().
//                post("/tasks/").
//                then();
//
//        Task[] temp = given()
//                .contentType(ContentType.JSON)
//                .when()
//                .get("/tasks")
//                .then().extract().body().as(Task[].class);
//
//        Task res = given()
//                .contentType(ContentType.JSON)
//                .when()
//                .get("/tasks/"+temp[0].getId())
//                .then()
//                .statusCode(200)
//                .extract().body().as(Task.class);
//
//        MatcherAssert.assertThat(res, equalTo(new Task(temp[0].getId(),
//                "Ivan@mail.ru", "Помыть полы", "Мыть полы", "Низкий", "Ivan@mail.ru", LocalDate.parse("2025-09-15", dtf),
//                temp[0].getTimeCreate(), State.NEW, temp[0].getTimeUpdate())));
//    }
//
//    @Test
//    void shouldUpdateTasks() {
//        List<Task> tasks = List.of(
//                new Task("Petr@mail.ru", "Помыть полы", "Мыть полы", "Высокий", "Petr@mail.ru",
//                        LocalDate.now(),  LocalDateTime.now(), State.NEW, LocalDateTime.now() )
//        );
//        taskRepository.saveAll(tasks);
//
//        Task[] taskInDb = given()
//                .contentType(ContentType.JSON)
//                .when()
//                .get("/tasks")
//                .then().extract().body().as(Task[].class);
//
//        String requestBody = "{\"id\":1,\"email\":\"Ivan@mail.ru\",\"name\":\"Помыть полы\",\"description\":\"Мыть полы\",\"priority\":\"Низкий\",\"emailExecutor\":\"Ivan@mail.ru\",\"date\":\"2025-09-15\",\"timeCreate\":\"2024-09-15T01:32:46.406105\",\"state\":\"NEW\",\"timeUpdate\":\"2024-09-15T01:32:46.406105\"}";
//
//        given().
//                contentType("application/json").
//                body(requestBody).
//                when().
//                put("/tasks/" + taskInDb[0].getId()).
//                then().
//                statusCode(200);
//
//        Task res = given()
//                .contentType(ContentType.JSON)
//                .when()
//                .get("/tasks/"+taskInDb[0].getId())
//                .then()
//                .extract().body().as(Task.class);
//
//        Task[] temp = given()
//                .contentType(ContentType.JSON)
//                .when()
//                .get("/tasks")
//                .then().extract().body().as(Task[].class);
//
//        MatcherAssert.assertThat(res, equalTo(new Task(taskInDb[0].getId(),
//                "Ivan@mail.ru", "Помыть полы", "Мыть полы", "Низкий", "Ivan@mail.ru", LocalDate.parse("2025-09-15", dtf),
//                taskInDb[0].getTimeCreate(), State.NEW, temp[0].getTimeUpdate())));
//    }
//
//    private String sendPut(String requestBody){
//        return given().
//                contentType("application/json").
//                body(requestBody).
//                when().
//                put("/tasks/2")
//                .then()
//                .statusCode(400)
//                .extract()
//                .asString();
//    }
//
//    @Test
//    void sendEmptyName() {
//        String requestBody = "{\"id\":2,\"email\":\"Ivan@mail.ru\",\"name\":\"\",\"description\":\"Мыть полы\",\"priority\":\"Низкий\",\"emailExecutor\":\"Ivan@mail.ru\",\"date\":\"2024-09-15\",\"timeCreate\":\"2024-09-15T01:32:46.406105\",\"state\":\"NEW\",\"timeUpdate\":\"2024-09-15T01:32:46.406105\"}";
//
//        String mess = sendPut(requestBody);
//        MatcherAssert.assertThat(mess, equalTo("Поле \"Название задачи\" обязательно для заполнения."));
//    }
//
//    @Test
//    void sendWhitespaceName () {
//        String requestBody = "{\"id\":2,\"email\":\"Ivan@mail.ru\",\"name\":\"  \",\"description\":\"Мыть полы\",\"priority\":\"Низкий\",\"emailExecutor\":\"Ivan@mail.ru\",\"date\":\"2024-09-15\",\"timeCreate\":\"2024-09-15T01:32:46.406105\",\"state\":\"NEW\",\"timeUpdate\":\"2024-09-15T01:32:46.406105\"}";
//
//        String mess = sendPut(requestBody);
//        MatcherAssert.assertThat(mess, equalTo("Введено недопустимое значение поля \"Тема задачи\"."));
//    }
//
//
//    @Test
//    void sendDateTooOld() {
//        String requestBody = "{\"id\":2,\"email\":\"Ivan@mail.ru\",\"name\":\"Помыть полы\",\"description\":\"Мыть полы\",\"priority\":\"Низкий\",\"emailExecutor\":\"Ivan@mail.ru\",\"date\":\"2024-09-15\",\"timeCreate\":\"2024-09-15T01:32:46.406105\",\"state\":\"NEW\",\"timeUpdate\":\"2024-09-15T01:32:46.406105\"}";
//
//        String mess = sendPut(requestBody);
//        MatcherAssert.assertThat(mess, equalTo("Срок исполнения задачи не должен быть меньше текущей даты."));
//    }
//
//    @Test
//    void sendEmptyDescription() {
//        String requestBody = "{\"id\":2,\"email\":\"Ivan@mail.ru\",\"name\":\"Помыть полы\",\"description\":\"\",\"priority\":\"Низкий\",\"emailExecutor\":\"Ivan@mail.ru\",\"date\":\"2025-09-15\",\"timeCreate\":\"2024-09-15T01:32:46.406105\",\"state\":\"NEW\",\"timeUpdate\":\"2024-09-15T01:32:46.406105\"}";
//
//        String mess = sendPut(requestBody);
//        MatcherAssert.assertThat(mess, equalTo("Поле \"Комментарий\" обязательно для заполнения."));
//    }
//
//    @Test
//    void sendWhitespaceDescription() {
//        String requestBody = "{\"id\":2,\"email\":\"Ivan@mail.ru\",\"name\":\"Помыть полы\",\"description\":\"   \",\"priority\":\"Низкий\",\"emailExecutor\":\"Ivan@mail.ru\",\"date\":\"2025-09-15\",\"timeCreate\":\"2024-09-15T01:32:46.406105\",\"state\":\"NEW\",\"timeUpdate\":\"2024-09-15T01:32:46.406105\"}";
//
//        String mess = sendPut(requestBody);
//        MatcherAssert.assertThat(mess, equalTo("Введено недопустимое значение поля \"Описание задачи\""));
//    }
//
//    @Test
//    void sendEmptyEmailExecutor() {
//        String requestBody = "{\"id\":2,\"email\":\"Ivan@mail.ru\",\"name\":\"Помыть полы\",\"description\":\"Мыть полы\",\"priority\":\"Низкий\",\"emailExecutor\":\"\",\"date\":\"2025-09-15\",\"timeCreate\":\"2024-09-15T01:32:46.406105\",\"state\":\"NEW\",\"timeUpdate\":\"2024-09-15T01:32:46.406105\"}";
//
//        String mess = sendPut(requestBody);
//        MatcherAssert.assertThat(mess, equalTo("Поле \"Исполнитель задачи\" обязательно для заполнения."));
//    }
//
//    @Test
//    void noTaskExceptionExecutor() {
//        String requestBody = "{\"id\":2,\"email\":\"Ivan@mail.ru\",\"name\":\"Помыть полы\",\"description\":\"Мыть полы\",\"priority\":\"Низкий\",\"emailExecutor\":\"Ivan@mail.ru\",\"date\":\"2025-09-15\",\"timeCreate\":\"2024-09-15T01:32:46.406105\",\"state\":\"NEW\",\"timeUpdate\":\"2024-09-15T01:32:46.406105\"}";
//
//        String mess = given().
//                contentType("application/json").
//                body(requestBody).
//                when().
//                put("/tasks/2")
//                .then()
//                .statusCode(400)
//                .extract()
//                .asString();
//        MatcherAssert.assertThat(mess, equalTo("Нет такой задачи."));
//    }
//
}

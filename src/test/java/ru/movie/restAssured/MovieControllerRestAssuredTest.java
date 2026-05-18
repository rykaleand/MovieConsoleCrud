package ru.movie.restAssured;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.movie.entity.Director;
import ru.movie.entity.Movie;
import ru.movie.repository.DirectorRepository;
import ru.movie.repository.MovieRepository;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovieControllerRestAssuredTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @AfterEach
    void cleanUp() {
        movieRepository.deleteAll();
        directorRepository.deleteAll();
    }

    private String sessionCookie;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        // Логинимся и получаем сессию
        sessionCookie = given()
                .contentType(ContentType.URLENC)
                .formParam("username", "admin")
                .formParam("password", "123456")
                .when()
                .post("/login")
                .then()
                .extract()
                .cookie("JSESSIONID");
    }

    // ===== GET /api/movies/{id} =====

    @Test
    void getById_shouldReturn200_whenMovieExists() {
        Director director = directorRepository.save(Director.builder()
                .firstName("Christopher")
                .lastName("Nolan")
                .birthYear(1970)
                .build());

        Movie movie = movieRepository.save(Movie.builder()
                .title("Inception")
                .releaseYear(2010)
                .duration(148)
                .director(director)
                .build());

        given()
                .cookie("JSESSIONID", sessionCookie)
                .when()
                .get("/api/movies/{id}", movie.getId())
                .then()
                .statusCode(200)
                .body("title", equalTo("Inception"))
                .body("releaseYear", equalTo(2010))
                .body("duration", equalTo(148));
    }

    @Test
    void getById_shouldReturn404_whenMovieNotExists() {
        given()
                .cookie("JSESSIONID", sessionCookie)
                .when()
                .get("/api/movies/{id}", 999999L)
                .then()
                .statusCode(404);
    }

    // ===== GET /api/movies/by-year-and-duration =====

    @Test
    void findByReleaseYearAndDuration_shouldReturn200_withResults() {
        Director director = directorRepository.save(Director.builder()
                .firstName("Christopher")
                .lastName("Nolan")
                .birthYear(1970)
                .build());

        movieRepository.save(Movie.builder()
                .title("Inception")
                .releaseYear(2010)
                .duration(148)
                .director(director)
                .build());

        given()
                .cookie("JSESSIONID", sessionCookie)
                .queryParam("releaseYear", 2010)
                .queryParam("durationMin", 100)
                .queryParam("durationMax", 200)
                .when()
                .get("/api/movies/by-year-and-duration")
                .then()
                .statusCode(200)
                .body("$", not(empty()));
    }

    @Test
    void findByReleaseYearAndDuration_shouldReturn200_withEmptyList() {
        given()
                .cookie("JSESSIONID", sessionCookie)
                .queryParam("releaseYear", 1900)
                .queryParam("durationMin", 0)
                .queryParam("durationMax", 1)
                .when()
                .get("/api/movies/by-year-and-duration")
                .then()
                .statusCode(200)
                .body("$", empty());
    }

    // ===== GET /api/movies/by-director =====

    @Test
    void findByDirectorLastName_shouldReturn200_withResults() {
        Director director = directorRepository.save(Director.builder()
                .firstName("Christopher")
                .lastName("Nolan")
                .birthYear(1970)
                .build());

        movieRepository.save(Movie.builder()
                .title("Interstellar")
                .releaseYear(2014)
                .duration(169)
                .director(director)
                .build());

        given()
                .cookie("JSESSIONID", sessionCookie)
                .queryParam("lastName", "Nolan")
                .when()
                .get("/api/movies/by-director")
                .then()
                .statusCode(200)
                .body("$", not(empty()));
    }

    @Test
    void findByDirectorLastName_shouldReturn200_withEmptyList() {
        given()
                .cookie("JSESSIONID", sessionCookie)
                .queryParam("lastName", "UnknownDirector")
                .when()
                .get("/api/movies/by-director")
                .then()
                .statusCode(200)
                .body("$", empty());
    }
}

package hassan.servicebasic;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import sserviceproject.servicebasic.ServicebasicApplication;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(classes = ServicebasicApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientApiIntegrationTest {

    @LocalServerPort
    private int localServerPort;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    public void setup() {
        RestAssured.port = localServerPort;
    }

    @Test
    public void testCreateClientAndVerifyInDB() throws Exception {
        String clientJson = """
            {
                "name": "TestName",
                "surname": "TestSurname"
            }
            """;

        int clientId = given()
                .contentType(ContentType.JSON)
                .body(clientJson)
                .when()
                .post("/clients")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("name", equalTo("TestName"))
                .extract()
                .path("id");

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT name, surname FROM client WHERE id = ?")) {
            ps.setInt(1, clientId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    assert rs.getString("name").equals("TestName");
                    assert rs.getString("surname").equals("TestSurname");
                } else {
                    throw new AssertionError("Cliente no encontrado en la base de datos.");
                }
            }
        }
    }
}

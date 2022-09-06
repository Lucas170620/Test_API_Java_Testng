package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.contains;

public class Pet {
    String uri = "https://petstore.swagger.io/v2/pet";



    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }
    //create == post
    @Test(priority = 1)
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("db/pet1.json");
        // Sintaxe Gherkin
        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name",is("Atena"))
                .body("status",is("available"))
                .body("category.name",is("AX234LORT"))
                .body("tags.name",contains("sta"))

        ;


    }
    @Test(priority = 2)
    public void consultarPet(){
        String petId = "2000061799";
        String token =
        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri+"/"+petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("name",is("Atena"))
                .body("category.name",is("AX234LORT"))
                .body("status",is("available"))
                .extract()
                    .path("category.name")

        ;
    }
    @Test(priority = 3)
    public void atualizarPet() throws IOException {
        String jsonBody = lerJson("db/pet2.json");
        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .put(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name",is("Atena"))
                .body("status",is("sold"))
        ;
    }
    @Test(priority = 4)
    public void deletarPet(){
        String petId = "2000061799";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete(uri+"/"+petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("code",is(200))
                .body("type",is("unknown"))
                .body("message",is(petId))
        ;
    }

}

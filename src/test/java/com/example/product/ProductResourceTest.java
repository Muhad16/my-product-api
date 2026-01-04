package com.example.product;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.graph.ElementOrder.sorted;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.wildfly.common.Assert.assertTrue;

@QuarkusTest
public class ProductResourceTest {

    @Test
    void testCreateAndGet() {

        given()
                .contentType("application/json")
                .body("""
            {
              "name":"Laptop",
              "description":"Gaming Laptop",
              "price":75000,
              "quantity":5
            }
        """)
                .when()
                .post("/products")
                .then()
                .statusCode(200)
                .body("data.id", notNullValue())
                .body("message", equalTo("Product created successfully"));

        given()
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .body("data.size()", greaterThan(0));
    }


    @Test
    void testStockCheck() {
        given()
                .when().get("/products/11/stock?count=2")
                .then()
                .statusCode(200);
    }


    @Test
    void testGetSortedByPrice() {

        List<Float> prices =
                given()
                        .when().get("/products/sorted/price")
                        .then()
                        .statusCode(200)
                        .extract()
                        .jsonPath()
                        .getList("price", Float.class);

        assertTrue(prices.get(0) <= prices.get(1));
    }


    @Test
    void testGetById() {

        Number idNumber = given()
                .contentType("application/json")
                .body("{\"name\":\"Phone\",\"description\":\"Smart\",\"price\":20000,\"quantity\":2}")
                .when().post("/products")
                .then()
                .extract().path("data.id");

        Long id = idNumber.longValue(); // ✅ safely convert

        given()
                .when().get("/products/" + id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id.intValue())); // JSON parser still sees it as int
    }


    @Test
    void testUpdateProduct() {

        // Assume product ID is 1 (or fetch dynamically)
        Long id = 11L;

        // Update product
        given()
                .contentType("application/json")
                .body("{\"name\":\"Updated Laptop\",\"description\":\"High-end Laptop\",\"price\":25,\"quantity\":25}")
                .when().put("/products/" + id)
                .then()
                .statusCode(200)
                .body("data.id", equalTo(id.intValue()))
                .body("data.name", equalTo("Updated Laptop"))
                ;
    }


    @Test
    void testDelete() {
        given()
                .when().delete("/products/11")
                .then()
                .statusCode(200);
    }






}

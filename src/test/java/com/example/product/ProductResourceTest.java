package com.example.product;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;

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
                .when().post("/products")
                .then()
                .statusCode(200)
                .body("id", notNullValue());

        given()
                .when().get("/products")
                .then()
                .statusCode(200)
                .body("$.size()", greaterThan(0));
    }

    @Test
    void testStockCheck() {
        given()
                .when().get("/products/1/stock?count=2")
                .then()
                .statusCode(200);
    }

    @Test
    void testDelete() {
        given()
                .when().delete("/products/1")
                .then()
                .statusCode(200);
    }
}

package com.wiley;

import static io.restassured.RestAssured.given;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;


abstract class AbstractRestITTest {

  static RequestSpecification getSpecification() {
    return new RequestSpecBuilder()
        .setContentType(ContentType.JSON)
        .setAccept(ContentType.JSON)
        .setBaseUri("https://www.wiley.com")
        .setBasePath("/en-us/search/autocomplete/comp_00001H9J")
        .build();
  }

  static RequestSpecification postSpecification() {
    return new RequestSpecBuilder()
        .setContentType(ContentType.JSON)
        .setAccept(ContentType.JSON)
        .setBaseUri("https://httpbin.org")
        .setBasePath("/delay/")
        .build();
  }

  protected int postDelay() {
    return given()
        .spec(postSpecification())
        .post("2")
        .then()
        .extract()
        .statusCode();
  }
}

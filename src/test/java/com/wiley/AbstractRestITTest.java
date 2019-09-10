package com.wiley;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;
import static org.hamcrest.core.IsEqual.equalTo;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import java.util.Collection;
import java.util.List;

public class AbstractRestITTest {

  RequestSpecification getSpecification() {
    return new RequestSpecBuilder()
        .setContentType(ContentType.JSON)
        .setAccept(ContentType.JSON)
        .setBaseUri("https://www.wiley.com/en-us/search/autocomplete/comp_00001H9J?term=Java")
        .build();
  }

  void get() {
    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .get("https://www.wiley.com/en-us/search/autocomplete/comp_00001H9J?term=Java")
        .then()
        .assertThat()
        .statusCode(200)
        .and()
        .body("suggestions.temp", equalTo("<span class=\"search-highlight\">java</span>script"));
  }
}

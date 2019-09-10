package com.wiley;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RestIT extends AbstractRestITTest {

  @DisplayName("Rest GET collection")
  @Test
  void getTerm() {
    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .get("https://www.wiley.com/en-us/search/autocomplete/comp_00001H9J?term=Java")
        .then()
        .assertThat()
        .statusCode(200)
        .and()
        .assertThat()
        .body("suggestions.term[0]", equalTo("<span class=\"search-highlight\">java</span>script"))
        .body("suggestions.term[1]", equalTo("<span class=\"search-highlight\">java</span>beans"))
        .body("suggestions.term[2]", equalTo("<span class=\"search-highlight\">java</span>dpour"))
        .body("suggestions.term[3]", equalTo("<span class=\"search-highlight\">java</span>scripts"));
  }

  @DisplayName("Rest GET collection")
  @Test
  void getName() {
    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .get("https://www.wiley.com/en-us/search/autocomplete/comp_00001H9J?term=Java")
        .then()
        .assertThat()
        .statusCode(200)
        .and()
        .assertThat()
        .body("products.variantOptions.name[0]", equalTo("<span class=\"search-highlight\">java</span>"))
        .body("suggestions.term[1]", equalTo("<span class=\"search-highlight\">java</span>beans"))
        .body("suggestions.term[2]", equalTo("<span class=\"search-highlight\">java</span>dpour"))
        .body("suggestions.term[3]", equalTo("<span class=\"search-highlight\">java</span>scripts"));
  }
}

package com.wiley;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.matchesPattern;

import com.fasterxml.jackson.databind.JsonNode;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import javax.imageio.ImageIO;

class RestIT extends AbstractRestITTest {

  private static final String spanRegExStarting = "<span class=\"search-highlight\">java</span>.*";
  private static final String spanRegExIncludes = ".*<span class='search-highlight'>Java</span>.*";
  private static final String nameWiley = ".*Wiley.*";

  @Test
  void getTest() throws IOException {
    JsonNode node = given()
        .spec(getSpecification())
        .queryParam("term", "Java")
        .get()
        .then()
        .assertThat()
        .statusCode(200)
        .and()
        .assertThat()
        .body("suggestions[0].term", matchesPattern(spanRegExStarting))
        .body("suggestions[1].term", matchesPattern(spanRegExStarting))
        .body("suggestions[2].term", matchesPattern(spanRegExStarting))
        .body("suggestions[3].term", matchesPattern(spanRegExStarting))
        .body("products[0].name", matchesPattern(spanRegExIncludes))
        .body("products[1].name", matchesPattern(spanRegExIncludes))
        .body("products[2].name", matchesPattern(spanRegExIncludes))
        .body("products[3].name", matchesPattern(spanRegExIncludes))
        .body("pages[0].title", matchesPattern(nameWiley))
        .body("pages[1].title", matchesPattern(nameWiley))
        .body("pages[2].title", matchesPattern(nameWiley))
        .body("pages[3].title", matchesPattern(nameWiley))
        .extract().body().as(JsonNode.class);
    JsonNode images = node.findValues("images").get(0);
    byte[] arr = given().when().get(images.get(0).get("url").asText()).asByteArray();
    BufferedImage image = ImageIO.read(new ByteArrayInputStream(arr));
    Assert.assertEquals(image.getWidth(), 300);
  }

  @Test
  public void maxDelayed() {
    await()
        .atMost(10, TimeUnit.SECONDS)
        .until(() -> this.postDelay() == 200);
  }
}

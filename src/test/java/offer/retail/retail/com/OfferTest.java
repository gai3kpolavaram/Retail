package offer.retail.retail.com;

import com.jayway.restassured.response.Response;
import offer.retail.model.Offer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static com.jayway.restassured.RestAssured.*;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)   // 1
@WebAppConfiguration   // 3
public class OfferTest {

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testNoContentTypeReturns415() {

        when().
                post("/merchant/111/offer").
                then().
                statusCode(415);

    }

    @Test
    public void testNoBodyReturns400() {

    given().contentType("application/json").
        when().
                post("/merchant/111/offer").
                then().
                statusCode(400);

    }

    @Test
    public void testInvalidCurrenyReturns400() {

        given().contentType("application/json").
                when().
                body("{\n" +
                        "  \"name\": \"abc\",\n" +
                        "  \"description\": \"cde\",\n" +
                        "  \"currency\": \"GBP000\",\n" +
                        "  \"offerPrice\": 67.00\n" +
                        "}").
                post("/merchant/111/offer").
                then().
                statusCode(400);

    }

    @Test
    public void testCreatesNewResource201() {
        String path = "/merchant/222/offer";

        Response r = given().contentType("application/json").
                when().
                body("{\n" +
                        "  \"name\": \"abc\",\n" +
                        "  \"description\": \"cde\",\n" +
                        "  \"currency\": \"GBP\",\n" +
                        "  \"offerPrice\": 67.00\n" +
                        "}").
                post(path).
                then().
                statusCode(201).
                extract().response();

        assertTrue(r.getHeader("Location").contains(path));

    }

    @Test
    public void testGetNonExistantReturns404() {

        given().contentType("application/json").
                when().
                get("/merchant/000/offer/1").
                then().
                statusCode(404).
                extract().response();

    }

    @Test
    public void testGetReturns200() {

        String path = "/merchant/333/offer";

        Response postResponse = given().contentType("application/json").
                when().
                body("{\n" +
                        "  \"name\": \"abc\",\n" +
                        "  \"description\": \"cde\",\n" +
                        "  \"currency\": \"GBP\",\n" +
                        "  \"offerPrice\": 67.00\n" +
                        "}").
                post(path);

        Response getResponse = given().contentType("application/json").
                when().
                get(postResponse.getHeader("Location")).
                then().
                statusCode(200).
                extract().response();

        try {
            Offer offer = mapper.readValue(getResponse.body().asString(), Offer.class);
            assertEquals("abc", offer.getName());
            assertEquals("cde", offer.getDescription());
            assertEquals("GBP", offer.getCurrency().getCurrencyCode());
            assertEquals(0,offer.getOfferPrice().compareTo(new BigDecimal("67")));
            System.out.println(offer.getOfferPrice());
        } catch(Exception e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    public void testPutReturns200andUpdatesRecords() {

        String path = "/merchant/444/offer";

        Response postResponse = given().contentType("application/json").
                when().
                body("{\n" +
                        "  \"name\": \"abc\",\n" +
                        "  \"description\": \"cde\",\n" +
                        "  \"currency\": \"GBP\",\n" +
                        "  \"offerPrice\": 67.00\n" +
                        "}").
                post(path);

        Response putResponse = given().contentType("application/json").
                when().
                body("{\n" +
                        "  \"name\": \"abc1\",\n" +
                        "  \"description\": \"cde1\",\n" +
                        "  \"currency\": \"GBP\",\n" +
                        "  \"offerPrice\": 900.00\n" +
                        "}").
                put(postResponse.getHeader("Location")).then().statusCode(200).extract().response();


        Response getResponse = given().contentType("application/json").
                when().
                get(postResponse.getHeader("Location"));

        try {
            Offer offer = mapper.readValue(getResponse.body().asString(), Offer.class);
            assertEquals("abc1", offer.getName());
            assertEquals("cde1", offer.getDescription());
            assertEquals("GBP", offer.getCurrency().getCurrencyCode());
            assertEquals(0,offer.getOfferPrice().compareTo(new BigDecimal("900")));
            System.out.println(offer.getOfferPrice());
        } catch(Exception e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    public void testBadMerchantIDReturns400() {
        String path = "/merchant/2g2/offer";

        Response r = given().contentType("application/json").
                when().
                body("{\n" +
                        "  \"name\": \"abc\",\n" +
                        "  \"description\": \"cde\",\n" +
                        "  \"currency\": \"GBP\",\n" +
                        "  \"offerPrice\": 67.00\n" +
                        "}").
                post(path).
                then().
                statusCode(400).
                extract().response();

    }

}

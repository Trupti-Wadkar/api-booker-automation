package testscripts;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import constants.Status_Code;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.datafaker.Faker;
import pojo.request.createbooking.BookingDates;
import pojo.request.createbooking.CreateBookingRequest;

public class CreateBookingJsonObjectTest {
	@Test
	public void createBookingTest() {
		RestAssured.baseURI ="https://restful-booker.herokuapp.com";
		Response res=RestAssured.given()
				.headers("Content-Type","application/json")
				.headers("Accept","application/json")
				.body("{\r\n"
						+ "    \"firstname\" : \"Trupti\",\r\n"
						+ "    \"lastname\" : \"Manglekar\",\r\n"
						+ "    \"totalprice\" : 112,\r\n"
						+ "    \"depositpaid\" : true,\r\n"
						+ "    \"bookingdates\" : {\r\n"
						+ "        \"checkin\" : \"2023-01-01\",\r\n"
						+ "        \"checkout\" : \"2023-01-02\"\r\n"
						+ "    },\r\n"
						+ "    \"additionalneeds\" : \"Breakfast\"\r\n"
						+ "}")
				.when()
				.post("/booking");
		System.out.println(res.getStatusCode());
		System.out.println(res.getStatusLine());
		Assert.assertEquals(res.getStatusCode(),Status_Code.OK);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void createbBookingTestWithJsonObject() {
		Faker faker=new Faker();

		JSONObject jsonBookingDate =new JSONObject();
		jsonBookingDate.put("checkin","2023-01-01");
		jsonBookingDate.put("checkout","2023-01-02");

		JSONObject jsonObject= new JSONObject();
		jsonObject.put("firstname",faker.name().firstName());
		jsonObject.put("lastname",faker.name().lastName());
		jsonObject.put("totalprice",faker.number().positive());
		jsonObject.put("depositpaid",faker.bool().bool());
		jsonObject.put("additionalneeds","Breakfast");
		jsonObject.put("bookingdates",jsonBookingDate);

		Response res = RestAssured.given()
				.headers("Content-Type","application/json")
				.headers("Accept","application/json")
				.body(jsonObject)
				.log().all()
				.when()
				.post("/booking");

		//bookingId = res.jsonPath().getInt("bookingid");

		Assert.assertEquals(res.statusCode(),Status_Code.OK);
		//Assert.assertTrue(Integer.valueOf(res.jsonPath().getInt("bookingid")) instanceof Integer);
		//System.out.println(bookingId);
		//Assert.assertTrue(bookingId>0);
		//validateResponse(res, payload,"booking.");

	}
}

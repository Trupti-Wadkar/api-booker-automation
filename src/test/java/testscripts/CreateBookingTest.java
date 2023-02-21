package testscripts;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import constants.Status_Code;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.request.createbooking.BookingDates;
import pojo.request.createbooking.CreateBookingRequest;

public class CreateBookingTest {
	String token;
	int bookingId;
	CreateBookingRequest payload;
	@ BeforeMethod
	public void generateToken() {
		RestAssured.baseURI ="https://restful-booker.herokuapp.com";

		Response res=RestAssured.given()
				.log().all()
				.headers("Content-Type","application/json")
				.body("{\r\n"
						+ "    \"username\" : \"admin\",\r\n"
						+ "    \"password\" : \"password123\"\r\n"
						+ "}")
				.when()
				.post("/auth");
		//.then().assertThat().statusCode(200)
		//.extract()
		// .response()
		;

		//System.out.println(res.statusCode());
		Assert.assertEquals(res.statusCode(), 200);
		//System.out.println(res.asPrettyString());
		token=res.jsonPath().getString("token");
		System.out.println(token);
	}
	@Test(enabled=false)
	public void createBookingTest() {
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
	
	@Test
	public void createbBookingTestWithPojo() {

		BookingDates bookingdates =new BookingDates();
		bookingdates.setCheckin("2023-01-01");
		bookingdates.setCheckout("2023-01-02");

		payload= new CreateBookingRequest();
		payload.setFirstname("Trupti");
		payload.setLastname("Manglekar");
		payload.setTotalprice(213);
		payload.setDepositpaid(true);
		payload.setAdditionalneeds("Breakfast");
		payload.setBookingdates(bookingdates);

		Response res = RestAssured.given()
				.headers("Content-Type","application/json")
				.headers("Accept","application/json")
				.body(payload)
				.log().all()
				.when()
				.post("/booking");

		bookingId = res.jsonPath().getInt("bookingid");

		Assert.assertEquals(res.statusCode(),Status_Code.OK);
		Assert.assertTrue(Integer.valueOf(res.jsonPath().getInt("bookingid")) instanceof Integer);
		System.out.println(bookingId);
		Assert.assertTrue(bookingId>0);
		validateResponse(res, payload,"booking.");

	}
	@Test(priority=1)
	public void getAllBookingTest() {
		Response res=RestAssured.given()
				.headers("Accept","application/json")
				.log().all() 
				.when()
				.get("/booking");

		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		System.out.println(res.asPrettyString());
		List<Integer> listOgBookingIds =res.jsonPath().getList("bookingid");
		System.out.println(listOgBookingIds.size());
		Assert.assertTrue(listOgBookingIds.contains(bookingId));
	}

	@Test(priority=2,enabled=false)
	public void getBookingIdTest() {
		Response res=RestAssured.given()
				.headers("Accept","application/json")
				.when()
				.get("/booking/"+bookingId);

		Assert.assertEquals(res.statusCode(), Status_Code.OK);
		System.out.println(res.asPrettyString());
		validateResponse(res,payload,"");
	}
	@Test(priority=2)
	public void getBookingIdDeserialization() {
		Response res=RestAssured.given()
				.headers("Accept","application/json")
				.when()
				.get("/booking/"+bookingId);

		Assert.assertEquals(res.statusCode(), Status_Code.OK);
		System.out.println(res.asPrettyString());
		
		CreateBookingRequest responseBody= res.as(CreateBookingRequest.class);
		Assert.assertTrue(responseBody.equals(payload));
	}
	
	@Test(priority=3)
	public void updateBookingIdTest() {
		payload.setFirstname("Aniket");
		Response res=RestAssured.given()
				.headers("Content-Type","application/json")
				.headers("Accept","application/json")
				.headers("Cookie", "token="+token)
				.log().all()
				.body(payload)
				.when()
				.put("/booking/"+bookingId);

		Assert.assertEquals(res.statusCode(), Status_Code.OK);
		System.out.println(res.asPrettyString());
		
		CreateBookingRequest responseBody= res.as(CreateBookingRequest.class);
		Assert.assertTrue(responseBody.equals(payload));
	}

	private void validateResponse(Response res,CreateBookingRequest payload,String object) {
		Assert.assertEquals(res.jsonPath().getString(object+"firstname"),payload.getFirstname());
		Assert.assertEquals(res.jsonPath().getString(object+"lastname"),payload.getLastname());
		Assert.assertEquals(res.jsonPath().getInt(object+"totalprice"),payload.getTotalprice());
		Assert.assertEquals(res.jsonPath().getBoolean(object+"depositpaid"),payload.isDepositpaid());
		Assert.assertEquals(res.jsonPath().getString(object+"bookingdates.checkin"),payload.getBookingdates().getCheckin());
		Assert.assertEquals(res.jsonPath().getString(object+"bookingdates.checkout"),payload.getBookingdates().getCheckout());
	}

	@Test(enabled=false)
	public void createBookingTestInPlanMode() {
		String payload="{\r\n"
				+ "    \"username\" : \"admin\",\r\n"
				+ "    \"password\" : \"password123\"\r\n"
				+ "}";
		RequestSpecification reqspec= RestAssured.given();
		reqspec.baseUri("https://restful-booker.herokuapp.com");
		reqspec.headers("Content-Type","application/json");
		reqspec.body(payload);
		Response res=reqspec.post("/auth");
		Assert.assertEquals(res.statusCode(), 200);
		System.out.println(res.asPrettyString());
	}

}

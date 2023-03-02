package testscripts;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseService;
import constants.Status_Code;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.datafaker.Faker;
import utilities.DataGenerator;

import static io.restassured.RestAssured.given;

import java.util.Map;

public class FMCTest {
	String emailId;
	String token;
	Response res;
	BaseService baseservice=new BaseService();
	
	private  void createToken() {
		Map<String,String> headerMap= baseservice.getHeaderWithoutAuth();
		res = baseservice.executeGetAPI("/fmc/token", headerMap);
		token=res.jsonPath().get("accessToken");
		System.out.println(token);
	}
	
	/*private  void createToken1() {
		RestAssured.baseURI="http://Fmc-env.eba-5akrwvvr.us-east-1.elasticbeanstalk.com";
		Response res=RestAssured.given()
				.headers("Accept","application/json")
				.when()
				.get("/fmc/token");
		System.out.println(res.asPrettyString());
		token=res.jsonPath().get("accessToken");
		System.out.println(token);
	}*/

	private String emailSignUp(String emailId ) {
		JSONObject emailSignUpPayload=new JSONObject();
		emailSignUpPayload.put("email_id",emailId);
		
		Map<String,String>headerMap=baseservice.getHeaderHavingAuth(token);
		res=baseservice.executePostAPI("/fmc/email-signup-automation", headerMap, emailSignUpPayload);
		System.out.println(res.asPrettyString());
		Assert.assertEquals(res.statusCode(), Status_Code.CREATED);
		return res.jsonPath().getString("content.otp");
	}
	/*private String emailSignUp(String emailId ) {
		JSONObject emailSignUpPayload=new JSONObject();
		emailSignUpPayload.put("email_id",emailId);

		Response res=given()
				.headers("Content-Type","application/json")
				.headers("Authorization", "Bearer "+ token)
				.log().all()
				.body(emailSignUpPayload)
				.when()
				.post("/fmc/email-signup-automation");
		System.out.println(res.asPrettyString());
		Assert.assertEquals(res.statusCode(), Status_Code.CREATED);
		return res.jsonPath().getString("content.otp");
	}*/
	
	private void verifyOtp(String emailId,String fullName,String phoneNumber,String password,String otp) {
		JSONObject verifyOtpPayload = new JSONObject();
		verifyOtpPayload.put("email_id", emailId);
		verifyOtpPayload.put("full_name", DataGenerator.getFullName());
		verifyOtpPayload.put("phone_number", DataGenerator.getPhoneNumber());
		verifyOtpPayload.put("password", password);
		verifyOtpPayload.put("otp", otp);
		
		Map<String,String>headerMap=baseservice.getHeaderHavingAuth(token);
		res=baseservice.executePutAPI("/fmc/verify-otp", headerMap, verifyOtpPayload);
		Assert.assertEquals(res.statusCode(), Status_Code.OK);
		System.out.println(res.asPrettyString());
	}
	
	/*private void verifyOtp(String emailId,String fullName,String phoneNumber,String password,String otp) {
		JSONObject verifyOtpPayload = new JSONObject();
		verifyOtpPayload.put("email_id", emailId);
		verifyOtpPayload.put("full_name", DataGenerator.getFullName());
		verifyOtpPayload.put("phone_number", DataGenerator.getPhoneNumber());
		verifyOtpPayload.put("password", password);
		verifyOtpPayload.put("otp", otp);

		res=given()
		.headers("Content-Type","application/json")
		.headers("Authorization", "Bearer "+ token)
		.log().all()
		.body(verifyOtpPayload)
		.when()
		.put("/fmc/verify-otp");
		
		Assert.assertEquals(res.statusCode(), Status_Code.OK);
		System.out.println(res.asPrettyString());
	}*/
	
	@Test
	public void signupTest() {
		String emailId=DataGenerator.getEmailId();
		String fullName=DataGenerator.getFullName();
		String phoneNumber=DataGenerator.getPhoneNumber();
		String password="pass@123";
		createToken();
		String otp=emailSignUp(emailId);
		System.out.println(otp);
		verifyOtp(emailId,fullName,phoneNumber,password,otp);
	}
	
}



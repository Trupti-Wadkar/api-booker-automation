package testscripts;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseService;
import constants.Status_Code;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.datafaker.Faker;
import services.GenerateTokenService;
import utilities.DataGenerator;

import static io.restassured.RestAssured.given;

import java.util.Map;

public class FMCTest3 {

	String token;
	String otp;
	Response res;
	String emailId=DataGenerator.getEmailId();
	String fullName=DataGenerator.getFullName();
	String phoneNumber=DataGenerator.getPhoneNumber();
	String password="pass@123";
	BaseService baseservice=new BaseService();
	GenerateTokenService generatetokenservice =new GenerateTokenService();

	@Test
	public void createToken() {
		Response res=generatetokenservice.getTokenResponse();
		System.out.println(res.asPrettyString());
		token=res.jsonPath().get("accessToken");
		Assert.assertEquals(res.statusCode(),Status_Code.OK);
		Assert.assertTrue(token.length()>0);
		Assert.assertEquals(res.jsonPath().get("tokenType"), "bearer");
		System.out.println(token);
	}

	@Test(priority=1)
	public void emailSignUp() {
		JSONObject emailSignUpPayload=new JSONObject();
		emailSignUpPayload.put("email_id",emailId);
		res=generatetokenservice.getEmailSignUpResponse(emailSignUpPayload);
		otp=res.jsonPath().getString("content.otp");
		Assert.assertEquals(res.statusCode(), Status_Code.CREATED);
	}
	
	@SuppressWarnings("unchecked")
	@Test(priority=2)
	public void verifyOtp() {
		if(otp==null) {
			JSONObject emailSignUpPayload = new JSONObject();
			emailSignUpPayload.put("email_id",emailId);
			otp=generatetokenservice.getOtpFromEmailSignUpResponse(emailSignUpPayload);
		}
		JSONObject verifyOtpPayload = new JSONObject();
		verifyOtpPayload.put("email_id", emailId);
		verifyOtpPayload.put("full_name", DataGenerator.getFullName());
		verifyOtpPayload.put("phone_number", DataGenerator.getPhoneNumber());
		verifyOtpPayload.put("password", password);
		verifyOtpPayload.put("otp", otp);

		res=generatetokenservice.getVerifyOtpResponse(verifyOtpPayload);
		Assert.assertEquals(res.statusCode(), Status_Code.OK);
		int userId=res.jsonPath().getInt("content.userId");
		System.out.println(userId);
		
		userId=generatetokenservice.getUserId(emailId, "pass123");
		System.out.println(userId);
	}
	@Test
	public void verifyOtp1() {
		int userId=generatetokenservice.getUserId(emailId, "pass123");
		System.out.println(userId);
	}

}



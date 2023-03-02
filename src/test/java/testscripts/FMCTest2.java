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

public class FMCTest2 {

	String token;
	String otp;
	Response res;
	String emailId=DataGenerator.getEmailId();
	String fullName=DataGenerator.getFullName();
	String phoneNumber=DataGenerator.getPhoneNumber();
	String password="pass@123";
	BaseService baseservice=new BaseService();

	@Test
	public void createToken() {
		Map<String,String> headerMap= baseservice.getHeaderWithoutAuth();
		res = baseservice.executeGetAPI("/fmc/token", headerMap);
		token=res.jsonPath().get("accessToken");
		Assert.assertEquals(res.statusCode(),Status_Code.OK);
		System.out.println(token);
	}

	@Test(priority=1)
	public void emailSignUp() {
		JSONObject emailSignUpPayload=new JSONObject();
		emailSignUpPayload.put("email_id",emailId);

		Map<String,String>headerMap=baseservice.getHeaderHavingAuth(token);
		res=baseservice.executePostAPI("/fmc/email-signup-automation", headerMap, emailSignUpPayload);

		otp=res.jsonPath().getString("content.otp");
		Assert.assertEquals(res.statusCode(), Status_Code.CREATED);
	}
	@SuppressWarnings("unchecked")
	@Test(priority=2)
	public void verifyOtp() {

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
}



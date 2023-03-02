package services;

import java.util.Map;

import org.json.simple.JSONObject;

import base.BaseService;
import constants.APIEndPoints;
import io.restassured.response.Response;

public class LoginService extends BaseService{
	
	GenerateTokenService generatetokenservice = new GenerateTokenService();
	
	@SuppressWarnings("unchecked")
	public Response login(String emailId,String password) {
		JSONObject loginPayload = new JSONObject();
		
		loginPayload.put("email_id",emailId );
		loginPayload.put("password",password);
		
		return login(loginPayload);
	}

	public Response login(JSONObject loginPayload) {
		String password= loginPayload.get("password").toString();
		if(password.equals("") || password==null) {
			password="temp";
		}
		generatetokenservice.getUserId(loginPayload.get("email_id").toString(), password);
		Map<String,String> headerMap=getHeaderHavingAuth(generatetokenservice.getToken());
		return executePostAPI(APIEndPoints.LOGIN,headerMap,loginPayload);
	}
}

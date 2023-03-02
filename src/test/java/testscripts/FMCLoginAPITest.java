package testscripts;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import constants.Status_Code;
import io.restassured.response.Response;
import services.LoginService;

public class FMCLoginAPITest {
	LoginService loginservice= new LoginService();
	String emailId="truptim3@gmail.com";
	String password="pass@123";
	
	@Test
	public void loginAPITest() {
		Response res= loginservice.login(emailId,password);
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		System.out.println(res.asPrettyString());
	}
	@Test(dataProvider="loginDataDetails")
	public void loginAPITestDataDriven(String emailId, String password,String result) {
		Response res= loginservice.login(emailId,password);
		if(result.equals("success")) {
			Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		}	
		else {
			Assert.assertEquals(res.getStatusCode(), Status_Code.BADREQUEST);
			Assert.assertEquals(res.jsonPath().getString("errors[0].message"),"Password is required");
		}
		System.out.println(res.asPrettyString());
	}
	
	@DataProvider(name="loginDataDetails")
	public String[][] getLoginData(){
		String [][] data =new String [2][3];
		
		data[0][0]="truptim3@gmail.com";
		data[0][1]="pass@123";
		data[0][2]="success";
		
		data[1][0]="truptim3@gmail.com";
		data[1][1]="";
		data[1][2]="fail";
		
		return data;
	}
	
}

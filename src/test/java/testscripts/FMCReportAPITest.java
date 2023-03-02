package testscripts;

import org.testng.Assert;
import org.testng.annotations.Test;
import constants.Status_Code;
import io.restassured.response.Response;
import services.FMC_AddReportService;


public class FMCReportAPITest {
	 @Test
	public void addReport() {
		FMC_AddReportService fmcaddreportservice =new FMC_AddReportService();
		Response res=fmcaddreportservice.addReport();
		System.out.println("content : "+res.jsonPath().getInt("content"));
		Assert.assertEquals(res.statusCode(), Status_Code.OK);
		Assert.assertTrue(res.jsonPath().getInt("content")>0);
		Assert.assertEquals(res.jsonPath().getString("status"), "Success");
}
}

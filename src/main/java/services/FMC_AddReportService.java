package services;

import org.testng.Assert;
import base.BaseService;
import constants.APIEndPoints;
import constants.Status_Code;
import io.restassured.response.Response;
import pojo.request.addreport.Add_Report;
import pojo.request.addreport.ChildDetails;
import pojo.request.addreport.IncidentDetails;
import pojo.request.addreport.ReporterDetails;
import utilities.DataGenerator;

public class FMC_AddReportService {

	LoginService loginservice= new LoginService();
	String emailId=DataGenerator.getEmailId();
	String password="pass@123";
	int userId;
	String requestid=DataGenerator.getRequestId();
	
	public void login() {
		Response res= loginservice.login(emailId,password);
		Assert.assertEquals(res.statusCode(), Status_Code.OK);
		userId=res.jsonPath().getInt("content.userId");
	}
	
	public Response addReport () {
		login();
	
		ReporterDetails reporterdetails =new ReporterDetails();
		reporterdetails.setRequest_id("requestid");
		reporterdetails.setUser_id(userId);
		reporterdetails.setReport_date("2023-02-03T01:37:30Z");
		reporterdetails.setReporter_fullname("Trupti M");
		reporterdetails.setReporter_age(40);
		reporterdetails.setReporter_gender("female");
		reporterdetails.setReporter_relation("mother");
		reporterdetails.setParenting_type("own child");
		reporterdetails.setContact_address_type("Home");
		reporterdetails.setContact_address_line_1("Paud road");
		reporterdetails.setContact_address_line_2("Kothrud");
		reporterdetails.setPincode("411058");
		reporterdetails.setCountry("India");
		reporterdetails.setPrimary_country_code("+91");
		reporterdetails.setPrimary_contact_number("1234567890");
		reporterdetails.setSecondary_country_code("+91");
		reporterdetails.setSecondary_contact_number("2345678901");
		reporterdetails.setCommunication_language("English");
		reporterdetails.setStatus("INCOMPLETE");
		
		ChildDetails childdetails = new ChildDetails();
		childdetails.setFullname("Trupti AM");
		childdetails.setAge(10);
		childdetails.setGender("female");
		childdetails.setHeight("3ft");
		childdetails.setWeight("30kg");
		childdetails.setComplexion("fair");
		childdetails.setClothing("white top and black pant");
		childdetails.setBirth_signs("birth mark on left hand");
		childdetails.setOther_details("wears spectacles");
		childdetails.setImage_file_key(null);
		childdetails.setNickname("Tara");
		
		IncidentDetails incidentdetails = new IncidentDetails();
		incidentdetails.setIncident_date("2023-02-15T10:37:30Z");
		incidentdetails.setIncident_brief("Child went missing near the school");
		incidentdetails.setLocation("Pune");
		incidentdetails.setLandmark_signs("near kasba peth");
		incidentdetails.setNearby_police_station("City police station");
		incidentdetails.setNearby_NGO("Shraddha NGO");
		incidentdetails.setAllow_connect_police_NGO(true);
		incidentdetails.setSelf_verification(true);
		incidentdetails.setCommunity_terms(true);
		
		Add_Report addreport=new Add_Report();
		addreport.setReporter_details(reporterdetails);
		addreport.setChild_details(childdetails);
		addreport.setIncident_details(incidentdetails);
		
		BaseService baseservice = new BaseService();
		GenerateTokenService generatetokenservice = new GenerateTokenService();
		String token =generatetokenservice.getToken();
		
		Response res=baseservice.executePostAPI(APIEndPoints.ADD_REPORT,baseservice.getHeaderHavingAuth(token),addreport);
		
		return res;
		
		
	}

}

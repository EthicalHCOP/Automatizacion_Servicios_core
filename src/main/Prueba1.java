package main;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.HashMap;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Prueba1 {
//  @Test
//  public void f() throws URISyntaxException {
//	  
////	  Response respuesta = given().get("http://restapi.demoqa.com/utilities/weather/city/Texas").thenReturn();
////	  System.out.println(respuesta.asString());
//	  
//	  URI url = new URI("http://restapi.demoqa.com/utilities/weather/city/Texas");
//	  Response resp = given().when().get(url);
//	  
//	  System.out.println(resp.asString());
//	  
//  }
//  @Test
//  public void generateToken() {
//
////      Map<String,String> userDetails = new HashMap<>();
////
////      userDetails.put("msISDN", "1217071016");
////      userDetails.put("messageSource", "TWITTER");
////      userDetails.put("socialId", "168988132");
//
//      Response respuesta = given()
//      .contentType("application/json")
//
//      .queryParam("access_token", "LLRPqxvU1uoT8YSl8")
//
//      .queryParam("pageNo", "1")
//
//      .queryParam("order", "desc")
//
//
//      .post("http://name.com/rest/crm/getdetails")
//
//      .thenReturn();
//      
//      System.out.println(respuesta.asString());
//
//  }
  
  	Map<String,String> map = new HashMap<>();
   	
	@BeforeTest
	public void putdata(){
		
		map.put("userId", "2");
		map.put("id", "19");
		map.put("title", "this is projectdebug.com");
		map.put("body", "i am testing REST api with REST-Assured and sending a PUT request.");	
		RestAssured.baseURI = "http://jsonplaceholder.typicode.com";
		RestAssured.basePath = "/posts/";
	}
	
	@Test
	public void testPUT(){
		Response resp = given()
		.contentType("application/json")
		.body(map)
		.when()
		.put("/100")
		.thenReturn();
		System.out.println(resp.asString());
	}
	
	
	@BeforeTest
	public void setup(){
		
		RestAssured.baseURI = "http://jsonplaceholder.typicode.com";
		RestAssured.basePath = "/posts/";
	}
 
	@Test
	public void testDelete(){
		Response resp = given()
		.when()
		.delete("/1")
		.thenReturn();	
		System.out.println(resp.asString());
		System.out.println(resp.getHeaders().toString());
	}
}

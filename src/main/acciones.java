package main;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.SSLException;

import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.authentication.CertificateAuthSettings;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.authentication.OAuthSignature;
import io.restassured.config.SSLConfig;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class acciones {
	
	public static Response response = null;
	public static String status = "";
	public static RequestSpecification httpRequest =  null;
	public static String Captured_Value_String = "";
	public static ArrayList<String> Captured_Value_Array = new ArrayList<String>();
	public static int itm=1;
	
//	public static void Return_value_on_array(String Atributo) {
//		JsonPath jsonPathEvaluator = response.jsonPath();
//		Captured_Value_String = jsonPathEvaluator.get(Atributo);
//		Captured_Value_Array.add(Captured_Value_Array.size(),Captured_Value_String);
//	}

	
	// ----------------------------------------------------------------------------------------
	// SETUP AND START METHODS
	// ----------------------------------------------------------------------------------------
	
	public static void Setup(String propiedad, String[] valor) {
		switch (propiedad) {
			case "baseURI":
				if(valor[0].toLowerCase().contains("https")) {
					RestAssured.config = RestAssured.config().sslConfig(SSLConfig.sslConfig().allowAllHostnames());
				}
				RestAssured.baseURI = valor[0];
			break;
			
			case "basePath":
				RestAssured.basePath = valor[0];
			break;
			
			case "port":
				RestAssured.port = Integer.parseInt(valor[1]);
			break;
		}
	}
	
	public static void Start_request() {
		httpRequest = RestAssured.given();
	}
	
	// ----------------------------------------------------------------------------------------
	// AUTHENTICATION METHODS
	// ----------------------------------------------------------------------------------------
		
	public static void Authenticate(String method, String[] params, KeyStore[] keys) {
		switch (method) {
			case "basic":
				// usr - pass
				httpRequest.auth().basic(params[0],params[1]);
			break;
			case "certificate":
				// certificate_path - pass - CertificateAuthSettings
				
				CertificateAuthSettings cas = new CertificateAuthSettings();
				cas.keyStoreType("");
				cas.keyStore(keys[0]);
				cas.trustStoreType("");
				cas.trustStore(keys[1]);
				cas.port(Integer.parseInt(params[2]));
					httpRequest.auth().certificate(params[0],params[1], cas);
			break;
			case "digest":
				// usr - pass
				httpRequest.auth().digest(params[0],params[1]);
			break;
			case "form":
				// user, pass, FormAuthConfig.formAuthConfig(id_form, id_usr, id_pass)
				if (params[2].equals("springSecurity")) {
					httpRequest.auth().form(params[0],params[1], FormAuthConfig.springSecurity());
				}else {
					httpRequest.auth().form(params[0],params[1], FormAuthConfig.formAuthConfig());
				}
			break;
			case "ntlm":
				// user, pass,workstation,domain
				httpRequest.auth().ntlm("", "" ,"" ,"");
			break;
			case "oauth":
				//consumerKey, consumerSecret, accessToken, secretToken, OAuthSignature
				if (params[4].equals("HEADER")) {
					httpRequest.auth().oauth("","","","",OAuthSignature.HEADER);
				}else {
					httpRequest.auth().oauth("","","","",OAuthSignature.QUERY_STRING);
				}
				
			break;
			case "oauth2":
				// accessToken, OAuthSignature
				if (params[1].equals("HEADER")) {
					httpRequest.auth().preemptive().oauth2("");
				}else {
					httpRequest.auth().preemptive().oauth2("");
				}
			break;
			default:
				httpRequest.auth().basic(params[0],params[1]);
			break;
		}
		response = httpRequest.get();
	}
	
	// ----------------------------------------------------------------------------------------
	// REQUEST METHODS
	// ----------------------------------------------------------------------------------------
	
	public static void Send_Get_request(String param) {
		response = httpRequest.get(param);
		
	}
	
	public static void Send_Post_request(String uri, String[] param, String Type_load, String[] Header, String[] File) {
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String> Headers = new HashMap<String, String>();
		Map<String, String> Files = new HashMap<String, String>();
		if (param.length % 2 == 0) {
			for (int i = 0; i < param.length; i++) {
				if(i == param.length-1) {
					break;
				}
				if (i % 2 == 0) {
					params.put(param[i], param[i+1]);
				}
			}
			if(Header.length==0) {
				System.out.println("Sin headers");
			}else {
				for (int i = 0; i < Header.length; i++) {
					if(i == Header.length-1) {
						break;
					}
					if (i % 2 == 0) {
						Headers.put(Header[i], Header[i+1]);
					}
				}
				httpRequest.headers(Headers);
			}
			if(File.length==0) {
				System.out.println("Sin file");
			}else {
				for (int i = 0; i < File.length; i++) {
					if(i == File.length-1) {
						break;
					}
					if (i % 2 == 0) {
						Files.put(File[i], File[i+1]);
						httpRequest.multiPart(File[i], new File(File[i+1]));
					}
				}
			}
			switch (Type_load) {
				case "Form":
					httpRequest.formParams(params);
				break;
				
				case "Body":
					httpRequest.body(params);
				break;
				
				case "Query":
					httpRequest.queryParams(params);
				break;
					
				default:
					httpRequest.queryParams(params);
				break;
			}
			//httpRequest.header("Content-Type", "application/json");
			//httpRequest.queryParameters(params);
			//httpRequest.formParameters(params);
			//httpRequest.body(params);
			response = httpRequest.post(uri);
		}else {
			System.out.println("Mal envio de parametros");
		}
	}
	
	public static void Send_Put_request(String uri, String[] param, String Type_load, String[] Header, String[] File) {
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String> Headers = new HashMap<String, String>();
		Map<String, String> Files = new HashMap<String, String>();
		if (param.length % 2 == 0) {
			for (int i = 0; i < param.length; i++) {
				if(i == param.length-1) {
					break;
				}
				if (i % 2 == 0) {
					params.put(param[i], param[i+1]);
				}
			}
			if(Header.length==0) {
				System.out.println("Sin headers");
			}else {
				for (int i = 0; i < Header.length; i++) {
					if(i == Header.length-1) {
						break;
					}
					if (i % 2 == 0) {
						Headers.put(Header[i], Header[i+1]);
					}
				}
				httpRequest.headers(Headers);
			}
			if(File.length==0) {
				System.out.println("Sin file");
			}else {
				for (int i = 0; i < File.length; i++) {
					if(i == File.length-1) {
						break;
					}
					if (i % 2 == 0) {
						Files.put(File[i], File[i+1]);
						httpRequest.multiPart(File[i], new File(File[i+1]));
					}
				}
			}
			switch (Type_load) {
				case "Form":
					httpRequest.formParams(params);
					
				break;
				
				case "Body":
					httpRequest.body(params);
				break;
				
				case "Query":
					httpRequest.queryParams(params);
				break;
					
				default:
					httpRequest.queryParams(params);
				break;
			}
			response = httpRequest.put(uri);
		}else {
			System.out.println("Mal envio de parametros");
		}
	}
	
	public static void Send_Delete_request(String uri) {
		response = httpRequest.delete(uri);
	}
	
	
	// ----------------------------------------------------------------------------------------
	// SHOW METHODS
	// ----------------------------------------------------------------------------------------
	
	public static void Show_response() {
		System.out.println(response.asString());
	}
	
	public static void Show_Status(String Type) {
		try {
			if (Type.equals("Linea")) {
				System.out.println(response.getStatusLine());
			}else {
				System.out.println(response.getStatusCode());
			}
		} catch (Exception e) {
			System.out.println(response.getStatusCode());
		}
	}
	
	public static void Show_Time() {
		System.out.println(response.getTime());
	}
	
	public static void Show_Headers() {
		System.out.println(response.getHeaders().toString());
	}

	public static void Show_Header(String header) {
		System.out.println(response.getHeader(header).toString());
	}
	
	public static void Show_DetailedCookies() {
		try {
			System.out.println("DetailedCookies "+response.getDetailedCookies().toString());
		} catch (Exception e) {
			System.out.println("Sin DetailedCookies");
		}
	}
	
	public static void Show_Cookies() {
		try {
			System.out.println(response.getCookies().toString());
		} catch (Exception e) {
			System.out.println("Sin Cookies");
		}
	}
	
	public static void Show_Cookie(String cookie) {
		try {
			System.out.println(response.getCookie(cookie).toString());
		} catch (Exception e) {
			System.out.println("Sin Cookie");
		}
	}
	
	public static void Show_ContentType() {
		try {
			System.out.println(response.getContentType().toString());
		} catch (Exception e) {
			System.out.println("Sin Show_ContentType");
		}
	}
	
	public static void Show_SessionId() {
		try {
			System.out.println("SessionId "+response.getSessionId().toString());
		} catch (Exception e) {
			System.out.println("Sin SessionId");
		}
	}
	
	public static void Show_Body() {
		try {
			System.out.println("Body asString "+response.getBody().asString());
//			System.out.println("Body prettyPrint "+response.getBody().prettyPrint());
//			System.out.println("Body print "+response.getBody().print());
//			System.out.println("Body toString "+response.getBody().toString());
		} catch (Exception e) {
			System.out.println("Sin Body");
		}
	}
	
	// ----------------------------------------------------------------------------------------
	// VERIFICATION METHODS
	// ----------------------------------------------------------------------------------------
	public static void Verify_Attr(String Atributo, String Valor) {
		JsonPath jsonPathEvaluator = response.jsonPath();
		String tmp_valor = jsonPathEvaluator.get(Atributo);
		if (tmp_valor.equals(Valor)) {
			System.out.println("El valor esperado coincide con el valor actual");
		}else {
			System.out.println("El valor esperado no coincide con el valor actual");
		}
	}
	
	public static void Verify_Status(String Type_status, String Valor) {
		if (Type_status.equals("Codigo")) {
			status = Integer.toString(response.getStatusCode());
		}else if(Type_status.equals("Linea")) {
			status = response.getStatusLine();
		}else {
			status = "null";
		}
		if (status.equals(Valor)) {
			System.out.println("El codigo esperado coincide con el codigo actual");
		}else {
			System.out.println("El codigo esperado no coincide con el codigo actual");
		}
	}
	
	public static void Verify_time(String[] range){
		int t_response = (int) response.getTime();
		if(Integer.parseInt(range[0]) < Integer.parseInt(range[1])) {
			if( (Integer.parseInt(range[0]) < t_response) && (Integer.parseInt(range[1]) > t_response)  ) {
				System.out.println("El tiempo de respuesta esta en el rango propuesto de "+range[0]+" y "+range[1]+" milisegundos.");
			}else {
				System.out.println("El tiempo de respuesta esta fuera del rango propuesto de "+range[0]+" y "+range[1]+" milisegundos.");
			}
		}else if(Integer.parseInt(range[0]) > Integer.parseInt(range[1])) {
			if( (Integer.parseInt(range[0]) > t_response) && (Integer.parseInt(range[1]) < t_response)  ) {
				System.out.println("El tiempo de respuesta esta en el rango propuesto de "+range[0]+" y "+range[1]+" milisegundos.");
			}else {
				System.out.println("El tiempo de respuesta esta fuera del rango propuesto de "+range[0]+" y "+range[1]+" milisegundos.");
			}
		}else if(Integer.parseInt(range[0]) == Integer.parseInt(range[1])) {
			if( (Integer.parseInt(range[0]) > t_response)) {
				System.out.println("El tiempo de respuesta es menor que el tiempo propuesto "+range[0]);
			}else {
				System.out.println("El tiempo de respuesta es mayor que el tiempo propuesto "+range[0]);
			}
		}
			
	}
	
	public static void Verify_Header(String header, String valor) {
		try {
			if (valor.equals(response.getHeader(header).toString())) {
				System.out.println("Header coincide");
			}else {
				System.out.println("Header no coincide");
			}
		} catch (Exception e) {
			System.out.println("Header no valido");
		}
		
	}	
	
	public static void Verify_ContentType(String CValue) {
		try {
			if(CValue.equals(response.getContentType().toString())) {
				System.out.println("ContentType Coincide");
			}else {
				System.out.println("ContentType no Coincide");
			}
		} catch (Exception e) {
			System.out.println("ContentType no valido");
		}		
	}
	
	public static void Verify_Cookie(String cookie, String valor) {
		try {
			if(valor.equals(response.getCookie(cookie).toString())) {
				System.out.println("Cookie Coincide");
			}else {
				System.out.println("Cookie no Coincide");
			}
		} catch (Exception e) {
			System.out.println("Sin Cookie");
		}
	}
	
	public static void Verify_SessionId(String valor) {
		try {
			if(valor.equals(response.getSessionId().toString())) {
				System.out.println("SessionId Coincide");
			}else {
				System.out.println("SessionId no Coincide");
			}
		} catch (Exception e) {
			System.out.println("Sin SessionId");
		}
	}
	
	// ----------------------------------------------------------------------------------------
	// SAVE METHODS
	// ----------------------------------------------------------------------------------------
		
	public static void Save_value(String Atributo) {
		JsonPath jsonPathEvaluator = response.jsonPath();
		Captured_Value_String = jsonPathEvaluator.get(Atributo);
	}
	
	public static void print_returned_value() {
		System.out.println(Captured_Value_String);
	}
	
	public static void Save_response(String ruta, String filename, String ext) throws IOException {
		String path = ruta+"\\"+filename+"."+ext;
        File archivo = new File(path);
        BufferedWriter bw;
        if(archivo.exists()) {
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write(response.asString());
        } else {
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write(response.asString());
        }
        bw.close();
	}
}
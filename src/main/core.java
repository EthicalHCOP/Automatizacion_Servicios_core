package main;

import java.net.URI;
import java.security.KeyStore;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import main.acciones;

public class core {
	
	static acciones acs = new acciones();
	static String [] conf_params1 = {"Https://geodata.solutions/apibeta/api.php?","443"};
	static String [] conf_params2 = {"https://geodata.solutions/restapi","443"};
	static String [] post_arrays = {"type","confCity","countryId","CO","stateId","2","cityId","Medellin"};
//	static String [] files_arrays = {"imagen", "C:\\Users\\Usuario.Usuario-PC\\Pictures\\screenshots\\EriBank\\Ingreso_datos31011883839.jpg"};
	static String [] files_arrays = {};
	static String Put_param = "/100";
	static String Dlt_param = "/1";
	//type=confCity&countryId=CO&stateId=%222%22&cityId=Medellin
	static String id_resource1 = "?type=confCity&countryId=CO&stateId=%222%22&cityId=Medellin";
	static String id_resource2 = "?country=Colombia&state=Antioquia&city=Medellin";
	static String resource = "/api/users";
	static String [] Header = {"Content-Type", "application/json"};
	static String [] range = {"1000","1400"};
	static String Type_load = "Body";
	static String auth_method="basic";
	static String [] Auth_params = {"ToolsQA","TestPassword"};
	static KeyStore [] Auth_keys = {null};

	public static void main(String[] args) {
		try {
/** ------------------------------------- CONF_METHODS ------------------------------------- **/
			acciones.Setup("baseURI",conf_params1); // conf_item, item_val
			acciones.Setup("port", conf_params1);
/** ------------------------------------- START_METHOD ------------------------------------- **/
			acciones.Start_request();
/** -------------------------------------- AUTH_METHOD ------------------------------------- **/
//			acciones.Authenticate(auth_method, Auth_params, Auth_keys);
/** ------------------------------------- SEND_METHODS ------------------------------------- **/	
			acciones.Send_Get_request(id_resource1);  // id_resource_path
//			acciones.Send_Post_request(resource,post_arrays,Type_load, Header, files_arrays); // id_resource_path , array_post_params{ var1, val1, var2, val2 ...}, Body-Query-Form, Header value
			acciones.Show_response();
			
			acciones.Setup("baseURI",conf_params2); // conf_item, item_val
			acciones.Setup("port", conf_params2);
			acciones.Start_request();
			acciones.Send_Get_request(id_resource2);
//			acciones.Send_Get_request(id_resource);
//			acciones.Send_Put_request(Put_param,post_arrays,Type_load, Header, files_arrays);
//			acciones.Send_Delete_request(Dlt_param);
						
/** --------------------------------------SHOW_METHODS ------------------------------------- **/
			acciones.Show_response();
//			acciones.Show_Headers();
//			acciones.Show_ContentType();
//			acciones.Show_Cookies();
//			acciones.Show_DetailedCookies();
//			acciones.Show_SessionId();
//			acciones.Show_Cookie("Path");
//			acciones.Show_Header("Connection");
		
			 // Linea, Codigo
//			acciones.Show_Status("Codigo");
//			acciones.Show_Body();
/** --------------------------------- VERIFICATION_METHODS --------------------------------- **/
//			acciones.Verify_Attr("title", "se actualizo aqui 2");
//			acciones.Verify_Header("Connection", "keep-alive");
//			acciones.Verify_ContentType("application/json; charset=utf-8");
//			acciones.Verify_Status("Codigo", "200");  // Linea - line_http_code  , Codigo - #_code
//			acciones.Verify_Cookie("__cfduid","d7be6621ee8d81b3923b595df2aca19d41533753918");
//			acciones.Verify_SessionId("");
//			acciones.Verify_time(range);  // min - max , max - min , min=max - max=min 
/** ------------------------------------- SAVE_METHODS ------------------------------------- **/
//			acciones.Save_value("title");
//			acciones.print_returned_value();
//			acciones.Save_response("C:\\Users\\Usuario.Usuario-PC\\Desktop\\reportes", "reporte1", "json");
			
		}catch(Exception e) {
			System.out.println(e);
		}
	}

}

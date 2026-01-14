package pe.interseguro.siv.common.persistence.rest.acsele;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import pe.interseguro.siv.common.config.BaseTest;
import pe.interseguro.siv.common.persistence.rest.acsele.response.TerceroResponse;
import pe.interseguro.siv.common.persistence.rest.indenova.response.CrearTokenResponse;

public class AcseleRestClientTest extends BaseTest {
	@Autowired
	private AcseleRestClient acseleRestClient;

	@Autowired
	@Qualifier("restTemplatePesado")
	private RestTemplate restTemplate;

	private MockRestServiceServer mockServer;

	@Before
	public void setup() {
		mockServer = MockRestServiceServer.createServer(restTemplate);
	}
	
	@Test
	public void crearTokenTest() {

		mockServer.expect(requestTo("http://10.29.23.22:7001/ws/usuarios/autenticar.do"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withSuccess("{\"token\": \"mock-token-123\"}", MediaType.APPLICATION_JSON));

		CrearTokenResponse response = acseleRestClient.crearToken();
		System.out.println("response = " + gson.toJson(response));

		mockServer.verify();
	}
	
	@Test
	public void obtenerIDTerceroTest() {

		// Mock token request which is called internally
		mockServer.expect(requestTo("http://10.29.23.22:7001/ws/usuarios/autenticar.do"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withSuccess("{\"token\": \"mock-token-123\"}", MediaType.APPLICATION_JSON));

		// Mock consultation request
		mockServer.expect(requestTo("http://10.29.23.22:7001/ws/terceros/consultarTerceros.do"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withSuccess("{\"Terceros\": [{\"idTercero\": \"999\"}]}", MediaType.APPLICATION_JSON));

		TerceroResponse response = acseleRestClient.obtenerIDTercero("PersonaNatural", "29597880");
		System.out.println("response = " + gson.toJson(response));

		mockServer.verify();
	}
}

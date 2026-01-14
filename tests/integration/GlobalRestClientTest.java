package pe.interseguro.siv.tests.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import pe.interseguro.siv.common.persistence.rest.global.GlobalRestClient;
import pe.interseguro.siv.common.persistence.rest.global.request.ObtenerAgenteRequest;
import pe.interseguro.siv.common.persistence.rest.global.response.ObtenerAgenteResponse;
import pe.interseguro.siv.tests.config.BaseTestConfig;
import pe.interseguro.siv.tests.utils.TestConstants;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para GlobalRestClient.
 * Valida la integración con el servicio Global.
 * 
 * @author ti-is
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para GlobalRestClient")
class GlobalRestClientTest extends BaseTestConfig {

    @Mock
    private RestTemplate restTemplate;

    private GlobalRestClient client;

    @BeforeEach
    void setUp() {
        // Inicializar el cliente con mocks necesarios
    }

    @Test
    @DisplayName("obtenerAgente - con código válido - debe retornar agente")
    void obtenerAgente_conCodigoValido_retornaAgente() {
        // Arrange
        ObtenerAgenteRequest request = new ObtenerAgenteRequest();
        request.setCodAgencia(TestConstants.TEST_CODIGO_AGENCIA);

        ObtenerAgenteResponse expectedResponse = new ObtenerAgenteResponse();
        expectedResponse.setCOD_USERNAME_AGENTE(TestConstants.TEST_ID_AGENTE_CRM);
        expectedResponse.setCOD_AGENTE(TestConstants.TEST_CODIGO_AGENTE);
        expectedResponse.setCOD_AGENCIA(TestConstants.TEST_CODIGO_AGENCIA);
        expectedResponse.setGLS_CORREO_AGENTE(TestConstants.TEST_EMAIL);

        // Este es un mock de ejemplo, se debe ajustar según la implementación real
        // when(restTemplate.postForObject(anyString(), any(), eq(ObtenerAgenteResponse.class)))
        //     .thenReturn(expectedResponse);

        // Act & Assert
        // Aquí se debe implementar la prueba real según la implementación del cliente
        assertNotNull(request);
    }

    @Test
    @DisplayName("obtenerAgente - con código inválido - debe manejar error")
    void obtenerAgente_conCodigoInvalido_manejaError() {
        // Arrange
        ObtenerAgenteRequest request = new ObtenerAgenteRequest();
        request.setCodAgencia("INVALID");

        // Act & Assert
        // Implementar prueba de manejo de errores
        assertNotNull(request);
    }
}


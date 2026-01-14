package pe.interseguro.siv.tests.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.test.util.ReflectionTestUtils;
import pe.interseguro.siv.admin.transactional.service.impl.UsuarioServiceImpl;
import pe.interseguro.siv.common.dto.request.UsuarioIngresoRequestDTO;
import pe.interseguro.siv.common.dto.response.UsuarioIngresoResponseDTO;
import pe.interseguro.siv.common.enums.PerfilEnum;
import pe.interseguro.siv.common.persistence.db.mysql.repository.UsuarioPerfilRepository;
import pe.interseguro.siv.common.persistence.db.mysql.response.Perfil;
import pe.interseguro.siv.common.persistence.rest.crm.CrmRestClient;
import pe.interseguro.siv.common.persistence.rest.global.GlobalRestClient;
import pe.interseguro.siv.common.persistence.rest.global.request.ObtenerAgenteRequest;
import pe.interseguro.siv.common.persistence.rest.global.response.ObtenerAgenteResponse;
import pe.interseguro.siv.common.persistence.rest.interseguro.InterseguroRestClient;
import pe.interseguro.siv.common.persistence.rest.interseguro.request.ObtenerDatosUsuarioRequest;
import pe.interseguro.siv.common.persistence.rest.interseguro.response.ObtenerDatosUsuarioResponse;
import pe.interseguro.siv.common.util.Constantes;
import pe.interseguro.siv.tests.config.BaseTestConfig;
import pe.interseguro.siv.tests.utils.TestConstants;
import pe.interseguro.siv.tests.utils.builders.UsuarioDTOBuilder;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para UsuarioServiceImpl.
 * Valida la lógica de negocio relacionada con usuarios.
 * 
 * @author ti-is
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para UsuarioServiceImpl")
class UsuarioServiceImplTest extends BaseTestConfig {

    @Mock
    private MessageSource messageSource;

    @Mock
    private InterseguroRestClient interseguroRestClient;

    @Mock
    private CrmRestClient crmRestClient;

    @Mock
    private GlobalRestClient globalRestClient;

    @Mock
    private UsuarioPerfilRepository usuarioPerfilRepository;

    @InjectMocks
    private UsuarioServiceImpl service;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "azmanObtenerDatosUsuario_aplicacion", "SEGURIDAD");
        
        when(messageSource.getMessage(anyString(), any(), any(Locale.class)))
            .thenReturn(TestConstants.MENSAJE_EXITO);
    }

    @Test
    @DisplayName("validarUsuario - con credenciales válidas no agente - debe retornar token")
    void validarUsuario_conCredencialesValidasNoAgente_retornaToken() {
        // Arrange
        UsuarioIngresoRequestDTO request = UsuarioDTOBuilder.builder()
            .usuario(TestConstants.TEST_USUARIO)
            .password(TestConstants.TEST_PASSWORD)
            .buildRequest();

        ObtenerDatosUsuarioResponse azmanResponse = new ObtenerDatosUsuarioResponse();
        azmanResponse.setStatusHttp(TestConstants.HTTP_STATUS_200);
        azmanResponse.setRolAzman(TestConstants.TEST_AZMAN_ROL_AGENTE);
        azmanResponse.setNombreCompleto(TestConstants.TEST_NOMBRE_COMPLETO);
        azmanResponse.setCorreo(TestConstants.TEST_EMAIL);

        Perfil perfil = new Perfil();
        perfil.setCodigo(PerfilEnum.PERFIL_OPERACIONES.getCodigo());
        perfil.setNombre(PerfilEnum.PERFIL_OPERACIONES.getPerfil());

        when(interseguroRestClient.obtenerDatosUsuario(any(ObtenerDatosUsuarioRequest.class)))
            .thenReturn(azmanResponse);
        when(usuarioPerfilRepository.findByUsuario(anyString())).thenReturn(perfil);

        // Act
        UsuarioIngresoResponseDTO result = service.validarUsuario(request);

        // Assert
        assertNotNull(result);
        assertEquals(Constantes.CODIGO_RESPUESTA_GENERAL_EXITO, result.getCodigoRespuesta());
        assertNotNull(result.getJwtToken());
        assertEquals(TestConstants.TEST_USUARIO, result.getIdUsuario());
        assertNotNull(result.getPerfil());
        verify(interseguroRestClient, times(1)).obtenerDatosUsuario(any());
    }

    @Test
    @DisplayName("validarUsuario - con credenciales válidas agente - debe incluir datos CRM")
    void validarUsuario_conCredencialesValidasAgente_incluyeDatosCrm() {
        // Arrange
        UsuarioIngresoRequestDTO request = UsuarioDTOBuilder.builder()
            .usuario(TestConstants.TEST_USUARIO_AGENTE)
            .password(TestConstants.TEST_PASSWORD)
            .buildRequest();

        ObtenerDatosUsuarioResponse azmanResponse = new ObtenerDatosUsuarioResponse();
        azmanResponse.setStatusHttp(TestConstants.HTTP_STATUS_200);
        azmanResponse.setRolAzman(TestConstants.TEST_AZMAN_ROL_AGENTE);
        azmanResponse.setNombreCompleto(TestConstants.TEST_NOMBRE_COMPLETO);
        azmanResponse.setCorreo(TestConstants.TEST_EMAIL);

        Perfil perfil = new Perfil();
        perfil.setCodigo(PerfilEnum.PERFIL_AGENTE.getCodigo());

        ObtenerAgenteResponse agenteResponse = new ObtenerAgenteResponse();
        agenteResponse.setCOD_USERNAME_AGENTE(TestConstants.TEST_ID_AGENTE_CRM);
        agenteResponse.setCOD_AGENTE(TestConstants.TEST_CODIGO_AGENTE);
        agenteResponse.setCOD_AGENCIA(TestConstants.TEST_CODIGO_AGENCIA);
        agenteResponse.setGLS_CORREO_AGENTE(TestConstants.TEST_EMAIL);

        when(interseguroRestClient.obtenerDatosUsuario(any())).thenReturn(azmanResponse);
        when(usuarioPerfilRepository.findByUsuario(anyString())).thenReturn(perfil);
        when(globalRestClient.obtenerAgente(any(ObtenerAgenteRequest.class)))
            .thenReturn(agenteResponse);

        // Act
        UsuarioIngresoResponseDTO result = service.validarUsuario(request);

        // Assert
        assertNotNull(result);
        assertEquals(Constantes.CODIGO_RESPUESTA_GENERAL_EXITO, result.getCodigoRespuesta());
        assertNotNull(result.getJwtToken());
        assertTrue(result.getEsAgenteSolicitud());
        assertEquals(TestConstants.TEST_ID_AGENTE_CRM, result.getIdUsuarioCRM());
        assertEquals(TestConstants.TEST_CODIGO_AGENTE, result.getCodigoVendedorCRM());
        verify(globalRestClient, times(1)).obtenerAgente(any());
    }

    @Test
    @DisplayName("validarUsuario - con credenciales inválidas - debe retornar error")
    void validarUsuario_conCredencialesInvalidas_retornaError() {
        // Arrange
        UsuarioIngresoRequestDTO request = UsuarioDTOBuilder.builder()
            .usuario(TestConstants.TEST_USUARIO)
            .password("wrongpassword")
            .buildRequest();

        ObtenerDatosUsuarioResponse azmanResponse = new ObtenerDatosUsuarioResponse();
        azmanResponse.setStatusHttp(TestConstants.HTTP_STATUS_401);
        azmanResponse.setCode("401");
        azmanResponse.setMessage("Credenciales inválidas");

        when(interseguroRestClient.obtenerDatosUsuario(any()))
            .thenReturn(azmanResponse);

        // Act
        UsuarioIngresoResponseDTO result = service.validarUsuario(request);

        // Assert
        assertNotNull(result);
        assertEquals(Constantes.CODIGO_RESPUESTA_GENERAL_ERROR, result.getCodigoRespuesta());
        assertNull(result.getJwtToken());
        verify(interseguroRestClient, times(1)).obtenerDatosUsuario(any());
    }

    @Test
    @DisplayName("getRefreshToken - con claims válidos - debe generar nuevo token")
    void getRefreshToken_conClaimsValidos_generaNuevoToken() {
        // Arrange
        Claims claims = new DefaultClaims();
        claims.setSubject(TestConstants.TEST_USUARIO);

        // Act
        UsuarioIngresoResponseDTO result = service.getRefreshToken(claims);

        // Assert
        assertNotNull(result);
        assertEquals(Constantes.CODIGO_RESPUESTA_GENERAL_EXITO, result.getCodigoRespuesta());
        assertNotNull(result.getJwtToken());
        assertTrue(result.getJwtToken().length() > 0);
    }

    @Test
    @DisplayName("validarUsuarioAzman - con usuario válido - debe validar en Azman")
    void validarUsuarioAzman_conUsuarioValido_validaEnAzman() {
        // Arrange
        UsuarioIngresoRequestDTO request = UsuarioDTOBuilder.builder()
            .usuario(TestConstants.TEST_USUARIO)
            .password(TestConstants.TEST_PASSWORD)
            .buildRequest();

        ObtenerDatosUsuarioResponse azmanResponse = new ObtenerDatosUsuarioResponse();
        azmanResponse.setStatusHttp(TestConstants.HTTP_STATUS_200);
        azmanResponse.setNombreCompleto(TestConstants.TEST_NOMBRE_COMPLETO);

        when(interseguroRestClient.obtenerDatosUsuario(any()))
            .thenReturn(azmanResponse);

        // Act
        UsuarioIngresoResponseDTO result = service.validarUsuarioAzman(request);

        // Assert
        assertNotNull(result);
        assertEquals(Constantes.CODIGO_RESPUESTA_GENERAL_EXITO, result.getCodigoRespuesta());
        verify(interseguroRestClient, times(1)).obtenerDatosUsuario(any());
    }

    @Test
    @DisplayName("validarUsuarioAzman - con error en Azman - debe manejar excepción")
    void validarUsuarioAzman_conErrorEnAzman_manejaExcepcion() {
        // Arrange
        UsuarioIngresoRequestDTO request = UsuarioDTOBuilder.builder().buildRequest();

        when(interseguroRestClient.obtenerDatosUsuario(any()))
            .thenThrow(new RuntimeException("Error de conexión"));

        // Act
        UsuarioIngresoResponseDTO result = service.validarUsuarioAzman(request);

        // Assert
        assertNotNull(result);
        assertEquals(Constantes.CODIGO_RESPUESTA_GENERAL_ERROR, result.getCodigoRespuesta());
        verify(interseguroRestClient, times(1)).obtenerDatosUsuario(any());
    }

    @Test
    @DisplayName("obtenerPerfilUsuario - con usuario no agente - debe retornar perfil")
    void obtenerPerfilUsuario_conUsuarioNoAgente_retornaPerfil() {
        // Arrange
        String usuario = TestConstants.TEST_USUARIO;
        
        Perfil perfil = new Perfil();
        perfil.setCodigo(PerfilEnum.PERFIL_OPERACIONES.getCodigo());
        perfil.setNombre(PerfilEnum.PERFIL_OPERACIONES.getPerfil());

        when(usuarioPerfilRepository.findByUsuario(usuario)).thenReturn(perfil);

        // Act
        UsuarioIngresoResponseDTO result = service.obtenerPerfilUsuario(usuario);

        // Assert
        assertNotNull(result);
        assertEquals(Constantes.CODIGO_RESPUESTA_GENERAL_EXITO, result.getCodigoRespuesta());
        assertNotNull(result.getPerfil());
        verify(usuarioPerfilRepository, times(1)).findByUsuario(usuario);
    }

    @Test
    @DisplayName("obtenerPerfilUsuario - con agente válido - debe obtener datos de Global")
    void obtenerPerfilUsuario_conAgenteValido_obtieneDatosGlobal() {
        // Arrange
        String usuario = TestConstants.TEST_USUARIO_AGENTE;

        ObtenerAgenteResponse agenteResponse = new ObtenerAgenteResponse();
        agenteResponse.setCOD_USERNAME_AGENTE(TestConstants.TEST_ID_AGENTE_CRM);
        agenteResponse.setCOD_AGENTE(TestConstants.TEST_CODIGO_AGENTE);
        agenteResponse.setCOD_AGENCIA(TestConstants.TEST_CODIGO_AGENCIA);
        agenteResponse.setGLS_CORREO_AGENTE(TestConstants.TEST_EMAIL);

        when(usuarioPerfilRepository.findByUsuario(usuario)).thenReturn(null);
        when(globalRestClient.obtenerAgente(any())).thenReturn(agenteResponse);

        // Act
        UsuarioIngresoResponseDTO result = service.obtenerPerfilUsuario(usuario);

        // Assert
        assertNotNull(result);
        assertEquals(Constantes.CODIGO_RESPUESTA_GENERAL_EXITO, result.getCodigoRespuesta());
        assertTrue(result.getEsAgenteSolicitud());
        assertEquals(TestConstants.TEST_ID_AGENTE_CRM, result.getIdUsuarioCRM());
        verify(globalRestClient, times(1)).obtenerAgente(any());
    }

    @Test
    @DisplayName("obtenerPerfilUsuario - con error en Global - debe manejar excepción")
    void obtenerPerfilUsuario_conErrorEnGlobal_manejaExcepcion() {
        // Arrange
        String usuario = TestConstants.TEST_USUARIO_AGENTE;

        when(usuarioPerfilRepository.findByUsuario(usuario)).thenReturn(null);
        when(globalRestClient.obtenerAgente(any()))
            .thenThrow(new RuntimeException("Error de conexión"));

        // Act
        UsuarioIngresoResponseDTO result = service.obtenerPerfilUsuario(usuario);

        // Assert
        assertNotNull(result);
        assertEquals(Constantes.CODIGO_RESPUESTA_GENERAL_ERROR, result.getCodigoRespuesta());
        verify(globalRestClient, times(1)).obtenerAgente(any());
    }
}


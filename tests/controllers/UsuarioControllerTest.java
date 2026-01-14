package pe.interseguro.siv.tests.controllers;

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
import pe.interseguro.siv.admin.transactional.factory.ServiceFactory;
import pe.interseguro.siv.admin.transactional.service.UsuarioService;
import pe.interseguro.siv.admin.view.controller.UsuarioController;
import pe.interseguro.siv.common.dto.request.UsuarioIngresoRequestDTO;
import pe.interseguro.siv.common.dto.response.UsuarioIngresoResponseDTO;
import pe.interseguro.siv.tests.config.BaseTestConfig;
import pe.interseguro.siv.tests.utils.TestConstants;
import pe.interseguro.siv.tests.utils.builders.UsuarioDTOBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para UsuarioController.
 * Valida el comportamiento de los endpoints de usuario.
 * 
 * @author ti-is
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para UsuarioController")
class UsuarioControllerTest extends BaseTestConfig {

    @Mock
    private ServiceFactory serviceFactory;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private MessageSource messageSource;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private UsuarioController controller;

    @BeforeEach
    void setUp() {
        when(serviceFactory.getUsuarioService()).thenReturn(usuarioService);
    }

    @Test
    @DisplayName("getRefreshToken - con claims válidos - debe retornar nuevo token")
    void getRefreshToken_conClaimsValidos_retornaNuevoToken() {
        // Arrange
        Claims claims = new DefaultClaims();
        claims.setSubject(TestConstants.TEST_USUARIO);
        
        when(request.getAttribute("claims")).thenReturn(claims);
        
        UsuarioIngresoResponseDTO expectedResponse = UsuarioDTOBuilder.buildSuccessResponse();
        when(usuarioService.getRefreshToken(any(Claims.class)))
            .thenReturn(expectedResponse);

        // Act
        UsuarioIngresoResponseDTO result = controller.getRefreshToken(request, response);

        // Assert
        assertNotNull(result);
        assertEquals(TestConstants.CODIGO_EXITO, result.getCodigoRespuesta());
        assertNotNull(result.getJwtToken());
        verify(usuarioService, times(1)).getRefreshToken(any(Claims.class));
    }

    @Test
    @DisplayName("getRefreshToken - con claims nulos - debe manejar error")
    void getRefreshToken_conClaimsNulos_manejaError() {
        // Arrange
        when(request.getAttribute("claims")).thenReturn(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            controller.getRefreshToken(request, response);
        });
    }

    @Test
    @DisplayName("validaAzman - con credenciales válidas - debe retornar respuesta exitosa")
    void validaAzman_conCredencialesValidas_retornaRespuestaExitosa() {
        // Arrange
        UsuarioIngresoRequestDTO requestDTO = UsuarioDTOBuilder.builder()
            .usuario(TestConstants.TEST_USUARIO)
            .password(TestConstants.TEST_PASSWORD)
            .buildRequest();

        UsuarioIngresoResponseDTO expectedResponse = UsuarioDTOBuilder.buildSuccessResponse();
        when(usuarioService.validarUsuarioAzman(any(UsuarioIngresoRequestDTO.class)))
            .thenReturn(expectedResponse);

        // Act
        UsuarioIngresoResponseDTO result = controller.validaAzman(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(TestConstants.CODIGO_EXITO, result.getCodigoRespuesta());
        assertEquals(TestConstants.TEST_JWT_TOKEN, result.getJwtToken());
        verify(usuarioService, times(1)).validarUsuarioAzman(requestDTO);
    }

    @Test
    @DisplayName("validaAzman - con credenciales inválidas - debe retornar error")
    void validaAzman_conCredencialesInvalidas_retornaError() {
        // Arrange
        UsuarioIngresoRequestDTO requestDTO = UsuarioDTOBuilder.builder()
            .usuario(TestConstants.TEST_USUARIO)
            .password("wrongpassword")
            .buildRequest();

        UsuarioIngresoResponseDTO expectedResponse = UsuarioDTOBuilder.buildErrorResponse();
        when(usuarioService.validarUsuarioAzman(any(UsuarioIngresoRequestDTO.class)))
            .thenReturn(expectedResponse);

        // Act
        UsuarioIngresoResponseDTO result = controller.validaAzman(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(TestConstants.CODIGO_ERROR, result.getCodigoRespuesta());
        assertNull(result.getJwtToken());
        verify(usuarioService, times(1)).validarUsuarioAzman(requestDTO);
    }

    @Test
    @DisplayName("obtenerPerfilUsuario - con usuario válido - debe retornar perfil")
    void obtenerPerfilUsuario_conUsuarioValido_retornaPerfil() {
        // Arrange
        String usuario = TestConstants.TEST_USUARIO;
        when(request.getParameter("usuario")).thenReturn(usuario);

        UsuarioIngresoResponseDTO expectedResponse = UsuarioDTOBuilder.buildSuccessResponse();
        when(usuarioService.obtenerPerfilUsuario(usuario))
            .thenReturn(expectedResponse);

        // Act
        UsuarioIngresoResponseDTO result = controller.obtenerPerfilUsuario(request, response);

        // Assert
        assertNotNull(result);
        assertEquals(TestConstants.CODIGO_EXITO, result.getCodigoRespuesta());
        verify(usuarioService, times(1)).obtenerPerfilUsuario(usuario);
    }

    @Test
    @DisplayName("obtenerPerfilUsuario - con usuario agente - debe incluir datos CRM")
    void obtenerPerfilUsuario_conUsuarioAgente_incluyeDatosCrm() {
        // Arrange
        String usuario = TestConstants.TEST_USUARIO_AGENTE;
        when(request.getParameter("usuario")).thenReturn(usuario);

        UsuarioIngresoResponseDTO expectedResponse = UsuarioDTOBuilder.buildAgenteResponse();
        when(usuarioService.obtenerPerfilUsuario(usuario))
            .thenReturn(expectedResponse);

        // Act
        UsuarioIngresoResponseDTO result = controller.obtenerPerfilUsuario(request, response);

        // Assert
        assertNotNull(result);
        assertEquals(TestConstants.CODIGO_EXITO, result.getCodigoRespuesta());
        assertTrue(result.getEsAgenteSolicitud());
        assertNotNull(result.getIdUsuarioCRM());
        assertNotNull(result.getCodigoVendedorCRM());
        verify(usuarioService, times(1)).obtenerPerfilUsuario(usuario);
    }

    @Test
    @DisplayName("obtenerPerfilUsuario - sin parámetro usuario - debe manejar null")
    void obtenerPerfilUsuario_sinParametroUsuario_manejaNullPointer() {
        // Arrange
        when(request.getParameter("usuario")).thenReturn(null);

        // Act
        UsuarioIngresoResponseDTO result = controller.obtenerPerfilUsuario(request, response);

        // Assert
        verify(usuarioService, times(1)).obtenerPerfilUsuario(null);
    }

    @Test
    @DisplayName("validaAzman - debe llamar al servicio correcto")
    void validaAzman_debeUsarServiceFactory() {
        // Arrange
        UsuarioIngresoRequestDTO requestDTO = UsuarioDTOBuilder.builder().buildRequest();
        UsuarioIngresoResponseDTO expectedResponse = UsuarioDTOBuilder.buildSuccessResponse();
        
        when(usuarioService.validarUsuarioAzman(any())).thenReturn(expectedResponse);

        // Act
        controller.validaAzman(requestDTO);

        // Assert
        verify(serviceFactory, atLeastOnce()).getUsuarioService();
    }
}


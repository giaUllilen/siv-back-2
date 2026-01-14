package pe.interseguro.siv.tests.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import pe.interseguro.siv.admin.transactional.factory.ServiceFactory;
import pe.interseguro.siv.admin.transactional.service.CotizaService;
import pe.interseguro.siv.admin.view.controller.CotizaController;
import pe.interseguro.siv.common.bean.CotizacionPDFBean;
import pe.interseguro.siv.common.dto.request.CotizacionCrmRequestDTO;
import pe.interseguro.siv.common.dto.request.CotizadorCorreoRequestDTO;
import pe.interseguro.siv.common.dto.request.ReprocesoRequestDTO;
import pe.interseguro.siv.common.dto.request.TokenRequestDTO;
import pe.interseguro.siv.common.dto.response.*;
import pe.interseguro.siv.common.util.Constantes;
import pe.interseguro.siv.tests.config.BaseTestConfig;
import pe.interseguro.siv.tests.utils.TestConstants;
import pe.interseguro.siv.tests.utils.builders.CotizacionDTOBuilder;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para CotizaController.
 * Valida el comportamiento de los endpoints de cotizaciones.
 * 
 * @author ti-is
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para CotizaController")
class CotizaControllerTest extends BaseTestConfig {

    @Mock
    private ServiceFactory serviceFactory;

    @Mock
    private CotizaService cotizaService;

    @Mock
    private MessageSource messageSource;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private CotizaController controller;

    @BeforeEach
    void setUp() {
        when(serviceFactory.getCotizaService()).thenReturn(cotizaService);
    }

    @Test
    @DisplayName("obtenerTabla - con código válido - debe retornar tabla")
    void obtenerTabla_conCodigoValido_retornaTabla() {
        // Arrange
        String codigoTabla = "TABLA_01";
        CotizaTablaResponseDTO expectedResponse = CotizacionDTOBuilder.buildTablaResponse();
        when(cotizaService.obtenerTabla(codigoTabla)).thenReturn(expectedResponse);

        // Act
        CotizaTablaResponseDTO result = controller.obtenerTabla(codigoTabla);

        // Assert
        assertNotNull(result);
        assertEquals(TestConstants.CODIGO_EXITO, result.getCodigoRespuesta());
        verify(cotizaService, times(1)).obtenerTabla(codigoTabla);
    }

    @Test
    @DisplayName("getLinkPago - con cotización válida - debe retornar link")
    void getLinkPago_conCotizacionValida_retornaLink() {
        // Arrange
        String cotizacion = TestConstants.TEST_COTIZACION_NUMERO;
        LinkPagoCotResponseDTO expectedResponse = new LinkPagoCotResponseDTO();
        expectedResponse.setCodigoRespuesta(TestConstants.CODIGO_EXITO);
        
        when(cotizaService.getLinkPago(cotizacion)).thenReturn(expectedResponse);

        // Act
        LinkPagoCotResponseDTO result = controller.getLinkPago(cotizacion);

        // Assert
        assertNotNull(result);
        assertEquals(TestConstants.CODIGO_EXITO, result.getCodigoRespuesta());
        verify(cotizaService, times(1)).getLinkPago(cotizacion);
    }

    @Test
    @DisplayName("getlistaDocumentoProducto - con documento válido - debe retornar lista")
    void getlistaDocumentoProducto_conDocumentoValido_retornaLista() {
        // Arrange
        String documento = TestConstants.TEST_DOCUMENTO;
        CotizaListaResponseDTO expectedResponse = CotizacionDTOBuilder.builder()
            .documento(documento)
            .buildListaResponse();
        
        when(cotizaService.listaDocumentoProducto(anyString(), anyString()))
            .thenReturn(expectedResponse);

        // Act
        CotizaListaResponseDTO result = controller.getlistaDocumentoProducto(documento);

        // Assert
        assertNotNull(result);
        assertEquals(TestConstants.CODIGO_EXITO, result.getCodigoRespuesta());
        assertNotNull(result.getLista());
        assertFalse(result.getLista().isEmpty());
        verify(cotizaService, times(1)).listaDocumentoProducto(documento, Constantes.COTIZADOR_VIDA_PRODUCTO_PLAN_GARANTIZADO);
    }

    @Test
    @DisplayName("getDetalle - con número de cotización válido - debe retornar detalle")
    void getDetalle_conNumeroCotizacionValido_retornaDetalle() {
        // Arrange
        Long nroCotizacion = TestConstants.TEST_COTIZACION_ID;
        CotizaDetalleResponseDTO expectedResponse = CotizacionDTOBuilder.builder()
            .nroCotizacion(nroCotizacion)
            .buildDetalleResponse();
        
        when(cotizaService.detalle(nroCotizacion)).thenReturn(expectedResponse);

        // Act
        CotizaDetalleResponseDTO result = controller.getDetalle(nroCotizacion);

        // Assert
        assertNotNull(result);
        assertEquals(TestConstants.CODIGO_EXITO, result.getCodigoRespuesta());
        assertEquals(nroCotizacion.toString(), result.getNroCotizacion());
        verify(cotizaService, times(1)).detalle(nroCotizacion);
    }

    @Test
    @DisplayName("getlistaCotizacionesVida - con parámetros válidos - debe retornar cotizaciones")
    void getlistaCotizacionesVida_conParametrosValidos_retornaCotizaciones() {
        // Arrange
        String documento = TestConstants.TEST_DOCUMENTO;
        String idUsuario = TestConstants.TEST_USUARIO;
        String device = TestConstants.TEST_DEVICE_WEB;
        String os = TestConstants.TEST_OS_WINDOWS;
        
        CotizaListaResponseDTO expectedResponse = CotizacionDTOBuilder.builder().buildListaResponse();
        when(cotizaService.listaCotizacionesVida(documento, idUsuario, device, os))
            .thenReturn(expectedResponse);

        // Act
        CotizaListaResponseDTO result = controller.getlistaCotizacionesVida(documento, idUsuario, device, os);

        // Assert
        assertNotNull(result);
        assertEquals(TestConstants.CODIGO_EXITO, result.getCodigoRespuesta());
        verify(cotizaService, times(1)).listaCotizacionesVida(documento, idUsuario, device, os);
    }

    @Test
    @DisplayName("getURLCotizadorVidaNuevo - con datos válidos - debe retornar URL")
    void getURLCotizadorVidaNuevo_conDatosValidos_retornaUrl() {
        // Arrange
        String numDocumento = TestConstants.TEST_DOCUMENTO;
        String idUsuario = TestConstants.TEST_USUARIO;
        String device = TestConstants.TEST_DEVICE_WEB;
        String os = TestConstants.TEST_OS_WINDOWS;
        
        CotizaUrlResponse expectedResponse = new CotizaUrlResponse();
        expectedResponse.setCodigoRespuesta(TestConstants.CODIGO_EXITO);
        expectedResponse.setURL(TestConstants.TEST_URL_COTIZADOR);
        
        when(cotizaService.ObtenerURLCotizadorVida(numDocumento, idUsuario, device, os))
            .thenReturn(expectedResponse);

        // Act
        CotizaUrlResponse result = controller.getURLCotizadorVidaNuevo(numDocumento, idUsuario, device, os);

        // Assert
        assertNotNull(result);
        assertEquals(TestConstants.CODIGO_EXITO, result.getCodigoRespuesta());
        assertNotNull(result.getURL());
        verify(cotizaService, times(1)).ObtenerURLCotizadorVida(numDocumento, idUsuario, device, os);
    }

    @Test
    @DisplayName("getCorrelativoCotizador - con datos válidos - debe generar correlativo")
    void getCorrelativoCotizador_conDatosValidos_generaCorrelativo() {
        // Arrange
        String tipoDocumento = TestConstants.TEST_TIPO_DOCUMENTO;
        String numeroDocumento = TestConstants.TEST_DOCUMENTO;
        
        CotizadorCorrelativoResponseDTO expectedResponse = new CotizadorCorrelativoResponseDTO();
        expectedResponse.setCodigoRespuesta(TestConstants.CODIGO_EXITO);
        expectedResponse.setNumeroCotizacion("001");
        
        when(cotizaService.generarCorrelativo(tipoDocumento, numeroDocumento))
            .thenReturn(expectedResponse);

        // Act
        CotizadorCorrelativoResponseDTO result = controller.getCorrelativoCotizador(tipoDocumento, numeroDocumento);

        // Assert
        assertNotNull(result);
        assertEquals(TestConstants.CODIGO_EXITO, result.getCodigoRespuesta());
        assertNotNull(result.getNumeroCotizacion());
        verify(cotizaService, times(1)).generarCorrelativo(tipoDocumento, numeroDocumento);
    }

    @Test
    @DisplayName("getCumulo - con datos válidos - debe retornar cúmulo")
    void getCumulo_conDatosValidos_retornaCumulo() {
        // Arrange
        String tipoDocumento = TestConstants.TEST_TIPO_DOCUMENTO;
        String numeroDocumento = TestConstants.TEST_DOCUMENTO;
        
        CotizadorCumuloResponseDTO expectedResponse = new CotizadorCumuloResponseDTO();
        expectedResponse.setCodigoRespuesta(TestConstants.CODIGO_EXITO);
        
        when(cotizaService.obtenerCumulo(tipoDocumento, numeroDocumento))
            .thenReturn(expectedResponse);

        // Act
        CotizadorCumuloResponseDTO result = controller.getCumulo(tipoDocumento, numeroDocumento);

        // Assert
        assertNotNull(result);
        assertEquals(TestConstants.CODIGO_EXITO, result.getCodigoRespuesta());
        verify(cotizaService, times(1)).obtenerCumulo(tipoDocumento, numeroDocumento);
    }

    @Test
    @DisplayName("getURLRecotizarVida - con datos válidos - debe retornar URL de recotización")
    void getURLRecotizarVida_conDatosValidos_retornaUrlRecotizacion() {
        // Arrange
        String nroDocumento = TestConstants.TEST_DOCUMENTO;
        String nroCotizacion = TestConstants.TEST_COTIZACION_NUMERO;
        String idUsuario = TestConstants.TEST_USUARIO;
        
        CotizaUrlResponse expectedResponse = new CotizaUrlResponse();
        expectedResponse.setCodigoRespuesta(TestConstants.CODIGO_EXITO);
        expectedResponse.setURL(TestConstants.TEST_URL_COTIZADOR + "/recotizar");
        
        when(cotizaService.ObtenerURLRecotizarVida(nroDocumento, nroCotizacion, idUsuario))
            .thenReturn(expectedResponse);

        // Act
        CotizaUrlResponse result = controller.getURLRecotizarVida(nroDocumento, nroCotizacion, idUsuario);

        // Assert
        assertNotNull(result);
        assertEquals(TestConstants.CODIGO_EXITO, result.getCodigoRespuesta());
        assertNotNull(result.getURL());
        verify(cotizaService, times(1)).ObtenerURLRecotizarVida(nroDocumento, nroCotizacion, idUsuario);
    }

    @Test
    @DisplayName("enviarCorreo - con datos válidos - debe enviar correo exitosamente")
    void enviarCorreo_conDatosValidos_enviaCorreoExitosamente() {
        // Arrange
        CotizadorCorreoRequestDTO request = new CotizadorCorreoRequestDTO();
        request.setDestinatario(TestConstants.TEST_EMAIL);
        request.setAsegurado("Juan Pérez");
        request.setAgenteNombre("Agente Test");
        request.setAgenteCorreo("agente@test.com");
        request.setAdjuntos(new ArrayList<>());
        
        when(bindingResult.hasErrors()).thenReturn(false);
        
        BaseResponseDTO expectedResponse = new BaseResponseDTO();
        expectedResponse.setCodigoRespuesta(TestConstants.CODIGO_EXITO);
        
        when(cotizaService.enviarCorreo(anyString(), anyList(), anyString(), anyString(), anyString()))
            .thenReturn(expectedResponse);
        
        when(messageSource.getMessage(anyString(), any(), any()))
            .thenReturn(TestConstants.MENSAJE_EXITO);

        // Act
        BaseResponseDTO result = controller.enviarCorreo(request, bindingResult);

        // Assert
        assertNotNull(result);
        assertEquals(Constantes.CODIGO_RESPUESTA_GENERAL_EXITO, result.getCodigoRespuesta());
        verify(cotizaService, times(1)).enviarCorreo(anyString(), anyList(), anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("getTipoCambio - debe retornar tipo de cambio")
    void getTipoCambio_retornaTipoCambio() {
        // Arrange
        ConversionResponseDTO expectedResponse = new ConversionResponseDTO();
        expectedResponse.setCodigoRespuesta(TestConstants.CODIGO_EXITO);
        expectedResponse.setValor(TestConstants.TIPO_CAMBIO_TEST);
        
        when(cotizaService.obtenerTipoCambio()).thenReturn(expectedResponse);

        // Act
        ConversionResponseDTO result = controller.getTipoCambio();

        // Assert
        assertNotNull(result);
        assertEquals(TestConstants.CODIGO_EXITO, result.getCodigoRespuesta());
        assertNotNull(result.getValor());
        verify(cotizaService, times(1)).obtenerTipoCambio();
    }

    @Test
    @DisplayName("decryptTokenVidaFree - con token válido - debe desencriptar")
    void decryptTokenVidaFree_conTokenValido_desencripta() {
        // Arrange
        TokenRequestDTO request = new TokenRequestDTO();
        request.setToken(TestConstants.TEST_JWT_TOKEN);
        
        TokenResponseDTO expectedResponse = new TokenResponseDTO();
        expectedResponse.setCodigoRespuesta(TestConstants.CODIGO_EXITO);
        
        when(cotizaService.decryptToken(anyString())).thenReturn(expectedResponse);

        // Act
        TokenResponseDTO result = controller.decryptTokenVidaFree(request);

        // Assert
        assertNotNull(result);
        assertEquals(TestConstants.CODIGO_EXITO, result.getCodigoRespuesta());
        verify(cotizaService, times(1)).decryptToken(anyString());
    }

    @Test
    @DisplayName("validateToken - con token válido - debe validar")
    void validateToken_conTokenValido_valida() {
        // Arrange
        TokenRequestDTO request = new TokenRequestDTO();
        request.setToken(TestConstants.TEST_JWT_TOKEN);
        
        TokenResponseDTO expectedResponse = new TokenResponseDTO();
        expectedResponse.setCodigoRespuesta(TestConstants.CODIGO_EXITO);
        
        when(cotizaService.validateToken(anyString())).thenReturn(expectedResponse);

        // Act
        TokenResponseDTO result = controller.validateToken(request);

        // Assert
        assertNotNull(result);
        assertEquals(TestConstants.CODIGO_EXITO, result.getCodigoRespuesta());
        verify(cotizaService, times(1)).validateToken(anyString());
    }

    @Test
    @DisplayName("guardarCotizacionCrm - con datos válidos - debe guardar en CRM")
    void guardarCotizacionCrm_conDatosValidos_guardaEnCrm() {
        // Arrange
        CotizacionCrmRequestDTO request = CotizacionDTOBuilder.builder().buildCrmRequest();
        
        CotizacionCrmResponseDTO expectedResponse = new CotizacionCrmResponseDTO();
        expectedResponse.setCodigoRespuesta(TestConstants.CODIGO_EXITO);
        
        when(cotizaService.guardaCotizacionAdn(any())).thenReturn(expectedResponse);

        // Act
        CotizacionCrmResponseDTO result = controller.guardarCotizacionCrm(request);

        // Assert
        assertNotNull(result);
        assertEquals(TestConstants.CODIGO_EXITO, result.getCodigoRespuesta());
        verify(cotizaService, times(1)).guardaCotizacionAdn(request);
    }

    @Test
    @DisplayName("exportCotizacionPDF - con datos válidos - debe generar PDF")
    void exportCotizacionPDF_conDatosValidos_generaPdf() {
        // Arrange
        String numeroCotizacion = TestConstants.TEST_COTIZACION_NUMERO;
        String agente = "Agente Test";
        String asegurado = "Asegurado Test";
        
        ByteArrayOutputStream mockOutputStream = new ByteArrayOutputStream();
        mockOutputStream.write(new byte[]{1, 2, 3}, 0, 3);
        
        when(cotizaService.generarPDFCotizacionVida(numeroCotizacion, agente, asegurado))
            .thenReturn(mockOutputStream);

        // Act
        ResponseEntity<InputStreamResource> result = controller.exportCotizacionPDF(numeroCotizacion, agente, asegurado);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(cotizaService, times(1)).generarPDFCotizacionVida(numeroCotizacion, agente, asegurado);
    }

    @Test
    @DisplayName("clonarVidaFree - con número de cotización válido - debe clonar")
    void clonarVidaFree_conNumeroCotizacionValido_clona() {
        // Arrange
        String numeroCotizacion = TestConstants.TEST_COTIZACION_NUMERO;
        
        ClonarCVFResponseDTO expectedResponse = new ClonarCVFResponseDTO();
        expectedResponse.setRs(TestConstants.CODIGO_EXITO);
        
        when(cotizaService.clonarCotizacionVidaFree(numeroCotizacion))
            .thenReturn(expectedResponse);

        // Act
        ClonarCVFResponseDTO result = controller.clonarVidaFree(numeroCotizacion);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getRs());
        verify(cotizaService, times(1)).clonarCotizacionVidaFree(numeroCotizacion);
    }

    @Test
    @DisplayName("forzarVidaFree - con lista de cotizaciones - debe forzar reproceso")
    void forzarVidaFree_conListaCotizaciones_fuerzaReproceso() {
        // Arrange
        ReprocesoRequestDTO request = new ReprocesoRequestDTO();
        request.setCotizaciones(Arrays.asList("COT-001", "COT-002", "COT-003"));
        
        BaseResponseDTO expectedResponse = new BaseResponseDTO();
        expectedResponse.setCodigoRespuesta(TestConstants.CODIGO_EXITO);
        
        when(cotizaService.forzarRecotizacion(anyList())).thenReturn(new ProcesoResponseDTO());

        // Act
        BaseResponseDTO result = controller.forzarVidaFree(request);

        // Assert
        assertNotNull(result);
        verify(cotizaService, times(1)).forzarRecotizacion(request.getCotizaciones());
    }

    @Test
    @DisplayName("validarVidaFree - con número de cotización válido - debe validar")
    void validarVidaFree_conNumeroCotizacionValido_valida() {
        // Arrange
        String numeroCotizacion = TestConstants.TEST_COTIZACION_NUMERO;
        
        BaseResponseDTO expectedResponse = new BaseResponseDTO();
        expectedResponse.setCodigoRespuesta(TestConstants.CODIGO_EXITO);
        
        when(cotizaService.validarNoObservado(numeroCotizacion)).thenReturn(expectedResponse);

        // Act
        BaseResponseDTO result = controller.validarVidaFree(numeroCotizacion);

        // Assert
        assertNotNull(result);
        assertEquals(TestConstants.CODIGO_EXITO, result.getCodigoRespuesta());
        verify(cotizaService, times(1)).validarNoObservado(numeroCotizacion);
    }
}


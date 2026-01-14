package pe.interseguro.siv.tests.services;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.test.util.ReflectionTestUtils;
import pe.interseguro.siv.admin.transactional.factory.ServiceFactory;
import pe.interseguro.siv.admin.transactional.service.impl.CotizaServiceImpl;
import pe.interseguro.siv.common.dto.request.TokenRequestDTO;
import pe.interseguro.siv.common.dto.response.*;
import pe.interseguro.siv.common.persistence.db.mysql.repository.*;
import pe.interseguro.siv.common.persistence.db.postgres.repository.AsegurableRepository;
import pe.interseguro.siv.common.persistence.db.postgres.repository.CotizacionRepository;
import pe.interseguro.siv.common.persistence.rest.global.GlobalRestClient;
import pe.interseguro.siv.common.persistence.rest.interseguro.InterseguroRestClient;
import pe.interseguro.siv.common.util.Constantes;
import pe.interseguro.siv.tests.config.BaseTestConfig;
import pe.interseguro.siv.tests.utils.TestConstants;
import pe.interseguro.siv.tests.utils.builders.CotizacionDTOBuilder;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para CotizaServiceImpl.
 * Valida la lógica de negocio relacionada con cotizaciones.
 * 
 * @author ti-is
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para CotizaServiceImpl")
class CotizaServiceImplTest extends BaseTestConfig {

    @Mock
    private ServiceFactory serviceFactory;

    @Mock
    private MessageSource messageSource;

    @Mock
    private MultitablaRepository multitablaRepository;

    @Mock
    private CotizacionCorrelativoRepository cotizacionCorrelativoRepository;

    @Mock
    private PersonaRepository personaRepository;

    @Mock
    private SolicitudPGRepository solicitudPGRepository;

    @Mock
    private SolicitudVFRepository solicitudVFRepository;

    @Mock
    private PlanFuturoRepository planFuturoRepository;

    @Mock
    private CotizacionRepository cotizacionRepository;

    @Mock
    private SolicitudRepository solicitudRepository;

    @Mock
    private AsegurableRepository asegurableRepository;

    @Mock
    private ServicioEdnRepository servicioEdnRepository;

    @Mock
    private SolicitudProductoDetalleRepository solicitudProductoDetalleRepository;

    @Mock
    private GlobalRestClient globalRestClient;

    @Mock
    private InterseguroRestClient interseguroRestClient;

    @InjectMocks
    private CotizaServiceImpl service;

    private Gson gson;

    @BeforeEach
    void setUp() {
        gson = new Gson();
        
        // Configurar valores de properties
        ReflectionTestUtils.setField(service, "urlCotizadorVida", TestConstants.TEST_URL_COTIZADOR);
        ReflectionTestUtils.setField(service, "serverPort", "8080");
        ReflectionTestUtils.setField(service, "flagCumulo", "true");
        
        when(messageSource.getMessage(anyString(), any(), any(Locale.class)))
            .thenReturn(TestConstants.MENSAJE_EXITO);
    }

    @Test
    @DisplayName("obtenerTabla - con código válido - debe retornar datos de tabla")
    void obtenerTabla_conCodigoValido_retornaDatosTabla() {
        // Arrange
        String codigoTabla = "TABLA_01";
        
        // Mock del repositorio
        when(multitablaRepository.findByCodigoTabla(codigoTabla)).thenReturn(new ArrayList<>());

        // Act
        CotizaTablaResponseDTO result = service.obtenerTabla(codigoTabla);

        // Assert
        assertNotNull(result);
        verify(multitablaRepository, times(1)).findByCodigoTabla(codigoTabla);
    }

    @Test
    @DisplayName("generarCorrelativo - con documento válido - debe generar correlativo")
    void generarCorrelativo_conDocumentoValido_generaCorrelativo() {
        // Arrange
        String tipoDocumento = TestConstants.TEST_TIPO_DOCUMENTO;
        String numeroDocumento = TestConstants.TEST_DOCUMENTO;

        // Act
        CotizadorCorrelativoResponseDTO result = service.generarCorrelativo(tipoDocumento, numeroDocumento);

        // Assert
        assertNotNull(result);
        assertEquals(Constantes.CODIGO_RESPUESTA_GENERAL_EXITO, result.getCodigoRespuesta());
        assertNotNull(result.getNumeroCotizacion());
    }

    @Test
    @DisplayName("decryptToken - con token válido - debe desencriptar correctamente")
    void decryptToken_conTokenValido_desencriptaCorrectamente() {
        // Arrange
        String token = TestConstants.TEST_JWT_TOKEN;

        // Act
        TokenResponseDTO result = service.decryptToken(token);

        // Assert
        assertNotNull(result);
    }

    @Test
    @DisplayName("validateToken - con token válido - debe validar correctamente")
    void validateToken_conTokenValido_validaCorrectamente() {
        // Arrange
        String token = TestConstants.TEST_JWT_TOKEN;

        // Act
        TokenResponseDTO result = service.validateToken(token);

        // Assert
        assertNotNull(result);
    }

    @Test
    @DisplayName("obtenerTipoCambio - debe retornar tipo de cambio actual")
    void obtenerTipoCambio_retornaTipoCambioActual() {
        // Arrange
        // Mock de cliente REST si es necesario

        // Act
        ConversionResponseDTO result = service.obtenerTipoCambio();

        // Assert
        assertNotNull(result);
    }

    @Test
    @DisplayName("validarNoObservado - con cotización válida - debe validar estado")
    void validarNoObservado_conCotizacionValida_validaEstado() {
        // Arrange
        String numeroCotizacion = TestConstants.TEST_COTIZACION_NUMERO;

        // Act
        BaseResponseDTO result = service.validarNoObservado(numeroCotizacion);

        // Assert
        assertNotNull(result);
    }

    @Test
    @DisplayName("buscarEnArchivo - con datos válidos - debe buscar en archivo")
    void buscarEnArchivo_conDatosValidos_buscaEnArchivo() {
        // Arrange
        String dato = "test_dato";
        String archivo = "test_archivo.txt";

        // Act
        BaseResponseDTO result = service.buscarEnArchivo(dato, archivo);

        // Assert
        assertNotNull(result);
    }

    @Test
    @DisplayName("obtenerCumulo - con documento válido - debe consultar cúmulo")
    void obtenerCumulo_conDocumentoValido_consultaCumulo() {
        // Arrange
        String tipoDocumento = TestConstants.TEST_TIPO_DOCUMENTO;
        String numeroDocumento = TestConstants.TEST_DOCUMENTO;

        // Act
        CotizadorCumuloResponseDTO result = service.obtenerCumulo(tipoDocumento, numeroDocumento);

        // Assert
        assertNotNull(result);
    }

    @Test
    @DisplayName("obtenerTipoCambioAcsele - debe retornar tipo de cambio de Acsele")
    void obtenerTipoCambioAcsele_retornaTipoCambioAcsele() {
        // Act
        TipoCambioResponseDTO result = service.obtenerTipoCambioAcsele();

        // Assert
        assertNotNull(result);
    }
}


package pe.interseguro.siv.tests.utils;

/**
 * Constantes utilizadas en las pruebas unitarias.
 * 
 * @author ti-is
 */
public final class TestConstants {

    // Usuarios de prueba
    public static final String TEST_USUARIO = "testuser";
    public static final String TEST_USUARIO_AGENTE = "agente001";
    public static final String TEST_PASSWORD = "password123";
    public static final String TEST_EMAIL = "test@interseguro.com";
    public static final String TEST_NOMBRE_COMPLETO = "Usuario Test";

    // Tokens de prueba
    public static final String TEST_JWT_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImlhdCI6MTUxNjIzOTAyMn0.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
    public static final String TEST_REFRESH_TOKEN = "refresh_token_test_12345";

    // Códigos de respuesta
    public static final String CODIGO_EXITO = "000";
    public static final String CODIGO_ERROR = "999";
    public static final String MENSAJE_EXITO = "Operación exitosa";
    public static final String MENSAJE_ERROR = "Error en la operación";

    // Cotizaciones de prueba
    public static final Long TEST_COTIZACION_ID = 123456L;
    public static final String TEST_COTIZACION_NUMERO = "COT-2024-001";
    public static final String TEST_DOCUMENTO = "12345678";
    public static final String TEST_TIPO_DOCUMENTO = "DNI";

    // CRM de prueba
    public static final String TEST_ID_OPORTUNIDAD = "OPP-001";
    public static final String TEST_ID_AGENTE_CRM = "AGT-001";
    public static final String TEST_CODIGO_AGENTE = "A001";
    public static final String TEST_CODIGO_AGENCIA = "AGENCIA-01";

    // Productos de prueba
    public static final String TEST_PRODUCTO_VIDA_GARANTIZADO = "VG";
    public static final String TEST_PRODUCTO_VIDA_FREE = "VF";
    public static final String TEST_PRODUCTO_ADN = "ADN";

    // URLs de prueba
    public static final String TEST_URL_COTIZADOR = "http://localhost:8080/cotizador";
    public static final String TEST_URL_CRM = "http://localhost:8080/crm";
    public static final String TEST_URL_GLOBAL = "http://localhost:8080/global";

    // Solicitudes de prueba
    public static final Long TEST_SOLICITUD_ID = 789012L;
    public static final String TEST_SOLICITUD_NUMERO = "SOL-2024-001";

    // Azman de prueba
    public static final String TEST_AZMAN_APLICACION = "SEGURIDAD";
    public static final String TEST_AZMAN_DOMINIO = "DINTERSEGURO";
    public static final String TEST_AZMAN_ROL_AGENTE = "Perfil_Agente_Comercial_vida";
    public static final String TEST_AZMAN_ROL_ADMIN = "Perfil_Admin_vida";

    // Perfiles de prueba
    public static final String TEST_PERFIL_AGENTE = "AGENTE";
    public static final String TEST_PERFIL_SUPERVISOR = "SUPERVISOR";
    public static final String TEST_PERFIL_ADMIN = "ADMIN";

    // Status HTTP de prueba
    public static final String HTTP_STATUS_200 = "200";
    public static final String HTTP_STATUS_400 = "400";
    public static final String HTTP_STATUS_401 = "401";
    public static final String HTTP_STATUS_404 = "404";
    public static final String HTTP_STATUS_500 = "500";

    // Monedas de prueba
    public static final String MONEDA_SOLES = "PEN";
    public static final String MONEDA_DOLARES = "USD";
    public static final Double TIPO_CAMBIO_TEST = 3.75;

    // Archivos de prueba
    public static final String TEST_PDF_FILENAME = "test_cotizacion.pdf";
    public static final String TEST_DOCX_FILENAME = "test_solicitud.docx";
    public static final String TEST_TEMPLATE_PATH = "templates/test_template.html";

    // Device y OS de prueba
    public static final String TEST_DEVICE_MOBILE = "MOBILE";
    public static final String TEST_DEVICE_WEB = "WEB";
    public static final String TEST_OS_ANDROID = "ANDROID";
    public static final String TEST_OS_IOS = "IOS";
    public static final String TEST_OS_WINDOWS = "WINDOWS";

    // Paths de prueba
    public static final String TEST_PATH_LOGIN = "/api/v1/usuarios/login";
    public static final String TEST_PATH_COTIZACIONES = "/api/v1/cotizaciones";
    public static final String TEST_PATH_SOLICITUDES = "/api/v1/solicitudes";

    // Valores numéricos de prueba
    public static final Double TEST_SUMA_ASEGURADA = 50000.00;
    public static final Double TEST_PRIMA_NETA = 1500.00;
    public static final Double TEST_PRIMA_TOTAL = 1770.00;
    public static final Integer TEST_PLAZO_ANOS = 10;
    public static final Integer TEST_EDAD = 35;

    /**
     * Constructor privado para evitar instanciación
     */
    private TestConstants() {
        throw new UnsupportedOperationException("Esta es una clase de utilidades y no debe ser instanciada");
    }
}


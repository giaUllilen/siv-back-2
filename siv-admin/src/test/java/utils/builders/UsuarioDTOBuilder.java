package pe.interseguro.siv.tests.utils.builders;

import pe.interseguro.siv.common.dto.request.UsuarioIngresoRequestDTO;
import pe.interseguro.siv.common.dto.response.UsuarioIngresoResponseDTO;
import pe.interseguro.siv.tests.utils.TestConstants;

/**
 * Builder para crear objetos UsuarioIngresoRequestDTO y UsuarioIngresoResponseDTO para pruebas.
 * Implementa el patrón Builder para facilitar la creación de objetos de prueba.
 * 
 * @author ti-is
 */
public class UsuarioDTOBuilder {

    private String usuario;
    private String password;
    private String device;
    private String os;
    private String path;

    /**
     * Constructor privado para iniciar el builder
     */
    private UsuarioDTOBuilder() {
        // Valores por defecto
        this.usuario = TestConstants.TEST_USUARIO;
        this.password = TestConstants.TEST_PASSWORD;
        this.device = TestConstants.TEST_DEVICE_WEB;
        this.os = TestConstants.TEST_OS_WINDOWS;
        this.path = TestConstants.TEST_PATH_LOGIN;
    }

    /**
     * Crea una nueva instancia del builder
     * 
     * @return Nueva instancia del builder
     */
    public static UsuarioDTOBuilder builder() {
        return new UsuarioDTOBuilder();
    }

    /**
     * Establece el usuario
     * 
     * @param usuario Nombre de usuario
     * @return Builder actualizado
     */
    public UsuarioDTOBuilder usuario(String usuario) {
        this.usuario = usuario;
        return this;
    }

    /**
     * Establece la contraseña
     * 
     * @param password Contraseña
     * @return Builder actualizado
     */
    public UsuarioDTOBuilder password(String password) {
        this.password = password;
        return this;
    }

    /**
     * Establece el dispositivo
     * 
     * @param device Tipo de dispositivo
     * @return Builder actualizado
     */
    public UsuarioDTOBuilder device(String device) {
        this.device = device;
        return this;
    }

    /**
     * Establece el sistema operativo
     * 
     * @param os Sistema operativo
     * @return Builder actualizado
     */
    public UsuarioDTOBuilder os(String os) {
        this.os = os;
        return this;
    }

    /**
     * Establece el path de la petición
     * 
     * @param path Path de la petición
     * @return Builder actualizado
     */
    public UsuarioDTOBuilder path(String path) {
        this.path = path;
        return this;
    }

    /**
     * Construye el objeto UsuarioIngresoRequestDTO
     * 
     * @return UsuarioIngresoRequestDTO construido
     */
    public UsuarioIngresoRequestDTO buildRequest() {
        UsuarioIngresoRequestDTO request = new UsuarioIngresoRequestDTO();
        request.setUsuario(this.usuario);
        request.setPassword(this.password);
        request.setDevice(this.device);
        request.setOs(this.os);
        request.setPath(this.path);
        return request;
    }

    /**
     * Construye un objeto UsuarioIngresoResponseDTO exitoso
     * 
     * @return UsuarioIngresoResponseDTO con datos de éxito
     */
    public static UsuarioIngresoResponseDTO buildSuccessResponse() {
        UsuarioIngresoResponseDTO response = new UsuarioIngresoResponseDTO();
        response.setCodigoRespuesta(TestConstants.CODIGO_EXITO);
        response.setMensajeRespuesta(TestConstants.MENSAJE_EXITO);
        response.setJwtToken(TestConstants.TEST_JWT_TOKEN);
        response.setIdUsuario(TestConstants.TEST_USUARIO);
        response.setNombreUsuario(TestConstants.TEST_NOMBRE_COMPLETO);
        response.setCorreoUsuario(TestConstants.TEST_EMAIL);
        return response;
    }

    /**
     * Construye un objeto UsuarioIngresoResponseDTO con error
     * 
     * @return UsuarioIngresoResponseDTO con datos de error
     */
    public static UsuarioIngresoResponseDTO buildErrorResponse() {
        UsuarioIngresoResponseDTO response = new UsuarioIngresoResponseDTO();
        response.setCodigoRespuesta(TestConstants.CODIGO_ERROR);
        response.setMensajeRespuesta(TestConstants.MENSAJE_ERROR);
        return response;
    }

    /**
     * Construye un objeto UsuarioIngresoResponseDTO para agente
     * 
     * @return UsuarioIngresoResponseDTO con datos de agente
     */
    public static UsuarioIngresoResponseDTO buildAgenteResponse() {
        UsuarioIngresoResponseDTO response = buildSuccessResponse();
        response.setIdUsuario(TestConstants.TEST_USUARIO_AGENTE);
        response.setIdUsuarioCRM(TestConstants.TEST_ID_AGENTE_CRM);
        response.setCodigoVendedorCRM(TestConstants.TEST_CODIGO_AGENTE);
        response.setCodigoAgenciaCRM(TestConstants.TEST_CODIGO_AGENCIA);
        response.setEsAgenteSolicitud(true);
        return response;
    }
}


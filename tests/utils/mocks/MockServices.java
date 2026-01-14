package pe.interseguro.siv.tests.utils.mocks;

import org.mockito.Mock;
import org.springframework.context.MessageSource;
import pe.interseguro.siv.admin.transactional.factory.ServiceFactory;
import pe.interseguro.siv.admin.transactional.service.*;

/**
 * Clase que agrupa todos los mocks de servicios necesarios para pruebas.
 * Facilita la reutilización de mocks en diferentes clases de prueba.
 * 
 * @author ti-is
 */
public class MockServices {

    @Mock
    public ServiceFactory serviceFactory;

    @Mock
    public CotizaService cotizaService;

    @Mock
    public UsuarioService usuarioService;

    @Mock
    public AdnService adnService;

    @Mock
    public SolicitudService solicitudService;

    @Mock
    public UtilitarioService utilitarioService;

    @Mock
    public DescargaService descargaService;

    @Mock
    public LogService logService;

    @Mock
    public MessageSource messageSource;

    /**
     * Constructor por defecto
     */
    public MockServices() {
        // Constructor vacío para permitir extensión
    }
}


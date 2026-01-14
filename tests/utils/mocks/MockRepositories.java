package pe.interseguro.siv.tests.utils.mocks;

import org.mockito.Mock;
import pe.interseguro.siv.common.persistence.db.mysql.repository.*;
import pe.interseguro.siv.common.persistence.db.postgres.repository.AsegurableRepository;
import pe.interseguro.siv.common.persistence.db.postgres.repository.CotizacionRepository;

/**
 * Clase que agrupa todos los mocks de repositorios necesarios para pruebas.
 * Facilita la reutilización de mocks en diferentes clases de prueba.
 * 
 * @author ti-is
 */
public class MockRepositories {

    @Mock
    public MultitablaRepository multitablaRepository;

    @Mock
    public CotizacionCorrelativoRepository cotizacionCorrelativoRepository;

    @Mock
    public PersonaRepository personaRepository;

    @Mock
    public SolicitudPGRepository solicitudPGRepository;

    @Mock
    public SolicitudVFRepository solicitudVFRepository;

    @Mock
    public PlanFuturoRepository planFuturoRepository;

    @Mock
    public SolicitudRepository solicitudRepository;

    @Mock
    public UsuarioPerfilRepository usuarioPerfilRepository;

    @Mock
    public EventLogRepository eventLogRepository;

    @Mock
    public CotizacionRepository cotizacionRepositoryPostgres;

    @Mock
    public AsegurableRepository asegurableRepository;

    @Mock
    public SolicitudProductoDetalleRepository solicitudProductoDetalleRepository;

    @Mock
    public ServicioEdnRepository servicioEdnRepository;

    /**
     * Constructor por defecto
     */
    public MockRepositories() {
        // Constructor vacío para permitir extensión
    }
}


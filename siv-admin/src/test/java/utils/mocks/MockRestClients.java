package pe.interseguro.siv.tests.utils.mocks;

import org.mockito.Mock;
import pe.interseguro.siv.common.persistence.rest.acsele.AcseleRestClient;
import pe.interseguro.siv.common.persistence.rest.bupo.BupoRestClient;
import pe.interseguro.siv.common.persistence.rest.cotizador.CotizadorRestClient;
import pe.interseguro.siv.common.persistence.rest.crm.CrmRestClient;
import pe.interseguro.siv.common.persistence.rest.global.GlobalRestClient;
import pe.interseguro.siv.common.persistence.rest.interseguro.InterseguroRestClient;
import pe.interseguro.siv.common.persistence.rest.plaft.PlaftRestClient;
import pe.interseguro.siv.common.persistence.rest.vidafree.VidaFreeRestClient;

/**
 * Clase que agrupa todos los mocks de clientes REST necesarios para pruebas.
 * Facilita la reutilización de mocks en diferentes clases de prueba.
 * 
 * @author ti-is
 */
public class MockRestClients {

    @Mock
    public InterseguroRestClient interseguroRestClient;

    @Mock
    public CrmRestClient crmRestClient;

    @Mock
    public GlobalRestClient globalRestClient;

    @Mock
    public CotizadorRestClient cotizadorRestClient;

    @Mock
    public VidaFreeRestClient vidaFreeRestClient;

    @Mock
    public AcseleRestClient acseleRestClient;

    @Mock
    public BupoRestClient bupoRestClient;

    @Mock
    public PlaftRestClient plaftRestClient;

    /**
     * Constructor por defecto
     */
    public MockRestClients() {
        // Constructor vacío para permitir extensión
    }
}


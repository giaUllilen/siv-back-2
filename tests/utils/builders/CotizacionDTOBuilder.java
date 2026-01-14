package pe.interseguro.siv.tests.utils.builders;

import pe.interseguro.siv.common.dto.request.CotizacionCrmRequestDTO;
import pe.interseguro.siv.common.dto.response.CotizaDetalleResponseDTO;
import pe.interseguro.siv.common.dto.response.CotizaListaItemResponseDTO;
import pe.interseguro.siv.common.dto.response.CotizaListaResponseDTO;
import pe.interseguro.siv.common.dto.response.CotizaTablaResponseDTO;
import pe.interseguro.siv.tests.utils.TestConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder para crear objetos relacionados con Cotizaciones para pruebas.
 * Implementa el patrón Builder para facilitar la creación de objetos de prueba.
 * 
 * @author ti-is
 */
public class CotizacionDTOBuilder {

    private Long nroCotizacion;
    private String documento;
    private String tipoDocumento;
    private String producto;
    private Double sumaAsegurada;
    private Double primaNeta;
    private Double primaTotal;

    /**
     * Constructor privado para iniciar el builder
     */
    private CotizacionDTOBuilder() {
        // Valores por defecto
        this.nroCotizacion = TestConstants.TEST_COTIZACION_ID;
        this.documento = TestConstants.TEST_DOCUMENTO;
        this.tipoDocumento = TestConstants.TEST_TIPO_DOCUMENTO;
        this.producto = TestConstants.TEST_PRODUCTO_VIDA_GARANTIZADO;
        this.sumaAsegurada = TestConstants.TEST_SUMA_ASEGURADA;
        this.primaNeta = TestConstants.TEST_PRIMA_NETA;
        this.primaTotal = TestConstants.TEST_PRIMA_TOTAL;
    }

    /**
     * Crea una nueva instancia del builder
     * 
     * @return Nueva instancia del builder
     */
    public static CotizacionDTOBuilder builder() {
        return new CotizacionDTOBuilder();
    }

    /**
     * Establece el número de cotización
     * 
     * @param nroCotizacion Número de cotización
     * @return Builder actualizado
     */
    public CotizacionDTOBuilder nroCotizacion(Long nroCotizacion) {
        this.nroCotizacion = nroCotizacion;
        return this;
    }

    /**
     * Establece el documento
     * 
     * @param documento Número de documento
     * @return Builder actualizado
     */
    public CotizacionDTOBuilder documento(String documento) {
        this.documento = documento;
        return this;
    }

    /**
     * Establece el tipo de documento
     * 
     * @param tipoDocumento Tipo de documento
     * @return Builder actualizado
     */
    public CotizacionDTOBuilder tipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
        return this;
    }

    /**
     * Establece el producto
     * 
     * @param producto Código de producto
     * @return Builder actualizado
     */
    public CotizacionDTOBuilder producto(String producto) {
        this.producto = producto;
        return this;
    }

    /**
     * Establece la suma asegurada
     * 
     * @param sumaAsegurada Suma asegurada
     * @return Builder actualizado
     */
    public CotizacionDTOBuilder sumaAsegurada(Double sumaAsegurada) {
        this.sumaAsegurada = sumaAsegurada;
        return this;
    }

    /**
     * Construye un CotizaTablaResponseDTO
     * 
     * @return CotizaTablaResponseDTO construido
     */
    public static CotizaTablaResponseDTO buildTablaResponse() {
        CotizaTablaResponseDTO response = new CotizaTablaResponseDTO();
        response.setCodigoRespuesta(TestConstants.CODIGO_EXITO);
        response.setMensajeRespuesta(TestConstants.MENSAJE_EXITO);
        return response;
    }

    /**
     * Construye un CotizaListaResponseDTO con lista de cotizaciones
     * 
     * @return CotizaListaResponseDTO construido
     */
    public CotizaListaResponseDTO buildListaResponse() {
        CotizaListaResponseDTO response = new CotizaListaResponseDTO();
        response.setCodigoRespuesta(TestConstants.CODIGO_EXITO);
        response.setMensajeRespuesta(TestConstants.MENSAJE_EXITO);
        
        List<CotizaListaItemResponseDTO> lista = new ArrayList<>();
        CotizaListaItemResponseDTO item = new CotizaListaItemResponseDTO();
        item.setNroCotizacion(this.nroCotizacion.toString());
        item.setNumeroDocumento(this.documento);
        item.setProductoNombre(this.producto);
        lista.add(item);
        
        response.setLista(lista);
        return response;
    }

    /**
     * Construye un CotizaDetalleResponseDTO
     * 
     * @return CotizaDetalleResponseDTO construido
     */
    public CotizaDetalleResponseDTO buildDetalleResponse() {
        CotizaDetalleResponseDTO response = new CotizaDetalleResponseDTO();
        response.setCodigoRespuesta(TestConstants.CODIGO_EXITO);
        response.setMensajeRespuesta(TestConstants.MENSAJE_EXITO);
        response.setNroCotizacion(this.nroCotizacion.toString());
        response.setMoneda(TestConstants.MONEDA_SOLES);
        response.setPlanId(this.producto);
        return response;
    }

    /**
     * Construye un CotizacionCrmRequestDTO
     * 
     * @return CotizacionCrmRequestDTO construido
     */
    public CotizacionCrmRequestDTO buildCrmRequest() {
        CotizacionCrmRequestDTO request = new CotizacionCrmRequestDTO();
        request.setNumeroCotizacion(this.nroCotizacion.toString());
        request.setNumeroDocumento(this.documento);
        request.setTipoDocumento(this.tipoDocumento);
        request.setIdProducto(this.producto);
        return request;
    }

    /**
     * Construye una lista vacía de cotizaciones
     * 
     * @return CotizaListaResponseDTO con lista vacía
     */
    public static CotizaListaResponseDTO buildListaVaciaResponse() {
        CotizaListaResponseDTO response = new CotizaListaResponseDTO();
        response.setCodigoRespuesta(TestConstants.CODIGO_EXITO);
        response.setMensajeRespuesta(TestConstants.MENSAJE_EXITO);
        response.setLista(new ArrayList<>());
        return response;
    }
}


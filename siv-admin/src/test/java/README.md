# Tests - Pruebas Unitarias del Proyecto SIV Backend

Este directorio contiene todas las pruebas unitarias del proyecto SIV Backend.

## Estructura

```
tests/
├── config/                          # Configuraciones base para pruebas
│   ├── BaseTestConfig.java         # Configuración base para todas las pruebas
│   └── TestSecurityConfig.java     # Configuración de seguridad para pruebas
│
├── controllers/                     # Pruebas de controladores REST
│   ├── CotizaControllerTest.java
│   ├── UsuarioControllerTest.java
│   ├── AdnControllerTest.java
│   ├── SolicitudControllerTest.java
│   └── UtilitarioControllerTest.java
│
├── services/                        # Pruebas de servicios de negocio
│   ├── CotizaServiceImplTest.java
│   ├── UsuarioServiceImplTest.java
│   ├── AdnServiceImplTest.java
│   ├── SolicitudServiceImplTest.java
│   └── UtilitarioServiceImplTest.java
│
├── integration/                     # Pruebas de integración con servicios externos
│   ├── CrmRestClientTest.java
│   ├── GlobalRestClientTest.java
│   └── InterseguroRestClientTest.java
│
└── utils/                           # Utilidades para pruebas
    ├── builders/                    # Builders de objetos de prueba
    │   ├── CotizacionDTOBuilder.java
    │   ├── UsuarioDTOBuilder.java
    │   └── SolicitudDTOBuilder.java
    │
    ├── mocks/                       # Mocks reutilizables
    │   ├── MockRepositories.java
    │   ├── MockRestClients.java
    │   └── MockServices.java
    │
    └── TestConstants.java           # Constantes para pruebas

```

## Tecnologías Utilizadas

- **JUnit 5**: Framework de pruebas unitarias
- **Mockito**: Framework para crear mocks
- **Spring Boot Test**: Utilidades de testing para Spring Boot
- **AssertJ**: Librería de assertions fluidas

## Convenciones

### Nomenclatura
- Clases de prueba: `NombreClaseTest.java`
- Métodos de prueba: `nombreMetodo_escenario_resultadoEsperado()`

### Estructura de Prueba
Cada prueba sigue el patrón AAA (Arrange-Act-Assert):

```java
@Test
void nombreMetodo_escenario_resultadoEsperado() {
    // Arrange - Preparar datos y mocks
    DatoDTO request = DatoDTOBuilder.builder().build();
    when(mockService.metodo(any())).thenReturn(response);
    
    // Act - Ejecutar el método bajo prueba
    ResultadoDTO resultado = controller.metodo(request);
    
    // Assert - Verificar resultados
    assertNotNull(resultado);
    assertEquals(expectedValue, resultado.getValue());
    verify(mockService, times(1)).metodo(any());
}
```

## Ejecución de Pruebas

### Ejecutar todas las pruebas
```bash
mvn test
```

### Ejecutar pruebas de un paquete específico
```bash
mvn test -Dtest="pe.interseguro.siv.tests.controllers.*"
```

### Ejecutar una prueba específica
```bash
mvn test -Dtest="CotizaControllerTest"
```

### Generar reporte de cobertura
```bash
mvn clean test jacoco:report
```

## Cobertura de Código

Se busca mantener una cobertura mínima de:
- **Controladores**: 80%
- **Servicios**: 85%
- **Utilidades**: 90%

## Buenas Prácticas

1. **Independencia**: Cada prueba debe ser independiente
2. **Nomenclatura Clara**: Los nombres deben ser descriptivos
3. **Mocks sobre Instancias Reales**: Usar mocks para dependencias
4. **Validación Completa**: Verificar tanto el resultado como las interacciones
5. **Casos Límite**: Incluir casos de error y validaciones
6. **Documentación**: Usar JavaDoc para casos complejos

## Ejemplos

### Prueba de Controlador
```java
@ExtendWith(MockitoExtension.class)
class CotizaControllerTest {
    
    @Mock
    private CotizaService cotizaService;
    
    @InjectMocks
    private CotizaController controller;
    
    @Test
    void obtenerTabla_conCodigoValido_retornaTabla() {
        // Arrange
        String codigo = "TABLA_01";
        CotizaTablaResponseDTO expected = new CotizaTablaResponseDTO();
        when(cotizaService.obtenerTabla(codigo)).thenReturn(expected);
        
        // Act
        CotizaTablaResponseDTO result = controller.obtenerTabla(codigo);
        
        // Assert
        assertNotNull(result);
        verify(cotizaService, times(1)).obtenerTabla(codigo);
    }
}
```

### Prueba de Servicio
```java
@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {
    
    @Mock
    private UsuarioPerfilRepository usuarioPerfilRepository;
    
    @Mock
    private InterseguroRestClient interseguroRestClient;
    
    @InjectMocks
    private UsuarioServiceImpl service;
    
    @Test
    void validarUsuario_conCredencialesValidas_retornaToken() {
        // Arrange
        UsuarioIngresoRequestDTO request = UsuarioDTOBuilder.builder()
            .usuario("testuser")
            .password("password123")
            .build();
            
        ObtenerDatosUsuarioResponse azmanResponse = new ObtenerDatosUsuarioResponse();
        azmanResponse.setStatusHttp("200");
        
        when(interseguroRestClient.obtenerDatosUsuario(any()))
            .thenReturn(azmanResponse);
        
        // Act
        UsuarioIngresoResponseDTO result = service.validarUsuario(request);
        
        // Assert
        assertNotNull(result);
        assertEquals(Constantes.CODIGO_RESPUESTA_GENERAL_EXITO, result.getCodigoRespuesta());
        assertNotNull(result.getJwtToken());
    }
}
```

## Mantenimiento

- Actualizar pruebas cuando cambie la lógica de negocio
- Agregar pruebas para nuevos métodos
- Mantener la cobertura de código
- Revisar y refactorizar pruebas obsoletas

## Contacto

Para consultas sobre las pruebas, contactar al equipo de desarrollo TI.


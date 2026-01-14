# Instrucciones de Integración - Pruebas Unitarias

Esta guía te ayudará a integrar las pruebas unitarias creadas en la carpeta `tests/` al proyecto SIV Backend.

## 📋 Prerequisitos

Antes de comenzar, asegúrate de tener:
- ✅ Java 8 o superior
- ✅ Maven 3.6 o superior
- ✅ Acceso al proyecto siv-back-2

## 🚀 Pasos de Integración

### Paso 1: Verificar Estructura de Archivos

La carpeta `tests/` debe estar en la raíz del proyecto con la siguiente estructura:

```
tests/
├── config/                          # Configuraciones base
├── controllers/                     # Pruebas de controladores
├── services/                        # Pruebas de servicios
├── integration/                     # Pruebas de integración
├── utils/                           # Utilidades de pruebas
│   ├── builders/                    # Builders de DTOs
│   └── mocks/                       # Mocks reutilizables
├── README.md
├── CHANGELOG.md
├── INSTRUCCIONES_INTEGRACION.md
├── pom-test-snippet.xml
└── .gitignore
```

### Paso 2: Actualizar pom.xml del módulo siv-admin

1. Abre el archivo `siv-admin/pom.xml`

2. Verifica que las siguientes dependencias de testing estén presentes:

```xml
<dependencies>
    <!-- Ya está en el proyecto -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    
    <!-- Ya está en el proyecto -->
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-junit-jupiter</artifactId>
        <scope>test</scope>
    </dependency>

    <!-- AÑADIR si no existe - JUnit 5 API -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-api</artifactId>
        <version>5.7.0</version>
        <scope>test</scope>
    </dependency>
    
    <!-- AÑADIR si no existe - JUnit 5 Engine -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-engine</artifactId>
        <version>5.7.0</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

3. En la sección `<build><plugins>`, añade o actualiza el plugin de Surefire:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>2.22.2</version>
    <configuration>
        <!-- Directorio adicional de pruebas -->
        <additionalClasspathElements>
            <additionalClasspathElement>${project.basedir}/../tests</additionalClasspathElement>
        </additionalClasspathElements>
        
        <includes>
            <include>**/*Test.java</include>
            <include>**/*Tests.java</include>
        </includes>
        
        <useJUnitPlatform>true</useJUnitPlatform>
    </configuration>
</plugin>
```

4. (Opcional) Añade el plugin JaCoCo para reportes de cobertura:

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.7</version>
    <executions>
        <execution>
            <id>prepare-agent</id>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

### Paso 3: Compilar el Proyecto

```bash
cd siv-back-2
mvn clean compile
```

### Paso 4: Ejecutar las Pruebas

```bash
# Ejecutar todas las pruebas
mvn test

# O solo las pruebas del módulo siv-admin
cd siv-admin
mvn test
```

### Paso 5: Verificar Resultados

Después de ejecutar las pruebas, deberías ver:

```
[INFO] Tests run: 60, Failures: 0, Errors: 0, Skipped: 0
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

### Paso 6: Generar Reporte de Cobertura (Opcional)

```bash
mvn clean test jacoco:report
```

El reporte se generará en: `siv-admin/target/site/jacoco/index.html`

## 🔧 Solución de Problemas Comunes

### Error: "No se encuentra el paquete pe.interseguro.siv.tests"

**Solución**: Asegúrate de que la carpeta `tests/` esté correctamente configurada como source folder en tu IDE.

**IntelliJ IDEA**:
1. Click derecho en la carpeta `tests/`
2. Mark Directory as → Test Sources Root

**Eclipse**:
1. Click derecho en la carpeta `tests/`
2. Build Path → Use as Source Folder

### Error: "Cannot resolve symbol TestConstants"

**Solución**: Los imports deben seguir la estructura de paquetes:
```java
import pe.interseguro.siv.tests.utils.TestConstants;
import pe.interseguro.siv.tests.utils.builders.UsuarioDTOBuilder;
```

### Error: "Method not found" en @Mock o @InjectMocks

**Solución**: Verifica que la versión de Mockito sea compatible con JUnit 5:
- Mockito 3.x o superior
- mockito-junit-jupiter en scope test

### Las pruebas no se ejecutan

**Solución**: 
1. Verifica que las clases de prueba terminen en `Test` o `Tests`
2. Verifica que los métodos de prueba tengan la anotación `@Test`
3. Asegúrate de tener `<useJUnitPlatform>true</useJUnitPlatform>` en Surefire

## 📊 Cobertura de Código

### Visualizar Cobertura

Después de ejecutar `mvn test jacoco:report`, abre el archivo:
```
siv-admin/target/site/jacoco/index.html
```

### Interpretación de Métricas

- **Verde**: Cobertura > 80% ✅
- **Amarillo**: Cobertura 50-80% ⚠️
- **Rojo**: Cobertura < 50% ❌

### Objetivo de Cobertura

- Controladores: 80%+
- Servicios: 85%+
- Utilidades: 90%+

## 🔄 Integración Continua (CI/CD)

### GitHub Actions (Ejemplo)

```yaml
name: Tests

on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK 8
        uses: actions/setup-java@v2
        with:
          java-version: '8'
      - name: Run tests
        run: mvn clean test
      - name: Generate coverage report
        run: mvn jacoco:report
```

### Jenkins (Ejemplo)

```groovy
pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh 'mvn clean test'
            }
        }
        stage('Coverage') {
            steps {
                sh 'mvn jacoco:report'
                publishHTML([
                    reportDir: 'target/site/jacoco',
                    reportFiles: 'index.html',
                    reportName: 'JaCoCo Coverage Report'
                ])
            }
        }
    }
}
```

## 📚 Recursos Adicionales

- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Spring Boot Testing Guide](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing)
- [JaCoCo Documentation](https://www.jacoco.org/jacoco/trunk/doc/)

## ✅ Checklist de Integración

- [ ] Carpeta `tests/` copiada a la raíz del proyecto
- [ ] Dependencias de JUnit 5 añadidas al pom.xml
- [ ] Plugin Surefire configurado
- [ ] Plugin JaCoCo añadido (opcional)
- [ ] Proyecto compilado exitosamente
- [ ] Pruebas ejecutadas exitosamente
- [ ] Reporte de cobertura generado
- [ ] IDE configurado para reconocer la carpeta tests/

## 🆘 Soporte

Si encuentras problemas durante la integración:

1. Revisa los logs de Maven en detalle
2. Verifica que todas las dependencias estén correctamente configuradas
3. Consulta el README.md en la carpeta tests/
4. Contacta al equipo de desarrollo TI

## 📝 Notas Finales

- Estas pruebas están diseñadas para ejecutarse sin dependencias externas
- Los mocks simulan todas las interacciones con bases de datos y servicios externos
- Las pruebas son independientes y pueden ejecutarse en cualquier orden
- La suite completa debería ejecutarse en menos de 30 segundos

---

**Fecha de Creación**: 13 de enero de 2026  
**Versión**: 1.0.0  
**Autor**: Equipo de Desarrollo TI - Interseguro


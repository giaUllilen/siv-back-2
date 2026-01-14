package pe.interseguro.siv.tests.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Configuración base para todas las pruebas unitarias.
 * Esta clase proporciona configuraciones comunes y utilidades para pruebas.
 * 
 * @author ti-is
 */
@ExtendWith(MockitoExtension.class)
public abstract class BaseTestConfig {

    protected static Gson gson;
    protected static MessageSource messageSource;

    /**
     * Configuración inicial que se ejecuta una vez antes de todas las pruebas
     */
    @BeforeAll
    public static void setUpBeforeAll() {
        gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create();

        messageSource = createMessageSource();
    }

    /**
     * Crea un MessageSource para pruebas con los mensajes del sistema
     * 
     * @return MessageSource configurado
     */
    private static MessageSource createMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    /**
     * Convierte un objeto a JSON para debugging
     * 
     * @param obj Objeto a convertir
     * @return String JSON del objeto
     */
    protected String toJson(Object obj) {
        return gson.toJson(obj);
    }

    /**
     * Convierte un JSON string a un objeto de tipo T
     * 
     * @param <T> Tipo del objeto
     * @param json String JSON
     * @param classOfT Clase del tipo T
     * @return Objeto deserializado
     */
    protected <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }
}


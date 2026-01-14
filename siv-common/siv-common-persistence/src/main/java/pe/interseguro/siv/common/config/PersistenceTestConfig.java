package pe.interseguro.siv.common.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
@ComponentScan(basePackages = {
	"pe.interseguro.siv.common.persistence.db.mysql.repository",
	"pe.interseguro.siv.common.persistence.db.postgres.repository",
	"pe.interseguro.siv.common.persistence.db.sitc.repository",
	"pe.interseguro.siv.common.persistence.db.acsele.repository",
	"pe.interseguro.siv.common.persistence.db.crm.repository",
	"pe.interseguro.siv.common.persistence.rest" ,
	"pe.interseguro.siv.common.persistence.soap"
})
@PropertySource("application.properties")
@Profile("uat")
public class PersistenceTestConfig {

	@Bean(name = "messageSource")
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}
	
}
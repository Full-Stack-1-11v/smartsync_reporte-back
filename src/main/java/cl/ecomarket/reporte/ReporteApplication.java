package cl.ecomarket.reporte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Aplicación principal de Ecomarket Reporte.
 * Permite iniciar la aplicación y configurar los componentes necesarios.
 */
@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = "cl.ecomarket.reporte")
public class ReporteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReporteApplication.class, args);
	}

}

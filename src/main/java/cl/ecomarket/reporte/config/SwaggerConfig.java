package cl.ecomarket.reporte.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

/**
 * Clase de configuracion basica de Swagger.
 * Permite modificar el titulo, version y la descripcion.
 * Swagger permite documentar y probar APIs del tipo REST.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                    .info(new Info()
                        .title("Api Reportes")
                        .version("1.0")
                        .description("Documentacion API EcoMarket Reporte"));
    }

}

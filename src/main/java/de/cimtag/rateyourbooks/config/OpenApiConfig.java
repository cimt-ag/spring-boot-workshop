package de.cimtag.rateyourbooks.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Rate Your Books API",
        version = "1.0",
        description = "API for managing books in the Rate Your Books application",
        contact = @Contact(
            name = "Niklas Witzel",
            email = "niklas.witzel@cimt-ag.de",
            url = "https://www.cimt-ag.de"
        ),
        license = @License(
            name = "Apache 2.0",
            url = "http://www.apache.org/licenses/LICENSE-2.0.html"
        )
    )
)
public class OpenApiConfig {

}

package com.multipedidos.clientes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.media.NumberSchema;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.BooleanSchema;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.ObjectSchema;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración de OpenAPI 3.0 para documentación automática de la API REST.
 * Sigue las mejores prácticas del estándar OpenAPI Specification (OAS).
 * 
 * Puerto del Servicio: 8080 (Definido en application.yml)
 * 
 * Estrategia de Puertos:
 * - 8080: Microservicio A (Clientes y Pedidos)
 * - 8081: Microservicio B (Proveedores y Facturación)
 * 
 * Documentación disponible en:
 * - Swagger UI: http://localhost:8080/swagger-ui.html
 * - OpenAPI JSON: http://localhost:8080/api-docs
 */
@Configuration
public class OpenApiConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MultiPedidos API - Gestión de Clientes y Pedidos")
                        .version("1.0.0")
                        .description("""
                                API RESTful para la gestión de clientes y pedidos.
                                
                                Funcionalidades principales:
                                - Registro y consulta de clientes
                                - Creación de pedidos con cálculo automático de totales
                                - Aplicación de descuentos escalonados (5%, 10%, 15%)
                                - Cálculo automático de IVA (15%)
                                
                                Base de Datos: MySQL/MariaDB
                                Puerto del Servicio: 8080
                                
                                Especificación OpenAPI: 3.0.1
                                """)
                        .contact(new Contact()
                                .name("Equipo de Desarrollo - MultiPedidos S.A.")
                                .email("dev@multipedidos.com")
                                .url("https://multipedidos.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort)
                                .description("Servidor de Desarrollo Local"),
                        new Server()
                                .url("http://localhost:" + serverPort + "/api/v1")
                                .description("Servidor de Desarrollo con versionado"),
                        new Server()
                                .url("https://api.multipedidos.com")
                                .description("Servidor de Producción (Pendiente)")
                ))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentación completa del proyecto")
                        .url("https://github.com/multipedidos/docs"))
                .components(new Components()
                        .addSchemas("ClienteDTO", new ObjectSchema()
                                .addProperty("id", new IntegerSchema().format("int64").description("ID único del cliente"))
                                .addProperty("nombre", new StringSchema().description("Nombre completo del cliente"))
                                .addProperty("correo", new StringSchema().format("email").description("Correo electrónico del cliente")))
                        .addSchemas("ClienteInputDTO", new ObjectSchema()
                                .addProperty("nombre", new StringSchema().description("Nombre completo del cliente").example("Juan Pérez"))
                                .addProperty("correo", new StringSchema().format("email").description("Correo electrónico del cliente").example("juan@example.com")))
                        .addSchemas("PedidoDTO", new ObjectSchema()
                                .addProperty("id", new IntegerSchema().format("int64").description("ID único del pedido"))
                                .addProperty("clienteId", new IntegerSchema().format("int64").description("ID del cliente"))
                                .addProperty("productos", new ArraySchema().items(new ObjectSchema()
                                        .addProperty("nombre", new StringSchema().description("Nombre del producto"))
                                        .addProperty("precio", new NumberSchema().format("decimal").description("Precio del producto"))))
                                .addProperty("total", new NumberSchema().format("decimal").description("Total del pedido con descuentos e IVA")))
                        .addSchemas("ProductoDTO", new ObjectSchema()
                                .addProperty("nombre", new StringSchema().description("Nombre del producto"))
                                .addProperty("precio", new NumberSchema().format("decimal").description("Precio del producto")))
                        .addSchemas("PedidoInputDTO", new ObjectSchema()
                                .addProperty("clienteId", new IntegerSchema().format("int64").description("ID del cliente"))
                                .addProperty("productos", new ArraySchema().items(new ObjectSchema()
                                        .addProperty("nombre", new StringSchema().description("Nombre del producto"))
                                        .addProperty("precio", new NumberSchema().format("decimal").description("Precio del producto")))))
                        .addSchemas("ErrorResponse", new ObjectSchema()
                                .addProperty("status", new IntegerSchema().description("Código de estado HTTP"))
                                .addProperty("error", new StringSchema().description("Tipo de error"))
                                .addProperty("message", new StringSchema().description("Mensaje de error"))
                                .addProperty("timestamp", new StringSchema().format("date-time").description("Timestamp del error"))));
    }
}


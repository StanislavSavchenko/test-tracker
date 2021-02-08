package com.test.tracker.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;

import static springfox.documentation.builders.PathSelectors.regex;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.builders.RequestHandlerSelectors.withClassAnnotation;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig implements WebMvcConfigurer {

    @Value("${swagger.base-path}")
    private String swaggerBasePath;

    @Bean
    public Docket api(ServletContext servletContext) {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(apis())
                .paths(paths())
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController(swaggerBasePath + "/v2/api-docs", "/v2/api-docs");
        registry.addRedirectViewController(swaggerBasePath + "/configuration/ui", "/configuration/ui");
        registry.addRedirectViewController(swaggerBasePath + "/configuration/security", "/configuration/security");
        registry.addRedirectViewController(swaggerBasePath + "/swagger-resources", "/swagger-resources");
        registry.addRedirectViewController(swaggerBasePath + "/", "/documentation/swagger-ui.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler(swaggerBasePath + "/**").addResourceLocations("classpath:/META-INF/resources/");
    }

    private Predicate<String> paths() {
        return Predicates.or(
//                regex(".*/" + swaggerBasePath + ".*")
                regex(".*")
        );
    }


    private Predicate<RequestHandler> apis() {
        return Predicates.and(
                basePackage("com.test.tracker.controller"),
                withClassAnnotation(Api.class)
        );
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("", "", "");
        return new ApiInfoBuilder()
                .contact(contact)
                .title("Report Core API")
                .description("")
                .version("1.0")
                .build();
    }


}

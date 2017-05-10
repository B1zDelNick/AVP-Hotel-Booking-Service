package com.avp.booking.config;

import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ErrorPageRegistrar;
import org.springframework.boot.web.servlet.ErrorPageRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration

public class WebConfig extends WebMvcConfigurerAdapter
{
    private static final String VIEWS = "/templates/";

    @Bean
    public ErrorPageRegistrar errorPageRegistrar()
    {
        return new MyErrorPageRegistrar();
    }

    private static class MyErrorPageRegistrar implements ErrorPageRegistrar
    {

        private static final String ERROR_VIEW = "/notFound";

        // Register your error pages and url paths.
        @Override
        public void registerErrorPages(ErrorPageRegistry registry)
        {
            registry.addErrorPages(
                    new ErrorPage(HttpStatus.NOT_FOUND, ERROR_VIEW),
                    new ErrorPage(HttpStatus.BAD_REQUEST, ERROR_VIEW));
        }
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
    {
        configurer.enable();
    }

    /**
     * Handles favicon.ico requests assuring no <code>404 Not Found</code> error is returned.
     */
    @Controller
    static class FaviconController
    {
        @GetMapping("favicon.ico")
        String favicon()
        {
            return "forward:/resources/images/favicon.ico";
        }
    }

    @Configuration
    public static class ThymeleafConfig
    {

        @Bean(name = "templateEngine")
        public TemplateEngine getTemplateEngine()
        {
            TemplateEngine templateEngine = new TemplateEngine();

            templateEngine.addTemplateResolver(getTemplateResolver());

            templateEngine.addDialect(new SpringSecurityDialect());
            templateEngine.addDialect(new Java8TimeDialect());

            return templateEngine;
        }

        private ITemplateResolver getTemplateResolver()
        {
            ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
            templateResolver.setPrefix(VIEWS);
            templateResolver.setSuffix(".html");
            templateResolver.setTemplateMode("HTML");//TemplateMode.TEXT);
            templateResolver.setCharacterEncoding("UTF8");
            templateResolver.setCheckExistence(true);
            templateResolver.setCacheable(false);
            return templateResolver;
        }
    }
}

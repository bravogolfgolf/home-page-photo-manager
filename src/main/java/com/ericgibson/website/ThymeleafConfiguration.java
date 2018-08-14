package com.ericgibson.website;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
public class ThymeleafConfiguration implements WebMvcConfigurer, ApplicationContextAware {

    private static final String UTF8 = "UTF-8";
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public ViewResolver htmlViewResolver() {
        ThymeleafViewResolver resolver = viewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setContentType("text/html");
        resolver.setViewNames(array("*.html"));
        return resolver;
    }

    @Bean
    public ViewResolver cssViewResolver() {
        ThymeleafViewResolver resolver = viewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setContentType("text/css");
        resolver.setViewNames(array("*.css"));
        return resolver;
    }

    @Bean
    public ViewResolver javascriptViewResolver() {
        ThymeleafViewResolver resolver = viewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setContentType("application/javascript");
        resolver.setViewNames(array("*.js"));
        return resolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.addTemplateResolver(htmlTemplateResolver());
        engine.addTemplateResolver(cssTemplateResolver());
        engine.addTemplateResolver(javascriptTemplateResolver());
        return engine;
    }

    private String[] array(String... strings) {
        return strings;
    }

    private ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setCharacterEncoding(UTF8);
        return resolver;
    }

    private SpringResourceTemplateResolver htmlTemplateResolver() {
        SpringResourceTemplateResolver resolver = templateResolver();
        resolver.setOrder(0);
        resolver.setPrefix("classpath:templates/");
        resolver.setTemplateMode(TemplateMode.HTML);
        return resolver;
    }

    private SpringResourceTemplateResolver cssTemplateResolver() {
        SpringResourceTemplateResolver resolver = templateResolver();
        resolver.setOrder(1);
        resolver.setPrefix("classpath:/static/css/");
        resolver.setTemplateMode(TemplateMode.CSS);
        return resolver;
    }

    private SpringResourceTemplateResolver javascriptTemplateResolver() {
        SpringResourceTemplateResolver resolver = templateResolver();
        resolver.setOrder(2);
        resolver.setPrefix("classpath:/static/js/");
        resolver.setTemplateMode(TemplateMode.JAVASCRIPT);
        return resolver;
    }

    private SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(applicationContext);
        resolver.setCheckExistence(true);
        resolver.setCacheable(false);
        return resolver;
    }
}

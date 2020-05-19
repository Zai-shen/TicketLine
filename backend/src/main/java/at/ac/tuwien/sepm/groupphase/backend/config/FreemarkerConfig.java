package at.ac.tuwien.sepm.groupphase.backend.config;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

@Configuration
public class FreemarkerConfig {
    @Bean
    public freemarker.template.Configuration freeMarkerConfiguration() throws IOException {
        freemarker.template.Configuration config = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_30);
        File templatePath = new ClassPathResource("templates/").getFile();
        config.setDirectoryForTemplateLoading(templatePath);
        config.setDefaultEncoding("UTF-8");
        return config;
    }
}
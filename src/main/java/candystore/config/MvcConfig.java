package candystore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${upload.path_user}")
    private String load_user;
    @Value("${upload.path_order}")
    private String load_order;
    @Value("${upload.path_employee}")
    private String load_employee;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img_user/**")
                .addResourceLocations("file://" + load_user + "/");
        registry.addResourceHandler("/img_service/**")
                .addResourceLocations("file://" + load_order + "/");
        registry.addResourceHandler("/img_employee/**")
                .addResourceLocations("file://" + load_employee + "/");
    }
}
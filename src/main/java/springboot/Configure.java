package springboot;


import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springboot.interceptor.Interceptor;

@Configuration
@RequiredArgsConstructor
public class Configure implements WebMvcConfigurer {

    @Value("${filePath}")
    private String uploadPath;

    private final String ajpProtocol = "AJP/1.3";

    private final Interceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/*").excludePathPatterns("/error");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:///"+uploadPath);
    }

    @Bean
    public ServletWebServerFactory servletContainer(){
        TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
        tomcatServletWebServerFactory.addAdditionalTomcatConnectors(creaConnector());
        return tomcatServletWebServerFactory;
    }

    private Connector creaConnector(){
        Connector ajp = new Connector(ajpProtocol);
        ajp.setPort(8085);
        ajp.setSecure(false);
        ajp.setAllowTrace(false);
        ajp.setScheme("http");
        ajp.setProperty("address","0.0.0.0");
        ajp.setProperty("allowedRequestAttributesPattern",".*");
        return ajp;
    }

   /* @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
        registry.addHandler(new BaseService.Websocket(),"/monitor").setAllowedOrigins("*");
    }*/
}

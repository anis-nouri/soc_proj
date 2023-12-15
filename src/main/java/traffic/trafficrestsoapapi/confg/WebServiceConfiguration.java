package traffic.trafficrestsoapapi.confg;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfiguration extends WsConfigurerAdapter {

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/allService/*");
    }

    @Bean(name = "users")
    DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema userSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("allServiceSoapHttp");
        wsdl11Definition.setLocationUri("/allService");
        wsdl11Definition.setTargetNamespace("com.roadguard");
        wsdl11Definition.setSchema(userSchema);
        return wsdl11Definition;
    }


    @Bean
    XsdSchema userSchema() {
        return new SimpleXsdSchema(new ClassPathResource("User.xsd"));
    }

}
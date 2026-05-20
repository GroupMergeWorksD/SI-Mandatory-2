package com.groupmergeworks.sirmeows.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig {

    private static final String NAMESPACE_URI = "http://groupmergeworks.com/sirmeows/equipment-reservation-soap-api";

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        var servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);

        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "equipment-reservation-soap-api")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema equipmentReservationSchema) {
        var definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("EquipmentReservationPort");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace(NAMESPACE_URI);
        definition.setSchema(equipmentReservationSchema);

        return definition;
    }

    @Bean
    public XsdSchema equipmentReservationSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/equipment-reservation-soap-api.xsd"));
    }
}

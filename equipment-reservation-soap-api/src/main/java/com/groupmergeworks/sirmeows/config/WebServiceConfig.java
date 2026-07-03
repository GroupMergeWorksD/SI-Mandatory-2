package com.groupmergeworks.sirmeows.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        var servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);

        return new ServletRegistrationBean<>(servlet, SoapConstants.WS_PATH_PATTERN);
    }

    @Bean(name = SoapConstants.API_NAME)
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema equipmentReservationSchema) {
        var definition = new DefaultWsdl11Definition();
        definition.setPortTypeName(SoapConstants.PORT_TYPE_NAME);
        definition.setLocationUri(SoapConstants.WS_PATH);
        definition.setTargetNamespace(SoapConstants.NAMESPACE_URI);
        definition.setSchema(equipmentReservationSchema);

        return definition;
    }

    @Bean
    public XsdSchema equipmentReservationSchema() {
        return new SimpleXsdSchema(new ClassPathResource(SoapConstants.XSD_PATH));
    }

    @Bean
    public Jaxb2Marshaller faultMarshaller() {
        var marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath(SoapConstants.SOAP_PACKAGE);
        return marshaller;
    }

    @Bean
    public SoapFaultExceptionResolver soapFaultExceptionResolver(Jaxb2Marshaller faultMarshaller) {
        var resolver = new SoapFaultExceptionResolver(faultMarshaller);
        resolver.setOrder(0);
        return resolver;
    }
}
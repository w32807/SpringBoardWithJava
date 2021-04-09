package com.zerock.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.commons.CommonsFileUploadSupport;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

//servlet-context.xml�� ������ ��� ������ ��� Ŭ����
@EnableWebMvc // ���⼭�� @EnableWebMvc ������̼� + WebMvcConfigurer �������̽� ����� �޾Ƽ� �����
@ComponentScan(basePackages = {"com.zerock.controller"})
public class ServletConfig implements WebMvcConfigurer{

    @Override
    // viewResolver ����
    /*
        <beans:bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <beans:property name="prefix" value="/WEB-INF/views/" />
        <beans:property name="suffix" value=".jsp" />
    </beans:bean> �� ����
      */
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/views/");
        bean.setSuffix(".jsp");
        registry.viewResolver(bean);
    }
    
    @Override
    // ���� ���ҽ� ����
    // <resources mapping="/resources/**" location="/resources/" /> �� ����
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
    /*
    // ���� ���ε�
    @Bean(name = "multipartResolver") // �޼ҵ带 ����� ���� Bean�� �������ش�
    public CommonsFileUploadSupport getResolver() throws IOException{
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        
        resolver.setMaxUploadSize(1024 * 1024 * 10);
        resolver.setMaxUploadSizePerFile(1024 * 1024 * 2);
        resolver.setMaxInMemorySize(1024 * 1024);
        resolver.setUploadTempDir(new FileSystemResource("C:\\upload\\tmp"));
        resolver.setDefaultEncoding("UTF-8");
        return resolver;
    }
    */
}

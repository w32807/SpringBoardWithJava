package com.zerock.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


// ����� web.xml�� �����Ǵ� Ŭ����
public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer{
    /*
    1. �������� ���۵Ǹ� ���� ApplicationContext��� �̸��� ��ü�� �������.
    2. �� ���� �������� ��ü�� �����ϰ� �����ؾ��ϴ� ��ü�鿡 ���� ������ �ʿ��� (root-context.xml����, Ȥ�� getRootConfigClasses�� ��ȯ���� �Ǵ� Ŭ����)
    3. ���� ���� �������� �ٸ� Ŭ������ ����� �����Ѵٸ�, return ���� ���� Ŭ������ ���ָ� ��
    */
    @Override
    // root-context.xml�� ����ϴ� �޼ҵ�
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {RootConfig.class}; 
        // ���� root-context.xml�� ������ �ۼ��� Ŭ������ ���� (���÷����� �̿��� Ŭ���� ����)
        // root-context���� �ڹ� ���� (DB , Bean scan ���� ������ �ۼ�)
    }

    @Override
    // servlet-context.xml�� ����ϴ� �޼ҵ�
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {ServletConfig.class};
        // ���� servlet-context.xml�� ������ �ۼ��� Ŭ������ ���� (���÷����� �̿��� Ŭ���� ����)
        // servlet-context���� �� ���� ������ �ۼ�
    }

    @Override
    /* web.xml��   
    <servlet-mapping>
        <servlet-name>appServlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping> �� ����ϴ� �޼ҵ�*/
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
    
    /* web.xml��   
        <init-param>
          <param-name>throwExceptionIfNoHandlerFound</param-name>
          <param-value>true</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup> �� ����ϴ� �޼ҵ�*/
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
        // ���� ���ε� ���� (������ ���� ��ǻ���� ����� ������ ����ȴ�.
        MultipartConfigElement multipartConfig = new MultipartConfigElement("C:\\upload\\tmp", 20971520, 41943040, 20971520);
        registration.setMultipartConfig(multipartConfig);
    }

    /*  <!-- �ѱ� ���� -->
    <filter>
        <filter-name>encoding</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encoding</filter-name>
        <servlet-name>appServlet</servlet-name>
    </filter-mapping>
    <!-- �ѱۼ��� END -->*/
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        
        return new Filter[] {characterEncodingFilter};
    }
}

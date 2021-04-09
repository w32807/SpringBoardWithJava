package com.zerock.config;

import javax.servlet.Filter;
import javax.servlet.ServletRegistration;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


// 여기는 web.xml과 대응되는 클래스
public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer{
    /*
    1. 스프링이 시작되면 먼저 ApplicationContext라는 이름의 객체가 만들어짐.
    2. 그 다음 스프링이 객체를 관리하고 생성해야하는 객체들에 대한 설정이 필요함 (root-context.xml파일, 혹은 getRootConfigClasses의 반환값이 되는 클래스)
    */
    @Override
    // root-context.xml을 대신하는 메소드
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {RootConfig.class}; 
        // 실제 root-context.xml의 내용이 작성될 클래스를 리턴 (리플렉션을 이용한 클래스 생성)
        // root-context에는 자바 설정 (DB , Bean scan 등의 내용을 작성)
    }

    @Override
    // servlet-context.xml을 대신하는 메소드
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {ServletConfig.class};
        // 실제 servlet-context.xml의 내용이 작성될 클래스를 리턴 (리플렉션을 이용한 클래스 생성)
        // servlet-context에는 웹 관련 설정을 작성
    }

    @Override
    /* web.xml의   
    <servlet-mapping>
        <servlet-name>appServlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping> 을 대신하는 메소드*/
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
    
    /* web.xml의   
        <init-param>
          <param-name>throwExceptionIfNoHandlerFound</param-name>
          <param-value>true</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup> 을 대신하는 메소드*/
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
    }

    /*  <!-- 한글 필터 -->
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
    <!-- 한글설정 END -->*/
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        
        return new Filter[] {characterEncodingFilter};
    }

    
    
}

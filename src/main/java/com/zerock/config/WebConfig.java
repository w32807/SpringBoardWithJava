package com.zerock.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


// 여기는 web.xml과 대응되는 클래스
public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer{
    /*
    1. 스프링이 시작되면 먼저 ApplicationContext라는 이름의 객체가 만들어짐.
    2. 그 다음 스프링이 객체를 관리하고 생성해야하는 객체들에 대한 설정이 필요함 (root-context.xml파일, 혹은 getRootConfigClasses의 반환값이 되는 클래스)
    3. 만약 여러 설정들을 다른 클래스로 나누어서 관리한다면, return 값에 여러 클래스를 써주면 됨
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
        // 파일 업로드 설정 (파일은 서버 컴퓨터의 어딘가의 폴더에 저장된다.
        MultipartConfigElement multipartConfig = new MultipartConfigElement("C:\\upload\\tmp", 20971520, 41943040, 20971520);
        registration.setMultipartConfig(multipartConfig);
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

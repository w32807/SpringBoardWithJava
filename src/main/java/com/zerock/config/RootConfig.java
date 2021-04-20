package com.zerock.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zerock.utils.StringBox;

@Configuration // xml 대신 설정파일로 만들고자 하는 클래스에 붙이는 어노테이션
// root-context의 <context:component-scan base-package="org.zerock.sample"/> Element와 동일
//@ComponentScan(basePackages = {"org.zerock.sample", "다른 패키지..."}) 형식으로 여러 패키지를 동시에 작성할 수 있음 (중괄호 사용)
@ComponentScan(basePackages = {"com.zerock.service", "com.zerock.task", "com.zerock.aop"})
@MapperScan(basePackages = {"com.zerock.mapper"})
@EnableAspectJAutoProxy
@EnableScheduling
@EnableTransactionManagement
public class RootConfig {
	
	@Value("#{global['db.driver']}") 
	private String dbDriver;
	
	@Value("#{global['db.url']}") 
	private String dbUrl;
	
	@Value("#{global['db.username']}") 
	private String dbUser;
	
	@Value("#{global['db.password']}") 
	private String dbPassword;

    // Hikari CP를 이용한 DataSource Bean 생성
    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setDriverClassName(dbDriver);
        hikariConfig.setJdbcUrl(dbUrl);
        hikariConfig.setUsername(dbUser);
        hikariConfig.setPassword(dbPassword);
        return new HikariDataSource(hikariConfig);
    }
    
    @Bean
    // mybatis 관련 설정으로 sqlSession을 생성
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        return (SqlSessionFactory) sessionFactory.getObject(); // Object를 얻어 형변환
    }
    
    @Bean
    // 트랜잭션 처리
    public DataSourceTransactionManager txManager() {
    	return new DataSourceTransactionManager(dataSource());
    }
    
    // properties 파일 관리를 위한 Bean 등록
    // Junit과 tomcat의 RootConfig, ServletConfig의 실행순서가 다른 지는 몰라도, @Value와 같은 파일에 작성하지 않으면
    // Junit에서는 DB연결이 되나, tomcat에서는 안되는 상황이 발생할 수 있다. 
    @Bean(name = "global")
    public PropertiesFactoryBean properties() {
    	PropertiesFactoryBean bean = new PropertiesFactoryBean();
    	bean.setLocation(new ClassPathResource("com/zerock/properties/global.properties"));
    	bean.setFileEncoding(StringBox.UTF_8);
    	return bean;
    }
}

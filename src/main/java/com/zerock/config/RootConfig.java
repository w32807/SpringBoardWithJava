package com.zerock.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration // xml 대신 설정파일로 만들고자 하는 클래스에 붙이는 어노테이션
// root-context의 <context:component-scan base-package="org.zerock.sample"/> Element와 동일
//@ComponentScan(basePackages = {"org.zerock.sample", "다른 패키지..."}) 형식으로 여러 패키지를 동시에 작성할 수 있음 (중괄호 사용)
@ComponentScan(basePackages = {"com.zerock.service"})
@MapperScan(basePackages = {"com.zerock.mapper"})
public class RootConfig {
    
    // Hikari CP를 이용한 DataSource Bean 생성
    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        // hikariConfig.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        // hikariConfig.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:orcl");
        hikariConfig.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
        hikariConfig.setJdbcUrl("jdbc:log4jdbc:oracle:thin:@localhost:1521:orcl");
        hikariConfig.setUsername("c##book_ex");
        hikariConfig.setPassword("1234");
        
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
}

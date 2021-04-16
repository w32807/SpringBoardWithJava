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

@Configuration // xml ��� �������Ϸ� ������� �ϴ� Ŭ������ ���̴� ������̼�
// root-context�� <context:component-scan base-package="org.zerock.sample"/> Element�� ����
//@ComponentScan(basePackages = {"org.zerock.sample", "�ٸ� ��Ű��..."}) �������� ���� ��Ű���� ���ÿ� �ۼ��� �� ���� (�߰�ȣ ���)
@ComponentScan(basePackages = {"com.zerock.service"})
@MapperScan(basePackages = {"com.zerock.mapper"})
public class RootConfig {
    
    // Hikari CP�� �̿��� DataSource Bean ����
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
    // mybatis ���� �������� sqlSession�� ����
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        return (SqlSessionFactory) sessionFactory.getObject(); // Object�� ��� ����ȯ
    }
    
    @Bean
    // Ʈ����� ó��
    public DataSourceTransactionManager txManager() {
    	return new DataSourceTransactionManager(dataSource());
    }
}

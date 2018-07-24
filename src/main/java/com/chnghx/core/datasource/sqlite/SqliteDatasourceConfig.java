//package com.chnghx.core.datasource.sqlite;
//
//import javax.sql.DataSource;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import com.alibaba.druid.pool.DruidDataSource;
//
//@Configuration
//@MapperScan(basePackages = "com.chnghx.dao.sqlite.mapper", sqlSessionTemplateRef = "sqliteSqlSessionTemplate") //mapper的包路径
//public class SqliteDatasourceConfig {
//
//	@Bean(name = "sqliteDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.sqlite")
//    public DataSource sqliteDataSource() {
////        return DataSourceBuilder.create().build();
//		return new DruidDataSource();
//    }
//
//    @Bean(name = "sqliteSqlSessionFactory")
//    public SqlSessionFactory sqliteSqlSessionFactory(@Qualifier("sqliteDataSource") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/sqlite/*.xml"));
//        return bean.getObject();
//    }
//
//    @Bean(name = "sqliteTransactionManager")
//    public DataSourceTransactionManager sqliteTransactionManager(@Qualifier("sqliteDataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean(name = "sqliteSqlSessionTemplate")
//    public SqlSessionTemplate sqliteSqlSessionTemplate(@Qualifier("sqliteSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//}

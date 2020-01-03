package com.pan.love.framework.config.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;


import javax.sql.DataSource;

/**
 * 数据库配置
 *
 * @author pan
 * @date 2019/10/15
 * TODO: 为什么basePackages="com.pan.love.*"  service层访问不到？待解决
 */
@Configuration
@MapperScan(basePackages = "com.pan.love.**.mapper" ,sqlSessionFactoryRef = "loveSqlSessionFactory")
public class MybatisDataSourceConfig {

    @Value("${spring.datasource.love.url}")
    private String url;

    @Value("${spring.datasource.love.driver-class-name}")
    private String driver;

    @Value("${spring.datasource.love.username}")
    private String username;

    @Value("${spring.datasource.love.password}")
    private String password;

    @Bean(value = "loveDataSource")
    @Primary
    public DataSource dataSource(){
        DruidDataSource dataSource=new DruidDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    @Primary
    public JdbcTemplate jdbcTemplate(@Qualifier(value = "loveDataSource") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "loveSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier(value = "loveDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        //实体类包名
        sqlSessionFactoryBean.setTypeAliasesPackage("com.pan.love");

        //读取mapper的xml文件
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver=new PathMatchingResourcePatternResolver();
        Resource[] resources=pathMatchingResourcePatternResolver.getResources("classpath:/mapper/*.xml");
        sqlSessionFactoryBean.setMapperLocations(resources);

        //开启驼峰命名法
        sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);

        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 事务管理器
     *
     * @author pan
     * @date 2019/10/16
     */
    @Bean
    @Primary
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier(value = "loveDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

}

package org.example.authservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.example.authservice.entity.Role;
import org.example.authservice.repository.RoleRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

@SpringBootApplication
@OpenAPIDefinition
public class AuthServiceApplication {

    public static void main(String[] args) throws SQLException {
       ApplicationContext ctx =  SpringApplication.run(AuthServiceApplication.class, args);

       // testing only
        ClassPathResource classPathResource = new ClassPathResource("init_sql_testing_only.sql");
        if (classPathResource.exists()) {
            DataSource dataSource = ctx.getBean(DataSource.class);
            ScriptUtils.executeSqlScript(dataSource.getConnection(), classPathResource);
        }

    }

}

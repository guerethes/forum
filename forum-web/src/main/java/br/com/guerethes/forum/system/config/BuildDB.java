package br.com.guerethes.forum.system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;

@Configuration
public class BuildDB {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void initDb() {
        Long total = jdbcTemplate.queryForObject("SELECT COALESCE(COUNT(*),0) FROM forum.role", Long.class);
        if ( total == 0 ) {
            //Create Role
            jdbcTemplate.update("INSERT INTO forum.role VALUES (nextval('forum.role_seq'), 'ADMIN');");

            //Create Category
            jdbcTemplate.update("INSERT INTO forum.category VALUES (nextval('forum.category_seq'), 'JAVA');");
            jdbcTemplate.update("INSERT INTO forum.category VALUES (nextval('forum.category_seq'), 'Python');");
            jdbcTemplate.update("INSERT INTO forum.category VALUES (nextval('forum.category_seq'), 'Spring');");
            jdbcTemplate.update("INSERT INTO forum.category VALUES (nextval('forum.category_seq'), 'C++');");
            jdbcTemplate.update("INSERT INTO forum.category VALUES (nextval('forum.category_seq'), 'C#');");
            jdbcTemplate.update("INSERT INTO forum.category VALUES (nextval('forum.category_seq'), 'NodeJS');");
        }
    }

}
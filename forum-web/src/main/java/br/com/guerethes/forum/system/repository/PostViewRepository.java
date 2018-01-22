package br.com.guerethes.forum.system.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PostViewRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void addView(Long idPost) {
        Long total = jdbcTemplate.queryForObject("select coalesce(count, 0) as total from forum.post_view  where post_id = " + idPost, Long.class);
        if ( total == 0 ) {
            jdbcTemplate.update("INSERT INTO forum.post_view VALUES (nextval('forum.post_view_seq'), 1, ?)", idPost);
        } else {
            jdbcTemplate.update("UPDATE forum.post_view SET count = ? WHERE post_id = ?", ++total, idPost);
        }
    }

}
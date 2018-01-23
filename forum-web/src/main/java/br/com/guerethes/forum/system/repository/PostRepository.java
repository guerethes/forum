package br.com.guerethes.forum.system.repository;

import br.com.guerethes.forum.architecture.model.User;
import br.com.guerethes.forum.system.model.Post;
import br.com.guerethes.forum.system.model.dto.SearchPost;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class PostRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @PersistenceContext
    EntityManager entityManager;

    public List<Post> findPostByUser(String email) {
        String sql = "SELECT p.*, u.* FROM forum.post p " +
                " JOIN forum.user u on ( p.user_id = u.user_id ) " +
                " WHERE u.email = '" + email + "'" +
                " ORDER BY p.created desc";

        return jdbcTemplate.query(sql, new RowMapper<Post>() {
            public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
                Post post = new Post();
                post.setId(rs.getLong("post_id"));
                post.setCreated( rs.getTimestamp("created") );
                post.setPost( rs.getString("post") );
                post.setUser(new User());
                post.getUser().setId( rs.getLong("user_id") );
                post.getUser().setLastName(rs.getString("last_name"));
                post.getUser().setName(rs.getString("name"));
                post.setTitle(rs.getString("title"));
                return post;
            }
        });
    }

    public List<Post> findByQuery(SearchPost searchPost) {
            String sql = "SELECT p.*, u.* " +
                " FROM forum.post p " +
                " JOIN forum.user u on ( p.user_id = u.user_id ) " +
                " WHERE 1 = 1 ";

            if ( searchPost.getQuery() != null && !searchPost.getQuery().isEmpty() )
                sql += " AND ( p.post ilike '%" + searchPost.getQuery() + "%' or " +
                        " p.title ilike '%" + searchPost.getQuery() + "%' ) ";

            if ( searchPost.getCategory() != null )
                sql += " AND p.category_id = " + searchPost.getCategory();

            sql += " ORDER BY p.created desc";

        return jdbcTemplate.query(sql, new RowMapper<Post>() {
            public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
                Post post = new Post();
                post.setId(rs.getLong("post_id"));
                post.setCreated( rs.getTimestamp("created") );
                post.setTitle(rs.getString("title"));
                post.setPost( rs.getString("post") );
                post.setUser(new User());
                post.getUser().setId( rs.getLong("user_id") );
                post.getUser().setName( rs.getString("name") );
                post.getUser().setLastName( rs.getString("last_name") );
                return post;
            }
        });
    }

}
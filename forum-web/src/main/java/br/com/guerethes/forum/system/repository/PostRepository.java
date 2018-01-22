package br.com.guerethes.forum.system.repository;

import br.com.guerethes.forum.architecture.model.User;
import br.com.guerethes.forum.system.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class PostRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Post> findPostByUser(String email) {
        String sql = "SELECT p.* FROM forum.post p " +
                " JOIN forum.user u on ( p.user_id = u.user_id ) " +
                " WHERE u.email = '" + email + "' and post_thread_id is null" +
                " ORDER BY p.created desc";

        return jdbcTemplate.query(sql, new RowMapper<Post>() {
            public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
                Post post = new Post();
                post.setId(rs.getLong("post_id"));
                post.setCreated( rs.getDate("created") );
                post.setPost( rs.getString("post") );
                post.setThread(new Post());
                post.getThread().setId(rs.getLong("post_thread_id"));
                post.setUser(new User());
                post.getUser().setId( rs.getLong("user_id") );
                post.setTitle(rs.getString("title"));
                return post;
            }
        });
    }

    public List<Post> findPostByThread(Long idPost) {
        String sql = "SELECT p.*, u.* " +
                " FROM forum.post p " +
                " JOIN forum.user u on ( p.user_id = u.user_id ) " +
                " WHERE post_thread_id = " + idPost +
                " ORDER BY p.created desc";

        return jdbcTemplate.query(sql, new RowMapper<Post>() {
            public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
                Post post = new Post();
                post.setId(rs.getLong("post_id"));
                post.setCreated( rs.getDate("created") );
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

    public List<Post> findPostByAtive() {
        String sql = "SELECT p.*, coalesce(pv.count, 0) as total_acesso, " +
                "( select count(*) from forum.post where post_thread_id = p.post_id ) as answer" +
                " FROM forum.post p " +
                " JOIN forum.user u on ( p.user_id = u.user_id ) " +
                " LEFT JOIN forum.post_view pv on ( p.post_id = pv.post_id ) " +
                " WHERE post_thread_id is null" +
                " ORDER BY p.created desc";

        return jdbcTemplate.query(sql, new RowMapper<Post>() {
            public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
                Post post = new Post();
                post.setId(rs.getLong("post_id"));
                post.setCreated( rs.getDate("created") );
                post.setPost( rs.getString("post") );
                post.setThread(new Post());
                post.getThread().setId(rs.getLong("post_thread_id"));
                post.setUser(new User());
                post.getUser().setId( rs.getLong("user_id") );
                post.setTitle(rs.getString("title"));
                post.setView( rs.getLong("total_acesso") );
                post.setAnswer( rs.getLong("answer") );
                return post;
            }
        });
    }

}
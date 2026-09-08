package job.repository.sql;

import job.domain.User;
import job.domain.Vacancy;
import job.repository.JobRepository;
import job.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.SqlArrayValue;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SqlUserRepository implements UserRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<User> rowmapper = (rs, rowNum) -> new User(rs.getString("name"), List.of((String[]) rs.getArray("skills").getArray()), rs.getInt("experience"));

    @Override
    public void addUser(User user) {
        String sql = """
                    INSERT INTO users
                    (name, skills, experience)
                    VALUES (:name, :skills, :experience)
                """;
        var params = new MapSqlParameterSource().addValue("name", user.getName()).addValue("skills", new SqlArrayValue("text", user.getSkills().toArray())).addValue("experience", user.getExperience());
        jdbcTemplate.update(sql, params);
    }

    @Override
    public List<User> getAllUsers() {
        String sql = """
                    SELECT * FROM users
                """;
        return jdbcTemplate.query(sql, rowmapper);
    }

    @Override
    public User getUser(String name) {
        String sql = """
                    SELECT * FROM users WHERE name = :name
                """;
        var params = new MapSqlParameterSource().addValue("name", name);
        return jdbcTemplate.queryForObject(sql, params, rowmapper);
    }
}

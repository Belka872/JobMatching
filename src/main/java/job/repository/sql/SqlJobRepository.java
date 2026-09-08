package job.repository.sql;

import job.domain.Vacancy;
import job.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.SqlArrayValue;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SqlJobRepository implements JobRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Vacancy> rowMapper = (rs, rowNum) -> new Vacancy(rs.getString("name"), rs.getString("company"), List.of((String[]) rs.getArray("tags").getArray()), rs.getInt("experience"));


    @Override
    public void addVacancy(Vacancy vacancy) {
        String sql = """
                    INSERT INTO vacancies
                    (name, company, tags, experience)
                    VALUES (:nameVacancy, :nameCompany, :tags, :needExperience)
                """;
        var params = new MapSqlParameterSource().addValue("nameVacancy", vacancy.getNameVacancy()).addValue("nameCompany", vacancy.getNameCompany()).addValue("tags", new SqlArrayValue("text", vacancy.getTags().toArray())).addValue("needExperience", vacancy.getNeedExperience());
        jdbcTemplate.update(sql, params);
    }

    @Override
    public List<Vacancy> getAllVacancies() {
        String sql = """
                SELECT * FROM vacancies
                """;
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Vacancy getVacancy(String nameVacancy) {
        String sql = """
                SELECT * FROM vacancies WHERE name = :nameVacancy
                """;
        var params = new MapSqlParameterSource().addValue("nameVacancy", nameVacancy);
        return jdbcTemplate.queryForObject(sql, params, rowMapper);
    }
}

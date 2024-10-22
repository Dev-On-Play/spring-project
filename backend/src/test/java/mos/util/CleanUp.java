package mos.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import jakarta.transaction.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CleanUp {
    private final JdbcTemplate jdbcTemplate;
    private final EntityManager entityManager;

    public CleanUp(JdbcTemplate jdbcTemplate, jakarta.persistence.EntityManager entityManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.entityManager = entityManager;
    }

    @Transactional
    public void all() {
        List<String> tables = entityManager.getMetamodel().getEntities().stream()
                .map(EntityType::getName)
                .map(CleanUp::convertCamelToSnake)
                .toList();

        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");

        tables.forEach(table -> {
            jdbcTemplate.execute(String.format("truncate table %s", table));
        });

        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }

    private static String convertCamelToSnake(String camelCase) {
        if (camelCase == null || camelCase.isEmpty()) {
            return camelCase;
        }

        // 정규 표현식을 사용하여 대문자 앞에 밑줄을 추가하고 소문자로 변환합니다.
        return camelCase.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }
}

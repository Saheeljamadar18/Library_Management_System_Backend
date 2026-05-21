package saheel_library_management.project.Library_Management_System.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Ensures card.created_date and card.updated_date exist (Render/production DBs
 * created before these columns were added).
 */
@Component
public class CardSchemaMigration implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(CardSchemaMigration.class);

    private final JdbcTemplate jdbcTemplate;

    public CardSchemaMigration(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        addColumnIfMissing("created_date",
                "DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)");
        addColumnIfMissing("updated_date",
                "DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)");
    }

    private void addColumnIfMissing(String columnName, String columnDefinition) {
        if (columnExists("card", columnName)) {
            return;
        }
        try {
            jdbcTemplate.execute(
                    "ALTER TABLE card ADD COLUMN " + columnName + " " + columnDefinition);
            log.info("Added card.{}", columnName);
        } catch (Exception e) {
            log.warn("Could not add card.{}: {}", columnName, e.getMessage());
        }
    }

    private boolean columnExists(String tableName, String columnName) {
        Integer count = jdbcTemplate.queryForObject(
                """
                SELECT COUNT(*) FROM information_schema.COLUMNS
                WHERE TABLE_SCHEMA = DATABASE()
                  AND TABLE_NAME = ?
                  AND COLUMN_NAME = ?
                """,
                Integer.class,
                tableName,
                columnName);
        return count != null && count > 0;
    }
}

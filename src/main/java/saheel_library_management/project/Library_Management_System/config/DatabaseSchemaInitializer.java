package saheel_library_management.project.Library_Management_System.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;

/**
 * Creates core tables when connecting to an empty database (e.g. fresh Render MySQL).
 */
@Component
public class DatabaseSchemaInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DatabaseSchemaInitializer.class);

    private final JdbcTemplate jdbcTemplate;

    public DatabaseSchemaInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (tableExists("student")) {
            log.debug("Database schema already present, skipping initialization");
            return;
        }

        log.info("Empty database detected — creating library management tables");
        String sql = StreamUtils.copyToString(
                new ClassPathResource("schema.sql").getInputStream(),
                StandardCharsets.UTF_8);

        for (String statement : sql.split(";")) {
            String trimmed = statement.trim();
            if (!trimmed.isEmpty() && !trimmed.startsWith("--")) {
                jdbcTemplate.execute(trimmed);
            }
        }
        log.info("Database schema initialized successfully");
    }

    private boolean tableExists(String tableName) {
        Integer count = jdbcTemplate.queryForObject(
                """
                SELECT COUNT(*) FROM information_schema.TABLES
                WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = ?
                """,
                Integer.class,
                tableName);
        return count != null && count > 0;
    }
}

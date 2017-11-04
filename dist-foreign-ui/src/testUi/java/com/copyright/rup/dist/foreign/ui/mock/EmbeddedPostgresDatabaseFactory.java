package com.copyright.rup.dist.foreign.ui.mock;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.test.integ.db.embedded.AbstractEmbeddedDatabaseFactory;

import de.flapdoodle.embed.process.distribution.IVersion;

import liquibase.database.Database;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;

import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;
import ru.yandex.qatools.embed.postgresql.PostgresProcess;
import ru.yandex.qatools.embed.postgresql.util.SocketUtil;

/**
 * Configurable replacement for factory.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: Oct 31, 2017
 *
 * @author Pavel_Liakh
 * <p>
 * TODO {pavelliakh} class should be removed after
 * {@link com.copyright.rup.common.test.integ.db.embedded.EmbeddedPostgresDatabaseFactory} will be updated.
 * </p>
 */
public class EmbeddedPostgresDatabaseFactory extends AbstractEmbeddedDatabaseFactory implements InitializingBean {
    private static final Logger LOGGER = RupLogUtils.getLogger();

    private static final String DRIVER = "org.postgresql.Driver";
    private static final String DATABASE_URL_PATTERN = "jdbc:postgresql://%s:%d/%s";

    @Value("${user.home}")
    private String userHome;
    @Value("${dbRupDatabase}")
    private String rupDatabase;
    @Value("${dbRupUsername}")
    private String rupUsername;
    @Value("${dbRupPassword}")
    private String rupPassword;
    @Value("${dbPidLocation}")
    private String pidLocation;
    private String version = "9.4.4-1";
    private EmbeddedPostgres postgres;
    private String url = null;
    private Database database = null;
    private Integer port;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String databaseUrl) {
        this.url = databaseUrl;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    protected void applyDatabaseProperties(Properties dBproperties) {
        super.setDataBaseProperties(dBproperties);
    }

    @Override
    protected String getDriverClassName() {
        return DRIVER;
    }

    @Override
    protected Database getLiquibaseDatabase() {
        try {
            if (null == database || database.getConnection().isClosed()) {
                database = new PostgresDatabase();
                database.setConnection(new JdbcConnection(getConnection()));
            }
        } catch (DatabaseException e) {
            LOGGER.warn(e.getMessage());
        }
        return database;
    }

    @Override
    public void destroy() {
        try {
            database.close();
        } catch (DatabaseException e) {
            LOGGER.warn("Can't close connection", e);
        }
        postgres.stop();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        postgres = new EmbeddedPostgres(new IVersion() {
            @Override
            public String asInDownloadPath() {
                return version;
            }
        });
        try {
            // find free port if no one was configured
            if (port == null) {
                port = SocketUtil.findFreePort();
            }

            LOGGER.info("Attempting to start embedded Postgres");
            url = postgres.start(EmbeddedPostgres.cachedRuntimeConfig(
                    Paths.get(StringUtils.join(userHome, "/.embedpostgresql/cache"))),
                    EmbeddedPostgres.DEFAULT_HOST, port, EmbeddedPostgres.DEFAULT_DB_NAME,
                    EmbeddedPostgres.DEFAULT_USER, EmbeddedPostgres.DEFAULT_PASSWORD,
                    Arrays.asList("-E", "'UTF-8'"));
            savePid();
            url = String.format(DATABASE_URL_PATTERN, EmbeddedPostgres.DEFAULT_HOST, port,
                    EmbeddedPostgres.DEFAULT_DB_NAME);
            setUserName(EmbeddedPostgres.DEFAULT_USER);
            setPassword(EmbeddedPostgres.DEFAULT_PASSWORD);
            liquibaseInit(getLiquibaseDatabase());
            database.close();
            url = String.format(DATABASE_URL_PATTERN, EmbeddedPostgres.DEFAULT_HOST, port, rupDatabase);
            LOGGER.info("Embedded Postgres url - {}", url);
            setUserName(rupUsername);
            setPassword(rupPassword);
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
            database.close();
            postgres.stop();
        }
    }

    /**
     * Saves pid of the main postgres process in temp file.
     */
    private void savePid() {
        try {
            Field process = postgres.getClass().getDeclaredField("process");
            process.setAccessible(true);
            PostgresProcess proc = (PostgresProcess) process.get(postgres);
            long pid = proc.getProcessId();
            process.setAccessible(false);
            FileUtils.writeStringToFile(new File(pidLocation), String.valueOf(pid));
        } catch (IOException e) {
            LOGGER.warn("Can't write PID to " + pidLocation, e);
        } catch (IllegalAccessException e) {
            LOGGER.warn("Can't access PID", e);
        } catch (NoSuchFieldException e) {
            LOGGER.warn("Can't reflect to EmbedPostgres field", e);
        }
    }
}

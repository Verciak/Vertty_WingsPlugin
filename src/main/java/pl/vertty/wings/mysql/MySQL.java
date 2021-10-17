package pl.vertty.wings.mysql;

import com.zaxxer.hikari.HikariDataSource;
import pl.vertty.wings.config.LoaderConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

public class MySQL
{
    public static final HikariDataSource dataSource = new HikariDataSource();

    public MySQL() {
        String host = LoaderConfig.host;
        String base = LoaderConfig.name;
        String user = LoaderConfig.user;
        String password = LoaderConfig.pass;
        int port = LoaderConfig.port;
        this.dataSource.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + base);
        this.dataSource.setUsername(user);
        this.dataSource.setPassword(password);
        this.dataSource.addDataSourceProperty("cachePrepStmts", Boolean.valueOf(true));
        this.dataSource.addDataSourceProperty("prepStmtCacheSize", Integer.valueOf(250));
        this.dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", Integer.valueOf(2048));
        this.dataSource.addDataSourceProperty("useServerPrepStmts", Boolean.valueOf(true));
        this.dataSource.addDataSourceProperty("rewriteBatchedStatements", Boolean.valueOf(true));
        this.dataSource.setConnectionTimeout(15000L);
        this.dataSource.setMaximumPoolSize(5);
    }

    public void close() {
        this.dataSource.close();
    }

    public static ResultSet query(String query) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeQuery();
        } catch (SQLException e) {
            System.out.println(" ");
            System.out.println(" ");
            for (int i=0;i<=10;i++) {
                System.out.println("Blad podczas laczenia z baza danych (sprawdz config)");
            }
            System.out.println(" ");
            System.out.println(" ");
            return null;
        }
    }

    public void executeQuery(String query, Consumer<ResultSet> action) {
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery()) {
            action.accept(result);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    }

    public int executeUpdate(String query) {
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            if (statement == null)
                return 0;
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}


package pl.vertty.wings.mysql;

import pl.vertty.wings.Main;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class StoreSQLITE implements Store
{
    private final String name;
    private Connection conn;
    private Executor executor;

    public StoreSQLITE(final String name) {
        super();
        this.name = name;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public boolean connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            this.conn = DriverManager.getConnection("jdbc:sqlite:" + Main.getPlugin().getDataFolder() + File.separator + "minecraft.db");
            System.out.println("Connected to the SQLite server!");
            return true;
        }
        catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found!"+ "Error: " + e.getMessage());
            e.printStackTrace();
        }
        catch (SQLException e2) {
            System.out.println("Can not connect to a SQLite server!"+ "Error: " + e2.getMessage());
            e2.printStackTrace();
        }
        return false;
    }

    public void update(final boolean now, final String update) {
        final Runnable r = new Runnable() {
            public void run() {
                try {
                    conn.createStatement().executeUpdate(update);
                }
                catch (SQLException e) {
                    System.out.println("An error occurred with given query!"+ "Error: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        };
        if (now) {
            r.run();
        }
        else {
            this.executor.execute(r);
        }
    }
    public ResultSet update(final String update) {
        try {
            final Statement statement = this.conn.createStatement();
            statement.executeUpdate(update, 1);
            final ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                return rs;
            }
        }
        catch (SQLException e) {
            System.out.println("An error occurred with given query '" + update + "'!"+ "Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void disconnect() {
        if (this.conn != null) {
            try {
                this.conn.close();
            }
            catch (SQLException e) {
                System.out.println("Can not close the connection to the MySQL server!"+ "Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void reconnect() {
        this.connect();
    }

    public boolean isConnected() {
        try {
            return !this.conn.isClosed() || this.conn == null;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet query(final String query) {
        try {
            return this.conn.createStatement().executeQuery(query);
        }
        catch (SQLException e) {
            System.out.println("An error occurred with given query '" + query + "'!"+ "Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void query(final String query, final Callback<ResultSet> cb) {
    }

    public Connection getConnection() {
        return this.conn;
    }

    public StoreMode getStoreMode() {
        return StoreMode.SQLITE;
    }

    public String getName() {
        return this.name;
    }

    public Connection getConn() {
        return this.conn;
    }

    public Executor getExecutor() {
        return this.executor;
    }

    public void setConn(final Connection conn) {
        this.conn = conn;
    }

    public void setExecutor(final Executor executor) {
        this.executor = executor;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof StoreSQLITE)) {
            return false;
        }
        final StoreSQLITE other = (StoreSQLITE)o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object thisname = this.getName();
        final Object othername = other.getName();
        Label_0065: {
            if (thisname == null) {
                if (othername == null) {
                    break Label_0065;
                }
            }
            else if (thisname.equals(othername)) {
                break Label_0065;
            }
            return false;
        }
        final Object thisconn = this.getConn();
        final Object otherconn = other.getConn();
        Label_0139: {
            if (thisconn == null) {
                if (otherconn == null) {
                    break Label_0139;
                }
            }
            else if (thisconn.equals(otherconn)) {
                break Label_0139;
            }
            return false;
        }
        final Object thisexecutor = this.getExecutor();
        final Object otherexecutor = other.getExecutor();
        if (thisexecutor == null) {
            if (otherexecutor == null) {
                return true;
            }
        }
        else if (thisexecutor.equals(otherexecutor)) {
            return true;
        }
        return false;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof StoreSQLITE;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object name = this.getName();
        result = result * 59 + ((name == null) ? 0 : name.hashCode());
        final Object conn = this.getConn();
        result = result * 59 + ((conn == null) ? 0 : conn.hashCode());
        final Object executor = this.getExecutor();
        result = result * 59 + ((executor == null) ? 0 : executor.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "StoreSQLITE(name=" + this.getName() + ", prefix=, conn=" + this.getConn() + ", executor=" + this.getExecutor() + ")";
    }
}


package pl.vertty.wings.wings;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.nukkit.Player;
import pl.vertty.wings.Main;
import pl.vertty.wings.mysql.StoreSQLITE;

import java.util.concurrent.ConcurrentHashMap;

public class UserWings
{
    public static final ConcurrentHashMap<String, UserWingsManager> users;

    public static UserWingsManager getUser(final Player p) {
        for (final UserWingsManager u : UserWings.users.values()) {
            if (u.getName().equalsIgnoreCase(p.getName())) {
                return u;
            }
        }
        return null;
    }

    public static UserWingsManager getUser(final String name) {
        for (final UserWingsManager u : UserWings.users.values()) {
            if (u.getName().equalsIgnoreCase(name)) {
                return u;
            }
        }
        return null;
    }

    public static void createrUser(final Player p) {
        final UserWingsManager u = new UserWingsManager(p);
        UserWings.users.put(p.getName(), u);
    }

    public static void deleteUser(final Player p) {
        final UserWingsManager u = new UserWingsManager(p);
        UserWings.users.remove(u.getName());
        Main.store.update("DELETE FROM `Vertty_WingsPlugin` WHERE `name` = '" + p.getName() + "'");
    }

    public static void deleteUser(final String p) {
        final UserWingsManager u = new UserWingsManager(p);
        UserWings.users.remove(u.getName());
        Main.store.update("DELETE FROM `Vertty_WingsPlugin` WHERE `name` = '" + p + "'");
    }

    public static void loadUsers() {
        try {
            final ResultSet rs = Main.store.query("SELECT * FROM `Vertty_WingsPlugin`");
            while (rs.next()) {
                final UserWingsManager u = new UserWingsManager(rs);
                UserWings.users.put(u.getName(), u);
            }
            rs.close();
        }
        catch (SQLException e) {}
    }

    public static ConcurrentHashMap<String, UserWingsManager> getUsers() {
        return UserWings.users;
    }

    static {
        users = new ConcurrentHashMap<String, UserWingsManager>();
    }
}

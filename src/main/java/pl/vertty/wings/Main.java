package pl.vertty.wings;

import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginManager;
import pl.vertty.wings.config.LoaderConfig;
import pl.vertty.wings.listeners.PlyaerJoinListener;
import pl.vertty.wings.mysql.Store;
import pl.vertty.wings.mysql.StoreSQLITE;
import pl.vertty.wings.wings.UserWings;
import pl.vertty.wings.wings.WingsManager;

import java.sql.SQLException;

public class Main extends PluginBase {

    private static Main plugin;
    private static Main inst;
    public static Store store;

    public void onLoad() {
        plugin = this;
    }

    public static Main getInst() {
        return inst;
    }

    public static Main getPlugin() {
        return plugin;
    }


    @Override
    public void onEnable() {
        inst = this;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        LoaderConfig.load();
        store = new StoreSQLITE("minecraft.db");
        final boolean conn = store.connect();
        if (conn) {
            store.update(true, "CREATE TABLE IF NOT EXISTS `Vertty_WingsPlugin` (`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `name` varchar(32) NOT NULL, `wings` varchar(255) NOT NULL);");
        }
        UserWings.loadUsers();
        try {
            WingsManager.init(this);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        final PluginManager pluginManager = Server.getInstance().getPluginManager();
        pluginManager.registerEvents(new PlyaerJoinListener(), Main.getPlugin());
    }

    @Override
    public void onDisable() {
    }
}

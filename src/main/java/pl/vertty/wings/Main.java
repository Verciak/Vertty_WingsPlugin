package pl.vertty.wings;

import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginManager;
import pl.vertty.wings.config.LoaderConfig;
import pl.vertty.wings.listeners.PlyaerJoinListener;
import pl.vertty.wings.mysql.MySQL;
import pl.vertty.wings.wings.UserWings;
import pl.vertty.wings.wings.WingsManager;

import java.sql.SQLException;

public class Main extends PluginBase {

    private static Main plugin;
    private static Main inst;
    public static MySQL store;

    public void onLoad() {
        plugin = this;
    }

    public static Main getInst() {
        return inst;
    }

    public static Main getPlugin() {
        return plugin;
    }

    public static void Update(String s){
        store.executeUpdate(s);
    }

    @Override
    public void onEnable() {
        inst = this;
        store = new MySQL();
        store.executeUpdate("CREATE TABLE IF NOT EXISTS `Vertty_WingsPlugin` (`id` int(100) NOT NULL PRIMARY KEY AUTO_INCREMENT, `name` varchar(32) NOT NULL, `wings` varchar(255) NOT NULL);");
        LoaderConfig.load();
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
        if (Main.store != null) {
            Main.store.close();
        }
    }
}

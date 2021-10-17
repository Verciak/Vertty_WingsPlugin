package pl.vertty.wings.config;

import pl.vertty.wings.Main;

public class LoaderConfig {


    public static String host;
    public static String name;
    public static String user;
    public static String pass;
    public static int port;

    public static void load() {
        Main.getInst().saveDefaultConfig();
        host = Main.getInst().getConfig().getString("mysql.host");
        name = Main.getInst().getConfig().getString("mysql.name");
        user = Main.getInst().getConfig().getString("mysql.user");
        pass = Main.getInst().getConfig().getString("mysql.pass");
        port = Main.getInst().getConfig().getInt("mysql.port");
    }
}


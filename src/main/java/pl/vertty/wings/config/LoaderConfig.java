package pl.vertty.wings.config;

import pl.vertty.wings.Main;

public class LoaderConfig {
    public static boolean status;

    public static boolean enableincognito;

    public static void load() {
        Main.getInst().saveDefaultConfig();
        enableincognito = Main.getInst().getConfig().getBoolean("drop.enable");
    }
}


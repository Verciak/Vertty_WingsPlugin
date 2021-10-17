package pl.vertty.wings;

import cn.nukkit.plugin.PluginBase;

public class Main extends PluginBase {

    private static Main plugin;
    private static Main inst;

    public void onLoad() {
        plugin = this;
    }

    public static Main getInst() {
        return inst;
    }

    public static Main getPlugin() {
        return plugin;
    }

}

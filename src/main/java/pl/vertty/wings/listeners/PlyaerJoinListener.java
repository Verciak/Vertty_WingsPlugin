package pl.vertty.wings.listeners;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import pl.vertty.wings.wings.SkinUtil;
import pl.vertty.wings.wings.UserWings;
import pl.vertty.wings.wings.WingsManager;

public class PlyaerJoinListener implements Listener {


    @EventHandler
    public void onFirst(PlayerJoinEvent e){
        Player p = e.getPlayer();
        SkinUtil.originalSkins.put(p.getUniqueId(), p.getSkin());
        if (UserWings.getUser(p) != null && UserWings.getUser(p).getWings() != null) {
            WingsManager.setRatWings(p, WingsManager.getWings(UserWings.getUser(p).getWings()));
        }
    }
}

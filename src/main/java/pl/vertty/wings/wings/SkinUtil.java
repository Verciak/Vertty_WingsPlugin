package pl.vertty.wings.wings;

import java.util.HashMap;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import cn.nukkit.network.protocol.DataPacket;
import java.util.Collection;
import cn.nukkit.Server;
import cn.nukkit.network.protocol.PlayerSkinPacket;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import cn.nukkit.Player;
import cn.nukkit.entity.data.Skin;
import pl.vertty.wings.Main;

import java.util.UUID;
import java.util.Map;

public class SkinUtil
{
    public static Map<UUID, Skin> originalSkins;

    public static void changeSkin(final Player player) throws SkinChangeException {
        final Skin skin = new Skin();
        final Path skinPath = Paths.get(Main.getPlugin().getDataFolder() + "/skin.png", new String[0]);
        final Path skinGeometryPath = Paths.get(Main.getPlugin().getDataFolder() + "/defaultGeometry.json", new String[0]);
        String geometry;
        BufferedImage skinData;
        try {
            geometry = new String(Files.readAllBytes(skinGeometryPath), StandardCharsets.UTF_8);
            skinData = ImageIO.read(skinPath.toFile());
        }
        catch (IOException e) {
            throw new SkinChangeException("Error loading data", e);
        }
        skin.setGeometryData(geometry);
        skin.setGeometryName("geometry.defaultGeometry");
        skin.setSkinData(skinData);
        skin.setSkinId("defaultGeometry");
        skin.setPremium(true);
        skin.setTrusted(true);
        final Skin oldSkin = player.getSkin();
        player.setSkin(skin);
        final PlayerSkinPacket packet = new PlayerSkinPacket();
        packet.skin = skin;
        packet.newSkinName = "defaultGeometry";
        packet.oldSkinName = oldSkin.getSkinId();
        packet.uuid = player.getUniqueId();
        Server.broadcastPacket(Server.getInstance().getOnlinePlayers().values(), packet);
    }

    public static boolean resetSkin(final Player player) {
        final Skin skin = SkinUtil.originalSkins.get(player.getUniqueId());
        if (skin != null) {
            final Skin oldSkin = player.getSkin();
            player.setSkin(skin);
            skin.setTrusted(true);
            final PlayerSkinPacket packet = new PlayerSkinPacket();
            packet.skin = skin;
            packet.newSkinName = skin.getSkinId();
            packet.oldSkinName = oldSkin.getSkinId();
            packet.uuid = player.getUniqueId();
            Server.broadcastPacket(Server.getInstance().getOnlinePlayers().values(), packet);
            return true;
        }
        return false;
    }

    static {
        SkinUtil.originalSkins = new HashMap<UUID, Skin>();
    }
}

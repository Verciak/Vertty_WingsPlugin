package pl.vertty.wings.wings;

import java.sql.SQLException;
import java.sql.ResultSet;
import cn.nukkit.Player;
import pl.vertty.wings.Main;

public class UserWingsManager
{
    private String name;
    private String wings;

    public UserWingsManager(final Player p) {
        this.name = p.getName();
        this.wings = "";
        this.insert();
    }

    public UserWingsManager(final String p) {
        this.name = p;
        this.wings = "";
    }

    public UserWingsManager(final ResultSet rs) throws SQLException {
        this.name = rs.getString("name");
        this.wings = rs.getString("wings");
    }

    public String getWings() {
        return this.wings;
    }

    public void setWings(final String wings) {
        this.wings = wings;
        Main.Update("UPDATE `Vertty_WingsPlugin` SET `wings` ='" + this.getWings() + "' WHERE `name` ='" + this.getName() + "'");
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    private void insert() {
        Main.Update("INSERT INTO `Vertty_WingsPlugin`(`id`, `name`, `wings`) VALUES (NULL, '" + this.getName() + "','" + this.getWings() + "')");
    }
}

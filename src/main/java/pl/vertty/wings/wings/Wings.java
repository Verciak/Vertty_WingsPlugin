package pl.vertty.wings.wings;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.io.File;
import java.awt.Color;

public class Wings
{
    private String name;
    private String geometryName;
    private String geometryData;
    private String path;
    private Color color;

    public Wings(final String name, final String path) {
        this.name = name;
        this.geometryName = "geometry." + name;
        this.path = path;
        final File geo = new File(this.path + "/geometry.json");
        final File skin = new File(this.path + "/skin.png");
        if (!geo.isFile()) {
            System.out.println("Nie znaleziono pliku: geometry.json");
        }
        if (!skin.isFile()) {
            System.out.println("Nie znaleziono pliku: skin.png");
        }
        try {
            this.geometryData = new String(Files.readAllBytes(geo.toPath()), StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return this.name;
    }

    public String getGeometryName() {
        return this.geometryName;
    }

    public String getGeometryData() {
        return this.geometryData;
    }
}

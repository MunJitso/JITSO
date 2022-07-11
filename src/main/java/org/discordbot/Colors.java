package org.discordbot;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public enum Colors {
    Red(new Color(255, 173, 173)),
    Orange(new Color(255, 214, 165)),
    Yellow(new Color(253, 255, 182)),
    Green(new Color(202, 255, 191)),
    DarkGreen(new Color(88, 129, 87)),
    Cyan(new Color(155, 246, 255)),
    Blue(new Color(160, 196, 255)),
    Mauve(new Color(189, 178, 255)),
    Purple(new Color(190, 137, 251)),
    Pink(new Color(255, 198, 255)),
    Brown(new Color(212, 163, 115)),
    AWeirdColor(new Color(181, 162, 173)),
    White(new Color(255, 255, 252));


    private final Color aestheticColor;
    Colors(Color aestheticColor) {
        this.aestheticColor = aestheticColor;
    }

    public Color getInstance() {
        return aestheticColor;
    }
    public static List<Color> getColorList(){
        ArrayList<Color> colorList = new ArrayList<>();
        int i = 0;
        while (i < Colors.values().length) {
            for (Colors value : Colors.values()) {
              colorList.add(value.getInstance());
            }
            i++;
        }
        return colorList;
    }

    public static Color getAestheticColor(int random) {
        return getColorList().get(random);
    }
}

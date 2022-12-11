package cn.liqing.mousedanmu.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.math.Color;

@Config(name = "guard")
public class GuardConfig implements ConfigData {
    public boolean isShow = true;
    @ConfigEntry.ColorPicker
    public int color = Color.ofRGBA(255, 255, 255, 0).getColor();

    @ConfigEntry.ColorPicker
    public int level1 = Color.ofRGBA(250, 103, 67, 0).getColor();

    @ConfigEntry.ColorPicker
    public int level2 = Color.ofRGBA(171, 64, 250, 0).getColor();

    @ConfigEntry.ColorPicker
    public int level3 = Color.ofRGBA(81, 104, 250, 0).getColor();
}

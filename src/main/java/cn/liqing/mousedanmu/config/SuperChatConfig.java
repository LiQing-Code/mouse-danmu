package cn.liqing.mousedanmu.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.math.Color;

@Config(name = "superChat")
public class SuperChatConfig implements ConfigData {
    public boolean isShowChat = true;
    public boolean isShowBossBar = true;

    @ConfigEntry.ColorPicker
    public int color = Color.ofRGBA(42, 96, 178, 0).getColor();
    @ConfigEntry.ColorPicker
    public int color50 = Color.ofRGBA(66, 125, 158, 0).getColor();
    @ConfigEntry.ColorPicker
    public int color100 = Color.ofRGBA(226, 181, 43, 0).getColor();
    @ConfigEntry.ColorPicker
    public int color500 = Color.ofRGBA(224, 148, 67, 0).getColor();
    @ConfigEntry.ColorPicker
    public int color1000 = Color.ofRGBA(229, 77, 77, 0).getColor();
    @ConfigEntry.ColorPicker
    public int color2000 = Color.ofRGBA(171, 26, 50, 0).getColor();
}

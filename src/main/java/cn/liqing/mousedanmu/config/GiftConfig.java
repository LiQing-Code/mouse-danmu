package cn.liqing.mousedanmu.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.math.Color;

@Config(name = "gift")
public class GiftConfig implements ConfigData {
    public boolean isShow = true;
    public boolean isShowFreeGiftToOverlayMessage = false;

    @ConfigEntry.ColorPicker
    public int color = Color.ofRGBA(240, 248, 187, 0).getColor();

    public int price1 = 5;
    @ConfigEntry.ColorPicker
    public int color1 = Color.ofRGBA(173, 255, 47, 0).getColor();

    public int price2 = 10;
    @ConfigEntry.ColorPicker
    public int color2 = Color.ofRGBA(255, 255, 0, 0).getColor();

    public int price3 = 50;
    @ConfigEntry.ColorPicker
    public int color3 = Color.ofRGBA(0, 0, 255, 0).getColor();

    public int price4 = 100;
    @ConfigEntry.ColorPicker
    public int color4 = Color.ofRGBA(255, 0, 255, 0).getColor();
}

package cn.liqing.mousedanmu.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.math.Color;

@Config(name = "danmu")
public class DanmuConfig implements ConfigData {
    public boolean isShow = true;
    public boolean isShowEmoji = true;
    @ConfigEntry.ColorPicker
    public int emojiColor = Color.ofRGBA(240, 248, 187, 0).getColor();
    public boolean isShowInteractive = true;
    public boolean isShowInteractiveToOverlayMessage = false;
    @ConfigEntry.ColorPicker
    public int interactiveColor = Color.ofRGBA(177, 241, 184, 0).getColor();
    public boolean isShowFansMedal = true;
    @ConfigEntry.ColorPicker
    public int fansColor = Color.ofRGBA(92, 150, 142, 0).getColor();
    @ConfigEntry.ColorPicker
    public int fansLargerThan4 = Color.ofRGBA(93, 123, 158, 0).getColor();
    @ConfigEntry.ColorPicker
    public int fansLargerThan8 = Color.ofRGBA(141, 124, 166, 0).getColor();
    @ConfigEntry.ColorPicker
    public int fansLargerThan12 = Color.ofRGBA(190, 102, 134, 0).getColor();
    @ConfigEntry.ColorPicker
    public int fansLargerThan16 = Color.ofRGBA(199, 157, 36, 0).getColor();
    @ConfigEntry.ColorPicker
    public int fansLargerThan20 = Color.ofRGBA(26, 84, 75, 0).getColor();
    @ConfigEntry.ColorPicker
    //public int fansLargerThan24 = Color.ofRGBA(6, 21, 76, 0).getColor();
    public int fansLargerThan24 = Color.ofRGBA(102, 179, 255, 0).getColor();
    @ConfigEntry.ColorPicker
    //public int fansLargerThan28 = Color.ofRGBA(45, 8, 85, 0).getColor();
    public int fansLargerThan28 = Color.ofRGBA(255, 128, 223, 0).getColor();
}

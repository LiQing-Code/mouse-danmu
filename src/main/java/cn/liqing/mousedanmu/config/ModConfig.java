package cn.liqing.mousedanmu.config;

import cn.liqing.mousedanmu.MouseDanmu;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = MouseDanmu.MOD_ID)
public class ModConfig implements ConfigData {
    @ConfigEntry.Category("liveRoom")
    @ConfigEntry.Gui.TransitiveObject
    public LiveRoomConfig liveRoom = new LiveRoomConfig();

    @ConfigEntry.Category("danmu")
    @ConfigEntry.Gui.TransitiveObject
    public DanmuConfig danmu = new DanmuConfig();

    @ConfigEntry.Category("gift")
    @ConfigEntry.Gui.TransitiveObject
    public GiftConfig gift = new GiftConfig();

    @ConfigEntry.Category("superChat")
    @ConfigEntry.Gui.TransitiveObject
    public SuperChatConfig superChat = new SuperChatConfig();

    @ConfigEntry.Category("guard")
    @ConfigEntry.Gui.TransitiveObject
    public GuardConfig guard = new GuardConfig();
}
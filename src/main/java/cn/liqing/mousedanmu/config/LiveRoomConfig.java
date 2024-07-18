package cn.liqing.mousedanmu.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.ArrayList;

@Config(name = "liveRoom")
public class LiveRoomConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    public boolean autoConnect = false;
    @ConfigEntry.Gui.Tooltip
    public int roomId = 24256088;
    @ConfigEntry.Gui.Tooltip
    public String cookie = "";

    public ArrayList<Integer> history = new ArrayList<>();
}

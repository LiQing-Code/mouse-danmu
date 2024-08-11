package cn.liqing.mousedanmu;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.math.Color;

import java.util.ArrayList;

@Config(name = MouseDanmu.MOD_ID)
public class ModConfig implements ConfigData {
    @ConfigEntry.Category("liveRoom")
    @ConfigEntry.Gui.TransitiveObject
    public LiveRoomConfig liveRoom = new LiveRoomConfig();

    @Config(name = "liveRoom")
    public static class LiveRoomConfig implements ConfigData {
        @ConfigEntry.Gui.Tooltip
        public boolean autoConnect = false;
        @ConfigEntry.Gui.Tooltip
        public int roomId = 24256088;
        @ConfigEntry.Gui.Tooltip
        public String cookie = "";

        public ArrayList<Integer> history = new ArrayList<>();
    }

    @ConfigEntry.Category("danmu")
    @ConfigEntry.Gui.TransitiveObject
    public DanmuConfig danmu = new DanmuConfig();

    @Config(name = "danmu")
    public static class DanmuConfig implements ConfigData {
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


    @ConfigEntry.Category("gift")
    @ConfigEntry.Gui.TransitiveObject
    public GiftConfig gift = new GiftConfig();

    @Config(name = "gift")
    public static class GiftConfig implements ConfigData {
        public boolean isShow = true;
        public boolean isShowFreeGiftToOverlayMessage = false;

        @ConfigEntry.ColorPicker
        public int color = Color.ofRGBA(255, 215, 119, 0).getColor();

        public int price1 = 5;
        @ConfigEntry.ColorPicker
        public int color1 = Color.ofRGBA(173, 255, 47, 0).getColor();

        public int price2 = 10;
        @ConfigEntry.ColorPicker
        public int color2 = Color.ofRGBA(255, 255, 0, 0).getColor();

        public int price3 = 50;
        @ConfigEntry.ColorPicker
        public int color3 = Color.ofRGBA(51, 153, 255, 0).getColor();

        public int price4 = 100;
        @ConfigEntry.ColorPicker
        public int color4 = Color.ofRGBA(255, 0, 255, 0).getColor();
    }


    @ConfigEntry.Category("superChat")
    @ConfigEntry.Gui.TransitiveObject
    public SuperChatConfig superChat = new SuperChatConfig();

    @Config(name = "superChat")
    public static class SuperChatConfig implements ConfigData {
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

    @ConfigEntry.Category("guard")
    @ConfigEntry.Gui.TransitiveObject
    public GuardConfig guard = new GuardConfig();

    @Config(name = "guard")
    public static class GuardConfig implements ConfigData {
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
}
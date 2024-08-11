package cn.liqing.mousedanmu;

import net.minecraft.entity.boss.BossBar;

public class DanmuColorPicker {

    private final ModConfig config;

    public DanmuColorPicker(ModConfig config) {
        this.config = config;
    }

    public int user(int level) {
        return switch (level) {
            case 3 -> config.guard.level3;
            case 2 -> config.guard.level2;
            case 1 -> config.guard.level1;
            default -> config.guard.color;
        };
    }

    public int fans(int level) {
        if (level > 28) {
            return config.danmu.fansLargerThan28;
        } else if (level > 24) {
            return config.danmu.fansLargerThan24;
        } else if (level > 20) {
            return config.danmu.fansLargerThan20;
        } else if (level > 16) {
            return config.danmu.fansLargerThan16;
        } else if (level > 12) {
            return config.danmu.fansLargerThan12;
        } else if (level > 8) {
            return config.danmu.fansLargerThan8;
        } else if (level > 4) {
            return config.danmu.fansLargerThan4;
        } else {
            return config.danmu.fansColor;
        }
    }

    public int gift(float price) {
        if (price >= config.gift.price4) {
            return config.gift.color4;
        } else if (price >= config.gift.price3) {
            return config.gift.color3;
        } else if (price >= config.gift.price2) {
            return config.gift.color2;
        } else if (price >= config.gift.price1) {
            return config.gift.color1;
        } else {
            return config.gift.color;
        }
    }

    public int superChat(float price) {
        if (price >= 2000) {
            return config.superChat.color2000;
        } else if (price >= 1000) {
            return config.superChat.color1000;
        } else if (price >= 500) {
            return config.superChat.color500;
        } else if (price >= 100) {
            return config.superChat.color100;
        } else if (price >= 50) {
            return config.superChat.color50;
        } else {
            return config.superChat.color;
        }
    }

    public static BossBar.Color superChatBossBar(float price) {
        if (price >= 2000) {
            return BossBar.Color.RED;
        } else if (price >= 1000) {
            return BossBar.Color.PINK;
        } else if (price >= 500) {
            return BossBar.Color.GREEN;
        } else if (price >= 100) {
            return BossBar.Color.YELLOW;
        } else if (price >= 50) {
            return BossBar.Color.BLUE;
        } else {
            return BossBar.Color.PURPLE;
        }
    }
}

package cn.liqing.mousedanmu.util;

import cn.liqing.mousedanmu.config.DanmuConfig;
import cn.liqing.mousedanmu.config.GiftConfig;
import cn.liqing.mousedanmu.config.GuardConfig;
import cn.liqing.mousedanmu.config.SuperChatConfig;
import net.minecraft.entity.boss.BossBar;
import org.jetbrains.annotations.NotNull;

public class ColorPicker {

    public static int user(GuardConfig config, int level) {
        return switch (level) {
            case 3 -> config.level3;
            case 2 -> config.level2;
            case 1 -> config.level1;
            default -> config.color;
        };
    }

    public static int fans(DanmuConfig config, int level) {
        if (level > 28) {
            return config.fansLargerThan28;
        } else if (level > 24) {
            return config.fansLargerThan24;
        } else if (level > 20) {
            return config.fansLargerThan20;
        } else if (level > 16) {
            return config.fansLargerThan16;
        } else if (level > 12) {
            return config.fansLargerThan12;
        } else if (level > 8) {
            return config.fansLargerThan8;
        } else if (level > 4) {
            return config.fansLargerThan4;
        } else {
            return config.fansColor;
        }
    }

    public static int gift(@NotNull GiftConfig config, float price) {
        if (price >= config.price4) {
            return config.color4;
        } else if (price >= config.price3) {
            return config.color3;
        } else if (price >= config.price2) {
            return config.color2;
        } else if (price >= config.price1) {
            return config.color1;
        } else {
            return config.color;
        }
    }

    public static int superChat(@NotNull SuperChatConfig config, float price) {
        if (price >= 2000) {
            return config.color2000;
        } else if (price >= 1000) {
            return config.color1000;
        } else if (price >= 500) {
            return config.color500;
        } else if (price >= 100) {
            return config.color100;
        } else if (price >= 50) {
            return config.color50;
        } else {
            return config.color;
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

package cn.liqing.mousedanmu;

import cn.liqing.model.*;
import cn.liqing.mousedanmu.config.ModConfig;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;

public class DanmuConverter {
    final ModConfig config;
    final DanmuColorPicker colorPicker;

    public DanmuConverter(ModConfig config, DanmuColorPicker colorPicker) {
        this.config = config;
        this.colorPicker = colorPicker;
    }

    public MutableText convertUser(@NotNull User user) {
        var mutableText = Text.literal(user.name)
                .append(": ")
                .styled(style -> style
                        .withColor(colorPicker.user(user.guardLevel))
                        .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,
                                "https://space.bilibili.com/%d".formatted(user.uid)))
                        .withHoverEvent(new HoverEvent(net.minecraft.text.HoverEvent.Action.SHOW_TEXT,
                                Text.translatable("text.mouse-danmu.click-to-user-space"))));

        if (config.danmu.isShowFansMedal && user.fansMedal != null && user.fansMedal.name.length() > 0) {
            var fansMedalText = Texts.bracketed(Text.literal(user.fansMedal.name)
                            .append("|")
                            .append(String.valueOf(user.fansMedal.level)))
                    .styled(style -> style.withColor(colorPicker.fans(user.fansMedal.level)));
            mutableText = fansMedalText.append(mutableText);
        }
        return mutableText;
    }

    public MutableText convert(@NotNull Danmu danmu) {
        MutableText text = Text.literal(danmu.body).formatted(Formatting.WHITE);
        if (danmu.user.guardLevel > 0) {
            text = Texts.bracketed(text).styled(style ->
                    style.withColor(colorPicker.user(danmu.user.guardLevel)));
        }
        return convertUser(danmu.user).append(text);

    }

    public MutableText convert(@NotNull Gift gift) {
        var mutableText = Texts.bracketed(Text.literal(gift.name))
                .styled(style -> style.withColor(colorPicker.gift(gift.price)))
                .append(" × ").append(String.valueOf(gift.num));
        if (gift.price > 0) {
            mutableText = mutableText.append(" ￥").append(String.valueOf(gift.price));
        }
        return convertUser(gift.user).append(mutableText);
    }

    public MutableText convert(@NotNull Emoji emoji) {
        var mutableText = Texts.bracketed(Text.literal(emoji.body))
                .styled(style -> style.withColor(config.danmu.emojiColor)
                        .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, emoji.uri))
                        .withHoverEvent(new HoverEvent(net.minecraft.text.HoverEvent.Action.SHOW_TEXT,
                                Text.translatable("text.mouse-danmu.click-to-view-emoji"))));
        return convertUser(emoji.user).append(mutableText);
    }

    public MutableText convert(@NotNull SuperChat sc) {
        var mutableText = Texts.bracketed(Text.literal(String.valueOf(sc.price)))
                .append(Text.literal(sc.body))
                .styled(style -> style
                        .withColor(colorPicker.superChat(sc.price))
                        .withBold(true));
        return convertUser(sc.user).append(mutableText);
    }

    public MutableText convert(@NotNull Guard guard) {
        var mutableText = Texts.bracketed(Text.literal(guard.name))
                .styled(style -> style.withColor(colorPicker.user(guard.level)))
                .append(" × ").append(String.valueOf(guard.num)).append(guard.unit);
        if (guard.price > 0) {
            mutableText = mutableText.append(" ￥").append(String.valueOf(guard.price));
        }
        return convertUser(guard.user).append(mutableText);
    }

    public MutableText convert(@NotNull Interactive interactive) {
        var msg = switch (interactive.type) {
            case 1 -> Text.translatable("text.mouse-danmu.entry-live-room");
            case 2 -> Text.translatable("text.mouse-danmu.attention-live-room");
            case 3 -> Text.translatable("text.mouse-danmu.share-live-room");
            case 4 -> Text.translatable("special-attention-live-room");
            case 5 -> Text.translatable("mutual-attention-live-room");
            default -> Text.literal("未知互动类型:%d".formatted(interactive.type));
        };
        var mutableText = Texts.bracketed(msg)
                .styled(style -> style.withColor(config.danmu.interactiveColor));
        return convertUser(interactive.user).append(mutableText);
    }
}

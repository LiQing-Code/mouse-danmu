package cn.liqing.mousedanmu;

import cn.liqing.BLiveClient;
import cn.liqing.model.*;
import cn.liqing.mousedanmu.config.ModConfig;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents.Disconnect;
import static net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents.Join;

public class DanmuClient extends BLiveClient implements Disconnect, Join {
    @Nullable PlayerEntity player;
    final ModConfig config;
    final DanmuConverter danmuText;
    final DanmuColorPicker colorPicker;
    final SuperChatManager superChatManager;

    public DanmuClient(ModConfig config) {
        ClientPlayConnectionEvents.JOIN.register(this);
        ClientPlayConnectionEvents.DISCONNECT.register(this);
        this.config = config;
        colorPicker = new DanmuColorPicker(config);
        danmuText = new DanmuConverter(config, colorPicker);
        superChatManager = new SuperChatManager(danmuText, colorPicker);
    }

    @Override
    public void onPlayDisconnect(ClientPlayNetworkHandler handler, MinecraftClient client) {
        close();
        player = null;
    }

    @Override
    public void onPlayReady(ClientPlayNetworkHandler handler, PacketSender sender, @NotNull MinecraftClient client) {
        player = client.player;
        if (config.liveRoom.autoConnect && config.liveRoom.roomId != 0) {
            if (!connect(config.liveRoom.roomId))
                status();
        }
    }

    @Override
    public boolean connect(int room) {
        if (!super.connect(room)) {
            status();
            return false;
        }

        //??????????????????
        var history = config.liveRoom.history;
        int i = history.indexOf(room);
        if (i != -1)
            history.remove(i);
        history.add(0, room);
        MouseDanmu.configHolder.save();
        return true;
    }

    @Override
    public boolean close() {
        if (!super.close()) {
            status();
            return false;
        }
        superChatManager.clear();
        return true;
    }

    public void status() {
        if (player == null)
            return;
        MutableText text;
        switch (super.state()) {
            case 1 -> text = Text.translatable("text.mouse-danmu.live-room-connected", String.valueOf(room))
                    .formatted(Formatting.GREEN)
                    .styled(style -> style
                            .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,
                                    "https://live.bilibili.com/" + room))
                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                    Text.translatable("text.mouse-danmu.open-live-room"))));
            case 3 -> text = Text.translatable("text.mouse-danmu.live-room-connecting", String.valueOf(room))
                    .styled(style -> style
                            .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,
                                    "https://live.bilibili.com/" + room))
                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                    Text.translatable("text.mouse-danmu.open-live-room"))));
            default -> text = Text.translatable("text.mouse-danmu.live-room-closed");
        }
        player.sendMessage(text);
    }

    public void test(int delay) throws InterruptedException {
        //????????????
        var user = new User();
        user.name = "??????";
        user.uid = 345259002;
        user.guardLevel = 0;
        var danmu = new Danmu();
        danmu.user = user;
        danmu.body = "???????????????????????????????????????";
        onDanmu(danmu);
        Thread.sleep(delay);

        //??????
        var emoji = new Emoji();
        emoji.uri = "https://www.bilibili.com/video/BV1GJ411x7h7";
        emoji.body = "??????????????????????????????????????????????????????????????????";
        emoji.user = user;
        onEmoji(emoji);
        Thread.sleep(delay);

        //??????????????????1???
        user.fansMedal = new User.FansMedal();
        user.fansMedal.name = "?????????";
        user.fansMedal.level = 1;
        var gift = new Gift();
        gift.price = 1;
        gift.name = "???????????????";
        gift.num = 1;
        gift.user = user;
        onGift(gift);
        Thread.sleep(delay);

        gift.price = config.gift.price1;
        gift.name = "??????1";
        onGift(gift);
        Thread.sleep(delay);
        gift.price = config.gift.price2;
        gift.name = "??????2";
        onGift(gift);
        Thread.sleep(delay);
        gift.price = config.gift.price3;
        gift.name = "??????3";
        onGift(gift);
        Thread.sleep(delay);
        gift.price = config.gift.price4;
        gift.name = "??????4";
        onGift(gift);
        Thread.sleep(delay);

        //?????????5???
        user.fansMedal.name = "?????????";
        user.fansMedal.level = 5;
        danmu.body = "???????????????";
        onDanmu(danmu);
        Thread.sleep(delay);

        //?????????9???
        user.fansMedal.name = "?????????";
        user.fansMedal.level = 9;
        danmu.body = "9?????????";
        onDanmu(danmu);
        Thread.sleep(delay);

        //?????????13???
        user.fansMedal.name = "?????????";
        user.fansMedal.level = 13;
        danmu.body = "??????????????????????????????";
        onDanmu(danmu);
        Thread.sleep(delay);

        //?????????17???
        user.fansMedal.name = "ikun";
        user.fansMedal.level = 17;
        danmu.body = "???xxx????????????????????????~??????";
        onDanmu(danmu);
        user.fansMedal.name = "?????????";
        Thread.sleep(delay);

        //?????????21????????????
        var guard = new Guard();
        guard.user = user;
        guard.name = "??????";
        guard.unit = "???";
        guard.level = 3;
        user.guardLevel = 3;
        guard.num = 1;
        guard.price = 138;
        onGuard(guard);
        Thread.sleep(delay);
        user.fansMedal.name = "?????????";
        user.fansMedal.level = 21;
        danmu.body = "?????????????????????????";
        onDanmu(danmu);
        Thread.sleep(delay);

        //?????????25????????????
        guard.name = "??????";
        guard.level = 2;
        user.guardLevel = 2;
        guard.price = 1998;
        onGuard(guard);
        Thread.sleep(delay);
        user.fansMedal.name = "?????????";
        user.fansMedal.level = 25;
        danmu.body = "??????????????????????????????";
        onDanmu(danmu);
        Thread.sleep(delay);

        //?????????29????????????
        guard.name = "??????";
        guard.level = 1;
        user.guardLevel = 1;
        guard.price = 19998;
        onGuard(guard);
        Thread.sleep(delay);
        user.fansMedal.name = "?????????";
        user.fansMedal.level = 29;
        danmu.body = "????????????????????????????????????????????????????????????????????????";
        onDanmu(danmu);
        user.fansMedal.name = "?????????";
        Thread.sleep(delay);

        var sc = new SuperChat();
        sc.user = user;

        sc.time = 30;
        sc.price = 1000;
        sc.body = "??????????????????????????????";
        onSuperChat(sc);
        Thread.sleep(delay);

        sc.time = 20;
        sc.price = 30;
        sc.body = "SC???????????????";
        onSuperChat(sc);
        Thread.sleep(delay);

        sc.price = 50;
        sc.body = "????????????,SC????????????????????????";
        onSuperChat(sc);
        Thread.sleep(delay);

        sc.price = 100;
        sc.body = "?????????????????????";
        onSuperChat(sc);
        Thread.sleep(delay);

        sc.price = 500;
        sc.body = "???????????????";
        onSuperChat(sc);
        Thread.sleep(delay);

        sc.time = 30;
        user.fansMedal.name = "?????????";
        sc.price = 2000;
        sc.body = "??????????????????????????????????????????????????????";
        onSuperChat(sc);
    }

    @Override
    public void onDanmu(Danmu danmu) {
        if (config.danmu.isShow && player != null)
            player.sendMessage(danmuText.convert(danmu));
    }

    @Override
    public void onEmoji(Emoji emoji) {
        if (config.danmu.isShowEmoji && player != null)
            player.sendMessage(danmuText.convert(emoji));
    }

    @Override
    public void onGift(Gift gift) {
        if (config.gift.isShow && player != null) {
            if (config.gift.isShowFreeGiftToOverlayMessage && gift.price <= 0) {
                MinecraftClient.getInstance().inGameHud
                        .setOverlayMessage(danmuText.convert(gift), false);
            } else {
                player.sendMessage(danmuText.convert(gift));
            }
        }
    }

    @Override
    public void onSuperChat(SuperChat sc) {
        if (config.superChat.isShowBossBar) {
            superChatManager.add(sc);
        }
        if (config.superChat.isShowChat && player != null) {
            player.sendMessage(danmuText.convert(sc));
        }
    }

    @Override
    public void onGuard(Guard guard) {
        if (config.guard.isShow && player != null)
            player.sendMessage(danmuText.convert(guard));
    }

    @Override
    public void onInteractive(Interactive interactive) {
        if (config.danmu.isShowInteractive && player != null) {
            player.sendMessage(danmuText.convert(interactive));
        }
        if (config.danmu.isShowInteractiveToOverlayMessage)
            MinecraftClient.getInstance().inGameHud.setOverlayMessage(
                    danmuText.convert(interactive), false);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        status();
        String msg;
        msg = "???????????????????????????%d".formatted(code);
        MouseDanmu.LOGGER.info(msg);
        if (player != null)
            player.sendMessage(Text.literal(msg).formatted(Formatting.GOLD));
    }

    @Override
    public void onError(Exception ex) {
        MouseDanmu.LOGGER.error("????????????", ex);
        String msg = "????????????:" + ex.getMessage();
        if (player != null)
            player.sendMessage(Text.literal(msg).formatted(Formatting.RED));
    }

    @Override
    public void onOpen() {
        MouseDanmu.LOGGER.info("????????????%d".formatted(room));
        status();
    }
}

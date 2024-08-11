package cn.liqing.mousedanmu;

import cn.liqing.bili.live.danmu.Auth;
import cn.liqing.bili.live.danmu.ConnectionListener;
import cn.liqing.bili.live.danmu.DanmuClient;
import cn.liqing.bili.live.danmu.User;
import cn.liqing.bili.live.danmu.handler.*;
import cn.liqing.bili.live.danmu.model.*;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MouseDanmuClient implements ClientPlayConnectionEvents.Disconnect, ClientPlayConnectionEvents.Join, ConnectionListener {
    static final Logger LOGGER = LoggerFactory.getLogger(MouseDanmuClient.class);

    @Nullable
    PlayerEntity player;
    final ModConfig config;
    final DanmuConverter danmuText;
    final DanmuColorPicker colorPicker;
    final SuperChatManager superChatManager;
    final DanmuClient client;
    private int room;

    public MouseDanmuClient(ModConfig config) {
        ClientPlayConnectionEvents.JOIN.register(this);
        ClientPlayConnectionEvents.DISCONNECT.register(this);
        this.config = config;
        colorPicker = new DanmuColorPicker(config);
        danmuText = new DanmuConverter(config, colorPicker);
        superChatManager = new SuperChatManager(danmuText, colorPicker);
        client = new DanmuClient();
        client.setListener(this);

        client.addHandler(new DanmuHandler(this::onDanmu));
        client.addHandler(new EomjiHandler(this::onEmoji));
        client.addHandler(new GiftHandler(this::onGift));
        client.addHandler(new GuardHandler(this::onGuard));
        client.addHandler(new InteractiveHandler(this::onInteractive));
        client.addHandler(new SuperChatHandler(this::onSuperChat));
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
            connect(config.liveRoom.roomId);
        }
    }

    public void connect(int room) {
        this.room = room;
        try {
            Auth auth = config.liveRoom.cookie.isEmpty() ?
                    Auth.create(room) :
                    Auth.create(room, config.liveRoom.cookie);
            client.connect(auth);
        } catch (Exception e) {
            LOGGER.error("鼠鼠出错", e);
            String msg = "鼠鼠出错:" + e.getMessage();
            if (player != null)
                player.sendMessage(Text.literal(msg).formatted(Formatting.RED));
        }

        //添加连接记录
        var history = config.liveRoom.history;
        int i = history.indexOf(room);
        if (i != -1)
            history.remove(i);
        history.add(0, room);
    }

    public void close() {
        client.disconnect();
        superChatManager.clear();
    }

    public void status() {
        if (player == null)
            return;
        MutableText text;
        if (client.isOpen()) {
            text = Text.translatable("text.mousedanmu.live-room-connected", String.valueOf(room))
                    .formatted(Formatting.GREEN)
                    .styled(style -> style
                            .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,
                                    "https://live.bilibili.com/" + room))
                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                    Text.translatable("text.mousedanmu.open-live-room"))));
        } else {
            text = Text.translatable("text.mousedanmu.live-room-closed");
        }
        player.sendMessage(text);
    }

    public void test(int delay) throws InterruptedException {
        //普通消息
        var user = new User();
        user.name = "立青";
        user.uid = "345259002";
        user.guardLevel = 0;
        var danmu = new Danmu();
        danmu.user = user;
        danmu.body = "初见，可以点我名字打开主页";
        onDanmu(danmu);
        Thread.sleep(delay);

        //表情
        var emoji = new Emoji();
        emoji.uri = "https://www.bilibili.com/video/BV1GJ411x7h7";
        emoji.body = "这是表情，我不会显示图片，可以点击查看图片！";
        emoji.user = user;
        onEmoji(emoji);
        Thread.sleep(delay);

        //打赏和粉丝团1级
        user.fansMedal = new User.FansMedal();
        user.fansMedal.name = "粉丝团";
        user.fansMedal.level = 1;
        var gift = new Gift();
        gift.price = 1;
        gift.name = "粉丝团灯牌";
        gift.num = 1;
        gift.user = user;
        onGift(gift);
        Thread.sleep(delay);

        gift.price = config.gift.price1;
        gift.name = "礼物1";
        onGift(gift);
        Thread.sleep(delay);
        gift.price = config.gift.price2;
        gift.name = "礼物2";
        onGift(gift);
        Thread.sleep(delay);
        gift.price = config.gift.price3;
        gift.name = "礼物3";
        onGift(gift);
        Thread.sleep(delay);
        gift.price = config.gift.price4;
        gift.name = "礼物4";
        onGift(gift);
        Thread.sleep(delay);

        //粉丝团5级
        user.fansMedal.name = "粉丝团";
        user.fansMedal.level = 5;
        danmu.body = "牌牌真好看";
        onDanmu(danmu);
        Thread.sleep(delay);

        //粉丝团9级
        user.fansMedal.name = "粉丝团";
        user.fansMedal.level = 9;
        danmu.body = "9级了耶";
        onDanmu(danmu);
        Thread.sleep(delay);

        //粉丝团13级
        user.fansMedal.name = "粉丝团";
        user.fansMedal.level = 13;
        danmu.body = "别问我的等级怎么涨的";
        onDanmu(danmu);
        Thread.sleep(delay);

        //粉丝团17级
        user.fansMedal.name = "ikun";
        user.fansMedal.level = 17;
        danmu.body = "我xxx没有开挂，你干嘛~哎哟";
        onDanmu(danmu);
        user.fansMedal.name = "粉丝团";
        Thread.sleep(delay);

        //粉丝团21级及舰长
        var guard = new Guard();
        guard.user = user;
        guard.name = "舰长";
        guard.unit = "月";
        guard.level = 3;
        user.guardLevel = 3;
        guard.num = 1;
        guard.price = 138;
        onGuard(guard);
        Thread.sleep(delay);
        user.fansMedal.name = "粉丝团";
        user.fansMedal.level = 21;
        danmu.body = "舰长有什么礼物吗?";
        onDanmu(danmu);
        Thread.sleep(delay);

        //粉丝团25级及提督
        guard.name = "提督";
        guard.level = 2;
        user.guardLevel = 2;
        guard.price = 1998;
        onGuard(guard);
        Thread.sleep(delay);
        user.fansMedal.name = "粉丝团";
        user.fansMedal.level = 25;
        danmu.body = "提督总有礼物吧！！！";
        onDanmu(danmu);
        Thread.sleep(delay);

        //粉丝团29级及总督
        guard.name = "总督";
        guard.level = 1;
        user.guardLevel = 1;
        guard.price = 19998;
        onGuard(guard);
        Thread.sleep(delay);
        user.fansMedal.name = "蒙古人";
        user.fansMedal.level = 29;
        danmu.body = "别激动，这其实是代码生成的总督，希望大家别被骗了";
        onDanmu(danmu);
        user.fansMedal.name = "粉丝团";
        Thread.sleep(delay);

        var sc = new SuperChat();
        sc.user = user;

        sc.time = 30;
        sc.price = 1000;
        sc.body = "我柜子动了，我不玩了";
        onSuperChat(sc);
        Thread.sleep(delay);

        sc.time = 20;
        sc.price = 30;
        sc.body = "SC才能点歌吗";
        onSuperChat(sc);
        Thread.sleep(delay);

        sc.price = 50;
        sc.body = "这不对吧,SC时间怎么走这么快";
        onSuperChat(sc);
        Thread.sleep(delay);

        sc.price = 100;
        sc.body = "鼠鼠你坏事做尽";
        onSuperChat(sc);
        Thread.sleep(delay);

        sc.price = 500;
        sc.body = "把你变成狗";
        onSuperChat(sc);
        Thread.sleep(delay);

        sc.time = 30;
        user.fansMedal.name = "雏草姬";
        sc.price = 2000;
        sc.body = "关注永雏塔菲喵！关注永雏塔菲谢谢喵！";
        onSuperChat(sc);
    }

    public void onDanmu(Danmu danmu) {
        if (config.danmu.isShow && player != null)
            player.sendMessage(danmuText.convert(danmu));
    }

    public void onEmoji(Emoji emoji) {
        if (config.danmu.isShowEmoji && player != null)
            player.sendMessage(danmuText.convert(emoji));
    }

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

    public void onSuperChat(SuperChat sc) {
        if (config.superChat.isShowBossBar) {
            superChatManager.add(sc);
        }
        if (config.superChat.isShowChat && player != null) {
            player.sendMessage(danmuText.convert(sc));
        }
    }

    public void onGuard(Guard guard) {
        if (config.guard.isShow && player != null)
            player.sendMessage(danmuText.convert(guard));
    }

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
        String msg;
        msg = "已断开连接，代码：%d".formatted(code);
        LOGGER.info(msg);
        if (player != null)
            player.sendMessage(Text.literal(msg).formatted(Formatting.GOLD));
    }

    @Override
    public void onError(Exception ex) {
        LOGGER.error("鼠鼠出错", ex);
        String msg = "鼠鼠出错:" + ex.getMessage();
        if (player != null)
            player.sendMessage(Text.literal(msg).formatted(Formatting.RED));
    }

    @Override
    public void onOpen() {
        LOGGER.info("已连接：%d".formatted(room));
        status();
    }

    public void sendDanmu(String message) {
        try {
            DanmuClient.send(config.liveRoom.cookie, String.valueOf(room), message);
        } catch (Exception e) {
            LOGGER.error("鼠鼠出错", e);
            String msg = "鼠鼠出错:" + e.getMessage();
            if (player != null)
                player.sendMessage(Text.literal(msg).formatted(Formatting.RED));
        }
    }
}

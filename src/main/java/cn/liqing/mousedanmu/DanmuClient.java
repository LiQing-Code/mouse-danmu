package cn.liqing.mousedanmu;

import cn.liqing.BLiveClient;
import cn.liqing.DanmuHandler;
import cn.liqing.model.*;
import cn.liqing.mousedanmu.config.ModConfig;
import cn.liqing.mousedanmu.util.ColorPicker;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.BossBarS2CPacket;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import static net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents.Disconnect;
import static net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents.Join;

public class DanmuClient implements DanmuHandler, Join, Disconnect {
    @Nullable PlayerEntity player;
    final BLiveClient bClient;
    final ModConfig config = MouseDanmu.config;
    public final DanmuConverter danmuText = new DanmuText();
    public final ArrayList<DanmuHandler> danmuHandlers = new ArrayList<>();

    public DanmuClient() {
        danmuHandlers.add(this);

        bClient = new BLiveClient();
        bClient.danmuHandlers = danmuHandlers;

        ClientPlayConnectionEvents.JOIN.register(this);
        ClientPlayConnectionEvents.DISCONNECT.register(this);
    }

    public void connect(int room) {
        bClient.room = room;
        if (bClient.isOpen() || bClient.isClosed()) {
            bClient.close();
            bClient.reconnect();
        } else {
            bClient.connect();
        }

        var history = MouseDanmu.client.config.liveRoom.history;
        int i = history.indexOf(room);
        if (i != -1)
            history.remove(i);
        MouseDanmu.config.liveRoom.history.add(0, room);
        MouseDanmu.configHolder.save();
    }

    public void close() {
        bClient.close();
    }

    public void status() {
        if (player == null)
            return;
        MutableText text = Text.translatable("text.mouse-danmu.live-room-status")
                .append(":");
        if (bClient.isOpen()) {
            text = text.append(Text.literal(String.valueOf(bClient.room))
                    .formatted(Formatting.GREEN)
                    .styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,
                                    "https://live.bilibili.com/" + bClient.room))
                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                    Text.translatable("text.mouse-danmu.open-live-room")))));
        } else {
            text = text.append(Text.translatable("text.mouse-danmu.live-room-disconnected"));
        }
        player.sendMessage(text);
    }

    public void test(int delay) throws InterruptedException {
        //普通消息
        var user = new User();
        user.name = "立青";
        user.uid = 345259002;
        user.guardLevel = 0;
        var danmu = new Danmu();
        danmu.user = user;
        danmu.body = "初见，可以点我名字打开主页";
        onDanmu(danmu);
        Thread.sleep(delay);

        //表情
        var emoji = new Emoji();
        emoji.uri = "https://www.bilibili.com/video/BV1GJ411x7h7";
        emoji.body = "这是表情，但没法(至少我不会)显示图片，可以点击查看图片！";
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
        guard.num = 1;
        guard.price = 138;
        onGuard(guard);
        Thread.sleep(delay);
        user.guardLevel = 3;
        user.fansMedal.name = "粉丝团";
        user.fansMedal.level = 21;
        danmu.body = "舰长有什么礼物吗?";
        onDanmu(danmu);
        Thread.sleep(delay);

        //粉丝团25级及提督
        guard.name = "提督";
        guard.level = 2;
        guard.price = 1998;
        onGuard(guard);
        Thread.sleep(delay);
        user.guardLevel = 2;
        user.fansMedal.name = "粉丝团";
        user.fansMedal.level = 25;
        danmu.body = "提督总有礼物吧！！！";
        onDanmu(danmu);
        Thread.sleep(delay);

        //粉丝团29级及总督
        guard.name = "总督";
        guard.level = 1;
        guard.price = 19998;
        onGuard(guard);
        Thread.sleep(delay);
        user.guardLevel = 1;
        user.fansMedal.name = "蒙古人";
        user.fansMedal.level = 29;
        danmu.body = "别激动，这其实是后台代码生成的总督，希望大家别被骗了";
        onDanmu(danmu);
        user.fansMedal.name = "粉丝团";
        Thread.sleep(delay);

        var sc = new SuperChat();
        sc.user = user;
        sc.time = 10;
        sc.price = 30;
        sc.body = "sc才能点歌吗";
        onSuperChat(sc);
        Thread.sleep(delay);

        sc.price = 50;
        sc.body = "这不对吧,进度怎么走这么快";
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

        sc.price = 1000;
        sc.body = "我柜子动力我不玩了";
        onSuperChat(sc);
        Thread.sleep(delay);

        user.fansMedal.name = "雏草姬";
        sc.price = 2000;
        sc.body = "关注永雏塔菲喵！关注永雏塔菲谢谢喵！";
        onSuperChat(sc);
    }

    @Override
    public void onPlayDisconnect(ClientPlayNetworkHandler handler, MinecraftClient client) {
        close();
        player = null;
    }

    @Override
    public void onPlayReady(ClientPlayNetworkHandler handler, PacketSender sender, MinecraftClient client) {
        player = client.player;
        if (MouseDanmu.config.liveRoom.autoConnect &&
                MouseDanmu.config.liveRoom.roomId != 0) {
            connect(MouseDanmu.config.liveRoom.roomId);
        }
    }

    @Override
    public boolean onDanmu(Danmu danmu) {
        if (config.danmu.isShow && player != null)
            player.sendMessage(danmuText.convert(danmu));
        return false;
    }

    @Override
    public boolean onEmoji(Emoji emoji) {
        if (config.danmu.isShowEmoji && player != null)
            player.sendMessage(danmuText.convert(emoji));
        return false;
    }

    @Override
    public boolean onGift(Gift gift) {
        if (config.gift.isShow && player != null) {
            if (config.gift.isShowFreeGiftToOverlayMessage && gift.price <= 0) {
                MinecraftClient.getInstance().inGameHud
                        .setOverlayMessage(danmuText.convert(gift), false);
            } else {
                player.sendMessage(danmuText.convert(gift));
            }
        }
        return false;
    }

    @Override
    public boolean onSuperChat(SuperChat sc) {
        if (config.superChat.isShowBossBar) {
            addSuperChat(sc);
        }
        if (config.superChat.isShowChat && player != null) {
            player.sendMessage(danmuText.convert(sc));
        }
        return false;
    }

    @Override
    public boolean onGuard(Guard guard) {
        if (config.guard.isShow && player != null)
            player.sendMessage(danmuText.convert(guard));
        return false;
    }

    @Override
    public boolean onInteractive(Interactive interactive) {
        if (config.danmu.isShowInteractive && player != null) {
            player.sendMessage(danmuText.convert(interactive));
        }
        if (config.danmu.isShowInteractiveToOverlayMessage)
            MinecraftClient.getInstance().inGameHud.setOverlayMessage(
                    danmuText.convert(interactive), false);
        return false;
    }

    ArrayList<BossBar> bossBars = new ArrayList<>();

    @SuppressWarnings("all")
    public UUID addSuperChat(@NotNull SuperChat sc) {
        UUID uuid = UUID.randomUUID();
        BossBarHud hud = MinecraftClient.getInstance().inGameHud.getBossBarHud();
        var fans = sc.user.fansMedal;
        sc.user.fansMedal = null;
        MutableText text = danmuText
                .convertUser(sc.user)
                .append(Texts.bracketed(Text.literal(String.valueOf(sc.price)))
                        .styled(style -> style.withColor(ColorPicker.superChat(config.superChat, sc.price))))
                .append(Text.literal(sc.body).formatted(Formatting.WHITE));
        ClientBossBar bossBar = new ClientBossBar(
                uuid, text, 1,
                ColorPicker.superChatBossBar(sc.price),
                BossBar.Style.PROGRESS,
                false, false, false);
        sc.user.fansMedal = fans;

        bossBars.add(bossBar);
        BossBarS2CPacket packet = BossBarS2CPacket.add(bossBar);
        MinecraftClient.getInstance().submit(() -> {
            //处理溢出BossBar
            if (bossBars.size() > 4) {
                for (int i = bossBars.size() - 1; i > 0; i--) {
                    if (bossBars.get(i).getPercent() <= 0)
                        bossBars.remove(i);
                }
                if (bossBars.size() > 0) {
                    hud.handlePacket(BossBarS2CPacket.remove(bossBars.get(0).getUuid()));
                    bossBars.remove(0);
                }
            }
            hud.handlePacket(packet);
        });

        float x = 1f / sc.time;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                float percent = bossBar.getPercent();
                percent -= x;
                bossBar.setPercent(percent);
                BossBarS2CPacket p;
                if (percent < 0) {
                    p = BossBarS2CPacket.remove(uuid);
                    this.cancel();
                } else {
                    p = BossBarS2CPacket.updateProgress(bossBar);
                }
                MinecraftClient.getInstance().submit(() -> hud.handlePacket(p));
            }
        }, 0, 1000);
        return uuid;
    }

    @SuppressWarnings("unused")
    public void delSuperChat(UUID uuid) {
        BossBarS2CPacket packet = BossBarS2CPacket.remove(uuid);
        MinecraftClient.getInstance().inGameHud.getBossBarHud().handlePacket(packet);
    }

    int retries = 0;

    @Override
    public boolean onClose(int code, String reason, boolean remote) {
        status();
        if (remote && retries < 4) {
            bClient.reconnect();
            retries++;
            MouseDanmu.LOGGER.error("已断开重连，代码：%d".formatted(code));
        } else {
            MouseDanmu.LOGGER.error("已断开连接，代码：%d".formatted(code));
        }
        return false;
    }

    @Override
    public boolean onError(Exception ex) {
        MouseDanmu.LOGGER.error("鼠鼠出错", ex);
        return false;
    }

    @Override
    public boolean onOpen() {
        MouseDanmu.LOGGER.error("已连接：%d".formatted(bClient.room));
        status();
        retries = 0;
        return false;
    }
}
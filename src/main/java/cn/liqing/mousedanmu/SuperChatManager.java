package cn.liqing.mousedanmu;

import cn.liqing.bili.live.danmu.model.SuperChat;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.network.packet.s2c.play.BossBarS2CPacket;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class SuperChatManager {
    final MinecraftClient client;
    private final DanmuConverter danmuText;
    private final DanmuColorPicker colorPicker;
    final ArrayList<ScBossBar> superChatList = new ArrayList<>();
    final ArrayList<ScBossBar> displayList = new ArrayList<>(4);
    @Nullable Timer handlePacketTimer;

    public SuperChatManager(DanmuConverter danmuText, DanmuColorPicker colorPicker) {
        this.client = MinecraftClient.getInstance();
        this.danmuText = danmuText;
        this.colorPicker = colorPicker;
    }

    public void add(SuperChat sc) {
        UUID uuid = UUID.randomUUID();
        //准备显示文本
        var fans = sc.user.fansMedal;
        sc.user.fansMedal = null;
        MutableText text = danmuText.convertUser(sc.user)
                .append(Texts.bracketed(Text.literal(String.valueOf(sc.price)))
                        .styled(style -> style.withColor(colorPicker.superChat(sc.price))))
                .append(Text.literal(sc.body).formatted(Formatting.WHITE));
        sc.user.fansMedal = fans;

        ScBossBar scBossBar = new ScBossBar(uuid, text,
                DanmuColorPicker.superChatBossBar(sc.price),
                BossBar.Style.PROGRESS, sc.time);

        synchronized (superChatList) {
            superChatList.add(scBossBar);
        }

        if (handlePacketTimer == null) {
            handlePacketTimer = new Timer();
            handlePacketTimer.schedule(handlePackets(), 0, 1000);
        }
    }

    public void clear() {
        if (handlePacketTimer != null) {
            handlePacketTimer.cancel();
            handlePacketTimer = null;
        }
        synchronized (superChatList) {
            superChatList.clear();
        }
        client.submit(() -> client.inGameHud.getBossBarHud().clear());
        displayList.clear();
    }


    TimerTask handlePackets() {
        BossBarHud hud = client.inGameHud.getBossBarHud();
        return new TimerTask() {
            static final int showTime = 15;
            @Override
            public void run() {
                //更新以及删除醒目留言
                for (int i = displayList.size() - 1; i >= 0; i--) {
                    var sc = displayList.get(i);
                    BossBarS2CPacket packet;
                    if (sc.getPercent() <= 0) {
                        displayList.remove(sc);
                        packet = BossBarS2CPacket.remove(sc.getUuid());
                    } else {
                        packet = BossBarS2CPacket.updateProgress(sc);
                    }
                    client.submit(() -> hud.handlePacket(packet));
                    sc.updateDisplay();
                }
                //添加醒目留言到显示列表
                synchronized (superChatList) {
                    for (int i = superChatList.size() - 1; i >= 0; i--) {
                        var sc = superChatList.get(i);
                        sc.updateTime();
                        if (sc.getPercent() <= 0)
                            superChatList.remove(i);
                    }
                    if (superChatList.isEmpty())
                        return;
                    //溢出时暂时移除已经显示showTime秒或以上的
                    if (displayList.size() >= 4 &&
                            superChatList.stream().anyMatch(sc -> sc.displayTime < showTime)) {
                        @Nullable ScBossBar sc = displayList.stream()
                                .filter(s -> s.displayTime >= showTime)
                                .findFirst().orElse(null);
                        if (sc != null) {
                            superChatList.add(sc);
                            displayList.remove(sc);
                            client.submit(() -> hud.handlePacket(BossBarS2CPacket.remove(sc.getUuid())));
                        } else
                            return;
                    }
                    ScBossBar sc = superChatList.stream()
                            .filter(s -> s.displayTime < showTime)
                            .findFirst().orElse(superChatList.get(0));
                    displayList.add(sc);
                    client.submit(() -> hud.handlePacket(BossBarS2CPacket.add(sc)));
                    superChatList.remove(sc);
                }
            }
        };
    }

    static class ScBossBar extends BossBar {
        public float time;
        public int displayTime;

        public void updateTime() {
            percent -= time;
        }

        public void updateDisplay() {
            displayTime++;
            updateTime();
        }

        public ScBossBar(UUID uuid, Text name, Color color, Style style, int time) {
            super(uuid, name, color, style);
            this.time = percent / time;
        }
    }
}
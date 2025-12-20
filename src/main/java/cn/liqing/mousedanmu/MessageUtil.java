package cn.liqing.mousedanmu;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

/**
 * 跨版本消息发送工具类
 * 封装不同 Minecraft 版本的 sendMessage API 差异
 */
public class MessageUtil {
    
    /**
     * 向玩家发送聊天消息（兼容所有支持的 MC 版本）
     * 
     * @param player 玩家实体
     * @param message 消息文本
     */
    public static void sendMessage(@Nullable PlayerEntity player, Text message) {
        if (player == null) return;
        
        /*? if >=1.21 {*/
        player.sendMessage(message, false);
        /*? } else {*/
        /*player.sendMessage(message);
        /*? }*/
    }
    
    /**
     * 向玩家发送操作栏消息（兼容所有支持的 MC 版本）
     * 
     * @param player 玩家实体
     * @param message 消息文本
     */
    public static void sendActionBar(@Nullable PlayerEntity player, Text message) {
        if (player == null) return;
        
        /*? if >=1.21 {*/
        player.sendMessage(message, true);
        /*? } else if >=1.19.4 {*/
        /*player.sendMessage(message, true);
        /*? } else {*/
        /*player.sendMessage(message, net.minecraft.network.MessageType.GAME_INFO, java.util.UUID.randomUUID());
        /*? }*/
    }
}

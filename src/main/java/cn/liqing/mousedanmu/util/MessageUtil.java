package cn.liqing.mousedanmu.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 跨版本消息发送工具类
 * 使用反射处理不同 Minecraft 版本的 sendMessage API 差异
 * 支持 Minecraft 1.19.4 - 1.21.10+
 */
public class MessageUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageUtil.class);
    
    // 缓存反射方法，避免重复查找
    private static Method sendMessageMethod = null;
    private static boolean methodSearched = false;
    private static boolean useTwoParams = false;
    
    static {
        // 在类加载时检测版本
        detectVersion();
    }
    
    /**
     * 检测当前 Minecraft 版本的 sendMessage 方法签名
     */
    private static void detectVersion() {
        try {
            // 尝试获取新版本的方法 (MC 1.21+): sendMessage(Text, boolean)
            sendMessageMethod = PlayerEntity.class.getMethod("sendMessage", Text.class, boolean.class);
            useTwoParams = true;
            LOGGER.info("检测到 Minecraft 1.21+ 版本 API");
        } catch (NoSuchMethodException e) {
            try {
                // 尝试获取旧版本的方法 (MC 1.19.4 - 1.20.x): sendMessage(Text)
                sendMessageMethod = PlayerEntity.class.getMethod("sendMessage", Text.class);
                useTwoParams = false;
                LOGGER.info("检测到 Minecraft 1.19.4-1.20.x 版本 API");
            } catch (NoSuchMethodException ex) {
                LOGGER.error("无法找到 sendMessage 方法，可能不支持当前 Minecraft 版本");
            }
        }
        methodSearched = true;
    }
    
    /**
     * 向玩家发送聊天消息（兼容所有支持的 MC 版本）
     * 
     * @param player 玩家实体
     * @param message 消息文本
     */
    public static void sendMessage(@Nullable PlayerEntity player, Text message) {
        if (player == null || message == null) {
            return;
        }
        
        if (!methodSearched) {
            detectVersion();
        }
        
        try {
            if (sendMessageMethod != null) {
                if (useTwoParams) {
                    // MC 1.21+: player.sendMessage(text, false)
                    sendMessageMethod.invoke(player, message, false);
                } else {
                    // MC 1.19.4-1.20.x: player.sendMessage(text)
                    sendMessageMethod.invoke(player, message);
                }
            } else {
                LOGGER.error("sendMessage 方法未初始化");
            }
        } catch (Exception e) {
            LOGGER.error("发送消息失败", e);
        }
    }
}

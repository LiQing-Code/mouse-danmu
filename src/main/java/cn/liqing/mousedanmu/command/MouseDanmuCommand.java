package cn.liqing.mousedanmu.command;

import cn.liqing.mousedanmu.MouseDanmu;
import cn.liqing.mousedanmu.command.argument.RoomIdArgumentType;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class MouseDanmuCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        var literalCommandNode = dispatcher.register(ClientCommandManager.literal("mousedanmaku")
                .then(ClientCommandManager.literal("connect")
                        .then(ClientCommandManager.argument("roomId", RoomIdArgumentType.roomId())
                                .executes(context -> connect(context.getArgument("roomId", Integer.class)))))
                .then(ClientCommandManager.literal("close")
                        .executes(context -> close()))
                .then(ClientCommandManager.literal("status")
                        .executes(context -> status()))
                .then(ClientCommandManager.literal("test")
                        .then(ClientCommandManager.argument("delay", IntegerArgumentType.integer(0, 10000))
                                .executes(context -> test(context.getArgument("delay", Integer.class))))
                        .executes(context -> test(500))));
//                .then(ClientCommandManager.literal("option")
//                        .executes(context -> option())));

        dispatcher.register(ClientCommandManager.literal("dm").redirect(literalCommandNode));
    }

    public static int connect(int roomId) {
        MouseDanmu.client.connect(roomId);
        return 1;
    }

    public static int close() {
        MouseDanmu.client.close();
        return 1;
    }

    public static int status() {
        MouseDanmu.client.status();
        return 1;
    }

    public static int test(int delay) {
        new Thread(() -> {
            try {
                MouseDanmu.client.test(delay);
            } catch (InterruptedException ignored) {
            }
        }).start();
        return 1;
    }

//    public static int option() {
//        MinecraftClient.getInstance().submit(() -> {
//            var client = MinecraftClient.getInstance();
//            Screen screen = client.currentScreen;
//            Screen configScreen = AutoConfig.getConfigScreen(ModConfig.class, screen).get();
//            //todo 显示一瞬间就没有了
//            client.setScreenAndRender(configScreen);
//        });
//        return 1;
//    }
}

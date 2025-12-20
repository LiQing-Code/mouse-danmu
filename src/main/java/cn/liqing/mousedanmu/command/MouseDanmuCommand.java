package cn.liqing.mousedanmu.command;

import cn.liqing.mousedanmu.MouseDanmu;
import cn.liqing.mousedanmu.command.argument.RoomIdArgumentType;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.argument.MessageArgumentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MouseDanmuCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(MouseDanmuCommand.class);
    private static final ExecutorService executorService = Executors.newCachedThreadPool(r -> {
        Thread thread = new Thread(r);
        thread.setName("MouseDanmu-Command-" + thread.getId());
        thread.setDaemon(true);
        return thread;
    });

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        var literalCommandNode = dispatcher.register(ClientCommandManager.literal("mousedanmu")
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
                        .executes(context -> test(500)))
                .then(ClientCommandManager.argument("message", MessageArgumentType.message())
                        .executes(context -> send(context.getArgument("message", MessageArgumentType.MessageFormat.class).getContents()))));

        dispatcher.register(ClientCommandManager.literal("dm").redirect(literalCommandNode));
    }

    public static int connect(int roomId) {
        MouseDanmu.client.close();
        MouseDanmu.client.connect(roomId);
        MouseDanmu.save();
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
        executorService.submit(() -> {
            try {
                MouseDanmu.client.test(delay);
            } catch (InterruptedException e) {
                LOGGER.debug("Test command interrupted", e);
                Thread.currentThread().interrupt();
            }
        });
        return 1;
    }

    public static int send(String message) {
        executorService.submit(() -> MouseDanmu.client.sendDanmu(message));
        return 1;
    }
}

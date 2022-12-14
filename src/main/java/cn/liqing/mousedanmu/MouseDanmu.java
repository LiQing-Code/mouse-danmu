package cn.liqing.mousedanmu;

import cn.liqing.mousedanmu.command.MouseDanmuCommand;
import cn.liqing.mousedanmu.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class MouseDanmu implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger(MouseDanmu.class);
    public static final String MOD_ID = "mouse-danmu";
    public static ModConfig config;
    public static ConfigHolder<ModConfig> configHolder;
    public static DanmuClient client;

    @Override
    public void onInitializeClient() {
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        configHolder = AutoConfig.getConfigHolder(ModConfig.class);
        config = configHolder.getConfig();
        client = new DanmuClient(config);
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                MouseDanmuCommand.register(dispatcher));
    }
}

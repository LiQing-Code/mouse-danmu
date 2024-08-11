package cn.liqing.mousedanmu;

import cn.liqing.mousedanmu.command.MouseDanmuCommand;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

public class MouseDanmu implements ClientModInitializer {
    public static final String MOD_ID = "mousedanmu";
    private static ConfigHolder<ModConfig> configHolder;
    public static MouseDanmuClient client;

    @Override
    public void onInitializeClient() {
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        configHolder = AutoConfig.getConfigHolder(ModConfig.class);
        client = new MouseDanmuClient(configHolder.getConfig());
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                MouseDanmuCommand.register(dispatcher));
    }

    public static ModConfig getConfig() {
        return configHolder.getConfig();
    }

    public static void save(){
        configHolder.save();
    }
}

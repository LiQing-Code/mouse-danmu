package cn.liqing.mousedanmu.command.argument;

import cn.liqing.mousedanmu.MouseDanmu;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import java.util.concurrent.CompletableFuture;

public class RoomIdArgumentType implements ArgumentType<Integer> {
    public static RoomIdArgumentType roomId() {
        return new RoomIdArgumentType();
    }

    @Override
    public Integer parse(StringReader reader) throws CommandSyntaxException {
        return reader.readInt();
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        MouseDanmu.getConfig().liveRoom.history.forEach(builder::suggest);
        return builder.buildFuture();
    }
}

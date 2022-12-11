package cn.liqing.mousedanmu;

import cn.liqing.model.*;
import net.minecraft.text.MutableText;

public interface DanmuConverter {
    MutableText convertUser(User user);

    MutableText convert(Danmu danmu);

    MutableText convert(Gift gift);

    MutableText convert(Emoji emoji);

    MutableText convert(SuperChat sc);

    MutableText convert(Guard guard);

    MutableText convert(Interactive interactive);
}

package piotro15.chatfilter;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ModConfig {
    public static ConfigClassHandler<ModConfig> HANDLER = ConfigClassHandler.createBuilder(ModConfig.class)
            .id(Identifier.of("chatfilter", "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("chatfilter.json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry
    boolean filter_chat = true;

    @SerialEntry
    boolean filter_game = true;

    @SerialEntry(comment = "List of words to filter out")
    List<String> filters = new ArrayList<>() {
        {
            add("badword");
            add("anotherbadword");
        }
    };
}

package piotro15.chatfilter;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.text.Text;

import java.util.ArrayList;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> YetAnotherConfigLib.createBuilder()
                .title(Text.of("Chat Filter Config"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.of("General"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.of("Filter Player Messages"))
                                .description(OptionDescription.of(Text.of("Toggle whether player messages should be filtered. Please note that most servers display player chat using server messages!")))
                                .binding(true, () -> ModConfig.HANDLER.instance().filter_chat, newValue -> ModConfig.HANDLER.instance().filter_chat = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.of("Filter Server Messages"))
                                .description(OptionDescription.of(Text.of("Toggle whether server messages should be filtered. Please note that most servers display player chat using server messages!")))
                                .binding(true, () -> ModConfig.HANDLER.instance().filter_game, newValue -> ModConfig.HANDLER.instance().filter_game = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(ListOption.<String>createBuilder()
                                .name(Text.of("Filters"))
                                .description(OptionDescription.of(Text.of("List of filters that will be used to filter out messages")))
                                .binding(new ArrayList<>(), () -> ModConfig.HANDLER.instance().filters, newValue -> ModConfig.HANDLER.instance().filters = newValue)
                                .controller(StringControllerBuilder::create)
                                .initial("")
                                .build())
                        .build())
                .save(() -> ModConfig.HANDLER.save())
                .build().generateScreen(parent);
    }
}

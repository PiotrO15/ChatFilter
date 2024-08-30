package piotro15.chatfilter;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;

import java.util.Collection;
import java.util.Objects;

public class ChatFilterClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        if (!ModConfig.HANDLER.load()) {
            ModConfig.HANDLER.save();
        }
        ModConfig.HANDLER.load();

        ClientReceiveMessageEvents.ALLOW_GAME.register((message, overlay) -> {
            if (!ModConfig.HANDLER.instance().filter_game) {
                return true;
            }

            String stringMessage = message.getString();
            return canBeDisplayed(stringMessage);
        });

        ClientReceiveMessageEvents.ALLOW_CHAT.register((message, signedMessage, sender, parameters, receptionTimestamp) -> {
            if (!ModConfig.HANDLER.instance().filter_chat) {
                return true;
            }

            String stringMessage = message.getString();
            return canBeDisplayed(stringMessage);
        });
    }

    private static boolean canBeDisplayed(String message) {
        Collection<PlayerListEntry> players = Objects.requireNonNull(MinecraftClient.getInstance().getNetworkHandler()).getPlayerList();

        for (String filter : ModConfig.HANDLER.instance().filters) {
            if (filter.contains("<online>")) {
                for (PlayerListEntry player : players) {
                    if (message.contains(filter.replace("<online>", player.getProfile().getName()))) {
                        return false;
                    }
                }
            } else if (message.contains(filter)) {
                return false;
            }
        }

        return true;
    }
}

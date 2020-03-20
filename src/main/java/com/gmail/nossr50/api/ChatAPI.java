package com.gmail.nossr50.api;

import com.gmail.nossr50.chat.ChatManager;
import com.gmail.nossr50.chat.ChatManagerFactory;
import com.gmail.nossr50.chat.PartyChatManager;
import com.gmail.nossr50.datatypes.chat.ChatMode;
import com.gmail.nossr50.party.PartyManager;
import com.gmail.nossr50.util.player.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public final class ChatAPI {
    private ChatAPI() {}

    /**
     * Send a message to all members of a party
     * </br>
     * This function is designed for API usage.
     *
     * @param plugin The plugin sending the message
     * @param sender The name of the sender
     * @param displayName The display name of the sender
     * @param party The name of the party to send to
     * @param message The message to send
     */
    public static void sendPartyChat(Plugin plugin, String sender, String displayName, String party, String message) {
        getPartyChatManager(plugin, party).handleChat(sender, displayName, message);
    }

    /**
     * Send a message to all members of a party
     * </br>
     * This function is designed for API usage.
     *
     * @param plugin The plugin sending the message
     * @param sender The name of the sender to display in the chat
     * @param party The name of the party to send to
     * @param message The message to send
     */
    public static void sendPartyChat(Plugin plugin, String sender, String party, String message) {
        getPartyChatManager(plugin, party).handleChat(sender, message);
    }

    /**
     * Check if a player is currently talking in party chat.
     *
     * @param player The player to check
     * @return true if the player is using party chat, false otherwise
     */
    public static boolean isUsingPartyChat(Player player) {
        return UserManager.getPlayer(player).isChatEnabled(ChatMode.PARTY);
    }

    /**
     * Check if a player is currently talking in party chat.
     *
     * @param playerName The name of the player to check
     * @return true if the player is using party chat, false otherwise
     */
    public static boolean isUsingPartyChat(String playerName) {
        return UserManager.getPlayer(playerName).isChatEnabled(ChatMode.PARTY);
    }

    /**
     * Toggle the party chat mode of a player.
     *
     * @param player The player to toggle party chat on.
     */
    public static void togglePartyChat(Player player) {
        UserManager.getPlayer(player).toggleChat(ChatMode.PARTY);
    }

    /**
     * Toggle the party chat mode of a player.
     *
     * @param playerName The name of the player to toggle party chat on.
     */
    public static void togglePartyChat(String playerName) {
        UserManager.getPlayer(playerName).toggleChat(ChatMode.PARTY);
    }

    private static ChatManager getPartyChatManager(Plugin plugin, String party) {
        ChatManager chatManager = ChatManagerFactory.getChatManager(plugin, ChatMode.PARTY);
        ((PartyChatManager) chatManager).setParty(PartyManager.getParty(party));

        return chatManager;
    }
}

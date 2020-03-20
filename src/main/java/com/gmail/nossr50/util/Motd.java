package com.gmail.nossr50.util;

import com.gmail.nossr50.config.Config;
import com.gmail.nossr50.datatypes.skills.SkillType;
import com.gmail.nossr50.locale.LocaleLoader;
import com.gmail.nossr50.mcMMO;
import com.gmail.nossr50.util.skills.PerksUtils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

import java.text.DecimalFormat;

public final class Motd {
    public static final String PERK_PREFIX = LocaleLoader.getString("MOTD.PerksPrefix") + " ";
    private static final PluginDescriptionFile pluginDescription = mcMMO.p.getDescription();

    private Motd() {}

    public static void displayAll(Player player) {
        displayVersion(player, pluginDescription.getVersion());
        displayXpPerks(player);
        displayCooldownPerks(player);
        displayActivationPerks(player);
        displayLuckyPerks(player);
        displayWebsite(player, pluginDescription.getWebsite());
    }

    /**
     * Display version info.
     *
     * @param player  Target player
     * @param version Plugin version
     */
    public static void displayVersion(Player player, String version) {
        if (Permissions.showversion(player)) {
            player.sendMessage(LocaleLoader.getString("MOTD.Version", version));
        }
    }

    /**
     * Display XP perks.
     *
     * @param player Target player
     */
    public static void displayXpPerks(Player player) {
        for (SkillType skill : SkillType.values()) {
            if (PerksUtils.handleXpPerks(player, 1, skill) > 1) {
                player.sendMessage(PERK_PREFIX + LocaleLoader.getString("Effects.Template", LocaleLoader.getString("Perks.XP.Name"), LocaleLoader.getString("Perks.XP.Desc")));
                return;
            }
        }
    }

    /**
     * Display cooldown perks.
     *
     * @param player Target player
     */
    public static void displayCooldownPerks(Player player) {
        double cooldownReduction = 1 - (PerksUtils.handleCooldownPerks(player, 12) / 12.0);

        if (cooldownReduction > 0.0) {
            DecimalFormat percent = new DecimalFormat("##0.00%");
            player.sendMessage(PERK_PREFIX + LocaleLoader.getString("Effects.Template", LocaleLoader.getString("Perks.Cooldowns.Name"), LocaleLoader.getString("Perks.Cooldowns.Desc", percent.format(cooldownReduction))));
        }
    }

    /**
     * Display activiation perks.
     *
     * @param player Target player
     */
    public static void displayActivationPerks(Player player) {
        int perkAmount = PerksUtils.handleActivationPerks(player, 0, 0);

        if (perkAmount > 0) {
            player.sendMessage(PERK_PREFIX + LocaleLoader.getString("Effects.Template", LocaleLoader.getString("Perks.ActivationTime.Name"), LocaleLoader.getString("Perks.ActivationTime.Desc", perkAmount)));
        }
    }

    /**
     * Display "lucky" perks.
     *
     * @param player Target player
     */
    public static void displayLuckyPerks(Player player) {
        for (SkillType skill : SkillType.values()) {
            if (Permissions.lucky(player, skill)) {
                player.sendMessage(PERK_PREFIX + LocaleLoader.getString("Effects.Template", LocaleLoader.getString("Perks.Lucky.Name"), LocaleLoader.getString("Perks.Lucky.Desc.Login")));
                return;
            }
        }
    }

    /**
     * Display website info.
     *
     * @param player  Target player
     * @param website Plugin website
     */
    public static void displayWebsite(Player player, String website) {
        player.sendMessage(LocaleLoader.getString("MOTD.Website", website));
    }
}

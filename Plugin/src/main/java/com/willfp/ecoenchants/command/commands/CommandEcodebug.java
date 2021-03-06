package com.willfp.ecoenchants.command.commands;

import com.willfp.ecoenchants.EcoEnchantsPlugin;
import com.willfp.ecoenchants.command.AbstractCommand;
import com.willfp.ecoenchants.enchantments.EcoEnchant;
import com.willfp.ecoenchants.enchantments.EcoEnchants;
import com.willfp.ecoenchants.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public final class CommandEcodebug extends AbstractCommand {
    public CommandEcodebug() {
        super("ecodebug", "ecoenchants.ecodebug", true);
    }

    @Override
    public void onExecute(CommandSender sender, List<String> args) {
        Logger.info("--------------- BEGIN DEBUG ----------------");
        Player player = (Player) sender;
        player.sendMessage("Held Item: " + player.getInventory().getItemInMainHand().toString());
        Logger.info("");

        Logger.info("Running Version: " + EcoEnchantsPlugin.getInstance().getDescription().getVersion());
        Logger.info("");

        Logger.info("Held Item: " + player.getInventory().getItemInMainHand().toString());
        Logger.info("");

        Logger.info("EcoEnchants.getAll(): " + EcoEnchants.getAll().toString());
        Logger.info("");

        Logger.info("Enchantment.values(): " + Arrays.toString(Enchantment.values()));
        Logger.info("");

        try {
            Field byNameField = Enchantment.class.getDeclaredField("byName");
            byNameField.setAccessible(true);
            Map<String, Enchantment> byName = (Map<String, Enchantment>) byNameField.get(null);
            Logger.info("Enchantment.byName: " + byName.toString());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        Logger.info("");


        List<Enchantment> extern = Arrays.stream(Enchantment.values()).collect(Collectors.toList());
        extern.removeAll(EcoEnchants.getAll().stream().map(EcoEnchant::getEnchantment).collect(Collectors.toList()));
        Logger.info("External/Vanilla Enchantments: " + extern.toString());
        Logger.info("");

        Logger.info("Installed Plugins: " + Arrays.stream(Bukkit.getPluginManager().getPlugins()).map(Plugin::getName).collect(Collectors.toList()).toString());
        Logger.info("");

        Set<EcoEnchant> withIssues = new HashSet<>();
        EcoEnchants.getAll().forEach(enchant -> {
            if(enchant.getRarity() == null) withIssues.add(enchant);
            if(enchant.getRawTargets().isEmpty()) withIssues.add(enchant);
        });
        Logger.info("Enchantments with evident issues: " + withIssues.toString());
        Logger.info("");

        Logger.info("Server Information: ");
        Logger.info("Players Online: " + Bukkit.getServer().getOnlinePlayers().size());
        Logger.info("Bukkit IP: " + Bukkit.getIp());
        Logger.info("Running Version: " + Bukkit.getVersion() + ", Bukkit Version: " + Bukkit.getBukkitVersion() + ", Alt Version: " + Bukkit.getServer().getVersion());
        Logger.info("--------------- END DEBUG ----------------");
    }
}

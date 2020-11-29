package me.bait.plugins;

import org.bukkit.Bukkit;
import org.bukkit.block.ShulkerBox;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import net.md_5.bungee.api.ChatColor;

//.. _____________________ ..#
////-- // WARNING \\ -- 
////-- THIS WORK IS UNDER EXCLUSIVE COPRIGHT FOR BAITCORP LABS. DISTRIBUTION, MODIFICATION, SELLING, OR SHARING THIS WITHOUT EXPLICIT PERMISSION FROM BAITCORP LABS IS STRICTLY PROHIBITED.
////-- LICENSED FOR MODIFICATION TO AND ONLY TO: "BAITCORP LABS" [FR], "CCHOST PARIS" [FR].
////-- THIS PROGRAM IS FREE. BY DOWNLOADING THIS YOU AGREE NOT TO SHARE OR MODIFY IT WITHOUT A LICENSE.
////-- 
////-- DupePlugin Sebastian Giheta 2019-2020
//.. _____________________ ..#

public class restriction implements Listener {

	static FileConfiguration c = dupe.getPlugin().getConfig();

	public static void clearfunc(Player p) {
		for (org.bukkit.inventory.ItemStack item : p.getInventory().getContents()) {
			if (item != null && item.getType() != null && c.contains(dupe.g000) && c.getStringList(dupe.g000) != null
					&& !(c.getStringList(dupe.g000).isEmpty())) {
				for (String s : c.getStringList(dupe.g000)) {
					if (s.equals(item.getType().toString()) && item.getAmount() > c.getInt(dupe.f000)) {
						item.setAmount(c.getInt(dupe.f000));
					}
				}
			}
			if (item != null && isShulkerBox(item.getType().toString()) && dupe.getPlugin().getConfig().getBoolean("illegal-check-shulkers")) {
				BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
				if (meta == null)
					return;
				ShulkerBox box = (ShulkerBox) meta.getBlockState();
				for (org.bukkit.inventory.ItemStack i : box.getInventory().getContents()) {
					// check counts
					if (i != null && i.getType() != null && c.contains(dupe.g000) && c.getStringList(dupe.g000) != null
							&& !(c.getStringList(dupe.g000).isEmpty())) {
						for (String s : c.getStringList(dupe.g000)) {
							if (s.equals(i.getType().toString()) && i.getAmount() > c.getInt(dupe.f000)) {
								i.setAmount(c.getInt(dupe.f000));
							}
						}
					}
					if (i != null && i.getType() != null && c.contains(dupe.i000) && c.getStringList(dupe.i000) != null
							&& !(c.getStringList(dupe.i000).isEmpty())) {
						for (String s : c.getStringList(dupe.i000)) {
							if (s.equals(i.getType().toString()) && dupe.getPlugin().getConfig().getInt(dupe.j000) == 1) {
								i.setAmount(0);
							}
						}
					}
				}
				meta.setBlockState(box);
				item.setItemMeta(meta);
			}
		}
		org.bukkit.inventory.ItemStack item = p.getInventory().getItemInOffHand();
		if (item != null && item.getType() != null) {
			for (String s : c.getStringList("limited-items")) {
				if (s.equals(item.getType().toString()) && item.getAmount() > c.getInt("maxstacksize")) {
					item.setAmount(c.getInt("maxstacksize"));
				}
			}
		}
	}

	public static String colorItem(String s) {
		switch (s) {
		case "BLACK":
			return "7";
		case "BLUE":
			return "1";
		case "BROWN":
			return "7";
		case "CYAN":
			return "3";
		case "GRAY":
			return "8";
		case "GREEN":
			return "2";
		case "LIGHT_BLUE":
			return "b";
		case "LIME":
			return "a";
		case "MAGENTA":
			return "5";
		case "ORANGE":
			return "6";
		case "PINK":
			return "d";
		case "PURPLE":
			return "5";
		case "RED":
			return "c";
		case "WHITE":
			return "f";
		case "YELLOW":
			return "e";
		case "LIGHT_GRAY":
			return "7";
		case "SILVER":
			return "7";
		default:
			return dupe.getPlugin().getConfig().getString(dupe.h000);
		}
	}

	public static String getFirstPart(String s) {
		int i = s.indexOf("_");
		if (i == -1 || i == 0)
			return s;
		String t = s.substring(0, i);
		if (t.equals("DARK") || t.equals("LIGHT")) {
			int j = s.substring(i + 1).indexOf("_");
			j = j + i + 1;
			t = s.substring(0, j);
			return t;
		}
		return t;
	}

	public static boolean isShulkerBox(String s) {
		switch (s) {
		case "BLACK_SHULKER_BOX":
		case "BLUE_SHULKER_BOX":
		case "BROWN_SHULKER_BOX":
		case "CYAN_SHULKER_BOX":
		case "GRAY_SHULKER_BOX":
		case "GREEN_SHULKER_BOX":
		case "LIGHT_BLUE_SHULKER_BOX":
		case "LIME_SHULKER_BOX":
		case "MAGENTA_SHULKER_BOX":
		case "ORANGE_SHULKER_BOX":
		case "PINK_SHULKER_BOX":
		case "PURPLE_SHULKER_BOX":
		case "RED_SHULKER_BOX":
		case "WHITE_SHULKER_BOX":
		case "YELLOW_SHULKER_BOX":
		case "LIGHT_GRAY_SHULKER_BOX":
		case "SILVER_SHULKER_BOX":
			return true;
		default:
			return false;
		}
	}

	public static void log(String string) {
		Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', string));
	}

	public static void logContents(ItemStack item) {
		BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
		if (meta == null)
			return;
		ShulkerBox box = (ShulkerBox) meta.getBlockState();
		log("&" + colorItem(getFirstPart(item.getType().toString())) + item.getType().toString()
				+ "&" + dupe.getPlugin().getConfig().getString("log-color") + " has contents:");
		int c = 0;
		for (org.bukkit.inventory.ItemStack i : box.getInventory().getContents()) {
			if (i != null) {
				c++;
				log("&" + dupe.getPlugin().getConfig().getString(dupe.h000) + "  - &"
						+ colorItem(getFirstPart(i.getType().toString())) + i.getType().toString() + "&"
						+ dupe.getPlugin().getConfig().getString("log-color") + " (x" + i.getAmount() + ")");
			}
		}
		log("&" + dupe.getPlugin().getConfig().getString(dupe.h000) + "  - (" + c + " items)");
	}

	public static void reload() {
		c = dupe.getPlugin().getConfig();
	}

}

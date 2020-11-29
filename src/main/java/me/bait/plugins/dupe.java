package me.bait.plugins;

import java.io.File;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.block.ShulkerBox;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

//.. _____________________ ..#
//// -- // WARNING \\ -- 
//// -- THIS WORK IS UNDER EXCLUSIVE COPRIGHT FOR BAITCORP LABS. DISTRIBUTION, MODIFICATION, SELLING, OR SHARING THIS WITHOUT EXPLICIT PERMISSION FROM BAITCORP LABS IS STRICTLY PROHIBITED.
//// -- LICENSED FOR MODIFICATION TO AND ONLY TO: "BAITCORP LABS" [FR], "CCHOST PARIS" [FR].
//// -- THIS PROGRAM IS FREE. BY DOWNLOADING THIS YOU AGREE NOT TO SHARE OR MODIFY IT WITHOUT A LICENSE.
//// -- 
//// -- DupePlugin Sebastian Giheta 2019-2020
//.. _____________________ ..#

public class dupe extends JavaPlugin implements Listener {

	public static final String k000 = "illegal-check-shulkers";
	public static final String j000 = "illegaldupemode";
	public static final String i000 = "illegal-items";

	public static final String g000 = "limited-items";
	public static final String f000 = "maxstacksize";
	public static final String h000 = "log-color";

	static Random r = new Random();

	public static dupe getPlugin() {
		return getPlugin(dupe.class);
	}

	private static int getRandomNumberInRange(int min, int max) {
		return r.nextInt((max - min) + 1) + min;
	}

	FileConfiguration c = getConfig();

	HashMap<UUID, Integer> d = new HashMap<UUID, Integer>();

	int looped = 0;

	int looped2 = 0;

	int runcommand = 0;

	int duping2 = 0;

	int return1 = 0;

	int remove = 0;

	int restrict = 0;

	@EventHandler
	public void onClick(PrepareItemCraftEvent e) {
		for (HumanEntity entity : e.getViewers()) {
			if (entity instanceof Player)
				d.put(entity.getUniqueId(), Integer.valueOf(4));
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		d.remove(e.getPlayer().getUniqueId());
		for (HumanEntity h : e.getInventory().getViewers()) {
			if (Bukkit.getPlayer(h.getName()) != null) {
				restriction.clearfunc(Bukkit.getPlayer(h.getName()));
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (args.length == 1 && args[0].equalsIgnoreCase("reload") && sender.isOp()) {
			this.reloadConfig();
			c = getConfig();
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&"
					+ dupe.getPlugin().getConfig().getString(h000) + "[BCLABS] DupePlugin Configuration reloaded."));
		} else {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
					"&" + dupe.getPlugin().getConfig().getString(h000) + "This server is running DupePlugin &a"
							+ this.getDescription().getVersion() + "&" + dupe.getPlugin().getConfig().getString(h000)
							+ " by Bubba#8888 with BAITCORP [BCLABS] https://bit.ly/37hPpJj"));
		}
		return true;
	}

	@Override
	public void onDisable() {
		d.clear();
		Bukkit.getScheduler().cancelTasks(this);
		restriction.log("&" + dupe.getPlugin().getConfig().getString(h000)
				+ "[BCLabs] DupePlugin (1.12 mode) disabled! Thank you for using BCLabs plugins. We hope to see you again soon. <3");
	}

	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, (Plugin) this);
		this.getCommand("dp").setExecutor(this);
		this.getCommand("dupeplugin").setExecutor(this);
		getServer().getPluginManager().registerEvents(new restriction(), (Plugin) this);
		restriction.log("&9[BCLabs]&" + dupe.getPlugin().getConfig().getString(h000)
				+ " 1.12 Dupe Re-Enabled, thanks for chosing baitcorphost plugins!");
		restriction.log("&9[BCLabs]&" + dupe.getPlugin().getConfig().getString(h000)
				+ "  - This project is non-profit. Baitcorp makes it's income through premium plugins such as");
		restriction.log("&9[BCLabs]&" + dupe.getPlugin().getConfig().getString(h000)
				+ "        ExploitsX. Support us by getting it here: https://www.mc-market.org/resources/17497/");
		File dir = new File("plugins/dupeplugin");
		if (!dir.exists())
			dir.mkdirs();
		this.saveDefaultConfig();
		Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) dupe.getPlugin(), new Runnable() {
			public void run() {
				for (Player player : Bukkit.getOnlinePlayers()) {
					if (d.containsKey(player.getUniqueId())) {
						Integer amt = d.get(player.getUniqueId());
						if (amt <= 0) {
							d.remove(player.getUniqueId());
						} else {
							amt--;
							d.put(player.getUniqueId(), amt);
						}
					}
				}
			}
		}, 5L, 20L);
		Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) dupe.getPlugin(), new Runnable() {
			public void run() {
				d.clear();
			}
		}, 5L, 432000L);
	}

	@EventHandler
	public void onItemMove(PlayerItemHeldEvent e) {
		restriction.clearfunc(e.getPlayer());
	}

	@EventHandler
	public void onKick(PlayerKickEvent e) {
		d.remove(e.getPlayer().getUniqueId());
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		d.remove(e.getPlayer().getUniqueId());
	}

	@EventHandler
	public void onPickup(final EntityPickupItemEvent e) {
		if (!(e.getEntity() instanceof Player))
			return;
		final Player p = (Player) e.getEntity();
		final Item i = e.getItem();
		if (d.containsKey(p.getUniqueId()) && d.get(p.getUniqueId()) != 0) {
			if (c.getBoolean("dev"))
				restriction.log("-1");
			final Thread t = new Thread(new Runnable() {
				public void run() {
					try {
						looped = 0;
						looped2 = 1;
						remove = 0;
						duping2 = 0;
						restrict = 0;
						if (c.getBoolean("dev"))
							restriction.log("1");
						int randamt = getRandomNumberInRange(getConfig().getInt("min"), getConfig().getInt("max"));
						if (i != null && i.getType() != null && getConfig().contains(g000)
								&& c.getStringList(g000) != null && !(c.getStringList(g000).isEmpty())) {
							if (c.getBoolean("dev"))
								restriction.log("1.a1");
							for (String s : c.getStringList(g000)) {
								if (c.getBoolean("dev"))
									restriction.log("1.a2");
								if (s.equals(i.getItemStack().getType().toString())) {
									if (c.getBoolean("dev"))
										restriction.log("1.a3");
									randamt = c.getInt(f000);
								}
							}
						}
						if (i.getType() != null && getConfig().contains(dupe.i000)
								&& getConfig().getStringList(dupe.i000) != null
								&& !(getConfig().getStringList(dupe.i000).isEmpty())) {
							if (c.getBoolean("dev"))
								restriction.log("1.b1");
							if (i != null && restriction.isShulkerBox(i.getItemStack().getType().toString())
									&& dupe.getPlugin().getConfig().getBoolean("illegal-check-shulkers")) {
								BlockStateMeta meta = (BlockStateMeta) i.getItemStack().getItemMeta();
								if (meta == null)
									return;
								ShulkerBox box = (ShulkerBox) meta.getBlockState();
								if (c.getBoolean("dev"))
									restriction.log("1.c1");
								for (org.bukkit.inventory.ItemStack item : box.getInventory().getContents()) {
									if (item != null) {
										for (String s : getConfig().getStringList(dupe.i000)) {
											if (c.getBoolean("dev"))
												restriction.log("1.c2");
											if (s.equalsIgnoreCase(item.getType().toString())) {
												if (c.getBoolean("dev"))
													restriction.log("1.c3");
												remove = getConfig().getInt(j000);
											}
										}
									}
								}
								if (c.getBoolean("dev"))
									restriction.log("1.1");
								if (remove != 0) {
									if (c.getBoolean("dev"))
										restriction.log("1.1");
									meta.setBlockState(box);
									i.getItemStack().setItemMeta(meta);
								}
								if (c.getBoolean("dev"))
									restriction.log("1.2");
								if (c.getBoolean("disable-shulkers")) {
									remove = 2;
								}
							}
							if (c.getBoolean("dev"))
								restriction.log("1.3");
							for (String s : getConfig().getStringList(dupe.i000)) {
								if (c.getBoolean("dev"))
									restriction.log("1.b2");
								if (s.equalsIgnoreCase(i.getItemStack().getType().toString())) {
									if (c.getBoolean("dev"))
										restriction.log("1.b3");
									remove = getConfig().getInt(j000);
								}
							}
						}
						if (c.getBoolean("dev"))
							restriction.log("2");
						for (org.bukkit.inventory.ItemStack item : p.getInventory().getContents()) {
							if (item != null && item.getType() != null
									&& (item.getType().toString().endsWith("PLANKS") || item.getType().toString().endsWith("WOOD") || item.getType().toString().endsWith("LOG"))) {
								if (item.getAmount() >= 48 && duping2 != 1) {
									duping2 = 1;
									item.setAmount(0);
								}
							}
						}
						if (c.getBoolean("dev"))
							restriction.log("3");
						for (org.bukkit.inventory.ItemStack item : p.getInventory().getContents()) {
							if (duping2 == 0)
								return;
							if (looped == 1)
								return;
							if (c.getBoolean("dev"))
								restriction.log("4");
							if (item != null && looped2 == 1 && item.getType() == i.getItemStack().getType()) {
								//
								if (c.getBoolean("dev"))
									restriction.log("5");
								if (remove == 0) {
									item.setAmount(randamt);
								} else if (remove == 1) {
									item.setAmount(0);
									if (getConfig().getBoolean("log")) {
										restriction.log("&" + dupe.getPlugin().getConfig().getString(h000) + "[BCLabs] "
												+ "&" + getConfig().getString(h000) + p.getName()
												+ " FAILED (Illegal detected) dupe item: " + "&"
												+ restriction
														.colorItem(restriction.getFirstPart(item.getType().toString()))
												+ item.getType().toString());
										if (getConfig().getBoolean("log-shulkers")
												&& restriction.isShulkerBox(item.getType().toString())) {
											restriction.logContents(item);
										}
									}
									return;
								} else if (remove == 2) {
									if (getConfig().getBoolean("log")) {
										restriction.log("&" + dupe.getPlugin().getConfig().getString(h000) + "[BCLabs] "
												+ "&" + getConfig().getString(h000) + p.getName()
												+ " FAILED (Cancel dupe) dupe item: " + "&"
												+ restriction
														.colorItem(restriction.getFirstPart(item.getType().toString()))
												+ item.getType().toString());
										if (getConfig().getBoolean("log-shulkers")
												&& restriction.isShulkerBox(item.getType().toString())) {
											restriction.logContents(item);
										}
									}
									return;
								}
								if (c.getBoolean("dev"))
									restriction.log("6");
								looped = 1;
								if (getConfig().getBoolean("log")) {
									restriction.log("&" + dupe.getPlugin().getConfig().getString(h000) + "[BCLabs] "
											+ "&" + getConfig().getString(h000) + p.getName() + " duped item: " + "&"
											+ restriction.colorItem(restriction.getFirstPart(item.getType().toString()))
											+ item.getType().toString());
									if (getConfig().getBoolean("log-shulkers")
											&& restriction.isShulkerBox(item.getType().toString())) {
										restriction.logContents(item);
									}
								}
								runcommand = 1;
								d.put(p.getUniqueId(), Integer.valueOf(0));
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			if (c.getBoolean("dev"))
				restriction.log("0");
			Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) dupe.getPlugin(), new Runnable() {
				public void run() {
					t.start();
				}
			}, 2L);
			if (c.getBoolean("dev"))
				restriction.log("7");
		}
	}
}
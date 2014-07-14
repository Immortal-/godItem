package us.craft77.god;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Achievement;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Util extends JavaPlugin {

	/**
	 * This function is called when the user run's a command to god an item it
	 * then get's the meta of the item and set's a lore and name. Then loops
	 * through the enchantment adding them to the item.
	 * 
	 * @param p This is the player that issued the command.
	 * */
	public void enchant(Player p) {
		ItemMeta meta = p.getItemInHand().getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',getConfig().getString("Strings.Colored_ItemName"))); // set's display name in item meta to Go Weapon
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Strings.Colored_Lorename")));
		meta.setLore(lore);
		p.getItemInHand().setItemMeta(meta); // Save new item meta
		for (Enchantment enc : Enchantment.values()) {
			p.getItemInHand().addUnsafeEnchantment(enc, 127); // Give item in hand every enchant at level 127
		}
		playerText(p, "You have made a god item, do not share these.",ChatColor.RED);
	}

	public void unEnchant(Player p) {
		removeLore(p);
		removeItemName(p);
		for (Enchantment enc : Enchantment.values()) {
			p.getItemInHand().removeEnchantment(enc);
		}
		playerText(p, "You have degoded this item.", ChatColor.RED);
	}

	public void removeItemName(Player p) {
		ItemMeta meta = p.getItemInHand().getItemMeta();
		if (meta.hasDisplayName()) {
			meta.setDisplayName(null);
			p.getItemInHand().setItemMeta(meta);
		}
	}

	public void removeLore(Player p) {
		ItemMeta meta = p.getItemInHand().getItemMeta();
		if (meta.hasLore()) {
			meta.setLore(null);
			p.getItemInHand().setItemMeta(meta);
		}
	}

	public void addAchievements(Player player) {
		try {
			if (player.hasPermission("godItem.achievements")) {
				for (Achievement ach : Achievement.values()) {
					player.awardAchievement(ach);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Simple function to log a string to console.
	public void logText(String text) {
		getLogger().info(text);
	}

	// Simple function to Display a colored text message to specified player.
	public void playerText(Player player, String text, ChatColor color) {
		player.sendMessage(color + text);
	}

	public void loadConfiguration() {
		// See "Creating you're defaults"
		getConfig().addDefault("permissions.Always_allow_ops", true);
		getConfig().addDefault("Strings.Colored_ItemName", "&aGod Weapon");
		getConfig().addDefault("Strings.Colored_Lorename",
				"&dFor the god's usage only!");
		getConfig().options().copyDefaults(true);
		// Save the config whenever you manipulate it
		saveConfig();
	}
}

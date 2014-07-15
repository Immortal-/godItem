/**
 * @author: ChrisDaCoder
 * @Website: http://dev.bukkit.org/bukkit-plugins/goditem/
 * @ChangeLog: http://pastebin.com/ibmdxXZE
 * @Version: 1.0.6
 */
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
		try {
			ItemMeta meta = p.getItemInHand().getItemMeta();
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',getConfig().getString("Strings.Colored_ItemName"))); // set's display name in item meta to Go Weapon
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Strings.Colored_Lorename")));
			meta.setLore(lore);
			p.getItemInHand().setItemMeta(meta); // Save new item meta
			for (Enchantment enc : Enchantment.values()) {
				p.getItemInHand().addUnsafeEnchantment(enc, getConfig().getInt("Settings.Max_Level")); // Give item in hand every enchant at level 127
			}
			playerText(p, "You have made a god item, do not share these.",ChatColor.RED);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
    /**
     * this function unenchants specified players hand item
     * @param p This is the player that ran the command. 
     * */
	public void unEnchant(Player p) {
		removeLore(p);
		removeItemName(p);
		for (Enchantment enc : Enchantment.values()) {
			p.getItemInHand().removeEnchantment(enc);
		}
		playerText(p, "You have degoded this item.", ChatColor.RED);
	}
	/**
	 * Removes the Item name set when you enchanted the weapon
	 * 
	 * @param p the player that ran the command
	 * */
	public void removeItemName(Player p) {
		ItemMeta meta = p.getItemInHand().getItemMeta();
		if (meta.hasDisplayName()) {
			meta.setDisplayName(null);
			p.getItemInHand().setItemMeta(meta);
		}
	}
	/**
	 * This function removes all added lore to the item in specified players hand
	 * 
	 * @param p the player that ran the command*/
	public void removeLore(Player p) {
		ItemMeta meta = p.getItemInHand().getItemMeta();
		if (meta.hasLore()) {
			meta.setLore(null);
			p.getItemInHand().setItemMeta(meta);
		}
	}
	/**
	 * This function addAchievements to a specified player
	 * @param p the player that sent the command
	 * */
	public void addAchievements(Player p) {
		try {
			if (p.hasPermission("godItem.achievements")) {
				for (Achievement ach : Achievement.values()) {
					p.awardAchievement(ach);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function prints given text to the console 
	 * @param text A string to be added to the console
	 * */
	public void logText(String text) {
		getLogger().info(text);
	}

	/** This function to Display a colored text message to specified player.
	 * @param p The player that sent the command.
	 * @param text The string to be sent to the player
	 * @param color The ChatColor color.
	 * */
	public void playerText(Player p, String text, ChatColor color) {
		p.sendMessage(color + text);
	}
	/**
	 * This function creates the config file with the default config.*/
	public void loadConfiguration() {
		// See "Creating you're defaults"
		getConfig().addDefault("Settings.Max_Level", 127);
		getConfig().addDefault("permissions.Always_allow_ops", true);
		getConfig().addDefault("Strings.Colored_ItemName", "&aGod Weapon");
		getConfig().addDefault("Strings.Colored_Lorename","&dFor the god's usage only!");
		getConfig().options().copyDefaults(true);
		// Save the config whenever you manipulate it
		saveConfig();
	}
}

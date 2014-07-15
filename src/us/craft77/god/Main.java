/**
 * @author: ChrisDaCoder
 * @Website: http://dev.bukkit.org/bukkit-plugins/goditem/
 * @ChangeLog: http://pastebin.com/ibmdxXZE
 * @Version: 1.0.6
 */
package us.craft77.god;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Main extends Util {
	/** When plugin is enabled it prints to console and loads the config. */
	public void onEnable() {
		logText("Plugin GodItem initialized");
		try {
			loadConfiguration();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** When the plugin closes the power down is sent to the console. */
	public void onDisable() {
		logText("Plugin godItem powering down");
	}

	/**
	 * When then plugin receives a command it handles how it should be processed
	 * @param sender The sender that sent the command 
	 * @param cmd the command that has been sent
	 * @param commandLabel the command alias that was used
	 * @param args an array of additional arguments, e.g. typing /hello abc def would put abc in args[0], and def in args[1] 
	 * @return false 
	 * */
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		try {
			if (sender instanceof Player) { // required as we are enchanting items in players hand
				boolean oped; // a boolean to check to see if user is oped and the config supports op's usage
				Player player = (Player) sender; // type casting to a player from sender in the listener
				Boolean isopallowed = getConfig().getBoolean("permissions.Always_allow_ops"); // get the config for op's
				if (player.isOp() && isopallowed) { //if config allows and they are oped then oped = true else oped=false
					oped = true;
				} else {
					oped = false;
				}

				if (player.hasPermission("godItem.use") || oped) {
					if (cmd.getName().equalsIgnoreCase("giup")) {
						enchant(player); //when player does /giup it runs this enchant
					}
					if (cmd.getName().equalsIgnoreCase("gidown")) {
						unEnchant(player); //when player does /gidown it runs this unenchant
					}
					if (cmd.getName().equalsIgnoreCase("gireload")) {
						reloadConfig();  //do /gireload runs the config reload
						playerText(player, "godItem reload complete!",ChatColor.LIGHT_PURPLE);
						logText("Player: " + player.getCustomName()+ " issued a config reload");
					}
					if (player.hasPermission("godItem.achievements")|| player.isOp()) {
						if (cmd.getName().equalsIgnoreCase("giach")) {
							addAchievements(player); // if user has permission and command is run then give all achievements
						}
						if (cmd.getName().equalsIgnoreCase("gikill")) {
							if (player.hasPermission("godItem.kill")) {
								// kills the bukkit plugin
								Bukkit.getPluginManager().disablePlugin(this);
								playerText(player, "godItem plugin killed!",ChatColor.LIGHT_PURPLE);
							}
						}
						if (cmd.getName().equalsIgnoreCase("gihelp")) {
							
							playerText(player, "--godItem Help menu--",ChatColor.GOLD);
							playerText(player,"/giup with item in hand to make a god item",ChatColor.GOLD);
							playerText(player,"/gidown with item in hand to degod an item",ChatColor.GOLD);
							if (player.hasPermission("godItem.achievements")) { // allows to only diplay help based on permssions you have
								playerText(player,"/giach get all achivements",ChatColor.GOLD);
							}
							if (player.hasPermission("godItem.reload")) {
								playerText(player,"/gireload reloads godItems config",ChatColor.GOLD);
							}
							if (player.hasPermission("godItem.kill")) {
								playerText(player,"/gikill kills godItems in bukkit",ChatColor.GOLD);
							}
						}
					}
				} else {
					playerText(player, "I am sorry but you do not have permission to use this.",ChatColor.RED);
				}

			} else {
				logText("You can only send this command as a player.");
			}
		} catch (Exception e) {

		}
		return false;
	}

}

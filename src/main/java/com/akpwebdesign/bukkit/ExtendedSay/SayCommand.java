package com.akpwebdesign.bukkit.ExtendedSay;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

public class SayCommand implements TabExecutor {
	
	private ExtendedSay plugin;
	
	public SayCommand(ExtendedSay plugin) {
		super();
		this.plugin = plugin;
	}

	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
	{
		
		//set up new List for our autocomplete data
		List<String> autoComplete = new ArrayList<String>();
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			autoComplete.add(player.getDisplayName());
		}

		return autoComplete;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!sender.hasPermission("bukkit.command.say"))
			return true;
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Usage: /say <message ...>");
			return false;
		}
		
		ChatColor color = ChatColor.LIGHT_PURPLE;
		
		String colorString = this.plugin.getConfig().getString("colors.say");
		
		if(ChatColor.valueOf(colorString).isColor())
			color = ChatColor.valueOf(colorString);


		StringBuilder message = new StringBuilder();
		message.append(color).append("[");
		message.append("Server");
		message.append(color).append("] ");

		if (args.length > 0) {
			message.append(args[0]);
			for (int i = 1; i < args.length; i++) {
				message.append(" ").append(args[i]);
			}
		}

		Bukkit.broadcastMessage(message.toString());
		return true;
	}
}

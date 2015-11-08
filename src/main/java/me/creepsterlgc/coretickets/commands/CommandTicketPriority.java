package me.creepsterlgc.coretickets.commands;

import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CoreTicket;
import me.creepsterlgc.core.utils.PermissionsUtils;
import me.creepsterlgc.core.utils.ServerUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandTicketPriority {

	public CommandTicketPriority(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(!PermissionsUtils.has(sender, "core.ticket.priority")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length != 3) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/ticket priority <id> <priority>")); return; }
	
		Player player = (Player) sender;
		
		int id;
		try { id = Integer.parseInt(args[1]); }
		catch(NumberFormatException e) {
			sender.sendMessage(Texts.builder("<id> has to be a number!").color(TextColors.RED).build());
			return;
		}
		
		CoreTicket ticket = CoreDatabase.getTicket(id);
		
		if(ticket == null) {
			sender.sendMessage(Texts.builder("Ticket with that ID does not exist!").color(TextColors.RED).build());
			return;
		}
		
		String priority = args[2].toLowerCase();
		if(!priority.equalsIgnoreCase("low")
		&& !priority.equalsIgnoreCase("medium")
		&& !priority.equalsIgnoreCase("high")) {
			sender.sendMessage(Texts.builder("<priority> has to be: low, medium or high").color(TextColors.RED).build());
			return;
		}
		
		if(!PermissionsUtils.has(sender, "core.ticket.priority-others")) {
			if(ticket.getUUID().equalsIgnoreCase(player.getUniqueId().toString())) {
				
			}
			else if(ticket.getAssigned().equalsIgnoreCase(player.getUniqueId().toString()) && PermissionsUtils.has(sender, "core.ticket.priority-assigned")) {
				
			}
			else {
				sender.sendMessage(Texts.builder("You do not have permissions to change the priority of this ticket!").color(TextColors.RED).build());
				return;
			}
		}
		
		ticket.setPriority(priority);
		ticket.update();
		
		Text p = Texts.of(TextColors.DARK_GREEN, "Low");
		if(ticket.getPriority().equalsIgnoreCase("medium")) p = Texts.of(TextColors.YELLOW, "Medium");
		else if(ticket.getPriority().equalsIgnoreCase("high")) p = Texts.of(TextColors.RED, "High");
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "Priority of ticket ", TextColors.GREEN, "#", id, TextColors.GRAY, " has been changed to ", p));
		
		ServerUtils.broadcast("core.ticket.notify", Texts.of(TextColors.YELLOW, sender.getName(), TextColors.GRAY, " has changed the priority of ticket ", TextColors.GREEN, "#", id, TextColors.GRAY, " to ", p));
		
	}

}

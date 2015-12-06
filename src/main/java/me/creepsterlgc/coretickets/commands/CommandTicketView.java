package main.java.me.creepsterlgc.coretickets.commands;

import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.customized.CoreTicket;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;
import main.java.me.creepsterlgc.core.utils.TimeUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;


public class CommandTicketView {

	public CommandTicketView(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(!PermissionsUtils.has(sender, "core.ticket.view")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length != 2) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/ticket view <id>")); return; }
	
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
		
		if(!PermissionsUtils.has(sender, "core.ticket.view-others")) {
			if(ticket.getUUID().equalsIgnoreCase(player.getUniqueId().toString())) {
				
			}
			else if(ticket.getAssigned().equalsIgnoreCase(player.getUniqueId().toString()) && PermissionsUtils.has(sender, "core.ticket.view-assigned")) {
				
			}
			else {
				sender.sendMessage(Texts.builder("You do not have permissions to view this ticket!").color(TextColors.RED).build());
				return;
			}
		}
		
		Text p = Texts.of(TextColors.DARK_GREEN, "Low");
		if(ticket.getPriority().equalsIgnoreCase("medium")) p = Texts.of(TextColors.YELLOW, "Medium");
		else if(ticket.getPriority().equalsIgnoreCase("high")) p = Texts.of(TextColors.RED, "High");
		
		sender.sendMessage(Texts.of(TextColors.GOLD, "Information for Ticket ", TextColors.GREEN, "#", id));
		sender.sendMessage(Texts.of(TextColors.GRAY, "By: ", TextColors.YELLOW, CoreDatabase.getPlayer(ticket.getUUID()).getName(), TextColors.GRAY, " | ", TextColors.GRAY, TimeUtils.toString(System.currentTimeMillis() - ticket.getTime()), TextColors.YELLOW, " ago", TextColors.GRAY, " | Priority: ", p));
		String assigned = "- no -"; if(!ticket.getAssigned().equalsIgnoreCase("")) assigned = CoreDatabase.getPlayer(ticket.getAssigned()).getName();
		sender.sendMessage(Texts.of(TextColors.GRAY, "Assigned: ", TextColors.YELLOW, assigned, TextColors.GRAY, " | Status: ", TextColors.YELLOW, ticket.getStatus()));
		sender.sendMessage(Texts.of(TextColors.GRAY, "Location: ", TextColors.YELLOW, "world: ", ticket.getWorld(), " x:", Math.round(ticket.getX()), " y:", Math.round(ticket.getY()), " z:", Math.round(ticket.getZ())));
		sender.sendMessage(Texts.of(TextColors.GRAY, "Message: ", TextColors.WHITE, ticket.getMessage()));
		if(ticket.getComments().isEmpty()) { sender.sendMessage(Texts.of(TextColors.GREEN, "Comments: ", TextColors.GRAY, "- none -")); }
		else {
			sender.sendMessage(Texts.of(TextColors.GREEN, "Comments:"));
			for(String comment : ticket.getComments()) sender.sendMessage(Texts.of(TextColors.GREEN, "- ", TextColors.WHITE, comment));
		}
		
	}

}

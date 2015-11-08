package main.java.me.creepsterlgc.coretickets.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import main.java.me.creepsterlgc.core.Controller;
import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.customized.CorePlayer;
import main.java.me.creepsterlgc.core.customized.CoreTicket;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandTicketList {

	public CommandTicketList(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(!PermissionsUtils.has(sender, "core.ticket.list")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length < 1 || args.length > 2) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/ticket list [player]")); return; }
	
		String uuid = "";
		
		if(args.length == 2) {
			
			uuid = CoreDatabase.getUUID(args[1]);
			
			if(uuid == null || uuid.equalsIgnoreCase("")) {
				sender.sendMessage(Texts.builder("Player not found!").color(TextColors.RED).build());
				return;
			}
			
		}
				
		HashMap<Integer, Text> tickets = new HashMap<Integer, Text>();
		HashMap<Integer, List<Text>> pages = new HashMap<Integer, List<Text>>();
		
		int counter = 1;
		for(Entry<Integer, CoreTicket> e : CoreDatabase.getTickets().entrySet()) {
			CoreTicket ticket = e.getValue();
			if(!uuid.equalsIgnoreCase("")) {
				if(!ticket.getUUID().equalsIgnoreCase(uuid)) continue;
			}
			else {
				if(!ticket.getStatus().equalsIgnoreCase("open")) continue;
			}
			Text p = Texts.of(TextColors.GREEN, "Low");
			if(ticket.getPriority().equalsIgnoreCase("medium")) p = Texts.of(TextColors.YELLOW, "Medium");
			else if(ticket.getPriority().equalsIgnoreCase("high")) p = Texts.of(TextColors.RED, "High");
			Text s = Texts.of(TextColors.DARK_GREEN, "Open");
			if(ticket.getStatus().equalsIgnoreCase("closed")) s = Texts.of(TextColors.DARK_RED, "Closed");
			Text message = Texts.of(TextColors.GREEN, "#", ticket.getID(), TextColors.GRAY, " | ", p, TextColors.GRAY, " | ", s, TextColors.GRAY, " | ", TextColors.WHITE, CoreDatabase.getPlayer(ticket.getUUID()).getName(), TextColors.GRAY, " | ", TextColors.WHITE, ticket.getMessage());
			Text hover = Texts.of(TextColors.YELLOW, "Click", TextColors.GRAY, " to view information on Ticket ", TextColors.GREEN, "#", ticket.getID());
			String command = "/ticket view " + ticket.getID();
			tickets.put(counter, Texts.builder().append(message).onHover(TextActions.showText(hover)).onClick(TextActions.runCommand(command)).build());
			counter += 1;
		}
	
		if(counter == 1) {
			sender.sendMessage(Texts.builder("No tickets found!").color(TextColors.RED).build());
			return;
		}
		
		counter = 0;
		while(!tickets.isEmpty()) {
			List<Text> c = new ArrayList<Text>();
			for(int i = 1; i <= 6; i++) {
				if(!tickets.containsKey(i + counter * 6)) break;
				c.add(tickets.get(i + counter * 6));
				tickets.remove(i  + counter * 6);
			}
			counter += 1;
			pages.put(counter, c);
		}
		
		Player player = (Player) sender;
		CorePlayer p = CoreDatabase.getPlayer(player.getUniqueId().toString());
		
		p.setPages(pages);
		
		p.setPageTitle(Texts.of(TextColors.GOLD, "Tickets"));
		p.setPageHeader(Texts.of(TextColors.GREEN, "ID", TextColors.GRAY, " | ", TextColors.WHITE, "Priority", TextColors.GRAY, " | ", TextColors.WHITE, "Status", TextColors.GRAY, " | ", TextColors.WHITE, "Issued by", TextColors.GRAY, " | ", TextColors.WHITE, "Message"));
		
		Controller.getGame().getCommandDispatcher().process(sender.getCommandSource().get(), "page 1");
		
	}

}

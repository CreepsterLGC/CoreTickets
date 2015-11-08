package main.java.me.creepsterlgc.coretickets.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;


public class CommandTicket implements CommandCallable {
	
	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(arguments.equalsIgnoreCase("")) { sender.sendMessage(usage); return CommandResult.success(); }
		
		if(args[0].equalsIgnoreCase("create")) { new CommandTicketCreate(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("list")) { new CommandTicketList(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("view")) { new CommandTicketView(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("tp")) { new CommandTicketTP(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("comment")) { new CommandTicketComment(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("assign")) { new CommandTicketAssign(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("priority")) { new CommandTicketPriority(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("open")) { new CommandTicketOpen(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("close")) { new CommandTicketClose(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("clear")) { new CommandTicketClear(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("help")) {
			sender.sendMessage(Texts.of(TextColors.GOLD, "Ticket Help"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/ticket create <message>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/ticket view <id>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/ticket list [player]"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/ticket tp <id>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/ticket comment <id> <message>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/ticket assign <id> <player>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/ticket priority <id> <priority>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/ticket <open|close> <id>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/ticket clear"));
		}
		else {
			sender.sendMessage(Texts.of(TextColors.GOLD, "Ticket Help"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/ticket create <message>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/ticket view <id>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/ticket list [player]"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/ticket tp <id>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/ticket comment <id> <message>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/ticket assign <id> <player>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/ticket priority <id> <priority>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/ticket <open|close> <id>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/ticket clear"));
		}
		
		return CommandResult.success();
		
	}

	private final Text usage = Texts.builder("Usage: /ticket help").color(TextColors.YELLOW).build();
	private final Text help = Texts.builder("Help: /ticket help").color(TextColors.YELLOW).build();
	private final Text description = Texts.builder("Core | Ticket Command").color(TextColors.YELLOW).build();
	private List<String> suggestions = new ArrayList<String>();
	private String permission = "";
	
	@Override
	public Text getUsage(CommandSource sender) { return usage; }
	@Override
	public Optional<Text> getHelp(CommandSource sender) { return Optional.of(help); }
	@Override
	public Optional<Text> getShortDescription(CommandSource sender) { return Optional.of(description); }
	@Override
	public List<String> getSuggestions(CommandSource sender, String args) throws CommandException { return suggestions; }
	@Override
	public boolean testPermission(CommandSource sender) { return permission.equals("") ? true : sender.hasPermission(permission); }

}

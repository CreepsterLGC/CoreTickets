package me.creepsterlgc.coretickets;

import java.util.logging.Logger;

import me.creepsterlgc.core.files.FileCommands;
import me.creepsterlgc.coretickets.commands.CommandTicket;

import org.spongepowered.api.Game;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import com.google.inject.Inject;

@Plugin(id = "CoreTickets", name = "Tickets Module for Core", dependencies = "required-after:Core")

public class CoreTickets {

	@Inject
	private Game game;
	
	@Inject
	Logger logger;
	
	public static CoreTickets coretickets;
	
	public static CoreTickets getInstance() { return coretickets; }
	public Game getGame() { return game; }
	
    @Listener
    public void onEnable(GameStartingServerEvent event) {
    	
    	if(FileCommands.TICKET()) game.getCommandDispatcher().register(this, new CommandTicket(), "ticket");
    	
    }
    
}
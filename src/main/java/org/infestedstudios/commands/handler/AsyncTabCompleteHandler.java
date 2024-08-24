package org.infestedstudios.commands.handler;

import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.infestedstudios.commands.BaseCommand;
import org.infestedstudios.commands.manager.CommandManager;

import java.util.List;
import java.util.stream.Collectors;

public class AsyncTabCompleteHandler implements Listener {

    private final CommandManager commandManager;

    public AsyncTabCompleteHandler(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @EventHandler
    public void onAsyncTabComplete(AsyncTabCompleteEvent event) {
        if (event.getBuffer().startsWith("/")) {
            String[] args = event.getBuffer().substring(1).split(" ");
            String commandName = args[0].toLowerCase();

            BaseCommand command = commandManager.getCommand(commandName);
            if (command != null) {
                List<String> completions = command.onTabComplete(event.getSender(), null, commandName, args);
                event.setCompletions(completions.stream().distinct().collect(Collectors.toList()));
            }
        }
    }
}

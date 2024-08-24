package org.infestedstudios.commands.manager;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.infestedstudios.commands.BaseCommand;
import org.infestedstudios.commands.annotations.CommandAlias;
import org.infestedstudios.commands.completions.CommandCompletions;
import org.infestedstudios.commands.handler.AsyncTabCompleteHandler;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    private JavaPlugin plugin;
    private boolean asyncTabCompleteAvailable = false;
    private final Map<String, BaseCommand> registeredCommands = new HashMap<>();
    private final CommandCompletions commandCompletions = new CommandCompletions();

    // Method to initialize the CommandManager with the plugin instance
    public void init(JavaPlugin plugin) {
        this.plugin = plugin;

        // Check for Async Tab Completion support
        try {
            Class.forName("com.destroystokyo.paper.event.server.AsyncTabCompleteEvent");
            plugin.getServer().getPluginManager().registerEvents(new AsyncTabCompleteHandler(this), plugin);
            this.asyncTabCompleteAvailable = true;
        } catch (ClassNotFoundException ignored) {
        }
    }

    // Method to register commands with the plugin
    public void registerCommands(BaseCommand... commands) {
        if (plugin == null) {
            throw new IllegalStateException("CommandManager has not been initialized. Call init(plugin) first.");
        }

        for (BaseCommand command : commands) {
            CommandAlias aliasAnnotation = command.getClass().getAnnotation(CommandAlias.class);
            if (aliasAnnotation != null) {
                String commandAlias = aliasAnnotation.value();
                PluginCommand pluginCommand = plugin.getCommand(commandAlias);

                if (pluginCommand != null) {
                    pluginCommand.setExecutor(command);
                    pluginCommand.setTabCompleter(command);
                    registeredCommands.put(commandAlias, command);
                } else {
                    plugin.getLogger().warning("Command with alias '" + commandAlias + "' is not defined in plugin.yml");
                }
            }
        }
    }

    // Method to unregister a command
    public void unregisterCommand(String alias) {
        BaseCommand command = registeredCommands.remove(alias);
        if (command != null) {
            PluginCommand pluginCommand = plugin.getCommand(alias);
            if (pluginCommand != null) {
                pluginCommand.setExecutor(null);
                pluginCommand.setTabCompleter(null);
            }
        }
    }

    // Accessor for CommandCompletions
    public CommandCompletions getCommandCompletions() {
        return commandCompletions;
    }

    public boolean isAsyncTabCompleteAvailable() {
        return asyncTabCompleteAvailable;
    }

    public BaseCommand getCommand(String commandName) {
        return registeredCommands.get(commandName);
    }
}

package org.infestedstudios.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.infestedstudios.commands.annotations.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public abstract class BaseCommand implements CommandExecutor, TabCompleter {

    private final Map<String, Method> subCommands = new HashMap<>();
    private String commandPrefix = "";

    public BaseCommand() {
        // No need to initialize commands here; handled by CommandManager
        for (Method method : this.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(SubCommand.class)) {
                SubCommand subCommand = method.getAnnotation(SubCommand.class);
                subCommands.put(subCommand.value(), method);
            }
        }
    }

    public void setCommandPrefix(String prefix) {
        this.commandPrefix = prefix;
    }

    public String getCommandPrefix() {
        return commandPrefix;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            if (args.length == 0) {
                for (Method method : this.getClass().getDeclaredMethods()) {
                    if (method.isAnnotationPresent(Default.class)) {
                        invokeMethod(method, sender, args);
                        return true;
                    }
                }
            } else if (subCommands.containsKey(args[0])) {
                Method subCommand = subCommands.get(args[0]);
                invokeMethod(subCommand, sender, args);
                return true;
            }

            for (Method method : this.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(HelpCommand.class)) {
                    method.invoke(this, sender);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            sender.sendMessage(commandPrefix + "An error occurred while executing the command.");
        }
        return false;
    }

    private void invokeMethod(Method method, CommandSender sender, String[] args) throws Exception {
        Parameter[] parameters = method.getParameters();
        Object[] methodArgs = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].getType().equals(CommandSender.class)) {
                methodArgs[i] = sender;
            } else if (parameters[i].getType().equals(String[].class)) {
                methodArgs[i] = args;
            } else if (parameters[i].isAnnotationPresent(Context.class)) {
                methodArgs[i] = resolveContext(parameters[i].getType(), sender);
            } else {
                methodArgs[i] = args.length > i - 2 ? args[i - 2] : null;  // Adjusted index to align with non-command parameters
            }
        }

        method.invoke(this, methodArgs);
    }

    private Object resolveContext(Class<?> type, CommandSender sender) {
        if (type.equals(Player.class) && sender instanceof Player) {
            return sender;
        }
        // Additional context types can be resolved here
        return null;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.addAll(subCommands.keySet());
        } else {
            for (Method method : subCommands.values()) {
                if (method.isAnnotationPresent(CommandCompletion.class)) {
                    CommandCompletion completion = method.getAnnotation(CommandCompletion.class);
                    completions.addAll(Arrays.asList(completion.value().split(" ")));
                }
            }
        }
        return completions;
    }

    public void help(CommandSender sender) {
        sender.sendMessage(commandPrefix + " Use /help for command information.");
    }
}

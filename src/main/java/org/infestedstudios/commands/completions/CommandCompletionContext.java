package org.infestedstudios.commands.completions;

import org.bukkit.command.CommandSender;

public class CommandCompletionContext {
    private final CommandSender sender;
    private final String[] args;

    public CommandCompletionContext(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
    }

    public CommandSender getSender() {
        return sender;
    }

    public String[] getArgs() {
        return args;
    }
}

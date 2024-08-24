package org.infestedstudios.commands.completions;

import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class CommandCompletions {

    private final Map<String, Function<CommandCompletionContext, List<String>>> completions = new HashMap<>();

    public void registerCompletion(String name, Function<CommandCompletionContext, List<String>> completion) {
        completions.put(name, completion);
    }

    public List<String> getCompletion(String name, CommandCompletionContext context) {
        Function<CommandCompletionContext, List<String>> completion = completions.get(name);
        if (completion != null) {
            return completion.apply(context);
        }
        return null;
    }
}


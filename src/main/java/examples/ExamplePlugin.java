package examples;

import org.bukkit.plugin.java.JavaPlugin;
import org.infestedstudios.commands.ExampleCommand;
import org.infestedstudios.commands.manager.CommandManager;

public class ExamplePlugin extends JavaPlugin {

    private CommandManager commandManager;

    @Override
    public void onEnable() {
        commandManager = new CommandManager();
        commandManager.init(this);

        // Register the ExampleCommand
        commandManager.registerCommands(
                new ExampleCommand()
        );

        getLogger().info("Commands registered successfully.");
    }

    @Override
    public void onDisable() {
        // Clean up or unregister commands if needed
    }
}

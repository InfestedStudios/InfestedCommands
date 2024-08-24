package examples;
import org.infestedstudios.commands.BaseCommand;
import org.infestedstudios.commands.annotations.*;
import org.bukkit.command.CommandSender;

@CommandAlias("example")
public class ExampleCommand extends BaseCommand {

    public ExampleCommand() {
        // Constructor can be empty, as CommandManager handles registration
    }

    @Default
    public void onDefault(CommandSender sender, String[] args) {
        sender.sendMessage("This is the default command.");
    }

    @SubCommand("greet")
    @CommandCompletion("@players")
    public void onGreet(CommandSender sender, String[] args) {
        sender.sendMessage("Hello, " + (args.length > 1 ? args[1] : "world") + "!");
    }

    @HelpCommand
    public void showHelp(CommandSender sender) {
        sender.sendMessage("Use /example greet <name> to greet someone.");
    }
}

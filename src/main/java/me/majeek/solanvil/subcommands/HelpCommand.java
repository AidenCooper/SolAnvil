package me.majeek.solanvil.subcommands;

import me.majeek.solanvil.SolAnvil;
import org.bukkit.command.CommandSender;

import java.util.Map;
import java.util.Objects;

public class HelpCommand implements SubCommand {
    @Override
    public String[] getName() {
        return new String[]{ "help" };
    }

    @Override
    public String getPermission() {
        return "solanvil.help";
    }

    @Override
    public boolean allowConsole() {
        return true;
    }

    @Override
    public int requiredArgs() {
        return 0;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Map<String, Object> content = Objects.requireNonNull(SolAnvil.getInstance().getMessagesConfig().getConfiguration().getConfigurationSection("help")).getValues(true);

        for(Object item : content.values()){
            if(item instanceof String) {
                SolAnvil.getInstance().getCommandManager().sendMessage(sender, (String) item);
            }
        }
    }
}

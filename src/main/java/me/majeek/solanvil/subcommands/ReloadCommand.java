package me.majeek.solanvil.subcommands;

import me.majeek.solanvil.SolAnvil;
import org.bukkit.command.CommandSender;

import java.util.Objects;

public class ReloadCommand implements SubCommand {
    @Override
    public String[] getName() {
        return new String[]{ "reload" };
    }

    @Override
    public String getPermission() {
        return "solanvil.reload";
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
        SolAnvil.getInstance().getMainConfig().reloadConfig();
        SolAnvil.getInstance().getMessagesConfig().reloadConfig();

        String content = Objects.requireNonNull(SolAnvil.getInstance().getMessagesConfig().getConfiguration().getString("reload"));
        SolAnvil.getInstance().getCommandManager().sendMessage(sender, content);
    }
}

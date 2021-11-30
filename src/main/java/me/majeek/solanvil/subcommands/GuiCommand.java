package me.majeek.solanvil.subcommands;

import me.majeek.solanvil.SolAnvil;
import me.majeek.solanvil.handlers.GuiHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class GuiCommand implements SubCommand {
    @Override
    public String[] getName() {
        return new String[]{ "gui" };
    }

    @Override
    public String getPermission() {
        return "solanvil.gui";
    }

    @Override
    public boolean allowConsole() {
        return false;
    }

    @Override
    public int requiredArgs() {
        return 0;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        GuiHandler.displayMainGui((Player) sender);

        String content = Objects.requireNonNull(SolAnvil.getInstance().getMessagesConfig().getConfiguration().getString("gui.gui"));
        SolAnvil.getInstance().getCommandManager().sendMessage(sender, content);
    }
}

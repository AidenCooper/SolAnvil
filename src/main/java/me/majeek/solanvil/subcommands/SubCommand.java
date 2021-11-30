package me.majeek.solanvil.subcommands;

import org.bukkit.command.CommandSender;

public interface SubCommand {
    String[] getName();

    String getPermission();

    boolean allowConsole();

    int requiredArgs();

    void execute(CommandSender sender, String[] args);
}

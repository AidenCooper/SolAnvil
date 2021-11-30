package me.majeek.solanvil;

import com.google.common.collect.Sets;
import me.majeek.solanvil.configs.ConfigFile;
import me.majeek.solanvil.listeners.AnvilListener;
import me.majeek.solanvil.listeners.GuiListener;
import me.majeek.solanvil.managers.CommandManager;
import me.majeek.solanvil.managers.EventManager;
import me.majeek.solanvil.subcommands.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;
import java.util.logging.Level;

public final class SolAnvil extends JavaPlugin {
    private static SolAnvil INSTANCE = null;

    private Economy vaultEconomy = null;

    private ConfigFile anvilDataConfig = null;
    private ConfigFile mainConfig = null;
    private ConfigFile messagesConfig = null;

    private CommandManager commandManager = null;
    private EventManager eventManager = null;

    @Override
    public void onEnable() {
        INSTANCE = this;

        if(!setupEconomy()) {
            getLogger().log(Level.SEVERE, "Could not connect to vault!");
            getServer().getPluginManager().disablePlugin(this);
        }

        // Create Configs
        // https://github.com/MATEQH/Annotation-Config
        this.anvilDataConfig = new ConfigFile("anvil_data").createFile(true).loadConfig();
        this.mainConfig = new ConfigFile("config").createFile(true).loadConfig();
        this.messagesConfig = new ConfigFile("messages").createFile(true).loadConfig();

        // Create Managers
        Set<SubCommand> commands = Sets.newHashSet(
                new AnvilCommand(),
                new GuiCommand(),
                new HelpCommand(),
                new ReloadCommand()
        );
        Set<Listener> listeners = Sets.newHashSet(
                new AnvilListener(),
                new GuiListener()
        );

        this.commandManager = new CommandManager("solanvil", commands);
        this.eventManager = new EventManager(listeners);
    }

    @Override
    public void onDisable() {

    }

    public static SolAnvil getInstance() {
        return INSTANCE;
    }

    public Economy getVaultEconomy() {
        return this.vaultEconomy;
    }

    public ConfigFile getAnvilDataConfig() {
        return this.anvilDataConfig;
    }

    public ConfigFile getMainConfig() {
        return this.mainConfig;
    }

    public ConfigFile getMessagesConfig() {
        return this.messagesConfig;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public EventManager getEventManager() {
        return this.eventManager;
    }

    private boolean setupEconomy() {
        if(getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> serviceProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (serviceProvider == null) {
            return false;
        }

        vaultEconomy = serviceProvider.getProvider();
        return vaultEconomy != null;
    }
}

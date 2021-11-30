package me.majeek.solanvil.managers;

import com.google.common.collect.Sets;
import me.majeek.solanvil.SolAnvil;
import org.bukkit.event.Listener;

import java.util.Set;

public class EventManager implements Listener {
    private final Set<Listener> listeners = Sets.newHashSet();

    public EventManager(Set<Listener> listeners) {
        this.listeners.addAll(listeners);

        this.registerListeners();
    }

    public Set<Listener> getListeners() {
        return this.listeners;
    }

    private void registerListeners() {
        for (Listener listener : this.listeners) {
            SolAnvil.getInstance().getServer().getPluginManager().registerEvents(listener, SolAnvil.getInstance());
        }
    }
}

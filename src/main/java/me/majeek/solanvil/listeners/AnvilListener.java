package me.majeek.solanvil.listeners;

import me.majeek.solanvil.SolAnvil;
import me.majeek.solanvil.handlers.AnvilHandler;
import me.majeek.solanvil.handlers.GuiHandler;
import net.minecraft.server.v1_8_R3.ItemStack;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class AnvilListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        List<String> locations = SolAnvil.getInstance().getAnvilDataConfig().getConfiguration().getStringList("locations");

        for(int i = 0; i < locations.size(); i += 4) {
            if(event.getBlock().getX() == Integer.parseInt(locations.get(i)) && event.getBlock().getY() == Integer.parseInt(locations.get(i + 1)) && event.getBlock().getZ() == Integer.parseInt(locations.get(i + 2)) && event.getBlock().getWorld().getName().equals(locations.get(i + 3))) {
                if(event.getPlayer().getGameMode() == GameMode.CREATIVE) {
                    locations.subList(i, i + 4).clear();

                    SolAnvil.getInstance().getAnvilDataConfig().getConfiguration().set("locations", locations);
                    SolAnvil.getInstance().getAnvilDataConfig().saveConfig();
                } else {
                    event.setCancelled(true);
                }

                break;
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if(event.getItemInHand() != null) {
            ItemStack itemNMS = CraftItemStack.asNMSCopy(event.getItemInHand());

            if(itemNMS.hasTag() && itemNMS.getTag().get(AnvilHandler.getAnvilIdentity()) != null) {
                List<String> locations = SolAnvil.getInstance().getAnvilDataConfig().getConfiguration().getStringList("locations");
                String[] location = { Integer.toString(event.getBlockPlaced().getX()), Integer.toString(event.getBlockPlaced().getY()), Integer.toString(event.getBlockPlaced().getZ()) };

                locations.add(location[0]);
                locations.add(location[1]);
                locations.add(location[2]);
                locations.add(event.getBlockPlaced().getWorld().getName());

                SolAnvil.getInstance().getAnvilDataConfig().getConfiguration().set("locations", locations);
                SolAnvil.getInstance().getAnvilDataConfig().saveConfig();
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            List<String> locations = SolAnvil.getInstance().getAnvilDataConfig().getConfiguration().getStringList("locations");

            for(int i = 0; i < locations.size(); i += 4) {
                if(event.getClickedBlock().getX() == Integer.parseInt(locations.get(i)) && event.getClickedBlock().getY() == Integer.parseInt(locations.get(i + 1)) && event.getClickedBlock().getZ() == Integer.parseInt(locations.get(i + 2)) && event.getClickedBlock().getWorld().getName().equals(locations.get(i + 3))) {
                    event.setCancelled(true);
                    GuiHandler.displayMainGui(event.getPlayer());
                    break;
                }
            }
        }
    }
}
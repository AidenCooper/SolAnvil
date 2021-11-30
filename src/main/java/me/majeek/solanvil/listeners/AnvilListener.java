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

import java.util.AbstractList;
import java.util.List;

public class AnvilListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        AbstractList<Integer> locations = (AbstractList<Integer>) SolAnvil.getInstance().getAnvilDataConfig().getConfiguration().getIntegerList("locations");
        for(int i = 0; i < locations.size(); i += 3) {
            if(event.getBlock().getX() == locations.get(i) && event.getBlock().getY() == locations.get(i + 1) && event.getBlock().getZ() == locations.get(i + 2)) {
                if(event.getPlayer().getGameMode() == GameMode.CREATIVE) {
                    locations.subList(i, i + 3).clear();

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
                List<Integer> locations = SolAnvil.getInstance().getAnvilDataConfig().getConfiguration().getIntegerList("locations");
                int[] location = { event.getBlockPlaced().getX(), event.getBlockPlaced().getY(), event.getBlockPlaced().getZ() };

                locations.add(location[0]);
                locations.add(location[1]);
                locations.add(location[2]);

                SolAnvil.getInstance().getAnvilDataConfig().getConfiguration().set("locations", locations);
                SolAnvil.getInstance().getAnvilDataConfig().saveConfig();
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            List<Integer> locations = SolAnvil.getInstance().getAnvilDataConfig().getConfiguration().getIntegerList("locations");

            for(int i = 0; i < locations.size(); i += 3) {
                if(event.getClickedBlock().getX() == locations.get(i) && event.getClickedBlock().getY() == locations.get(i + 1) && event.getClickedBlock().getZ() == locations.get(i + 2)) {
                    event.setCancelled(true);
                    GuiHandler.displayMainGui(event.getPlayer());
                    break;
                }
            }
        }
    }
}
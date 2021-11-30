package me.majeek.solanvil.listeners;

import me.majeek.solanvil.SolAnvil;
import me.majeek.solanvil.handlers.GuiHandler;
import net.minecraft.server.v1_8_R3.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class GuiListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getClickedInventory() != null) {
            String title = event.getInventory().getTitle();

            String combineTitle = ChatColor.translateAlternateColorCodes('&', SolAnvil.getInstance().getMainConfig().getConfiguration().getString("gui.combine.title"));
            String mainTitle = ChatColor.translateAlternateColorCodes('&', SolAnvil.getInstance().getMainConfig().getConfiguration().getString("gui.main.title"));
            String repairTitle = ChatColor.translateAlternateColorCodes('&', SolAnvil.getInstance().getMainConfig().getConfiguration().getString("gui.repair.title"));

            ItemStack itemNMS = CraftItemStack.asNMSCopy(event.getCurrentItem());

            if(title.equals(combineTitle)) {
                if(event.getClickedInventory().getTitle().equals(combineTitle)) {
                    if (event.getSlot() == GuiHandler.getCombineSlot1Position() && (!itemNMS.hasTag() || itemNMS.getTag().get(GuiHandler.getCombineSlot1Tag()) == null)) {
                        for (int i = 0; i < event.getWhoClicked().getInventory().getContents().length; i++) {
                            if (event.getWhoClicked().getInventory().getItem(i) == null) {
                                event.getWhoClicked().getInventory().setItem(i, event.getInventory().getItem(event.getSlot()));
                                event.getClickedInventory().setItem(event.getSlot(), GuiHandler.getCombineSlot1Item());

                                // Update
                                for(int slot : GuiHandler.getCombineConfirmPositions()) {
                                    event.getInventory().setItem(slot, GuiHandler.getCombineConfirmItem(-1));
                                }
                                event.getInventory().setItem(GuiHandler.getCombineAnvilPositions()[0], GuiHandler.getCombineAnvilItem());
                                event.getInventory().setItem(GuiHandler.getCombineAnvilPositions()[1], new org.bukkit.inventory.ItemStack(Material.AIR));

                                break;
                            }
                        }
                    } else if (event.getSlot() == GuiHandler.getCombineSlot2Position() && (!itemNMS.hasTag() || itemNMS.getTag().get(GuiHandler.getCombineSlot2Tag()) == null)) {
                        for (int i = 0; i < event.getWhoClicked().getInventory().getContents().length; i++) {
                            if (event.getWhoClicked().getInventory().getItem(i) == null) {
                                event.getWhoClicked().getInventory().setItem(i, event.getInventory().getItem(event.getSlot()));
                                event.getClickedInventory().setItem(event.getSlot(), GuiHandler.getCombineSlot2Item());

                                // Update
                                for(int slot : GuiHandler.getCombineConfirmPositions()) {
                                    event.getInventory().setItem(slot, GuiHandler.getCombineConfirmItem(-1));
                                }
                                event.getInventory().setItem(GuiHandler.getCombineAnvilPositions()[0], GuiHandler.getCombineAnvilItem());
                                event.getInventory().setItem(GuiHandler.getCombineAnvilPositions()[1], new org.bukkit.inventory.ItemStack(Material.AIR));

                                break;
                            }
                        }
                    } else if(Arrays.stream(GuiHandler.getCombineConfirmPositions()).boxed().collect(Collectors.toList()).contains(event.getSlot())) {
                        ItemStack slot1NMS = CraftItemStack.asNMSCopy(event.getInventory().getItem(GuiHandler.getCombineSlot1Position()));
                        ItemStack slot2NMS = CraftItemStack.asNMSCopy(event.getInventory().getItem(GuiHandler.getCombineSlot2Position()));

                        if(!((slot1NMS.hasTag() && slot1NMS.getTag().get(GuiHandler.getCombineSlot1Tag()) != null) || (slot2NMS.hasTag() && slot2NMS.getTag().get(GuiHandler.getCombineSlot2Tag()) != null))) {
                            int price = GuiHandler.getCombinedPrice(event.getInventory().getItem(GuiHandler.getCombineSlot1Position()), event.getInventory().getItem(GuiHandler.getCombineSlot2Position()));

                            if(SolAnvil.getInstance().getVaultEconomy().getBalance((Player) event.getWhoClicked()) >= price && SolAnvil.getInstance().getVaultEconomy().withdrawPlayer((Player) event.getWhoClicked(), price).transactionSuccess()) {
                                for (int i = 0; i < event.getWhoClicked().getInventory().getContents().length; i++) {
                                    if (event.getWhoClicked().getInventory().getItem(i) == null) {
                                        event.getWhoClicked().getInventory().setItem(i, event.getInventory().getItem(GuiHandler.getCombineAnvilPositions()[0]));

                                        event.getInventory().setItem(GuiHandler.getCombineSlot1Position(), GuiHandler.getCombineSlot1Item());
                                        event.getInventory().setItem(GuiHandler.getCombineSlot2Position(), GuiHandler.getCombineSlot2Item());

                                        break;
                                    }
                                }

                                event.getWhoClicked().closeInventory();

                                String content = Objects.requireNonNull(SolAnvil.getInstance().getMessagesConfig().getConfiguration().getString("gui.combined"));
                                SolAnvil.getInstance().getCommandManager().sendMessage(event.getWhoClicked(), content);
                            }
                        }
                    } else if(Arrays.stream(GuiHandler.getCombineDeclinePositions()).boxed().collect(Collectors.toList()).contains(event.getSlot())) {
                        event.getWhoClicked().closeInventory();
                    }
                } else {
                    if(GuiHandler.isValidItem(event.getCurrentItem().getType())) {
                        ItemStack slot1NMS = CraftItemStack.asNMSCopy(event.getInventory().getItem(GuiHandler.getCombineSlot1Position()));
                        ItemStack slot2NMS = CraftItemStack.asNMSCopy(event.getInventory().getItem(GuiHandler.getCombineSlot2Position()));

                        if(slot1NMS.hasTag() && slot1NMS.getTag().get(GuiHandler.getCombineSlot1Tag()) != null) {
                            if(!slot2NMS.hasTag() || slot2NMS.getTag().get(GuiHandler.getCombineSlot2Tag()) == null) {
                                if(event.getCurrentItem().getType() == event.getInventory().getItem(GuiHandler.getCombineSlot2Position()).getType()) {
                                    event.getInventory().setItem(GuiHandler.getCombineSlot1Position(), event.getCurrentItem());
                                    event.getClickedInventory().setItem(event.getSlot(), new org.bukkit.inventory.ItemStack(Material.AIR));

                                    // Update
                                    for(int slot : GuiHandler.getCombineConfirmPositions()) {
                                        event.getInventory().setItem(slot, GuiHandler.getCombineConfirmItem(GuiHandler.getCombinedPrice(event.getInventory().getItem(GuiHandler.getCombineSlot1Position()), event.getInventory().getItem(GuiHandler.getCombineSlot2Position()))));
                                    }
                                    event.getInventory().setItem(GuiHandler.getCombineAnvilPositions()[0], GuiHandler.getCombinedItem(event.getInventory().getItem(GuiHandler.getCombineSlot1Position()), event.getInventory().getItem(GuiHandler.getCombineSlot2Position())));
                                    event.getInventory().setItem(GuiHandler.getCombineAnvilPositions()[1], GuiHandler.getCombineAnvilItem());
                                }
                            } else {
                                event.getInventory().setItem(GuiHandler.getCombineSlot1Position(), event.getCurrentItem());
                                event.getClickedInventory().setItem(event.getSlot(), new org.bukkit.inventory.ItemStack(Material.AIR));
                            }
                        } else if(slot2NMS.hasTag() && slot2NMS.getTag().get(GuiHandler.getCombineSlot2Tag()) != null) {
                            if(event.getCurrentItem().getType() == event.getInventory().getItem(GuiHandler.getCombineSlot1Position()).getType()) {
                                event.getInventory().setItem(GuiHandler.getCombineSlot2Position(), event.getCurrentItem());
                                event.getClickedInventory().setItem(event.getSlot(), new org.bukkit.inventory.ItemStack(Material.AIR));

                                // Update
                                for(int slot : GuiHandler.getCombineConfirmPositions()) {
                                    event.getInventory().setItem(slot, GuiHandler.getCombineConfirmItem(GuiHandler.getCombinedPrice(event.getInventory().getItem(GuiHandler.getCombineSlot1Position()), event.getInventory().getItem(GuiHandler.getCombineSlot2Position()))));
                                }
                                event.getInventory().setItem(GuiHandler.getCombineAnvilPositions()[0], GuiHandler.getCombinedItem(event.getInventory().getItem(GuiHandler.getCombineSlot1Position()), event.getInventory().getItem(GuiHandler.getCombineSlot2Position())));
                                event.getInventory().setItem(GuiHandler.getCombineAnvilPositions()[1], GuiHandler.getCombineAnvilItem());
                            }
                        }
                    }
                }

                event.setCancelled(true);
            } else if(title.equals(mainTitle)) {
                if(event.getSlot() == GuiHandler.getMainCombinePosition()) {
                    GuiHandler.displayCombineGui((Player) event.getWhoClicked());
                } else if(event.getSlot() == GuiHandler.getMainRepairPosition()) {
                    GuiHandler.displayRepairGui((Player) event.getWhoClicked());
                }

                event.setCancelled(true);
            } else if(title.equals(repairTitle)) {
                if(event.getClickedInventory().getTitle().equals(repairTitle)) {
                    if (event.getSlot() == GuiHandler.getRepairSlotPosition() && (!itemNMS.hasTag() || itemNMS.getTag().get(GuiHandler.getRepairSlotTag()) == null)) {
                        for (int i = 0; i < event.getWhoClicked().getInventory().getContents().length; i++) {
                            if (event.getWhoClicked().getInventory().getItem(i) == null) {
                                event.getWhoClicked().getInventory().setItem(i, event.getInventory().getItem(event.getSlot()));
                                event.getClickedInventory().setItem(event.getSlot(), GuiHandler.getRepairSlotItem());

                                break;
                            }
                        }
                    } else if(Arrays.stream(GuiHandler.getRepairConfirmPositions()).boxed().collect(Collectors.toList()).contains(event.getSlot())) {
                        ItemStack slotNMS = CraftItemStack.asNMSCopy(event.getInventory().getItem(GuiHandler.getRepairSlotPosition()));

                        if(!(slotNMS.hasTag() && slotNMS.getTag().get(GuiHandler.getRepairSlotTag()) != null)) {
                            int price = SolAnvil.getInstance().getMainConfig().getConfiguration().getInt("price.repair");

                            if(SolAnvil.getInstance().getVaultEconomy().getBalance((Player) event.getWhoClicked()) >= price && SolAnvil.getInstance().getVaultEconomy().withdrawPlayer((Player) event.getWhoClicked(), price).transactionSuccess()) {
                                for (int i = 0; i < event.getWhoClicked().getInventory().getContents().length; i++) {
                                    if (event.getWhoClicked().getInventory().getItem(i) == null) {
                                        org.bukkit.inventory.ItemStack item = event.getInventory().getItem(GuiHandler.getRepairSlotPosition());
                                        item.setDurability((short) 0);

                                        event.getWhoClicked().getInventory().setItem(i, item);

                                        event.getInventory().setItem(GuiHandler.getRepairSlotPosition(), GuiHandler.getRepairSlotItem());
                                        event.getInventory().setItem(GuiHandler.getCombineSlot2Position(), GuiHandler.getCombineSlot2Item());

                                        break;
                                    }
                                }

                                event.getWhoClicked().closeInventory();

                                String content = Objects.requireNonNull(SolAnvil.getInstance().getMessagesConfig().getConfiguration().getString("gui.repaired"));
                                SolAnvil.getInstance().getCommandManager().sendMessage(event.getWhoClicked(), content);
                            }
                        }
                    } else if(Arrays.stream(GuiHandler.getCombineDeclinePositions()).boxed().collect(Collectors.toList()).contains(event.getSlot())) {
                        event.getWhoClicked().closeInventory();
                    }
                } else {
                    if(GuiHandler.isValidItem(event.getCurrentItem().getType())) {
                        ItemStack slotNMS = CraftItemStack.asNMSCopy(event.getInventory().getItem(GuiHandler.getRepairSlotPosition()));

                        if(slotNMS.hasTag() && slotNMS.getTag().get(GuiHandler.getRepairSlotTag()) != null) {
                            event.getInventory().setItem(GuiHandler.getRepairSlotPosition(), event.getCurrentItem());
                            event.getClickedInventory().setItem(event.getSlot(), new org.bukkit.inventory.ItemStack(Material.AIR));
                        }
                    }
                }

                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        String title = event.getInventory().getTitle();

        String combineTitle = ChatColor.translateAlternateColorCodes('&', SolAnvil.getInstance().getMainConfig().getConfiguration().getString("gui.combine.title"));
        String repairTitle = ChatColor.translateAlternateColorCodes('&', SolAnvil.getInstance().getMainConfig().getConfiguration().getString("gui.repair.title"));

        if(title.equals(combineTitle)) {
            ItemStack slot1NMS = CraftItemStack.asNMSCopy(event.getInventory().getItem(GuiHandler.getCombineSlot1Position()));
            ItemStack slot2NMS = CraftItemStack.asNMSCopy(event.getInventory().getItem(GuiHandler.getCombineSlot2Position()));

            if(!slot1NMS.hasTag() || slot1NMS.getTag().get(GuiHandler.getCombineSlot1Tag()) == null) {
                for(int i = 0; i < event.getPlayer().getInventory().getContents().length; i++) {
                    if(event.getPlayer().getInventory().getItem(i) == null) {
                        event.getPlayer().getInventory().setItem(i, event.getInventory().getItem(GuiHandler.getCombineSlot1Position()));
                        break;
                    }
                }
            }

            if(!slot2NMS.hasTag() || slot2NMS.getTag().get(GuiHandler.getCombineSlot2Tag()) == null) {
                for(int i = 0; i < event.getPlayer().getInventory().getContents().length; i++) {
                    if(event.getPlayer().getInventory().getItem(i) == null) {
                        event.getPlayer().getInventory().setItem(i, event.getInventory().getItem(GuiHandler.getCombineSlot2Position()));
                        break;
                    }
                }
            }
        } else if(title.equals(repairTitle)) {
            ItemStack slotNMS = CraftItemStack.asNMSCopy(event.getInventory().getItem(GuiHandler.getRepairSlotPosition()));

            if(!slotNMS.hasTag() || slotNMS.getTag().get(GuiHandler.getRepairSlotTag()) == null) {
                for(int i = 0; i < event.getPlayer().getInventory().getContents().length; i++) {
                    if(event.getPlayer().getInventory().getItem(i) == null) {
                        event.getPlayer().getInventory().setItem(i, event.getInventory().getItem(GuiHandler.getRepairSlotPosition()));
                        break;
                    }
                }
            }
        }
    }
}

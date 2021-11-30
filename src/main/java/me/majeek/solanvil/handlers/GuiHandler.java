package me.majeek.solanvil.handlers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.majeek.solanvil.SolAnvil;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagString;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class GuiHandler {
    private static final int[] combineAnvilPositions = { 13, 22 };
    private static final int[] combineConfirmPositions = { 3, 12, 21 };
    private static final int[] combineDeclinePositions = { 5, 14, 23 };
    private static final int combineSlot1Position = 10;
    private static final int combineSlot2Position = 16;
    private static final int mainCombinePosition = 11;
    private static final int mainRepairPosition = 15;
    private static final int repairAnvilPosition = 22;
    private static final int[] repairConfirmPositions = { 1, 2, 3, 10, 11, 12, 19, 20, 21 };
    private static final int[] repairDeclinePositions = { 5, 6, 7, 14, 15, 16, 23, 24, 25 };
    private static final int repairSlotPosition = 4;

    private static final String combineAnvilTag = "solanvil_combine_anvil";
    private static final String combineConfirmTag = "solanvil_combine_confirm";
    private static final String combineDeclineTag = "solanvil_combine_decline";
    private static final String combineSlot1Tag = "solanvil_combine_slot1";
    private static final String combineSlot2Tag = "solanvil_combine_slot2";
    private static final String mainCombineTag = "solanvil_main_combine";
    private static final String mainRepairTag = "solanvil_main_repair";
    private static final String repairAnvilTag = "solanvil_repair_anvil";
    private static final String repairConfirmTag = "solanvil_repair_confirm";
    private static final String repairDeclineTag = "solanvil_repair_decline";
    private static final String repairSlotTag = "solanvil_repair_slot";

    public static void displayCombineGui(Player player) {
        Inventory gui = Bukkit.createInventory(player, 27, ChatColor.translateAlternateColorCodes('&', SolAnvil.getInstance().getMainConfig().getConfiguration().getString("gui.combine.title")));

        gui.setItem(getCombineSlot1Position(), getCombineSlot1Item());
        gui.setItem(getCombineSlot2Position(), getCombineSlot2Item());
        for(int slot : getCombineConfirmPositions()) {
            gui.setItem(slot, getCombineConfirmItem(-1));
        }
        for(int slot : getCombineDeclinePositions()) {
            gui.setItem(slot, getCombineDeclineItem(0));
        }
        gui.setItem(getCombineAnvilPositions()[0], getCombineAnvilItem());

        player.openInventory(gui);
    }

    public static void displayMainGui(Player player) {
        Inventory gui = Bukkit.createInventory(player, 27, ChatColor.translateAlternateColorCodes('&', SolAnvil.getInstance().getMainConfig().getConfiguration().getString("gui.main.title")));

        gui.setItem(getMainCombinePosition(), getMainCombineItem());
        gui.setItem(getMainRepairPosition(), getMainRepairItem(SolAnvil.getInstance().getMainConfig().getConfiguration().getInt("price.repair")));

        player.openInventory(gui);
    }

    public static void displayRepairGui(Player player) {
        Inventory gui = Bukkit.createInventory(player, 27, ChatColor.translateAlternateColorCodes('&', SolAnvil.getInstance().getMainConfig().getConfiguration().getString("gui.repair.title")));

        int price = SolAnvil.getInstance().getMainConfig().getConfiguration().getInt("price.repair");

        gui.setItem(getRepairSlotPosition(), getRepairSlotItem());
        gui.setItem(getRepairAnvilPosition(), getRepairAnvilItem(price));
        for(int slot : getRepairConfirmPositions()) {
            gui.setItem(slot, getRepairConfirmItem(price));
        }
        for(int slot : getRepairDeclinePositions()) {
            gui.setItem(slot, getRepairDeclineItem(0));
        }

        player.openInventory(gui);
    }

    public static ItemStack getCombineAnvilItem() {
        return createItem("gui.combine.anvil", -1, getCombineAnvilTag());
    }

    public static int[] getCombineAnvilPositions() {
        return combineAnvilPositions;
    }

    public static String getCombineAnvilTag() {
        return combineAnvilTag;
    }

    public static ItemStack getCombineConfirmItem(int price) {
        return createItem("gui.combine.confirm", price, getCombineConfirmTag());
    }

    public static int[] getCombineConfirmPositions() {
        return combineConfirmPositions;
    }

    public static String getCombineConfirmTag() {
        return combineConfirmTag;
    }

    public static ItemStack getCombineDeclineItem(int price) {
        return createItem("gui.combine.decline", price, getCombineDeclineTag());
    }

    public static int[] getCombineDeclinePositions() {
        return combineDeclinePositions;
    }

    public static String getCombineDeclineTag() {
        return combineDeclineTag;
    }

    public static ItemStack getCombineSlot1Item() {
        return createItem("gui.combine.slot-1", -1, getCombineSlot1Tag());
    }

    public static int getCombineSlot1Position() {
        return combineSlot1Position;
    }

    public static String getCombineSlot1Tag() {
        return combineSlot1Tag;
    }

    public static ItemStack getCombineSlot2Item() {
        return createItem("gui.combine.slot-2", -1, getCombineSlot2Tag());
    }

    public static int getCombineSlot2Position() {
        return combineSlot2Position;
    }

    public static String getCombineSlot2Tag() {
        return combineSlot2Tag;
    }

    public static ItemStack getMainCombineItem() {
        return createItem("gui.main.combine", -1, getMainCombineTag());
    }

    public static int getMainCombinePosition() {
        return mainCombinePosition;
    }

    public static String getMainCombineTag() {
        return mainCombineTag;
    }

    public static ItemStack getMainRepairItem(int price) {
        return createItem("gui.main.repair", price, getMainRepairTag());
    }

    public static int getMainRepairPosition() {
        return mainRepairPosition;
    }

    public static String getMainRepairTag() {
        return mainRepairTag;
    }

    public static ItemStack getRepairAnvilItem(int price) {
        return createItem("gui.repair.anvil", price, getRepairAnvilTag());
    }

    public static int getRepairAnvilPosition() {
        return repairAnvilPosition;
    }

    public static String getRepairAnvilTag() {
        return repairAnvilTag;
    }

    public static ItemStack getRepairConfirmItem(int price) {
        return createItem("gui.repair.confirm", price, getRepairConfirmTag());
    }

    public static int[] getRepairConfirmPositions() {
        return repairConfirmPositions;
    }

    public static String getRepairConfirmTag() {
        return repairConfirmTag;
    }

    public static ItemStack getRepairDeclineItem(int price) {
        return createItem("gui.repair.decline", price, getRepairDeclineTag());
    }

    public static int[] getRepairDeclinePositions() {
        return repairDeclinePositions;
    }

    public static String getRepairDeclineTag() {
        return repairDeclineTag;
    }

    public static ItemStack getRepairSlotItem() {
        return createItem("gui.repair.slot", -1, getRepairSlotTag());
    }

    public static int getRepairSlotPosition() {
        return repairSlotPosition;
    }

    public static String getRepairSlotTag() {
        return repairSlotTag;
    }

    public static int getCombinedPrice(ItemStack item1, ItemStack item2) {
        List<Enchantment> enchantments1 = new ArrayList<>(item1.getEnchantments().keySet());
        List<Integer> levels1 = new ArrayList<>(item1.getEnchantments().values());

        List<Enchantment> enchantments2 = new ArrayList<>(item2.getEnchantments().keySet());
        List<Integer> levels2 = new ArrayList<>(item2.getEnchantments().values());

        int price = 0;
        for(int i = 0; i < enchantments1.size(); i++) {
            for(int j = 0; j < enchantments2.size(); j++) {
                if(enchantments1.get(i) == enchantments2.get(j)) {
                    if(levels1.get(i).equals(levels2.get(j)) && enchantments1.get(i).getMaxLevel() != levels1.get(i)) {
                        price += getUpgradeToPrice(enchantments1.get(i), levels1.get(i) + 1);
                    } else if(levels1.get(i) < levels2.get(j) && enchantments1.get(i).getMaxLevel() != levels1.get(i)) {
                        price += getUpgradeToPrice(enchantments2.get(j), levels2.get(j));
                    }

                    enchantments2.remove(j);
                    levels2.remove(j);
                    break;
                }
            }
        }
        for(int i = 0; i < enchantments2.size(); i++) {
            price += getAddPrice(enchantments2.get(i), levels2.get(i));
        }

        return price;
    }

    public static ItemStack getCombinedItem(ItemStack item1, ItemStack item2) {
        List<Enchantment> enchantments1 = new ArrayList<>(item1.getEnchantments().keySet());
        List<Integer> levels1 = new ArrayList<>(item1.getEnchantments().values());

        List<Enchantment> enchantments2 = new ArrayList<>(item2.getEnchantments().keySet());
        List<Integer> levels2 = new ArrayList<>(item2.getEnchantments().values());

        ItemStack item = new ItemStack(item1);
        Map<Enchantment, Integer> enchantments = Maps.newHashMap();
        for(int i = 0; i < enchantments1.size(); i++) {
            for(int j = 0; j < enchantments2.size(); j++) {
                if(enchantments1.get(i) == enchantments2.get(j)) {
                    if(levels1.get(i).equals(levels2.get(j)) && enchantments1.get(i).getMaxLevel() != levels1.get(i)) {
                        enchantments.put(enchantments1.get(i), levels1.get(i) + 1);
                    } else if(levels1.get(i) < levels2.get(j) && enchantments1.get(i).getMaxLevel() != levels1.get(i)) {
                        enchantments.put(enchantments2.get(j), levels2.get(j));
                    } else {
                        enchantments.put(enchantments1.get(i), levels1.get(i));
                    }

                    enchantments2.remove(j);
                    levels2.remove(j);
                    break;
                } else {
                    if(j == enchantments2.size() - 1) {
                        enchantments.put(enchantments1.get(i), levels1.get(i));
                    }
                }
            }
        }
        for(int i = 0; i < enchantments2.size(); i++) {
            enchantments.put(enchantments2.get(i), levels2.get(i));
        }

        for(Enchantment enchantment : item.getEnchantments().keySet()) {
            item.removeEnchantment(enchantment);
        }

        item.addEnchantments(enchantments);

        return item;
    }

    private static int getAddPrice(Enchantment enchantment, int level) {
        return SolAnvil.getInstance().getMainConfig().getConfiguration().getInt("price.combine.add." + enchantment.getName().replace('_', '-') + "-" + level);
    }

    private static int getUpgradeToPrice(Enchantment enchantment, int level) {
        return SolAnvil.getInstance().getMainConfig().getConfiguration().getInt("price.combine.upgrade-to." + enchantment.getName().replace('_', '-') + "-" + level);
    }

    private static List<Enchantment> enchantmentsToAdd(ItemStack item1, ItemStack item2) {
        return Arrays.asList();
    }

    public static boolean isValidItem(Material material) {
        switch (material) {
            case ENCHANTED_BOOK:
            case WOOD_AXE:
            case STONE_AXE:
            case GOLD_AXE:
            case IRON_AXE:
            case DIAMOND_AXE:
            case WOOD_PICKAXE:
            case STONE_PICKAXE:
            case GOLD_PICKAXE:
            case IRON_PICKAXE:
            case DIAMOND_PICKAXE:
            case WOOD_HOE:
            case STONE_HOE:
            case GOLD_HOE:
            case IRON_HOE:
            case DIAMOND_HOE:
            case WOOD_SPADE:
            case STONE_SPADE:
            case GOLD_SPADE:
            case IRON_SPADE:
            case DIAMOND_SPADE:
            case FISHING_ROD:
            case SHEARS:
            case FLINT_AND_STEEL:
            case CARROT_STICK:
            case WOOD_SWORD:
            case STONE_SWORD:
            case GOLD_SWORD:
            case IRON_SWORD:
            case DIAMOND_SWORD:
            case BOW:
            case LEATHER_HELMET:
            case CHAINMAIL_HELMET:
            case GOLD_HELMET:
            case IRON_HELMET:
            case DIAMOND_HELMET:
            case LEATHER_CHESTPLATE:
            case CHAINMAIL_CHESTPLATE:
            case GOLD_CHESTPLATE:
            case IRON_CHESTPLATE:
            case DIAMOND_CHESTPLATE:
            case LEATHER_LEGGINGS:
            case CHAINMAIL_LEGGINGS:
            case GOLD_LEGGINGS:
            case IRON_LEGGINGS:
            case DIAMOND_LEGGINGS:
            case LEATHER_BOOTS:
            case CHAINMAIL_BOOTS:
            case GOLD_BOOTS:
            case IRON_BOOTS:
            case DIAMOND_BOOTS:
                return true;
            default:
                return false;
        }
    }

    private static ItemStack createItem(final String path, int price, final String tag) {
        String priceString = Integer.toString(price);
        if(price == -1) {
            priceString = "???";
        }

        String title = ChatColor.translateAlternateColorCodes('&', SolAnvil.getInstance().getMainConfig().getConfiguration().getString(path + ".title"));
        boolean isEnchanted = SolAnvil.getInstance().getMainConfig().getConfiguration().getBoolean(path + ".enchanted");
        Material material = Material.valueOf(SolAnvil.getInstance().getMainConfig().getConfiguration().getString(path + ".material"));
        int color = SolAnvil.getInstance().getMainConfig().getConfiguration().getInt(path + ".color");
        List<String> lore = Lists.newArrayList();
        for(String item : SolAnvil.getInstance().getMainConfig().getConfiguration().getStringList(path + ".lore")) {
            lore.add(ChatColor.translateAlternateColorCodes('&', item.replace("{price}", priceString)));
        }

        ItemStack item;
        if(color == -1) {
            item = new ItemStack(material);
        } else {
            item = new ItemStack(material, 1, (short) color);
        }
        ItemMeta meta = item.getItemMeta();

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);

        meta.setDisplayName(title);

        if(isEnchanted) {
            meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        }

        meta.setLore(lore);
        item.setItemMeta(meta);

        net.minecraft.server.v1_8_R3.ItemStack itemNMS = CraftItemStack.asNMSCopy(item);
        NBTTagCompound itemCompound = (itemNMS.hasTag()) ? itemNMS.getTag() : new NBTTagCompound();

        itemCompound.set(tag, new NBTTagString(tag));
        itemNMS.setTag(itemCompound);

        item = CraftItemStack.asBukkitCopy(itemNMS);

        return item;
    }
}

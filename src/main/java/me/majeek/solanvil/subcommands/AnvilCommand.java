package me.majeek.solanvil.subcommands;

import com.google.common.collect.Lists;
import me.majeek.solanvil.SolAnvil;
import me.majeek.solanvil.handlers.AnvilHandler;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagString;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class AnvilCommand implements SubCommand {
    @Override
    public String[] getName() {
        return new String[]{ "anvil" };
    }

    @Override
    public String getPermission() {
        return "solanvil.anvil";
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
        ItemStack anvil = new ItemStack(Material.ANVIL);

        // Edit meta data
        ItemMeta anvilMeta = anvil.getItemMeta();

        String name = ChatColor.translateAlternateColorCodes('&', SolAnvil.getInstance().getMainConfig().getConfiguration().getString("anvil.name"));
        List<String> lore = Lists.newArrayList();
        for(String item : SolAnvil.getInstance().getMainConfig().getConfiguration().getStringList("anvil.lore")) {
            lore.add(ChatColor.translateAlternateColorCodes('&', item));
        }

        anvilMeta.setDisplayName(name);
        anvilMeta.setLore(lore);

        // Set meta data
        anvil.setItemMeta(anvilMeta);

        // Edit NBT tag
        net.minecraft.server.v1_8_R3.ItemStack nmsAnvil = CraftItemStack.asNMSCopy(anvil);
        NBTTagCompound anvilCompound = (nmsAnvil.hasTag()) ? nmsAnvil.getTag() : new NBTTagCompound();

        anvilCompound.set(AnvilHandler.getAnvilIdentity(), new NBTTagString(AnvilHandler.getAnvilIdentity()));
        nmsAnvil.setTag(anvilCompound);

        // Set NBT tag
        anvil = CraftItemStack.asBukkitCopy(nmsAnvil);

        // Give item
        Player player = (Player) sender;
        if(player.getInventory().firstEmpty() == -1) {
            String content = SolAnvil.getInstance().getMessagesConfig().getConfiguration().getString("inventory-full");
            SolAnvil.getInstance().getCommandManager().sendMessage(sender, content);
        } else {
            player.getInventory().setItem(player.getInventory().firstEmpty(), anvil);

            String content = SolAnvil.getInstance().getMessagesConfig().getConfiguration().getString("anvil");
            SolAnvil.getInstance().getCommandManager().sendMessage(sender, content);
        }
    }
}

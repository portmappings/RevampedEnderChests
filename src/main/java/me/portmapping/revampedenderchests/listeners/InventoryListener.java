package me.portmapping.revampedenderchests.listeners;

import me.portmapping.revampedenderchests.Main;
import me.portmapping.revampedenderchests.object.EnderChestOwner;
import me.portmapping.revampedenderchests.object.tiers.Tier;
import me.portmapping.revampedenderchests.utils.chat.CC;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

public class InventoryListener implements Listener {

    private final Main instance = Main.getInstance();
    @EventHandler
    private void onCloseInventory(InventoryCloseEvent event){
        if(!(event.getInventory().getHolder() instanceof EnderChestOwner)) return;

        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();
        InventoryView view = event.getView();


        List<ItemStack> contentsToSet = new ArrayList<>();

        for(int i = 0 ; i < inventory.getSize() ; i++){
            ItemStack itemStack = inventory.getItem(i);
            if(itemStack == null || itemStack.getType()== Material.AIR){
                continue;
            }
            boolean isTierItem = true;
            for(Tier tier : instance.getEnderChestManager().getTiers()){
                if(!tier.toItemStack().isSimilar(itemStack)) isTierItem = false;
            }
            if(isTierItem){
                contentsToSet.add(itemStack);
            }else {
                contentsToSet.add(new ItemStack(Material.AIR));
            }
        }
        instance.getEnderChestManager().getEnderChest(player).getInventory().clear();
        for(ItemStack itemStack : contentsToSet){
            instance.getEnderChestManager().getEnderChest(player).getInventory().addItem(itemStack);
        }
    }

    @EventHandler
    private void onOpenInventory(InventoryOpenEvent event){
        if(!(event.getInventory().getHolder() instanceof EnderChestOwner)) return;

        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();
        InventoryView view = event.getView();

        view.setTitle(CC.translate(instance.getSettingsConfig().getConfig().getString("ENDERCHEST.TITLE")));
        inventory.setContents(instance.getEnderChestManager().getEnderChest(player).getInventory().getContents());

        for(Tier tier : instance.getEnderChestManager().getTiers()){
            if(tier.isEnabled()){
                for(int i = tier.getSlotOne(); i<tier.getSlotTwo(); i++){
                    if(inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR){
                        inventory.setItem(i,tier.toItemStack());
                    }
                }
            }
        }

    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent e){
        if(!(e.getInventory().getHolder() instanceof EnderChestOwner)) return;

        if(e.getCurrentItem()== null || e.getCurrentItem().getType() == Material.AIR) return;

        boolean isTierItem = false;
        for(Tier tier : instance.getEnderChestManager().getTiers()){
            if(tier.toItemStack().isSimilar(e.getCurrentItem())) isTierItem = true;
        }

        e.setCancelled(isTierItem);

    }

}

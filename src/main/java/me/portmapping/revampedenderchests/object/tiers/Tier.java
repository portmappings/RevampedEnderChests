package me.portmapping.revampedenderchests.object.tiers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.portmapping.revampedenderchests.utils.chat.CC;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

@Getter
@AllArgsConstructor
public class Tier {
    private String name;
    private String displayName;
    private List<String> lore;
    private int slotOne;
    private int slotTwo;
    private Material material;
    private boolean glowing;
    private String permission;
    private boolean enabled;


    public ItemStack toItemStack(){
        ItemStack itemStack = new ItemStack(this.getMaterial(),1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(CC.translate(this.getDisplayName()));
        itemMeta.setLore(CC.translate(this.getLore()));

        if(this.isGlowing()){
            itemStack.addUnsafeEnchantment(Enchantment.DURABILITY,5);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }



}

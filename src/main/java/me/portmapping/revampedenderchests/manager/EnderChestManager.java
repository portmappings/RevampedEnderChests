package me.portmapping.revampedenderchests.manager;

import lombok.Getter;
import me.portmapping.revampedenderchests.Main;
import me.portmapping.revampedenderchests.object.EnderChest;
import me.portmapping.revampedenderchests.object.EnderChestOwner;
import me.portmapping.revampedenderchests.object.tiers.Tier;
import me.portmapping.revampedenderchests.utils.chat.CC;
import me.portmapping.revampedenderchests.utils.file.Config;
import me.portmapping.revampedenderchests.utils.file.FileConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.*;

@Getter

public class EnderChestManager {


    private Map<UUID, EnderChest> enderChestMap;
    private List<Tier> tiers;
    private Main instance;
    private FileConfiguration settingsConfig;
    private FileConfiguration dataConfig;
    public EnderChestManager(Main instance){
        this.instance = instance;
        this.settingsConfig = instance.getSettingsConfig().getConfig();
        this.dataConfig = instance.getDataConfig().getConfig();
        this.enderChestMap = new HashMap<>();
        this.tiers = new ArrayList<>();
        this.load();
    }

    public void disable(){
        this.enderChestMap.forEach((uuid, enderChest) -> {
            dataConfig.set("DATA."+uuid.toString(),inventoryToString(enderChest.getInventory()));
        });
    }

    private void load(){
        //Load tiers
        String path = "ENDERCHEST.TIERS.TIER-SLOTS.";
        for(String key : settingsConfig.getConfigurationSection("ENDERCHEST.TIERS.TIER-SLOTS").getKeys(false)){
            Tier tier = new Tier(key,
                            settingsConfig.getString(path+key+".DISPLAY-NAME"),
                            settingsConfig.getStringList(path+key+".LORE"),
                            settingsConfig.getInt(path+key+".SLOT-ONE"),
                            settingsConfig.getInt(path+key+".SLOT-TWO"),
                            Material.valueOf(settingsConfig.getString(path+key+".MATERIAL")),
                            settingsConfig.getBoolean(path+key+".GLOW"),
                            settingsConfig.getString(path+key+".PERMISSION"),
                            settingsConfig.getBoolean(path+key+".ENABLED"));
            this.tiers.add(tier);
        }

        //Load enderchests
        if(settingsConfig.getConfigurationSection("DATA")==null) return;

        for(String key : settingsConfig.getConfigurationSection("DATA").getKeys(false)){
            EnderChest enderChest = new EnderChest(stringToInventory(dataConfig.getString("DATA."+key)));
            this.enderChestMap.put(UUID.fromString(key),enderChest);
        }

    }


    public EnderChest getEnderChest(UUID uuid){
        if(this.enderChestMap.get(uuid)==null){
            Inventory inventory = Bukkit.createInventory(new EnderChestOwner(), settingsConfig.getInt("ENDERCHEST.SIZE"));
            EnderChest enderChest = new EnderChest(inventory);
            enderChestMap.put(uuid,enderChest);
            return this.enderChestMap.get(uuid);
        }else {
            return this.enderChestMap.get(uuid);
        }

    }
    public EnderChest getEnderChest(Player player){
        return getEnderChest(player.getUniqueId());
    }

    public void openEnderChest(Player player){
        player.openInventory(getEnderChest(player).getInventory());
    }
    private boolean isLoreSame(List<String> tierLore, List<String> itemLore){
        if(tierLore.size() != itemLore.size()) return false;
        boolean toReturn = true;
        for(int i = 0; i<tierLore.size() ; i++){
            if(!CC.strip(tierLore.get(i)).equalsIgnoreCase(CC.strip((itemLore.get(i))))){
                toReturn = false;
            }
        }
        return toReturn;
    }
    public boolean isItemStackTier(ItemStack itemStack){
        if(tiers.isEmpty()) return false;

        for(int i = 0; i<tiers.size(); i++){
            Tier tier = tiers.get(i);
            if(CC.strip(tier.getDisplayName()).equalsIgnoreCase(CC.strip(itemStack.getItemMeta().getDisplayName()))
                    && tier.getMaterial() == itemStack.getType() && this.isLoreSame(tier.getLore(),itemStack.getItemMeta().getLore())){
                return true;


            }else{
                continue;
            }

        }
        return false;
    }


    public String inventoryToString(Inventory inventory) {
        try {
            ByteArrayOutputStream str = new ByteArrayOutputStream();
            BukkitObjectOutputStream data = new BukkitObjectOutputStream(str);
            data.writeInt(inventory.getSize());
            for (int i = 0; i < inventory.getSize(); i++) {
                data.writeObject(inventory.getItem(i));
            }
            data.close();
            return Base64.getEncoder().encodeToString(str.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public Inventory stringToInventory(String inventoryData) {
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(inventoryData));
            BukkitObjectInputStream data = new BukkitObjectInputStream(stream);
            Inventory inventory = Bukkit.createInventory(null, data.readInt());
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, (ItemStack) data.readObject());
            }
            data.close();
            return inventory;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

package me.portmapping.revampedenderchests.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

@AllArgsConstructor
@Getter
@Setter
public class EnderChestOwner implements InventoryHolder {


    @Override
    public Inventory getInventory() {
        return this.getInventory();
    }
}

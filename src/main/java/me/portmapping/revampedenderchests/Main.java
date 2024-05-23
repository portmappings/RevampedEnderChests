package me.portmapping.revampedenderchests;

import lombok.Getter;
import me.portmapping.revampedenderchests.commands.EnderChestCommand;
import me.portmapping.revampedenderchests.listeners.InventoryListener;
import me.portmapping.revampedenderchests.manager.EnderChestManager;
import me.portmapping.revampedenderchests.utils.file.Config;
import me.portmapping.revampedenderchests.utils.file.FileConfig;
import org.bukkit.plugin.java.JavaPlugin;


@Getter
public final class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    private EnderChestManager enderChestManager;
    private FileConfig settingsConfig;
    private Config dataConfig;

    @Override
    public void onEnable() {
        instance = this;

        this.settingsConfig = new FileConfig(this,"settings.yml");
        this.dataConfig = new Config("data",this);

        this.enderChestManager =  new EnderChestManager(this);
        getCommand("enderchest").setExecutor(new EnderChestCommand());
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        this.enderChestManager.disable();
        // Plugin shutdown logic
    }
}

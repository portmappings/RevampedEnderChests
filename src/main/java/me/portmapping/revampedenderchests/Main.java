package me.portmapping.revampedenderchests;

import lombok.Getter;
import me.portmapping.revampedenderchests.commands.EnderChestCommand;
import me.portmapping.revampedenderchests.listeners.InventoryListener;
import me.portmapping.revampedenderchests.manager.EnderChestManager;
import me.portmapping.revampedenderchests.utils.chat.CC;
import me.portmapping.revampedenderchests.utils.file.Config;
import me.portmapping.revampedenderchests.utils.file.FileConfig;
import org.bukkit.Bukkit;
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


        Bukkit.getLogger().info(CC.LINE);
        Bukkit.getLogger().info(CC.AQUA+"Revamped EnderChests");
        Bukkit.getLogger().info(CC.WHITE+"Author: PortMapping");
        Bukkit.getLogger().info(CC.AQUA+"Git Repo: https://github.com/portmappings/RevampedEnderChests");
        this.loadCommands();
        this.loadConfig();
        this.loadListeners();
        Bukkit.getLogger().info(CC.LINE);

        this.enderChestManager =  new EnderChestManager(this);


        // Plugin startup logic

    }

    private void loadCommands(){
        getCommand("enderchest").setExecutor(new EnderChestCommand());
        Bukkit.getLogger().info(CC.GREEN+"Loaded 2 commands...");

    }
    private void loadConfig(){
        this.settingsConfig = new FileConfig(this,"settings.yml");
        this.dataConfig = new Config("data",this);
        Bukkit.getLogger().info(CC.GREEN+"Loaded 2 configs...");
    }
    private void loadListeners(){
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        Bukkit.getLogger().info(CC.GREEN+"Loaded 2 listener...");
    }

    @Override
    public void onDisable() {
        this.enderChestManager.disable();
        // Plugin shutdown logic
    }
}

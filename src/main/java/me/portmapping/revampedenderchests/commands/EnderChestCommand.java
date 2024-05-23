package me.portmapping.revampedenderchests.commands;

import lombok.Getter;
import me.portmapping.revampedenderchests.Main;
import me.portmapping.revampedenderchests.object.EnderChest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnderChestCommand implements CommandExecutor {

    private Main instance = Main.getInstance();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(!(commandSender instanceof Player)) return false;

        Player player = (Player) commandSender;



            instance.getEnderChestManager().openEnderChest(player);






        return false;
    }
}

package me.portmapping.revampedenderchests.commands;

import lombok.Getter;
import me.portmapping.revampedenderchests.Main;
import me.portmapping.revampedenderchests.object.EnderChest;
import me.portmapping.revampedenderchests.utils.chat.CC;
import org.bukkit.Bukkit;
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
        if(args.length == 0){

            instance.getEnderChestManager().openEnderChest(player);
        }

        if(args.length == 1){
            if(!player.hasPermission(instance.getSettingsConfig().getConfig().getString("ENDERCHEST.ADMIN-PERMISSION"))){
                instance.getEnderChestManager().openEnderChest(player);
            }else{
                Player target = Bukkit.getPlayer(args[0]);
                if(target!=null){
                    player.sendMessage(CC.GREEN+"Seeing "+target.getName()+"'s enderchest");

                }else {
                    player.sendMessage(CC.RED+"That player is not online.");
                }
            }

        }else {
            instance.getEnderChestManager().openEnderChest(player);
        }

        return false;
    }
}

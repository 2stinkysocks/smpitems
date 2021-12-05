package com.twostinkysocks.smpitems;

import com.twostinkysocks.smpitems.items.NecromancerStick;
import com.twostinkysocks.smpitems.items.SmpItem;
import com.twostinkysocks.smpitems.items.WarpSword;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main extends JavaPlugin implements CommandExecutor, TabCompleter {

    private ArrayList<SmpItem> items;

    @Override
    public void onEnable() {
        items = new ArrayList<>();
        getCommand("cgive").setExecutor(this);
        getCommand("cgive").setTabCompleter(this);
        getServer().getPluginManager().registerEvents(new Events(this, items), this);

        registerItem(new NecromancerStick(this));
        registerItem(new WarpSword(this));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(command.getName().equals("cgive")) {
                if(!p.isOp()) {
                    p.sendMessage(ChatColor.RED + "You don't have permission to do that!");
                    return true;
                }
                if(args.length == 0) {
                    p.sendMessage(ChatColor.RED + "You need to enter an item to give!");
                    return true;
                }
                boolean foundItem = false;
                for(SmpItem i : items) {
                    if(args[0].equals(i.getItemId())) {
                        foundItem = true;
                        p.getInventory().addItem(i.getItemStack());
                        p.sendMessage(ChatColor.GREEN + "Gave 1x " + i.getItemId());
                    }
                }
                if(!foundItem) {
                    p.sendMessage(ChatColor.RED + "That item doesn't exist!");
                }
            }
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        List<String> list = new ArrayList<>();
        if(sender instanceof Player) {
            if(cmd.getName().equals("cgive")) {
                if(args.length == 1) {
                    for(SmpItem i : items) {
                        list.add(i.getItemId());
                    }
                    Collections.sort(list);
                    return list;
                }
            }
        }
        return list;
    }

    public void registerItem(SmpItem i) {
        items.add(i);
    }


}

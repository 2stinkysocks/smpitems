package com.twostinkysocks.smpitems;

import com.twostinkysocks.smpitems.items.SmpItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class Events implements Listener {

    private Main plugin;
    private ArrayList<SmpItem> items;

    public Events(Main plugin, ArrayList<SmpItem> items) {
        this.plugin = plugin;
        this.items = items;
    }


    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
        if(item != null && item.getItemMeta() != null) {
            if(item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "ITEM_ID"), PersistentDataType.STRING)) {
                String itemId = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "ITEM_ID"), PersistentDataType.STRING);
                if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
                    for(SmpItem i : items) {
                        if(itemId.equals(i.getItemId())) {
                            i.getRightClick().accept(e.getPlayer());
                        }
                    }
                } else if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
                    if(e.getPlayer().getInventory().getItemInMainHand().getType() != Material.AIR) {
                        for(SmpItem i : items) {
                            if(itemId.equals(i.getItemId())) {
                                i.getLeftClick().accept(e.getPlayer());
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent e) {
        if(e.getTarget() instanceof Player) {
            Player p = (Player) e.getTarget();
            ItemStack item = p.getInventory().getItemInMainHand();
            if(item != null && item.getItemMeta() != null) {
                if(item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "ITEM_ID"), PersistentDataType.STRING)) {
                    String itemId = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "ITEM_ID"), PersistentDataType.STRING);
                    for(SmpItem i : items) {
                        if(itemId.equals(i.getItemId())) {
                            i.getEntityTarget().accept(e);
                        }
                    }
                }
            }
        }
    }
}

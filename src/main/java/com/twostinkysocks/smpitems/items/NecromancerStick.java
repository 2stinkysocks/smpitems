package com.twostinkysocks.smpitems.items;

import com.twostinkysocks.smpitems.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.UUID;

public class NecromancerStick extends SmpItem {

    private HashMap<UUID, Long> cooldown;
    private HashMap<UUID, Long> shortCooldown;

    public NecromancerStick(Main plugin) {
        super("§6Necromancer Stick", "NECROMANCER_STICK", Material.STICK, plugin,
                "",
                "§6Item ability: Undead Army §e§lRIGHT CLICK",
                "§7Spawns a zombie on cast",
                "",
                "§6Item ability: ??? §e§lLEFT CLICK",
                "§7§o???",
                "",
                "§6§lLEGENDARY"
        );
        cooldown = new HashMap<>();
        shortCooldown = new HashMap<>();
        setLeftClick(p -> {
            if(!cooldown.containsKey(p.getUniqueId()) || cooldown.get(p.getUniqueId()) < System.currentTimeMillis()) {
                cooldown.put(p.getUniqueId(), System.currentTimeMillis() + 30000); // 30 seconds
                Block b = p.getTargetBlock(null, 60);
                b.getWorld().spawnEntity(b.getLocation().add(0, 30, 0), EntityType.GIANT);
                p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SPAWN, 3.0F, 1.0F);
            } else {
                p.sendMessage(ChatColor.RED + "The wand sizzles, but nothing happens... Maybe if you wait some time...");
                p.playSound(p.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 3.0F, 1.0F);
            }
        });
        setRightClick(p -> {
            if((!shortCooldown.containsKey(p.getUniqueId()) || shortCooldown.get(p.getUniqueId()) < System.currentTimeMillis())) {
                shortCooldown.put(p.getUniqueId(), System.currentTimeMillis() + 1000);
                Block b = p.getTargetBlock(null, 60);
                b.getWorld().spawnEntity(b.getLocation().add(0, 1, 0), EntityType.ZOMBIE);
                p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 3.0F, 2.0F);
            } else {
                p.sendMessage(ChatColor.RED + "The wand sizzles, but nothing happens... Maybe if you wait some time...");
                p.playSound(p.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 3.0F, 1.0F);
            }

        });
        setEntityTarget(e -> {
            if(e.getEntityType() == EntityType.ZOMBIE || e.getEntityType() == EntityType.ZOMBIE_VILLAGER) {
                e.setTarget(null);
            }
        });
    }
}

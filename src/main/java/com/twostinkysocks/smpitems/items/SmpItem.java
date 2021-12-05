package com.twostinkysocks.smpitems.items;

import com.twostinkysocks.smpitems.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.function.Consumer;

public class SmpItem {

    private String name;
    private String[] lore;
    private Material material;
    private ItemStack item;
    private Main plugin;
    private String itemId;
    private Consumer<Player> rc, lc;
    private Consumer<EntityTargetEvent> entityTarget;

    public SmpItem(String name, String itemId, Material material, Main plugin, String...lore) {
        this.name = name;
        this.lore = lore;
        this.material = material;
        this.plugin = plugin;
        this.itemId = itemId;
        this.rc = p -> {};
        this.lc = p -> {};
        this.entityTarget = e -> {};

        this.item = new ItemStack(material);

        ItemMeta im = item.getItemMeta();
        im.setDisplayName(name);
        im.setLore(List.of(lore));

        im.getPersistentDataContainer().set(new NamespacedKey(plugin, "ITEM_ID"), PersistentDataType.STRING, itemId);

        item.setItemMeta(im);
    }

    public ItemStack getItemStack() {
        return item;
    }

    public String getItemId() {
        return itemId;
    }

    public void setRightClick(Consumer<Player> toRun) {
        this.rc = toRun;
    }

    public void setLeftClick(Consumer<Player> toRun) {
        this.lc = toRun;
    }

    public void setEntityTarget(Consumer<EntityTargetEvent> toRun) {
        this.entityTarget = toRun;
    }

    public Consumer<Player> getRightClick() {
        return rc;
    }

    public Consumer<Player> getLeftClick() {
        return lc;
    }

    public Consumer<EntityTargetEvent> getEntityTarget() {
        return entityTarget;
    }

    public String getName() {
        return name;
    }
}

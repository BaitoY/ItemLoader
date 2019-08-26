package org.baito.forge.cnpcsis.config;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import org.json.JSONObject;

public class SaveItem {
    public String mappingName;
    public ItemStack item;
    public String itemName;
    public int metadata;
    public String nbt;

    public SaveItem(EntityPlayer e, String n) {
        mappingName = n;
        item = e.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).copy();
        itemName = item.getItem().delegate.name().toString();
        metadata = item.getMetadata();
        nbt = item.serializeNBT().toString();
    }

    public SaveItem(JSONObject j) {
        itemName = j.getString("itemName");
        metadata = j.getInt("metadata");
        nbt = j.getString("nbt");
        mappingName = j.getString("mappingName");
        try {
            item = new ItemStack((NBTTagCompound) JsonToNBT.getTagFromJson(nbt));
        } catch (NBTException e) {
            e.printStackTrace();
        }
    }

    public JSONObject toJson() {
        JSONObject j = new JSONObject();
        j.put("mappingName", mappingName);
        j.put("itemName", itemName);
        j.put("metadata", metadata);
        j.put("nbt", nbt);
        return j;
    }
}

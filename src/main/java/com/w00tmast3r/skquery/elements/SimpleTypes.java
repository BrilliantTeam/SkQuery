package com.w00tmast3r.skquery.elements;

import com.w00tmast3r.skquery.api.AbstractTask;
import com.w00tmast3r.skquery.skript.TypeClassInfo;
import com.w00tmast3r.skquery.util.minecraft.MoonPhase;
import org.bukkit.Art;
import org.bukkit.FireworkEffect;
import org.bukkit.Sound;
import org.bukkit.entity.Villager;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.scoreboard.DisplaySlot;


public class SimpleTypes extends AbstractTask {

    @Override
    public void run() {
        TypeClassInfo.create(DisplaySlot.class, "displayslot").register();
        TypeClassInfo.create(Sound.class, "sound").register();
        TypeClassInfo.create(FireworkEffect.Type.class, "fireworktype").register();
        TypeClassInfo.create(InventoryType.class, "inventorytype").register();
        TypeClassInfo.create(Villager.Profession.class, "profession").register();
        TypeClassInfo.create(Art.class, "art").register();
        TypeClassInfo.create(MoonPhase.class, "moonphase").register();
        TypeClassInfo.create(EntityRegainHealthEvent.RegainReason.class, "regainreason").register();
    }
}

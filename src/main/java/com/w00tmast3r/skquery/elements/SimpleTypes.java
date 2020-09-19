package com.w00tmast3r.skquery.elements;

import com.w00tmast3r.skquery.annotations.AbstractTask;
import com.w00tmast3r.skquery.skript.EnumClassInfo;
import com.w00tmast3r.skquery.skript.TypeClassInfo;
import com.w00tmast3r.skquery.util.minecraft.MoonPhase;

import java.sql.ResultSet;

import org.bukkit.Art;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Villager;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.scoreboard.DisplaySlot;


public class SimpleTypes extends AbstractTask {

	@Override
	public void run() {
		EnumClassInfo.create(EntityRegainHealthEvent.RegainReason.class, "regainreason").register();
		EnumClassInfo.create(Villager.Profession.class, "profession").register();
		EnumClassInfo.create(InventoryType.class, "inventorytype").register();
		EnumClassInfo.create(DisplaySlot.class, "displayslot").register();
		EnumClassInfo.create(MoonPhase.class, "moonphase").register();
		EnumClassInfo.create(Particle.class, "particle").register();
		EnumClassInfo.create(Sound.class, "sound").register();
		EnumClassInfo.create(Art.class, "art").register();

		TypeClassInfo.create(WorldBorder.class, "worldborder").register();
		TypeClassInfo.create(ResultSet.class, "queryresult").register();
	}

}

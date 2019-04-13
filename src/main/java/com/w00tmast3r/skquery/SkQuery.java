package com.w00tmast3r.skquery;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;

import com.w00tmast3r.skquery.elements.events.EvtLambdaWhen;
import com.w00tmast3r.skquery.skript.DynamicEnumTypes;
import com.w00tmast3r.skquery.skript.LambdaCondition;
import com.w00tmast3r.skquery.skript.SkqFileRegister;
import com.w00tmast3r.skquery.sql.ScriptCredentials;
import com.w00tmast3r.skquery.util.menus.FormattedSlotManager;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class SkQuery extends JavaPlugin {

	private static SkQuery instance;
	private static SkriptAddon addonInstance;
	public static Boolean LIME_EDIT = true;
	private static Metrics metrics;

	@Override
	public void onEnable() {
		instance = this;
		DynamicEnumTypes.register();
		addonInstance = Skript.registerAddon(this).setLanguageFileDirectory("lang");
		Registration.enableSnooper();
		Bukkit.getPluginManager().registerEvents(new FormattedSlotManager(), this);
		SkqFileRegister.load();
		metrics = new Metrics(this);
		//new Documentation(this);
	}

	@Override
	public void onDisable() {
		ScriptCredentials.clear();
		Set<LambdaCondition> limiter = EvtLambdaWhen.limiter;
		if (limiter == null || limiter.isEmpty())
			return;
		EvtLambdaWhen.limiter.clear();
	}

	public static String cc(String colour) {
		return ChatColor.translateAlternateColorCodes('&', colour);
	}

	public static SkriptAddon getAddonInstance() {
		return addonInstance;
	}

	public static SkQuery getInstance() {
		return instance;
	}

	public static Metrics getMetrics() {
		return metrics;
	}

}

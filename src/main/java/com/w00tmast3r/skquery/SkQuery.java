package com.w00tmast3r.skquery;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;

import com.w00tmast3r.skquery.db.ScriptCredentials;
import com.w00tmast3r.skquery.elements.events.EvtLambdaWhen;
import com.w00tmast3r.skquery.skript.DynamicEnumTypes;
import com.w00tmast3r.skquery.skript.SkqFileRegister;
import com.w00tmast3r.skquery.util.custom.menus.v2_.FormattedSlotManager;
import com.w00tmast3r.skquery.util.custom.note.MidiUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class SkQuery extends JavaPlugin {

    private static SkQuery instance;
    private static SkriptAddon addonInstance;
    public static Boolean LIME_EDIT = true;

    @Override
    public void onEnable() {
    	instance = this;
        DynamicEnumTypes.register();
        getDataFolder().mkdirs();
        addonInstance = Skript.registerAddon(this);
        Registration.enableSnooper();
        Bukkit.getPluginManager().registerEvents(new FormattedSlotManager(), this);
        SkqFileRegister.load();
        new Metrics(this);
    }

    @Override
    public void onDisable() {
        ScriptCredentials.clear();
        MidiUtil.dump();
        EvtLambdaWhen.limiter.clear();
    }
    
    public static String cc(String colour) {
		return ChatColor.translateAlternateColorCodes('&', colour);
	}

    public static SkQuery getInstance() {
        return instance;
    }

    public static SkriptAddon getAddonInstance() {
        return addonInstance;
    }
}
/*
Added a Lambda when event:
	
	when %predicate% [[with] limit[(ing|er)] %-boolean%]
	
This event is called when a certain condition is met.
If the limiter option is set, this lambda event will only be called once until the script is reloaded again.
It basically allows for users to create their own defined event. It also enables the ability to use variables in events.

TODO: test if the lambda is null. Using the c/condition of the given lambda
*/

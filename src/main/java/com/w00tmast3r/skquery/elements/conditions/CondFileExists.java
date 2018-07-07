package com.w00tmast3r.skquery.elements.conditions;

import java.io.File;

import org.bukkit.event.Event;

import com.w00tmast3r.skquery.api.Patterns;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Name("Is Divisible")
@Description("Checks whether or not a number can be divided into another number.")
@Patterns({"file [exist[(s|ance)] [at]] %string%", "file %string% exists"})
public class CondFileExists extends Condition {
	
	private Expression<String> file;
	
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		file = (Expression<String>) e[0];
		return true;
	}
	
	public String toString(Event e, boolean arg1) {
		return "file [exist[(s|ance)] [at]] %string%";
	}
	
	public boolean check(Event e) {
		try {
			File f = new File(file.getSingle(e));
	        if (f.exists()) {
				return true;
	        } else {
	        	return false;
	        }
		}
		catch (Exception e1) {
			return false;
		}
	}
}
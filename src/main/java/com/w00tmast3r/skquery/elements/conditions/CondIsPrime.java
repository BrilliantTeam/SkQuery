package com.w00tmast3r.skquery.elements.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Checker;
import ch.njol.util.Kleenean;

import org.bukkit.event.Event;

import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Name;
import com.w00tmast3r.skquery.api.Patterns;

@Name("Is Prime")
@Description("Checks whether or not a number is prime.")
@Patterns({"%number% is [a] prime [number]", "%number% is not [a] prime [number]"})
public class CondIsPrime extends Condition {

	private Expression<Number> number;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		number = (Expression<Number>) expressions[0];
		setNegated(matchedPattern == 1);
		return true;
	}
	
	@Override
	public boolean check(Event event) {
		return number.check(event, new Checker<Number>() {
			@Override
			public boolean check(Number number) {
				return isPrime(number.intValue());
			}
		}, isNegated());
	}

	@Override
	public String toString(Event event, boolean debug) {
		return number.toString(event, debug) + " is a prime";
	}

	public static boolean isPrime(int number) {
		if (number % 2 == 0)
			return false;
		for (int i = 3; i * i <= number; i += 2) {
			if (number % i == 0)
				return false;
		}
		return true;
	}

}

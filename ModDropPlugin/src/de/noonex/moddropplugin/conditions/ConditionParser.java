package de.noonex.moddropplugin.conditions;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

public final class ConditionParser
{
	public static Condition[] ParseConditions(String conditionsString) throws ParseException
	{
		List<Condition> conditions = new LinkedList<Condition>();
		String[] keyvaluepairs = conditionsString.split(";");
		
		for(String keyvalue: keyvaluepairs)
		{
			String[] keyvalueArr = keyvalue.split(":");
			
			if(keyvalueArr[0].equalsIgnoreCase("world"))
			{
				conditions.add(ParseWorldCondition(keyvalueArr[1]));
				System.out.println("world condition added: " + keyvalue);
			}
			else if(keyvalueArr[0].equalsIgnoreCase("damage"))
			{
				try
				{
					conditions.add(ParseDamageCondition(keyvalueArr[1]));
					System.out.println("damage condition added: " + keyvalue);
				}
				catch(ParseException ex)
				{
					System.out.println("[ModDrop][WARNING] The damage condition value is not a number." + keyvalue);
					continue;
				}
			}
			else
			{
				continue;
			}
		}
		
		if(conditions.size() <= 0)
		{
			throw new ParseException("No conditions specified.", 0);
		}
		
		return conditions.toArray(new Condition[conditions.size()]);
	}

	private static Condition ParseDamageCondition(String value) throws ParseException
	{
		byte damagevalue;
		
		try
		{
			damagevalue = Byte.parseByte(value);
		}
		catch(NumberFormatException ex)
		{
			throw new ParseException("The damage condition value is not valid", 0);
		}
		
		return new DamageValueCondition(damagevalue);
	}

	private static Condition ParseWorldCondition(String value)
	{
		return new WorldCondition(value);
	}
}
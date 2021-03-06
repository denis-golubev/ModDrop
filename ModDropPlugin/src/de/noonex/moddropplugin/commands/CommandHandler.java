package de.noonex.moddropplugin.commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.java.JavaPlugin;

import de.noonex.moddropplugin.ModDropPlugin;

public class CommandHandler
{
	// singleton stuff
	private static CommandHandler _instance = new CommandHandler();

	private CommandHandler()
	{
		commands = new HashMap<String, AbstractCommand>();
	}

	public static CommandHandler getInstance()
	{
		return _instance;
	}

	// commands
	private Map<String, AbstractCommand> commands;
	private AbstractCommand defaultCommand;
	
	public void SetDefaultCommand(AbstractCommand command)
	{
		defaultCommand = command;
	}

	public void AddCommand(String name, AbstractCommand command)
	{
		if (commands.containsKey(name.toLowerCase()))
		{
			String errorMessage = String.format("The command %s is already added.", name);
			throw new IllegalArgumentException(errorMessage);
		}
		
		commands.put(name, command);
	}

	public void AddCommand(String name, AbstractCommand command, String... aliases)
	{
		try
		{
			this.AddCommand(name.toLowerCase(), command);
		}
		catch (IllegalArgumentException ex)
		{
			ex.printStackTrace();
			throw ex;
		}

		for (String alias : aliases)
		{
			try
			{
				this.AddCommand(alias.toLowerCase(), command);
			}
			catch(IllegalArgumentException ex)
			{
				continue;
			}
		}
	}
	
	public boolean HasCommand(String name)
	{
		return commands.containsKey(name.toLowerCase());
	}
	
	public String ProcessCommand(String commandString, ModDropPlugin plugin)
	{
		String commandParts[] = commandString.split(" ");
		
		if(commandParts.length == 1)
		{
			if(defaultCommand == null)
			{
				String errorString = String.format("[ModDrop] Usage: /md command");
				return errorString;
			}
			else
			{
				return defaultCommand.ExecuteCommand(plugin);
			}
		}
		
		String commandName = commandParts[0];
		
		if(!commands.containsKey(commandName.toLowerCase()))
		{
			String errorString = String.format("[ModDrop] Unknown command: /md %s", commandName);
			return errorString;
		}
		
		AbstractCommand command = commands.get(commandName.toLowerCase());
		
		if(commandParts.length == 1)
		{
			return command.ExecuteCommand(plugin);
		}
		else
		{
			return command.ExecuteCommand(plugin, commandParts);
		}
	}
}

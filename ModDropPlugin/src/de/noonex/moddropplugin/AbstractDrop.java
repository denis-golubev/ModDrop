package de.noonex.moddropplugin;

import org.bukkit.Location;
import org.bukkit.World;

public abstract class AbstractDrop 
{	
	public AbstractDrop()
	{
	}
	
	public abstract void CreateDrop(Location loc, World world);
}
package org.crackpixelmobs;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldGen extends WorldCreator {
    public WorldGen(String name) {
        super(name);
    }

    public boolean onCommand(CommandSender sender, Command command, String name, String[] args) {
        if (command.getName().equalsIgnoreCase("worldgen")) {
            WorldGen wc = new WorldGen("worldgenerator_");
            Player s = (Player) sender;
            Location p = s.getLocation();
            World w = p.getWorld();


            wc.environment(World.Environment.NORMAL);
            wc.type(WorldType.FLAT);

            w = Bukkit.createWorld(wc);
        }
        return true;
    }
}

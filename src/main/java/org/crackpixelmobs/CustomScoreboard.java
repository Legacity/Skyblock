package org.crackpixelmobs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class CustomScoreboard {
    private final JavaPlugin plugin;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d");

    public CustomScoreboard(JavaPlugin plugin) {
        this.plugin = plugin;
        (new UpdateTask(this)).runTaskTimer(plugin, 0L, 1200L);
    }

    public static String parseTime(long time) {
        long hours = time / 1000L + 6L;
        long minutes = time % 1000L * 60L / 1000L;
        String ampm = "AM";

        if (hours >= 12L) {
            hours -= 12L;
            ampm = "PM";
        }

        if (hours >= 12L) {
            hours -= 12L;
            ampm = "AM";
        }

        if (hours == 0L) {
            hours = 12L;
        }

        String mm = "0" + minutes;
        mm = mm.substring(mm.length() - 2, mm.length());
        return hours + ":" + mm + " " + ampm;
    }

    private void updateScoreboard() {
        Iterator<Player> iterator = (Iterator<Player>) Bukkit.getOnlinePlayers().iterator();

        while (iterator.hasNext()) {
            Player player = iterator.next();
            String date = dateFormat.format(new Date());
            String currentTime = (new SimpleDateFormat("dd/MM/yyyy HH:mm")).format(new Date());
            dateFormat.format(new Date(System.currentTimeMillis()));
            Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
            Objective obj = board.registerNewObjective("test", "dummy", ChatColor.YELLOW + "" + ChatColor.BOLD + "SKYBLOCK" + ChatColor.AQUA + "" + ChatColor.BOLD + " ALPHA");
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);

            Score idSkyblock = obj.getScore(ChatColor.GRAY + date + ChatColor.GRAY + " mini01A");
            idSkyblock.setScore(13);

            obj.getScore(ChatColor.GREEN + "").setScore(12);

            Score monthSkyblock = obj.getScore(ChatColor.WHITE + "Early Summer 5th");
            monthSkyblock.setScore(11);

            Score timeSkyblock = obj.getScore(ChatColor.GRAY + currentTime + ChatColor.GRAY + "am" + ChatColor.YELLOW + " ☀");
            timeSkyblock.setScore(10);

            Score locSkyblock = obj.getScore(ChatColor.GRAY + "⏣" + ChatColor.AQUA + " Village");
            locSkyblock.setScore(9);

            obj.getScore(ChatColor.DARK_GREEN + "").setScore(8);

            Score coinSkyblock = obj.getScore(ChatColor.WHITE + "Purse:" + ChatColor.GOLD + PlaceholderAPI.setPlaceholders(player, " %vault_eco_balance% "));
            coinSkyblock.setScore(7);

            Score bitSkyblock = obj.getScore(ChatColor.WHITE + "Bits:" + ChatColor.AQUA + " 0");
            bitSkyblock.setScore(6);

            obj.getScore(ChatColor.BLACK + "").setScore(5);

            Score taskSkyblock = obj.getScore(ChatColor.WHITE + "Objective");
            taskSkyblock.setScore(4);

            Score taskCoreSkyblock = obj.getScore(ChatColor.YELLOW + "Overthrow Dante!");
            taskCoreSkyblock.setScore(3);

            obj.getScore(ChatColor.DARK_BLUE + "").setScore(2);

            Score addressSkyblock = obj.getScore(ChatColor.YELLOW + "mc.gamepixel.org");
            addressSkyblock.setScore(1);

            player.setScoreboard(board);
        }
    }

    public void updateScoreboard(Player player) {
        Scoreboard board = player.getScoreboard();
    }

    private class UpdateTask implements Runnable {
        private final CustomScoreboard customScoreboard;

        public UpdateTask(CustomScoreboard customScoreboard) {
            this.customScoreboard = customScoreboard;
        }

        @Override
        public void run() {
            customScoreboard.updateScoreboard();
        }

        public void runTaskTimer(JavaPlugin plugin, long delay, long period) {
            Bukkit.getScheduler().runTaskTimer(plugin, this, delay, period);
        }}}

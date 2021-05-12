package us.rjks.utils;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import us.rjks.event.CountDownEndsEvent;
import us.rjks.event.CountDownTimeEvent;
import us.rjks.game.GameManager;
import us.rjks.game.Main;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 25.04.2021 / 16:29
 *
 **************************************************************************/

public class Counter {

    private Type countername;
    private int counter, countdown, duration;
    private boolean run = false;

    public Counter(Type name, final int duration) {
        this.countdown = duration;
        this.duration = duration;
        this.countername = name;

        counter = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
            public void run() {
                if (isRunning()) {
                    if (countdown == 30 || countdown == 10 || countdown == 5 || countdown == 3 || countdown == 2 || countdown == 1) {
                        Bukkit.getPluginManager().callEvent(new CountDownTimeEvent(countername, countdown));
                    }
                    if (countdown == 0) {
                        countdown = duration;
                        Bukkit.getPluginManager().callEvent(new CountDownEndsEvent(countername));
                    }
                }
            }
        }, 20L, 20L);
    }

    public void start() {
        run = true;
    }

    public void stop() {
        countdown = duration;
        run = false;
    }

    public void hold() {
        countdown = duration;
    }

    private void deleteCounter() {
        countdown = duration;
        Bukkit.getScheduler().cancelTask(counter);
    }

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    public boolean isRunning() {
        return run;
    }

}
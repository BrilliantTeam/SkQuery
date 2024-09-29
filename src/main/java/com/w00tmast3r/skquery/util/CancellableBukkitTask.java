package com.w00tmast3r.skquery.util;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Bukkit;

public abstract class CancellableBukkitTask implements Runnable {

    private ScheduledTask task;

    public final void setTaskId(ScheduledTask task) {
        this.task = task;
    }

    public final void cancel() {
        if (!task.isCancelled())
            task.cancel();
    }
}

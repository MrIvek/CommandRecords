package com.github.mrivek.commandrecords;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandRecords extends JavaPlugin implements Listener {

	private File logFile = new File(getDataFolder(), "log.txt");
	private File commandRecordsFolder = logFile.getParentFile();

	@Override
	public void onEnable() {
		if (!commandRecordsFolder.exists()) {
			commandRecordsFolder.mkdirs();
		}

		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		getServer().getPluginManager().registerEvents(this, this);
		super.onEnable();
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}

	@EventHandler
	public void executeCMD(PlayerCommandPreprocessEvent event) {
		try {
			BufferedWriter logWriter = new BufferedWriter(new FileWriter(logFile, true));
			Date date = new Date();
			DateFormat dateFormated = new SimpleDateFormat("yyyy:MM:dd ### hh:mm:ss a");
			logWriter.write(dateFormated.format(date) + " ### " + event.getPlayer().getName() + " ### "
					+ event.getMessage() + "\n");
			logWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

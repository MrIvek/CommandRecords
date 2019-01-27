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
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandRecords extends JavaPlugin implements Listener {

	private File commandFile = new File(getDataFolder(), "commandLog.txt");
	private File chatFile = new File(getDataFolder(), "chatLog.txt");
	private File commandRecordsFolder = commandFile.getParentFile();

	@Override
	public void onEnable() {
		if (!commandRecordsFolder.exists()) {
			commandRecordsFolder.mkdirs();
		}

		if (!commandRecordsFolder.exists()) {
			try {
				commandFile.createNewFile();
				chatFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (!commandFile.exists()) {
			try {
				commandFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (!chatFile.exists()) {
			try {
				chatFile.createNewFile();
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
			BufferedWriter commandWriter = new BufferedWriter(new FileWriter(commandFile, true));
			Date date = new Date();
			DateFormat dateFormated = new SimpleDateFormat("yyyy:MM:dd ### hh:mm:ss a");
			commandWriter.write(dateFormated.format(date) + " # " + event.getPlayer().getName() + " # "
					+ event.getMessage() + "\n");
			commandWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@EventHandler
	public void chat(AsyncPlayerChatEvent event) {
		try {
			BufferedWriter chatWriter = new BufferedWriter(new FileWriter(chatFile, true));
			Date date = new Date();
			DateFormat dateFormated = new SimpleDateFormat("yyyy:MM:dd # hh:mm:ss a");
			chatWriter.write(dateFormated.format(date) + " # " + event.getPlayer().getName() + ": " + event.getMessage() + "\n");
			chatWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
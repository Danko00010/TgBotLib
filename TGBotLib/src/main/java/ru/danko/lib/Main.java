package ru.danko.lib;

import java.io.IOException;
import java.util.ArrayList;

import ru.danko.lib.object.Gender;
import ru.danko.lib.object.User;
import ru.danko.lib.tgbot.Bot;

public class Main {
public static void main(String[] args) {
	System.out.println("BotStart");
	
Config.loadConfig();
//botStart();
User us1 = new User("Danko10", 145676689);
us1.setGender(Gender.MALE);
User us2 = new User("Puhla", 22232434);
Config.saveUser(us2);
Config.saveUser(us1);
for(User us:Config.getUsers().values()) {
	System.out.println(us.toString());
}
}

public static void reload() {
	Config.loadConfig();
}
private static void botStart() {
	new Thread(new Runnable() {
		
		@Override
		public void run() {
			new Bot(Config.getString("Token"));
		}
	}).start();
}

}

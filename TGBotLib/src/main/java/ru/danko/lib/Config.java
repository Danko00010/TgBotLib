package ru.danko.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import ru.danko.lib.object.User;
import ru.danko.lib.utils.UserUtils;

public class Config {
    
	private static File file = new File("config.yml");
	private static Map<String, Object> map;
	private static HashMap<Long, User> users = new HashMap<Long, User>();
	public static void saveConfig(Map<String, Object> map) {
		 PrintWriter writer = null;
		try {
			writer = new PrintWriter(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} 
		 DumperOptions options = new DumperOptions(); 
		 options.setIndent(2); 
		 options.setAllowUnicode(false);
		 options.setPrettyFlow(true); 
		 options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK); 
		 Yaml yaml = new Yaml(options); 
		 yaml.dump(map, writer); 
	}
	private static Map<String, Object> getDefaltConfig(){
		 Map<String, Object> map = new HashMap<>(); 
		 
		map.put("Token", "YOUR_TELEGRAM_TOKEN");
		
		return map;
	}
	public static void set(String key, Object obj) {
		if(map.containsKey(key)) {
			map.remove(key);
		}
		map.put(key, obj);
		saveConfig(map);
	}
	public static void saveUser(User us) {
		users.remove(us.getChatId());
		users.put(us.getChatId(), us);
		UserUtils.saveUser(users);
	}
	public static Boolean getBoolean(String key) {
		return (boolean) map.get(key);
	}
	public static String getString(String key) {
		return (String) map.get(key);
	}
	@SuppressWarnings("removal")
	public static Double getDouble(String key) {
		Double d = 0.0;
		try {
		d = new Double(getString(key));
		}catch(NumberFormatException ex) {
			System.out.println("Ошибка конфигураций: ошибка формата числа (double): "+key);
			ex.printStackTrace();
		}
		return d;
	}
	@SuppressWarnings("removal")
	public static Long getLong(String key) {
		long lo = 0;
		try {
		lo = new Long(getString(key));
		}catch(NumberFormatException ex) {
			System.out.println("Ошибка конфигураций: ошибка формата числа (long): "+key);
			ex.printStackTrace();
		}
		return lo;
	}
	@SuppressWarnings("removal")
	public static int getInt(String key) {
		int i = 0;
		try {
		i = new Integer(getString(key));
		}catch(NumberFormatException ex) {
			System.out.println("Ошибка конфигураций: ошибка формата числа (int): "+key);
			ex.printStackTrace();
		}
		return i;
	}
	public static List<String> getStringList(String key) {
		List<String> list = new ArrayList<String>();
		for(String s:getString(key).split(",")) {
			list.add(s);
		}
		return list;
	}
	public static void loadConfig(){
		DumperOptions options = new DumperOptions(); 
		 options.setIndent(2); 
		 options.setAllowUnicode(false);
		 options.setPrettyFlow(true); 
		 options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK); 
		 Yaml yaml = new Yaml(options); 
		  InputStream inputStream = null;
		 
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			try {
				file.createNewFile();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			saveConfig(getDefaltConfig());
			try {
				inputStream = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} 
		 map = yaml.load(inputStream);
		 users= UserUtils.getUsers();
	}
	public static HashMap<Long, User> getUsers(){
		return users;
	}
	public static  Map<String, Object>  getConfig() {
	return map;
	}
	@SuppressWarnings("unchecked")
	public static List<String> getStringListOld(String string) {
		return (List<String>) map.get(string);
	}
}

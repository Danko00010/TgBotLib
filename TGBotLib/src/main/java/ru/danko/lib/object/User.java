package ru.danko.lib.object;

import java.io.Serializable;

public class User implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String name;
private Gender gender = Gender.Null;
private long chatId;
public User(String name, long chatId) {
	this.name = name;
	this.chatId =chatId;
}
public String toString() {
	return "Name: "+name+" Gender "+gender.toString()+" ChatId:"+chatId;
}
public Long getChatId() {
	return chatId;
}
public void setGender(Gender Gender) {
	this.gender = Gender;
}
}


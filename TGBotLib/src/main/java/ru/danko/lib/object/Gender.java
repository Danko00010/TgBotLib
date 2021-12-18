package ru.danko.lib.object;

public enum Gender {
	MALE("М"),FEMALE("Ж"), Null("Не определен");
    private String gendername;
    private Gender(String gendername){
        this.gendername=gendername;
    }

    @Override
    public String toString(){
        return String.valueOf(this.gendername);
    } 
 }

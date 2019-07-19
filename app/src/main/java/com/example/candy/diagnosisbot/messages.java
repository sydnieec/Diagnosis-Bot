package com.example.candy.diagnosisbot;

public class messages {
    private int id;
    private String username;
    private String  message;

    //Constructor
    public messages(int id, String username, String message){
        this.id=id;
        this.username=username;
        this.message=message;


    }
    //Setter, getter


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getmessage() {
        return message;
    }

    public void setmessage(String message) {
        this.message = message;
    }


}

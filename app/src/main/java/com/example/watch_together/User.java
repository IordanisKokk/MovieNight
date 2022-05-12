package com.example.watch_together;

public class User {

    private String username;
    private String email;
    private String avatar;

    private int age;

    public User(){}

    public User(String username, String email ){
        this.username = username;
        this.email = email;
        setDefaultAvatar();
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setDefaultAvatar(){
        this.avatar = "gs://movienight-659f5.appspot.com/img_avatar.png";
    }

}

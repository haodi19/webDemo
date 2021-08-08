package cn.domain;

public class User {
    private int id;
    private String username;
    private String password;
    private Gender gender;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public enum Gender{
        MALE,
        FEMALE
    }

    public User(int id, String username, String password, Gender gender) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                '}';
    }
}

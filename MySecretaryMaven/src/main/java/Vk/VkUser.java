package Vk;

public class VkUser {

    String first_name;
    String last_name;
    boolean online;

    public VkUser() {
    }


    public VkUser(String first_name, String last_name, boolean online) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.online = online;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public boolean isOnline() {
        return online;
    }
}

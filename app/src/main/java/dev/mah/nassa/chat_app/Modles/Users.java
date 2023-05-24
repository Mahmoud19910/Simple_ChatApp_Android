package dev.mah.nassa.chat_app.Modles;

import java.io.Serializable;

public class Users implements Serializable {
    private String email;
    private String id;
    private String name;

    public Users() {
        // Default constructor required for Firebase
    }

    public Users(String email, String id, String name) {
        this.email = email;
        this.id = id;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

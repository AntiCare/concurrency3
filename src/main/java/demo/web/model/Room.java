package demo.web.model;

import java.util.UUID;

public class Room {
    public Boolean available;
    public UUID serialNum;

    public Room(Boolean available) {
        this.available = available;
        this.serialNum = UUID.randomUUID();
    }

    public Room() {
        this.available = true;
        this.serialNum = UUID.randomUUID();
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public UUID getSerialNum() {
        return serialNum;
    }

}

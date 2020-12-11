package demo.web.model;

import java.util.ArrayList;
import java.util.UUID;

public class Hotel{
    public UUID serialNUm;
    public String nameOfHotel;
    public int theAmountOfRooms;
    public ArrayList<Room> roomArrayList;
    public boolean hotelAvailable;

    public Hotel(String nameOfHotel, ArrayList<Room> roomArrayList) {
        this.serialNUm= UUID.randomUUID();
        this.nameOfHotel = nameOfHotel;
        this.theAmountOfRooms = roomArrayList.size();
        this.roomArrayList = roomArrayList;
        this.hotelAvailable = true;
    }

    public  Hotel(){}

    public String getNameOfHotel() {
        return nameOfHotel;
    }

    public void setNameOfHotel(String nameOfHotel) {
        this.nameOfHotel = nameOfHotel;
    }

    public int getTheAmountOfRooms() {
        return roomArrayList.size();
    }

    public void setTheAmountOfRooms(int theAmountOfRooms) {
        this.theAmountOfRooms = roomArrayList.size();
    }

    public ArrayList<Room> getRoomArrayList() {
        return roomArrayList;
    }

    public void setRoomArrayList(ArrayList<Room> roomArrayList) {
        this.roomArrayList = roomArrayList;
    }

    public void addRoom(Room room){
        roomArrayList.add(room);
    }
}
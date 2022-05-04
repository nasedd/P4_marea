package fr.nazodev.p4_mareu.model;


import fr.nazodev.p4_mareu.R;

public enum Rooms {
    ROOM1(R.id.room_1,R.string.room_1),
    ROOM2(R.id.room_2,R.string.room_2),
    ROOM3(R.id.room_3,R.string.room_3),
    ROOM4(R.id.room_4,R.string.room_4),
    ROOM5(R.id.room_5,R.string.room_5),
    ROOM6(R.id.room_6,R.string.room_6),
    ROOM7(R.id.room_7,R.string.room_7),
    ROOM8(R.id.room_8,R.string.room_8),
    ROOM9(R.id.room_9,R.string.room_9),
    ROOM10(R.id.room_10,R.string.room_10);

    public int getIdRoom() {
        return idRoom;
    }

    public int getStringRoom() {
        return stringRoom;
    }

    private final int idRoom;
    private final int stringRoom;

    Rooms(int idRoom, int stringRoom){
        this.idRoom = idRoom;
        this.stringRoom = stringRoom;
    }

    public static Rooms findRoomById(int idRoom){
        for(Rooms room: values()){
            if(room.idRoom == idRoom){
                return room;
            }
        }
        return null;
    }



}

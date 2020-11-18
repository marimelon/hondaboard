package oit.is.chisakiken.hondaboard.model;

import lombok.Data;

@Data
public class RoomUser {
    final int room_id;
    final int user_id;
    final String role;
}

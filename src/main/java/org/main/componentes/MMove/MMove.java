package org.main.componentes.MMove;

import org.main.Room;

import javax.swing.*;
import java.util.List;

public class MMove extends JPanel implements MMoveInt {
    private List<Room>rooms;
    private MMoveListener listener;

    private JButton north;
    private JButton east;

    private JButton west;
    private JButton south;



    @Override
    public void setRooms(List<Room> rooms) {
        this.rooms=rooms;
    }
    public MMove(MMoveListener listener) {

        this.listener = listener;
        initComponente();
    }
    private void initComponente() {}
    @Override
    public void loadRoom(Room room) {

    }
}

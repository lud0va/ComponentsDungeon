package org.main.componentes.MMove;

import Modelo.Room;

import java.util.EventListener;

public interface MMoveListener  extends EventListener {

    void roomUpdated(Room room);


}

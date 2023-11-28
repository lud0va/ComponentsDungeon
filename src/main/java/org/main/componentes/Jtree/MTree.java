package org.main.componentes.Jtree;

import Modelo.Door;
import Modelo.Dungeon;
import Modelo.Room;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.List;

public class MTree extends JScrollPane implements MTreeInterface {
    public JTree createJTree(Dungeon dungeon) {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Dungeon");
        List<Room> rooms = dungeon.getRoom();
        for (Room room : rooms) {
            DefaultMutableTreeNode roomNode = new DefaultMutableTreeNode("Room: " + room.getId());
            rootNode.add(roomNode);

            DefaultMutableTreeNode descriptionNode = new DefaultMutableTreeNode("Description: " + room.getDescription());
            roomNode.add(descriptionNode);

            List<Door> doors = room.getDoorList();
            for (Door door : doors) {
                DefaultMutableTreeNode doorNode = new DefaultMutableTreeNode("Room: " + door.getName() + " a " + door.getDest());
                roomNode.add(doorNode);
            }
        }

        DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
        return new JTree(treeModel);
    }
}
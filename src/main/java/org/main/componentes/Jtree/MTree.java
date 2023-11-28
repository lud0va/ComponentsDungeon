package org.main.componentes.Jtree;

import org.main.Door;
import org.main.Dungeon;
import org.main.Room;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.List;

public class MTree extends JScrollPane implements MTreeInterface {
    public JTree createJTree(Dungeon dungeon) {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Mazmorra");
        List<Room> rooms = dungeon.getRoom();
        for (Room room : rooms) {
            DefaultMutableTreeNode roomNode = new DefaultMutableTreeNode("Habitación: " + room.getId());
            rootNode.add(roomNode);

            DefaultMutableTreeNode descriptionNode = new DefaultMutableTreeNode("Descripción: " + room.getDescription());
            roomNode.add(descriptionNode);

            List<Door> doors = room.getDoors();
            for (Door door : doors) {
                DefaultMutableTreeNode doorNode = new DefaultMutableTreeNode("Puerta: " + door.getName() + " a " + door.getDest());
                roomNode.add(doorNode);
            }
        }

        DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
        return new JTree(treeModel);
    }
}
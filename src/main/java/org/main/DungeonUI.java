package org.main;

import Modelo.Dungeon;
import Modelo.Room;
import log.MLog;
import org.example.Jtree.MTree;
import org.example.MMove;
import org.example.MMoveListener;
import org.example.Mload.MLoad;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DungeonUI {
    private static JTree treePanel;
    private static Dungeon dungeon;
    private static int pos;
    private JFrame f;
    private JPanel menuPanel;
    private JPanel mainpanel;
    private JPanel gamePanel;
    private JPanel roomsPanel;
    private JPanel movesPanel;
    private JScrollPane panelTreeScroll;

    private final TextArea roomInfoText = new TextArea();
    MTree mTree = new MTree();
    MLog movesText = new MLog();

    MMove move;
    private static final TextArea moveText = new TextArea();
    int lineas = 0;

    public DungeonUI() {
        f = new JFrame("Panel Example");
        menuPanel = new JPanel(new BorderLayout());
        mainpanel = new JPanel(new BorderLayout());
        gamePanel = new JPanel(new BorderLayout());
        roomsPanel = new JPanel(new BorderLayout());
        movesPanel = new JPanel();
        panelTreeScroll = new JScrollPane();
        treePanel = new JTree();

        movesText.setPreferredSize(new Dimension(300, 300));
        roomInfoText.setPreferredSize(new Dimension(300, 350));

        roomsPanel.setBackground(new Color(40, 54, 24));
        movesPanel.setBackground(new Color(143, 140, 140));
        panelTreeScroll.setBackground(new Color(155, 150, 150));
        menuPanel.setBounds(40, 80, 900, 500);
        mainpanel.setBackground(Color.white);
        mainpanel.setBounds(40, 80, 900, 490);
        gamePanel.setBounds(40, 80, 450, 490);

        JMenuBar menuBar = new JMenuBar();
        JMenu opciones = new JMenu("Opciones");
        JMenuItem menuitemLoad = new JMenuItem("Load");
        JMenuItem menuitemStart = new JMenuItem("Start");


        menuitemLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                    if (treePanel != null) {
                        pos = 0;
                        lineas = 0;
                        movesText.addLogMessage("");
                        roomInfoText.setText("");
                        panelTreeScroll.remove(treePanel);
                        dungeon = null;
                    }
                    dungeon = readFile();
                    treePanel= mTree.createJTree(dungeon);

                    panelTreeScroll.setViewportView(treePanel);


            }
        });

        menuitemStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    cargarHabitacion();
                    movesText.clearLog();
                } catch (NullPointerException n) {
                    movesText.addLogMessage("Tienes que cargar el XML antes de empezar el juego");

                }
            }
        });

        opciones.add(menuitemLoad);
        opciones.add(menuitemStart);
        menuBar.add(opciones);

        menuPanel.add(menuBar, BorderLayout.NORTH);
        menuPanel.add(mainpanel, BorderLayout.CENTER);


        mainpanel.add(panelTreeScroll, BorderLayout.CENTER);
        MMoveListener listener = new MMoveListener() {
            @Override
            public void roomUpdated(Room room) {
                movesText.addLogMessage("Has ido a la habitacion" + room.getId() + "\n");
            }
        };

        move = new MMove(listener);

        mainpanel.add(gamePanel, BorderLayout.EAST);

        gamePanel.add(movesPanel, BorderLayout.SOUTH);
        gamePanel.add(move, BorderLayout.CENTER);


        movesPanel.add(movesText);

        roomsPanel.add(roomInfoText);


        f.add(menuPanel);
        f.setSize(1000, 1000);
        f.setLayout(null);
        f.setVisible(true);
    }





    public static void main(String args[]) {

        new DungeonUI();

    }

    public void cargarHabitacion() {
        roomInfoText.setText(dungeon.getRoom().get(pos).getDescription());
        move.setRooms(dungeon.getRoom());
        move.loadRoom(dungeon.getRoom().get(pos));




    }


    private static Dungeon readFile() {
        Dungeon dungeon = new Dungeon();
        MLoad load = new MLoad();
        load.loadXMLFile();

        return load.getDungeon();

    }

}











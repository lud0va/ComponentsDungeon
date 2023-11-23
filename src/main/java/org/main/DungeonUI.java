package org.main;

import org.main.componentes.Mload.MLoad;
import org.main.componentes.Mlog.MLog;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private final Button botonNorth = new Button("Norte");
    private final Button botonSur = new Button("Sur");
    private final Button botonEast = new Button("Este");
    private final Button botonOest = new Button("Oeste");
    private final TextArea roomInfoText = new TextArea();

    MLog movesText = new MLog();
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

        // Cambiar colores de los paneles y tamaños


        gamePanel.setBackground(new Color(96, 108, 56));
        roomsPanel.setBackground(new Color(40, 54, 24));
        movesPanel.setBackground(new Color(143, 140, 140));
        panelTreeScroll.setBackground(new Color(155, 150, 150));
        menuPanel.setBounds(40, 80, 900, 500);
        mainpanel.setBackground(Color.white);

        JMenuBar menuBar = new JMenuBar();
        JMenu opciones = new JMenu("Opciones");
        JMenuItem menuitemLoad = new JMenuItem("Load");
        JMenuItem menuitemStart = new JMenuItem("Start");

        menuitemLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    if (treePanel != null) {
                        pos = 0;
                        lineas = 0;
                        movesText.addLogMessage("");
                        roomInfoText.setText("");
                        panelTreeScroll.remove(treePanel);
                        dungeon = null;
                    }
                    dungeon = readFile();
                    treePanel = createJTreeFromDungeon(dungeon);
                    panelTreeScroll.setViewportView(treePanel);
                    botonNorth.setEnabled(false);
                    botonEast.setEnabled(false);
                    botonOest.setEnabled(false);
                    botonSur.setEnabled(false);
                }
            }
        });

        menuitemStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    botonNorth.setEnabled(true);
                    botonEast.setEnabled(true);
                    botonOest.setEnabled(true);
                    botonSur.setEnabled(true);
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

        mainpanel.add(gamePanel, BorderLayout.EAST);
        mainpanel.add(panelTreeScroll, BorderLayout.CENTER);

        gamePanel.add(roomsPanel, BorderLayout.NORTH);
        gamePanel.add(movesPanel, BorderLayout.CENTER);

        movesPanel.add(movesText);

        roomsPanel.add(roomInfoText, BorderLayout.CENTER);
        roomsPanel.add(botonNorth, BorderLayout.NORTH);
        roomsPanel.add(botonSur, BorderLayout.SOUTH);
        roomsPanel.add(botonEast, BorderLayout.EAST);
        roomsPanel.add(botonOest, BorderLayout.WEST);
        botonNorth.addActionListener(clickBtn(botonNorth));
        botonEast.addActionListener(clickBtn(botonEast));
        botonOest.addActionListener(clickBtn(botonOest));
        botonSur.addActionListener(clickBtn(botonSur));
        botonNorth.setEnabled(false);
        botonEast.setEnabled(false);
        botonOest.setEnabled(false);
        botonSur.setEnabled(false);

        f.add(menuPanel);
        f.setSize(1000, 1000);
        f.setLayout(null);
        f.setVisible(true);
    }

    public ActionListener clickBtn(Button btn) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Door door : dungeon.getRoom().get(pos).getDoors()) {
                    if (door.getName().equals(btn.getLabel())) {
                        List<Room> room = new ArrayList<>();
                        room.addAll(dungeon.getRoom().stream().filter(dun -> dun.getId().equals(door.getDest())).toList());
                        int n = dungeon.getRoom().indexOf(room.get(0));
                        List<String> dests = new ArrayList<>();
                        for (Door doorss : dungeon.getRoom().get(n).getDoors()) {
                            dests.add(doorss.getDest());
                        }
                        if (dests.contains(dungeon.getRoom().get(pos).getId())) {
                            pos = n;
                            movesText.addLogMessage(" Has ido al " + door.getName() + "\n ");
                            lineas++;
                            cargarHabitacion();

                        } else {
                            movesText.addLogMessage("Esa habitación es extraña, no tiene puerta de vuelta asi que será mejor que no entres" + "\n ");

                        }
                    }
                }

            }
        };
    }


    public static void main(String args[]) {

        new DungeonUI();
    }

    public void cargarHabitacion() {
        roomInfoText.setText(dungeon.getRoom().get(pos).description);

        Set<String> availableDoors = new HashSet<>();

        for (Door door : dungeon.getRoom().get(pos).getDoors()) {
            availableDoors.add(door.getName());

        }

        botonNorth.setEnabled(availableDoors.contains("Norte"));
        botonEast.setEnabled(availableDoors.contains("Este"));
        botonSur.setEnabled(availableDoors.contains("Sur"));
        botonOest.setEnabled(availableDoors.contains("Oeste"));

    }


    private static Dungeon readFile() {
        Dungeon dungeon = new Dungeon();
        MLoad load = new MLoad();
        load.loadXMLFile();

        return load.getDungeon();

    }

    private static JTree createJTreeFromDungeon(Dungeon dungeon) {

        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Dungeon");


        List<Room> rooms = dungeon.getRoom();


        for (Room room : rooms) {

            DefaultMutableTreeNode roomNode = new DefaultMutableTreeNode("Room: " + room.id);

            List<Door> doors = room.getDoors();
            roomNode.add(new DefaultMutableTreeNode(room.description));

            for (Door door : doors) {
                DefaultMutableTreeNode doorNode = new DefaultMutableTreeNode("Door: " + door.getName() + " ->" + door.getDest());
                roomNode.add(doorNode);
            }


            rootNode.add(roomNode);
        }

        JTree jTree = new JTree(rootNode);

        jTree.expandRow(0);

        return jTree;
    }

}






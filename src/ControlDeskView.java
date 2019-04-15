/* ControlDeskView.java
 *
 *  Version:
 *			$Id$
 *
 *  Revisions:
 * 		$Log$
 *
 */

/**
 * Class for representation of the control desk
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import java.util.*;

public class ControlDeskView implements ActionListener, ControlDeskObserver {

    private JButton addParty;
    private JButton finished;
    private JButton assign;
    private JFrame win;
    private JList<Object> partyList;

    /** The maximum  number of members in a party */
    private int maxMembers;

    private ControlDesk controlDesk;

    /**
     * Displays a GUI representation of the ControlDesk
     *
     */

    public ControlDeskView(ControlDesk controlDesk, int maxMembers) {

        this.controlDesk = controlDesk;
        this.maxMembers = maxMembers;
        int numLanes = controlDesk.getNumLanes();

        win = new JFrame("Control Desk");
        win.getContentPane().setLayout(new BorderLayout());
        ((JPanel) win.getContentPane()).setOpaque(false);

        JPanel colPanel = new JPanel();
        colPanel.setLayout(new BorderLayout());

        // Controls Panel
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new GridLayout(3, 1));
        controlsPanel.setBorder(new TitledBorder("Controls"));

        addParty = new JButton("Add Party");
        JPanel addPartyPanel = new JPanel();
        addPartyPanel.setLayout(new FlowLayout());
        addParty.addActionListener(this);
        addPartyPanel.add(addParty);
        controlsPanel.add(addPartyPanel);

        assign = new JButton("Assign Lanes");
        JPanel assignPanel = new JPanel();
        assignPanel.setLayout(new FlowLayout());
        assign.addActionListener(this);
        assignPanel.add(assign);
//		controlsPanel.add(assignPanel);

        finished = new JButton("Finished");
        JPanel finishedPanel = new JPanel();
        finishedPanel.setLayout(new FlowLayout());
        finished.addActionListener(this);
        finishedPanel.add(finished);
        controlsPanel.add(finishedPanel);

        // Lane Status Panel
        JPanel laneStatusPanel = new JPanel();
        laneStatusPanel.setLayout(new GridLayout(numLanes, 1));
        laneStatusPanel.setBorder(new TitledBorder("Lane Status"));

        HashSet<Lane> lanes = controlDesk.getLanes();

        int laneCount = 0;

        for (Lane lane: lanes) {
            LaneStatusView laneStat = new LaneStatusView(lane, (laneCount + 1));
            lane.subscribe(laneStat);
            lane.getPinsetter().subscribe(laneStat);
            JPanel lanePanel = laneStat.showLane();
            lanePanel.setBorder(new TitledBorder("Lane" + ++laneCount));
            laneStatusPanel.add(lanePanel);
        }

        // Party Queue Panel
        JPanel partyPanel = new JPanel();
        partyPanel.setLayout(new FlowLayout());
        partyPanel.setBorder(new TitledBorder("Party Queue"));

        ArrayList<String> empty = new ArrayList<>();
        empty.add("(Empty)");

        partyList = new JList<>(empty.toArray());
        //TODO CHECK THIS
        partyList.setFixedCellWidth(120);
        partyList.setVisibleRowCount(10);
        JScrollPane partyPane = new JScrollPane(partyList);
        partyPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        partyPanel.add(partyPane);
        //		partyPanel.add(partyList);

        // Clean up main panel
        colPanel.add(controlsPanel, "East");
        colPanel.add(laneStatusPanel, "Center");
        colPanel.add(partyPanel, "West");

        win.getContentPane().add("Center", colPanel);

        win.pack();

        /* Close program when this window closes */
        win.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // Center Window on Screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        win.setLocation(
                ((screenSize.width) / 2) - ((win.getSize().width) / 2),
                ((screenSize.height) / 2) - ((win.getSize().height) / 2));
        win.setVisible(true);

    }

    /**
     * Handler for actionEvents
     *
     * @param e    the ActionEvent that triggered the handler
     *
     */

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(addParty)) {
            AddPartyView addPartyWin = new AddPartyView(this, maxMembers);
        }
        if (e.getSource().equals(assign)) {
            controlDesk.assignLane();
        }
        if (e.getSource().equals(finished)) {
            win.setVisible(false);
            System.exit(0);
        }
    }

    /**
     * Receive a new party from andPartyView.
     *
     * @param addPartyView    the AddPartyView that is providing a new party
     *
     */

    public void updateAddParty(AddPartyView addPartyView) {

        controlDesk.addPartyQueue(addPartyView.getParty());
    }

    /**
     * Receive a broadcast from a ControlDesk
     *
     * @param ce    the ControlDeskEvent that triggered the handler
     *
     */

    public void receiveControlDeskEvent(ControlDeskEvent ce) {
        partyList.setListData(((Vector<Object>) ce.getPartyQueue()));
    }
}

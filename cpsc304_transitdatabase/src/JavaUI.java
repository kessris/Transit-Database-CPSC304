/**
 * Created by quejessica on 2018-03-27.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class JavaUI {
    private JFrame mainFrame;
    private JFrame userFrame;

    private JPanel menu;
    private JPanel content;
    private JPanel execute;

    private final String VEHICLEROUTE = "Route of Vehicle";
    private final String ALLZONEROUTES = "All Zone Routes";
    private final String ROUTEADDRESSES = "Route Addresses";
    private final String ROUTESTOPS = "Route Stops";
    private final String STOPS = "Stops";


    public JavaUI(){
        createMainFrame();
    }

    public void createMainFrame(){
        mainFrame = new JFrame("Transit Application");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel(new GridBagLayout());

        //Create Buttons
        JButton userButton = new JButton("User Menu");
        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createUserFrame();
            }
        });

        JButton adminButton = new JButton("Admin Menu");
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAdminFrame();
            }
        });

        mainPanel.add(userButton);
        mainPanel.add(adminButton);
        mainFrame.add(mainPanel);
        mainFrame.setSize(300,200);
        mainFrame.setVisible(true);
    }

    //TODO: Admin frame
    public void createAdminFrame(){
        new JavaUIAdmin();
    }

    public void createUserFrame(){
        //Create userFrame
        userFrame = new JFrame("Transit Application");

        menu = new JPanel();
        menu.setLayout(new GridLayout(3, 3));
        menu.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Menu"),
                        BorderFactory.createEmptyBorder(1, 10, 1, 10)));
        content = new JPanel();
        execute = new JPanel();
        execute.setLayout(new GridLayout(2, 1));

        menu.add(createMenu(VEHICLEROUTE));
        menu.add(createMenu(ALLZONEROUTES));
        menu.add(createMenu(ROUTEADDRESSES));
        menu.add(createMenu(ROUTESTOPS));
        menu.add(createMenu(STOPS));

        userFrame.add(menu, BorderLayout.NORTH);
        userFrame.add(content, BorderLayout.CENTER);

        //Put everything together.
        //userFrame.setMinimumSize(new Dimension(650,100));
        //userFrame.pack();
        userFrame.setSize(700, 400);
        userFrame.setVisible(true);
    }

    public void selectMenu(String menu) {
        clear();
        content.setLayout(new GridBagLayout());
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        switch (menu) {
            case VEHICLEROUTE:
                VehicleInRoutePanel();
                break;
            case ALLZONEROUTES:
                AllZoneRoutesPanel();
                break;
            case ROUTEADDRESSES:
                routeAddresesPanel();
                break;
            case ROUTESTOPS:
                routeStopsPanel();
                break;
            case STOPS:
                stopsPanel();
                break;
        }

        //userFrame.pack();
    }

    private JButton createMenu(String menu) {
        JButton button = new JButton(menu);
        button.addActionListener(e -> selectMenu(button.getText()));
        return button;
    }

    public void VehicleInRoutePanel(){
        Box box = Box.createVerticalBox();

        //QUERY: Route of vehicle
        JPanel vehiclePanel = new JPanel(new GridBagLayout());
        JLabel vehicleInRouteInfo = new JLabel("Find a vehicle's route. Enter vehicle number: ");
        JLabel vehicleInRouteResult = new JLabel("");

        JTextField vehicleField = new JTextField(10);
        vehicleField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField source = (JTextField)e.getSource();
                int vehicleNumber = 0;
                try {
                    vehicleNumber = Integer.parseInt(source.getText());
                    String route = JavaConnection.routeOfVehicle(vehicleNumber);
                    vehicleInRouteResult.setText(route);
                    //userFrame.pack();
                } catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Error: Enter a valid vehicle number");
                }
            }
        });

        JComponent[] components = {vehicleInRouteInfo, vehicleField, vehicleInRouteResult};
        addComponentsToPanel(components, vehiclePanel, "Route of vehicle");
        box.add(vehiclePanel);
        content.add(box);
    }

    public void AllZoneRoutesPanel(){
        //QUERY: Division
        Box box = Box.createVerticalBox();

        JPanel divisionPanel = new JPanel(new GridBagLayout());
        JLabel divisionInfo = new JLabel("Find routes that go through every single zone");
        JLabel divisionResult = new JLabel("");

        JButton divisionButton = new JButton("Find");
        divisionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = JavaConnection.routesAllZones();
                divisionResult.setText(result);
                //userFrame.pack();
            }
        });

        JComponent[] divisionComponents = {divisionInfo, divisionButton, divisionResult};
        addComponentsToPanel(divisionComponents, divisionPanel, "All Zone Routes");
        box.add(divisionPanel);
        content.add(box);

    }

    public void routeAddresesPanel(){
        Box box = Box.createVerticalBox();

        //QUERY: Addresses Route passes by
        JPanel routePanel = new JPanel(new GridBagLayout());
        JLabel routeInfo = new JLabel("List of routes and addresses they pass by");

        JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(false);
        JScrollPane editorScrollPane = new JScrollPane(editorPane);
        editorScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        editorScrollPane.setPreferredSize(new Dimension(600, 100));
        editorScrollPane.setMinimumSize(new Dimension(250, 100));

        JButton routeButton = new JButton("View");
        routeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ArrayList<String> addresses = JavaConnection.getAddressesRoutesPassesBy();
                String result = "";
                Collections.sort(addresses);
                for (String address : addresses){
                    result = result.concat(address+"\n");
                }
                editorPane.setText(result);
                //userFrame.pack();
            }
        });

        JComponent[] routeComponents = {routeInfo, routeButton};
        addComponentsToPanel(routeComponents, routePanel, "Route Information");
        box.add(routePanel);
        box.add(editorScrollPane);
        content.add(box);
    }

    public void routeStopsPanel(){
        Box box = Box.createVerticalBox();

        //QUERY: Route Has passes by
        JPanel routeHasPannel = new JPanel(new GridBagLayout());
        JLabel routeHasInfo = new JLabel("List route and stop connections");

        //Create an editor pane.
        JEditorPane editorPane2 = new JEditorPane();
        editorPane2.setEditable(false);
        JScrollPane editorScrollPane2 = new JScrollPane(editorPane2);
        editorScrollPane2.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        editorScrollPane2.setPreferredSize(new Dimension(550, 100));
        editorScrollPane2.setMinimumSize(new Dimension(250, 10));

        JButton routeHasButton = new JButton("View");
        routeHasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ArrayList<String> list = JavaConnection.getRouteHasStop();
                String result = "";
                Collections.sort(list);
                for (String inList : list){
                    result = result.concat(inList+"\n");
                }
                editorPane2.setText(result);
                //userFrame.pack();
            }
        });

        JComponent[] routeHasComponents = {routeHasInfo, routeHasButton};
        addComponentsToPanel(routeHasComponents, routeHasPannel, "Route relation to Stop Information");
        box.add(routeHasPannel);
        box.add(editorScrollPane2);
        content.add(box);
    }

    public void stopsPanel(){
        Box box = Box.createVerticalBox();

        //QUERY: Route Has passes by
        JPanel stopsPanel = new JPanel(new GridBagLayout());
        JLabel stopsInfo = new JLabel("List all stops");

        //Create an editor pane.
        JEditorPane editorPane2 = new JEditorPane();
        editorPane2.setEditable(false);
        JScrollPane editorScrollPane2 = new JScrollPane(editorPane2);
        editorScrollPane2.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        editorScrollPane2.setPreferredSize(new Dimension(550, 100));
        editorScrollPane2.setMinimumSize(new Dimension(250, 10));

        JButton stopButton = new JButton("View");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ArrayList<String> list = JavaConnection.returnAllStops();
                String result = "";
                Collections.sort(list);
                for (String inList : list){
                    result = result.concat(inList+"\n");
                }
                editorPane2.setText(result);
                //userFrame.pack();
            }
        });

        JComponent[] stopComponents = {stopsInfo, stopButton};
        addComponentsToPanel(stopComponents, stopsPanel, "Stop Information");
        box.add(stopsPanel);
        box.add(editorScrollPane2);
        content.add(box);
    }

    public void addComponentsToPanel(JComponent[] components, JPanel panel, String description){
        //Grid constraints
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 0.1;

        c.gridx = 0;
        c.gridy = 0;
        int i = 0;
        for (JComponent component : components) {
            c.gridy = i;
            panel.add(component,c);
            i++;
        }

//        panel.setBorder(
//                BorderFactory.createCompoundBorder(
//                        BorderFactory.createTitledBorder(description),
//                        BorderFactory.createEmptyBorder(1,10,1,10)));
    }

    private void clear() {
        content.removeAll();
        content.repaint();
        content.revalidate();

        execute.removeAll();
        execute.repaint();
        execute.revalidate();
    }

}

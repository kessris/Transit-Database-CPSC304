import javax.swing.*;
import java.awt.*;

/**
 * Created by mbp on 2018-03-28.
 */
public class JavaUIAdmin {

    

	private JFrame mainFrame;

    private JPanel menu;
    private JPanel content;
    private JPanel execute;

    private final String YONG = "Yongest or Oldest";
    private final String RICH = "Richest In Age Range";
    private final String DELETE_STOP = "Delete Stop";
    private final String DELETE_STOP_ROUTE = "Delete Route";
    private final String ADD_BALANCE = "Add Balance";
    private final String INSERT_STOP = "Insert Stop";
    private final String INSERT_ADDRESS = "Insert Address";
    private final String INSERT_ROUTE_HAS_STOP = "Insert Route Has Stop";

    public JavaUIAdmin() {
        mainFrame = new JFrame("Admin");
        mainFrame.setLayout(new BorderLayout());

        menu = new JPanel();
        menu.setLayout(new GridLayout(3, 3));
        menu.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Menu"),
                        BorderFactory.createEmptyBorder(1, 10, 1, 10)));
        content = new JPanel();
        execute = new JPanel();
        execute.setLayout(new GridLayout(2, 1));

        menu.add(createMenu(YONG));
        menu.add(createMenu(RICH));
        menu.add(createMenu(DELETE_STOP));
        menu.add(createMenu(DELETE_STOP_ROUTE));
        menu.add(createMenu(ADD_BALANCE));
        menu.add(createMenu(INSERT_STOP));
        menu.add(createMenu(INSERT_ADDRESS));
        menu.add(createMenu(INSERT_ROUTE_HAS_STOP));

        mainFrame.add(menu, BorderLayout.NORTH);
        mainFrame.add(content, BorderLayout.CENTER);
        mainFrame.add(execute, BorderLayout.SOUTH);

        mainFrame.pack();
        mainFrame.setSize(500, 400);
        mainFrame.setVisible(true);
    }

    public void selectMenu(String menu) {
        clear();
        content.setLayout(new GridBagLayout());
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        switch (menu) {
            case YONG:
                menu1();
                break;
            case RICH:
                menu2();
                break;
            case DELETE_STOP:
                menu3();
                break;
            case DELETE_STOP_ROUTE:
                menu4();
                break;
            case ADD_BALANCE:
                menu5();
                break;
            case INSERT_STOP:
                menu6();
                break;
            case INSERT_ADDRESS:
                menu7();
                break;
            case INSERT_ROUTE_HAS_STOP:
            	menu8();
            	break;
        }
    }

    private void menu1() {
        JLabel labelRich = new JLabel("Maximum or Minimum");
        labelRich.setVisible(true);
        content.add(labelRich);

        String[] choices = {"MAX", "MIN"};
        JComboBox<String> box = new JComboBox<>(choices);
        content.add(box);

        addDescription("Find Maximum or Minimum Age of Users");
        JButton button = createExecuteButton("OK");
        button.addActionListener(e -> {
            String choice = (String) box.getSelectedItem();
            String result = JavaConnection.youngestOldest(choice);
            JOptionPane.showMessageDialog(mainFrame, result);
        });
    }

    private void menu2() {
        JPanel agePanel = new JPanel();
        agePanel.setLayout(new GridLayout(3, 2));

        JTextField ageText1 = createTextField("Age Category 1", agePanel);
        JTextField ageText2 = createTextField("Age Category 2", agePanel);
        JLabel labelRich = new JLabel("Maximum or Minimum");
        labelRich.setVisible(true);
        agePanel.add(labelRich);

        String[] choices = {"MAX", "MIN"};
        JComboBox<String> box = new JComboBox<>(choices);
        agePanel.add(box);

        content.add(agePanel);

        addDescription("Group Age into Three category. Find Max/Min Farecard Average Group");
        JButton button = createExecuteButton("OK");
        button.addActionListener(e -> {
            try {
                Integer age1 = Integer.parseInt(ageText1.getText());
                Integer age2 = Integer.parseInt(ageText2.getText());
                String choice = (String) box.getSelectedItem();
                String result;
                if (age1 <= age2) {
                    result = JavaConnection.richestInAgeRanges(age1, age2, choice);
                } else {
                    result = JavaConnection.richestInAgeRanges(age2, age1, choice);
                }
                JOptionPane.showMessageDialog(mainFrame, result);
            } catch (Exception err) {
                JOptionPane.showMessageDialog(mainFrame, "Invalid Age Input");
            }
        });
    }

    private void menu3() {
        JTextField deleteText = createTextField("Stop ID", content);

        addDescription("Enter the Stop ID to Delete Stop");
        JButton button = createExecuteButton("DELETE");
        button.addActionListener(e -> {
            try {
                Integer id = Integer.parseInt(deleteText.getText());
                String result = JavaConnection.deleteStop(id);
                if (result.startsWith("Successfully")) {
                    JOptionPane.showMessageDialog(mainFrame, result);
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Stop ID Does Not Exist");
                }
            } catch (Exception err) {
                JOptionPane.showMessageDialog(mainFrame, "Invalid Stop ID");
            }
        });
    }

    private void menu4() {
        JPanel deletePanel = new JPanel();
        deletePanel.setLayout(new GridLayout(2, 2));

        JTextField stopText = createTextField("Stop ID", deletePanel);
        JTextField routeText = createTextField("Route Number", deletePanel);
        content.add(deletePanel);

        addDescription("Enter Stop Number and Route Number");
        JButton button = createExecuteButton("DELETE");
        button.addActionListener(e -> {
            try {
                Integer stopID = Integer.parseInt(stopText.getText());
                Integer routeID = Integer.parseInt(routeText.getText());
                    String result = JavaConnection.deleteRouteHasStop(JavaConnection.connection,routeID,stopID);
                    if (result.startsWith("Successfully")){
                        JOptionPane.showMessageDialog(mainFrame, result);
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "Stop ID or RouteID does not Exist");
                    }
            } catch (Exception err) {
                JOptionPane.showMessageDialog(mainFrame, "Invalid StopID or RouteNum Input");
            }
        });
    }

    private void menu5() {
        JPanel addBalancePanel = new JPanel();
        addBalancePanel.setLayout(new GridLayout(2, 2));

        JTextField cardNumText = createTextField("FareCard ID", addBalancePanel);
        JTextField amountText = createTextField("Amount To Add", addBalancePanel);
        content.add(addBalancePanel);

        addDescription("Add Balance to a Card");
        JButton button = createExecuteButton("ADD");
        button.addActionListener(e -> {
            try {
                Integer cardNumber = Integer.parseInt(cardNumText.getText());
                Float amount = Float.parseFloat(amountText.getText());
                String result = JavaConnection.addBalance(cardNumber, amount);
                JOptionPane.showMessageDialog(mainFrame, result);
            } catch (Exception err) {
                JOptionPane.showMessageDialog(mainFrame, "Invalid CardNumber or Amount Input");
            }
        });
    }

    private void menu6() {
        JPanel insertStop = new JPanel();
        insertStop.setLayout(new GridLayout(5, 2));

        JTextField stopIDText = createTextField("Stop ID", insertStop);
        JTextField streetNameText = createTextField("Street Name", insertStop);
        JTextField streetNumberText = createTextField("Street Number", insertStop);
        JTextField cityText = createTextField("City", insertStop);
        JTextField zoneText = createTextField("Zone", insertStop);
        content.add(insertStop);

        addDescription("Insert Stop Information");
        JButton button = createExecuteButton("INSERT");
        button.addActionListener(e -> {
            try {
                if (stopIDText.getText().equals("")) {
                    JOptionPane.showMessageDialog(mainFrame, "Stop ID Needed");
                    return;
                }
                Integer stopID = Integer.parseInt(stopIDText.getText());
                Integer streetNumber = Integer.parseInt(streetNumberText.getText());
                Integer zone = Integer.parseInt(zoneText.getText());
                String streetName = streetNameText.getText();
                String city = cityText.getText();
                String result = JavaConnection.insertStop(stopID, city, streetName, streetNumber, zone);
                if (result.startsWith("Successfully")) {
                    JOptionPane.showMessageDialog(mainFrame, result);
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Stop ID already Exists");
                }
            } catch (Exception err) {
                JOptionPane.showMessageDialog(mainFrame, "Invalid Input");
            }
        });
    }

    private void menu7() {
        JPanel insertAddress = new JPanel();
        insertAddress.setLayout(new GridLayout(4, 2));

        JTextField streetNameText = createTextField("Street Name", insertAddress);
        JTextField streetNumberText = createTextField("Street Number", insertAddress);
        JTextField cityText = createTextField("City", insertAddress);
        JTextField zoneText = createTextField("Zone", insertAddress);
        content.add(insertAddress);

        addDescription("Insert Address Inforamtion");
        JButton button = createExecuteButton("INSERT");
        button.addActionListener(e -> {
            try {
                if (cityText.getText().equals("") ||
                        streetNameText.getText().equals("") ||
                        streetNumberText.getText().equals("")) {
                    JOptionPane.showMessageDialog(mainFrame, "City and Street Information must be Entered");
                    return;
                }
                Integer streetNumber = Integer.parseInt(streetNumberText.getText());
                Integer zone = Integer.parseInt(zoneText.getText());
                String streetName = streetNameText.getText();
                String city = cityText.getText();
                String result = JavaConnection.insertAddress(city, streetName, streetNumber, zone);
                if (result.startsWith("Successfully")) {
                    JOptionPane.showMessageDialog(mainFrame, result);
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Address already exists or input is too long");
                }
            } catch (Exception err) {
                JOptionPane.showMessageDialog(mainFrame, "Invalid Input");
            }
        });
    }
    
    private void menu8() {
        JPanel insertRouteHasStop = new JPanel();
        insertRouteHasStop.setLayout(new GridLayout(3, 2));

        JTextField routeNumber = createTextField("Route Number", insertRouteHasStop);
        JTextField stopNumber = createTextField("Stop Number", insertRouteHasStop);
        JTextField orderNumber = createTextField("Order Number", insertRouteHasStop);
        content.add(insertRouteHasStop);

        addDescription("Insert Address Inforamtion");
        JButton button = createExecuteButton("INSERT");
        button.addActionListener(e -> {
            try {
                if (routeNumber.getText().equals("") ||
                		stopNumber.getText().equals("") ||
                		orderNumber.getText().equals("")) {
                    JOptionPane.showMessageDialog(mainFrame, "Route Number, Stop Number, and Order Number Information must be Entered");
                    return;
                }
                Integer routeNumberInput = Integer.parseInt(routeNumber.getText());
                Integer stopNumberInput = Integer.parseInt(stopNumber.getText());
                Integer orderNumberInput = Integer.parseInt(orderNumber.getText());
                String result = JavaConnection.insertRouteHasStop(routeNumberInput, stopNumberInput, orderNumberInput);
                if (result.startsWith("Successfully")) {
                    JOptionPane.showMessageDialog(mainFrame, result);
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "ERROR: Invalid input");
                }
            } catch (Exception err) {
                JOptionPane.showMessageDialog(mainFrame, "Invalid Input");
            }
        });
    }

    private void addDescription(String des) {
        JLabel label = new JLabel(des, SwingConstants.CENTER);
        execute.add(label);
    }

    private JButton createMenu(String menu) {
        JButton button = new JButton(menu);
        button.addActionListener(e -> selectMenu(button.getText()));
        return button;
    }

    private JTextField createTextField(String name, JPanel panel) {
        JLabel label = new JLabel(name);
        label.setVisible(true);
        panel.add(label);

        JTextField stopText = new JTextField(10);
        panel.add(stopText);
        return stopText;
    }

    private JButton createExecuteButton(String name) {
        JButton button = new JButton(name);
        execute.add(button);
        return button;
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

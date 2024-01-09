import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main {
    private static JFrame frame;
    private static List<DeluxeRoom> rooms;
    private static JComboBox<String> roomComboBox;
    private static JTextField checkInField;
    private static JTextField checkOutField;
    private static JComboBox<String> paymentMethodComboBox;
    private static JTextArea outputArea;
    private static JTable roomTable;

    public static void main(String[] args) {
        // Inisialisasi daftar kamar
        rooms = new ArrayList<>();
        rooms.add(new DeluxeRoom(101, "Deluxe", 150000, 2, "WiFi, TV, AC"));
        rooms.add(new DeluxeRoom(102, "Deluxe", 160000, 2, "WiFi, TV, AC, Minibar"));
        rooms.add(new DeluxeRoom(103, "Deluxe Suite", 200000, 4, "WiFi, TV, AC, Kitchenette"));
        rooms.add(new DeluxeRoom(104, "Standard", 100000, 2, "WiFi, TV, AC"));
        rooms.add(new DeluxeRoom(105, "Standard", 110000, 2, "WiFi, TV, AC"));
        rooms.add(new DeluxeRoom(106, "Suite", 180000, 3, "WiFi, TV, AC, Kitchenette"));
        rooms.add(new DeluxeRoom(107, "Suite", 190000, 3, "WiFi, TV, AC, Kitchenette"));
        rooms.add(new DeluxeRoom(108, "Superior", 120000, 2, "WiFi, TV, AC, Minibar"));
        rooms.add(new DeluxeRoom(109, "Superior", 130000, 2, "WiFi, TV, AC, Minibar"));
        rooms.add(new DeluxeRoom(110, "Standard", 100000, 2, "WiFi, TV, AC"));

        // Create the main frame
        frame = new JFrame("Hotel Reservation System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);

        // Set warna background frame
        frame.getContentPane().setBackground(new Color(192, 192, 192));  // Silver

        // Create components
        roomTable = createRoomTable();
        JScrollPane tableScrollPane = new JScrollPane(roomTable);

        roomComboBox = new JComboBox<>();
        for (DeluxeRoom room : rooms) {
            roomComboBox.addItem(Integer.toString(room.getRoomNumber()));
        }

        checkInField = new JTextField(10);
        checkOutField = new JTextField(10);

        paymentMethodComboBox = new JComboBox<>();
        paymentMethodComboBox.addItem("Cash");
        paymentMethodComboBox.addItem("Kartu Kredit");
        paymentMethodComboBox.addItem("Kartu Debit");
        paymentMethodComboBox.addItem("Voucher");

        JButton reserveButton = new JButton("Reserve");
        reserveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reserveRoom();
            }
        });

        outputArea = new JTextArea(15, 60);
        outputArea.setEditable(false);

        // Set warna background panel
        JPanel panel = new JPanel();
        panel.setBackground(new Color(192, 192, 192));  // Silver
        panel.add(new JLabel("Select Room:"));
        panel.add(roomComboBox);
        panel.add(new JLabel("Check-in Date (dd/mm/yyyy):"));
        panel.add(checkInField);
        panel.add(new JLabel("Check-out Date (dd/mm/yyyy):"));
        panel.add(checkOutField);
        panel.add(new JLabel("Payment Method:"));
        panel.add(paymentMethodComboBox);
        panel.add(reserveButton);

        JScrollPane scrollPane = new JScrollPane(outputArea);
        panel.add(scrollPane);

        // Add components to the frame
        frame.add(tableScrollPane);
        frame.add(panel);

        // Set layout manager for the frame
        GroupLayout layout = new GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);

        // Adjust layout positions
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(tableScrollPane)
                .addComponent(panel)
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(tableScrollPane)
                .addComponent(panel)
        );

        // Pack the frame to ensure all components are laid out
        frame.pack();

        // Set the frame visible
        frame.setVisible(true);
    }

    private static void reserveRoom() {
        int roomNumber = Integer.parseInt((String) roomComboBox.getSelectedItem());
        DeluxeRoom selectedRoom = findRoomByNumber(rooms, roomNumber);

        if (selectedRoom != null) {
            String checkInDate = checkInField.getText();
            String checkOutDate = checkOutField.getText();
            String paymentMethod = (String) paymentMethodComboBox.getSelectedItem();

            int totalDays = calculateTotalDays(checkInDate);
            double totalPrice = totalDays * selectedRoom.getPricePerNight();

            outputArea.append("Reservasi Berhasil!\n");
            outputArea.append("Nomor Kamar: " + roomNumber + "\n");
            outputArea.append("Tanggal Masuk: " + checkInDate + "\n");
            outputArea.append("Tanggal Keluar: " + checkOutDate + "\n");
            outputArea.append("Total Hari Dipesan: " + totalDays + " hari\n");
            outputArea.append("Metode Pembayaran: " + paymentMethod + "\n");
            outputArea.append("Room Number: " + selectedRoom.getRoomNumber() + "\n");
            outputArea.append("Room Type: " + selectedRoom.getRoomType() + "\n");
            outputArea.append("Facilities: " + selectedRoom.getFacilities() + "\n");
            outputArea.append("Price per Night: Rp " + formatCurrency(selectedRoom.getPricePerNight()) + "\n");
            outputArea.append("Capacity: " + selectedRoom.getCapacity() + " person(s)\n");
            outputArea.append("Total Harga: Rp " + formatCurrency(totalPrice) + "\n");
            outputArea.append("\n");

            // Update room table
            updateRoomTable();

        } else {
            outputArea.append("Nomor kamar tidak valid.\n");
        }
    }

    private static DeluxeRoom findRoomByNumber(List<DeluxeRoom> rooms, int roomNumber) {
        for (DeluxeRoom room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    private static int calculateTotalDays(String checkInDate) {
        String[] parts = checkInDate.split("/");
        return Integer.parseInt(parts[0]);
    }

    private static JTable createRoomTable() {
        String[] columnNames = {"Room Number", "Room Type", "Facilities", "Price per Night", "Capacity"};
        DefaultTableModel model = new DefaultTableModel(null, columnNames);

        for (DeluxeRoom room : rooms) {
            model.addRow(new Object[]{room.getRoomNumber(), room.getRoomType(), room.getFacilities(),
                    formatCurrency(room.getPricePerNight()), room.getCapacity()});
        }

        return new JTable(model);
    }

    private static void updateRoomTable() {
        DefaultTableModel model = (DefaultTableModel) roomTable.getModel();
        model.setRowCount(0);

        for (DeluxeRoom room : rooms) {
            model.addRow(new Object[]{room.getRoomNumber(), room.getRoomType(), room.getFacilities(),
                    formatCurrency(room.getPricePerNight()), room.getCapacity()});
        }
    }

    private static String formatCurrency(double amount) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return currencyFormat.format(amount);
    }
}

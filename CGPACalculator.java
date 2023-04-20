import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.table.DefaultTableModel;

public class CGPACalculator extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JTextField courseCodeField, creditField;
    private JComboBox<String> gradeCombo;
    private JTable courseTable;
    private double totalGrade = 0;
    private double totalCredit = 0;
    private JLabel cgpaLabel;

    public CGPACalculator() {
        super("CGPA Calculator");

        // Set the look and feel to Nimbus
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, use the default look and feel
            UIManager.put("OptionPane.background", Color.WHITE);
            UIManager.put("Panel.background", Color.WHITE);
        }

        // Create and customize the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        mainPanel.setBounds(10, 10, 580, 530);

        // Create and customize the header label
        JLabel headerLabel = new JLabel("CGPA Calculator");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setBounds(0, 0, 580, 50);
        headerLabel.setOpaque(true);
        headerLabel.setBackground(Color.GRAY);

        // Create and customize the course code label
        JLabel courseCodeLabel = new JLabel("Course Code:");
        courseCodeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        courseCodeLabel.setBounds(20, 70, 100, 30);

        // Create and customize the course code text field
        courseCodeField = new JTextField();
        courseCodeField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        courseCodeField.setBounds(130, 70, 150, 30);

        // Create and customize the credit label
        JLabel creditLabel = new JLabel("Credit:");
        creditLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        creditLabel.setBounds(20, 110, 100, 30);

        // Create and customize the credit text field
        creditField = new JTextField();
        creditField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        creditField.setBounds(130, 110, 150, 30);

        // Create and customize the grade label
        JLabel gradeLabel = new JLabel("Grade:");
        gradeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gradeLabel.setBounds(20, 150, 100, 30);

        // Create and customize the grade combo box
        String[] grades = {"A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "F"};
        gradeCombo = new JComboBox<>(grades);
        gradeCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gradeCombo.setBounds(130, 150, 150, 30);

        // Create and customize the add course button
        JButton addCourseButton = new JButton("Add Course");
        addCourseButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addCourseButton.setBounds(20, 200, 260, 30);
        addCourseButton.addActionListener(this);

        // Create and customize the course table
        courseTable = new JTable(new DefaultTableModel(new Object[]{"Course Code", "Credit ", "Grade"}, 0));
        courseTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        courseTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        courseTable.getTableHeader().setForeground(Color.WHITE);
        courseTable.getTableHeader().setBackground(Color.GRAY);
        courseTable.getTableHeader().setOpaque(true);
        courseTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(courseTable);
        scrollPane.setBounds(20, 240, 260, 230);

        // Create and customize the calculate CGPA button
        JButton calculateCGPAButton = new JButton("Calculate CGPA");
        calculateCGPAButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        calculateCGPAButton.setBounds(20, 480, 260, 30);
        calculateCGPAButton.addActionListener(this);

        // Create and customize the CGPA label
        cgpaLabel = new JLabel("CGPA: ");
        cgpaLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        cgpaLabel.setForeground(Color.GRAY);
        cgpaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        cgpaLabel.setBounds(300, 480, 250, 30);

        // Add all components to the main panel
        mainPanel.add(headerLabel);
        mainPanel.add(courseCodeLabel);
        mainPanel.add(courseCodeField);
        mainPanel.add(creditLabel);
        mainPanel.add(creditField);
        mainPanel.add(gradeLabel);
        mainPanel.add(gradeCombo);
        mainPanel.add(addCourseButton);
        mainPanel.add(scrollPane);
        mainPanel.add(calculateCGPAButton);
        mainPanel.add(cgpaLabel);

        // Set the frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 580);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().add(mainPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add Course")) {
            // Validate the input fields
            if (courseCodeField.getText().equals("") || creditField.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Please enter data in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Add the course to the table
            DefaultTableModel model = (DefaultTableModel) courseTable.getModel();
            model.addRow(new Object[]{courseCodeField.getText(), creditField.getText(), (String) gradeCombo.getSelectedItem()});

            // Clear the input fields
            courseCodeField.setText("");
            creditField.setText("");
        } else if (e.getActionCommand().equals("Calculate CGPA")) {
            // Calculate the CGPA
            double totalGradePoint = 0.0;
            DefaultTableModel model = (DefaultTableModel) courseTable.getModel();
            int rowCount = model.getRowCount();
            for (int i = 0; i < rowCount ; i++) {
                String grade = (String) model.getValueAt(i, 2);
                double credit = Double.parseDouble((String) model.getValueAt(i, 1));
                totalCredit += credit;
                switch (grade) {
                    case "A+":
                        totalGradePoint += (4.00 * credit);
                        break;
                    case "A":
                        totalGradePoint += (3.75 * credit);
                        break;
                    case "A-":
                        totalGradePoint += (3.50 * credit);
                        break;
                    case "B+":
                        totalGradePoint += (3.25 * credit);
                        break;
                    case "B":
                        totalGradePoint += (3.00 * credit);
                        break;
                    case "B-":
                        totalGradePoint += (2.75 * credit);
                        break;
                    case "C+":
                        totalGradePoint += (2.50 * credit);
                        break;
                    case "C":
                        totalGradePoint += (2.25 * credit);
                        break;
                    case "C-":
                        totalGradePoint += (2.00 * credit);
                        break;
                    case "D":
                        totalGradePoint += (1.00 * credit);
                        break;
                    case "F":
                        totalGradePoint += (0.00 * credit);
                        break;
                }
            }
            double cgpa = totalGradePoint / totalCredit;
            cgpaLabel.setText(String.format("CGPA: %.2f", cgpa));
        }
    }
    // clear all input fields
    private void clearFields() {
        courseCodeField.setText("");
        creditField.setText("");
        gradeCombo.setSelectedIndex(0);
    }

    // main method to run the program
    public static void main(String[] args) {
        CGPACalculator app = new CGPACalculator();
        app.setVisible(true);
    }
}

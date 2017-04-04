package view;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class GUI extends JFrame {

    

    private JButton okBtn;

    private HintTextField timesliceField;
    private HintTextField overheadField;



    private JButton addToTableBtn;
    private JButton deleteToTableBtn;

    private JTable table;


    private ButtonGroup bgAlgorithm;
    private JRadioButton rbFCFS;
    private JRadioButton rbSJF;
    private JRadioButton rbNPP;
    private JRadioButton rbSRTF;
    private JRadioButton rbPP;
    private JRadioButton rbRR;

    private JPanel contentPanel;
    private JPanel inputPanel;
    private JPanel tablePanel;
    private JPanel optionPanel;
    private JPanel averagePanel;
    private JLabel waitingTimeLabel;
    private JLabel turnAroundTimeLabel;
    private JPanel outputPanel;



    public GUI(){
        super("CPU Scheduling");

        contentPanel = new JPanel();

        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        setContentPane(contentPanel);

        setLayout(new BorderLayout());

        inputPanel = new JPanel();
        tablePanel = new JPanel();
        optionPanel = new JPanel();

        inputPanel.setLayout(new BorderLayout());

        createTable();
        addToTableBtn = new JButton("Add");
        deleteToTableBtn = new JButton("Delete");


        tablePanel.add(addToTableBtn);
        tablePanel.add(deleteToTableBtn);

        averagePanel = new JPanel();
        averagePanel.setPreferredSize(new Dimension(300, 200));
        waitingTimeLabel = new JLabel("<html><h3>Waiting Time<br>Average: <br></h3></html>");
        turnAroundTimeLabel = new JLabel("<html><h3>Turnaround Time<br>Average: <br></h3></html>");
        outputPanel = new JPanel();
        averagePanel.add(waitingTimeLabel);
        averagePanel.add(turnAroundTimeLabel);
        inputPanel.add(tablePanel, BorderLayout.CENTER);
        inputPanel.add(optionPanel, BorderLayout.WEST);


        bgAlgorithm = new ButtonGroup();
        rbFCFS = new JRadioButton("First Come First Serve");
        rbSJF = new JRadioButton("Shortest Job First");
        rbSRTF =new JRadioButton("Shortest Remainting Time First");
        rbNPP = new JRadioButton("Non Preemptive Priority");
        rbPP = new JRadioButton("Preemptive Priority");
        rbRR = new JRadioButton("Round Robin");
        optionPanel.setLayout(new GridBagLayout());
        add(rbFCFS, 0, 0, GridBagConstraints.LINE_START);
        add(rbSJF, 0, 1, GridBagConstraints.LINE_START);
        add(rbNPP, 0 ,2, GridBagConstraints.LINE_START);
        add(rbSRTF, 0, 3, GridBagConstraints.LINE_START);
        add(rbPP, 0, 4, GridBagConstraints.LINE_START);
        add(rbRR, 0, 5, GridBagConstraints.LINE_START);
        bgAlgorithm.add(rbFCFS);
        bgAlgorithm.add(rbSJF);
        bgAlgorithm.add(rbNPP);
        bgAlgorithm.add(rbPP);
        bgAlgorithm.add(rbRR);
        bgAlgorithm.add(rbSRTF);
        bgAlgorithm.setSelected(rbFCFS.getModel(), true);

        //timesliceLabel = new JLabel("Quantum: ");
        timesliceField = new HintTextField("Quantum");
        overheadField = new HintTextField("Overhead");



        okBtn = new JButton("Ok");
        outputPanel.setLayout(new GridLayout());
        //add(timesliceLabel, 0, 6, GridBagConstraints.LINE_START);
        add(timesliceField, 0, 6, GridBagConstraints.LINE_END);
        add(overheadField, 0, 7, GridBagConstraints.LINE_END);
        add(okBtn, 0, 8, GridBagConstraints.LINE_END);
        outputPanel.setBorder(BorderFactory.createEtchedBorder());
		
        JScrollPane output = new    JScrollPane(outputPanel, 
                                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
                                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        

        add(inputPanel, BorderLayout.NORTH);
        add(output, BorderLayout.CENTER);
        add(averagePanel, BorderLayout.EAST);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void add(JComponent c, int x, int y, int anchor) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx = x;
        gc.gridy = y;
        gc.anchor = anchor;
        gc.insets = new Insets(5, 5, 5, 5);
        optionPanel.add(c, gc);
    }
    private void createTable() {
        table = new JTable(new DefaultTableModel(new Object[]{"Job", "Arrival Time", "Burst Time", "Priority"}, 2));
        tablePanel.add(new JScrollPane(table));
    }

    public JPanel getOutputPanel() {
        return outputPanel;
    }

    public HintTextField getTimesliceField() {
        return timesliceField;
    }

    public JButton getOkBtn() {
        return okBtn;
    }



    public JButton getAddToTableBtn() {
        return addToTableBtn;
    }

    public JButton getDeleteToTableBtn() {
        return deleteToTableBtn;
    }

    public JTable getTable() {
        return table;
    }

    public JRadioButton getRbFCFS() {
        return rbFCFS;
    }

    public JRadioButton getRbSJF() {
        return rbSJF;
    }

    public JRadioButton getRbNPP() {
        return rbNPP;
    }

    public JRadioButton getRbSRTF() {
        return rbSRTF;
    }

    public JRadioButton getRbPP() {
        return rbPP;
    }

    public JRadioButton getRbRR() {
        return rbRR;
    }

    public JLabel getWaitingTimeLabel() {
        return waitingTimeLabel;
    }

    public JLabel getTurnAroundTimeLabel() {
        return turnAroundTimeLabel;
    }
    public HintTextField getOverheadField() {
        return overheadField;
    }
}

package controller;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Job;
import view.GUI;

public class OS implements ActionListener{

    private GUI view;
    private ListSelectionModel listSelectionModel;
    private DefaultTableModel model;
    private ArrayList<Job> jobs;
    private CPUSched cpu;
    private String algorithm = "First Come First Serve";
    

    public OS() {
        view = new GUI();

        model = (DefaultTableModel) view.getTable().getModel();
        cpu = new CPUSched();
        jobs = new ArrayList<>();

        view.getAddToTableBtn().addActionListener(ae-> {

            model.addRow(new Object[]{"", "", "", ""});

        });


        listSelectionModel = view.getTable().getSelectionModel();

        view.getDeleteToTableBtn().setEnabled(false);
        listSelectionModel.addListSelectionListener(le -> {
            if(view.getTable().getSelectedRow() != -1) {
                view.getDeleteToTableBtn().setEnabled(true);
            } else {
                view.getDeleteToTableBtn().setEnabled(false);
            }
        });

        view.getDeleteToTableBtn().addActionListener(ae-> {
            if (view.getTable().getSelectedRow() != -1) {
                model.removeRow(view.getTable().getSelectedRow());
            }
        });

        view.getRbFCFS().addActionListener(this);
        view.getRbSJF().addActionListener(this);
        view.getRbRR().addActionListener(this);
        view.getRbSRTF().addActionListener(this);
        view.getRbPP().addActionListener(this);
        view.getRbNPP().addActionListener(this);
        
        setButtonEnabled(false);
        setRRFieldEnabled(false);

        view.getOkBtn().addActionListener(ae -> {
            getData();
            double quantum = Double.parseDouble(view.getTimesliceField().getText());
            double overhead = Double.parseDouble(view.getOverheadField().getText());
            drawGantt(cpu.methodRR(jobs, quantum, overhead));
        });
        
        view.getTable().getModel().addTableModelListener(te -> {
            boolean ok = true;
            for(int i = 0; i < view.getTable().getRowCount(); i++) {
                for(int j = 0; j < view.getTable().getColumnCount() && ok; j++) {
                    String value = String.valueOf(view.getTable().getModel().getValueAt(i, j));
                    ok = !(value.equals("")) && view.getTable().getModel().getValueAt(i, j) != null;
                }
            }
            
            if(view.getTable().getRowCount() == 0) {
                ok = false;
                view.getOutputPanel().removeAll();
                view.getWaitingTimeLabel().setText("<html><h3>Waiting Time<br>Average: <br>" + String.format("%.2f",0.0) + "</h3></html>");
                view.getTurnAroundTimeLabel().setText("<html><h3>Turnaround Time<br>Average: <br>" +  String.format("%.2f", 0.0) + "</h3></html>");
                view.getOutputPanel().revalidate();
                view.getOutputPanel().repaint();
            }
            setButtonEnabled(ok);
            if(ok) {
                getData();
                methodSelector(algorithm);
            }
                
        });
		
    }
    private void setButtonEnabled(boolean b) {

        view.getRbFCFS().setEnabled(b);
        view.getRbNPP().setEnabled(b);
        view.getRbSJF().setEnabled(b);
        view.getRbPP().setEnabled(b);
        view.getRbRR().setEnabled(b);
        view.getRbSRTF().setEnabled(b);
    }
    private void setRRFieldEnabled(boolean b) {
        //view.getTimesliceLabel().setEnabled(b);
        view.getOverheadField().setEnabled(b);
        view.getTimesliceField().setEnabled(b);
        view.getOkBtn().setEnabled(b);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        getData();
        algorithm = e.getActionCommand();
        setRRFieldEnabled(false);

        methodSelector(algorithm);


    }
    private void methodSelector(String algorithm) {
        switch(algorithm) {

            case "First Come First Serve": {
                drawGantt(cpu.methodFCFS(jobs));
                break;
            }
            case "Shortest Job First": {
                drawGantt(cpu.methodSJF(jobs));
                break;
            }
            case "Shortest Remainting Time First": {
                drawGantt(cpu.methodSRTF(jobs));
                break;
            }
            case "Non Preemptive Priority": {
                drawGantt(cpu.methodNPP(jobs));
                break;
            }
            case "Preemptive Priority": {
                drawGantt(cpu.methodPP(jobs));
                break;

            }
            case "Round Robin": {
                setRRFieldEnabled(true);
                break;
            }
    }
    }
    public void drawGantt(ArrayList<GC> gc){

        view.getOutputPanel().removeAll();
        view.getOutputPanel().add(new JLabel("<html>"+ algorithm +"<br> <br> <br> <p align=right>" + 0.0, SwingConstants.RIGHT));
        for(int i=0; i<gc.size(); i++){
            JPanel ganttChart = new JPanel();
            ganttChart.setLayout(new GridLayout(2, 1));
            JLabel gantt = new JLabel(String.valueOf(gc.get(i).getProcessName()), SwingConstants.CENTER);
            JLabel time = new JLabel(gc.get(i).getEndTime() + "", SwingConstants.RIGHT);
            ganttChart.add(gantt);
            ganttChart.add(time);
            gantt.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            view.getOutputPanel().add(ganttChart);
        }
        GC.compute(jobs, gc);
        view.getWaitingTimeLabel().setText("<html><h3>Waiting Time<br>Average: <br>" + String.format("%.2f",GC.getWaitAve()) + "</h3></html>");
        view.getTurnAroundTimeLabel().setText("<html><h3>Turnaround Time<br>Average: <br>" +  String.format("%.2f",GC.getTurnAve()) + "</h3></html>");
        view.getOutputPanel().revalidate();
        view.getOutputPanel().repaint();
        
    }
    
    private void getData() {
                    
        try {
            jobs.clear();
            for(int i = 0; i < view.getTable().getRowCount(); i++){

                String processName = String.valueOf(view.getTable().getModel().getValueAt(i, 0));
                double arrivalTime = Double.parseDouble(String.valueOf(view.getTable().getModel().getValueAt(i, 1)));
                double burstTime = Double.parseDouble(String.valueOf(view.getTable().getModel().getValueAt(i, 2)));
                int priority = Integer.parseInt(String.valueOf(view.getTable().getModel().getValueAt(i, 3)));
                System.out.println(processName);
                jobs.add(new Job(processName, arrivalTime, burstTime, priority));

            }
            Collections.sort(jobs, Job.SortByArrival);
            cpu.setCPU(jobs.size());
       
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "e.getMessage()");
        }
    }
}

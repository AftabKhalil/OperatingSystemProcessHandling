package madihakhurramassignment;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import static madihakhurramassignment.MadihaKhurramAssignment.lf;
import static madihakhurramassignment.MadihaKhurramAssignment.rf;

/**
 * @author aftab
 */
public class LastFrame extends javax.swing.JFrame {

    public Processor processor;
    DefaultTableModel tableModel;
    DefaultTableCellRenderer renderer;

    public LastFrame() {
        initComponents();
        this.setLocationRelativeTo(null);
        tableModel = (DefaultTableModel) Table.getModel();
        renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        Table.getColumnModel().getColumn(0).setCellRenderer(renderer);
        Table.getColumnModel().getColumn(1).setCellRenderer(renderer);
        Table.getColumnModel().getColumn(2).setCellRenderer(renderer);
        Table.getColumnModel().getColumn(3).setCellRenderer(renderer);
    }

    public void crateProcessor(int instructionPerProcess[]) {
        processor = new Processor(instructionPerProcess);
        processor.executer.start();
        rf.setVisible(true);
    }

    public boolean resumeProcess(int processIdentityNo) {
        if (processor.processes[processIdentityNo].readyState() == false) {
            processor.processes[processIdentityNo].eventResolved();
            return true;
        }
        return false;
    }

    public void repaintTable() {
        for (int i = 0; i < 10; i++) {
            tableModel.setValueAt((char) ('a' + i), i, 0);
            tableModel.setValueAt(processor.processes[i].totalInstruction, i, 1);
            tableModel.setValueAt(processor.processes[i].instructionExecuted, i, 2);

            if (processor.processes[i].isExecuted()) {
                tableModel.setValueAt("Executed", i, 3);
            } else {
                if (processor.processes[i] == processor.currentProcess) {
                    if (processor.processes[i].readyState()) {
                        tableModel.setValueAt("Running", i, 3);
                    } else {
                        tableModel.setValueAt("Suspended", i, 3);
                    }
                } else if (processor.processes[i].readyState() == true) {
                    tableModel.setValueAt("Ready", i, 3);
                }
            }
        }
    }

    private class Processor implements Runnable {

        Process[] processes;
        Process currentProcess;

        Thread executer;
        StopExecuter stpe;

        public Processor(int instructionPerProcesses[]) {
            executer = new Thread(this);
            stpe = new StopExecuter();
            stpe.stopper.start();

            processes = new Process[10];
            for (int i = 0; i < 10; i++) {
                processes[i] = new Process(instructionPerProcesses[i]);
            }
        }

        @Override
        public void run() {
            int p = 0;
            while (true) {
                repaintTable();
                processor.currentProcess = processor.processes[p];

                if (!processor.processes[p].isExecuted() && processor.processes[p].readyState) {
                    processor.processes[p].executeNextInstruction();
                    //System.out.println("p is "+p);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(LastFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                repaintTable();
                processor.currentProcess = processor.processes[p];

                if (!processor.processes[p].isExecuted() && processor.processes[p].readyState) {
                    processor.processes[p].executeNextInstruction();
                    //System.out.println("p is "+p);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(LastFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                p++;
                if (p == 10) {
                    p = 0;
                }
            }
        }

        private class StopExecuter implements Runnable {

            Thread stopper = new Thread(this);

            @Override
            public void run() {
                boolean allfinished = false;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(LastFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                while (true) {
                    for (int i = 0; i < 10; i++) {
                        if (processes[i].isExecuted() == true) {
                            allfinished = true;
                        } else {
                            allfinished = false;
                            break;
                        }
                    }
                    if (allfinished == true) {
                        executer.stop();
                        repaintTable();
                        //System.out.println("Executter Stopped!");
                        finishLabel.setText("Process Finished... Click here to Exit!");
                        JOptionPane.showMessageDialog(lf, "The Processor has finished all Processes!", "Done!", INFORMATION_MESSAGE);

                        rf.setVisible(false);

                        this.stopper.stop();
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(LastFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    private class Process {

        private int totalInstruction;
        private int instructionExecuted;

        private boolean readyState;
        private boolean executed;

        public Process(int totalInstruction) {
            this.totalInstruction = totalInstruction;
            this.instructionExecuted = 0;
            this.readyState = true;
            this.executed = false;
        }

        public void executeNextInstruction() {
            this.instructionExecuted++;
            if (this.instructionExecuted == this.totalInstruction) {
                this.executed = true;
                //System.out.println("True");
            }
        }

        public boolean readyState() {
            return this.readyState;
        }

        public boolean isExecuted() {
            return this.executed;
        }

        public void eventOccured() {
            this.readyState = false;
        }

        public void eventResolved() {
            this.readyState = true;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        finishLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Lucida Calligraphy", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 0));
        jLabel1.setText("University Of Karachi");

        Table.setBorder(new javax.swing.border.MatteBorder(null));
        Table.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null}
                },
                new String[]{
                    "Process_Id", "Total Instruction", "Instruction Executed", "Status"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableMouseClicked(evt);
            }
        });
        Table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TableKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(Table);

        jLabel2.setFont(new java.awt.Font("MV Boli", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Click or Press any key to Invoke an event");

        jLabel3.setFont(new java.awt.Font("MV Boli", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("It will Suspend the currently running process.");

        jLabel4.setFont(new java.awt.Font("MV Boli", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("You might find a small window to resume a process on tle upper");

        jLabel5.setFont(new java.awt.Font("MV Boli", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Left corner of your screen, use it to resume any suspended process.");

        finishLabel.setFont(new java.awt.Font("MV Boli", 3, 14)); // NOI18N
        finishLabel.setForeground(new java.awt.Color(0, 204, 51));
        finishLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                finishLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(0, 47, Short.MAX_VALUE)
                                                .addComponent(jLabel1)
                                                .addGap(0, 47, Short.MAX_VALUE))
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(finishLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addGap(0, 0, 0)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addGap(0, 0, 0)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
                                .addComponent(finishLabel)
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        processor.currentProcess.eventOccured();
    }//GEN-LAST:event_formKeyReleased

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        processor.currentProcess.eventOccured();
    }//GEN-LAST:event_formMouseReleased

    private void TableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableKeyReleased
        processor.currentProcess.eventOccured();
    }//GEN-LAST:event_TableKeyReleased

    private void TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableMouseClicked
        processor.currentProcess.eventOccured();
    }//GEN-LAST:event_TableMouseClicked

    private void finishLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_finishLabelMouseClicked
        System.exit(0);
    }//GEN-LAST:event_finishLabelMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int c = JOptionPane.showConfirmDialog(this, "Do you really wish to EXIT.", "Exit?", YES_NO_OPTION);
        if (c == 0) {
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LastFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LastFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LastFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LastFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LastFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Table;
    private javax.swing.JLabel finishLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}

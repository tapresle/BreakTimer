package timer;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class BreakTimer extends JPanel
                             implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7794165450463932937L;

	public final static int ONE_SECOND = 1000;

    private JProgressBar progressBar;
    private Timer timer;
    private JButton startButton;
    private LongTask task;
    private JTextArea taskOutput;
    private JTextArea taskInput;
    private String newline = "\n";

    public BreakTimer() {
        super(new BorderLayout());
        task = new LongTask();
        
        //Create the demo's UI.
        startButton = new JButton("Start");
        startButton.setActionCommand("start");
        startButton.addActionListener(this);

        progressBar = new JProgressBar(0, task.getLengthOfTask());
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        taskOutput = new JTextArea(5, 20);
        taskOutput.setMargin(new Insets(5,5,5,5));
        taskOutput.setEditable(false);
        taskOutput.setCursor(null); 
        
        taskInput = new JTextArea(1, 5);
        taskInput.setMargin(new Insets(5,5,5,5));
        taskInput.setEditable(true);
        taskInput.setCursor(null);

        JPanel panel = new JPanel();
        panel.add(taskInput);
        panel.add(startButton);
        panel.add(progressBar);

        add(panel, BorderLayout.PAGE_START);
        add(new JScrollPane(taskOutput), BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //Create a timer.
        timer = new Timer(ONE_SECOND, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                progressBar.setValue(task.getCurrent());
                String s = task.getMessage();
                if (s != null && !startButton.isEnabled()) {
                    taskOutput.append(s + newline);
                    taskOutput.setCaretPosition(
                            taskOutput.getDocument().getLength());
                }
                if (task.isDone() && !startButton.isEnabled()) {
                    JOptionPane.showConfirmDialog(null, "Time to take a break!", "Break time!", JOptionPane.DEFAULT_OPTION);
                    startButton.setEnabled(true);
                    setCursor(null); //turn off the wait cursor
                    progressBar.setValue(progressBar.getMaximum());
                }
            }
        });
    }

    /**
     * Called when the user presses the start button.
     */
    public void actionPerformed(ActionEvent evt) {
    	startButton.setEnabled(false);
        int time = Integer.parseInt(taskInput.getText());
    	task.setLengthOfTask(time);
    	progressBar.setMaximum(time);
    	progressBar.setValue(time);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        task.go();
        timer.start();
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);

        JFrame frame = new JFrame("Timer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent newContentPane = new BreakTimer();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
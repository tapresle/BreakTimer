package timer;

/** Uses a SwingWorker to perform a time-consuming (and utterly fake) task. */

public class LongTask {
    private int lengthOfTask;
    private int current = 0;
    private boolean done = false;
    private boolean canceled = false;
    private String statMessage;

    public LongTask() {
        lengthOfTask = 0;
    }

    /**
     * Called from ProgressBarDemo to start the task.
     */
    public void go() {
        final SwingWorker worker = new SwingWorker() {
            public Object construct() {
                current = lengthOfTask;
                done = false;
                canceled = false;
                statMessage = null;
                return new ActualTask();
            }
        };
        worker.start();
    }

    public int getLengthOfTask() {
        return lengthOfTask;
    }
    
    public void setLengthOfTask(int seconds) {
    	lengthOfTask = seconds;
    }

    public int getCurrent() {
        return current;
    }

    public void stop() {
        canceled = true;
        statMessage = null;
    }

    public boolean isDone() {
        return done;
    }

    public String getMessage() {
        return statMessage;
    }

    class ActualTask {
        ActualTask() {
            while (!canceled && !done) {
                try {
                    Thread.sleep(1000); //sleep for a second
                    current -= 1; //make some progress
                    if (current <= 0) {
                        done = true;
                        statMessage = "Time to take a break!";
                    } else {
                    	statMessage = "Time until next break: " + current + " seconds";
                    }
                } catch (InterruptedException e) {
                    System.out.println("ActualTask interrupted");
                }
            }
        }
    }
}
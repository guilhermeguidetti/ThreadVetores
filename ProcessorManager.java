public class ProcessorManager {
    private int availableProcessors;

    public ProcessorManager(int maxProcessors) {
        this.availableProcessors = maxProcessors;
    }

    public synchronized boolean allocateProcessor() {
        if (availableProcessors > 0) {
            availableProcessors--;
            return true;
        } else {
            return false;
        }
    }

    public synchronized void releaseProcessor() {
        availableProcessors++;
    }

    public synchronized boolean hasAvailableProcessor() {
        return availableProcessors > 0;
    }

    public int getAvailableProcessors() {
        return availableProcessors;
    }
}

public class ResourceManager {

    private final Object gradeLock = new Object();
    private final Object reportLock = new Object();
    public void methodNoDeadlock() {
        synchronized (gradeLock) {
            System.out.println("Locked gradeLock");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            synchronized (reportLock) {
                System.out.println("Locked reportLock");
            }
        }
    }
}
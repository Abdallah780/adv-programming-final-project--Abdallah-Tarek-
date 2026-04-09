import java.util.concurrent.ConcurrentHashMap;

public class ThreadSafeGradeRepository {

    private ConcurrentHashMap<String, String> results = new ConcurrentHashMap<>();
    private final Object lock = new Object();
    private boolean dataAvailable = false;
    public void addResult(String studentId, String result) {
        synchronized(lock) {
            results.put(studentId, result);
            dataAvailable = true;
            lock.notify();
        }
    }
    public String waitForResult(String studentId) {
        synchronized(lock) {
            while (!dataAvailable) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return null;
                }
            }
            dataAvailable = false;
            return results.get(studentId);
        }
    }
    public ConcurrentHashMap<String, String> getAllResults() {
        return results;
    }
}
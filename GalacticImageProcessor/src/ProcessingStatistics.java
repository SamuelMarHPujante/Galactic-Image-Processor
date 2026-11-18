import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ProcessingStatistics {

    private final AtomicInteger completedImages = new AtomicInteger();
    private final AtomicInteger failedImages = new AtomicInteger();
    private final AtomicLong accumulatedTaskTime = new AtomicLong();

    public void register(ImageResult result) {
        if (result.successful()) completedImages.incrementAndGet();
        else failedImages.incrementAndGet();
        accumulatedTaskTime.addAndGet(result.elapsedMilliseconds());
    }

    public void reset() {
        completedImages.set(0);
        failedImages.set(0);
        accumulatedTaskTime.set(0);
    }

    public int getCompletedImages() {
        return completedImages.get();
    }

    public int getFailedImages() {
        return failedImages.get();
    }

    public long getAccumulatedTaskTime() {
        return accumulatedTaskTime.get();
    }
}

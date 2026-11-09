import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ProcessingStatistics {

    private final AtomicInteger completedImages = new AtomicInteger();
    private final AtomicInteger failedImages = new AtomicInteger();
    private final AtomicLong accumulatedTaskTime = new AtomicLong();

    public void register(ImageResult result) {
        /*
         * TODO:
         * 1. Do not synchronize this method. Use the atomic operations supplied
         *    by AtomicInteger and AtomicLong.
         * 2. ImageResult is a record. Read its components with the automatic
         *    accessors, which have the same names as the components. If
         *    result.successful() is true, call incrementAndGet() on
         *    completedImages; otherwise call it on failedImages.
         * 3. Add result.elapsedMilliseconds() with addAndGet(). Do not write
         *    getSuccessful() or getElapsedMilliseconds().
         */
    }

    public void reset() {
        /*
         * TODO: call set(0) on the three atomic objects. Main calls reset()
         * only once, before starting the complete comparison of the five
         * execution modes.
         */
    }

    public int getCompletedImages() {
        // TODO: return the current atomic value with get().
        return 0;
    }

    public int getFailedImages() {
        // TODO: return the current atomic value with get().
        return 0;
    }

    public long getAccumulatedTaskTime() {
        // TODO: return the current atomic value with get().
        return 0;
    }
}

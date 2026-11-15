import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public class Main {

    private static final Path INPUT_DIRECTORY = Path.of("input");
    private static final Path OUTPUT_DIRECTORY = Path.of("output");

    /*
     * This is the ONE result list used by the five execution modes.
     * It is synchronized because raw threads and executor workers may add
     * results at the same time. It is cleared once before the complete
     * comparison starts. Each mode adds twelve results, so after every mode
     * finishes it contains the 60 results used by the report.
     */
    private static final List<ImageResult> results =
            Collections.synchronizedList(new ArrayList<>());

    /*
     * This object summarizes all five execution modes. Its atomic counters are
     * reset once before the complete comparison starts and receive 60 updates.
     */
    private static final ProcessingStatistics statistics =
            new ProcessingStatistics();

    private static final int POOL_SIZE = Math.max(
            1,
            Runtime.getRuntime().availableProcessors()
    );

    public static void main(String[] args) {
        try {
            List<Path> imagePaths = findImages(INPUT_DIRECTORY);

            System.out.println("Images found: " + imagePaths.size());
            System.out.println("Complete the TODO sections described in the assignment.");

            /*
             * TODO 1: call resetSharedData() ONCE, before running any mode.
             * TODO 2: run the sequential version and store its returned total
             *         time in sequentialTime.
             * TODO 3: run the raw-thread version and store its returned total
             *         time in rawThreadTime.
             * TODO 4: run the ExecutorService version and store its returned
             *         total time in executorTime.
             * TODO 5: run the Callable version and store its returned total
             *         time in callableTime.
             * TODO 6: run the CompletableFuture version and store its returned
             *         total time in completableFutureTime.
             * TODO 7: after every mode has completely finished, results and
             *         statistics contain 60 entries: 12 from each mode. Create
             *         output/report/report.html by passing them, the five
             *         total times and POOL_SIZE to ReportWriter.
             *
             * Do not clear results or reset statistics between modes.
             */

        } catch (IOException e) {
            System.err.println("Unable to read the input directory: "
                    + e.getMessage());
        }
    }

    private static List<Path> findImages(Path directory) throws IOException {
        List<Path> images = new ArrayList<>();
        try (Stream<Path> paths = Files.list(directory)) {
            images = paths
                    .filter(Files::isRegularFile)
                    .filter(i -> i.getFileName().toString().toLowerCase().endsWith(".png") ||
                            i.getFileName().toString().toLowerCase().endsWith(".jpg") ||
                            i.getFileName().toString().toLowerCase().endsWith(".jpeg"))
                    .filter(i -> Files.exists(Paths.get("input/" + i.getFileName() + ".txt")))
                    .sorted()
                    .toList();
        }
        return images;
    }

    private static long runSequentially(List<Path> images) {
        /*
         * TODO:
         * 1. Start the total mode timer.
         * 2. For every path, call ImageProcessor.processImage() using
         *    output/sequential as the mode output directory and
         *    "Sequential" as the execution mode.
         * 3. Add the returned ImageResult to results.
         * 4. Call statistics.register(result).
         * 5. Return the complete elapsed time in milliseconds.
         */
        return 0;
    }

    private static long runWithRawThreads(List<Path> images)
            throws InterruptedException {

        /*
         * TODO:
         * 1. Start the total mode timer.
         * 2. Create one Thread per image and keep the Thread objects in ONE
         *    List<Thread>. This is not the ImageResult list named results.
         * 3. Inside each Thread: process its image using output/raw_threads
         *    and "Raw threads", add the returned ImageResult to results and call
         *    statistics.register(result).
         * 4. Start every Thread and then join every Thread.
         * 5. Only after all joins have finished, return the complete elapsed
         *    time in milliseconds.
         */
        return 0;
    }

    private static long runWithExecutor(List<Path> images)
            throws InterruptedException {

        /*
         * TODO:
         * 1. Start the total mode timer.
         * 2. Create a fixed pool containing POOL_SIZE workers.
         * 3. Submit one ImageJob per image. Pass output/executor, results and
         *    statistics to every ImageJob constructor.
         * 4. Call shutdown() and awaitTermination(). If necessary, use
         *    shutdownNow() as explained in the assignment.
         * 5. Only after the executor has terminated, return the complete
         *    elapsed time in milliseconds.
         */
        return 0;
    }

    private static long runWithCallables(List<Path> images)
            throws InterruptedException {

        /*
         * TODO:
         * 1. Start the total mode timer.
         * 2. Create a fixed ExecutorService with POOL_SIZE workers.
         * 3. Submit one ImageCallable per image and store every returned
         *    Future<ImageResult> in one list.
         * 4. In main, call get() on every Future. Add each returned result to
         *    results and register it in statistics. The Callable itself must
         *    not modify either shared object.
         * 5. Handle ExecutionException and interruption as described in the
         *    assignment, then shut down the executor correctly.
         * 6. Save images below output/callable and return the total elapsed
         *    time only after all results have been collected.
         */
        return 0;
    }

    private static long runWithCompletableFutures(List<Path> images) {

        /*
         * TODO:
         * 1. Start the total mode timer. Do not create an ExecutorService for
         *    this mode: use CompletableFuture in the same way as in the notes.
         * 2. Create one CompletableFuture<ImageResult> per image with
         *    supplyAsync(). Call the overload that only receives the Supplier,
         *    so Java uses its common pool. Save files in output/completable_future.
         * 3. Chain thenApply() to increment a local AtomicInteger progress
         *    counter and print "Future progress X/12" with one println call.
         * 4. Store all CompletableFuture objects in one list.
         * 5. Build one CompletableFuture<Void> with allOf() and wait for it.
         * 6. After allOf has completed, join each original future, add the
         *    ImageResult to results and register it in statistics.
         * 7. Return the total elapsed time after collecting every result. Do
         *    not call shutdown() or shutdownNow() on the common pool: Java
         *    manages it. Do not use Thread.sleep() or parallel streams.
         */
        return 0;
    }

    private static void resetSharedData() {
        /*
         * TODO: clear results and call statistics.reset().
         * Main calls this method only once, before starting the sequential
         * mode. It must not be called between any execution modes.
         */
    }
}

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public final class ReportWriter {

    private ReportWriter() {
    }

    public static void writeHtmlReport(
            Path reportPath,
            List<ImageResult> results,
            ProcessingStatistics statistics,
            long sequentialTime,
            long rawThreadTime,
            long executorTime,
            long callableTime,
            long completableFutureTime,
            int poolSize) throws IOException {

        /*
         * TODO: generate one HTML file with:
         * - the five total execution times;
         * - speedup of every concurrent mode compared with sequential execution;
         * - number of successful and failed executions obtained from
         *   statistics (60 total when every image succeeds in all modes);
         * - pool size;
         * - one table with mode, workers, total ms and speedup;
         * - one row per ImageResult containing execution mode, location,
         *   coordinates, capture time, dimensions, worker, task time, status
         *   and error;
         * - relative links to originalName_grayScale.png,
         *   originalName_blur.png and originalName_edge.png.
         *
         * ImageResult and ImageMetadata are records. Access their components
         * with methods named exactly like those components. Examples:
         * result.executionMode(), result.fileName(), result.threadName(),
         * result.elapsedMilliseconds() and result.successful().
         * Access nested metadata with result.metadata().location(),
         * result.metadata().coordinates() and result.metadata().capturedAt().
         * Do not use getExecutionMode(), getMetadata() or getLocation().
         *
         * Create output/report before writing output/report/report.html.
         * Main calls this method after every mode has terminated. The list and
         * statistics therefore contain all 60 executions: 12 sequential,
         * 12 raw-thread, 12 executor, 12 Callable and 12 CompletableFuture
         * results.
         * You may build the HTML with StringBuilder and save it as UTF-8
         * with Files.writeString(). Escape all text from metadata or errors.
         */

        throw new UnsupportedOperationException(
                "Complete ReportWriter.writeHtmlReport()"
        );
    }
}

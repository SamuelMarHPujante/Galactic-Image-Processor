/**
 * Immutable result of processing one image in one execution mode.
 *
 * A record is created by passing every component in the declared order:
 * new ImageResult(executionMode, fileName, metadata, threadName, ...).
 *
 * Java generates one accessor per component. Use result.fileName(),
 * result.successful() or result.metadata(); records do not use getters such
 * as getFileName() or isSuccessful(). A record cannot be modified after it
 * has been created, so create a new record if different values are needed.
 */
public record ImageResult(
        String executionMode,
        String fileName,
        ImageMetadata metadata,
        String threadName,
        int width,
        int height,
        long elapsedMilliseconds,
        boolean successful,
        String errorMessage,
        String grayScaleOutput,
        String blurOutput,
        String edgeOutput) {
}

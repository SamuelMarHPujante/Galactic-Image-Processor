# Galactic Image Processor

Java 21 starter project for the concurrency assignment.

1. Open this folder as a Maven project in IntelliJ IDEA.
2. Run `SingleImageDemo` first. It demonstrates the supplied filters on one image.
3. Read the assignment document.
4. Complete the `TODO` sections in the remaining classes.

The `input` folder contains twelve 1920 x 1080 PNG images and twelve UTF-8 metadata files. Every image has a TXT file with the same base filename.

Each execution mode must create exactly these filter directories when needed:

- `grayScale`
- `blur`
- `edge`

Output names must follow `originalName_grayScale.png`, `originalName_blur.png` and `originalName_edge.png`. The final report must be written to `output/report/report.html` and must include the supplied location, coordinates and capture time for every image.

`Main` supplies one synchronized `List<ImageResult>` and one `ProcessingStatistics` object. Call `resetSharedData()` only once, before the sequential execution. Do not clear or reset these objects between modes. Each execution adds twelve results, so the final list contains 60 `ImageResult` objects and the statistics summarize the same 60 executions. Every result stores its execution mode (`Sequential`, `Raw threads`, `ExecutorService`, `Callable` or `CompletableFuture`) so that `ReportWriter` can distinguish the rows.

The advanced extension adds two real processing modes. `ImageCallable` returns one `ImageResult` through a `Future<ImageResult>` and uses a fixed `ExecutorService`. The `CompletableFuture` mode follows the approach used in the notes: it calls `supplyAsync()` without passing an executor, so Java uses its common pool. It reports progress in a chained stage and waits for the complete group with `allOf()`. Students must not create or shut down an executor for this mode. `ProcessingStatistics` uses `AtomicInteger` and `AtomicLong`, and the CompletableFuture progress counter uses a local `AtomicInteger`.

No external libraries are required. The project uses only the Java standard library.

## Records used in the project

`ImageResult` and `ImageMetadata` are Java records: compact, immutable classes
intended to carry data. Create one by passing every component in the order
declared in the record, for example `new ImageMetadata(imageName, location,
coordinates, capturedAt, description)`.

Java automatically creates an accessor with the same name as each component.
Use `result.successful()`, `result.elapsedMilliseconds()` and
`result.metadata().location()`. Do not write `getSuccessful()`,
`getElapsedMilliseconds()` or `getLocation()`. Record components are final and
cannot be changed after construction; create a new record if different values
are required.

Do not use `break` or `continue` in the completed solution. Organize loop bodies with conditions, helper methods and the normal loop condition instead.

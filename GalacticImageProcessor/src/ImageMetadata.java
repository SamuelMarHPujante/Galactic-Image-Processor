import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Reads the supplied key=value metadata file associated with an image.
 * This class is complete and does not need to be modified.
 *
 * ImageMetadata is a record. Java generates the accessors imageName(),
 * location(), coordinates(), capturedAt() and description(). For example,
 * use metadata.location(), not metadata.getLocation().
 */
public record ImageMetadata(
        String imageName,
        String location,
        String coordinates,
        String capturedAt,
        String description) {

    public static ImageMetadata readForImage(Path imagePath)
            throws IOException {

        String baseName = baseName(imagePath.getFileName().toString());
        Path metadataPath = imagePath.resolveSibling(baseName + ".txt");

        Map<String, String> values = new HashMap<>();

        for (String line : Files.readAllLines(
                metadataPath,
                StandardCharsets.UTF_8)) {

            boolean hasData = !line.isBlank()
                    && !line.stripLeading().startsWith("#");

            if (hasData) {
                int separator = line.indexOf('=');
                if (separator <= 0) {
                    throw new IOException(
                            "Invalid metadata line in "
                                    + metadataPath + ": " + line
                    );
                }

                String key = line.substring(0, separator).trim();
                String value = line.substring(separator + 1).trim();
                values.put(key, value);
            }
        }

        return new ImageMetadata(
                required(values, "IMAGE", metadataPath),
                required(values, "LOCATION", metadataPath),
                required(values, "COORDINATES", metadataPath),
                required(values, "CAPTURED_AT", metadataPath),
                required(values, "DESCRIPTION", metadataPath)
        );
    }

    public static String baseName(String fileName) {
        int dot = fileName.lastIndexOf('.');
        return dot > 0 ? fileName.substring(0, dot) : fileName;
    }

    private static String required(
            Map<String, String> values,
            String key,
            Path metadataPath) throws IOException {

        String value = values.get(key);
        if (value == null || value.isBlank()) {
            throw new IOException(
                    "Missing " + key + " in " + metadataPath
            );
        }
        return value;
    }
}

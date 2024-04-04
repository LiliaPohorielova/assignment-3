package vandy.mooc.functional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static vandy.mooc.functional.ExceptionUtils.rethrowSupplier;

/**
 * This class is used to create a {@link Map} from the files located
 * in a specified folder.  The {@link Map}'s keys are the filenames
 * and values are the file contents.
 */
public class FileUtils {
    /**
     * Reads all the files in the specified folder and stores their
     * contents in a {@link Map}.  The method replaces any dashes in
     * the file name with spaces.
     *
     * @param folderName The name of the folder in the resources directory
     *                   to read from (e.g., "plays")
     * @param extension  The extension of the files to read (e.g., ".txt")
     * @return A {@link Map} where keys are the filenames with dashes
     *         replaced by spaces, and values are the contents of the
     *         files
     */
    public static Map<String, String> loadMapFromFolder
        (String folderName,
         String extension) {
        try (
             // Use the Files.walk method to get a stream of paths in
             // the folder.
             var paths = Files.walk(getPaths(folderName))) {

            return paths
                // Filter the stream to include only regular files.
                .filter(Files::isRegularFile)

                // Collect the paths into a map, grouping them by
                // modified key, using TreeMap as the map
                // implementation to maintain sorted order, and
                // mapping each path to its corresponding file
                // contents.
                .collect(groupingBy(path ->
                                    // Remove the extension and
                                    // replace '_' with ' ';
                                    updatePath(path, extension),

                                    // Use TreeMap as the Map.
                                    TreeMap::new,

                                    // Associate the play contents
                                    // with the play title.
                                    mapping(FileUtils::getFileContents,
                                            Collectors.joining())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the contents of the {@link File} at the specified {@link
     * Path}.
     *
     * @param path The {@link Path} to the {@link File}
     * @return A {@link String} containing all the bytes in the {@link
     *         File} at the specified {@link Path}
     */
    private static String getFileContents(Path path) {
        // This adapter converts a checked exception into a runtime
        // exception.
        return rethrowSupplier(() ->
                               new String(Files.readAllBytes(path)))
            // Get the result of the Supplier.
            .get();
    }

    /**
     * Gets the path to the specified folder.
     *
     * @param folderName The name of the folder in the resources
     *                   directory
     * @return The path to the specified folder
     */
    private static Path getPaths(String folderName) {
        // This adapter converts a checked exception into a runtime
        // exception.
        return rethrowSupplier(() -> Paths
                               // Use the ClassLoader's
                               // getSystemResource() method to get
                               // the path to the folder.
                               .get(ClassLoader
                                    .getSystemResource(folderName)
                                    .toURI()))
            // Get the result of the Supplier.
            .get();
    }

    /**
     * Remove specified {@code extension} from {@code filename} and
     * replace underscores ('_') with spaces (' ').
     *
     * @param path The {@link Path} of the file to remove the
     *             extension from
     * @param extension The extension of the files to read (e.g.,
     *                  ".txt")
     * @return The filename without the extension and with underscores
     *         replaced with spaces.
     */
    private static String updatePath(Path path,
                                     String extension) {
        // Get the filename.
        var filename = path.getFileName().toString();

        // If the filename ends with the specified extension, then
        // remove it.
        if (filename.endsWith(extension)) 
            filename = filename
                .substring(0,
                           filename.length()
                           - extension.length());

        return filename
            // Replace underscores with spaces.
            .replace('-', ' ');
    }
}

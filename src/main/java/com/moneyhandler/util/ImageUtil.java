package com.moneyhandler.util;

import java.io.File;
import java.io.IOException;

import jakarta.servlet.http.Part;

/**
 * Utility class for handling image uploads in the MoneyHandler application.
 */
public class ImageUtil {

    /**
     * Extracts the filename from the uploaded image part.
     *
     * @param part the {@link Part} object from the form upload.
     * @return the image filename, or "default.png" if not found.
     */
    public String getImageNameFromPart(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");

        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                String imageName = s.substring(s.indexOf('=') + 2, s.length() - 1);
                return imageName.isEmpty() ? "default.png" : imageName;
            }
        }

        return "default.png";
    }

    /**
     * Saves the uploaded image to the specified folder within the project structure.
     *
     * @param part       the uploaded file {@link Part}.
     * @param rootPath   the project root path (typically from servlet context).
     * @param folderName the subfolder under /resources/images/ where the image will be saved.
     * @return true if uploaded successfully, false otherwise.
     */
    public boolean uploadImage(Part part, String rootPath, String folderName) {
        String savePath = getSavePath(rootPath, folderName);
        File dir = new File(savePath);

        if (!dir.exists() && !dir.mkdirs()) {
            return false; // Could not create directories
        }

        try {
            String imageName = getImageNameFromPart(part);
            part.write(savePath + File.separator + imageName);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Constructs the full save path for images within the project.
     *
     * @param rootPath   the real path to the webapp.
     * @param folderName the image subfolder (e.g., "users", "transactions").
     * @return the full absolute path for saving images.
     */
    public String getSavePath(String rootPath, String folderName) {
        return rootPath + "resources" + File.separator + "images" + File.separator + folderName;
    }
}

package com.test.tracker.core.util;

import com.test.tracker.core.exception.EntityNotFoundException;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DocumentUtil {

    public static void attachDocumentToResponse(File file, HttpServletResponse response, String mediaType) {
        try {
            InputStream in;
            try {
                in = new FileInputStream(file);
                response.setContentType(mediaType);
                response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
                response.setHeader("Content-Length", String.valueOf(file.length()));
                FileCopyUtils.copy(in, response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (EntityNotFoundException e) {
            response.setStatus(404);
        }
    }
}

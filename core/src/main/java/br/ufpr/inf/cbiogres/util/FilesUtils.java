/*
 * Copyright 2016 Jackson Antonio do Prado Lima <jacksonpradolima at gmail.com> & Thiago Nascimento.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.ufpr.inf.cbiogres.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.google.common.base.Preconditions;

/**
 * Files Utils Class
 *
 * @author Thiago Nascimento
 * @since 2015-10-26
 * @version 1.0.0
 */
public class FilesUtils {

    /**
     * Get all files at folder
     *
     * @param folder folder path
     * @return List of files at folder
     */
    public static List<String> getFiles(final File folder) {
        return getFiles(folder, "");
    }

    public static List<String> getFiles(final File folder, String startWith) {
        return getFiles(folder, startWith, null);
    }

    /**
     * Get all files at folder start with some words
     *
     * @param folder folder path
     * @return List of files at folder
     */
    public static List<String> getFiles(final File folder, String startWith, String ignore) {
        Preconditions.checkNotNull(startWith, "Startwith cannot be empty");
        Preconditions.checkNotNull(folder, "Folder cannot be null");
        Preconditions.checkArgument(folder.isDirectory(), "Folder variable cannot be a file");

        List<String> files = new ArrayList<String>();

        for (final File fileEntry : folder.listFiles()) {

            if (fileEntry.isDirectory()) {
                files.addAll(getFiles(fileEntry, startWith, ignore));
            } else if (fileEntry.getName().startsWith(startWith)) {
                if (ignore == null || !fileEntry.getName().contains(ignore)) {
                    files.add(fileEntry.getAbsolutePath());
                }
            }
        }

        return files;
    }

    /**
     * Return a double value into the file
     *
     * @param File File that will be read
     * @return A Double value
     */
    public static Double getValue(File file) {
        Preconditions.checkNotNull(file, "File cannot be null");
        Preconditions.checkArgument(!file.getAbsolutePath().isEmpty(), "File cannot be empty");

        try {
            String value = FileUtils.readFileToString(file);

            if (value != null && !value.isEmpty()) {
                return Double.valueOf(value);
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

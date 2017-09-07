/*
 * Copyright (c) 2016. EAGER-CLI Alexander Peltzer
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package IO;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

/**
 * Recursively lists files in Folders, ending with *.fq or *.fastq
 */
public final class FileSearcher {
    private ArrayList<String> configuration_files = new ArrayList<String>();

    public ArrayList<String> processFiles(String pathtoprocess) throws IOException{
        FileVisitor<Path> fileProcessor = new ProcessFile();
        Files.walkFileTree(Paths.get(pathtoprocess), fileProcessor);
        return configuration_files;
    }

    private class ProcessFile extends SimpleFileVisitor<Path> {
        @Override
        public FileVisitResult visitFile(Path aFile, BasicFileAttributes aAttrs
        ) throws IOException {
            if(!aFile.toFile().isDirectory()){
                if(aFile.toFile().getAbsolutePath().endsWith(".xml") && !aFile.toFile().getAbsolutePath().startsWith(".") && !aFile.toFile().getName().startsWith(".")){
                    configuration_files.add(aFile.toString());
                }
            }
            return FileVisitResult.CONTINUE;
        }


    }
}


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

package Runner;

import IO.Communicator;
import com.google.common.io.Files;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by peltzer on 29.01.14.
 */
public class PrerequisitesChecker {
    private Communicator c;

    public PrerequisitesChecker(Communicator c) {
        this.c = c;


    }

    public boolean checkForIndices(String whichone) {
        boolean out = false;
        if (whichone.equals("CircularMapper")) {
            Path path = Paths.get(this.c.getGUI_reference());

            if (java.nio.file.Files.exists(path)) {
                String output_path = new File(this.c.getGUI_reference()).getParent();
                String extension = Files.getFileExtension(this.c.getGUI_reference());
                String output_stem = Files.getNameWithoutExtension(this.c.getGUI_reference());
                Path path2 = Paths.get(output_path + "/" + output_stem + "_" +this.c.getCM_elongationfac()+"."+extension + ".ann");
                System.out.println("Checking for file at path: " + path2 + "\n");
                if(java.nio.file.Files.notExists(path2)) {
                    out = true;
                }
            }
        }

        if (whichone.equals("CircGenerator")) {
            Path path = Paths.get(this.c.getGUI_reference());

            if (java.nio.file.Files.exists(path)) {
                String extension = Files.getFileExtension(this.c.getGUI_reference());
                String filename = Files.getNameWithoutExtension(this.c.getGUI_reference());
                Path path2 = Paths.get(filename + "_" + this.c.getCM_elongationfac() + "." + extension );
                System.out.println("Checking for file at path: " + path2 + "\n");
                if(java.nio.file.Files.notExists(path2)) {
                    out = true;
                }
            }
        }

        if (whichone.equals("whole")) {
            Path path = Paths.get(this.c.getGUI_reference() + ".ann");
            if (java.nio.file.Files.notExists(path)) {
                out = true;
            }
        }


        if (whichone.equals("SeqDict")) {
            Path path = Paths.get(this.c.getGUI_reference());
            if (java.nio.file.Files.exists(path)) {
                File f = new File(this.c.getGUI_reference());
                String absolutPath = f.getAbsolutePath();
                String filepath = absolutPath.substring(0, absolutPath.lastIndexOf(File.separator));
                Path path2 = Paths.get(filepath + "/" + Files.getNameWithoutExtension(absolutPath) + ".dict");
                System.out.println("Checking for file at path: " + path2 + "\n");
                if (java.nio.file.Files.notExists(path2)) {
                    out = true;
                }
            }


        }

        if (whichone.equals("faidx")) {
            Path path = Paths.get(this.c.getGUI_reference() + ".fai");
            if (java.nio.file.Files.notExists(path)) {
                out = true;
            }
        }

        if(whichone.equals("samindex")){
            Path path = Paths.get(this.c.getGUI_inputfiles().get(0) + ".bai");
            if(java.nio.file.Files.notExists(path)){
                out = true;
            }
        }

        if (whichone.equals("Stampy")) {
            Path indexpath = Paths.get(this.c.getGUI_reference() + ".stidx");
            Path hashpath = Paths.get(this.c.getGUI_reference() + ".sthash");
            if (java.nio.file.Files.notExists(indexpath) && java.nio.file.Files.notExists(hashpath)) {
                out = true;
            }
        }

        if (whichone.equals("BT2")) {
            Path path = Paths.get(this.c.getGUI_reference() + ".1.bt2");
            if (java.nio.file.Files.notExists(path)) {
                out = true;
            }
        }


        return out;
    }

}

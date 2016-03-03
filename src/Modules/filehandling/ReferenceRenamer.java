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

package Modules.filehandling;

import IO.Communicator;
import Modules.AModule;
import com.google.common.io.Files;

import java.io.File;

/**
 * Created by peltzer on 14.02.14.
 */
public class ReferenceRenamer extends AModule {

    public ReferenceRenamer(Communicator c) {
        super(c);
    }

    @Override
    public void setParameters() {
        String inputref = this.communicator.getGUI_reference();
        File f = new File(inputref);
        String absolutePath = f.getAbsolutePath();
        String filepath = absolutePath.substring(0,absolutePath.lastIndexOf(File.separator));
        String filename = Files.getNameWithoutExtension(inputref);
        String extension = Files.getFileExtension(inputref);
        if(extension != "fasta"){
          String newFile = filepath+"/"+ filename + ".fasta";
          this.parameters = new String[]{"mv", inputref, newFile};
          this.communicator.setGUI_reference(newFile);
        } else {
            this.parameters = new String[]{"echo", "Reference was already in *.fasta Format!"};
        }
        this.outputfile = this.communicator.getGUI_inputfiles();


    }

    @Override
    public String getOutputfolder() {
        return null;
    }

}

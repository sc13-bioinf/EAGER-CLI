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

package Modules.indexing;

import IO.Communicator;
import Modules.AModule;
import com.google.common.io.Files;

import java.io.File;

/**
 * Created by peltzer on 29.01.14.
 */
public class CreateSequenceDictionary extends AModule {
    public CreateSequenceDictionary(Communicator c) {
        super(c);
    }

    @Override
    public void setParameters() {
        String output_stem = Files.getNameWithoutExtension(this.communicator.getGUI_reference());
        File f = new File(this.communicator.getGUI_reference());
        String absolutPath = f.getAbsolutePath();
        String filepath = absolutPath.substring(0,absolutPath.lastIndexOf(File.separator));
        this.parameters = new String[]{"picard", "CreateSequenceDictionary","R=", this.communicator.getGUI_reference(), "O=",
                                        filepath+"/"+output_stem+".dict"};
        this.outputfile = this.inputfile;

    }

    @Override
    public String getOutputfolder() {
        File f = new File(this.communicator.getGUI_reference());
        return f.getParent();
    }


}

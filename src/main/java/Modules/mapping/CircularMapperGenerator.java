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

package Modules.mapping;

import IO.Communicator;
import Modules.AModule;
import com.google.common.io.Files;

import java.io.File;

/**
 * Created by peltzer on 27.01.14.
 */
public class CircularMapperGenerator extends AModule {
    public CircularMapperGenerator(Communicator c) {
        super(c);
    }

    @Override
    public void setParameters() {
        String output_path = new File(this.communicator.getGUI_reference()).getAbsolutePath();
        String filePath = output_path.substring(0, output_path.lastIndexOf(File.separator));
        String extension = Files.getFileExtension(this.communicator.getGUI_reference());
        String output_stem = Files.getNameWithoutExtension(this.communicator.getGUI_reference());
        this.parameters = new String[]{"circulargenerator", "-e",  String.valueOf(this.communicator.getCM_elongationfac()),
                                       "-i", this.communicator.getGUI_reference(), "-s", this.communicator.getCM_tobemapped_against()
                                       };
        this.communicator.setCM_referencemt_elong(filePath+"/"+output_stem + "_" +
                                    this.communicator.getCM_elongationfac() + "." + extension);
        this.outputfile = this.inputfile;
    }

    @Override
    public String getOutputfolder() {
        return new File(this.communicator.getGUI_reference()).getParent();
    }


}

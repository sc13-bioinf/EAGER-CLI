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

import java.util.ArrayList;

/**
 * Created by peltzer on 17.07.14.
 */
public class Bowtie2 extends AModule {

    public Bowtie2(Communicator c) {
        super(c);
    }


    @Override
    public void setParameters() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        this.parameters = new String[]{"bowtie2", "-x", this.communicator.getGUI_reference(), "-U", this.inputfile.get(0), "-S",
                            getOutputfolder()+"/"+output_stem + ".bt2.sam", "--end-to-end", "--very-sensitive" ,
                            "-p", this.communicator.getCpucores(), this.communicator.getMapper_advanced()};
        this.outputfile = new ArrayList<String>();
        this.outputfile.add(getOutputfolder()+"/"+output_stem + ".bt2.sam");
    }

    @Override
    public String getOutputfolder() {
        return this.communicator.getGUI_resultspath() + "/3-Mapper";
    }

}

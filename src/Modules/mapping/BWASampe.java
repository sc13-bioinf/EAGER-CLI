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
 * Created by peltzer on 9/18/14.
 */
public class BWASampe extends AModule {



    public BWASampe(Communicator c) {
        super(c);
    }

    @Override
    public void setParameters() {
        String output_stem1 = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_stem2 = Files.getNameWithoutExtension(this.inputfile.get(1));

        this.parameters = new String[]{"bwa", "sampe", "-r", this.communicator.getMapper_readgroup(),
                                        this.communicator.getGUI_reference(),
                                        getOutputfolder()+"/"+output_stem1+".sai",
                                        getOutputfolder()+"/"+output_stem2+".sai",
                                        this.inputfile.get(0), this.inputfile.get(1),
                                        "-f", getOutputfolder()+"/"+output_stem1+".sam"};
        this.outputfile = new ArrayList<String>();
        this.outputfile.add(getOutputfolder()+"/"+output_stem1+".sam");

    }

    @Override
    public String getOutputfolder() {
        return this.communicator.getGUI_resultspath() + "/3-Mapper";
    }



}

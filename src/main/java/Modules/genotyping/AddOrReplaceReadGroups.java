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

package Modules.genotyping;

import IO.Communicator;
import Modules.AModule;
import com.google.common.io.Files;

import java.util.ArrayList;

/**
 * Created by peltzer on 17.07.14, used to add Readgroups if there is no ReadGroup available, e.g. after mapping with BT2 or Stampy.
 *
 */
public class AddOrReplaceReadGroups extends AModule {
    public AddOrReplaceReadGroups(Communicator c) {
        super(c);
    }

    @Override
    public void setParameters() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String fileout = getOutputfolder()+"/"+output_stem+".RG.bam";
        this.parameters = new String[]{"picard", "AddOrReplaceReadGroups", "I="+this.inputfile.get(0), "O="+fileout,
                                        "RGLB=lib", "RGPL=illumina", "RGPU=4410", "RGSM=Project", "VALIDATION_STRINGENCY=SILENT"};
        this.outputfile = new ArrayList<String>();
        outputfile.add(fileout);
    }

    @Override
    public String getOutputfolder() {
        return this.communicator.getGUI_resultspath() + "/4-Samtools";
    }


}

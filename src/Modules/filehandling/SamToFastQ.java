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
import java.util.ArrayList;

/**
 * Created by peltzer on 27.01.14.
 */
public class SamToFastQ extends AModule {
    public SamToFastQ(Communicator c) {
        super(c);
    }

    @Override
    public void setParameters() {

        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        if(this.communicator.isRun_mapping() && this.communicator.getMapper_to_use().equals("CircularMapper")){
            this.parameters = new String[]{"picard", "SamToFastq", "I=", this.communicator.getCM_remapped(), "F=", getOutputfolder()+"/"+output_stem+".remap.fq"};
            this.outputfile = new ArrayList<String>();
            outputfile.add(getOutputfolder()+"/"+output_stem+".remap.fq");
        } else {
            this.parameters = new String[]{"SamToFastq", "I=", this.getInputfile().get(0), "FASTQ=", getOutputfolder()+ "/"+output_stem+".remap.fq"};
            this.outputfile = new ArrayList<String>();
            outputfile.add(getOutputfolder()+ "/"+output_stem+".remap.fq");
        }

    }

    @Override
    public String getOutputfolder() {
        File file = new File(this.inputfile.get(0));
        String absolutePath = file.getAbsolutePath();
        String output_path = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));
        return output_path;
    }


}

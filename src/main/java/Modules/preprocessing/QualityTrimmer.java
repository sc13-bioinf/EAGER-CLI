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

package Modules.preprocessing;

import IO.Communicator;
import Modules.AModule;
import com.google.common.io.Files;

import java.util.ArrayList;

/**
 * Created by peltzer on 23.01.14.
 */
public class QualityTrimmer extends AModule {
    public QualityTrimmer(Communicator c) {
        super(c);
    }

    @Override
    public void setParameters() {
        this.parameters = new String[]{"/bin/sh","-c", addAllParamsUp()};

    }


    private String addAllParamsUp(){
        String output_path = getOutputfolder();
        String combiner = " && ";
        ArrayList<String> commands = new ArrayList<>();
        ArrayList<String> outputfiles = new ArrayList<>();


        for(int i = 0; i < this.inputfile.size(); i++){
            String input_file_path = this.inputfile.get(i);
            String output_stem = Files.getNameWithoutExtension(input_file_path);
            String output_file_path = output_path+"/"+output_stem+".qT"+this.communicator.getQuality_minreadquality()+".lT"+this.communicator.getQuality_readlength()+".fq.gz";
            if ( input_file_path.endsWith(".gz") ) {
                commands.add("zcat " + input_file_path + " | fastq_quality_trimmer -t " + String.valueOf(this.communicator.getQuality_minreadquality())+" -l "+String.valueOf(this.communicator.getQuality_readlength())
                    +" -Q33" + " -z -o "+output_file_path);
            } else {
                commands.add("fastq_quality_trimmer -t " + String.valueOf(this.communicator.getQuality_minreadquality())+" -l "+String.valueOf(this.communicator.getQuality_readlength())
                    +" -Q33" + " -i " + input_file_path + " -z -o "+output_file_path);
            }
            outputfiles.add(output_file_path);
        }
        this.outputfile = outputfiles;
        return String.join(combiner, commands);
    }

    @Override
    public String getOutputfolder() {
        return this.communicator.getGUI_resultspath()+"/2-QualityTrimming";
    }

}

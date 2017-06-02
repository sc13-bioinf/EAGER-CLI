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
 * Created by peltzer on 24.01.14.
 */
public class VCF2Genome extends AModule {
    public VCF2Genome(Communicator c) {
        super(c);
    }

    @Override
    public void setParameters() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = getOutputfolder();
        String filename = Files.getNameWithoutExtension(this.inputfile.get(0));

       // -in -ref -draft -refMod -uncertain -minq -minc -minfreq -draftname -h

        String vcf2genome = "vcf2genome " + "-in "+ this.inputfile.get(0) + " -ref " + this.communicator.getGUI_reference() + " -draft " + output_path+"/"+output_stem+".fasta "
                + "-refMod "+ output_path+"/"+output_stem+".refMod.fasta " + " -uncertain " + output_path+"/"+output_stem+".nr1234.fasta "+ "-minq " +
                String.valueOf(this.communicator.getVcf2draft_minquality()) + " -minc " +
                String.valueOf(this.communicator.getVcf2dmincov())+ " -minfreq " +
                String.valueOf(this.communicator.getVcf2dminsnpall())+ " -draftname " +
                String.valueOf(filename) +
                " > " + output_path+"/"+output_stem+".fasta.stats";
                this.parameters = new String[]{"/bin/sh", "-c", vcf2genome};
        this.outputfile = new ArrayList<String>();
        outputfile.add(output_path+"/"+output_stem+".fasta");


    }



    @Override
    public String getOutputfolder() {
        return this.communicator.getGUI_resultspath() + "/12-VCF2Genome";
    }

}

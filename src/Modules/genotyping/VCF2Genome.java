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

import java.io.File;
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
        String sample_name = new File(this.communicator.getGUI_resultspath()).getParentFile().getName();

        updateVCF2OutputName();
        String vcf2genome = "vcf2genome " + this.inputfile.get(0) + " " + this.communicator.getGUI_reference() + " " + output_path+"/"+output_stem+".fasta "
                + output_path+"/"+output_stem+".refMod.fasta " + output_path+"/"+output_stem+".nr1234.fasta "+
                String.valueOf(this.communicator.getVcf2draft_minquality()) + " " +
                String.valueOf(this.communicator.getVcf2dmincov())+ " " +
                String.valueOf(this.communicator.getVcf2dminsnpall())+ " " +
                String.valueOf(sample_name)+ " " +
                String.valueOf(this.communicator.getVcf2draft_advanced()) +
                " > " + output_path+"/"+output_stem+".fasta.stats";
                this.parameters = new String[]{"/bin/sh", "-c", vcf2genome};
        this.outputfile = new ArrayList<String>();
        outputfile.add(output_path+"/"+output_stem+".fasta");


    }

    private void updateVCF2OutputName() {
        communicator.setVcf2dname(communicator.getGUI_inputfiles().get(0));
    }

    @Override
    public String getOutputfolder() {
        return this.communicator.getGUI_resultspath() + "/12-VCF2Genome";
    }

}

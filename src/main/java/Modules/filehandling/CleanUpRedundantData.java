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

import java.util.ArrayList;
import java.io.File;

/**
 * Created by peltzer on 11/5/14.
 */
public class CleanUpRedundantData extends AModule {

        public CleanUpRedundantData(Communicator c){
            super(c);
            this.outputfile = new ArrayList<String>();
            this.outputfile.add(c.getGUI_resultspath());
            setParameters();
        }

    @Override
    public void setParameters () {};

    // Need to set parameters at runTime for this one
    @Override
    public String [] getParameters () {
        String combiner = "; ";
        //This is to remove sam files, as these are just uncompressed BAM files and therefore are not required in the pipeline!
        //Afterwards we run touch with the name "DUMMY.SAM"
        String remove_sam_data = "rm "+this.communicator.getGUI_resultspath()+"/3-Mapper/*.sam" + combiner;
        String remove_sam_sai =  "rm "+communicator.getGUI_resultspath()+"/3-Mapper/*.sai";

        String remove_bam_unsorted_data = combiner+"echo \"No bam dir found\"";

        String remove_unprefixed_fastq_files = combiner+"echo \"No fastq dir found\"";

        File d = new File(this.communicator.getGUI_resultspath()+"/4-Samtools");

        if ( d.isDirectory() ) {
            remove_bam_unsorted_data = "";
            for (File f : d.listFiles()) {
                if ( f.getName().endsWith(".bam") && !( f.getName().endsWith(".mappedonly.sorted.bam") || f.getName().endsWith(".mapped.sorted.bam") || f.getName().endsWith(".extractunmapped.bam")) || f.getName().endsWith(".qF.bam")) {
                    remove_bam_unsorted_data += combiner+"rm " + f.getPath() + " ";
                }
                else if ( f.getName().endsWith(".bai") && !( f.getName().endsWith(".mappedonly.sorted.bam.bai") || f.getName().endsWith(".mapped.sorted.bam.bai") || f.getName().endsWith(".extractunmapped.bam.bai") || f.getName().endsWith(".qF.bam.bai"))) {
                    remove_bam_unsorted_data += combiner + "rm " + f.getPath();
                }
            }
        }


        //RMdup stuff

        d = new File(this.communicator.getGUI_resultspath()+"/5-DeDup");

        if ( d.isDirectory() ) {
            for (File f : d.listFiles()) {
                if(f.getName().endsWith("_rmdup.bam") && !f.getName().endsWith("_rmdup.sorted.bam") && communicator.isRmdup_run()){
                    remove_bam_unsorted_data += combiner + "rm " + f.getPath();
                }
            }
        }

        remove_bam_unsorted_data += combiner;

        File fq = new File(this.communicator.getGUI_resultspath() + "/1-AdapClip/*.combined.fq.gz");
        remove_unprefixed_fastq_files = "";

        if ( communicator.getMerge_tool().equals("AdapterRemoval") && communicator.getMerge_type().equals("PAIRED")){
            remove_unprefixed_fastq_files += "rm " + fq + combiner;
        }

        return new String[] {
                "/bin/sh", "-c", remove_sam_data + remove_sam_sai  + remove_bam_unsorted_data + remove_unprefixed_fastq_files
        };
    }

        @Override
        public String getOutputfolder() {
            return this.getResultfolder();
        }





}

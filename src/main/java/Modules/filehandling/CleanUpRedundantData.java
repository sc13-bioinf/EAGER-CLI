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
        String combiner = " && ";
        //This is to remove sam files, as these are just uncompressed BAM files and therefore are not required in the pipeline!
        //Afterwards we run touch with the name "DUMMY.SAM"
        String remove_sam_data = "rm "+this.communicator.getGUI_resultspath()+"/3-Mapper/*.sam";

        String remove_bam_unsorted_data = "echo 'No bam dir found'";

        File f = new File(this.communicator.getGUI_resultspath());

        if ( f.isDirectory() ) {
            remove_bam_unsorted_data = "";
            for (File file : f.listFiles()) {
               remove_bam_unsorted_data += "rm " + file.getPath() + " ";
            }
        }

        return new String[] {
                "/bin/sh", "-c", remove_sam_data+combiner+remove_bam_unsorted_data
        };
    }

        @Override
        public String getOutputfolder() {
            return this.getResultfolder();
        }





}

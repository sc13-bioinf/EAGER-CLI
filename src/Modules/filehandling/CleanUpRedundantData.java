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
        public void setParameters() {

            //This is to remove sam files, as these are just uncompressed BAM files and therefore are not required in the pipeline!
            //Afterwards we run touch with the name "DUMMY.SAM"
            String remove_sam_data = "rm "+this.communicator.getGUI_resultspath()+"/3-Mapper/*.sam";

            //See above, we combine these command using the shell && command, combine a single string out of them and run these calling bash
            //This removes redundant data and touches the files again, so the other tools will even not be run again if this is required in the pipeline!
            String remove_bam_unsorted_data = "rm "+this.communicator.getGUI_resultspath()+"/4-Samtools/*[!sorted].bam";


            String combiner = " && ";

            this.parameters = new String[]{
                    "/bin/sh", "-c", remove_sam_data+combiner+remove_bam_unsorted_data};
        }

        @Override
        public String getOutputfolder() {
            return this.getResultfolder();
        }





}

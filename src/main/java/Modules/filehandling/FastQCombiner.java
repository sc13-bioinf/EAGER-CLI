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

import java.util.ArrayList;

/**
 * Created by peltzer on 1/8/15.
 */
public class FastQCombiner extends AModule {

    public FastQCombiner(Communicator c){
        super(c);
    }

    @Override
    public void setParameters() {
        //Only works if equally laneX,laneY x R1 = laneX,laneY x R2!!
        ArrayList<String> combiner = new ArrayList<String>();
        combiner.add("LaneMerger");
        combiner.addAll(this.communicator.getGUI_inputfiles());
        combiner.add(this.communicator.getGUI_resultspath());
        this.parameters = combiner.toArray(new String[combiner.size()]);
        this.outputfile = new ArrayList<>();
        String filename = Files.getNameWithoutExtension(this.communicator.getGUI_inputfiles().get(0));

        outputfile.add(this.communicator.getGUI_resultspath() + "/" +filename + "_mergedLanes_R1.fq.gz");
        outputfile.add(this.communicator.getGUI_resultspath() + "/" +filename + "_mergedLanes_R2.fq.gz");
    }

    @Override
    public String getOutputfolder() {
        return this.communicator.getGUI_resultspath();
    }


}
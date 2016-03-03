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

/**
 * Created by peltzer on 01/12/15.
 */
public class BAMListCreator extends AModule {
    public BAMListCreator(Communicator c) {
        super(c);
    }

    @Override
    public void setParameters() {
        //We don't need to change this at all
        this.outputfile = this.inputfile;

        this.parameters = new String[]{"/bin/sh", "-c", returnlist()};

    }

    @Override
    public String getOutputfolder() {
        return this.communicator.getGUI_resultspath()+"/10-GATKGenotyper";
    }

    private String returnlist(){
        String output = "echo "+"\"";
        String end = " > " + this.getOutputfolder()+"/bam_list.txt";
        for(int i = 0; i < this.inputfile.size(); i++){
            String file = this.inputfile.get(i);
                output+= file;
            }

        output+="\""+end;
        return output;
    }
}

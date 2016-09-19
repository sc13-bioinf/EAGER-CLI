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

package Modules.mapping;

import IO.Communicator;
import Modules.AModule;
import com.google.common.io.Files;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by peltzer on 24.01.14.
 */
public class DeDup extends AModule {

    public DeDup(Communicator c) {
        super(c);
    }

    @Override
    public void setParameters() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        if ( this.communicator.isRmdup_allReadsAsMerged() ) {
            this.parameters = new String[]{"dedup", "-i", this.inputfile.get(0), "-m", "-o", getOutputfolder()};
        } else {
            this.parameters = new String[]{"dedup", "-i", this.inputfile.get(0), "-o", getOutputfolder()};
        }
        this.outputfile = new ArrayList<String>();
        this.outputfile.add(getOutputfolder()+"/"+output_stem+"_rmdup.bam");
    }

    @Override
    public void setProcessEnvironment (Map <String, String> env) {
        if ( !this.communicator.isUsesystemtmpdir() ) {
          AModule.setEnvironmentForParameterPrepend (env,
                                                     " ",
                                                     "JAVA_TOOL_OPTIONS",
                                                     "-Djava.io.tmpdir=" + getOutputfolder() + System.getProperty ("file.separator") + ".tmp");
        }
    }

    @Override
    public String getOutputfolder() {
        return this.communicator.getGUI_resultspath() + "/5-DeDup";
    }


}

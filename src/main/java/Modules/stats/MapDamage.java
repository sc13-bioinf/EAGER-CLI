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

package Modules.stats;

import IO.Communicator;
import Modules.AModule;
import com.google.common.io.Files;
import java.util.Map;

/**
 * Created by peltzer on 24.01.14.
 */
public class MapDamage extends AModule {
    public MapDamage(Communicator c) {
        super(c);
    }

    @Override
    public void setProcessEnvironment (Map <String, String> env) {
        if ( !this.communicator.isUsesystemtmpdir() ) {
          AModule.setEnvironmentForParameterReplace (env,
                                                     "TMPDIR",
                                                     getOutputfolder() + System.getProperty ("file.separator") + ".tmp");
        }
    }

    @Override
    public void setParameters() {
        String output_stem = Files.getNameWithoutExtension(this.getInputfile().get(0));
        String output_path = getOutputfolder();
        this.parameters = new String[]{"mapDamage", "-i", this.getInputfile().get(0), "-r", this.communicator.getGUI_reference(),
                                        "-l", this.communicator.getMapdamage_length(),
                                        "-d", output_path+"/"+output_stem,"--merge-reference-sequences",
                                         this.communicator.getMapdamage_advanced()};
        this.outputfile = this.inputfile;
    }

    @Override
    public String getOutputfolder() {
        return this.communicator.getGUI_resultspath() + "/7-DnaDamage";
    }


}

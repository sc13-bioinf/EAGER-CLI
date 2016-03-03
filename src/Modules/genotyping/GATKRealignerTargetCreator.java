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

/**
 * Created by peltzer on 24.01.14.
 */
public class GATKRealignerTargetCreator extends AModule {
    public GATKRealignerTargetCreator(Communicator c) {
        super(c);
    }

    @Override
    public void setParameters() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = getOutputfolder();
        if (this.communicator.getGUI_GATKSNPreference() == null) {
            if (this.communicator.isRun_mt_capture_mode()) {
                this.parameters = new String[]{"gatk", "-T", "RealignerTargetCreator", "-R", this.communicator.getGUI_reference(),
                        "-I", this.inputfile.get(0), "-nt", this.communicator.getCpucores(), "-L", this.communicator.getFilter_for_mt(),
                        "-o", output_path + "/" + output_stem + ".intervals"};
            } else {
                this.parameters = new String[]{"gatk", "-T", "RealignerTargetCreator", "-R", this.communicator.getGUI_reference(),
                        "-I", this.inputfile.get(0), "-nt", this.communicator.getCpucores(),
                        "-o", output_path + "/" + output_stem + ".intervals"};
            }

        } else {
            if (this.communicator.isRun_mt_capture_mode()) {
                this.parameters = new String[]{"gatk", "-T", "RealignerTargetCreator", "-R", this.communicator.getGUI_reference(),
                        "-I", this.inputfile.get(0), "-nt", this.communicator.getCpucores(),  "-L", this.communicator.getFilter_for_mt(),
                        "-o", output_path + "/" + output_stem + ".intervals",
                        "-known", this.communicator.getGUI_GATKSNPreference()};
            } else {
                this.parameters = new String[]{"gatk", "-T", "RealignerTargetCreator", "-R", this.communicator.getGUI_reference(),
                        "-I", this.inputfile.get(0), "-nt", this.communicator.getCpucores(),
                        "-o", output_path + "/" + output_stem + ".intervals",
                        "-known", this.communicator.getGUI_GATKSNPreference()};
            }

        }
        this.outputfile = this.inputfile;
    }

    @Override
    public String getOutputfolder() {
        return this.communicator.getGUI_resultspath() + "/9-GATKBasics";
    }

}

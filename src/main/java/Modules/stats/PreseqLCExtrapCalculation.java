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

/**
 * Created by peltzer on 24.01.14.
 */
public class PreseqLCExtrapCalculation extends AModule {
    private int currentConfiguration = DEFAULT;
    public static final int DEFAULT = 0;
    public static final int QUICKMODE = 1;
    public static final int RUN_ON_HIST = 2;

    public PreseqLCExtrapCalculation(Communicator c) {
        super(c);
    }

    public PreseqLCExtrapCalculation(Communicator c, int configuration) {
        super(c);
        this.currentConfiguration = configuration;
    }

    @Override
    public void setParameters() {

        switch (currentConfiguration) {
            case DEFAULT:
                this.parameters = getDefaultParameters();
                this.outputfile = this.inputfile;
                break;

            case QUICKMODE:
                this.parameters = getQuickParameters();
                this.outputfile = this.inputfile;
                break;

            case RUN_ON_HIST:
                this.parameters = getHistParameters();
                this.outputfile = this.inputfile;
                break;
        }

    }

    private String[] getQuickParameters() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        return new String[]{"preseq","lc_extrap", "-s", String.valueOf(this.communicator.getPreseq_lcextrap_stepsize()), "-o",
                getOutputfolder() + "/" + output_stem + ".lcextrap", "-B", this.inputfile.get(0),
                "-e", this.communicator.getPreseq_lcextrap_extrapolationsize(),
                "-Q"
        };
    }

    private String[] getDefaultParameters() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        return new String[]{"preseq", "lc_extrap", "-s", String.valueOf(this.communicator.getPreseq_lcextrap_stepsize()), "-o",
                getOutputfolder() + "/" + output_stem + ".lcextrap", "-B", this.inputfile.get(0),
                "-b", String.valueOf(this.communicator.getPreseq_lcextrap_bootstraps()),
                "-e", this.communicator.getPreseq_lcextrap_extrapolationsize()
        };
    }

    private String[] getHistParameters() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        return new String[]{"preseq", "lc_extrap", "-s", String.valueOf(this.communicator.getPreseq_lcextrap_stepsize()), "-o",
                getOutputfolder() + "/" + output_stem + ".lcextrap", "-H", this.inputfile.get(0).replace("_rmdup.sorted.bam", ".hist"),
                "-e", this.communicator.getPreseq_lcextrap_extrapolationsize(), "-Q"
        };
    }

    @Override
    public String getOutputfolder() {
        return this.communicator.getGUI_resultspath() + "/8-Preseq";
    }


}

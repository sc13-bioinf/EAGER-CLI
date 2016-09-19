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

import java.util.ArrayList;

/**
 * Created by peltzer on 24.01.14.
 */
public class QualiMap extends AModule {
    public static final int DEFAULT = 0;
    public static final int CAPTURE = 1;
    private int currentConf = DEFAULT;
    public QualiMap(Communicator c) {
        super(c);
    }

    public QualiMap(Communicator c, int currentconf){
        super(c);
        this.currentConf = currentconf;
    }

    @Override
    public void setParameters() {
        switch(currentConf) {
            case DEFAULT:
                getDefaultParameters();
                break;
            case CAPTURE:
                getCaptureParameters();
                break;
        }
    }

    private void getDefaultParameters(){
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = getOutputfolder();
        this.parameters = new String[]{"qualimap", "bamqc", "-bam", this.inputfile.get(0), "-nt",
                String.valueOf(this.communicator.getCpucores()),
                "-outdir", output_path+"/"+output_stem, "-outformat", "HTML",
                "--java-mem-size="+this.communicator.getMaxmemory()+"G"};
        this.outputfile = this.inputfile;
    }

    private void getCaptureParameters(){
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = getOutputfolder();
        if(this.communicator.getSnpcapture_type().equals("390K")){
            this.parameters = new String[]{"qualimap", "bamqc", "-bam", this.inputfile.get(0), "-nt",
                    String.valueOf(this.communicator.getCpucores()),
                    "-outdir", output_path+"/"+output_stem, "-outformat", "HTML",
                    "--java-mem-size="+this.communicator.getMaxmemory()+"G", "-gff", this.communicator.getBedfile()};
        } else {
            this.parameters = new String[]{"qualimap", "bamqc", "-bam", this.inputfile.get(0), "-nt",
                    String.valueOf(this.communicator.getCpucores()),
                    "-outdir", output_path + "/" + output_stem, "-outformat", "HTML",
                    "--java-mem-size=" + this.communicator.getMaxmemory() + "G", "-gff", this.communicator.getBedfile()};
        }

        this.outputfile = this.inputfile;
    }

    @Override
    public String getOutputfolder() {
        return this.communicator.getGUI_resultspath() + "/6-QualiMap";
    }

    private String getSubModuleName() {
        switch (currentConf){
            case DEFAULT:
                return "Default";

            case CAPTURE:
                return "CAPTUREMODE";

            default: return " Default";
        }
    }

    @Override
    public String getModulename(){
        return super.getModulename() + getSubModuleName() ;
    }

}

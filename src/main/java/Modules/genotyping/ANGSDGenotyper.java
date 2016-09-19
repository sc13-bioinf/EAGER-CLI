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
 * Created by peltzer on 01/12/15.
 */
public class ANGSDGenotyper extends AModule{
    public static final int DEFAULT = 0;
    public static final int WITHFASTA = 1;
    public static final int WITHFASTACOUNTS = 2;
    private int currentConfiguration = DEFAULT;


    public ANGSDGenotyper(Communicator c) {
        super(c);
    }

    public ANGSDGenotyper(Communicator c, int conf){
        super(c);
        this.currentConfiguration = conf;
    }

    @Override
    public void setParameters() {

        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        this.outputfile = this.inputfile;

        switch(currentConfiguration){
            case DEFAULT:
                this.parameters= new String[]{"angsd", "-bam", getOutputfolder()+"/bam_list.txt",
                        "-GL", this.communicator.getAngsd_glf_model(),
                        "-out", getOutputfolder()+"/"+output_stem+".glf", "-doGLF", this.communicator.getAngsd_glm_outformat()};
                break;
            case WITHFASTA:
                this.parameters= new String[]{"angsd", "-bam", getOutputfolder()+"/bam_list.txt",
                        "-GL", this.communicator.getAngsd_glf_model(),
                        "-out", getOutputfolder()+"/"+output_stem+".glf", "-doGLF", this.communicator.getAngsd_glm_outformat(),
                        "-doFasta", this.communicator.getAngsd_fasta_callmethod()};
                break;
            case WITHFASTACOUNTS:
                this.parameters= new String[]{"angsd", "-bam", getOutputfolder()+"/bam_list.txt",
                        "-GL", this.communicator.getAngsd_glf_model(),
                        "-out", getOutputfolder()+"/"+output_stem+".glf", "-doGLF", this.communicator.getAngsd_glm_outformat(),
                        "-doFasta", this.communicator.getAngsd_fasta_callmethod(), "-doCounts", "1"};
                break;
        }

    }

    @Override
    public String getOutputfolder() {
        return this.communicator.getGUI_resultspath()+"/10-GATKGenotyper";
    }

    @Override
    public String getModulename(){
        return super.getModulename() + getSubModuleName();
    }


    private String getSubModuleName(){
        switch(currentConfiguration){
            case DEFAULT:
                return "default";
            case WITHFASTA:
                return "Fasta";
            case WITHFASTACOUNTS:
                return "FastaMajorityVote";
            default: return "default";
        }
    }
}

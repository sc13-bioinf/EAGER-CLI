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
 * Created by peltzer on 24.01.14.
 */
public class SamtoolsView extends AModule {
    public static final int DEFAULT = 0;
    private int currentConfiguration = DEFAULT;
    public static final int ONLYUNMAPPED = 1;
    public static final int ONLYMAPPED = 2;
    public static final int ONLYMAPPEDSAM = 3;
    public static final int HYBRID = 4;
    public static final int EXTRACTMAPPED = 7;
    public static final int EXTRACTUNMAPPED = 8;
    public static final int FILTERED = 9;
    public static final int ONLYMAPPEDFILTERED = 10;

    public SamtoolsView(Communicator c){
        super(c);
    }

    public SamtoolsView(Communicator c, int config){
        super(c);
        this.currentConfiguration = config;
    }

    @Override
    public void setParameters() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = getOutputfolder();

        this.outputfile = new ArrayList<String>();

        switch (currentConfiguration){
            case DEFAULT: this.parameters = getDefaultParameterList();
                this.outputfile.add(output_path+"/"+output_stem+".bam");
                break;
            case ONLYUNMAPPED: this.parameters = getOnlyUnmapped();
                this.outputfile.add(output_path+"/"+output_stem+".unmapped.bam");
                break;
            case ONLYMAPPED: this.parameters = getOnlyMapped();
                this.outputfile.add(output_path+"/"+output_stem+".mappedonly.bam");
                break;
            case ONLYMAPPEDFILTERED: this.parameters = getOnlyMappedFiltered();
                this.outputfile.add(output_path+"/"+output_stem+".mappedonlyqF.bam");
                break;
            case ONLYMAPPEDSAM: this.parameters = getOnlyMappedSAM();
                this.outputfile.add(output_path+"/"+output_stem+".mappedsamonly.sam");
                break;
            case HYBRID: this.parameters = getHybridSAM();
                this.outputfile.add(output_path+"/"+output_stem+".hybridmap.bam");
                break;
            case EXTRACTMAPPED : this.parameters = getExtractMappedParams();
                this.outputfile = this.inputfile;
                break;
            case EXTRACTUNMAPPED : this.parameters = getExtractUnmappedParams();
                this.outputfile = this.inputfile;
                break;
            case FILTERED: this.parameters = getQualityFiltered();
                this.outputfile.add(output_path+ "/"+ output_stem +".qF.bam");
                break;

        }

    }


    private String[] getExtractUnmappedParams() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = this.communicator.getGUI_resultspath() + "/4-Samtools";
        return new String[]{"samtools", "view", "-@", String.valueOf(this.communicator.getCpucores()),
                "-f4", "-q", this.communicator.getMapper_mapquality_filter(),"-b", this.inputfile.get(0), "-o", output_path+ "/"+ output_stem +".extractunmapped.bam"};
    }

    private String[] getExtractMappedParams() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = this.communicator.getGUI_resultspath() + "/4-Samtools";
        return new String[]{"samtools", "view", "-@", String.valueOf(this.communicator.getCpucores()),
                "-F4", "-q", this.communicator.getMapper_mapquality_filter(), "-b", this.inputfile.get(0), "-o", output_path+ "/"+ output_stem +".extractmapped.bam"};
    }



    private String[] getOnlyMappedSAM() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = this.communicator.getGUI_resultspath() + "/4-Samtools";
        return new String[]{"samtools", "view", "-@", String.valueOf(this.communicator.getCpucores()),
                "-F4", "-q", this.communicator.getMapper_mapquality_filter(),"-bS", this.inputfile.get(0), "-o", output_path+ "/"+ output_stem +".mappedsamonly.sam"};

    }

    private String[] getOnlyMapped() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = this.communicator.getGUI_resultspath() + "/4-Samtools";
        return new String[]{"samtools", "view", "-F4", "-b", this.inputfile.get(0), "-o", output_path+ "/"+ output_stem +".mappedonly.bam"};
    }

    private String[] getOnlyMappedFiltered() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = this.communicator.getGUI_resultspath() + "/4-Samtools";
        return new String[]{"samtools", "view", "-F4", "-q", this.communicator.getMapper_mapquality_filter(),"-b", this.inputfile.get(0), "-o", output_path+ "/"+ output_stem +".mappedonlyqF.bam"};
    }

    private String[] getOnlyUnmapped() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = this.communicator.getGUI_resultspath() + "/4-Samtools";
        return new String[]{"samtools", "view", "-f4", "-q", this.communicator.getMapper_mapquality_filter(),"-b", this.inputfile.get(0), "-o", output_path+ "/"+ output_stem +".unmapped.bam"};    }

    private String[] getQualityFiltered() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = this.communicator.getGUI_resultspath() + "/4-Samtools";
        return new String[]{"samtools", "view", "-@", String.valueOf(this.communicator.getCpucores()),
                "-q", this.communicator.getMapper_mapquality_filter(),"-b", this.inputfile.get(0), "-o", output_path+ "/"+ output_stem +".qF.bam"};
    }

    private String[] getDefaultParameterList() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = this.communicator.getGUI_resultspath() + "/4-Samtools";
        return new String[]{"samtools", "view", "-@", String.valueOf(this.communicator.getCpucores()),
                "-bS", this.inputfile.get(0), "-o", output_path+ "/"+ output_stem +".bam"};
    }

    private String[] getHybridSAM() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = this.communicator.getGUI_resultspath() + "/4-Samtools";
        return new String[]{"samtools", "view", "-@", String.valueOf(this.communicator.getCpucores()),
                "-q", this.communicator.getMapper_mapquality_filter(),"-bS", this.inputfile.get(0), "-o", output_path+ "/"+ output_stem +".hybridmap.bam"};

    }

    @Override
    public String getModulename(){
      return super.getModulename() + getSubModuleName();
    }

    private String getSubModuleName() {
        switch (currentConfiguration){
            case DEFAULT:
                return "default";
            case FILTERED:
                return "filtered";
            case ONLYUNMAPPED:
                return "OnlyUnmapped";
            case ONLYMAPPED:
                return "OnlyMapped";
            case ONLYMAPPEDFILTERED:
                return "OnlyMappedFiltered";
            case ONLYMAPPEDSAM:
                return "OnlyMappedSAM";
            case HYBRID:
                return "Hybrid";
            case EXTRACTMAPPED:
                return "Extract Mapped Reads";
            case EXTRACTUNMAPPED:
                return "Extract Unmapped Reads";
            default: return "default";
        }
    }

    @Override
    public String getOutputfolder() {
        return this.communicator.getGUI_resultspath() + "/4-Samtools";
    }


}

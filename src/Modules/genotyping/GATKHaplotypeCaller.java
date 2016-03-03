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

import java.util.ArrayList;

/**
 * Created by peltzer on 24.01.14.
 */
public class GATKHaplotypeCaller extends AModule {
    public static final int DEFAULT = 0;
    private int currentConfiguration = DEFAULT;
    public static final int EMIT_ALL_SITES = 1;
    public static final int EMIT_CONF_SITES = 2;
    public static final int EMIT_ALL_WITHDBSNP = 3;
    public static final int EMIT_CONF_WITHDBSNP = 4;

    public GATKHaplotypeCaller(Communicator c) {
        super(c);
    }

    public GATKHaplotypeCaller(Communicator c, int currentConfiguration) {
        super(c);
        this.currentConfiguration = currentConfiguration;
    }

    @Override
    public void setParameters() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = getOutputfolder();

        this.outputfile = new ArrayList<String>();
        this.outputfile.add(output_path + "/" + output_stem + ".haplotyper.vcf");

        switch (currentConfiguration) {
            case DEFAULT:
                this.parameters = getDefaultParameterList();
                getTargetInformation();
                break;
            case EMIT_ALL_SITES:
                this.parameters = getEmitAllwithoutDBSNP();
                getTargetInformation();
                break;
            case EMIT_CONF_SITES:
                this.parameters = getEmitConfwithoutDBSNP();
                getTargetInformation();
                break;
            case EMIT_ALL_WITHDBSNP:
                this.parameters = getEmitAllwithDBSNP();
                getTargetInformation();
                break;
            case EMIT_CONF_WITHDBSNP:
                this.parameters = getEmitConfwithDBSNP();
                getTargetInformation();
                break;
        }
    }


    private String[] getDefaultParameterList() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = getOutputfolder();
        return new String[]{"gatk", "-T", "HaplotypeCaller", "-R", this.communicator.getGUI_reference(),
                "-I", this.inputfile.get(0),
                "-o", output_path + "/" + output_stem + ".haplotyper.vcf",
                "-nct", String.valueOf(this.communicator.getCpucores()),
                "-stand_call_conf", this.communicator.getGatk_standard_call_confidence(),
                "-stand_emit_conf", this.communicator.getGatk_standard_emit_confidence(),
                this.communicator.getGatk_snp_advanced()};
    }

    private String[] getEmitAllwithoutDBSNP() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = getOutputfolder();
        return new String[]{"gatk", "-T", "HaplotypeCaller", "-R", this.communicator.getGUI_reference(),
                "-I", this.inputfile.get(0),
                "-o", output_path + "/" + output_stem + ".haplotyper.vcf",
                "-nct", String.valueOf(this.communicator.getCpucores()),
                "-stand_call_conf", this.communicator.getGatk_standard_call_confidence(),
                "-stand_emit_conf", this.communicator.getGatk_standard_emit_confidence(),
                "--output_Mode EMIT_ALL_SITES",
                this.communicator.getGatk_snp_advanced()};
    }

    private String[] getEmitConfwithoutDBSNP() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = getOutputfolder();
        return new String[]{"gatk", "-T", "HaplotypeCaller", "-R", this.communicator.getGUI_reference(),
                "-I", this.inputfile.get(0),
                "-o", output_path + "/" + output_stem + ".haplotyper.vcf",
                "-nct", String.valueOf(this.communicator.getCpucores()),
                "-stand_call_conf", this.communicator.getGatk_standard_call_confidence(),
                "-stand_emit_conf", this.communicator.getGatk_standard_emit_confidence(),
                "--output_Mode EMIT_CONFIDENT_SITES",
                this.communicator.getGatk_snp_advanced()};
    }

    private String[] getEmitAllwithDBSNP() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = getOutputfolder();
        return new String[]{"gatk", "-T", "HaplotypeCaller", "-R", this.communicator.getGUI_reference(),
                "-I", this.inputfile.get(0), "--dbsnp", this.communicator.getGUI_GATKSNPreference(),
                "-o", output_path + "/" + output_stem + ".haplotyper.vcf",
                "-nct", this.communicator.getCpucores(),
                "-stand_call_conf", this.communicator.getGatk_standard_call_confidence(),
                "-stand_emit_conf", this.communicator.getGatk_standard_emit_confidence(),
                "--output_Mode EMIT_ALL_SITES",
                this.communicator.getGatk_snp_advanced()};
    }

    private String[] getEmitConfwithDBSNP() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = getOutputfolder();
        return new String[]{"gatk", "-T", "HaplotypeCaller", "-R", this.communicator.getGUI_reference(),
                "-I", this.inputfile.get(0), "--dbsnp", this.communicator.getGUI_GATKSNPreference(),
                "-o", output_path + "/" + output_stem + ".haplotyper.vcf",
                "-nct", this.communicator.getCpucores(),
                "-stand_call_conf", this.communicator.getGatk_standard_call_confidence(),
                "-stand_emit_conf", this.communicator.getGatk_standard_emit_confidence(),
                "--output_Mode EMIT_CONFIDENT_SITES",
                this.communicator.getGatk_snp_advanced()};
    }

    @Override
    public String getOutputfolder() {
        return this.communicator.getGUI_resultspath() + "/10-GATKGenotyper";
    }



    private void getTargetInformation(){
        if(this.communicator.isRun_mt_capture_mode()){
            ArrayList<String> tmp = new ArrayList<String>();

            for(String s : this.getParameters()){
                tmp.add(s);
            }
            tmp.add("-L");
            tmp.add(this.communicator.getFilter_for_mt());
            this.parameters = (String[]) tmp.toArray();
        }
        //no else required, we ain't do nothing in the other case.
    }


}

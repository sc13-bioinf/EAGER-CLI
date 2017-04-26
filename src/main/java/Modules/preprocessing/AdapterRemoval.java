package Modules.preprocessing;

import IO.Communicator;
import Modules.AModule;
import com.google.common.io.Files;

import java.util.ArrayList;

/**
 * Created by peltzer on 19.04.17.
 */
public class AdapterRemoval extends AModule {

    public static final int DEFAULT = 0;
    private int currentConfiguration = DEFAULT;
    public static final int ADAPTER_CLIPPING_ONLY = 1;
    public static final int SINGLE_ENDED_ONLY = 2;

    public AdapterRemoval(Communicator c) { super(c);}

    public AdapterRemoval(Communicator c, int config){
        super(c);
        this.currentConfiguration = config;
    }


    @Override
    public void setParameters() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        this.outputfile = new ArrayList<String>();
        switch (currentConfiguration){
            case DEFAULT: this.parameters = getDefaultParameterList();
                break;
            case ADAPTER_CLIPPING_ONLY: this.parameters = getAdapterClippingOnlyParameterList();
                break;
            case SINGLE_ENDED_ONLY: this.parameters = getSingleEndedOnlyParameterList();
                break;
        }
    }


    private String[] getDefaultParameterList(){
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        this.outputfile.add(getOutputfolder()+output_stem+".paired.truncated.gz");

        return new String[]{"AdapterRemoval", "--file1", getForwards(), "--file2", getReverse(), "--basename", getOutputfolder()+ output_stem, "--gzip",
                "--threads", this.communicator.getCpucores(), "--trimns", "--trimqualities",
                "--adapter1", this.communicator.getMerge_fwadaptor(), "--adapter2", this.communicator.getMerge_bwadaptor(),
                "--minlength", String.valueOf(this.communicator.getQuality_readlength()), "--minquality", String.valueOf(this.communicator.getQuality_minreadquality()),
        "--collapse", "--combined-output", "--interleaved-output"};
    }

    private String[] getAdapterClippingOnlyParameterList(){
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));

        this.outputfile.add(getOutputfolder()+output_stem+".pair1.truncated.gz");
        this.outputfile.add(getOutputfolder()+output_stem+".pair2.truncated.gz");

        return new String[]{"AdapterRemoval", "--file1", getForwards(), "--file2", getReverse(), "--basename", getOutputfolder()+ output_stem, "--gzip", "--threads", this.communicator.getCpucores(),
        "--trimns", "--trimqualities", "--adapter1", this.communicator.getMerge_fwadaptor(), "--adapter2", this.communicator.getMerge_bwadaptor(),
        "--minlength", String.valueOf(this.communicator.getQuality_readlength()), "--minquality", String.valueOf(this.communicator.getQuality_minreadquality()), "--combined-output"};

    }

    private String[] getSingleEndedOnlyParameterList() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        this.outputfile.add(getOutputfolder()+output_stem+".truncated.gz");


        return new String[]{"AdapterRemoval", "--file1", getAll(), "--basename", getOutputfolder()+ output_stem, "--gzip", "--threads", this.communicator.getCpucores(),
                "--trimns", "--trimqualities", "--adapter1", this.communicator.getMerge_fwadaptor(), "--adapter2", this.communicator.getMerge_bwadaptor(),
                "--minlength", String.valueOf(this.communicator.getQuality_readlength()), "--minquality", String.valueOf(this.communicator.getQuality_minreadquality())};

    }


    @Override
    public String getOutputfolder() {
        return  this.communicator.getGUI_resultspath()+"/1-ClipAndMerge/";
    }

    @Override
    public String getModulename(){
        return super.getModulename() + getSubModuleName();
    }

    public String getSubModuleName(){
        switch(currentConfiguration){
            case DEFAULT: return "default";
            case ADAPTER_CLIPPING_ONLY: return "adapterclippingonly";
            case SINGLE_ENDED_ONLY: return "singleendedonly";
            default: return "default";
        }

    }




    private String getForwards(){

        String out = "";
        for(String s : this.communicator.getGUI_inputfiles()){
            if(s.contains("R1_") || s.contains("R1.")){
                out+= s + " ";
            }
        }
        return out.trim(); //remove trailing space
    }

    private String getReverse(){
        String out = "";
        for(String s : this.communicator.getGUI_inputfiles()){
            if(s.contains("R2_") || s.contains("R2.")){
                out+= s + " ";
            }
        }
        return out.trim(); //remove trailing space
    }

    private String getAll(){
        String out = "";
        for (String s : this.communicator.getGUI_inputfiles()){
            out+= s + " ";
        }
        return out.trim();
    }


}

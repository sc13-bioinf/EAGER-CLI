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

package Runner;

import Modules.AModule;
import exceptions.ModuleFailedException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Created by peltzer on 22.01.14.
 * This Class is used to run abstract Modules using a ProcessBuilder Java IO Framework, parsing their
 * parameters provided by the corresponding module.
 */
public class ModuleRunner{
    private String[] parameters;
    private int returnCode = 0;

    //Constructor of Class
    public ModuleRunner(AModule module) throws IOException, InterruptedException, ModuleFailedException {
        this.parameters = module.getParameters();
        if(!module.hasbeenExecuted()) {
            run(module.getResultfolder(),module);
            if(returnCode == 0){
                runDependencyChecker(module.getOutputfolder(),module);
            } else {
                throw new ModuleFailedException("Module " + module.getModulename() + " failed in execution. Check Logfile for details.");
                //Dont write DONE file if not succesfully terminated!
            }
        }
    }

    public void run(String outputpath, AModule module) throws IOException, InterruptedException {
        FileWriter fw = new FileWriter(new File(outputpath+"/EAGER.log"), true);
        BufferedWriter bfw = new BufferedWriter(fw);
        long currtime_prior_execution = System.currentTimeMillis();
        ProcessBuilder processBuilder = new ProcessBuilder(this.parameters);
        Map<String, String> env = processBuilder.environment();
        module.setProcessEnvironment (env);
        processBuilder.redirectError(ProcessBuilder.Redirect.appendTo(new File(outputpath + "/EAGER.log")));

        Process process = processBuilder.start();

        if((returnCode = process.waitFor()) == 0) { //Exit Value should be zero = normal
            long currtime_post_execution = System.currentTimeMillis();
            long diff = currtime_post_execution - currtime_prior_execution;
            long runtime_s = diff / 1000;
            if (runtime_s > 60) {
                long minutes = runtime_s / 60;
                long seconds = runtime_s % 60;
                String outputText = "# Runtime of Module was: " + minutes + " minutes, and " + seconds + " seconds.";
                System.out.println(outputText);
                bfw.write(outputText + "\n");
                bfw.flush();

            } else {
                String outputText = "# Runtime of Module was: " + runtime_s + " seconds.";
                System.out.println(outputText);
                bfw.write(outputText + "\n");
                bfw.flush();
            }
            bfw.close();
        } else { //Exit Value is not zero
            String failText = "# The Module " + module.getModulename() + " failed in execution. Check what happened in the logfile.";
            process.destroy(); //We fail then
            System.out.println(failText);
            bfw.write(failText);
            bfw.flush();
            bfw.close();
        }
    }

    public void runDependencyChecker(String outputpath, AModule module) throws InterruptedException, IOException {
        String[] createDoneParameters = new String[]{"touch",module.getOutputfolder()+"/"+"DONE."+module.getModulename()};
        ProcessBuilder processBuilder = new ProcessBuilder(createDoneParameters);
        Process process = processBuilder.start();
        process.waitFor();
    }


}

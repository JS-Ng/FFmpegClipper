package ffmpeg.job;

import ffmpeg.util.ArrayUtils;

import java.io.IOException;
import java.io.InputStream;

// this is a job
public class CommandLineJob extends Job<InputStream>{
    private String[] command = {"bash", "-c"};
    private Process executedR;
    public CommandLineJob(String... command) {
        if (command.length >= 2) {
            if (!command[0].equals("bash") || !command[1].equals("-c")) {
                this.command = ArrayUtils.concat(this.command, command);
            }

            else {
                this.command = command;
            }
        }
        else {
            this.command = ArrayUtils.concat(this.command, command);
        }
    }
    public CommandLineJob runJob () throws IOException {
        Process subprocess = new ProcessBuilder(this.command).start();
        this.executedR = subprocess;
        return this;
    }

    @Override
    public InputStream getResult() {
        return this.executedR.getInputStream();
    }
    @Override
    public InputStream getError() {
        return this.executedR.getErrorStream();
    }
}

package Runner;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.function.Consumer;

class StreamGobbler implements Runnable {
    private InputStream inputStream;
    private Consumer<String> consumeInputLine;

    public StreamGobbler(InputStream inputStream, Consumer<String> consumeInputLine) {
        this.inputStream = inputStream;
        this.consumeInputLine = consumeInputLine;
    }

    public void run() {
        new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(consumeInputLine);
    }
}
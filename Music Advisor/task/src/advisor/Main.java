package advisor;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length > 1) {
            Util.setAuthServer(args[1]);
        }
        Util.parseInput();

    }
}

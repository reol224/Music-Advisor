package advisor;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length > 1) {
            if(args[0].contains("-access")){
            Util.setAuthServer(args[1]);
            }
            if(args[2].contains("-resource")){
                Util.setApiServer(args[3]);
            }
        }
        else{
            Util.setAuthServer(Util.AUTH_SERVER);
            Util.setApiServer(Util.API_SERVER);
        }
        Util.parseInput();

    }
}

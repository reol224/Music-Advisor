package advisor;

//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

//@SpringBootApplication
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        //SpringApplication.run(Main.class, args);
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

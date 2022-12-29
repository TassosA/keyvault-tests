package uni.cardlink.tests;

import io.quarkus.runtime.annotations.QuarkusMain;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.Quarkus;

@QuarkusMain  
public class Main {

    public static void main(String ... args) {
        System.out.println("Starting up Quarkus");
        Quarkus.run(MyApp.class, args);
    }

    public static class MyApp implements QuarkusApplication {

        @Override
        public int run(String... args) throws Exception {
            System.out.println("Running startup logic");
            Quarkus.waitForExit();
            System.out.println("Shutting down...");            
            return 0;
        }
    }
}

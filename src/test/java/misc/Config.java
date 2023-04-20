package misc;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    private static final Config instance = new Config();

    public static Config getConfig () {
        return instance;
    }

    private Dotenv env;

    public Config () {
        this.env = Dotenv.configure().directory("./").ignoreIfMissing().load();
    }

    public String get (String variable) {
        return env.get(variable);
    }
}
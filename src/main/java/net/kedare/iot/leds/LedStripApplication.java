package net.kedare.iot.leds;

import com.qmetric.spark.authentication.AuthenticationDetails;
import com.qmetric.spark.authentication.BasicAuthenticationFilter;
import net.kedare.iot.particle.ParticleException;
import org.json.JSONObject;
import spark.servlet.SparkApplication;

import java.util.logging.Level;
import java.util.logging.Logger;

import static spark.Spark.*;

public class LedStripApplication implements SparkApplication {

    private LedStrip ledStrip;
    private String token;
    private String deviceId;
    private String user;
    private String password;
    private Logger logger;

    public LedStripApplication() throws Exception {
        this.token = System.getenv("LED_CONTROLLER_TOKEN");
        this.deviceId = System.getenv("LED_CONTROLLER_DEVICE");
        this.user = System.getenv("LED_CONTROLLER_USER");
        this.password = System.getenv("LED_CONTROLLER_PASS");

        this.logger = Logger.getLogger(LedStripApplication.class.getName());

        if (this.token == null || this.deviceId == null || this.user == null || this.password == null) {
            logger.log(Level.SEVERE, "Please make sure all the required environment variables are configured : ");
            logger.log(Level.SEVERE, "LED_CONTROLLER_TOKEN, LED_CONTROLLER_DEVICE, LED_CONTROLLER_USER, LED_CONTROLLER_PASS");
            throw new Exception("Missing environment variables");
        }

        try {
            this.ledStrip = new LedStrip(token, deviceId, logger);
        } catch (ParticleException exception) {
            logger.log(Level.SEVERE, "Fail to initialize Particle", exception);
        }
    }

    public static void main(String[] args) throws Exception {
        int configuredPort;
        try {
            configuredPort = Integer.valueOf(System.getenv("PORT"));
        } catch (Exception e) {
            configuredPort = 80;
        }
        port(configuredPort);
        LedStripApplication controller = new LedStripApplication();
        controller.init();
    }

    public void init() {
        externalStaticFileLocation("target/static");
        before(new BasicAuthenticationFilter("/*", new AuthenticationDetails(this.user, this.password)));

        // Modes list
        get("/api/modes", (req, res) -> {
            res.type("application/json");
            return new JSONObject(LedStrip.PROGRAMS);
        });

        // GET/SET mode
        get("/api/mode", (req, res) -> {
            res.type("application/json");
            return this.ledStrip.getMode();
        });

        post("/api/mode", (req, res) -> {
            res.type("application/json");
            return this.ledStrip.setMode(req.queryParams("mode"));
        });

        // GET/SET Power
        get("/api/power", (req, res) -> {
            res.type("application/json");
            return this.ledStrip.getPower();
        });

        post("/api/power", (req, res) -> {
            res.type("application/json");
            return this.ledStrip.setPower(Integer.parseInt(req.queryParams("power")));
        });

        // GET/SET Wait
        get("/api/wait", (req, res) -> {
            res.type("application/json");
            return this.ledStrip.getDelay();
        });

        post("/api/wait", (req, res) -> {
            res.type("application/json");
            return this.ledStrip.setDelay(Integer.parseInt(req.queryParams("wait")));
        });

        // GET/SET color
        get("/api/color/:index", (req, res) -> {
            res.type("application/json");
            return this.ledStrip.getColor(Integer.parseInt(req.params("index")));
        });

        post("/api/color/:index", (req, res) -> {
            res.type("application/json");
            return this.ledStrip.setColor(Integer.parseInt(req.params("index")), req.queryParams("color"));
        });
    }
}

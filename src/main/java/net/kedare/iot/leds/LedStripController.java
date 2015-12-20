package net.kedare.iot.leds;

import org.json.JSONArray;

import java.util.logging.Logger;

import static spark.Spark.*;

public class LedStripController {
    public static void main(String[] args) throws Exception {
        String token = System.getenv("LED_CONTROLLER_TOKEN");
        String deviceId = System.getenv("LED_CONTROLLER_DEVICE");

        Logger logger =
                Logger.getLogger(LedStripController.class.getName());

        logger.info("Current directory is " + new java.io.File(".").getCanonicalPath());

        LedStrip ledStrip = new LedStrip(token, deviceId);

        externalStaticFileLocation("static");

        get("/modes", (req, res) -> {
            res.type("application/json");
            return new JSONArray(LedStrip.SUPPORTED_PROGRAMS);
        });

        get("/mode", (req, res) -> {
            res.type("application/json");
            return ledStrip.getMode();
        });
        post("/mode", (req, res) -> {
            res.type("application/json");
            return ledStrip.setMode(req.queryParams("mode"));
        });

        get("/power", (req, res) -> {
            res.type("application/json");
            return ledStrip.getPower();
        });
        post("/power", (req, res) -> {
            res.type("application/json");
            return ledStrip.setPower(Integer.parseInt(req.queryParams("power")));
        });

        get("/wait", (req, res) -> {
            res.type("application/json");
            return ledStrip.getDelay();
        });
        post("/wait", (req, res) -> {
            res.type("application/json");
            return ledStrip.setDelay(Integer.parseInt(req.queryParams("delay")));
        });
    }
}

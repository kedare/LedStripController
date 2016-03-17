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

        LedStrip ledStrip = new LedStrip(token, deviceId, logger);

        externalStaticFileLocation("static");

        // Modes list
        get("/api/modes", (req, res) -> {
            res.type("application/json");
            return new JSONArray(LedStrip.SUPPORTED_PROGRAMS);
        });

        // GET/SET mode
        get("/api/mode", (req, res) -> {
            res.type("application/json");
            return ledStrip.getMode();
        });

        post("/api/mode", (req, res) -> {
            res.type("application/json");
            return ledStrip.setMode(req.queryParams("mode"));
        });

        // GET/SET Power
        get("/api/power", (req, res) -> {
            res.type("application/json");
            return ledStrip.getPower();
        });

        post("/api/power", (req, res) -> {
            res.type("application/json");
            return ledStrip.setPower(Integer.parseInt(req.queryParams("power")));
        });

        // GET/SET Wait
        get("/api/wait", (req, res) -> {
            res.type("application/json");
            return ledStrip.getDelay();
        });

        post("/api/wait", (req, res) -> {
            res.type("application/json");
            return ledStrip.setDelay(Integer.parseInt(req.queryParams("delay")));
        });

        // GET/SET color
        get("/api/color/:index", (req, res) -> {
            res.type("application/json");
            return ledStrip.getColor(req.params("index"));
        });

        post("/api/color/:index", (req, res) -> {
            res.type("application/json");
            return ledStrip.setColor(req.params("index"), req.queryParams("color"));
        });

    }
}

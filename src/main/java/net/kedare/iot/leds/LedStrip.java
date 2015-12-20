package net.kedare.iot.leds;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;


public class LedStrip {
    public static final List<String> SUPPORTED_PROGRAMS = Arrays.asList("colorWipe", "rainbow", "rainbowCycle", "fullColorCycle", "randomDots", "turnedOff");
    public static final int MAX_POWER = 100;
    public static final int MIN_POWER = 0;
    public static final int MAX_DELAY = 1000;
    public static final int MIN_DELAY = 0;
    private final static Logger logger =
            Logger.getLogger(LedStrip.class.getName());
    private String token;
    private String deviceId;

    public LedStrip(String token, String deviceId) {
        if (token != null && deviceId != null) {
            this.token = token;
            this.deviceId = deviceId;
            logger.info("Connecting with token " + token + " and deviceId " + deviceId);
        } else {
            logger.severe("Missing credentials !");
        }
    }

    public JsonNode getVariable(String variableName) {
        try {
            return Unirest.get("https://api.particle.io/v1/devices/{device_id}/{variable}")
                    .routeParam("device_id", this.deviceId)
                    .routeParam("variable", variableName)
                    .queryString("access_token", this.token)
                    .asJson().getBody();
        } catch (UnirestException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JsonNode callFunction(String functionName, HashMap<String, Object> arguments) {
        try {
            return Unirest.post("https://api.particle.io/v1/devices/{device_id}/{function}")
                    .routeParam("device_id", this.deviceId)
                    .routeParam("function", functionName)
                    .queryString("access_token", this.token)
                    .fields(arguments)
                    .asJson().getBody();
        } catch (UnirestException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JsonNode getMode() {
        return this.getVariable("mode");
    }

    public JsonNode setMode(String program) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("mode", program);
        return this.callFunction("setMode", params);
    }

    public JsonNode getDelay() {
        return this.getVariable("wait");
    }

    public JsonNode setDelay(int delay) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("wait", delay);
        return this.callFunction("setWait", params);
    }

    public JsonNode getPower() {
        return this.getVariable("power");
    }

    public JsonNode setPower(int power) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("power", power);
        return this.callFunction("setPower", params);
    }
}
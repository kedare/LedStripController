package net.kedare.iot.leds;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import net.kedare.iot.particle.ParticleException;
import net.kedare.iot.particle.ParticleObject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class LedStrip extends ParticleObject {
    public static final int MAX_POWER = 100;
    public static final int MIN_POWER = 0;
    public static final int MAX_DELAY = 1000;
    public static final int MIN_DELAY = 0;
    public static Map<String, String> PROGRAMS = new HashMap<>();

    LedStrip(String token, String deviceId, Logger logger) throws ParticleException {
        super(token, deviceId, logger);
        PROGRAMS.put("colorWipe", "Fixed custom color");
        PROGRAMS.put("fadeCycle", "Cycle between 2 colors");
        PROGRAMS.put("gradient", "Gradient between 2 colors");
        PROGRAMS.put("rainbow", "Short color spectrum");
        PROGRAMS.put("rainbowCycle", "Large color spectrum");
        PROGRAMS.put("fullColorCycle", "Cycle between all colors");
        PROGRAMS.put("randomDots", "Random dots (colors and positions)");
        PROGRAMS.put("frozen", "Freeze to current state");
        PROGRAMS.put("off", "Turn off");
    }

    public JsonNode getMode() throws UnirestException {
        return this.getVariable("mode");
    }

    public JsonNode setMode(String program) throws UnirestException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("mode", program);
        return this.callFunction("setMode", params);
    }

    public JsonNode getDelay() throws UnirestException {
        return this.getVariable("wait");
    }

    public JsonNode setDelay(int delay) throws UnirestException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("wait", delay);
        return this.callFunction("setWait", params);
    }

    public JsonNode getPower() throws UnirestException {
        return this.getVariable("power");
    }

    public JsonNode setPower(int power) throws UnirestException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("power", power);
        return this.callFunction("setPower", params);
    }

    public JsonNode getColor(int index) throws UnirestException {
        return this.getVariable("color" + index);
    }

    public JsonNode setColor(int index, String color) throws UnirestException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("color" + index, color);
        return this.callFunction("setColor" + index, params);
    }
}
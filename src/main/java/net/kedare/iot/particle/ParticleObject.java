package net.kedare.iot.particle;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Created by kedare on 17/03/16.
 */
public class ParticleObject {

    private Logger logger =
            Logger.getLogger(getClass().getName());
    private String token;
    private String deviceId;


    public ParticleObject(String token, String deviceId, Logger logger) throws ParticleException {
        this.logger = logger;
        if (token != null && deviceId != null) {
            this.token = token;
            this.deviceId = deviceId;
            logger.info("Connecting with token " + token + " and deviceId " + deviceId);
        } else {
            throw new ParticleException("Missing credentials");
        }

        try {
            JsonNode body = Unirest.get("https://api.particle.io/v1/devices/{device_id}")
                    .routeParam("device_id", this.deviceId)
                    .queryString("access_token", this.token)
                    .asJson().getBody();
            logger.info("Found device " + body.getObject().getString("name"));
        } catch (Exception exception) {
            throw new ParticleException("Fail to connect, reason: " + exception.getClass().getCanonicalName() + " : " + exception.getLocalizedMessage());
        }

    }

    public JsonNode get(String variableName) throws UnirestException {
        return Unirest.get("https://api.particle.io/v1/devices/{device_id}/{variable}")
                .routeParam("device_id", this.deviceId)
                .routeParam("variable", variableName)
                .queryString("access_token", this.token)
                .asJson().getBody();
    }

    public JsonNode call(String functionName, HashMap<String, Object> arguments) throws UnirestException {
        return Unirest.post("https://api.particle.io/v1/devices/{device_id}/{function}")
                .routeParam("device_id", this.deviceId)
                .routeParam("function", functionName)
                .queryString("access_token", this.token)
                .fields(arguments)
                .asJson().getBody();
    }

}

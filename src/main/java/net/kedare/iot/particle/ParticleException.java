package net.kedare.iot.particle;

/**
 * Created by kedare on 17/03/16.
 */
public class ParticleException extends Exception {

    public ParticleException() {
        super();
    }

    public ParticleException(String message) {
        super(message);
    }

    public ParticleException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParticleException(Throwable cause) {
        super(cause);
    }

}

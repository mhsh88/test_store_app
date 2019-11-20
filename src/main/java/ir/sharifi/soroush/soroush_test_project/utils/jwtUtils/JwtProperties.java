package ir.sharifi.soroush.soroush_test_project.utils.jwtUtils;

public class JwtProperties {
    private String secret;
    private long tokenValidityInSeconds;

    public String getSecret() {
        return this.secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getTokenValidityInSeconds() {
        return this.tokenValidityInSeconds;
    }

    public void setTokenValidityInSeconds(long tokenValidityInSeconds) {
        this.tokenValidityInSeconds = tokenValidityInSeconds;
    }
}

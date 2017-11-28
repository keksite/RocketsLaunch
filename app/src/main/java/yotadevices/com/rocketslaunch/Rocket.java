package yotadevices.com.rocketslaunch;

/**
 * Created by keksi on 27.11.2017.
 */

public class Rocket {
    String rocketIcon;
    String rocketNAme;
    long launchData;
    String details;

    public Rocket(String rocketIcon, String rocketNAme, long launchData, String details) {

        this.rocketIcon = rocketIcon;
        this.rocketNAme = rocketNAme;
        this.launchData = launchData;
        this.details = details;
    }

    public void setRocketNAme(String rocketNAme) {
        this.rocketNAme = rocketNAme;
    }

    public void setLaunchData(long launchData) {
        this.launchData = launchData;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setRocketIcon(String rocketIcon) {

        this.rocketIcon = rocketIcon;
    }

    public String getRocketIcon() {

        return rocketIcon;
    }

    public String getRocketNAme() {
        return rocketNAme;
    }

    public long getLaunchData() {
        return launchData;
    }

    public String getDetails() {
        return details;
    }


}

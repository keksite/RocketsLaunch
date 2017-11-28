package yotadevices.com.rocketslaunch;

import java.util.Date;

/**
 * Created by keksi on 27.11.2017.
 */

public class Rocket {
    int rocketIcon;
    String rocketNAme;
    Date launchData;
    String details;

    public Rocket(String rocketNAme, Date launchData, String details) {

        this.rocketIcon = android.R.drawable.presence_busy;
        this.rocketNAme = rocketNAme;
        this.launchData = launchData;
        this.details = details;
    }

    public void setRocketNAme(String rocketNAme) {
        this.rocketNAme = rocketNAme;
    }

    public void setLaunchData(Date launchData) {
        this.launchData = launchData;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setRocketIcon(int rocketIcon) {

        this.rocketIcon = rocketIcon;
    }

    public int getRocketIcon() {

        return rocketIcon;
    }

    public String getRocketNAme() {
        return rocketNAme;
    }

    public Date getLaunchData() {
        return launchData;
    }

    public String getDetails() {
        return details;
    }


}

package yotadevices.com.rocketslaunch;

import java.util.Calendar;

/**
 * Created by keksi on 27.11.2017.
 */

public class Rocket {
    String rocketIcon;
    String rocketNAme;
    String launchData;
    String details;

    public Rocket(String rocketIcon, String rocketNAme, String launchData, String details) {

        this.rocketIcon = rocketIcon;
        this.rocketNAme = rocketNAme;
        this.launchData = launchData;
        this.details = details;
    }

    public void setRocketNAme(String rocketNAme) {
        this.rocketNAme = rocketNAme;
    }

    public void setLaunchData(String launchData) {
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

    public String getLaunchData() {
        try{
            Calendar mydate = Calendar.getInstance();
            mydate.setTimeInMillis(Long.parseLong(this.launchData)*1000);
            return String.valueOf(mydate.get(Calendar.DAY_OF_MONTH)) + "-" +
                    mydate.get(Calendar.MONTH) + "-" +
                    mydate.get(Calendar.YEAR);
        }
        catch (NumberFormatException ex){
            return this.launchData;
        }

    }

    public String getDetails() {
        return details;
    }


}

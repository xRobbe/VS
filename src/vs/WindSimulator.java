/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vs;

import static java.lang.Thread.sleep;
import java.util.Random;

/**
 *
 * @author debian
 */
public class WindSimulator {

    private long sleepTime;
    private int windSpeed;
    private boolean stopSimulator;

    public static void main(String[] args) {
        final WindSimulator sim = new WindSimulator();
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        sim.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            sleep(100000);
            sim.stopSimulator();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    WindSimulator() {
        this.stopSimulator = false;
        this.sleepTime = 1000;
    }

    WindSimulator(long sleepTime) {
        this.stopSimulator = false;
        this.sleepTime = sleepTime;
    }

    public void start() throws Exception {
        while (!this.stopSimulator) {
            this.windSpeed = simulate();
            System.out.println("Windspeed: " + this.windSpeed + "km/h");
            //send UDP
            sleep(this.sleepTime);
        }
    }

    public void startThread() {
        try {
            final WindSimulator sim = this;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        sim.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getWind() {
        return this.windSpeed;
    }

    public void stopSimulator() {
        this.stopSimulator = true;
    }

    public int simulate() throws Exception {
        Random rand = new Random();
        int random = rand.nextInt(100);

        if (random >= 0 && random < 10) {         // 10% 0-10
            return rand.nextInt(11);
        } else if (random >= 10 && random < 20) { // 10% 10-15
            return 10 + rand.nextInt(6);
        } else if (random >= 20 && random < 40) { // 20% 15-20
            return 15 + rand.nextInt(6);
        } else if (random >= 40 && random < 55) { // 15% 20-30
            return 20 + rand.nextInt(11);
        } else if (random >= 55 && random < 65) { // 10% 30-40
            return 30 + rand.nextInt(11);
        } else if (random >= 65 && random < 75) { // 10% 40-50
            return 40 + rand.nextInt(11);
        } else if (random >= 75 && random < 85) { // 10% 50-60
            return 50 + rand.nextInt(11);
        } else if (random >= 85 && random < 90) { //  5% 60-80
            return 60 + rand.nextInt(21);
        } else if (random >= 90 && random < 95) { //  5% 80-100
            return 80 + rand.nextInt(21);
        } else if (random >= 95 && random <= 100) { // 5% 100-140
            return 100 + rand.nextInt(41);
        } else {
            throw new Exception("Something went wrong");
        }
    }
}

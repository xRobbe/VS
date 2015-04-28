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
public class PowerSimulator {

    private long sleepTime;
    private int powerConsumtion;
    private boolean stopSimulator;

    public static void main(String[] args) {
        final PowerSimulator sim = new PowerSimulator();
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
            sleep(10000);
            sim.stopSimulator();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    PowerSimulator() {
        this.stopSimulator = false;
        this.sleepTime = 1000;
    }

    PowerSimulator(long sleepTime) {
        this.stopSimulator = false;
        this.sleepTime = sleepTime;
    }

    public void start() throws Exception {
        while (!this.stopSimulator) {
            this.powerConsumtion = simulate();
            System.out.println("Power Consumtion: " + this.powerConsumtion + " Watt");
            sleep(this.sleepTime);
        }
    }

    public void startThread() {
        try {
            final PowerSimulator sim = this;
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

    public int getConsumption() {
        return this.powerConsumtion;
    }

    public void stopSimulator() {
        this.stopSimulator = true;
    }

    public int simulate() throws Exception {
        Random rand = new Random();
        int random = rand.nextInt(100);

        if (random >= 0 && random < 10) {          // 10% 0-1
            return rand.nextInt(2);
        } else if (random >= 10 && random < 20) {  // 10% 1-2
            return 1 + rand.nextInt(2);
        } else if (random >= 20 && random < 40) {  // 20% 2-3
            return 2 + rand.nextInt(2);
        } else if (random >= 40 && random < 55) {  // 15% 3-4
            return 3 + rand.nextInt(2);
        } else if (random >= 55 && random < 65) {  // 10% 4-5
            return 4 + rand.nextInt(2);
        } else if (random >= 65 && random < 75) {  // 10% 5-6
            return 5 + rand.nextInt(2);
        } else if (random >= 75 && random < 85) {  // 10% 6-7
            return 6 + rand.nextInt(2);
        } else if (random >= 85 && random < 90) {  //  5% 7-8
            return 7 + rand.nextInt(2);
        } else if (random >= 90 && random < 95) {  //  5% 8-9
            return 8 + rand.nextInt(2);
        } else if (random >= 95 && random <= 100) { // 5% 9-10
            return 9 + rand.nextInt(2);
        } else {
            throw new Exception("Something went wrong");
        }
    }

}

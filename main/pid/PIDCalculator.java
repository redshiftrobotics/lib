package org.redshiftrobotics.lib.pid;

import com.qualcomm.robotcore.util.Range;

import org.redshiftrobotics.lib.debug.DebugHelper;
import org.redshiftrobotics.lib.pid.imu.IMU;

/**
 * Created by adam on 9/16/17.
 */

public class PIDCalculator {
    public static class PIDTuning {
        public final double P;
        public final double I;
        public final double D;

        public final double MAX_I;

        public PIDTuning(double P, double I, double D) { this(P, I, D, 5); }

        public PIDTuning(double P, double I, double D, double MAX_I) {
            this.P = P;
            this.I = I;
            this.D = D;
            this.MAX_I = MAX_I;
        }
    }

    private final IMU imu;

    private PIDTuning tuning;

    // Proportional, Integral, and Derivative Terms of the PID formula
    public double P = 0, I = 0, D = 0;
    public double lastError = 0;
    public double target;
    public double angle;

    // Our current delta time that holds the time between current and last calculations.
    private double deltaTime;


    /**
     * Primary constructor
     *
     * @param imu Valid implementation of the IMU interface
     */
    public PIDCalculator(IMU imu) {
        this.imu = imu;
        resetTarget();
    }

    /** Calculate P using the most efficient angular correction possible.
     *
     */
    private void calculateP() {
        angle = imu.getAngularRotationX();

        lastError = P;

        // We have to determine what the most efficient way to turn is (we should never turn
        // more than 180 degrees to hit a target).
        if (angle + 360 - target <= 180) {
            P = (angle -  target + 360);
        } else if (target + 360 - angle <= 180) {
            P = (target - angle + 360) * -1;
        } else if (angle -  target <= 180) {
            P = (angle -  target);
        }
    }

    /** Continuously approximates the cumulative integral of the e(t) function using
     * finite Riemann sums.
     *
     */
    public void calculateI() {
        I += P * deltaTime / 1000f;
        I = Range.clip(I, -tuning.MAX_I, tuning.MAX_I);

    }

    /** Internal member to calculate D. Approximates the current derivative
     * of the error function e(t). Scales down by 1000;
     *
     */
    private void calculateD() {
        D = ((P - lastError) / deltaTime) / 1000;
    }

    /** Calculate PID by adding the P, I, and D terms together.
     *
     * @param deltaTime the current time delta in seconds. Must be converted beforehand.
     * @return
     */
    public double calculatePID(double deltaTime) {
        this.deltaTime = deltaTime;
        calculateP();
        calculateI();
        calculateD();

        DebugHelper.addData("PID:dT", deltaTime);
        DebugHelper.addData("PID:P", P);
        DebugHelper.addData("PID:I", I);
        DebugHelper.addData("PID:D", D);
        DebugHelper.addData("PID:target", target);
        DebugHelper.addData("PID:angle", angle);


        return tuning.P * P + tuning.I * I / 2000f + tuning.D * D / 2000f;
    }

    public void setTuning(PIDTuning tuning) { this.tuning = tuning; }

    public void setTarget(double targetAngle) {
        this.target = targetAngle;
    }
    public void addTarget(double angleDelta) {
        this.target += angleDelta;
    }
    public void resetTarget() {
        this.target = this.imu.getAngularRotationX();
    }

    /**
     * Clears out all PID-associated data.
     */
    public void clearData() {
        this.P = 0;
        this.I = 0;
        this.D = 0;
        this.lastError = 0;
        this.deltaTime = 0;
    }
}



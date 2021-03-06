package org.redshiftrobotics.lib;

import android.content.Context;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.redshiftrobotics.lib.pid.PIDCalculator;
import org.redshiftrobotics.lib.pid.imu.IMU;

public interface RobotHardware {
    IMU getIMU();
    Context getAppContext();
    LinearOpMode getOpMode();

    void applyMotorPower(double velocityX, double velocityY, double correctionAngular);
    void stop();

    boolean getCanStrafe();

    PIDCalculator.PIDTuning getStraightTurning();
    PIDCalculator.PIDTuning getStrafeTuning();
    PIDCalculator.PIDTuning getTurningTuning();
    double getTurningAngleThreshold();
}


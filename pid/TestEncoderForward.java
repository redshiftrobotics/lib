package org.redshiftrobotics.lib.pid;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.redshiftrobotics.lib.MecanumRobot;

/**
 * Created by adam on 10/25/17.
 */
public class TestEncoderForward extends LinearOpMode {
    DcMotor frontLeft, frontRight, backLeft, backRight;
    MecanumRobot robot;
    BNO055IMU imu;

    @Override
    public void runOpMode() throws InterruptedException {
        frontLeft = hardwareMap.dcMotor.get("fl");
        frontRight = hardwareMap.dcMotor.get("fr");

        backLeft = hardwareMap.dcMotor.get("bl");
        backRight = hardwareMap.dcMotor.get("br");
        imu = hardwareMap.get(BNO055IMU.class, "imu");

        robot = new MecanumRobot(frontLeft,
                frontRight,
                backLeft,
                backRight,
                imu, this, telemetry);
        robot.imupidController.setTuning(1, 1, 1);

        waitForStart();

        // Forward Ten CM
        robot.moveStraight(1, Math.PI/2, 10000, 10);
        robot.STOP();

        Thread.sleep(1000);

        robot.moveStraight(1, -Math.PI/2, 10000, -10);

     /*   telemetry.addData("Turning... ", "");
        telemetry.update();
        Thread.sleep(5000);
        robot.turn(45, 5000);*/
    }
}

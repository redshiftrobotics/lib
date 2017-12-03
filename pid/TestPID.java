package org.redshiftrobotics.lib.pid;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.redshiftrobotics.lib.MecanumRobot;

/**
 * Created by adam on 10/17/17.
 */

@Autonomous(name = "Test Pid")
@Disabled
public class TestPID extends LinearOpMode {

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
      //  robot.imupidController.setTuning(0, 0, 0);
       // robot.imupidController.setTuning(1, 1, 1);

        waitForStart();

        robot.moveStraight(1,  3*Math.PI/2, 2000, 10);

        telemetry.addData("Turning... ", "");
        telemetry.update();
        robot.turn(45, 5000);
        robot.turn(90, 5000);
        robot.turn(-90, 5000);

    }
}

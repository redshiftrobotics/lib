package org.redshiftrobotics.lib.pixycam;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Duncan on 10/1/2017.
 */
@Autonomous(name="PixySensor")
public class PixyTest extends OpMode {

    PixyCam pixyCam;

    @Override
    public void init() {
        pixyCam = hardwareMap.get(PixyCam.class, "pixyCam");
    }

    @Override
    public void loop() {
        pixyCam.updateData();
        telemetry.addData("Name", pixyCam.getDeviceName());
        telemetry.addData("Active", pixyCam.checkZero());
        telemetry.addData("Data", pixyCam.readPixy());
        telemetry.addData("Sync", pixyCam.data.sync);
        telemetry.addData("Checksum", pixyCam.data.checksum);
        telemetry.addData("Signature", pixyCam.data.signature);
        telemetry.addData("X Center", pixyCam.data.xCenter);
        telemetry.addData("Y Center", pixyCam.data.yCenter);
        telemetry.addData("Width", pixyCam.data.width);
        telemetry.addData("Height", pixyCam.data.height);
        telemetry.update();
    }
}

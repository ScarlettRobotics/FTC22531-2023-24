package org.firstinspires.ftc.teamcode.Drive;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Core.*;


@TeleOp(name = "TankDrive2p", group = "auto")
public class TankDrive2p extends SystemsManager {
    // Class variables
    protected DrivetrainCore drivetraincore;

    // Gamepad variables
    protected boolean pgamepad2a;

    @Override
    public void loop() {
        // Update drive system
        // Protect against stick drift: in this case dead zone is 5%


        // Telemetry
        telemetry(telemetry);

        pgamepad2a = gamepad2.a;
    }

    public void telemetry(Telemetry telemetry) {
        drivetraincore.telemetry(telemetry);
    }
}

package org.firstinspires.ftc.teamcode.Drive;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Core.*;


/**
 * ServoTest
 * Designed to find the best servo locations for opening and closing the claw.
 */
@TeleOp(name = "TankDrive2p", group = "auto")
public class TankDrive2p extends OpMode {
    // Class variables
    protected DrivetrainCore drivetraincore;

    // Gamepad variables
    protected boolean pgamepad2a;

    @Override
    public void init() {
        drivetraincore = new DrivetrainCore(hardwareMap);
        // Telemetry
        telemetry.addData("STATUS: ", "Initialized"); // the FTC equivalent to println()
        telemetry.addData("FTC Team #", "22531");
    }

    @Override
    public void loop() {
        // Update drive system
        // Protect against stick drift: in this case dead zone is 5%
        if (!(gamepad1.left_stick_y < .05)) {
            drivetraincore.setMoveVelocity(gamepad1.left_stick_y, gamepad1.right_stick_y);
        }

        // Telemetry
        telemetry(telemetry);

        pgamepad2a = gamepad2.a;
    }

    public void telemetry(Telemetry telemetry) {
        drivetraincore.telemetry(telemetry);
    }
}

package org.firstinspires.ftc.teamcode.Drive;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Core.*;


/**
 * ServoTest
 * Designed to find the best servo locations for opening and closing the claw.
 */
@TeleOp(name = "ServoTest", group = "auto")
public class ServoTest extends OpMode {
    protected ClawCore clawCore;
    protected DualMotorDrive dualMotorDrive;

    @Override
    public void init() {
        clawCore = new ClawCore(hardwareMap);
        dualMotorDrive = new DualMotorDrive(hardwareMap);
        // Telemetry
        telemetry.addData("STATUS: ", "Initialized"); // the FTC equivalent to println()
        telemetry.addData("FTC Team #", "22531");
    }

    @Override
    public void loop() {
        // Update drive system
        dualMotorDrive.move(gamepad1.left_stick_y, gamepad1.right_stick_y);

        // Update claw
        if (gamepad2.dpad_up) {
            clawCore.move(.001);
        } else if (gamepad2.dpad_down) {
            clawCore.move(-.001);
        }
        clawCore.update();

        // Telemetry
        telemetry(telemetry);
    }

    public void telemetry(Telemetry telemetry) {
        clawCore.telemetry(telemetry);
        dualMotorDrive.telemetry(telemetry);
    }
}

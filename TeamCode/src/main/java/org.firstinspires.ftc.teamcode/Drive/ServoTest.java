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

    @Override
    public void init() {
        clawCore = new ClawCore(hardwareMap);
        // Telemetry
        telemetry.addData("STATUS: ", "Initialized"); // the FTC equivalent to println()
        telemetry.addData("FTC Team #", "22531");
    }

    @Override
    public void loop() {
        if (gamepad2.dpad_up) {
            clawCore.move(.001);
        } else if (gamepad2.dpad_down) {
            clawCore.move(-.001);
        }
        clawCore.update();

        telemetry(telemetry);
    }

    public void telemetry(Telemetry telemetry) {
        clawCore.telemetry(telemetry);
    }
}

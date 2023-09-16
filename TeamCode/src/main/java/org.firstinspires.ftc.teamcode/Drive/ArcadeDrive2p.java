package org.firstinspires.ftc.teamcode.Drive;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Core.SystemsManager;


/**
 * ArcadeDrive 1 Player
 * Designed for driving to be on controller 1, upper systems on controller 2.
 * Arcade drive. left stick controls turning (left/right), right stick controls movement (forward/backward).
 * Strafing on centre motor.
 */
@TeleOp(name = "ArcadeDrive2P", group = "auto")
public class ArcadeDrive2p extends SystemsManager {
    @Override
    public void loop() {
        telemetry.addData("STATUS", "Running");

        updateMotorArcade(1);
        updateClaw(2);
        updateSlide(2);

        cameraServo.resetCameraServo();
        cameraServo.telemetry(telemetry);

        telemetry.update();
    }
}

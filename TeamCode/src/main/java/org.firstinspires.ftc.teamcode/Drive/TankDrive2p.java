package org.firstinspires.ftc.teamcode.Drive;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Core.SystemsManager;


/**
 * TankDrive 1 Player
 * Designed for driving to be on controller 1, upper systems on controller 1.
 * Tank drive. left stick controls left motor, right stick controls right motor. Triggers on centre motor.
 */
@TeleOp(name = "TankDrive2P", group = "auto")
public class TankDrive2p extends SystemsManager {
    @Override
    public void loop() {
        telemetry.addData("STATUS", "Running");

        updateMotor(1);
        updateClaw(2);
        updateSlide(2);

        cameraServo.resetCameraServo();
        cameraServo.telemetry(telemetry);

        telemetry.update();
    }
}

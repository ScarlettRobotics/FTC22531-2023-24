package org.firstinspires.ftc.teamcode.Drive;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Core.SystemsManager;


/**
 * TankDrive 1 Player
 * Designed for driving to be on controller 1, upper systems on controller 1.
 * Tank drive. left stick controls left motor, right stick controls right motor. Triggers on centre motor.
 */
@TeleOp(name = "BROKEN! TankDrive1P", group = "auto")
public class TankDrive extends SystemsManager {
    @Override
    public void loop() {
        telemetry.addData("STATUS", "Running");

        //// DRIVETRAIN
        // Move left/right wheels based on left/right stick movement
        double left = gamepad1.left_stick_y;
        double right = gamepad1.right_stick_y;
        double center = gamepad1.right_trigger + (-gamepad1.left_trigger);
        cameraServo.resetCameraServo();
        drive.setMoveVelocity(left, right, center);
        drive.telemetry(telemetry);
        cameraServo.telemetry(telemetry);

        updateClaw(1);
        updateSlide(1);
        telemetry.update();
    }
}

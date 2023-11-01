package org.firstinspires.ftc.teamcode.Core;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public abstract class SystemsManager extends OpMode {
    // Initialize claw and slide classes
    protected DrivetrainCore drive;
    protected ClawCore claw;

    @Override
    public void init() {
        // Define classes
        drive = new DrivetrainCore(hardwareMap);
        // Telemetry
        telemetry.addData("STATUS: ", "Initialized"); // the FTC equivalent to println()
        telemetry.addData("FTC Team #", "22531");
    }


    /* Moves the slide based on gamepad presses */
    protected void updateSlide(final int controllerNum) {

    }

    /* Updates claw state based on gamepad presses. */
    protected void updateClaw(final int controllerNum) {
        // controllerNum determines the gamepad that controls the robot
        switch (controllerNum) {
            case 1:
                // Open/close claw if A/B is pressed (respectively)
                if (gamepad1.a) {
                    claw.open();
                } else if (gamepad1.b) {
                    claw.close();
                }
                break;
            case 2:
                // Open/close claw if A/B is pressed (respectively)
                if (gamepad2.a) {
                    claw.open();
                } else if (gamepad2.b) {
                    claw.close();
                }
                break;
        }
        claw.telemetry(telemetry);
    }

    /* Updates drivetrain state based on joystick movement. Uses tank drive controls. */
    protected void updateMotorTank(final int controllerNum) {
        double left, right;

        // controllerNum determines the gamepad that controls the robot
        switch(controllerNum) {
            case 1:
                // Move left/right wheels based on left/right stick movement
                left = gamepad1.left_stick_y;
                right = gamepad1.right_stick_y;
                break;
            case 2:
                // Move left/right wheels based on left/right stick movement
                left = gamepad2.left_stick_y;
                right = gamepad2.right_stick_y;
                break;
            default:
                left = 0;
                right = 0;
        }
        drive.setMoveVelocity(left, right);
        drive.telemetry(telemetry);
    }

    /* Updates drivetrain state based on joystick movement. Uses arcade drive controls. */
    protected void updateMotorArcade(final int controllerNum) {
        // turn is positive if intention is to turn right
        double forward, turn;

        // controllerNum determines the gamepad that controls the robot
        switch(controllerNum) {
            case 1:
                // Move left/right wheels based on left/right stick movement
                forward = gamepad1.left_stick_y;
                turn = gamepad1.right_stick_x;
                break;
            case 2:
                // Move left/right wheels based on left/right stick movement
                forward = gamepad2.left_stick_y;
                turn = gamepad2.right_stick_x;
                break;
            default:
                forward = 0;
                turn = 0;
        }
        drive.setMoveVelocity(forward - turn, forward + turn);
        drive.telemetry(telemetry);
    }
}

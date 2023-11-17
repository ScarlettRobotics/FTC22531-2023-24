package org.firstinspires.ftc.teamcode.Core;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public abstract class SystemsManager extends OpMode {
    // Initialize drivetrain and slide classes
    protected DrivetrainCore drivetrainCore;
    protected ArmCore armCore;
    protected ClawCore claw;

    @Override
    public void init() {
        // Define classes
        drivetrainCore = new DrivetrainCore(hardwareMap);
        armCore = new ArmCore(hardwareMap);
        // Telemetry
        telemetry.addData("STATUS: ", "Initialized"); // the FTC equivalent to println()
        telemetry.addData("FTC Team #", "22531");
    }


    /** Updates drivetrain state based on joystick movement. Uses tank drive controls. */
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
        drivetrainCore.setMoveVelocity(left, right);
    }

    /** Updates drivetrain state based on joystick movement. Uses arcade drive controls. */
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
        drivetrainCore.setMoveVelocity(forward - turn, forward + turn);
    }

    /** Updates arm movement based on left and right trigger. Uses encoder to keep the arm in place. */
    protected void updateArm(int controllerNum) {
        double raise;

        switch(controllerNum) {
            case 1:
                raise = gamepad1.right_trigger - gamepad1.left_trigger;
                break;
            case 2:
                raise = gamepad2.right_trigger - gamepad2.left_trigger;
                break;
            default:
                raise = 0;
        }
        armCore.moveByEncoder((int)raise*1000);
    }

    protected void updateClaw(int controllerNum){
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

    protected void updateArmBlind(int controllerNum){
        double power;

        switch(controllerNum) {
            case 1:
                // Move left/right wheels based on left/right stick movement
                power= gamepad1.right_trigger - gamepad1.left_trigger;
                break;
            case 2:
                // Move left/right wheels based on left/right stick movement
                power = gamepad2.right_trigger - gamepad2.left_trigger;
                break;
            default:
                power = 0;
        }

        armCore.moveLikeVelocity(power);
    }

    protected void telemetry(Telemetry telemetry) {
        drivetrainCore.telemetry(telemetry);
        armCore.telemetry(telemetry);
    }
}

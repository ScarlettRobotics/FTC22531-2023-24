package org.firstinspires.ftc.teamcode.Core;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public abstract class SystemsManager extends OpMode {
    // Initialize claw and slide classes
    protected TriMotorDrive drive;
    protected ClawCore claw;
    protected SlideCore slide;

    protected boolean pLeftBumper;
    protected boolean pRightBumper;

    protected CameraServoCore cameraServo;
    

    protected int turnQueueLeft = 0;
    protected int turnQueueRight = 0;
    @Override
    public void init() {
        // Define classes
        drive = new TriMotorDrive(hardwareMap);
        claw = new ClawCore(hardwareMap);
        slide = new SlideCore(hardwareMap);
        cameraServo = new CameraServoCore(hardwareMap);
        // Telemetry
        telemetry.addData("STATUS: ", "Initialized"); // the FTC equivalent to println()
        telemetry.addData("FTC Team #", "22531");
    }


    /* Moves the slide based on gamepad presses */
    protected void updateSlide(final int controllerNum) {
        // controllerNum determines the gamepad that controls the robot
        switch (controllerNum) {
            case 1:
                slide.slideManual(gamepad1.right_trigger - gamepad1.left_trigger);
                break;
            case 2:
                slide.slideManual(gamepad2.right_trigger - gamepad2.left_trigger);
                break;
        }
        slide.telemetry(telemetry);
    }

    /* Updates claw state based on gamepad presses. */
    protected void updateClaw(final int controllerNum) {
        // controllerNum determines the gamepad that controls the robot
        switch (controllerNum) {
            case 1:
                // Open/close claw if A/B is pressed (respectively)
                if (gamepad1.left_bumper) {
                    claw.open();
                } else if (gamepad1.right_bumper) {
                    claw.close();
                }
                break;
            case 2:
                // Open/close claw if A/B is pressed (respectively)
                if (gamepad2.left_bumper) {
                    claw.open();
                } else if (gamepad2.right_bumper) {
                    claw.close();
                }
                break;
        }
        claw.telemetry(telemetry);
    }


    protected void updateMotor(final int controllerNum) {
        double left, right, center;

        // controllerNum determines the gamepad that controls the robot
        switch(controllerNum) {
            case 1:
                // Move left/right wheels based on left/right stick movement
                left = gamepad1.left_stick_y;
                right = gamepad1.right_stick_y;
                center = gamepad1.right_trigger - gamepad1.left_trigger;
                // Snap turn
                if (!pLeftBumper && gamepad1.left_bumper) {
                    turnQueueLeft++;
                    if (turnQueueLeft > 0) {
                        if (drive.getLeftMotorTargetPosition() == drive.getLeftMotorCurrentPosition()) {
                            turnQueueLeft--;
                            drive.moveInches(-11, 11, 0);

                        } else {
                            return;
                        }



                    }
                }

                if (!pRightBumper && gamepad1.right_bumper) {
                    turnQueueRight++;
                    if (turnQueueRight > 0) {
                        if (drive.getRightMotorTargetPosition() == drive.getRightMotorCurrentPosition()) {
                            turnQueueRight--;
                            drive.moveInches(11, -11, 0);
                        } else {
                            return;
                        }



                    }

                }
                pLeftBumper = gamepad1.left_bumper;
                pRightBumper = gamepad1.right_bumper;
                break;
            case 2:
                // Move left/right wheels based on left/right stick movement
                left = gamepad2.left_stick_y;
                right = gamepad2.right_stick_y;
                center = gamepad2.right_trigger - gamepad2.left_trigger;
                // Snap turn

                break;
            default:
                left = 0;
                right = 0;
                center = 0;
        }
        drive.setMoveVelocity(left, right, center);
        drive.update();
        drive.telemetry(telemetry);
    }

    /* Garbage quick fix code that adds arcade drive to the drivetrain */
    protected void updateMotorArcade(final int controllerNum) {
        // turn is positive if intention is to turn right
        double forward, turn, center;

        // controllerNum determines the gamepad that controls the robot
        switch(controllerNum) {
            case 1:
                // Move left/right wheels based on left/right stick movement
                forward = gamepad1.left_stick_y;
                turn = gamepad1.right_stick_x;
                center = gamepad1.right_trigger - gamepad1.left_trigger;
                break;
            case 2:
                // Move left/right wheels based on left/right stick movement
                forward = gamepad2.left_stick_y;
                turn = gamepad2.right_stick_x;
                center = gamepad2.right_trigger - gamepad2.left_trigger;
                break;
            default:
                forward = 0;
                turn = 0;
                center = 0;
        }
        drive.setMoveVelocity(forward - turn, forward + turn, center);
        drive.update();
        drive.telemetry(telemetry);
    }
}


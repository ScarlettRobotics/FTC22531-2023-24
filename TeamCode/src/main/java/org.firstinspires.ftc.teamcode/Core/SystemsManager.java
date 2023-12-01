package org.firstinspires.ftc.teamcode.Core;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/** Intended to be inherited from driver portion OpModes.
 * Takes "---Core" classes, mapping them to controller inputs depending on controllerNum.
 * All contained methods will receive "controllerNum" stating which gamepad/controller controls each system part.
 * For example, "updateMotorTank(1)" will have controller 1 control the robot.
 * If you'd like to use FTC Dashboard, please refer to https://acmerobotics.github.io/ftc-dashboard/,
 *  then "Getting Started." */
public abstract class SystemsManager extends OpMode {
    // Drivetrain and slide classes
    protected DrivetrainCore drivetrainCore;
    protected ArmCore armCore;
    protected ClawCore clawCore;
    protected DroneLauncherCore droneLauncherCore;
    // FTC Dashboard telemetry variables
    FtcDashboard dashboard;
    Telemetry dashboardTelemetry;
    // Variables used in methods
    private int armPixelLevel;
    private int[] pixelLevelEncoders = {-2800, -2670, -2620, -2520, -2400, -2270, -2100};
    private boolean pGamepadDpadUp, pGamepadDpadDown;

    @Override
    public void init() {
        // Initialize classes
        drivetrainCore = new DrivetrainCore(hardwareMap);
        armCore = new ArmCore(hardwareMap);
        clawCore = new ClawCore(hardwareMap);
        droneLauncherCore = new DroneLauncherCore(hardwareMap);
        // Make preloading work by closing claw
        clawCore.close();
        // Telemetry
        telemetry.addData("STATUS: ", "Initialized"); // the FTC equivalent to println()
        telemetry.addData("FTC Team #", "22531");
        // Initialize FTC Dashboard variables
        dashboard = FtcDashboard.getInstance();
        dashboardTelemetry = dashboard.getTelemetry();
        // Initialize method variables
        armPixelLevel = 0;
        pGamepadDpadUp = false;
        pGamepadDpadDown = false;
    }

    /** Receives a gamepad joystick input and returns zero if below a value. */
    private double noDrift(double stick, double drift) {
        if (stick < drift && stick > 0-drift) {
            return 0;
        }
        return stick;
    }

    /** Updates drivetrain state based on joystick movement. Uses tank drive controls.
     * @param controllerNum Determines the driver number that operates the machine system.
     *                      Receives 1 or 2; otherwise does nothing.
     */
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
        drivetrainCore.setMoveVelocity(noDrift(left, 0.05),
                noDrift(right, 0.05));
    }

    /** Updates drivetrain state based on joystick movement. Uses arcade drive controls.
     * @param controllerNum Determines the driver number that operates the machine system.
     *                      Receives 1 or 2; otherwise does nothing.
     */
    protected void updateMotorArcade(final int controllerNum) {
        // turn is positive if intention is to turn right
        double forward, turn;

        // controllerNum determines the gamepad that controls the robot
        switch(controllerNum) {
            case 1:
                // Move left/right wheels based on left/right stick movement
                forward = gamepad1.left_stick_y;
                if (gamepad1.a){
                    forward = .15;
                }
                if(gamepad1.b){
                    forward = -.15;
                }
                turn = gamepad1.right_stick_x;
                break;
            case 2:
                // Move left/right wheels based on left/right stick movement
                forward = gamepad2.left_stick_y;
                if (gamepad2.a){
                    forward = .15;
                }
                if(gamepad2.b){
                    forward = -.15;
                }
                turn = gamepad2.right_stick_x;
                break;
            default:
                forward = 0;
                turn = 0;
        }
        drivetrainCore.setMoveVelocity(noDrift(forward - turn, 0.05),
                noDrift(forward + turn, 0.05));
    }

    /** Updates arm movement.
     * Press LSB to reset the arm to its pick up location.
     * Use the dpad to move the arm to different pixel levels.
     *  If in base position, pressing dpadDown will move the arm to its highest position.
     * @param controllerNum Determines the driver number that operates the machine system.
     *                      Receives 1 or 2; otherwise does nothing. */
    protected void updateArm(int controllerNum) {
        boolean gamepadLSB, gamepadDpadUp, gamepadDpadDown;
        switch (controllerNum) {
            case 1:
                gamepadLSB = gamepad1.left_stick_button;
                gamepadDpadUp = gamepad1.dpad_up;
                gamepadDpadDown = gamepad1.dpad_down;
                break;
            case 2:
                gamepadLSB = gamepad2.left_stick_button;
                gamepadDpadUp = gamepad2.dpad_up;
                gamepadDpadDown = gamepad2.dpad_down;
                break;
            default:
                gamepadLSB = false;
                gamepadDpadUp = false;
                gamepadDpadDown = false;
        }
        // TODO
        if (gamepadLSB) {
            armCore.setTargetPosition(-3300); // Move to ground position
            armPixelLevel = -1; // at ground pos
        }
        // Move up by one pixel level
        if (gamepadDpadUp && !pGamepadDpadUp) {
            // not at topmost pixel level
            if (armPixelLevel != pixelLevelEncoders.length-1) {
                armCore.setTargetPosition(pixelLevelEncoders[armPixelLevel+1]);
            }
        }
        // Move down by one pixel level
        if (gamepadDpadDown && !pGamepadDpadDown) {
            // not at bottommost pixel level and selecting a pixel level
            if (armPixelLevel > 0) {
                armCore.setTargetPosition(pixelLevelEncoders[armPixelLevel-1]);
            }
            // at ground level
            if (armPixelLevel == -1) {
                armCore.setTargetPosition(pixelLevelEncoders[pixelLevelEncoders.length-1]);
            }
        }
        armCore.update();
        pGamepadDpadUp = gamepadDpadUp;
        pGamepadDpadDown = gamepadDpadDown;
    }

    /** Updates arm movement.
     * Right and left trigger moves the arm.
     * Uses setPower(). Only use if ArmCore's RUN_TO_POSITION doesn't work.
     * @param controllerNum Determines the driver number that operates the machine system.
     *                      Receives 1 or 2; otherwise does nothing. */

    protected void updateArmBlind(int controllerNum){
        double power;

        switch(controllerNum) {
            case 1:
                // Move left/right wheels based on left/right stick movement
                power = gamepad1.left_trigger - gamepad1.right_trigger;
                break;
            case 2:
                // Move left/right wheels based on left/right stick movement
                power = gamepad2.left_trigger - gamepad2.right_trigger;
                break;
            default:
                power = 0;
        }
        armCore.setPower(power/2);
    }

    /** Updates the claw's movement.
     * A/B opens/closes the claw respectively.
     * Opening will be prioritized over closing the claw if both buttons are pressed.
     * @param controllerNum Determines the driver number that operates the machine system.
     *                      Receives 1 or 2; otherwise does nothing. */
    protected void updateClaw(int controllerNum) {
        boolean open, close;
        switch (controllerNum) {
            case 1:
                open = gamepad1.a;
                close = gamepad1.b;
                break;
            case 2:
                open = gamepad2.a;
                close = gamepad2.b;
                break;
            default:
                open = false;
                close = false;
        }
        if (open) clawCore.open();
        if (close) clawCore.close();
    }

    /** Checks for a button press to launch the drone.
     * If drone does not launch, rotate the part attached to the servo by 180 degrees.
     * @param controllerNum Determines the driver number that operates the machine system.
     *                      Receives 1 or 2; otherwise does nothing. */
    protected void checkForDroneLaunch(int controllerNum) {
        boolean launching = false;
        switch (controllerNum) {
            case 1:
                launching = gamepad1.dpad_up;
                break;
            case 2:
                launching = gamepad2.dpad_up;
                break;
        }
        if (launching) droneLauncherCore.launch();
    }

    /** Telemetry */
    protected void telemetry(Telemetry telemetry) {
        // Telemetry sent to Driver Hub
        drivetrainCore.telemetry(telemetry);
        armCore.telemetry(telemetry);
        clawCore.telemetry(telemetry);
        droneLauncherCore.telemetry(telemetry);
        // Telemetry sent to FTC Dashboard
        drivetrainCore.telemetry(dashboardTelemetry);
        armCore.telemetry(dashboardTelemetry);
        clawCore.telemetry(dashboardTelemetry);
        droneLauncherCore.telemetry(dashboardTelemetry);
        dashboardTelemetry.update();
    }
}

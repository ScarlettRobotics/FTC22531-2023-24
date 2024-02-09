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
                if (gamepad1.dpad_up || gamepad1.dpad_down) { // slight forward/backwards, backwards prioritized
                    forward = (gamepad1.dpad_down) ? 0.3 : -0.3;
                }
                turn = gamepad1.right_stick_x;
                if (gamepad1.dpad_left || gamepad1.dpad_right) { // slight left/right, left prioritized
                    turn = (gamepad1.dpad_left) ? -0.3 : 0.3;
                }
                break;
            case 2:
                // Move left/right wheels based on left/right stick movement
                forward = gamepad2.left_stick_y;
                if (gamepad2.dpad_up || gamepad2.dpad_down) { // slight forward/backwards, backwards prioritized
                    forward = (gamepad2.dpad_down) ? 0.3 : -0.3;
                }
                turn = gamepad2.right_stick_x;
                if (gamepad2.dpad_left || gamepad2.dpad_right) { // slight left/right, left prioritized
                    turn = (gamepad2.dpad_left) ? -0.3 : 0.3;
                }
                break;
            default:
                forward = 0;
                turn = 0;
        }
        drivetrainCore.setMoveVelocity(noDrift(forward - turn, 0.05),
                noDrift(forward + turn, 0.05));
    }

    /** Updates arm movement.
     * Right and left trigger moves the arm.
     * Uses setPower(). Only use if ArmCore's RUN_TO_POSITION doesn't work.
     * @param controllerNum Determines the driver number that operates the machine system.
     *                      Receives 1 or 2; otherwise does nothing. */

    protected void updateArm(int controllerNum){
        double power;
        boolean lifting;

        switch(controllerNum) {
            case 1:
                // Move left/right wheels based on left/right stick movement
                power = gamepad1.left_trigger - gamepad1.right_trigger;
                lifting = gamepad1.right_stick_button;
                break;
            case 2:
                // Move left/right wheels based on left/right stick movement
                power = gamepad2.left_trigger - gamepad2.right_trigger;
                lifting = gamepad2.right_stick_button;
                break;
            default:
                power = 0;
                lifting = false;
        }
        armCore.setPower(power/2);
        if (lifting) armCore.setPower(1);
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
                launching = gamepad1.left_stick_button;
                break;
            case 2:
                launching = gamepad2.left_stick_button;
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

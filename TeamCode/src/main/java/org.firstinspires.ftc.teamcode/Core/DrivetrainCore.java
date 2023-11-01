package org.firstinspires.ftc.teamcode.Core;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/*** Manages the drivetrain of the robot. */
public class DrivetrainCore {
    /* Initialization */
    /** Initialization is done within DrivetrainCore for ease of access. */
    private DcMotor leftMotor;
    private DcMotor rightMotor;

    /** Initializes 2 DcMotor Objects for the 2 wheels and sets movement directions */
    public DrivetrainCore(HardwareMap hardwareMap) {
        // Map DcMotor variables to hardwareMap
        leftMotor = hardwareMap.get(DcMotor.class, "leftMotor");
        rightMotor = hardwareMap.get(DcMotor.class, "rightMotor");

        // Set motor movement directions
        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        /** When coding autonomous for the robot,
         * resetting the encoder values to zero for each motor makes coding autonomous easier. */
        // Reset encoder values
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotor.setTargetPosition(0);
        rightMotor.setTargetPosition(0);

        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /** setMoveVelocity
     * Uses RUN_USING_ENCODER to move all motors by an inputted velocity
     * @param leftVelocity - power sent to the left motor
     * @param rightVelocity - power sent to the right motor
     */
    public void setMoveVelocity(double leftVelocity, double rightVelocity) {
        leftMotor.setPower(leftVelocity);
        rightMotor.setPower(rightVelocity);
    }

    /** Telemetry in contained in each class for ease of access. */
    public void telemetry(Telemetry telemetry) {
        telemetry.addData("\nCurrent class", "DrivetrainCore.java");
        telemetry.addData("runMode", leftMotor.getMode());
        // if motors aren't running don't pass extra values to telemetry
        if (leftMotor.getPower() == rightMotor.getPower() && leftMotor.getPower() == 0) {
            telemetry.addData("Motors Idle", "");
        } else {
            telemetry.addData("Left Power",
                    "%4.2f", leftMotor.getPower());
            telemetry.addData("Right Power",
                    "%4.2f", rightMotor.getPower());
        }
    }
}

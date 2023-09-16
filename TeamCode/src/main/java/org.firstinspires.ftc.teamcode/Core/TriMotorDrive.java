package org.firstinspires.ftc.teamcode.Core;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/*** Manages the drivetrain of the robot.
 * "Tri" refers to the three wheels in the drivetrain. */
public class TriMotorDrive {
    //// CLASS VARIABLES
    private DcMotor leftMotor;
    private DcMotor rightMotor;
    private DcMotor centerMotor;
    //// CONSTANT VARIABLES
    private final int ENCODER_VALUES_PER_ROTATION = 700;
    // TODO ADJUST VALUE
    private final double INCHES_PER_ROTATION = 12.36;

    /** Init */

    //Initializes 3 DcMotor Objects for the 3 wheels and sets movement directions
    public TriMotorDrive (HardwareMap hardwareMap) {
        // Map DcMotor variables to hardwareMap
        leftMotor = hardwareMap.get(DcMotor.class, "left_motor");
        rightMotor = hardwareMap.get(DcMotor.class, "right_motor");
        centerMotor = hardwareMap.get(DcMotor.class, "center_motor");

        // Set motor movement directions
        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        centerMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        centerMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftMotor.setTargetPosition(0);
        rightMotor.setTargetPosition(0);
        centerMotor.setTargetPosition(0);
    }


    /** setMoveVelocity
     * Uses RUN_USING_ENCODER to move all motors by an inputted velocity
     *
     * @param leftVelocity - power sent to the left motor
     * @param rightVelocity - power sent to the right motor
     * @param centerVelocity - power sent to the center motor
     */
    public void setMoveVelocity(double leftVelocity, double rightVelocity, double centerVelocity) {
        if (leftMotor.getMode() == DcMotor.RunMode.RUN_TO_POSITION) {
            if (leftVelocity == 0 &&
                    rightVelocity == 0 &&
                    centerVelocity == 0) {
                return;
            }
        }

        if (leftMotor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            centerMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        leftMotor.setPower(leftVelocity);
        rightMotor.setPower(rightVelocity);
        centerMotor.setPower(centerVelocity);
    }

    /** Converts inches to encoder values using constants
     * MAY BE UNRELIABLE, AS FRICTION IS UNACCOUNTED FOR */
    private int inchesToEncoderValues(double inches) {
        return Math.toIntExact(Math.round(inches * ENCODER_VALUES_PER_ROTATION / INCHES_PER_ROTATION));
    }

    /** Uses RUN_TO_POSITION to move the motors by a distance. */
    public void moveInches(double leftInches, double rightInches, double centerInches) {
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        centerMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftMotor.setTargetPosition(-inchesToEncoderValues(leftInches));
        rightMotor.setTargetPosition(-inchesToEncoderValues(rightInches));
        centerMotor.setTargetPosition(inchesToEncoderValues(centerInches));

        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        centerMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public int getLeftMotorTargetPosition() {
       return  leftMotor.getTargetPosition();
    }

    public int getRightMotorTargetPosition() {
        return  rightMotor.getTargetPosition();
    }

    public int getLeftMotorCurrentPosition() {
        return  leftMotor.getTargetPosition();
    }

    public int getRightMotorCurrentPosition() {
        return  rightMotor.getTargetPosition();
    }

    /** If the motors are in RUN_TO_POSITION, motors progress to their target position */
    public void update() {
        if (leftMotor.getMode() == DcMotor.RunMode.RUN_TO_POSITION) {
            leftMotor.setTargetPosition(leftMotor.getTargetPosition());
            leftMotor.setPower(1);
            rightMotor.setTargetPosition(rightMotor.getTargetPosition());
            rightMotor.setPower(1);
            centerMotor.setTargetPosition(centerMotor.getTargetPosition());
            centerMotor.setPower(1);
        }
    }

    public void telemetry(Telemetry telemetry) {
        telemetry.addData("\nCurrent class", "TriMotorDrive.java");
        telemetry.addData("runMode", leftMotor.getMode());
        if (leftMotor.getMode() == DcMotor.RunMode.RUN_USING_ENCODER) {
            telemetry.addData("Left Power",
                    "%4.2f", leftMotor.getPower());
            telemetry.addData("Right Power",
                    "%4.2f", rightMotor.getPower());
            telemetry.addData("Center Power",
                    "%4.2f", centerMotor.getPower());
        }
        if (leftMotor.getMode() == DcMotor.RunMode.RUN_TO_POSITION) {
            telemetry.addData("Left Position & Target",
                    "%d %d", leftMotor.getCurrentPosition(), leftMotor.getTargetPosition());
            telemetry.addData("Right Position & Target",
                    "%d %d", rightMotor.getCurrentPosition(), rightMotor.getTargetPosition());
            telemetry.addData("Center Position & Target",
                    "%d %d", centerMotor.getCurrentPosition(), centerMotor.getTargetPosition());
        }
    }
}

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
    private PIDController leftMotorAuto;
    private PIDController rightMotorAuto;

    /** Initializes 2 DcMotor Objects for the 2 wheels and sets movement directions */
    public DrivetrainCore(HardwareMap hardwareMap) {
        // Map DcMotor variables to hardwareMap
        leftMotor = hardwareMap.get(DcMotor.class, "leftMotor");
        rightMotor = hardwareMap.get(DcMotor.class, "rightMotor");
        leftMotorAuto = new PIDController(hardwareMap, "leftMotor",
                0, 0, 0, 1);
        rightMotorAuto = new PIDController(hardwareMap, "rightMotor",
                0, 0, 0, 1);

        // Set motor movement directions
        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        /** When coding autonomous for the robot,
         * resetting the encoder values to zero for each motor makes coding autonomous easier. */
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // encoders don't set to 0 by itself
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); // mode doesn't use encoders to set raw motor powers
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); // more consistent this way
    }

    /** Sets a new target position based on the current position, moving by the input.
     * Make sure everything isn't moving before doing this. */
    public void moveByEncoder(int leftEncoder, int rightEncoder) {
        leftMotorAuto.moveByEncoder(leftEncoder);
        rightMotorAuto.moveByEncoder(rightEncoder);
    }

    /** Returns left targetPosition */
    protected int getTargetPositionLeft() {
        return leftMotorAuto.getTargetPosition();
    }
    /** Returns right targetPosition */
    protected int getTargetPositionRight() {
        return rightMotorAuto.getTargetPosition();
    }

    /** Updates the PIDController to move towards the provided goal position. */
    public void updateAuto() {
        leftMotorAuto.update();
        rightMotorAuto.update();
    }

    /** setMoveVelocity
     * Uses RUN_USING_ENCODER to move all motors by an inputted velocity
     * @param leftVelocity - power sent to the left motor
     * @param rightVelocity - power sent to the right motor
     */
    public void setMoveVelocity(double leftVelocity, double rightVelocity) {
        leftMotor.setPower(changePower(leftVelocity));
        rightMotor.setPower(changePower(rightVelocity));
    }

    /**
     * Squares the velocity in the given direction
     * @param velocity
     * @return - return the squared velocity
     */
    private double changePower(double velocity) {
        return velocity * velocity * velocity;
    }

    /** Telemetry in contained in each class for ease of access. */
    public void telemetry(Telemetry telemetry) {
        telemetry.addData("\nCURRENT CLASS", "DrivetrainCore.java");
        telemetry.addData("runMode", leftMotor.getMode());
        // left motor telemetry
        telemetry.addData("Left Power",
                "%4.2f", leftMotor.getPower());
        telemetry.addData("Left currentPosition", leftMotor.getCurrentPosition());
        telemetry.addData("Left targetPosition", leftMotor.getTargetPosition());
        // right motor telemetry
        telemetry.addData("Right Power",
                "%4.2f", rightMotor.getPower());
        telemetry.addData("Right currentPosition", rightMotor.getCurrentPosition());
        telemetry.addData("Right targetPosition", rightMotor.getTargetPosition());
    }
}

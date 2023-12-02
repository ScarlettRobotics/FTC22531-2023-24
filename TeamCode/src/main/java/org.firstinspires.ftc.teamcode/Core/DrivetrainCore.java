package org.firstinspires.ftc.teamcode.Core;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/*** Manages the drivetrain of the robot. */
public class DrivetrainCore {
    /* Initialization */
    /** Initialization is done within DrivetrainCore for ease of access. */
    private PIDController leftMotor;
    private PIDController rightMotor;

    /** Initializes 2 DcMotor Objects for the 2 wheels and sets movement directions */
    public DrivetrainCore(HardwareMap hardwareMap) {
        // Map DcMotor variables to hardwareMap
        leftMotor = new PIDController(hardwareMap, "leftMotor",
                0.01, 0.0003, 0.0003, 0.1);
        rightMotor = new PIDController(hardwareMap, "rightMotor",
                0.01, 0.0003, 0.0003, 0.1);

        // Set motor movement directions
        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    /** Sets a new target position based on the current position, moving by the input. */
    public void moveByEncoder(int leftEncoder, int rightEncoder) {
        leftMotor.moveByEncoder(leftEncoder);
        rightMotor.moveByEncoder(rightEncoder);
    }

    /** Returns left targetPosition */
    protected int getTargetPositionLeft() {
        return leftMotor.getTargetPosition();
    }
    /** Returns right targetPosition */
    protected int getTargetPositionRight() {
        return rightMotor.getTargetPosition();
    }

    /** Updates the PIDController to move towards the provided goal position. */
    public void updateAuto() {
        leftMotor.update();
        rightMotor.update();
    }

    /** setMoveVelocity
     * Uses RUN_USING_ENCODER to move all motors by an inputted velocity
     * @param leftVelocity - power sent to the left motor
     * @param rightVelocity - power sent to the right motor
     */
    public void setMoveVelocity(double leftVelocity, double rightVelocity) {
        leftMotor.overridePower(changePower(leftVelocity));
        rightMotor.overridePower(changePower(rightVelocity));
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
        leftMotor.telemetry(telemetry);
        rightMotor.telemetry(telemetry);
    }
}

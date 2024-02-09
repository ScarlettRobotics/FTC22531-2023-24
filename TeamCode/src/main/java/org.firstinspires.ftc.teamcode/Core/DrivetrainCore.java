package org.firstinspires.ftc.teamcode.Core;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.AutoCore.PIDController;

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
                0.01, 0.0003, 0.0003, 0.4);
        rightMotor = new PIDController(hardwareMap, "rightMotor",
                0.01, 0.0003, 0.0003, 0.4);

        // Set motor movement directions
        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    /** Sets a new target position based on the current position, moving by the input. */
    public void moveByEncoder(int leftEncoder, int rightEncoder) {
        leftMotor.moveByEncoder(leftEncoder);
        rightMotor.moveByEncoder(rightEncoder);
    }

    /** Moves the robot forwards by the inputted encoder value */
    public void forwardByEncoder(int encoder) {
        moveByEncoder(-encoder, -encoder);
    }

    /** Turns the robot by the inputted encoder value.
     * Positive turns right. */
    public void rotateByEncoder(int encoder) {
        moveByEncoder(-encoder, encoder);
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
        leftMotor.overridePower(constrainPower(leftVelocity));
        rightMotor.overridePower(constrainPower(rightVelocity));
    }

    /**
     * @param velocity current power of motor
     * @return - return the squared velocity
     */
    private double constrainPower(double velocity) {
        return Math.min(Math.max(velocity, -0.8), 0.8);
    }

    /** Telemetry in contained in each class for ease of access. */
    public void telemetry(Telemetry telemetry) {
        telemetry.addData("\nCURRENT CLASS", "DrivetrainCore.java");
        leftMotor.telemetry(telemetry);
        rightMotor.telemetry(telemetry);
    }
}

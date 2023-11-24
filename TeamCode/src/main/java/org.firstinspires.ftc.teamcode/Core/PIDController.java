package org.firstinspires.ftc.teamcode.Core;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

/** PIDController
 * Class used to streamline usage of DcMotor.RunMode.RUN_TO_POSITION.
 * Uses control theory to smoothly move parts to desired location.
 * If you are using this class, ensure the motor always starts in the same position for consistency.
 * See https://www.ctrlaltftc.com/the-pid-controller for info. */
public class PIDController {
    private DcMotor motor;
    // PID vars
    private double Kp, Ki, Kd;
    private final double integralSumMax = (Ki == 0) ? 0.25 : 0.25/Ki;
    private int goalPosition, currentPosition;
    private int error, pError;
    private double derivative, integralSum;
    // Measures time passed in millis
    ElapsedTime timer;

    /** @param motorName Name of motor when getting from hardwareMap.
     * @param Kp Proportional coefficient (P in PID)
     * @param Ki Integral coefficient (I in PID)
     * @param Kd Derivative coefficient (D in PID) */
    PIDController(HardwareMap hardwareMap, String motorName, double Kp, double Ki, double Kd) {
        // Initialize PID variables
        timer = new ElapsedTime();
        goalPosition = 0;
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;
        // Initialize motor
        motor = hardwareMap.get(DcMotor.class, motorName);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // encoder doesn't normally reset to zero
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /** Sets a new goal position for the PIDController to move towards. */
    protected void setGoalPosition(int goalPosition) {
        this.goalPosition = goalPosition;
        // Reset possible extremes
        pError = 0;
        integralSum = 0;
    }

    /** Moves the controller towards goalPosition encoder location.
     * If the PIDController has already reached goalPosition, no code is executed. */
    protected void update() {
        currentPosition = motor.getCurrentPosition();
        // Exit if already at goalPosition
        if (goalPosition == currentPosition) {
            return;
        }
        // Distance between goal and current
        error = goalPosition - currentPosition;

        // rate of change of error
        // timer.seconds() is time passed since last run
        derivative = (error - pError) / timer.seconds();

        // sum of all errors over time
        // timer.seconds() is time passed since last run
        integralSum += error * timer.seconds();

        // set power of motor
        motor.setPower(Kp * error +
                Ki * derivative +
                Kp * integralSum);

        pError = error;

        // reset timer for next instance
        timer.reset();
    }

    /** Overrides the set power from update() to the inputted power.
     * Run this code after update(), or overridePower() will do nothing. */
    protected void overridePower(double power) {
        motor.setPower(power);
    }
}

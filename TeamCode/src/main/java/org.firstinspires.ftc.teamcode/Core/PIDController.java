package org.firstinspires.ftc.teamcode.Core;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

/** PIDController
 * Class used to streamline usage of DcMotor.RunMode.RUN_TO_POSITION.
 * Uses control theory to smoothly move parts to desired location.
 * See https://www.ctrlaltftc.com for info.*/
public class PIDController {
    private DcMotor motor;
    // PID vars
    private final double Kp = 0, Ki = 0, Kd = 0;
    private final double integralSumMax = 0.25/Ki;
    private int goalPosition, currentPosition;
    private int error, pError;
    private double integralSum;
    // Measures time passed in millis
    ElapsedTime timer;

    /** @param motorName Name of motor when getting from hardwareMap.*/
    PIDController(HardwareMap hardwareMap, String motorName) {
        timer = new ElapsedTime();

        motor = hardwareMap.get(DcMotor.class, motorName);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // encoder doesn't normally reset to zero
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    protected void setGoalPosition(int goalPosition) {
        this.goalPosition = goalPosition;
        error = goalPosition - currentPosition;
        pError = 0;
        integralSum = 0;
    }

    /** Moves the controller towards the new position. */
    protected void update() {
        currentPosition = motor.getCurrentPosition();
        // Distance between goal and current
        error = goalPosition - currentPosition;

        // rate of change of error
        double derivative = (error - pError) / timer.seconds();

        // sum of all errors over time
        integralSum += error * timer.seconds();

        // set power of motor
        motor.setPower(Kp * error +
                Ki * derivative +
                Kp * integralSum);

        pError = error;

        // reset timer for next instance
        timer.reset();
    }
}

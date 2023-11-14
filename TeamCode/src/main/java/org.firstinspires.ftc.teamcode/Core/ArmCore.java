package org.firstinspires.ftc.teamcode.Core;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ArmCore {
    private DcMotor armMotor;

    ArmCore(HardwareMap hardwareMap) {
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    protected int getTargetPosition() {
        return armMotor.getTargetPosition();
    }

    protected int getCurrentPosition() {
        return armMotor.getCurrentPosition();
    }

    protected void goToEncoder(int encoder) {
        armMotor.setTargetPosition(encoder);
    }

    protected void moveByEncoder(int encoder) {
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setTargetPosition(encoder);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }


    /** Uses a PID controller to set power: https://www.ctrlaltftc.com/the-pid-controller
     * Updates motor use */
    protected void update() {
        int reference = armCore.getTargetPosition();
        // obtain encoder position
        int encoderPosition = armCore.getCurrentPosition();
        // calculate the error
        int error = reference - encoderPosition;

        // rate of change of the error
        derivative = (error - lastError) / timer.seconds();

        // sum of all error over time
        integralSum = integralSum + (error * timer.seconds());

        out = (Kp * error) + (Ki * integralSum) + (Kd * derivative);

        armMotor.setPower(out);

        lastError = error;

        // reset the timer for next time
        timer.reset();
        armMotor.setPower(1);
    }


    protected void telemetry(Telemetry telemetry) {
        telemetry.addData("CURRENT CLASS", "ArmCore.java");
        telemetry.addData("runMode", armMotor.getMode());
        telemetry.addData("targetPosition", armMotor.getTargetPosition());
        telemetry.addData("currentPosition", armMotor.getCurrentPosition());
        telemetry.addData("power", armMotor.getPower());
    }
}

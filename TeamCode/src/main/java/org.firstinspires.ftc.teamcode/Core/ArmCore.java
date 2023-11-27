package org.firstinspires.ftc.teamcode.Core;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/** Operates the arm of the robot.
 * Current, only setPower() and telemetry() are useful.
 * The current mode is RUN_USING_ENCODER, making multiple methods unusable.
 * Methods that don't work: getTargetPosition(), goToEncoder(), moveByEncoder()
 * */
public class ArmCore {
    private DcMotor armMotor;
    private PIDController armMotorNew;

    ArmCore(HardwareMap hardwareMap) {
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        armMotorNew = new PIDController(hardwareMap, "armMotor",
                0, 0, 0);
        // mode doesn't use encoders to set raw motor powers. more consistent this way
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /** Sets a new target position for the motor. */
    protected void setTargetPosition(int encoder) {
        armMotorNew.setTargetPosition(encoder);
    }

    /** Returns targetPosition */
    protected int getTargetPosition() {
        return armMotorNew.getTargetPosition();
    }

    /** Updates the PIDController to move towards the provided goal position. */
    protected void update() {
        armMotorNew.update();
    }

    /** Moves the arm motor using the inputted power. */
    protected void setPower(double power){
        armMotor.setPower(power);
    }

    /** Telemetry */
    protected void telemetry(Telemetry telemetry) {
        telemetry.addData("\nCURRENT CLASS", "ArmCore.java");
        telemetry.addData("runMode", armMotor.getMode());
        if (armMotor.getMode() == DcMotor.RunMode.RUN_TO_POSITION) {
            telemetry.addData("targetPosition", armMotor.getTargetPosition());
            telemetry.addData("currentPosition", armMotor.getCurrentPosition());
        }
        telemetry.addData("power", armMotor.getPower());
        armMotorNew.telemetry(telemetry);
    }
}

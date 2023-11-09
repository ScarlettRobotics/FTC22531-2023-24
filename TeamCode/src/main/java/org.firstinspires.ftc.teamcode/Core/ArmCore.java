package org.firstinspires.ftc.teamcode.Core;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ArmCore {
    private DcMotor armMotor;

    ArmCore(HardwareMap hardwareMap) {
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");

        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setTargetPosition(0);

        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    protected int getTargetPosition() {
        return armMotor.getTargetPosition();
    }

    protected void goToEncoder(int encoder) {
        armMotor.setTargetPosition(encoder);
    }

    protected void moveByEncoder(int encoder) {
        armMotor.setTargetPosition(armMotor.getTargetPosition() + encoder);
    }

    protected void update() {
        armMotor.setPower(1);
    }

    protected void telemetry(Telemetry telemetry) {
        telemetry.addData("CURRENT CLASS", "ArmCore.java");
        telemetry.addData("runMode", armMotor.getMode());
        telemetry.addData("currentPosition", armMotor.getCurrentPosition());
        telemetry.addData("targetPosition", armMotor.getTargetPosition());
        telemetry.addData("power", armMotor.getPower());
    }
}

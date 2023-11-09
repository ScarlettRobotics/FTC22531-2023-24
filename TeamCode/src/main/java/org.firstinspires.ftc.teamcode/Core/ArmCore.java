package org.firstinspires.ftc.teamcode.Core;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ArmCore {
    DcMotor armCore;

    ArmCore(HardwareMap hardwareMap) {
        armCore = hardwareMap.get(DcMotor.class, "armMotor");

        armCore.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armCore.setTargetPosition(0);

        armCore.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    protected void goToEncoder(int encoder) {
        armCore.setTargetPosition(encoder);
    }

    protected void moveByEncoder(int encoder) {
        armCore.setTargetPosition(armCore.getTargetPosition() + encoder);
    }

    protected void update() {
        armCore.setPower(1);
    }

    protected void telemetry(Telemetry telemetry) {
        telemetry.addData("CURRENT CLASS", "ArmCore.java");
        telemetry.addData("runMode", armCore.getMode());
        telemetry.addData("currentPosition", armCore.getCurrentPosition());
        telemetry.addData("targetPosition", armCore.getTargetPosition());
        telemetry.addData("power", armCore.getPower());
    }
}

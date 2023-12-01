package org.firstinspires.ftc.teamcode.Concepts;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.Core.ArmCore;

public class ArmPIDTest extends OpMode {
    ArmCore armCore;
    @Override
    public void init() {
        armCore = new ArmCore(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.dpad_up) armCore.setTargetPosition(300);
        if (gamepad1.dpad_right) armCore.setTargetPosition(100);
        if (gamepad1.dpad_down) armCore.setTargetPosition(0);
    }
}

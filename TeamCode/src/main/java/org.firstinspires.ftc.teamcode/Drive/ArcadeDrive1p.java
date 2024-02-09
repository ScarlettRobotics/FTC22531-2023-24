package org.firstinspires.ftc.teamcode.Drive;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Core.SystemsManager;

@TeleOp(name = "ArcadeDrive1p", group = " arcade")
public class ArcadeDrive1p extends SystemsManager {
    @Override
    public void loop() {
        // Telemetry
        telemetry(telemetry);
        // update drivetrain
        updateMotorArcade(1);
        updateArm(1);
        updateClaw(1);
        checkForDroneLaunch(1);
    }

}

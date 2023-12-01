package org.firstinspires.ftc.teamcode.Drive;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Core.SystemsManager;

@TeleOp(name = "TankDrive1p", group = "tank")
public class TankDrive1p extends SystemsManager {
    @Override
    public void loop() {
        // Telemetry
        telemetry(telemetry);
        // update drivetrain
        updateMotorTank(1);
        updateArm(1);
        updateClaw(1);
        checkForDroneLaunch(1);
    }

}

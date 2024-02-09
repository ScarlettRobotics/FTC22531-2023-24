package org.firstinspires.ftc.teamcode.Drive;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Core.*;

@TeleOp(name = "TankDrive2p", group = " tank")
public class TankDrive2p extends SystemsManager {

    @Override
    public void loop() {
        // Telemetry
        telemetry(telemetry);
        // update drivetrain
        updateMotorTank(1);
        updateArm(2);
        updateClaw(2);
        checkForDroneLaunch(1);
    }

}

package org.firstinspires.ftc.teamcode.Drive;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Core.*;

@TeleOp(name = "ArcadeDrive2p", group = "auto")
public class ArcadeDrive2p extends SystemsManager {

    @Override
    public void loop() {
        // Telemetry
        telemetry(telemetry);
        // update drivetrain
        updateMotorArcade(1);
        updateArm(2);
    }

}

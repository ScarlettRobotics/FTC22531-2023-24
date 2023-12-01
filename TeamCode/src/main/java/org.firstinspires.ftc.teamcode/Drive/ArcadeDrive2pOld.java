package org.firstinspires.ftc.teamcode.Drive;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Core.SystemsManager;

@TeleOp(name = "ArcadeDrive2pOld", group = "arcade")
public class ArcadeDrive2pOld extends SystemsManager {

    /*-2800
    -2670
-2620
            -2520
            -2400
            -2270
    -2100*/
    @Override
    public void loop() {
        // Telemetry
        telemetry(telemetry);
        // update drivetrain
        updateMotorArcade(1);
        updateArmBlind(2);
        updateClaw(2);
        checkForDroneLaunch(1);
    }

}

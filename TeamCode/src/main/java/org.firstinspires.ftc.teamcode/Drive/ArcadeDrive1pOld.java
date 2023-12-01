package org.firstinspires.ftc.teamcode.Drive;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Core.SystemsManager;

@TeleOp(name = "ArcadeDrive1pOld", group = "arcade")
public class ArcadeDrive1pOld extends SystemsManager {
    @Override
    public void loop() {
        // Telemetry
        telemetry(telemetry);
        // update drivetrain
        updateMotorArcade(1);
        updateArmBlind(1);
        updateClaw(1);
        checkForDroneLaunch(1);
    }

}

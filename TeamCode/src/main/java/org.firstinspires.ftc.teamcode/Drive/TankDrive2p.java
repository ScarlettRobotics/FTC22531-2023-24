package org.firstinspires.ftc.teamcode.Drive;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Core.*;


@TeleOp(name = "TankDrive2p", group = "auto")
public class TankDrive2p extends SystemsManager {

    @Override
    public void loop() {
        // Telemetry
        telemetry(telemetry);
        // update drivetrain
        updateMotorTank(1);
        // update the slide
        updateSlide(2);
    }

    public void telemetry(Telemetry telemetry) {
        drive.telemetry(telemetry);
    }
}

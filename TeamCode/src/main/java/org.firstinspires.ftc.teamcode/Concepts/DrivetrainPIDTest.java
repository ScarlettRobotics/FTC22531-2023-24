package org.firstinspires.ftc.teamcode.Concepts;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Core.DrivetrainCore;

/** Moves the arm to preset locations.
 * Use FTC dashboard to see how PID variables need to be tweaked. */
@TeleOp(name = "DrivetrainPIDTest", group = "concepts")
public class DrivetrainPIDTest extends OpMode {
    DrivetrainCore drivetrainCore;
    FtcDashboard dashboard;
    Telemetry dashboardTelemetry;
    @Override
    public void init() {
        drivetrainCore = new DrivetrainCore(hardwareMap);
        // Telemetry
        telemetry.addData("STATUS: ", "Initialized"); // the FTC equivalent to println()
        telemetry.addData("FTC Team #", "22531");
        // Initialize FTC Dashboard variables
        dashboard = FtcDashboard.getInstance();
        dashboardTelemetry = dashboard.getTelemetry();
    }

    @Override
    public void loop() {
        if (gamepad1.dpad_up) drivetrainCore.moveByEncoder(300, 300);
        if (gamepad1.dpad_down) drivetrainCore.moveByEncoder(-300, -300);
        if (gamepad1.dpad_left) drivetrainCore.moveByEncoder(-200, 200);
        if (gamepad1.dpad_right) drivetrainCore.moveByEncoder(200, -200);
        drivetrainCore.updateAuto();
        // Telemetry
        drivetrainCore.telemetry(telemetry);
        drivetrainCore.telemetry(dashboardTelemetry);
        dashboardTelemetry.update();
    }
}
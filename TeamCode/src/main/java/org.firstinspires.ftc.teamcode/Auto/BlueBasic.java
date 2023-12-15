package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.checkerframework.checker.units.qual.C;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Core.*;

/** Class containing basics of an autonomous class.
 * Don't edit this class directly. Instead, make a copy, then edit the new class.
 * When editing, change the next two lines to an appropriate name for your new autonomous file. */
@Autonomous(name = "BlueBasic", group = "blue")
public class BlueBasic extends LinearOpMode {
    // FTC Dashboard
    private FtcDashboard dashboard;
    private Telemetry dashboardTelemetry;
    // Timing related
    private ElapsedTime timer;
    private EventManager eventManager;
    // Core classes
    DrivetrainCore drivetrainCore;
    ArmCore armCore;
    ClawCore clawCore;
    DroneLauncherCore droneLauncherCore;

    @Override
    public void runOpMode() {
        initialize();

        waitForStart();
        timer.reset();

        while (opModeIsActive()) {
            updateAuto();

            if (eventManager.eventOccurred(timer.time(), 0)) {
                /* TODO INSTRUCTION */
            } // event desc

            if (eventManager.eventOccurred(timer.time(), 1)) {
                /* TODO INSTRUCTION */
            } // event desc

            if (eventManager.eventOccurred(timer.time(), 2)) {
                /* TODO INSTRUCTION */
            } // event desc

            /* TODO etc. */

            addTelemetry(telemetry);
        }
    }

    private void initialize() {
        // Init dashboard
        dashboard = FtcDashboard.getInstance();
        dashboardTelemetry = dashboard.getTelemetry();
        // Init timing related
        timer = new ElapsedTime(ElapsedTime.Resolution.SECONDS);
        eventManager = new EventManager();
        // Init timings
        eventManager.addEvent(2); // event desc
        //TODO eventManager.addEvent(xx); // event desc
        //TODO eventManager.addEvent(xx); // event desc
        /* TODO etc. */
        // Init core classes
        drivetrainCore = new DrivetrainCore(hardwareMap);
        armCore = new ArmCore(hardwareMap);
        clawCore = new ClawCore(hardwareMap);
        droneLauncherCore = new DroneLauncherCore(hardwareMap);
        // Init telemetry
        telemetry.addData("STATUS", "Initialized");
        telemetry.update();
        dashboardTelemetry.addData("STATUS", "Initialized");
        dashboardTelemetry.update();
        // Close claw to grip pixels
        clawCore.close();
    }

    private void updateAuto() {
        drivetrainCore.updateAuto();
        armCore.updateAuto();
    }

    private void addTelemetry(Telemetry telemetry) {
        // Telemetry
        telemetry.addData("timer", timer.time());
        eventManager.telemetry(telemetry); //reuse ".telemetry(telemetry)"
        drivetrainCore.telemetry(telemetry);
        armCore.telemetry(telemetry);
        clawCore.telemetry(telemetry);
        droneLauncherCore.telemetry(telemetry);
        telemetry.update();
        // FTC Dashboard
        dashboardTelemetry.addData("timer", timer.time());
        eventManager.telemetry(dashboardTelemetry); //reuse ".telemetry(dashboardTelemetry)"
        drivetrainCore.telemetry(dashboardTelemetry);
        armCore.telemetry(dashboardTelemetry);
        clawCore.telemetry(dashboardTelemetry);
        droneLauncherCore.telemetry(dashboardTelemetry);
        dashboardTelemetry.update();
    }
}
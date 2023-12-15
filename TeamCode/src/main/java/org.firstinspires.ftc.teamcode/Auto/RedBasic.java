package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Core.*;

/** Class containing basics of an autonomous class.
 * Don't edit this class directly. Instead, make a copy, then edit the new class.
 * When editing, change the next two lines to an appropriate name for your new autonomous file. */
@Autonomous(name = "BlueBasic", group = "blue")
public class RedBasic extends LinearOpMode {
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
            } // end rotate 90 degrees

            if (eventManager.eventOccurred(timer.time(), 1)) {
                /* TODO INSTRUCTION */
            } // end forward to backdrop, move arm to backdrop position

            if (eventManager.eventOccurred(timer.time(), 2)) {
                /* TODO INSTRUCTION */
            } // end open claw

            if (eventManager.eventOccurred(timer.time(), 3)) {
                /* TODO INSTRUCTION */
            } // end slightly move back

            if (eventManager.eventOccurred(timer.time(), 4)) {
                /* TODO INSTRUCTION */
            } // end slighly move back

            if (eventManager.eventOccurred(timer.time(), 5)) {
                /* TODO INSTRUCTION */
            } // end rotate 90 degrees right

            if (eventManager.eventOccurred(timer.time(), 6)) {
                /* TODO INSTRUCTION */
            } // end move back to stop blocking backdrop

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
        eventManager.addEvent(1); // move forward
        //TODO eventManager.addEvent(xx); // rotate 90 degrees
        //TODO eventManager.addEvent(xx); // forward to backdrop, move arm to backdrop position
        //TODO eventManager.addEvent(xx); // open claw
        //TODO eventManager.addEvent(xx); // slightly move back
        //TODO eventManager.addEvent(xx); // slightly move back
        //TODO eventManager.addEvent(xx); // rotate 90 degrees right
        //TODO eventManager.addEvent(xx); // move back to stop blocking backdrop
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
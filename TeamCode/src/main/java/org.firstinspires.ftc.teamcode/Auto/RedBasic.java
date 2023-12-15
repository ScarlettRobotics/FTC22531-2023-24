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
@Autonomous(name = "RedBasic", group = "red")
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
                drivetrainCore.forwardByEncoder(-1700);
            } // end move forward 1 space

            if (eventManager.eventOccurred(timer.time(), 1)) {
                drivetrainCore.rotateByEncoder(600);
            } // end rotate 90 degrees

            if (eventManager.eventOccurred(timer.time(), 2)) {
                drivetrainCore.forwardByEncoder(-2100);
                armCore.setTargetPosition(-2400);
            } // end forward to backdrop, move arm to backdrop position

            if (eventManager.eventOccurred(timer.time(), 3)) {
                clawCore.open();
            } // end open claw

            if (eventManager.eventOccurred(timer.time(), 4)) {
                drivetrainCore.forwardByEncoder(150);
            } // end slightly move back

            if (eventManager.eventOccurred(timer.time(), 5)) {
                drivetrainCore.forwardByEncoder(150);
            } // end slighly move back

            if (eventManager.eventOccurred(timer.time(), 6)) {
                drivetrainCore.rotateByEncoder(-300);
                armCore.setTargetPosition(-500);
            } // end rotate 90 degrees left, move arm back

            if (eventManager.eventOccurred(timer.time(), 7)) {
                drivetrainCore.forwardByEncoder(-1000);
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
        eventManager.addEvent(1); // move forward 1 space
        eventManager.addEvent(2); // rotate 90 degrees
        eventManager.addEvent(3); // forward to backdrop, move arm to backdrop position
        eventManager.addEvent(5); // open claw
        eventManager.addEvent(5.5); // slightly move back
        eventManager.addEvent(6); // slightly move back
        eventManager.addEvent(6.5); // rotate 90 degrees left, move arm back
        eventManager.addEvent(8); // move back to stop blocking backdrop
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
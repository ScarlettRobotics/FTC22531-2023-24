package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.AutoCore.EventManager;
import org.firstinspires.ftc.teamcode.AutoCore.TensorFlowCore;
import org.firstinspires.ftc.teamcode.AutoCore.VisionPortalCore;
import org.firstinspires.ftc.teamcode.Core.ArmCore;
import org.firstinspires.ftc.teamcode.Core.ClawCore;
import org.firstinspires.ftc.teamcode.Core.DrivetrainCore;

/** Used if on the close side of movement. Place where wheels touch right teeth.
 * Places yellow pixel based on prop position, then places purple pixel on backdrop based on prop position. */
@Autonomous(name = "GeneralPropDetectionLeft", group = "general")
public class GeneralPropDetectionLeft extends LinearOpMode {
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
    // Vision classes
    VisionPortalCore visionPortalCore;
    TensorFlowCore tensorFlowCore;
    // Other
    int propLocation; // 0-1-2 is left-middle-right

    private void initialize() {
        // Init dashboard
        dashboard = FtcDashboard.getInstance();
        dashboardTelemetry = dashboard.getTelemetry();
        // Init timing related
        timer = new ElapsedTime(ElapsedTime.Resolution.SECONDS);
        eventManager = new EventManager();
        // Init timings
        eventManager.addEvent(6); // find prop, move forward to avoid wall
        eventManager.addEvent(7); // rotate to align with location 2
        eventManager.addEvent(8); // move forward based on prop location, move arm to safe
        eventManager.addEvent(10); // rotate to align with prop location, arm down
        eventManager.addEvent(12); // if propLocation == 0, move back
        eventManager.addEvent(14); // move forward to prop
        eventManager.addEvent(16); // open claw
        eventManager.addEvent(17); // move back

        // Init core classes
        drivetrainCore = new DrivetrainCore(hardwareMap);
        armCore = new ArmCore(hardwareMap);
        clawCore = new ClawCore(hardwareMap);
        // Init vision classes
        visionPortalCore = new VisionPortalCore(hardwareMap);
        tensorFlowCore = new TensorFlowCore(hardwareMap, visionPortalCore.builder);
        visionPortalCore.build();
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

    @Override
    public void runOpMode() {
        initialize();

        waitForStart();
        timer.reset();

        while (opModeIsActive()) {
            updateAuto();

            if (eventManager.eventOccurred(timer.time(), 0)) {
                if (!tensorFlowCore.recognizing()) { // not recognizing any cubes
                    propLocation = 2;
                } else if (tensorFlowCore.getX() > (double) 640/2) { // to right of camera
                    propLocation = 1;
                } else { // to right of camera
                    propLocation = 0;
                }
                visionPortalCore.stopStreaming();
                drivetrainCore.forwardByEncoder(130);
            } // end find prop, move forward to avoid wall
            if (eventManager.eventOccurred(timer.time(), 1)) {
                drivetrainCore.rotateByEncoder(300);
            } // end rotate to align with location 2
            if (propLocation == 0) { // Left
                if (eventManager.eventOccurred(timer.time(), 2)) {
                    drivetrainCore.forwardByEncoder(500);
                    armCore.setTargetPosition(-2500);
                } // end move forward based on prop location, arm safe
                if (eventManager.eventOccurred(timer.time(), 3)) {
                    drivetrainCore.rotateByEncoder(-350);
                } // end rotate to align with prop location
                if (eventManager.eventOccurred(timer.time(), 4)) {
                    drivetrainCore.forwardByEncoder(-200);
                    armCore.setTargetPosition(-3300);
                } // end if propLocation == 0, move back, arm down
                if (eventManager.eventOccurred(timer.time(), 5)) {
                    drivetrainCore.forwardByEncoder(300);
                } // end move forward to prop
                if (eventManager.eventOccurred(timer.time(), 6)) {
                    drivetrainCore.forwardByEncoder(150);
                    clawCore.open();
                } // end open claw
                if (eventManager.eventOccurred(timer.time(), 7)) {
                    drivetrainCore.forwardByEncoder(-200);
                    armCore.setTargetPosition(-2500);
                } // end move back, move arm to safe
            }
            else if (propLocation == 1) { // Middle
                if (eventManager.eventOccurred(timer.time(), 2)) {
                    drivetrainCore.forwardByEncoder(300);
                    armCore.setTargetPosition(-2500);
                } // end move forward based on prop location, arm safe
                if (eventManager.eventOccurred(timer.time(), 3)) {
                    drivetrainCore.rotateByEncoder(-180);
                    armCore.setTargetPosition(-3300);
                } // end rotate to align with prop location, arm down
                if (eventManager.eventOccurred(timer.time(), 4)) {
                    drivetrainCore.forwardByEncoder(500);
                } // end move forward to prop
                if (eventManager.eventOccurred(timer.time(), 6)) {
                    clawCore.open();
                } // end open claw
                if (eventManager.eventOccurred(timer.time(), 7)) {
                    drivetrainCore.forwardByEncoder(-200);
                    armCore.setTargetPosition(-2500);
                } // end move back, move arm to safe
            }
            else { // Right
                if (eventManager.eventOccurred(timer.time(), 2)) {
                    drivetrainCore.forwardByEncoder(300);
                    armCore.setTargetPosition(-2500);
                } // end move forward based on prop location, arm safe
                if (eventManager.eventOccurred(timer.time(), 3)) {
                    armCore.setTargetPosition(-3300);
                } // end arm down
                if (eventManager.eventOccurred(timer.time(), 4)) {
                    drivetrainCore.forwardByEncoder(200);
                } // end move forward to prop
                if (eventManager.eventOccurred(timer.time(), 6)) {
                    clawCore.open();
                } // end open claw
                if (eventManager.eventOccurred(timer.time(), 7)) {
                    drivetrainCore.forwardByEncoder(-200);
                    armCore.setTargetPosition(-2500);
                } // end move back, move arm to safe
            }

            // TELEMETRY
            addTelemetry(telemetry);
        }
    }

    private void addTelemetry(Telemetry telemetry) {
        // Telemetry
        addTelemetryInstance(telemetry);
        // FTC Dashboard
        addTelemetryInstance(dashboardTelemetry);
    }

    private void addTelemetryInstance(Telemetry telemetry) {
        telemetry.addData("timer", timer.time());
        eventManager.telemetry(telemetry); //reuse ".telemetry(dashboardTelemetry)"
        drivetrainCore.telemetry(telemetry);
        clawCore.telemetry(telemetry);
        tensorFlowCore.telemetry(telemetry);
        telemetry.update();
    }
}
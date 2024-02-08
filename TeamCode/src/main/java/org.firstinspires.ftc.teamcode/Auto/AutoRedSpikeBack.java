package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Core.*;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

/** Used if on the close side of movement. Place where wheels touch right teeth.
 * Places yellow pixel based on prop position, then places purple pixel on backdrop based on prop position. */
@Autonomous(name = "BlueCloseDetection", group = "blue")
public class AutoRedSpikeBack extends LinearOpMode {
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
    TensorFlowCore tensorFlowCore;
    AprilTagCore aprilTagCore;
    // Other
    int propLocation; // 0-1-2 is left-middle-right
    PIDControllerSimple aprilTagAlignerPID;

    private void initialize() {
        // Init dashboard
        dashboard = FtcDashboard.getInstance();
        dashboardTelemetry = dashboard.getTelemetry();
        // Init timing related
        timer = new ElapsedTime(ElapsedTime.Resolution.SECONDS);
        eventManager = new EventManager();
        // Init timings
        eventManager.addEvent(1); // find prop, strafe to align with centre, move arm to safe
        eventManager.addEvent(2.5); // move towards position based on prop
        eventManager.addEvent(4.5); // rotate based on prop
        eventManager.addEvent(5.5); // move purple forward to meet tape

        eventManager.addEvent(7); // move back to org pos
        eventManager.addEvent(8.5); // rotate back to align with AprilTag
        eventManager.addEvent(10); // move forward to see AprilTag
        eventManager.addEvent(12); // align with AprilTag based on propLocation
        eventManager.addEvent(13); // strafe to center
        eventManager.addEvent(14); // move forward to backdrop, set arm to drop pos

        eventManager.addEvent(15.5); // open claw
        eventManager.addEvent(16); // move arm to safe pos, strafe to edge based on propLocation
        eventManager.addEvent(17.5); // move into park

        // Init core classes
        drivetrainCore = new DrivetrainCore(hardwareMap);
        armCore = new ArmCore(hardwareMap);
        clawCore = new ClawCore(hardwareMap);
        tensorFlowCore = new TensorFlowCore(hardwareMap);
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
                } else if (tensorFlowCore.getX() > (double) 640 /2) { // to right of camera
                    propLocation = 1;
                } else { // to right of camera
                    propLocation = 0;
                }
                propLocation = 0; // TODO debug forced propLocation
                armCore.setTargetPosition(-2000);
                tensorFlowCore.stopStreaming();
            } // end find prop, strafe to align with centre, move arm to safe
            if (eventManager.eventOccurred(timer.time(), 1)) {
                if (propLocation == 1) {
                    // If prop in middle, drive straight forward to place pixel on spike mark.
                } else {
                    // Otherwise, move forward far enough to be able to rotate 90 degrees
                    // and still be able to place pixel on spike mark
                }
            } // end move towards position based on prop
            if (eventManager.eventOccurred(timer.time(), 2)) {
                if (propLocation == 0) {
                    // Rotate to left
                } else if (propLocation == 2) {
                    // Rotate to right
                }
            } // end rotate based on prop
            if (eventManager.eventOccurred(timer.time(), 3)) {
                if (propLocation != 1) {
                    // If the prop isn't in the middle, then that means that by now,
                    // the robot is far enough forward to place the pixel on the spike mark.
                }
            } // end move purple forward to meet tape

            if (eventManager.eventOccurred(timer.time(), 4)) {
                if (propLocation == 1) {
                    // If the prop is in the middle, move forward enough so that the robot can
                    // move out of the way of the pixel on the spike mark.
                } else {
                    // Move forward. This will need to help align the robot with the correct side
                    // on the backdrop.
                }
            } // end move back to org pos
            if (eventManager.eventOccurred(timer.time(), 5)) {
                if (propLocation == 0) {
                    // Rotate far enough to be able to move towards left side of backdrop
                } else if (propLocation == 1) {
                    // Rotate far enough to be able to move toward middle of backdrop
                } else {
                    // Rotate far enough to be able to move toward right side of backdrop
                }
            } // end rotate back to align with AprilTag
            if (eventManager.eventOccurred(timer.time(), 6)) {
                if (propLocation != 1) {
                    // If the pixel will go on either side of the backdrop, move to align with that selection.
                }
            } // end strafe to center

            if (eventManager.eventOccurred(timer.time(), 7)) {
                // Move close to enough backdrops for camera to register apriltag
            } // end move forward to see AprilTag
            if (eventManager.eventOccurred(timer.time(), 8)) {
                aprilTagCore = new AprilTagCore(hardwareMap, 2);
                aprilTagAlignerPID = new PIDControllerSimple("AprilTag aligner", 0, 0, 0, 0.3);
                aprilTagAlignerPID.setTargetPosition(0); // goal of X = 0 with apriltag
            } // end align with AprilTag based on propLocation
            if (eventManager.getActionTaken(8) && !eventManager.getActionTaken(9)) {
                // Update aligner with correct AprilTag ID
                List<AprilTagDetection> currentDetections = aprilTagCore.getDetections();
                for (AprilTagDetection detection : currentDetections) {
                    // Align with ID that is same position as propLocation
                    // Works with red only, as blue has different AprilTags and IDs (+4 will have to be changed)
                    if (detection.id != propLocation+4) continue;
                    aprilTagAlignerPID.update(detection.ftcPose.x);
                    break;
                }
                // Move drivetrain based on AprilTag
                //
                // IMPORTANT: This will potentially involve changing when certain actions happen after this script
                //
            } // end align with AprilTag
            if (eventManager.eventOccurred(timer.time(), 9)) {
                aprilTagCore.closeVisionPortal();
                // Move forward to backdrop
                // Move claw down
            } // end move forward to backdrop, set arm to drop pos

            if (eventManager.eventOccurred(timer.time(), 10)) {
                // Open claw.
                clawCore.open();
            } // end open claw

            // The robot will need to rotate 90 degrees towards whichever direction backstage is.
            // After the (currently unwritten) script above is run, the robot's long side should be
            // parallel to the backdrop.

            if (eventManager.eventOccurred(timer.time(), 11)) {
                if (propLocation == 0) {
                    // Move far enough that the robot can turn 90 degrees into the backdrop
                } else if (propLocation == 1) {
                    // Move far enough that the robot can turn 90 degrees into the backdrop
                } else {
                    // Move far enough that the robot can turn 90 degrees into the backdrop
                }
                // Bring arm down
            } // end move arm to safe pos, move to edge based on propLocation
            if (eventManager.eventOccurred(timer.time(), 12)) {
                // Rotate 90 degrees into the backstage, move forward into backstage.
            } // end move into park

            addTelemetry(telemetry);
        }
    }

    private void addTelemetry(Telemetry telemetry) {
        // Telemetry
        telemetry.addData("timer", timer.time());
        eventManager.telemetry(telemetry); //reuse ".telemetry(telemetry)"
        drivetrainCore.telemetry(telemetry);
        armCore.telemetry(telemetry);
        clawCore.telemetry(telemetry);
        tensorFlowCore.telemetry(telemetry);
        if (aprilTagCore != null) {
            aprilTagCore.telemetry(telemetry);
        }
        telemetry.update();
        // FTC Dashboard
        dashboardTelemetry.addData("timer", timer.time());
        eventManager.telemetry(dashboardTelemetry); //reuse ".telemetry(dashboardTelemetry)"
        drivetrainCore.telemetry(dashboardTelemetry);
        armCore.telemetry(dashboardTelemetry);
        clawCore.telemetry(dashboardTelemetry);
        tensorFlowCore.telemetry(dashboardTelemetry);
        if (aprilTagCore != null) {
            aprilTagCore.telemetry(dashboardTelemetry);
        }
        dashboardTelemetry.update();
    }
}
package org.firstinspires.ftc.teamcode.Auto;

/** Detects the sleeve detected on the camera, then corrects itself to the right position.
 * Once the robot has done this, it moves forward 2 squares to land in the parking spot. */

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Core.*;
import org.firstinspires.ftc.teamcode.Core.CV.*;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Standard Auto", group="Auto")

public class StandardAuto extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

    protected TriMotorDrive drive;
    protected ClawCore claw;
    protected SlideCore slide;
    protected WebcamCore webcam;
    protected CameraServoCore cameraServo;

    protected AutoEventHandler autoEventHandler;

    @Override
    public void runOpMode() {
        initialize();

        waitForStart();

        runtime.reset();
        cameraServo.resetCameraServo();

        // TODO rightMotor moves before leftMotor; they should move at the same time
        // strafe right to center on tile

        // run until the end of match (driver pressed STOP)
        while(opModeIsActive()) {
            if (autoEventHandler.actionOccurred(0, runtime.time())) {
                claw.close();
            }


            // Align with correct position
            if (autoEventHandler.actionOccurred(1, runtime.time())) {
                switch (webcam.pipeline.getSleevePos()) {
                    case 1:
                        drive.moveInches(0, 0, -45);
                        break;
                    case 3:
                        drive.moveInches(0, 0, 45);
                        break;
                }
            }

            // Move forward 2 tiles
            if (autoEventHandler.actionOccurred(2, runtime.time())) {
                drive.moveInches(30, 30, 0);
            }

            drive.update();
            addTelemetry(telemetry);
        }
    }

    /** Runs everything related to class setup */
    private void initialize() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //init motor/servo classes
        drive = new TriMotorDrive(hardwareMap);
        claw = new ClawCore(hardwareMap);
        slide = new SlideCore(hardwareMap);
        //init webcam classes
        webcam = new WebcamCore(hardwareMap);
        cameraServo = new CameraServoCore(hardwareMap);
        //init auto classes
        autoEventHandler = new AutoEventHandler();

        // Add times where the robot takes actions
        autoEventHandler.addDetectionTime(2000);
        autoEventHandler.addDetectionTime(2500);
        autoEventHandler.addDetectionTime(4000);
    }

    private void addTelemetry(Telemetry telemetry) {
        telemetry.addData("FTC Team #", "22531");
        telemetry.addData("Elapsed time", "%4.2f", runtime.time());
        drive.telemetry(telemetry);
        claw.telemetry(telemetry);
        webcam.pipeline.telemetry(telemetry);
        autoEventHandler.telemetry(telemetry);
        telemetry.update();
    }
}

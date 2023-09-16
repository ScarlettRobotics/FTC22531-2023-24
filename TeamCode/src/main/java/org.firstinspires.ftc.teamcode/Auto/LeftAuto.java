package org.firstinspires.ftc.teamcode.Auto;

/** MOVEMENTS TO MAKE:
 * adjust to high junction
 * strafe 7 in
 * move 52 in forward
 * strafe 12 in
 * CASES:
 *  1: strafe 12 in/36 in
 *  2: strafe 12 in
 *  3: strafe 36 in/12 in */

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Core.*;
import org.firstinspires.ftc.teamcode.Core.CV.*;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Left Auto", group="Auto")

public class LeftAuto extends LinearOpMode {
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
            // Close claw
            if (autoEventHandler.actionOccurred(0, runtime.time())) {
                claw.close();
            }
            // Centre, and close claw
            if (autoEventHandler.actionOccurred(1, runtime.time())) {
                drive.moveInches(0, 0, 9);
            }

            // Move forward 2 tiles, and move slide up
            if (autoEventHandler.actionOccurred(2, runtime.time())) {
                drive.moveInches(48, 48, 0);
                //slide.slideManual(0.8);
            }

            // Centre with pole
            if (autoEventHandler.actionOccurred(3, runtime.time())) {
                drive.moveInches(0, 0, 11);
            }

            // Move slightly forward
            if (autoEventHandler.actionOccurred(4, runtime.time())) {
                drive.moveInches(7, 7, 0);
            }

            // Slightly move slide down
            if (autoEventHandler.actionOccurred(5, runtime.time())) {
                //slide.slideManual(-0.6);
            }

            // Drop cone on slide
            if (autoEventHandler.actionOccurred(6, runtime.time())) {
                //claw.open();
                //drive.moveInches(-7, -7, 0);
            }

            // Park to correct position
            if (autoEventHandler.actionOccurred(7, runtime.time())) {
                switch (webcam.pipeline.getSleevePos()) {
                    case 1:
                        drive.moveInches(0, 0, -33);
                        break;
                    case 3:
                        drive.moveInches(0, 0, 11);
                        break;
                    default:
                        drive.moveInches(0, 0, -11); //Case 2
                }
            }

            if (autoEventHandler.actionOccurred(8, runtime.time())) {
                //slide.slideManual(0);
            }

            // Clap while waiting
            if (runtime.time() >= 13500) {
                if ((int)(runtime.time() / 400) % 2 == 1) {
                    //claw.open();
                } else {
                    //claw.close();
                }
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
        autoEventHandler.addDetectionTime(0);
        autoEventHandler.addDetectionTime(500);
        autoEventHandler.addDetectionTime(1500);
        autoEventHandler.addDetectionTime(5500);
        autoEventHandler.addDetectionTime(8500);
        autoEventHandler.addDetectionTime(9500);
        autoEventHandler.addDetectionTime(9800);
        autoEventHandler.addDetectionTime(11500);
        autoEventHandler.addDetectionTime(13500);
    }

    private void addTelemetry(Telemetry telemetry) {
        telemetry.addData("FTC Team #", "22531");
        telemetry.addData("Elapsed time", "%4.2f", runtime.time());
        drive.telemetry(telemetry);
        webcam.pipeline.telemetry(telemetry);
        autoEventHandler.telemetry(telemetry);
        telemetry.update();
    }
}

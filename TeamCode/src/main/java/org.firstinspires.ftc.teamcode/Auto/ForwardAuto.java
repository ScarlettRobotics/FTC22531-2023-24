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
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Core.AutoEventHandler;
import org.firstinspires.ftc.teamcode.Core.CV.WebcamCore;
import org.firstinspires.ftc.teamcode.Core.ClawCore;
import org.firstinspires.ftc.teamcode.Core.TriMotorDrive;
import org.firstinspires.ftc.teamcode.Core.SlideCore;

@Autonomous(name="Forward Auto", group="Auto")

public class ForwardAuto extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

    private AutoEventHandler autoEventHandler;
    protected TriMotorDrive drive;
    protected ClawCore claw;
    protected SlideCore slide;
    protected WebcamCore webcam;

    @Override
    public void runOpMode() {
        initialize();

        waitForStart();
        runtime.reset();

        // run until the end of match (driver pressed STOP)
        while(opModeIsActive()) {
            //Constantly Ran
            drive.update();
            addTelemetry(telemetry);
            telemetry.update();

            if (autoEventHandler.actionOccurred(0, runtime.time())) {
                claw.close();
            }

            //proceed forward
            if (autoEventHandler.actionOccurred(1, runtime.time())) {
                drive.moveInches(-45, -45, 0);
            }
        }


    }

    /** Runs everything related to class setup */
    private void initialize() {
        // Define classes
        drive = new TriMotorDrive(hardwareMap);
        claw = new ClawCore(hardwareMap);
        slide = new SlideCore(hardwareMap);

        webcam = new WebcamCore(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //AutoEventHandler
        autoEventHandler = new AutoEventHandler();
        autoEventHandler.addDetectionTime(0);
        autoEventHandler.addDetectionTime(500);
    }

    private void addTelemetry(Telemetry telemetry) {
        telemetry.addData("FTC Team #", "20718");
        telemetry.addData("Elapsed time", "%4.2f", runtime.time());
        drive.telemetry(telemetry);
        webcam.pipeline.telemetry(telemetry);
        autoEventHandler.telemetry(telemetry);
        telemetry.update();
    }
}

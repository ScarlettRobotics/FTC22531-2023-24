package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Core.*;
import org.firstinspires.ftc.teamcode.Core.CV.WebcamCore;

/*** Constantly updates the camera, returning the sleeve that it detects. */
@Autonomous(name="Camera Test", group = "Auto")
public class CameraTest extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

    protected WebcamCore webcam;
    protected CameraServoCore cameraServo;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //init webcam classes
        webcam = new WebcamCore(hardwareMap);
        cameraServo = new CameraServoCore(hardwareMap);

        waitForStart();

        runtime.reset();
        cameraServo.resetCameraServo();

        // Loop until end of autonomous
        while(opModeIsActive()) {
            addTelemetry(telemetry);
        }
    }

    private void addTelemetry(Telemetry telemetry) {
        telemetry.addData("FTC Team #", "22531");
        telemetry.addData("Elapsed time", "%4.2f", runtime.time());
        webcam.pipeline.telemetry(telemetry);
        telemetry.update();
    }
}

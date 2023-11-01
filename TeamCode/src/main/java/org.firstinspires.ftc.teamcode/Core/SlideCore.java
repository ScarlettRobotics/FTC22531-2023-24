package org.firstinspires.ftc.teamcode.Core;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * SlideCore
 * Handles inputs to the slide mechanism's motor.
 */
public class SlideCore {
    /* Initialization */
    /** Initialization is done within SlideCore for ease of access. */
    protected final DcMotor slideMotor;

    // Map DC motor variable to driver hub
    public SlideCore (HardwareMap hardwareMap) {
        slideMotor = hardwareMap.get(DcMotor.class, "slide_motor");
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void slideManual(double power){
        slideMotor.setPower(power);
    }

    /** Telemetry in contained in each class for ease of access. */
    public void telemetry(Telemetry telemetry) {
        telemetry.addData("\nCurrent class", "SlideCore.java");
        telemetry.addData("Slide Y", slideMotor.getCurrentPosition());
    }

}

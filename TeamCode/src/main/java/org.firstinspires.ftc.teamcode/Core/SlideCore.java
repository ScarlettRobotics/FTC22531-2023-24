package org.firstinspires.ftc.teamcode.Core;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * SlideCore
 * Handles inputs to the slide mechanism's motor.
 */
public class SlideCore {
    // Initialize DC motor variable
    protected final DcMotor slideMotor;
    // Position that the slide wants to be at (value is at lowest position)
    private int goalPosition = -510;

    // Map DC motor variable to driver hub
    public SlideCore (HardwareMap hardwareMap) {
        slideMotor = hardwareMap.get(DcMotor.class, "slide_motor");
        slideMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void slideUp(){
        slideMotor.setPower(0.9);
    }

    public void slideDown(){
        slideMotor.setPower(-0.9);
    }

    public void slideStop(){
        slideMotor.setPower(0);
    }
    public void slideManual(double power){
        slideMotor.setPower(power);
    }


    public void telemetry(Telemetry telemetry) {
        telemetry.addData("\nCurrent class", "SlideCore.java");
        telemetry.addData("Slide Y", slideMotor.getCurrentPosition());
    }

}

package org.firstinspires.ftc.teamcode.Core;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class CameraServoCore {
    private Servo cameraServo;

    public CameraServoCore (HardwareMap hardwareMap){
        cameraServo = hardwareMap.get(Servo.class, "webcam_servo");
    }

    public void resetCameraServo(){
        setCameraServo(0.90);
    }

    public void setCameraServo(double pos) {
        cameraServo.setPosition(pos);
    }

    public void telemetry(Telemetry telemetry){
        telemetry.addData("\nCurrent class:", "CameraServoCore.java");
        telemetry.addData("Camera servo pos:",
                "%4.2f", cameraServo.getPosition());
    }
}

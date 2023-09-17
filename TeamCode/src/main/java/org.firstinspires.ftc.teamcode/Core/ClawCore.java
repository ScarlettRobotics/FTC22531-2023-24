package org.firstinspires.ftc.teamcode.Core;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ClawCore {
    // Initialize claw variables
    private Servo claw;
    private double clawGoalPos;

    // Map claw variables to driver hub
    public ClawCore (HardwareMap hardwareMap) {
        claw = hardwareMap.get(Servo.class, "claw");
        clawGoalPos = claw.getPosition();
    }

    public void move(double strength) {
        clawGoalPos += strength;
    }

    public void update() {
        claw.setPosition(clawGoalPos);
    }

    public void telemetry(Telemetry telemetry) {
        telemetry.addData("\nCurrent class", "ClawCore.java");
        telemetry.addData("claw position", claw.getPosition());
    }
}

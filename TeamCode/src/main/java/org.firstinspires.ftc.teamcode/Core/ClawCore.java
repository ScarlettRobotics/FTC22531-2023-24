package org.firstinspires.ftc.teamcode.Core;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/** Operates the claw of the robot.
 * Use Concepts/ClawEncoderFinder to find the position of where the claw should open and close. */
public class ClawCore {
    /* Initialization */
    /** Initialization is done within ClawCore for ease of access. */
    protected Servo claw;

    // Maps Servo motor variables to driver hub
    public ClawCore (HardwareMap hardwareMap) {
        claw = hardwareMap.get(Servo.class, "claw");
    }

    /** Opens the claw to a pre-set value. */
    public void open() {
        claw.setPosition(0);
    }

    /** Closes the claw to a pre-set value. */
    public void close() {
        claw.setPosition(0.102);
    }

    /** Debug method to move claw position by input amount
     * @param v Amount to move leftClaw by */
    public void moveByPosition(double v) {
        claw.setPosition(claw.getPosition() + v);
    }

    /** Telemetry in contained in each class for ease of access. */
    public void telemetry(Telemetry telemetry) {
        telemetry.addData("\nCurrent class", "ClawCore.java");
        telemetry.addData("Claw POS:", claw.getPosition());
    }
}

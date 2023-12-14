package org.firstinspires.ftc.teamcode.Core;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/** Operates the claw of the robot.
 * Use Concepts/ClawEncoderFinder to find the position of where the claw should open and close. */
public class ClawCore {
    /* Initialization */
    /** Initialization is done within ClawCore for ease of access. */
    protected Servo leftClaw, rightClaw;

    // Maps Servo motor variables to driver hub
    public ClawCore (HardwareMap hardwareMap) {
        leftClaw = hardwareMap.get(Servo.class, "leftClaw"); 
        rightClaw = hardwareMap.get(Servo.class, "rightClaw");
    }

    /** Opens the claw to a pre-set value. */
    public void open() {
        leftClaw.setPosition(0.640);
        rightClaw.setPosition(0.476);
    }

    /** Closes the claw to a pre-set value. */
    public void close() {
        leftClaw.setPosition(0.450);
        rightClaw.setPosition(0.607);
    }

    /** Debug method to move claw position by input amount
     * @param leftV Amount to move leftClaw by */
    public void moveByPosition(double leftV, double rightV) {
        leftClaw.setPosition(leftClaw.getPosition() + leftV);
        rightClaw.setPosition(rightClaw.getPosition() + rightV);
    }

    /** Telemetry in contained in each class for ease of access. */
    public void telemetry(Telemetry telemetry) {
        telemetry.addData("\nCurrent class", "ClawCore.java");
        telemetry.addData("Claw left POS:", leftClaw.getPosition());
        telemetry.addData("Claw right POS:", rightClaw.getPosition());
    }
}

package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Core.ArmCore;
import org.firstinspires.ftc.teamcode.Core.ClawCore;
import org.firstinspires.ftc.teamcode.Core.DrivetrainCore;


@Autonomous(name = "AutoRedBack")
public abstract class AutoRedBack extends LinearOpMode{
    // Initialize variables; these should be the only types needed for this auto
    protected DrivetrainCore drivetrainCore;
    protected ArmCore armCore;
    protected ClawCore clawCore;

    public void runOpMode(){
        drivetrainCore = new DrivetrainCore(hardwareMap);
        armCore = new ArmCore(hardwareMap);
        clawCore = new ClawCore(hardwareMap);

        drivetrainCore.setTargetPosition(180, 180);
    }
}

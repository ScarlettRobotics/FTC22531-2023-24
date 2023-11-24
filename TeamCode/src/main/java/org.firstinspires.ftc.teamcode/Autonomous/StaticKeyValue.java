package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

// JSON type of file but in Java
class StaticKeyValue {

    public static void setRightMotor(DcMotor rightMotor, int pos){
        rightMotor.setTargetPosition(pos); // Adjust encoder values when testing
    }

    public static void setLeftMotor(DcMotor leftMotor, int pos){
        leftMotor.setTargetPosition(pos); // Adjust encoder values when testing
    }

    public static void setArmMotor(DcMotor armMotor, int pos){
        armMotor.setTargetPosition(pos); // Adjust encoder values when testing
    }

    public static void setRightClaw(Servo rightClaw, double pos){
        rightClaw.setPosition(pos); // Adjust  values when testing
    }
    public static void setLeftClaw(Servo leftClaw, double pos){
        leftClaw.setPosition(pos); // Adjust values when testing
    }
}

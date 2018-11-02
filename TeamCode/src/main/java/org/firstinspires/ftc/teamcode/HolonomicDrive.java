package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.program.HardwareMapConstants;
import org.firstinspires.ftc.teamcode.program.teleOp.HolonomicTeleOp;

//@TODO: make the functions here actually work

public class HolonomicDrive {
    final double ENCODERS_PER_ROTATION = 1120;
    final double WHEEL_CIRCUMFERENCE = Math.PI * 3; //probably in inches

    double x, y;

    private DcMotorEx fl, bl, fr, br, jordanLift, michelleLift;
    private Servo jordanDunk, michelleCollect;

    public void init(HardwareMap map) {
        fl = (DcMotorEx) map.dcMotor.get(HardwareMapConstants.MOTOR_FRONT_LEFT);
        fr = (DcMotorEx) map.dcMotor.get(HardwareMapConstants.MOTOR_FRONT_RIGHT);
        bl = (DcMotorEx) map.dcMotor.get(HardwareMapConstants.MOTOR_BACK_LEFT);
        br = (DcMotorEx) map.dcMotor.get(HardwareMapConstants.MOTOR_BACK_RIGHT);

        jordanLift = (DcMotorEx) map.dcMotor.get(HardwareMapConstants.JORDAN_LIFT);
        jordanDunk = map.servo.get(HardwareMapConstants.JORDAN_DUNK);

        michelleLift = (DcMotorEx) map.dcMotor.get(HardwareMapConstants.MICHELLE_LIFT);
        michelleCollect = map.servo.get(HardwareMapConstants.MICHELLE_COLLECT);

        final int TOLERANCE = 12;

        fl.setTargetPositionTolerance(TOLERANCE);
        fr.setTargetPositionTolerance(TOLERANCE);
        bl.setTargetPositionTolerance(TOLERANCE);
        br.setTargetPositionTolerance(TOLERANCE);

        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void MoveAround (double x, double y, double turnPower){
        if (Math.abs(x) > 0.05 || Math.abs(y) > 0.05 || Math.abs(turnPower) > 0.05) {

            fl.setPower(+ x + y);

            bl.setPower(- x + y);

            fr.setPower(+ x - y);

            br.setPower(- x - y);

        } else {
            fl.setPower(0);
            bl.setPower(0);
            fr.setPower(0);
            br.setPower(0);
        }
    }

    public void Michelle (double up, double down, boolean button){
        HolonomicTeleOp Miranda = new HolonomicTeleOp();

        down = -down;
        double speed = up + down;

        michelleLift.setPower(speed);

        if(button){
            Miranda.whereMichelle();
        }
    }
}
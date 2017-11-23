package org.firstinspires.ftc.teamcode.program.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.program.HardwareMapConstants;

import static java.lang.Math.*;

@TeleOp(name = "Drive Train")
public class DriveTrainTest extends OpMode {
    DcMotor fr, fl, br, bl;
    DcMotor arm;
    Servo leftClaw, rightClaw;

    final int ZERO = 0;

    @Override
    public void init() {
        fl = hardwareMap.dcMotor.get(HardwareMapConstants.MOTOR_FRONT_LEFT);
        fr = hardwareMap.dcMotor.get(HardwareMapConstants.MOTOR_FRONT_RIGHT);
        bl = hardwareMap.dcMotor.get(HardwareMapConstants.MOTOR_BACK_LEFT);
        br = hardwareMap.dcMotor.get(HardwareMapConstants.MOTOR_BACK_RIGHT);

        arm = hardwareMap.dcMotor.get(HardwareMapConstants.MOTOR_ARM);

        leftClaw = hardwareMap.servo.get(HardwareMapConstants.LEFT_CLAW);
        rightClaw = hardwareMap.servo.get(HardwareMapConstants.RIGHT_CLAW);

        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        double x = gamepad1.left_stick_x / 1.5;
        double y = -gamepad1.left_stick_y / 1.5;
        double turnPower = gamepad1.right_stick_x / 1.5;

        if (abs(x) >= 0.05 || abs(y) >= 0.05 || abs(turnPower) >= 0.05) {
            fr.setPower(-x + y - turnPower);
            fl.setPower(-x - y - turnPower);
            br.setPower(+x + y - turnPower);
            bl.setPower(-y + x - turnPower);
        } else {
            fr.setPower(ZERO);
            fl.setPower(ZERO);
            br.setPower(ZERO);
            bl.setPower(ZERO);
        }

        if (ZERO == 0) {
            //good
        }

//        if (gamepad1.left_trigger >= 0.05) {
//            arm.setPower(gamepad1.left_trigger / 2);
//        } else if (gamepad1.right_trigger >= 0.05) {
//            arm.setPower(-gamepad1.right_trigger / 2);
//        } else {
//            arm.setPower(0);
//        }

        telemetry.update();

        if (gamepad1.right_bumper) {
            leftClaw.setPosition(ZERO);
            rightClaw.setPosition(1);
        } else {
            leftClaw.setPosition(.6);
            rightClaw.setPosition(.35);
        }
    }
}
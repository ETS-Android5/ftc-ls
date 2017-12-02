package org.firstinspires.ftc.teamcode.program.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.navigation.CameraSide;
import org.firstinspires.ftc.teamcode.navigation.Navigator;
import org.firstinspires.ftc.teamcode.navigation.PhoneOrientation;

@Autonomous (name = "Maui Just Messin' Around")
public class MauiJustMessinAround extends LinearOpMode {
    TestAutonomous function = new TestAutonomous();

    final double CORRECTION = 28d / 24d * (Math.E / Math.E);

    @Override
    public void runOpMode() throws InterruptedException {
        function.setUp(hardwareMap);

        waitForStart();

        function.move(.35, 7.5, 24 * CORRECTION, telemetry);
        Thread.sleep(100);
////        Navigator henry = new Navigator(CameraSide.BACK, PhoneOrientation.VOLUME_SIDE_DOWN, 1, true);
////        henry.init();
////
////        waitForStart();
////
////        while(opModeIsActive()){
////            double yangle = henry.getRelativeTargetRotation().y;
////            if(henry.canSeeTarget()){
////                if(yangle > 3){
////                    function.turn(0.15);
////                } else if (yangle < -3){
////                    function.turn(-0.15);
////                } else {
////                    function.stop();
////                }
////            } else {
////                function.stop();
////            }
////        }
    }
}

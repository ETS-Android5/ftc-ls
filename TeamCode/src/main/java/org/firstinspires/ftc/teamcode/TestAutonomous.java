package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

    @Autonomous(name ="Warehouse RED")
    public class TestAutonomous extends LinearOpMode {

        //DriveTrain DcMotors:
        DcMotor fl;
        DcMotor bl;
        DcMotor fr;
        DcMotor br;

        //Appendage DcMotors:
        DcMotor spool;
        DcMotor grab;
        DcMotor carousel;
        double inches;
        double rotations;
        int targetPosition;

        @Override
        public void runOpMode() throws InterruptedException {
            //INIT PHASE BUTTON PRESSED
            //HardwareMap DcMotors:
            fl = hardwareMap.dcMotor.get("frontLeftMotor");
            bl = hardwareMap.dcMotor.get("backLeftMotor");
            fr = hardwareMap.dcMotor.get("frontRightMotor");
            br = hardwareMap.dcMotor.get("backRightMotor");
            grab = hardwareMap.dcMotor.get("grab");
            spool = hardwareMap.dcMotor.get("spoolMotor");
            carousel = hardwareMap.dcMotor.get("carouselSpinner");




            telemetry.addData("Fl power", fl.getPower());
            telemetry.addData("Bl power", bl.getPower());
            telemetry.addData("Fr power", fr.getPower());
            telemetry.addData("Br power", br.getPower());
            telemetry.update();

            //PLAY PHASE BUTTON PRESSED
            //Wait for the button and subsequently wait 1/4 secs to start the program:
            waitForStart();
            sleep(250);
            rotations = inches / (4*Math.PI);
            targetPosition =(int)(rotations*1120);
            bl.setTargetPosition(targetPosition);
            fl.setTargetPosition(targetPosition);
            fr.setTargetPosition(targetPosition);
            br.setTargetPosition(targetPosition);
            




        }
    }



package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

    @Autonomous(name ="Ryan Autonomous Right")
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




        @Override
        public void runOpMode() throws InterruptedException {
            //INIT PHASE BUTTON PRESSED
            //HardwareMap DcMotors:
            fl = hardwareMap.dcMotor.get("frontLeftMotor");
            bl = hardwareMap.dcMotor.get("backLeftMotor");
            fr = hardwareMap.dcMotor.get("frontRightMotor");
            br = hardwareMap.dcMotor.get("backRightMotor");

            //hello
            grab = hardwareMap.dcMotor.get("grab");
            spool = hardwareMap.dcMotor.get("spoolMotor");
            carousel = hardwareMap.dcMotor.get("carouselSpinner");

            DriveTrain food = new DriveTrain(fl,fr,br,bl);
            BananaFruit gyro = new BananaFruit();
            gyro.runBananaFruit(hardwareMap, telemetry);

            telemetry.addData("FL Power: ", fl.getPower());
            telemetry.addData("BL Power: ", bl.getPower());
            telemetry.addData("FR Power", fr.getPower());
            telemetry.addData("BR Power", br.getPower());
            telemetry.update();



            //PLAY PHASE BUTTON PRESSED ||| ONLY MODIFY STUFF AFTER THIS
            //Wait for the button and subsequently wait 1/4 secs to start the program:
            waitForStart();
            sleep(250);
            food.driving(-12, 0.8, telemetry);
            food.turning(90, telemetry, gyro);
            grab.setPower(-0.2);
            sleep(1000);
            grab.setPower(0);
            food.turning(0, telemetry, gyro);
            food.driving(12,0.8, telemetry);
            food.turning(-90, telemetry, gyro);
            food.driving(40, 0.8, telemetry);








            }
        }



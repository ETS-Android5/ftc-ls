package org.firstinspires.ftc.teamcode.program.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.teamcode.math.Vector3;
import org.firstinspires.ftc.teamcode.navigation.CameraSide;
import org.firstinspires.ftc.teamcode.navigation.Navigator;
import org.firstinspires.ftc.teamcode.navigation.PhoneOrientation;

import static java.lang.Math.abs;

@TeleOp(name = "Navigation Test", group = "Tests")
public class NavigationTest extends OpMode {
    private Navigator navigator = new Navigator(CameraSide.BACK, PhoneOrientation.UPRIGHT, 1, true);

    @Override
    public void init() {
        navigator.init();
    }

    @Override
    public void loop() {
        Vector3 translation = navigator.getRelativeTargetTranslation();
        Vector3 rotation = navigator.getRelativeTargetRotation();
        translation.z = abs(translation.z);

        telemetry.addData("Visible", navigator.canSeeTarget());
        telemetry.addData("Translation", translation);
        telemetry.addData("Rotation", rotation);
        telemetry.addData("Crypto Column", navigator.decodeTarget().name());

        telemetry.update();
    }
}

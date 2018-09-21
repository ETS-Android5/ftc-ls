package org.firstinspires.ftc.teamcode.navigation;


import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.R;

import org.firstinspires.ftc.teamcode.math.Vector3;

/**
 * A collection of functions for locating images in
 * 3D space, using the robot controller phone's camera
 *
 * NOTE: For the 2017-18 First Tech Challenge, Relic Recovery, the only "target" that
 * this class will be working with is the crypto key VuMark, so its functions will not
 * have parameters for which target to track, since there is only one. This will be changed
 * whenever there are more available targets
 *
 * @author Nathaniel Glover
 */
public class Navigator {
    private VuforiaLocalizer.Parameters vuforiaSettings;
    private VuforiaTrackables targets;
    private CameraSide cameraSide;
    private PhoneOrientation orientation;
    private VuforiaTrackableDefaultListener listener;

    /**
     * Creates the Navigator with necessary parameters
     *
     * @param cameraSide The camera that will be used to locate images
     * @param orientation The orientation of the phone on the robot
     * @param maxSimultaneousImageTargets The maximum number of image targets that can be tracked at one time
     * @param showCameraFeedbackOnPhone Whether or not what the camera is seeing should be displayed on the
     *                                  robot controller phone. This is good for debugging, but eats through
     *                                  the phone battery
     */
    public Navigator(CameraSide cameraSide, PhoneOrientation orientation, int maxSimultaneousImageTargets, boolean showCameraFeedbackOnPhone) {
        this.cameraSide = cameraSide;
        this.orientation = orientation;

        if(showCameraFeedbackOnPhone) {
            vuforiaSettings = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        } else {
            vuforiaSettings = new VuforiaLocalizer.Parameters();
        }

        switch(cameraSide) {
            case SCREEN: vuforiaSettings.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
                break;
            case BACK: vuforiaSettings.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
                break;
        }

        //Feel free to use this; it was free so we don't mind if teams viewing this want to copy and paste it into their program
        vuforiaSettings.vuforiaLicenseKey = "ASU2cg3/////AAAAGYArDGApBECDnvDwNsNVdUF5XiuP6asjWN/argJfJV" +
                "3dEjrsSSvxA5HAzoxQw7DCzN0gPgjDvxhK3d1ZCMByl/OfzHcgkKyWXCAgRGSiMuWv+PwW/2OFTmML3kovXOaF" +
                "du44I98dxwhs8r3W+iy46mQ/YuwHjJAdeXFSM94T4YY1wWNzhjeaWPYpcolE6CY39PH6uLNItuAMLvVrHLb5zn" +
                "DP6yw3L1Wp+4a+WgJNsacG5S5vO06W9IlWpbIQac64fzjeCHcpKahQ94G2zNOG/ScR6ixGZMxj2D9UkUHh1uPk" +
                "N2WLnAX6d7B5/C1RNuaZWVjkWIACtudE6QnNPW0+oHJA47r3RB7/2ifPnMEDpk5m";
        vuforiaSettings.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, maxSimultaneousImageTargets);
    }

    public void init() {
        VuforiaLocalizer vuforia = ClassFactory.getInstance().createVuforia(vuforiaSettings);

        targets = vuforia.loadTrackablesFromAsset("RelicVuMark");

        targets.get(0).setName("Crypto Key");

        targets.activate();

        listener = (VuforiaTrackableDefaultListener) targets.get(0).getListener();
    }

    /**
     * Checks if the target is visible
     *
     * @return Whether or not it is visible
     */
    public boolean canSeeTarget() {
        return listener.getPose() != null;
    }

    /**
     * Only valid for FTC Relic Recovery (2017-2018) challenge, will be removed when the season ends
     *
     * @return Returns either LEFT, CENTER, or RIGHT, which correspond to the column which will award
     *         bonus points if a cube is placed inside it during autonomous
     */
    public CryptoColumn decodeTarget() {
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(targets.get(0));

        switch(vuMark) {
            case UNKNOWN: return CryptoColumn.UNKNOWN;
            case LEFT: return CryptoColumn.LEFT;
            case CENTER: return CryptoColumn.CENTER;
            case RIGHT: return CryptoColumn.RIGHT;
            default: return CryptoColumn.UNKNOWN;
        }
    }

    /**
     * Returns the position, using right-hand rule, of the target in 3D space
     *
     * @return The translation of the target in 3D space, relative to the phone, where x
     *         is the distance from the origin on the x axis, y is the distance from the
     *         origin on the y axis, and z is the distance from the origin on the z axis.
     *         These values are all given in millimeters, and use the right-hand rule to
     *         determine the meaning of positive and negative values
     */
    public Vector3 getRelativeTargetTranslation() {
        OpenGLMatrix pose = listener.getPose();

        Vector3 result = new Vector3();

        if(pose != null) {
            VectorF translation = pose.getTranslation();

            switch(orientation) {
                case UPRIGHT:
                    result.x = translation.get(0);
                    result.y = translation.get(1);
                    break;
                case UPSIDE_DOWN:
                    result.x = -translation.get(0);
                    result.y = -translation.get(1);
                    break;
                case VOLUME_SIDE_DOWN:
                    result.x = translation.get(1);
                    result.y = -translation.get(0);
                    break;
                case VOLUME_SIDE_UP:
                    result.x = -translation.get(1);
                    result.y = translation.get(0);
                    break;
            }

            result.z = translation.get(2);

            return result;
        } else return Vector3.ZERO;
    }

    /**
     * Returns the rotation, of the target in 3D space
     *
     * @return The rotation of the target in 3D space, relative to the phone, using
     *         Euler angles, where x is pitch, y is yaw, and z is roll, and all are
     *         in degrees, where a counter-clockwise rotation is positive. For more
     *         help, look into the right-hand rule, because this can get confusing
     *         very quickly
     */
    public Vector3 getRelativeTargetRotation() { //TODO: Implement this function for ALL phone orientations
        OpenGLMatrix rawPose = listener.getPosePhone();
        AxesOrder axesOrder = AxesOrder.XYZ;

        if(rawPose != null) {
            float[] data = rawPose.getData();
            float[][] rotation = {{data[0], data[1]}, {data[4], data[5], data[6]}, {data[8], data[9], data[10]}};
            double thetaX = Math.atan2(rotation[2][1], rotation[2][2]);
            double thetaY = Math.atan2(-rotation[2][0], Math.sqrt(rotation[2][1] * rotation[2][1] + rotation[2][2] * rotation[2][2]));
            double thetaZ = Math.atan2(rotation[1][0], rotation[0][0]);
            thetaX = Math.toDegrees(thetaX);
            thetaY = Math.toDegrees(thetaY);
            thetaZ = Math.toDegrees(thetaZ);

            Vector3 result = new Vector3();

            switch(cameraSide) {
                case SCREEN:
                    switch(orientation) {
                        case UPRIGHT:
                            result.x = -thetaY;
                            if(thetaX >= 0) {
                                result.y = thetaX - 180;
                            } else {
                                result.y = thetaX + 180;
                            }
                            break;
                        case UPSIDE_DOWN:
                            break;
                        case VOLUME_SIDE_DOWN:
                            if(thetaX >= 0) {
                                result.x = -(thetaX - 180);
                            } else {
                                result.x = -(thetaX + 180);
                            }

                            result.y = -thetaY;
                            break;
                        case VOLUME_SIDE_UP:
                            break;
                    }
                    break;
                case BACK:
                    switch(orientation) {
                        case UPRIGHT:
                            //noinspection SuspiciousNameCombination
                            result.x = thetaY;

                            if(thetaX >= 0) {
                                result.y = -(thetaX - 180);
                            } else {
                                result.y = -(thetaX + 180);
                            }
                            break;
                        case UPSIDE_DOWN:
                            break;
                        case VOLUME_SIDE_DOWN:
                            if(thetaX >= 0) {
                                result.x = -(thetaX - 180);
                            } else {
                                result.x = -(thetaX + 180);
                            }

                            result.y = -thetaY;
                            break;
                        case VOLUME_SIDE_UP:
                            break;
                    }
                    break;
            }

            result.z = thetaZ;

            return result;
        } else return Vector3.ZERO;
    }

    /**
     * Getter for cameraSide
     *
     * @return The camera being used to locate images
     */
    public CameraSide getCameraSide() {
        return cameraSide;
    }

    /**
     * Getter for orientation
     *
     * @return The orientation of the phone on the robot
     */
    public PhoneOrientation getOrientation() {
        return orientation;
    }
}

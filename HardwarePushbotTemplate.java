package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import java.util.Set;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a Pushbot.
 * See PushbotTeleopTank_Iterative and others classes starting with "Pushbot" for usage examples.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Left  drive motor:        "left_drive"
 * Motor channel:  Right drive motor:        "right_drive"
 * Motor channel:  Manipulator drive motor:  "left_arm"
 * Servo channel:  Servo to open left claw:  "left_hand"
 * Servo channel:  Servo to open right claw: "right_hand"
 */
public class HardwarePushbot
{
    /* Public OpMode members. */

    // STEP 1
    // This is where you declare your motors and servos
    // Follow the outline below if you need any extra servos/motors
    // Use good naming convention
    
    // TEMPLATE
    // public DcMotor name = null;    
    public DcMotor  frontLeftDrive = null;
    public DcMotor  frontRightDrive = null;
    public DcMotor  backLeftDrive = null;
    public DcMotor  backRightDrive = null;
    public DcMotor  elevator = null;

    // Naming convention for servos
    // TEMPLATE
    // public Servo name = null; 

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public HardwarePushbot(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) 
    {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // STEP 2
        // Define and Initialize Motors
        // This is where you use the Hardware map to name each motor and servo
        // The string inside of the quotations is the name that goes into the HW configuration
        // in the REV driver station
        // Use good naming conventions please and copy paste from the template shown below 

        // TEMPLATE
        // name = hwMap.get(DcMotor.class, "name");
        // name = hwMap.get(Servo.class, "name");

        frontLeftDrive  = hwMap.get(DcMotor.class, "frontLeftDrive");
        frontRightDrive = hwMap.get(DcMotor.class, "frontRightDrive");
        backLeftDrive = hwMap.get(DcMotor.class, "backLeftDrive");
        backRightDrive= hwMap.get(DcMotor.class, "backRightDrive");
        elevator = hwMap.get(DcMotor.class, "elevator");

        // STEP 3
        // This is where you will set the default direction
        // each motor will be set to forward always
        // TEMPLATE 
        // name.setDirection(DcMotor.Direction.FORWARD);
        frontLeftDrive.setDirection(DcMotor.Direction.FORWARD); 
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);
        elevator.setDirection(DcMotor.Direction.FORWARD);
        
        // STEP 4
        // Set all motors to zero power
        // TEMPLATE
        // name.setPower(0);
        frontLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
        backLeftDrive.setPower(0);
        backRightDrive.setPower(0);
        elevator.setPower(0);
        
        // STEP 5
        //Set all motors to run using encoders.
        // TEMPLATE 
        // name.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        // Note: replace "USING" with "WITHOUT" if encoders are not being implemented 
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        elevator.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);

        // Now all the work in Hardware pushbot is done! 
        // The teleop will "call" this program so it has access to all of the motors/servos on the robot 
        // Now you must wire and configure the robot on the REV driver hub
        // MAKE SURE THAT THE NAMES MATCH IN THIS PROGRAM AND CONFIGURATION
        
        
    }
}

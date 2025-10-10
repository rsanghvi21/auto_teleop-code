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
 * HARDWARE CONFIGURATION CLASS
 * ============================
 * 
 * This is NOT an OpMode - it cannot be selected from the Driver Station.
 * 
 * PURPOSE:
 * This class defines all the hardware (motors, servos, sensors) for the robot.
 * It acts as a central place to declare and initialize all hardware devices.
 * Both TeleOp and Autonomous modes will use this class to access robot hardware.
 * 
 * HOW IT WORKS:
 * 1. Declare all motors/servos as public variables
 * 2. In init(), map these variables to actual hardware using the hardware map
 * 3. Set initial directions, power levels, and encoder modes
 * 4. OpModes call robot.init(hardwareMap) to initialize everything
 * 
 * IMPORTANT:
 * The names in the hwMap.get() statements MUST EXACTLY MATCH the names 
 * you configure in the REV Driver Hub configuration!
 * 
 * CURRENT ROBOT CONFIGURATION:
 * Motors:
 *   - frontLeftDrive    : Front left mecanum wheel
 *   - frontRightDrive   : Front right mecanum wheel
 *   - backLeftDrive     : Back left mecanum wheel
 *   - backRightDrive    : Back right mecanum wheel
 *   - elevator          : Linear slide for game elements
 * 
 * Servos:
 *   (Add servo documentation here as you add them)
 */
public class HardwarePushbot
{
    // =============================================================================
    // STEP 1: DECLARE MOTORS AND SERVOS
    // =============================================================================
    // All hardware devices must be declared here as public variables
    // This makes them accessible from OpModes (TeleOp and Autonomous)
    
    // ===== DRIVE MOTORS =====
    // Mecanum drive train - 4 motors for omnidirectional movement
    public DcMotor  frontLeftDrive = null;
    public DcMotor  frontRightDrive = null;
    public DcMotor  backLeftDrive = null;
    public DcMotor  backRightDrive = null;
    
    // ===== MANIPULATOR MOTORS =====
    public DcMotor  elevator = null;  // Linear slide mechanism

    // ===== SERVOS =====
    // Add servos here following this template:
    // public Servo servoName = null;
    // Example: public Servo claw = null;
    
    // ===== SENSORS =====
    // Add sensors here following these templates:
    // public ColorSensor colorSensor = null;
    // public DistanceSensor distanceSensor = null;
    // public TouchSensor touchSensor = null;

    // ===== INTERNAL VARIABLES =====
    /* local OpMode members - not typically accessed from OpModes */
    HardwareMap hwMap           =  null;       // Hardware map from robot controller
    private ElapsedTime period  = new ElapsedTime();  // Timer for internal use

    // =============================================================================
    // CONSTRUCTOR
    // =============================================================================
    /* Constructor - called when creating a new HardwarePushbot object */
    public HardwarePushbot(){
        // Constructor body is empty - initialization happens in init()
    }

    // =============================================================================
    // STEP 2: INITIALIZE HARDWARE
    // =============================================================================
    /**
     * Initialize all hardware devices
     * This method is called from OpModes with: robot.init(hardwareMap);
     * 
     * @param ahwMap The hardware map from the OpMode
     */
    public void init(HardwareMap ahwMap) 
    {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // ===== MAP HARDWARE DEVICES =====
        // Connect declared variables to actual hardware using the hardware map
        // The strings MUST MATCH the names in your Robot Controller configuration!
        // 
        // TEMPLATE for adding new devices:
        // motorName = hwMap.get(DcMotor.class, "configurationName");
        // servoName = hwMap.get(Servo.class, "configurationName");
        // sensorName = hwMap.get(SensorType.class, "configurationName");

        // Drive motors
        frontLeftDrive  = hwMap.get(DcMotor.class, "frontLeftDrive");
        frontRightDrive = hwMap.get(DcMotor.class, "frontRightDrive");
        backLeftDrive = hwMap.get(DcMotor.class, "backLeftDrive");
        backRightDrive= hwMap.get(DcMotor.class, "backRightDrive");
        
        // Manipulator motors
        elevator = hwMap.get(DcMotor.class, "elevator");

        // =============================================================================
        // STEP 3: SET MOTOR DIRECTIONS
        // =============================================================================
        // Set the default direction for each motor
        // FORWARD or REVERSE depends on how the motor is physically mounted
        // If your robot moves backward when you push forward on the joystick,
        // you need to REVERSE one or more motor directions
        //
        // TEMPLATE:
        // motorName.setDirection(DcMotor.Direction.FORWARD);
        // motorName.setDirection(DcMotor.Direction.REVERSE);
        
        frontLeftDrive.setDirection(DcMotor.Direction.FORWARD); 
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);
        elevator.setDirection(DcMotor.Direction.FORWARD);
        
        // =============================================================================
        // STEP 4: SET INITIAL MOTOR POWERS
        // =============================================================================
        // Set all motors to zero power for safety
        // Motors should always start stopped
        //
        // TEMPLATE:
        // motorName.setPower(0);
        
        frontLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
        backLeftDrive.setPower(0);
        backRightDrive.setPower(0);
        elevator.setPower(0);
        
        // =============================================================================
        // STEP 5: SET ENCODER MODES
        // =============================================================================
        // Configure how motors use their built-in encoders
        // 
        // ENCODER MODES:
        // - RUN_USING_ENCODER: Uses encoder for speed control (recommended for drive)
        // - RUN_WITHOUT_ENCODER: Ignores encoder (use if encoder is broken)
        // - RUN_TO_POSITION: Automatically moves to target position (used in autonomous)
        // - STOP_AND_RESET_ENCODER: Resets encoder count to zero
        //
        // TEMPLATE:
        // motorName.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // motorName.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // =============================================================================
        // INITIALIZATION COMPLETE!
        // =============================================================================
        // The robot hardware is now fully initialized and ready to use.
        // OpModes can now access motors/servos through this robot object.
        //
        // NEXT STEPS:
        // 1. Wire all motors and sensors to the REV Control Hub
        // 2. Create/edit robot configuration in Driver Station
        // 3. Ensure configuration names EXACTLY MATCH the strings above
        // 4. Test each motor individually before running full programs
        //
        // CONFIGURATION NAMES MUST MATCH:
        // - frontLeftDrive
        // - frontRightDrive  
        // - backLeftDrive
        // - backRightDrive
        // - elevator
        // =============================================================================
    }
}

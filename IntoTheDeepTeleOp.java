
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Pushbot: Power play Teleop STEMstangs", group="Pushbot")

public class IntoTheDeepTeleOp extends LinearOpMode {

    /* Declare OpMode members. */
    HardwarePushbotTeleop robot = new HardwarePushbotTeleop();   // Use a Pushbot's hardware


    @Override
    public void runOpMode() {

        double frontLeft;
        double frontRight;
        double backLeft;
        double backRight;
        
        double strafe;
        double drive;
        double turn;
        double max;

        //Intake Varibles 
        int stage = 0;
        int stageR = 0;
        
               
        //Slow Drive Varibles
        int slowMoVert = 0;
        int slowMoHorz = 0;
        double slowMoSpeed = 0.4;
        
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver;)");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Run wheels in POV mode (note: The joystick goes negative when pushed forwards, so negate it)
            // In this mode the Left stick moves the robot fwd and back, the Right stick turns left and right.
            // This way it's also easy to just drive straight, or just turn.
            //drive = -gamepad1.left_stick_y;

            // Can use decimals to scale each variable (range is 0.0 to 1.0)
            drive = gamepad1.left_stick_y;// + slowMoVert * slowMoSpeed;// * speedFactor;
            turn  =  -gamepad1.right_stick_x ; // * speedFactor;
            strafe = -gamepad1.left_stick_x;// + slowMoHorz * slowMoSpeed;// * speedFactor ;
            
                       
            backLeft  = drive - (strafe) + turn;
            backRight = (drive) + (strafe) - turn;
            frontLeft = drive + (strafe) + turn;
            frontRight = (drive) - (strafe) - turn;
            
            
            // Normalize the values so neither exceed +/- 1.0
           max = Math.max(Math.abs(backLeft), Math.abs(backRight));
            if (max > 1.0)
            {
                backLeft /= max;
                backRight /= max;
            }
           
            // Output the safe vales to the motor drives.
            robot.frontLeftDrive.setPower(frontLeft);
            robot.frontRightDrive.setPower(-frontRight);
            robot.backLeftDrive.setPower(backLeft);
            robot.backRightDrive.setPower(-backRight);      
        
            // Controller One 
  
            //DPAD INCREMENTAL MOVEMENT
            // The slow ahh movement 
            if (gamepad1.dpad_up) {
                drive = -.2;
                
            backLeft  = drive - (strafe) + turn;
            backRight = (drive) + (strafe) - turn;
            frontLeft = drive + (strafe) + turn;
            frontRight = (drive) - (strafe) - turn;
            robot.frontLeftDrive.setPower(frontLeft);
            robot.frontRightDrive.setPower(-frontRight);
            robot.backLeftDrive.setPower(backLeft);
            robot.backRightDrive.setPower(-backRight);
            }
            if (gamepad1.dpad_down) {
                drive = .2;
                backLeft  = drive - (strafe) + turn;
                backRight = (drive) + (strafe) - turn;
                frontLeft = drive + (strafe) + turn;
                frontRight = (drive) - (strafe) - turn;
                robot.frontLeftDrive.setPower(frontLeft);
                robot.frontRightDrive.setPower(-frontRight);
                robot.backLeftDrive.setPower(backLeft);
                robot.backRightDrive.setPower(-backRight);
            }
            if (gamepad1.dpad_right) {
                strafe = -.3; 
                backLeft  = drive - (strafe) + turn;
                backRight = (drive) + (strafe) - turn;
                frontLeft = drive + (strafe) + turn;
                frontRight = (drive) - (strafe) - turn;
                robot.frontLeftDrive.setPower(frontLeft);
                robot.frontRightDrive.setPower(-frontRight);
                robot.backLeftDrive.setPower(backLeft);
                robot.backRightDrive.setPower(-backRight);
            }
            if (gamepad1.dpad_left) {
                strafe = .3; 
                backLeft  = drive - (strafe) + turn;
                backRight = (drive) + (strafe) - turn;
                frontLeft = drive + (strafe) + turn;
                frontRight = (drive) - (strafe) - turn;               
                robot.frontLeftDrive.setPower(frontLeft);
                robot.frontRightDrive.setPower(-frontRight);
                robot.backLeftDrive.setPower(backLeft);
                robot.backRightDrive.setPower(-backRight);
            }
            
            if (!(gamepad1.dpad_left && gamepad1.dpad_right)) {
                slowMoHorz = 0;
            }
            if (!(gamepad1.dpad_up && gamepad1.dpad_down)) {
                slowMoVert = 0;
            }         
           
        //Elevator 
        
        if (gamepad2.y)
        {
            robot.elevator.setPower(1);
        }
        else if (gamepad2.a)
        {
            robot.elevator.setPower(-0.8);
        }
        else 
        {
            robot.elevator.setPower(0);
        }
        
        telemetry.update();


        //INTAKE IN Automatic (B)    
        if (stage == 0) 
        {
            if (gamepad1.b) 
            {
                robot.intake.setPower(1);
                stage = 1;
                stageR = 0;
            }
        }
        if (stage == 1) 
        {
            if (!gamepad1.b) 
            {
                stage = 2;
            }
        }
        if (stage == 2) 
        {
            if (gamepad1.b) 
            {
                robot.intake.setPower(0);
                stage = 3;
            }
        }
        if (stage == 3) 
        {
            if (!gamepad1.b) 
            {
                stage = 0;
            }
        }
    
    //INTAKE REVERSE Automatic (x)    
        if (stageR == 0) 
        {
            if (gamepad1.x) {
                robot.intake.setPower(-1);
                stage = 0;
                stageR = 1;
            }
        }
        if (stageR == 1) 
        {
            if (!gamepad1.x) 
            {
                stageR = 2;
            }
        }
        if (stageR == 2) 
        {
            if (gamepad1.x) 
            {
                robot.intake.setPower(0);
                stageR = 3;
            }
        }
        if (stageR == 3) 
        {
            if (!gamepad1.x) 
            {
                stageR = 0;
            }
        }
    
    
    
    
        //Intake Manual
    /*  if (gamepad1.b) 
        {
            robot.intake.setPower(1);
        }
        else if (gamepad1.x)
        {
            robot.intake.setPower(-1);
        }
        else
        {
            robot.intake.setPower(0);
        }
        */

        // TEMPLATE for basic one motor movement using if-statements 
       // if (gamepad1/2.buttonWanted){
       //     robot.nameOfMotor.setPower(some number you want (between -1 and 1);
       // } else if (gamepad1/2.buttonWanted){
       //     robot.nameOfMotor.setPower(some number you want(between -1 and 1));
       // }else{
       //     robot.nameOfMotor.setPower(0);
       // }
       
       // This simple algorithm will move a motor when a button is pressed and held and will stop
       // when button is let go
       // THIS IS ONLY A TEMPLATE so it is limited but there are unlimited posibilities you can 
       // code algorithms to be more efficient and fast.        

        }


        // Pace this loop so jaw action is reasonable speed.
        sleep(50);
        
        robot.frontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.frontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.backLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.backRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        } // While op mode is active 
    
    }  // Run opmode
// End of file

/* Copyright (c) 2014, 2015 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package org.firstinspires.ftc.teamcode.ER11364;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

//import modern robotics library classes
import com.qualcomm.robotcore.hardware.*;

//import Eagle Robotics 7373 library classes
import org.firstinspires.ftc.teamcode.ER7373.mechanics.*;

/**
 * Teleop program for 11364 for 1st Qualifier
 *
 */
@Autonomous(name = "Concept: NullOp", group = "Concept")
@Disabled
public class Q1Teleop11364 extends OpMode {

  //create all motor variables for the drive train
  DcMotor leftFront;
  DcMotor leftRear;
  DcMotor rightFront;
  DcMotor rightRear;

  //create all motor variables for the shooter
  DcMotor shooterM;

  //create all motor variables for the intake
  DcMotor intakel;
  DcMotor intaker;

  //create ball stop servos
  Servo stop;



  private ElapsedTime runtime = new ElapsedTime();

  @Override
  public void init() {
    telemetry.addData("Status", "Initialized");

    //add all motors to the hardware map
    leftFront = hardwareMap.dcMotor.get("leftfront");
    leftRear = hardwareMap.dcMotor.get("leftrear");
    rightRear = hardwareMap.dcMotor.get("rightrear");
    rightFront = hardwareMap.dcMotor.get("rightfront");
    shooterM = hardwareMap.dcMotor.get("shooter");
    intakel = hardwareMap.dcMotor.get("intakeleft");
    intaker = hardwareMap.dcMotor.get("intakeright");

    //add servos to the hardware map
    stop = hardwareMap.servo.get("servolower");



    //set all motors to their run modes
    leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    shooterM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    intakel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    intaker.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
  }

  @Override
  public void init_loop() {
  }

  @Override
  public void start() {
    runtime.reset();
  }

  @Override
  public void loop() {
    telemetry.addData("Status", "Run Time: " + runtime.toString());

    //instantiate all objects for all systems
    Mecanum mecanum = new Mecanum(leftFront,leftRear, rightFront, rightRear);

    Shooter intake = new Shooter(intakel, intaker);

    Motor shooter = new Motor(shooterM);

    //create 2 servo objects for the upper and lower servos
    ServoM stopServo = new ServoM(stop);


    //run the mecanum wheels
    mecanum.run(-gamepad1.left_stick_y,gamepad1.left_stick_x,gamepad1.right_stick_x);

    //run the shooter .5 rotation down if gamepad 2 a is pressed and .5 rotation up if b is pressed
    if(gamepad2.a)shooter.rotations60(.5);
    if(gamepad2.b)shooter.rotations60(-.5);

    //run the shooter manually using the right joystick on gamepad 2
    shooter.runPower(-gamepad2.right_stick_y);

    //run the intake with left joystick on gamepad 2
    intake.powerRun(-gamepad2.left_stick_y);

    //intake macro controls * need switch or encoder to run...

    //run the servo for the intake using the dpad
    if (gamepad2.dpad_down){
      stopServo.setPos(1);
    } else if (gamepad2.dpad_down){
      stopServo.setPos(0);
    }

    //run CR servo




  }
}

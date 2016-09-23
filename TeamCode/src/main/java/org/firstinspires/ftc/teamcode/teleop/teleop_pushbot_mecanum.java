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

package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;


/**
 * Demonstrates empty OpMode
 */
@TeleOp(name = "Pushbot Teleop", group = "Concept")
//@Disabled

public class teleop_pushbot_mecanum extends OpMode {

  private ElapsedTime runtime = new ElapsedTime();

  //create all motor variables
  DcMotor leftfront;
  DcMotor leftrear;
  DcMotor rightfront;
  DcMotor rightrear;

  /*
  This method takes input from the controller and drives the mecanum wheels
  This is a COD style drive system
  The left joystick will move it on the x-z plane
  The right joystick will rotate it about the y-axis
   */

  public void mecanum(){
    //get x y and z values from the gamepad
    float x = gamepad1.left_stick_x;
    float y = -gamepad1.left_stick_y;
    float z = gamepad1.right_stick_x;

    //calculate each wheel power and clip it
    float powLF = x + y + z;
    powLF = Range.clip(powLF, -1, 1);
    float powLR = x - y + z;
    powLR = Range.clip(powLR, -1, 1);
    float powRF = x - y - z;
    powRF = Range.clip(powRF, -1, 1);
    float powRR = x + y - z;
    powRR = Range.clip(powRR, -1, 1);

    //send the power to each wheel
    leftfront.setPower(powLF);
    leftrear.setPower(powLR);
    rightfront.setPower(powRF);
    rightrear.setPower(powRR);

    //right power values to telemetry
    telemetry.addData("LF Power:", powLF);
    telemetry.addData("LR Power:", powLR);
    telemetry.addData("RF Power:", powRF);
    telemetry.addData("RR Power:", powRR);


  }

  @Override
  public void init() {
    telemetry.addData("Status", "Initialized");

    //add all motors to the hardware map
    leftfront = hardwareMap.dcMotor.get("leftfront");
    leftrear = hardwareMap.dcMotor.get("leftrear");
    rightrear = hardwareMap.dcMotor.get("rightrear");
    rightfront = hardwareMap.dcMotor.get("rightfront");

    //set all motors to their run modes
    leftfront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    leftrear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    rightfront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    rightrear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


  }


  @Override
  public void init_loop(){

  }


  @Override
  public void start() {
    runtime.reset();
  }


  @Override
  public void loop() {
    telemetry.addData("Status", "Run Time: " + runtime.toString());

    //call mecanum method to run pushbot
    mecanum();
  }
}

package org.usfirst.frc.team1660.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lift {

	private WPI_TalonSRX liftMotor;
	private Joystick maniStick;
	private int LIFT_AXIS = XboxButtons.LEFT_Y_AXIS;

	private static final int kLiftMotorChannel = 7;
	
	private final int kLiftLimitTop = 1;
	private DigitalInput limitSwitchLiftTop;
	private final int kLiftLimitBottom = 1;
	private DigitalInput limitSwitchLiftBottom;
	
	
	
	private static final int kSlotIdx = 0;
	private static final int kPidIdx = 0;
	private static final int ktimeout = 10; //number of ms to update closed-loop control
	private static final double kF = 0.2;
	private static final double kP = 0.3;
	private static final double kI = 0.0;
	private static final double kD = 0.0;

	int rawPerRev = -13300 - 2347 + 700;  //neg numbers go up in air
	//int rawPerRev = -8200;  //neg numbers go up in air
	//height setpoints in inches
	double bottomHeight = 0.0;
	double topHeight = 30.0;
	double switchHeight = 20.0;
	double exchangeHeight = 2.0;
	double tierHeight = 11.0;
	
	int liftButtonHeight = -1;
	
	boolean manualFlag;



	public Lift(Joystick maniStick) {
		this.maniStick = maniStick;
	}

	public void liftInit() {

		liftMotor = new WPI_TalonSRX(kLiftMotorChannel); //A.K.A Elevator/Climb maniulator
		liftMotor.setInverted(true);

		liftMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, kPidIdx, ktimeout);

		// set closed loop gains in desired slot
		liftMotor.selectProfileSlot(kSlotIdx, kPidIdx);
		liftMotor.config_kF(kSlotIdx, kF, ktimeout);
		liftMotor.config_kP(kSlotIdx, kP, ktimeout);
		liftMotor.config_kI(kSlotIdx, kI, ktimeout);
		liftMotor.config_kD(kSlotIdx, kD, ktimeout);

		// set acceleration and vcruise velocity
		liftMotor.configMotionCruiseVelocity(15000, ktimeout);
		liftMotor.configMotionAcceleration(6000, ktimeout);

		// zero the sensor
		liftMotor.setSelectedSensorPosition(0, kPidIdx, ktimeout);

		manualFlag = false;

		this.setEncoderZero();
		this.elevatorLift(0);

	}


	//method to get the value from the armabot encoder- lakiera and pinzon
	public int getEncoder(){

		int x = liftMotor.getSelectedSensorPosition(kPidIdx);
		SmartDashboard.putNumber("liftHeight", x);
		return x;
	}

	//joystick method to zero encoder -pinzon & lakiera
	public void checkEncoderZero() {
		if(maniStick.getRawButton(XboxButtons.BACK_BUTTON)==true) {
			this.setEncoderZero();
		}

	}

	//base method to set the encoder value to zero -pinzon & lakiera
	public void setEncoderZero() {
		liftMotor.setSelectedSensorPosition(0, this.kPidIdx, this.ktimeout);

	}



	//method to convert height in inches off the ground to raw units -pinzon & lakiera
	public int convert(double height) {
		double diameter = 1.66; //inches
		double circumference = diameter * Math.PI;	//inches/rev
		double revs = height / circumference;
		double raw =  revs * this.rawPerRev;

		//SmartDashboard.putNumber(", value)

		return (int)raw;
	}



	//joystick method to make the lift move to a specific height -pinzon & lakiera & Mal Einstein
	public void  checkLiftPoints() {
		

		if (maniStick.getPOV()==XboxButtons.LB_BUTTON) {
			manualFlag = false;
			liftButtonHeight = this.convert(topHeight);
		}
		if (maniStick.getPOV()==XboxButtons.POV_DOWN) {
			manualFlag = false;
			liftButtonHeight = this.convert(bottomHeight);
		}
		if (maniStick.getPOV()==XboxButtons.POV_UP) {
			manualFlag = false;
			liftButtonHeight = this.convert(switchHeight);
		}
		if (maniStick.getPOV()==XboxButtons.POV_RIGHT) {
			manualFlag = false;
			liftButtonHeight = this.convert(exchangeHeight);
		}
		if (maniStick.getPOV()==XboxButtons.POV_LEFT) {
			manualFlag = false;
			liftButtonHeight = this.convert(tierHeight);
		}
			
			
			if(manualFlag == false) {

				elevatorLift(liftButtonHeight);
			}

			SmartDashboard.putNumber("LiftButtonHeight", liftButtonHeight);
		}



	


	//method to lift up (With Joystick) - mathew & marlahna
	public void checkElevatorLift() {

		double thresh = 0.1;
		double liftJoyValue = maniStick.getRawAxis(LIFT_AXIS);
		SmartDashboard.putNumber("Lift Axis", liftJoyValue);


		if (Math.abs(liftJoyValue) > thresh) {
			manualFlag = true;
			if(manualFlag == true) {
				liftMotor.set(ControlMode.PercentOutput, liftJoyValue);
			}
		} else { 
			if(manualFlag == true) {
				liftMotor.set(ControlMode.PercentOutput, 0);
			}
		}

		SmartDashboard.putBoolean("liftManualFlag", manualFlag);
	}

	//method to lift up (to a SET Position) -mathew & marlahna
	public void elevatorLift(double setHeight) {

		SmartDashboard.putNumber("setHeight", setHeight);
		liftMotor.set(ControlMode.MotionMagic, setHeight); 
	}


	//Basic method to climb down -Aldenis
	public void climbDown() {
		liftMotor.set(-1.0);
	}



}

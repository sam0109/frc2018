package org.usfirst.frc.team1660.robot.subsystems;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team1660.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class LiftSubsystem extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	private WPI_TalonSRX m_liftMotor;
	
	private DigitalInput limitLiftTop = new DigitalInput(RobotMap.LIFT_LIMIT_TOP_CHANNEL);
	private DigitalInput limitLiftBottom = new DigitalInput(RobotMap.LIFT_LIMIT_BOTTOM_CHANNEL);
	
	// TODO: find a suitable place for these
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
	// TODO: find a suitable place for these
	double bottomHeight = 0.0;
	double topHeight = 30.0;
	double switchHeight = 20.0;
	double exchangeHeight = 2.0;
	double tierHeight = 11.0;
	int liftButtonHeight = -1;
	boolean manualFlag = false;
	double m_threshold = 0.01;
	
	public LiftSubsystem()
	{
		m_liftMotor = new WPI_TalonSRX(RobotMap.LIFT_MOTOR_CHANNEL); //A.K.A Elevator/Climb maniulator
		m_liftMotor.setInverted(true);
		
		m_liftMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, kPidIdx, ktimeout);

		// set closed loop gains in desired slot
		m_liftMotor.selectProfileSlot(kSlotIdx, kPidIdx);
		m_liftMotor.config_kF(kSlotIdx, kF, ktimeout);
		m_liftMotor.config_kP(kSlotIdx, kP, ktimeout);
		m_liftMotor.config_kI(kSlotIdx, kI, ktimeout);
		m_liftMotor.config_kD(kSlotIdx, kD, ktimeout);

		// set acceleration and cruise velocity
		m_liftMotor.configMotionCruiseVelocity(15000, ktimeout);
		m_liftMotor.configMotionAcceleration(6000, ktimeout);

		// zero the sensor
		m_liftMotor.setSelectedSensorPosition(0, kPidIdx, ktimeout);

		this.setEncoderZero();
	}

	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	//Method to move the elevator up & down- mathew & marlahna
	public void moveLift(double speed)
	{
		if (speed > m_threshold)
		{
			if (!limitLiftTop.get())
			{
				m_liftMotor.set(ControlMode.PercentOutput, speed);
			}
		}
		else if (speed < m_threshold)
		{
			if (!limitLiftBottom.get())
			{
				m_liftMotor.set(ControlMode.PercentOutput, speed);
			}
		}
		else
		{
			m_liftMotor.set(ControlMode.PercentOutput, 0.0);
		}
	}
	
	public void setLiftToHeight(int height)
	{
		// TODO: This is incorrect. I don't know how the motors
		// behave though, so I don't know how to set these values.
		moveLift(this.convert(switchHeight));
	}
	
	//method to get the value from the encoder- lakiera and pinzon (Black side faces LEFT, Silver side faces RIGHT)
	public int getEncoder()
	{
		return m_liftMotor.getSelectedSensorPosition(kPidIdx);
	}

	//base method to set the encoder value to zero -pinzon & lakiera
	public void setEncoderZero()
	{
		m_liftMotor.setSelectedSensorPosition(0, this.kPidIdx, this.ktimeout);
	}
	
	//method to convert height in inches off the ground to raw units -pinzon & lakiera
	public int convert(double height)
	{
		double diameter = 1.66; //inches
		double circumference = diameter * Math.PI;	//inches/rev
		double revs = height / circumference;
		double raw =  revs * this.rawPerRev;

		//SmartDashboard.putNumber(", value)

		return (int)raw;
	}
}
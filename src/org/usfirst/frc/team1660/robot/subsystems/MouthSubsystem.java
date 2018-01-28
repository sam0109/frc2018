package org.usfirst.frc.team1660.robot.subsystems;
import org.usfirst.frc.team1660.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class MouthSubsystem extends Subsystem
{
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	// Mouth motor declarations
	private WPI_TalonSRX m_mouthMotorLeft;
	private WPI_TalonSRX m_mouthMotorRight;
	private DigitalInput m_limitSwitchMouth;
	
	private double m_speed = 0.5;
	
	public MouthSubsystem()
	{
		m_limitSwitchMouth = new DigitalInput(RobotMap.MOUTH_LIMITER_CHANNEL);
		m_mouthMotorRight = new WPI_TalonSRX(RobotMap.MOUTH_RIGHT_CHANNEL);
		m_mouthMotorLeft = new WPI_TalonSRX(RobotMap.MOUTH_LEFT_CHANNEL);
		m_mouthMotorLeft.setInverted(true);
	}

	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	
	//eat method by Kwaku, does takes in the power cube  
	public void eat()
	{
		m_mouthMotorLeft.set(m_speed);
		m_mouthMotorRight.set(m_speed);
	}
	
	// Basic method for robot to spit out a PowerCube -Kwaku Boafo
	public void spit()
	{
		m_mouthMotorLeft.set(-m_speed);
		m_mouthMotorRight.set(-m_speed);
	}

	
	//not moving method by Mal, method to hold the box
	public void shutUp()
	{
		m_mouthMotorLeft.set(0);
		m_mouthMotorRight.set(0);
	}
	
	public void setMouthSpeed(double speed)
	{
		m_speed = speed;
	}
}
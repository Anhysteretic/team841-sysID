package frc.robot;

import static edu.wpi.first.units.Units.Volts;

import com.ctre.phoenix6.mechanisms.swerve.SwerveDrivetrain;
import com.ctre.phoenix6.mechanisms.swerve.SwerveDrivetrainConstants;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModuleConstants;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.ctre.phoenix6.SignalLogger;


import edu.wpi.first.units.Measure;
import edu.wpi.first.units.Voltage;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

import java.util.function.Supplier;

public class Drivetrain extends SwerveDrivetrain implements Subsystem {

  private SysIdRoutine DriveRoutine = new SysIdRoutine(
    new SysIdRoutine.Config(null, null, null, (state)->SignalLogger.writeString("State", state.toString())), 
    new SysIdRoutine.Mechanism(
      (Measure<Voltage> volts) -> setControl(new SwerveVoltageRequest().withVoltage(volts.in(Volts))),
      null, 
      this)
    );

  public Command DrivesysIdQuasistatic(SysIdRoutine.Direction direction) {
      return DriveRoutine.quasistatic(direction);
    }
    
  public Command DrivesysIdDynamic(SysIdRoutine.Direction direction) {
      return DriveRoutine.dynamic(direction);
    }

  private SysIdRoutine SteerRoutine = new SysIdRoutine(
    new SysIdRoutine.Config(null, null, null, (state)->SignalLogger.writeString("State", state.toString())),
    new SysIdRoutine.Mechanism(
      (Measure<Voltage> volts) -> setControl(new SwerveSteerVoltageRequest().withVoltage(volts.in(Volts))),
      null,
      this
    )
  );

  public Command SteersysIdQuasistatic(SysIdRoutine.Direction direction){
    return SteerRoutine.quasistatic(direction);
  }

  public Command SteersysIdDynamic(SysIdRoutine.Direction direction){
    return SteerRoutine.dynamic(direction);
  }

  public Drivetrain(
      SwerveDrivetrainConstants driveTrainConstants,
      double OdometryUpdateFrequency,
      SwerveModuleConstants... modules) {
    super(driveTrainConstants, OdometryUpdateFrequency, modules);
  }

  public Drivetrain(
      SwerveDrivetrainConstants driveTrainConstants, SwerveModuleConstants... modules) {
    super(driveTrainConstants, modules);
  }

  public Command applyRequest(Supplier<SwerveRequest> requestSupplier) {
    return new RunCommand(
        () -> {
          this.setControl(requestSupplier.get());
        },
        this);
  }

  
}

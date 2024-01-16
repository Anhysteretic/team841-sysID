package frc.robot;

import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;

public class sysidrobot {

    final double MaxSpeed = 6; // 6 meters per second desired top speed
  final double MaxAngularRate = Math.PI; // Half a rotation per second max angular velocity

    private Drivetrain m_drive = Swerve.drivetrain;

    SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage);
    SwerveRequest.RobotCentric rdrive = new SwerveRequest.RobotCentric()
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

    CommandPS4Controller joystick =
      new CommandPS4Controller(0); // My joystick

    Telemetry logger = new Telemetry(MaxSpeed);


    private void configureBindings() {}

  private void configureCoBindings() {
    joystick.triangle().whileTrue(m_drive.SteersysIdQuasistatic(Direction.kForward));
    joystick.circle().whileTrue(m_drive.SteersysIdQuasistatic(Direction.kReverse));
    joystick.square().whileTrue(m_drive.SteersysIdDynamic(Direction.kForward));
    joystick.cross().whileTrue(m_drive.SteersysIdDynamic(Direction.kReverse));

    joystick.L2().whileTrue(m_drive.DrivesysIdQuasistatic(Direction.kForward));
    joystick.L1().whileTrue(m_drive.DrivesysIdQuasistatic(Direction.kReverse));
    joystick.R2().whileTrue(m_drive.DrivesysIdDynamic(Direction.kForward));
    joystick.R1().whileTrue(m_drive.DrivesysIdDynamic(Direction.kReverse));
  }

  public sysidrobot() {
    configureCoBindings();
  }

    public Command getAutonomousCommand() {
        // Do nothing
        return m_drive.run(() -> {
        });
    }
}

package io.github.astrapi69.silent.mouse.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SettingsModelBean
{
	@Builder.Default
	Integer xAxis = 1;
	@Builder.Default
	Integer yAxis = 1;
	@Builder.Default
	Integer intervalOfSeconds = 10;
	@Builder.Default
	Integer intervalOfMouseMovementsCheckInSeconds = 5;
	@Builder.Default
	boolean moveOnStartup = true;
}

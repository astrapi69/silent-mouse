/**
 * The MIT License
 *
 * Copyright (C) 2022 Asterios Raptis
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.astrapi69.silent.mouse;

public class SettingsModelBean
{
	public static final String X_AXIS = "xAxis";
	public static final String Y_AXIS = "yAxis";
	public static final String INTERVAL_OF_SECONDS = "intervalOfSeconds";
	public static final String INTERVAL_OF_MOUSE_MOVEMENTS_CHECK_IN_SECONDS = "intervalOfMouseMovementsCheckInSeconds";
	public static final String MOVE_ON_STARTUP = "moveOnStartup";
	private Integer xAxis = 1;
	private Integer yAxis = 1;
	private Integer intervalOfSeconds = 10;
	private Integer intervalOfMouseMovementsCheckInSeconds = 5;
	private boolean moveOnStartup = true;

	public SettingsModelBean(Integer xAxis, Integer yAxis, Integer intervalOfSeconds,
		Integer intervalOfMouseMovementsCheckInSeconds, boolean moveOnStartup)
	{
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.intervalOfSeconds = intervalOfSeconds;
		this.intervalOfMouseMovementsCheckInSeconds = intervalOfMouseMovementsCheckInSeconds;
		this.moveOnStartup = moveOnStartup;
	}

	public SettingsModelBean()
	{
	}

	protected SettingsModelBean(SettingsModelBeanBuilder<?, ?> b)
	{
		this.xAxis = b.xAxis$value;
		this.yAxis = b.yAxis$value;
		this.intervalOfSeconds = b.intervalOfSeconds$value;
		this.intervalOfMouseMovementsCheckInSeconds = b.intervalOfMouseMovementsCheckInSeconds$value;
		this.moveOnStartup = b.moveOnStartup$value;
	}

	static Integer $default$xAxis()
	{
		return 1;
	}

	static Integer $default$yAxis()
	{
		return 1;
	}

	static Integer $default$intervalOfSeconds()
	{
		return 10;
	}

	static Integer $default$intervalOfMouseMovementsCheckInSeconds()
	{
		return 5;
	}

	static boolean $default$moveOnStartup()
	{
		return true;
	}

	public static SettingsModelBeanBuilder<?, ?> builder()
	{
		return new SettingsModelBeanBuilderImpl();
	}

	public Integer getXAxis()
	{
		return this.xAxis;
	}

	public void setXAxis(Integer xAxis)
	{
		this.xAxis = xAxis;
	}

	public Integer getYAxis()
	{
		return this.yAxis;
	}

	public void setYAxis(Integer yAxis)
	{
		this.yAxis = yAxis;
	}

	public Integer getIntervalOfSeconds()
	{
		return this.intervalOfSeconds;
	}

	public void setIntervalOfSeconds(Integer intervalOfSeconds)
	{
		this.intervalOfSeconds = intervalOfSeconds;
	}

	public Integer getIntervalOfMouseMovementsCheckInSeconds()
	{
		return this.intervalOfMouseMovementsCheckInSeconds;
	}

	public void setIntervalOfMouseMovementsCheckInSeconds(
		Integer intervalOfMouseMovementsCheckInSeconds)
	{
		this.intervalOfMouseMovementsCheckInSeconds = intervalOfMouseMovementsCheckInSeconds;
	}

	public boolean isMoveOnStartup()
	{
		return this.moveOnStartup;
	}

	public void setMoveOnStartup(boolean moveOnStartup)
	{
		this.moveOnStartup = moveOnStartup;
	}

	public boolean equals(final Object o)
	{
		if (o == this)
			return true;
		if (!(o instanceof SettingsModelBean))
			return false;
		final SettingsModelBean other = (SettingsModelBean)o;
		if (!other.canEqual((Object)this))
			return false;
		final Object this$xAxis = this.getXAxis();
		final Object other$xAxis = other.getXAxis();
		if (this$xAxis == null ? other$xAxis != null : !this$xAxis.equals(other$xAxis))
			return false;
		final Object this$yAxis = this.getYAxis();
		final Object other$yAxis = other.getYAxis();
		if (this$yAxis == null ? other$yAxis != null : !this$yAxis.equals(other$yAxis))
			return false;
		final Object this$intervalOfSeconds = this.getIntervalOfSeconds();
		final Object other$intervalOfSeconds = other.getIntervalOfSeconds();
		if (this$intervalOfSeconds == null
			? other$intervalOfSeconds != null
			: !this$intervalOfSeconds.equals(other$intervalOfSeconds))
			return false;
		final Object this$intervalOfMouseMovementsCheckInSeconds = this
			.getIntervalOfMouseMovementsCheckInSeconds();
		final Object other$intervalOfMouseMovementsCheckInSeconds = other
			.getIntervalOfMouseMovementsCheckInSeconds();
		if (this$intervalOfMouseMovementsCheckInSeconds == null
			? other$intervalOfMouseMovementsCheckInSeconds != null
			: !this$intervalOfMouseMovementsCheckInSeconds
				.equals(other$intervalOfMouseMovementsCheckInSeconds))
			return false;
		if (this.isMoveOnStartup() != other.isMoveOnStartup())
			return false;
		return true;
	}

	protected boolean canEqual(final Object other)
	{
		return other instanceof SettingsModelBean;
	}

	public int hashCode()
	{
		final int PRIME = 59;
		int result = 1;
		final Object $xAxis = this.getXAxis();
		result = result * PRIME + ($xAxis == null ? 43 : $xAxis.hashCode());
		final Object $yAxis = this.getYAxis();
		result = result * PRIME + ($yAxis == null ? 43 : $yAxis.hashCode());
		final Object $intervalOfSeconds = this.getIntervalOfSeconds();
		result = result * PRIME + ($intervalOfSeconds == null ? 43 : $intervalOfSeconds.hashCode());
		final Object $intervalOfMouseMovementsCheckInSeconds = this
			.getIntervalOfMouseMovementsCheckInSeconds();
		result = result * PRIME + ($intervalOfMouseMovementsCheckInSeconds == null
			? 43
			: $intervalOfMouseMovementsCheckInSeconds.hashCode());
		result = result * PRIME + (this.isMoveOnStartup() ? 79 : 97);
		return result;
	}

	public String toString()
	{
		return "SettingsModelBean(xAxis=" + this.getXAxis() + ", yAxis=" + this.getYAxis()
			+ ", intervalOfSeconds=" + this.getIntervalOfSeconds()
			+ ", intervalOfMouseMovementsCheckInSeconds="
			+ this.getIntervalOfMouseMovementsCheckInSeconds() + ", moveOnStartup="
			+ this.isMoveOnStartup() + ")";
	}

	public SettingsModelBeanBuilder<?, ?> toBuilder()
	{
		return new SettingsModelBeanBuilderImpl().$fillValuesFrom(this);
	}

	public static abstract class SettingsModelBeanBuilder<C extends SettingsModelBean, B extends SettingsModelBeanBuilder<C, B>>
	{
		Integer xAxis$value;
		boolean xAxis$set;
		Integer yAxis$value;
		boolean yAxis$set;
		Integer intervalOfSeconds$value;
		boolean intervalOfSeconds$set;
		Integer intervalOfMouseMovementsCheckInSeconds$value;
		boolean intervalOfMouseMovementsCheckInSeconds$set;
		boolean moveOnStartup$value;
		boolean moveOnStartup$set;

		private static void $fillValuesFromInstanceIntoBuilder(SettingsModelBean instance,
			SettingsModelBeanBuilder<?, ?> b)
		{
			b.xAxis(instance.xAxis);
			b.yAxis(instance.yAxis);
			b.intervalOfSeconds(instance.intervalOfSeconds);
			b.intervalOfMouseMovementsCheckInSeconds(
				instance.intervalOfMouseMovementsCheckInSeconds);
			b.moveOnStartup(instance.moveOnStartup);
		}

		public B xAxis(Integer xAxis)
		{
			this.xAxis$value = xAxis;
			this.xAxis$set = true;
			return self();
		}

		public B yAxis(Integer yAxis)
		{
			this.yAxis$value = yAxis;
			this.yAxis$set = true;
			return self();
		}

		public B intervalOfSeconds(Integer intervalOfSeconds)
		{
			this.intervalOfSeconds$value = intervalOfSeconds;
			this.intervalOfSeconds$set = true;
			return self();
		}

		public B intervalOfMouseMovementsCheckInSeconds(
			Integer intervalOfMouseMovementsCheckInSeconds)
		{
			this.intervalOfMouseMovementsCheckInSeconds$value = intervalOfMouseMovementsCheckInSeconds;
			this.intervalOfMouseMovementsCheckInSeconds$set = true;
			return self();
		}

		public B moveOnStartup(boolean moveOnStartup)
		{
			this.moveOnStartup$value = moveOnStartup;
			this.moveOnStartup$set = true;
			return self();
		}

		protected B $fillValuesFrom(C instance)
		{
			SettingsModelBeanBuilder.$fillValuesFromInstanceIntoBuilder(instance, this);
			return self();
		}

		protected abstract B self();

		public abstract C build();

		public String toString()
		{
			return "SettingsModelBean.SettingsModelBeanBuilder(xAxis$value=" + this.xAxis$value
				+ ", yAxis$value=" + this.yAxis$value + ", intervalOfSeconds$value="
				+ this.intervalOfSeconds$value + ", intervalOfMouseMovementsCheckInSeconds$value="
				+ this.intervalOfMouseMovementsCheckInSeconds$value + ", moveOnStartup$value="
				+ this.moveOnStartup$value + ")";
		}
	}

	private static final class SettingsModelBeanBuilderImpl
		extends
			SettingsModelBeanBuilder<SettingsModelBean, SettingsModelBeanBuilderImpl>
	{
		private SettingsModelBeanBuilderImpl()
		{
		}

		protected SettingsModelBeanBuilderImpl self()
		{
			return this;
		}

		public SettingsModelBean build()
		{
			if (!this.xAxis$set)
			{
				this.xAxis$value = SettingsModelBean.$default$xAxis();
			}
			if (!this.yAxis$set)
			{
				this.yAxis$value = SettingsModelBean.$default$yAxis();
			}
			if (!this.intervalOfSeconds$set)
			{
				this.intervalOfSeconds$value = SettingsModelBean.$default$intervalOfSeconds();
			}
			if (!this.intervalOfMouseMovementsCheckInSeconds$set)
			{
				this.intervalOfMouseMovementsCheckInSeconds$value = SettingsModelBean
					.$default$intervalOfMouseMovementsCheckInSeconds();
			}
			if (!this.moveOnStartup$set)
			{
				this.moveOnStartup$value = SettingsModelBean.$default$moveOnStartup();
			}
			SettingsModelBean settingsModelBean = new SettingsModelBean(this);
			return settingsModelBean;
		}
	}
}

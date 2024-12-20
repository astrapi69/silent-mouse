/**
 * The MIT License
 *
 * Copyright (C) 2022 Asterios Raptis
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.astrapi69.silent.mouse.model;


/**
 * The {@link SettingsModelBean_} class represents the configuration settings for mouse movements,
 * including axis movements, interval settings, and whether the mouse movement starts on startup
 */
public class SettingsModelBean_
{
	/**
	 * Constant for the "not set" option
	 */
	public static final String NOT_SET = "not set";

	/**
	 * Key for the X-axis setting in preferences
	 */
	public static final String X_AXIS = "xAxis";

	/**
	 * Key for the Y-axis setting in preferences
	 */
	public static final String Y_AXIS = "yAxis";

	/**
	 * Key for the interval of seconds setting in preferences
	 */
	public static final String INTERVAL_OF_SECONDS = "intervalOfSeconds";

	/**
	 * Key for the interval of mouse movements check in preferences
	 */
	public static final String INTERVAL_OF_MOUSE_MOVEMENTS_CHECK_IN_SECONDS = "intervalOfMouseMovementsCheckInSeconds";

	/**
	 * Key for the move on startup setting in preferences
	 */
	public static final String MOVE_ON_STARTUP = "moveOnStartup";

	/** X-axis movement value */
	private Integer xAxis = 1;

	/** Y-axis movement value */
	private Integer yAxis = 1;

	/** Interval in seconds for mouse movement */
	private Integer intervalOfSeconds = 10;

	/** Interval in seconds for checking mouse movements */
	private Integer intervalOfMouseMovementsCheckInSeconds = 5;

	/** Flag for enabling mouse movement on startup */
	private boolean moveOnStartup = true;

	/**
	 * Constructor for creating a {@link SettingsModelBean_} with specific settings
	 *
	 * @param xAxis
	 *            the X-axis movement value
	 * @param yAxis
	 *            the Y-axis movement value
	 * @param intervalOfSeconds
	 *            the interval in seconds for mouse movement
	 * @param intervalOfMouseMovementsCheckInSeconds
	 *            the interval for checking mouse movements
	 * @param moveOnStartup
	 *            whether to move the mouse on startup
	 */
	public SettingsModelBean_(Integer xAxis, Integer yAxis, Integer intervalOfSeconds,
		Integer intervalOfMouseMovementsCheckInSeconds, boolean moveOnStartup)
	{
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.intervalOfSeconds = intervalOfSeconds;
		this.intervalOfMouseMovementsCheckInSeconds = intervalOfMouseMovementsCheckInSeconds;
		this.moveOnStartup = moveOnStartup;
	}

	/**
	 * Default constructor for creating an empty {@link SettingsModelBean_}
	 */
	public SettingsModelBean_()
	{
	}

	/**
	 * Builder-based constructor for {@link SettingsModelBean_}
	 *
	 * @param b
	 *            the builder to create the {@link SettingsModelBean_}
	 */
	protected SettingsModelBean_(SettingsModelBeanBuilder<?, ?> b)
	{
		this.xAxis = b.xAxis$value;
		this.yAxis = b.yAxis$value;
		this.intervalOfSeconds = b.intervalOfSeconds$value;
		this.intervalOfMouseMovementsCheckInSeconds = b.intervalOfMouseMovementsCheckInSeconds$value;
		this.moveOnStartup = b.moveOnStartup$value;
	}

	/**
	 * Default X-axis value @return the integer
	 */
	static Integer $default$xAxis()
	{
		return 1;
	}

	/**
	 * Default Y-axis value @return the integer
	 */
	static Integer $default$yAxis()
	{
		return 1;
	}

	/**
	 * Default interval in seconds @return the integer
	 */
	static Integer $default$intervalOfSeconds()
	{
		return 10;
	}

	/**
	 * Default interval for checking mouse movements @return the integer
	 */
	static Integer $default$intervalOfMouseMovementsCheckInSeconds()
	{
		return 5;
	}

	/**
	 * Default move on startup boolean.
	 *
	 * @return the boolean
	 */
	static boolean $default$moveOnStartup()
	{
		return true;
	}

	/**
	 * Builder settings model bean builder.
	 *
	 * @return the settings model bean builder
	 */
	public static SettingsModelBeanBuilder<?, ?> builder()
	{
		return new SettingsModelBeanBuilderImpl();
	}

	/**
	 * Gets x axis.
	 *
	 * @return the x axis
	 */
	public Integer getXAxis()
	{
		return this.xAxis;
	}

	/**
	 * Sets x axis.
	 *
	 * @param xAxis
	 *            the x axis
	 */
	public void setXAxis(Integer xAxis)
	{
		this.xAxis = xAxis;
	}

	/**
	 * Gets y axis.
	 *
	 * @return the y axis
	 */
	public Integer getYAxis()
	{
		return this.yAxis;
	}

	/**
	 * Sets y axis.
	 *
	 * @param yAxis
	 *            the y axis
	 */
	public void setYAxis(Integer yAxis)
	{
		this.yAxis = yAxis;
	}

	/**
	 * Gets interval of seconds.
	 *
	 * @return the interval of seconds
	 */
	public Integer getIntervalOfSeconds()
	{
		return this.intervalOfSeconds;
	}

	/**
	 * Sets interval of seconds.
	 *
	 * @param intervalOfSeconds
	 *            the interval of seconds
	 */
	public void setIntervalOfSeconds(Integer intervalOfSeconds)
	{
		this.intervalOfSeconds = intervalOfSeconds;
	}

	/**
	 * Gets interval of mouse movements check in seconds.
	 *
	 * @return the interval of mouse movements check in seconds
	 */
	public Integer getIntervalOfMouseMovementsCheckInSeconds()
	{
		return this.intervalOfMouseMovementsCheckInSeconds;
	}

	/**
	 * Sets interval of mouse movements check in seconds.
	 *
	 * @param intervalOfMouseMovementsCheckInSeconds
	 *            the interval of mouse movements check in seconds
	 */
	public void setIntervalOfMouseMovementsCheckInSeconds(
		Integer intervalOfMouseMovementsCheckInSeconds)
	{
		this.intervalOfMouseMovementsCheckInSeconds = intervalOfMouseMovementsCheckInSeconds;
	}

	/**
	 * Is move on startup boolean.
	 *
	 * @return the boolean
	 */
	public boolean isMoveOnStartup()
	{
		return this.moveOnStartup;
	}

	/**
	 * Sets move on startup.
	 *
	 * @param moveOnStartup
	 *            the move on startup
	 */
	public void setMoveOnStartup(boolean moveOnStartup)
	{
		this.moveOnStartup = moveOnStartup;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(final Object o)
	{
		if (o == this)
			return true;
		if (!(o instanceof SettingsModelBean_))
			return false;
		final SettingsModelBean_ other = (SettingsModelBean_)o;
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

	/**
	 * Can equal boolean.
	 *
	 * @param other
	 *            the other
	 * @return the boolean
	 */
	protected boolean canEqual(final Object other)
	{
		return other instanceof SettingsModelBean_;
	}

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
	public String toString()
	{
		return "SettingsModelBean(xAxis=" + this.getXAxis() + ", yAxis=" + this.getYAxis()
			+ ", intervalOfSeconds=" + this.getIntervalOfSeconds()
			+ ", intervalOfMouseMovementsCheckInSeconds="
			+ this.getIntervalOfMouseMovementsCheckInSeconds() + ", moveOnStartup="
			+ this.isMoveOnStartup() + ")";
	}

	/**
	 * To builder settings model bean builder.
	 *
	 * @return the settings model bean builder
	 */
	public SettingsModelBeanBuilder<?, ?> toBuilder()
	{
		return new SettingsModelBeanBuilderImpl().$fillValuesFrom(this);
	}

	/**
	 * The type Settings model bean builder.
	 *
	 * @param <C>
	 *            the type parameter
	 * @param <B>
	 *            the type parameter
	 */
	public static abstract class SettingsModelBeanBuilder<C extends SettingsModelBean_, B extends SettingsModelBeanBuilder<C, B>>
	{
		/**
		 * The X axis value.
		 */
		Integer xAxis$value;
		/**
		 * The X axis set.
		 */
		boolean xAxis$set;
		/**
		 * The Y axis value.
		 */
		Integer yAxis$value;
		/**
		 * The Y axis set.
		 */
		boolean yAxis$set;
		/**
		 * The Interval of seconds value.
		 */
		Integer intervalOfSeconds$value;
		/**
		 * The Interval of seconds set.
		 */
		boolean intervalOfSeconds$set;
		/**
		 * The Interval of mouse movements check in seconds value.
		 */
		Integer intervalOfMouseMovementsCheckInSeconds$value;
		/**
		 * The Interval of mouse movements check in seconds set.
		 */
		boolean intervalOfMouseMovementsCheckInSeconds$set;
		/**
		 * The Move on startup value.
		 */
		boolean moveOnStartup$value;
		/**
		 * The Move on startup set.
		 */
		boolean moveOnStartup$set;

		private static void $fillValuesFromInstanceIntoBuilder(SettingsModelBean_ instance,
			SettingsModelBeanBuilder<?, ?> b)
		{
			b.xAxis(instance.xAxis);
			b.yAxis(instance.yAxis);
			b.intervalOfSeconds(instance.intervalOfSeconds);
			b.intervalOfMouseMovementsCheckInSeconds(
				instance.intervalOfMouseMovementsCheckInSeconds);
			b.moveOnStartup(instance.moveOnStartup);
		}

		/**
		 * X axis b.
		 *
		 * @param xAxis
		 *            the x axis
		 * @return the b
		 */
		public B xAxis(Integer xAxis)
		{
			this.xAxis$value = xAxis;
			this.xAxis$set = true;
			return self();
		}

		/**
		 * Y axis b.
		 *
		 * @param yAxis
		 *            the y axis
		 * @return the b
		 */
		public B yAxis(Integer yAxis)
		{
			this.yAxis$value = yAxis;
			this.yAxis$set = true;
			return self();
		}

		/**
		 * Interval of seconds b.
		 *
		 * @param intervalOfSeconds
		 *            the interval of seconds
		 * @return the b
		 */
		public B intervalOfSeconds(Integer intervalOfSeconds)
		{
			this.intervalOfSeconds$value = intervalOfSeconds;
			this.intervalOfSeconds$set = true;
			return self();
		}

		/**
		 * Interval of mouse movements check in seconds b.
		 *
		 * @param intervalOfMouseMovementsCheckInSeconds
		 *            the interval of mouse movements check in seconds
		 * @return the b
		 */
		public B intervalOfMouseMovementsCheckInSeconds(
			Integer intervalOfMouseMovementsCheckInSeconds)
		{
			this.intervalOfMouseMovementsCheckInSeconds$value = intervalOfMouseMovementsCheckInSeconds;
			this.intervalOfMouseMovementsCheckInSeconds$set = true;
			return self();
		}

		/**
		 * Move on startup b.
		 *
		 * @param moveOnStartup
		 *            the move on startup
		 * @return the b
		 */
		public B moveOnStartup(boolean moveOnStartup)
		{
			this.moveOnStartup$value = moveOnStartup;
			this.moveOnStartup$set = true;
			return self();
		}

		/**
		 * Fill values from b.
		 *
		 * @param instance
		 *            the instance
		 * @return the b
		 */
		protected B $fillValuesFrom(C instance)
		{
			SettingsModelBeanBuilder.$fillValuesFromInstanceIntoBuilder(instance, this);
			return self();
		}

		/**
		 * Self b.
		 *
		 * @return the b
		 */
		protected abstract B self();

		/**
		 * Build c.
		 *
		 * @return the c
		 */
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
			SettingsModelBeanBuilder<SettingsModelBean_, SettingsModelBeanBuilderImpl>
	{
		private SettingsModelBeanBuilderImpl()
		{
		}

		protected SettingsModelBeanBuilderImpl self()
		{
			return this;
		}

		public SettingsModelBean_ build()
		{
			if (!this.xAxis$set)
			{
				this.xAxis$value = SettingsModelBean_.$default$xAxis();
			}
			if (!this.yAxis$set)
			{
				this.yAxis$value = SettingsModelBean_.$default$yAxis();
			}
			if (!this.intervalOfSeconds$set)
			{
				this.intervalOfSeconds$value = SettingsModelBean_.$default$intervalOfSeconds();
			}
			if (!this.intervalOfMouseMovementsCheckInSeconds$set)
			{
				this.intervalOfMouseMovementsCheckInSeconds$value = SettingsModelBean_
					.$default$intervalOfMouseMovementsCheckInSeconds();
			}
			if (!this.moveOnStartup$set)
			{
				this.moveOnStartup$value = SettingsModelBean_.$default$moveOnStartup();
			}
			SettingsModelBean_ settingsModelBean = new SettingsModelBean_(this);
			return settingsModelBean;
		}
	}

}
